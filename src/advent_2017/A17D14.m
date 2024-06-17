#import "A17D14.h"
#import "A17D10.h"

NS_ASSUME_NONNULL_BEGIN

@implementation A17D14

- (NSString*)input {
    return @"ljoxqyyw";
}

-(NSUInteger)countOneBits:(NSInteger) number {
    NSUInteger count = 0;
    while (number) {
        count += number & 1;
        number >>= 1;
    }
    return count;
}

- (NSArray<NSArray<NSNumber *> *> *)knotHashMatrix:(NSString*)input {
    NSMutableArray<NSArray<NSNumber *> *> *result = [[NSMutableArray alloc] init];
    A17D10 *a17d10 = [[A17D10 alloc] initWithInputPath:nil];
    for (NSUInteger x=0; x<128; x++) {
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

- (nullable id)part2 {
    return nil;
}

@end

NS_ASSUME_NONNULL_END
