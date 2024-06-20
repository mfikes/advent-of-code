#import "A17D16.h"

NS_ASSUME_NONNULL_BEGIN

@interface A17D16 ()

@property (nonatomic, readonly) NSArray *danceMoves;

@end

@implementation A17D16 {
    NSArray *_danceMoves;
}

- (id)parseDanceMove:(NSString *)s {
    NSString *suffix = [s substringFromIndex:1];
    if ([s hasPrefix:@"s"]) {
        NSInteger arg1 = suffix.integerValue;
        return ^(NSArray *xs) {
            NSUInteger c = xs.count;
            NSMutableArray *result = [[NSMutableArray alloc] init];
            for (NSUInteger idx = 0; idx < c; idx++) {
                result[idx] = xs[(idx + (c - arg1)) % c];
            }
            return result;
        };
    } else if ([s hasPrefix:@"x"]) {
        return ^(NSArray *xs) {
            NSUInteger slashIndex = [suffix rangeOfString:@"/"].location;
            NSInteger arg1 = [suffix substringToIndex:slashIndex].integerValue;
            NSInteger arg2 = [suffix substringFromIndex:slashIndex + 1].integerValue;
            NSMutableArray *result = [[NSMutableArray alloc] initWithArray:xs];
            id tmp = result[arg1];
            result[arg1] = result[arg2];
            result[arg2] = tmp;
            return result;
        };
    } else {
        return ^(NSArray *xs) {
            NSString *s1 = [suffix substringToIndex:1];
            NSString *s2 = [suffix substringFromIndex:2];
            NSInteger arg1 = [xs indexOfObject:s1];
            NSInteger arg2 = [xs indexOfObject:s2];
            NSMutableArray *result = [[NSMutableArray alloc] initWithArray:xs];
            id tmp = result[arg1];
            result[arg1] = result[arg2];
            result[arg2] = tmp;
            return result;
        };
    }
}

- (NSArray *)danceMoves {
    if (!_danceMoves) {
        NSMutableArray *moves = [[NSMutableArray alloc] init];
        for (NSString *move in [self.input componentsSeparatedByString:@","]) {
            [moves addObject:[self parseDanceMove:move]];
        }
        _danceMoves = [moves copy];
    }
    return _danceMoves;
}

- (NSArray *)dance:(NSArray *)xs {
    for (NSArray *(^move)(NSArray *) in self.danceMoves) {
        xs = move(xs);
    }
    return xs;
}

- (NSArray<NSString *> *)startingPositions {
    return @[@"a", @"b", @"c", @"d", @"e", @"f", @"g", @"h", @"i", @"j", @"k", @"l", @"m", @"n", @"o", @"p"];
}

- (nullable id)part1 {
    return [[self dance:[self startingPositions]] componentsJoinedByString:@""];
}

- (nullable id)part2 {
    NSArray<NSString *> *positions = [self startingPositions];
    NSArray<NSString *> *positions0 = positions;
    NSUInteger period = 0;
    for (;;) {
        positions = [self dance:positions];
        period++;
        if ([positions isEqual:positions0]) {
            break;
        }
    }
    
    positions = [self startingPositions];
    for (NSUInteger n = 0; n < 1000000000 % period; n++) {
        positions = [self dance:positions];
    }
    
    return [positions componentsJoinedByString:@""];
}

@end

NS_ASSUME_NONNULL_END
