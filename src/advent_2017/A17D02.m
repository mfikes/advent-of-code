#import "A17D02.h"

NS_ASSUME_NONNULL_BEGIN

@implementation A17D02

- (NSArray<NSArray<NSNumber*>*>*)data
{
    NSMutableArray<NSArray<NSNumber*>*>* rv = [[NSMutableArray alloc] init];
    [self.input enumerateLinesUsingBlock:^(NSString* line, BOOL* stop) {
        NSArray<NSString*>* strings = [line componentsSeparatedByCharactersInSet:[NSCharacterSet whitespaceCharacterSet]];
        NSMutableArray<NSNumber*>* numbers = [[NSMutableArray alloc] initWithCapacity:strings.count];
        for (NSString* string in strings) {
            [numbers addObject:@([string intValue])];
        }
        [rv addObject:[numbers copy]];
    }];
    return [rv copy];
}

- (id)solve:(int (^)(NSArray<NSNumber*>*))block {
    int sum = 0;
    for (NSArray<NSNumber*>* arr in self.data) {
        sum += block(arr);
    }
    return @(sum);
}

- (nullable id)part1 {
    return [self solve:^int(NSArray<NSNumber*>* arr) {
        NSNumber *maxNumber = [arr valueForKeyPath:@"@max.self"];
        NSNumber *minNumber = [arr valueForKeyPath:@"@min.self"];
        return maxNumber.intValue - minNumber.intValue;
    }];
}

- (nullable id)part2 {
    return [self solve:^int(NSArray<NSNumber*>* arr) {
        for (NSNumber* x in arr) {
            for (NSNumber* y in arr) {
                if (![y isEqualToNumber:x] && !(y.intValue % x.intValue)) {
                    return y.intValue / x.intValue;
                }
            }
        }
        return 0;
    }];
}

@end

NS_ASSUME_NONNULL_END
