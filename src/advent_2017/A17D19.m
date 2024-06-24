#import "A17D19.h"

NS_ASSUME_NONNULL_BEGIN

typedef NS_ENUM(NSUInteger, A17D17Dir) {
    A17D17DirDown,
    A17D17DirUp,
    A17D17DirLeft,
    A17D17DirRight,
};

@interface A17D19State : NSObject

@property (nonatomic, assign) A17D17Dir dir;
@property (nonatomic, assign) NSUInteger row;
@property (nonatomic, assign) NSUInteger col;

@end

@implementation A17D19State

- (void)move {
    switch (_dir) {
        case A17D17DirDown:
            _row++;
            break;
        case A17D17DirUp:
            _row--;
            break;
        case A17D17DirLeft:
            _col--;
            break;
        case A17D17DirRight:
            _col++;
            break;
    }
}

- (void)updateDir:(NSArray<NSArray<NSString *> *> *)matrix {
    if ([matrix[_row][_col] isEqualToString:@"+"]) {
        switch (_dir) {
            case A17D17DirDown:
            case A17D17DirUp: {
                NSString *adjacent = matrix[_row][_col - 1];
                if ([adjacent isEqualToString:@"|"] || [adjacent isEqualToString:@" "]) {
                    _dir = A17D17DirRight;
                } else {
                    _dir = A17D17DirLeft;
                }
                break;
            }
            case A17D17DirLeft:
            case A17D17DirRight: {
                NSString *adjacent = matrix[_row - 1][_col];
                if ([adjacent isEqualToString:@"-"] || [adjacent isEqualToString:@" "]) {
                    _dir = A17D17DirDown;
                } else {
                    _dir = A17D17DirUp;
                }
                break;
            }
        }
    }
}

@end

@interface A17D19 ()

@property (nonatomic, copy, readonly) NSArray<NSArray<NSString *> *> *matrix;

@end

@implementation A17D19 {
    NSArray<NSArray<NSString *> *> *_matrix;
}

- (NSArray<NSArray<NSString *> *> *)matrix {
    if (!_matrix) {
        NSMutableArray *tmp = [[NSMutableArray alloc] init];
        [self.input enumerateLinesUsingBlock:^(NSString *line, BOOL *stop) {
            NSMutableArray *tmp2 = [[NSMutableArray alloc] init];
            [line enumerateSubstringsInRange:NSMakeRange(0, line.length)
                                     options:NSStringEnumerationByComposedCharacterSequences
                                  usingBlock:^(NSString *substring, NSRange substringRange, NSRange enclosingRange, BOOL *stop) {
                [tmp2 addObject:substring];
            }];
            [tmp addObject:[tmp2 copy]];
        }];
        _matrix = [tmp copy];
    }
    return _matrix;
}

- (NSArray *)path {
    A17D19State *state = [[A17D19State alloc] init];
    state.dir = A17D17DirDown;
    state.row = 0;
    [self.matrix[0] enumerateObjectsUsingBlock:^(NSString *obj, NSUInteger idx, BOOL *stop) {
        if ([obj isEqualToString:@"|"]) {
            state.col = idx;
            *stop = YES;
        }
    }];
    
    NSMutableArray *result = [[NSMutableArray alloc] init];
    for (;;) {
        [state move];
        [state updateDir:self.matrix];
        [result addObject:@[@(state.row), @(state.col)]];
        if ([self.matrix[state.row][state.col] isEqualToString:@" "]) {
            break;
        }
    }
    return result;
}

- (nullable id)part1 {
    NSMutableSet *letters = [[NSMutableSet alloc] init];
    [@"ABCDEFGHIJKLMNOPQRSTUVWXYZ" enumerateSubstringsInRange:NSMakeRange(0, 26)
                                                      options:NSStringEnumerationByComposedCharacterSequences
                                                   usingBlock:^(NSString *substring, NSRange substringRange, NSRange enclosingRange, BOOL *stop) {
        [letters addObject:substring];
    }];
    NSMutableString *result = [[NSMutableString alloc] init];
    for (NSArray<NSNumber *> *coords in [self path]) {
        NSString *val = self.matrix[coords[0].integerValue][coords[1].integerValue];
        if ([letters containsObject:val]) {
            [result appendString:val];
        }
    }
    return result;
}

- (nullable id)part2 {
    return @([self path].count);
}

@end

NS_ASSUME_NONNULL_END
