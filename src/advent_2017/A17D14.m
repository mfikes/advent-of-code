#import "A17D14.h"
#import "A17D10.h"
#import "A17D12.h"

NS_ASSUME_NONNULL_BEGIN

@implementation A17D14

- (NSString *)input {
    return @"ljoxqyyw";
}

- (NSUInteger)countOneBits:(NSInteger)number {
    NSUInteger count = 0;
    while (number) {
        count += number & 1;
        number >>= 1;
    }
    return count;
}

- (NSArray<NSArray<NSNumber *> *> *)knotHashMatrix:(NSString *)input {
    NSMutableArray<NSArray<NSNumber *> *> *result = [[NSMutableArray alloc] init];
    A17D10 *a17d10 = [[A17D10 alloc] initWithInputPath:nil];
    for (NSUInteger x = 0; x < 128; x++) {
        [result addObject:[a17d10 knotHashDecimal:[NSString stringWithFormat:@"%@-%lu", input, (unsigned long)x]]];
    }
    return [result copy];
}

- (nullable id)part1 {
    NSUInteger result = 0;
    NSArray<NSArray<NSNumber *> *> *matrix = [self knotHashMatrix:self.input];
    for (NSArray<NSNumber *> *arr in matrix) {
        for (NSNumber *number in arr) {
            result += [self countOneBits:number.integerValue];
        }
    }
    return @(result);
}

- (BOOL)bitTestValue:(NSUInteger)x bitPos:(NSUInteger)n {
    NSUInteger mask = 1;
    while (n--) {
        mask <<= 1;
    }
    return x & mask;
}

- (BOOL)bitTestMatrix:(NSArray<NSArray<NSNumber *> *> *)matrix row:(NSUInteger)row col:(NSUInteger)col {
    return [self bitTestValue:matrix[row][col / 8].integerValue bitPos:7 - (col % 8)];
}

- (NSInteger)linearIndexRow:(NSInteger)row col:(NSInteger)col {
    return row * 128 + col;
}

- (nullable id)part2 {
    NSArray<NSArray<NSNumber *> *> *matrix = [self knotHashMatrix:self.input];
    UFStructure *uf = [[UFStructure alloc] init];
    for (NSUInteger row = 0; row < 128; row++) {
        for (NSUInteger col = 0; col < 128; col++) {
            if (![self bitTestMatrix:matrix row:row col:col]) {
                continue;
            }
            for (NSArray<NSNumber *> *offset in @[@[@0, @0], @[@-1, @0], @[@1, @0], @[@0, @-1], @[@0, @1]]) {
                NSInteger neighRow = row + offset[0].integerValue;
                NSInteger neighCol = col + offset[1].integerValue;
                if (!(0 <= neighRow && neighRow < 128 &&
                      0 <= neighCol && neighCol < 128 &&
                      [self bitTestMatrix:matrix row:neighRow col:neighCol])) {
                    continue;
                }
                [uf union:[self linearIndexRow:row col:col] with:[self linearIndexRow:neighRow col:neighCol]];
            }
        }
    }
    return @([uf distinctSetCount]);
}

@end

NS_ASSUME_NONNULL_END
