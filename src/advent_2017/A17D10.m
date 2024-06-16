#import "A17D10.h"

NS_ASSUME_NONNULL_BEGIN

@implementation A17D10

- (NSString *)input {
    return @"129,154,49,198,200,133,97,254,41,6,2,1,255,0,191,108";
}

- (NSArray *)roundWithLengths:(NSArray<NSNumber *> *)lengths
                           xs:(NSMutableArray<NSNumber *> *)xs
                   currentPos:(NSUInteger)currentPos
                     skipSize:(NSUInteger)skipSize {
    for (NSNumber *length in lengths) {
        NSMutableArray<NSNumber *> *arr = [[NSMutableArray alloc] init];
        for (NSUInteger ndx = 0; ndx < 256; ndx++) {
            NSNumber *x = xs[(ndx + currentPos) % 256];
            if (ndx < length.integerValue) {
                [arr insertObject:x atIndex:0];
            } else {
                [arr addObject:x];
            }
        }
        [xs removeAllObjects];
        for (NSUInteger ndx = 0; ndx < 256; ndx++) {
            [xs addObject:arr[(ndx + 256 - currentPos) % 256]];
        }
        currentPos = (currentPos + length.integerValue + skipSize) % 256;
        skipSize = (skipSize + 1) % 256;
    }
    return @[xs, @(currentPos), @(skipSize)];
}

- (NSArray<NSNumber *> *)getInitialXs {
    NSMutableArray<NSNumber *> *xs = [[NSMutableArray alloc] init];
    for (NSUInteger ndx = 0; ndx < 256; ndx++) {
        [xs addObject:@(ndx)];
    }
    return [xs copy];
}

- (nullable id)part1 {
    NSMutableArray<NSNumber *> *xs = [[self getInitialXs] mutableCopy];
    NSMutableArray<NSNumber *> *lengths = [[NSMutableArray alloc] init];
    for (NSString *s in [self.input componentsSeparatedByString:@","]) {
        [lengths addObject:@(s.integerValue)];
    }
    NSArray *result = [self roundWithLengths:lengths
                                          xs:xs
                                  currentPos:0
                                    skipSize:0];
    xs = result[0];
    return @(xs[0].integerValue * xs[1].integerValue);
}

- (NSArray<NSNumber *> *)knotHashDecimal:(NSString *)s {
    NSMutableArray<NSNumber *> *lengths = [[NSMutableArray alloc] init];
    [s enumerateSubstringsInRange:NSMakeRange(0, s.length) options:NSStringEnumerationByComposedCharacterSequences
                       usingBlock:^(NSString *substring, NSRange substringRange, NSRange enclosingRange, BOOL *stop) {
        [lengths addObject:@([substring characterAtIndex:0])];
    }];
    [lengths addObjectsFromArray:@[@17, @31, @73, @47, @23]];
    NSMutableArray<NSNumber *> *xs = [[self getInitialXs] mutableCopy];
    NSUInteger currentPos = 0;
    NSUInteger skipSize = 0;
    for (NSUInteger x = 0; x < 64; x++) {
        NSArray *result = [self roundWithLengths:lengths
                                              xs:xs
                                      currentPos:currentPos
                                        skipSize:skipSize];
        xs = result[0];
        currentPos = ((NSNumber *)result[1]).integerValue;
        skipSize = ((NSNumber *)result[2]).integerValue;
    }
    NSMutableArray<NSNumber *> *result = [[NSMutableArray alloc] init];
    NSUInteger reduced = 0;
    for (NSUInteger idx = 0; idx < xs.count; idx++) {
        reduced ^= xs[idx].integerValue;
        if ((idx + 1) % 16 == 0) {
            [result addObject:@(reduced)];
            reduced = 0;
        }
    }
    return [result copy];
}

- (NSString *)knotHash:(NSString *)s {
    NSMutableString *result = [[NSMutableString alloc] init];
    for (NSNumber *number in [self knotHashDecimal:s]) {
        [result appendFormat:@"%02lx", (long)number.integerValue];
    }
    return [result copy];
}

- (nullable id)part2 {
    return [self knotHash:self.input];
}

@end

NS_ASSUME_NONNULL_END
