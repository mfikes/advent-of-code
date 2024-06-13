#import "A17D06.h"

NS_ASSUME_NONNULL_BEGIN

@implementation A17D06

- (NSArray<NSNumber*>*)data
{
    NSMutableArray<NSNumber*>* rv = [[NSMutableArray alloc] init];
    for (NSString* component in [self.input componentsSeparatedByString:@"\t"]) {
        [rv addObject:@([component intValue])];
    }
    return [rv copy];
}

- (NSArray<NSNumber*>*)redistribute:(NSArray<NSNumber*>*)banks
{
    NSUInteger count = banks.count;
    NSInteger maxValue = [[banks valueForKeyPath:@"@max.self"] integerValue];
    NSInteger maxIndex = [banks indexOfObject:@(maxValue)];
    
    NSMutableArray<NSNumber*>* result = [banks mutableCopy];
    result[maxIndex] = @0;
    
    for (NSInteger i = 0; i < maxValue; i++) {
        NSInteger targetIndex = (maxIndex + 1 + i) % count;
        result[targetIndex] = @(result[targetIndex].integerValue + 1);
    }
    
    return [result copy];
}

- (NSArray<NSNumber*>*)solve:(NSArray<NSNumber*>*)banks
{
    NSMutableDictionary<NSString*, NSNumber*>* lastSeen = [[NSMutableDictionary alloc] init];
    NSUInteger steps = 0;
    
    while (true) {
        NSString* banksKey = [banks componentsJoinedByString:@","];
        NSNumber* s = lastSeen[banksKey];
        if (s) {
            return @[@(steps), s];
        }
        lastSeen[banksKey] = @(steps);
        banks = [self redistribute:banks];
        steps++;
    }
}

- (nullable id)part1
{
    return [self solve:[self data]][0];
}

- (nullable id)part2
{
    NSArray<NSNumber*>* arr = [self solve:[self data]];
    return @(arr[0].integerValue - arr[1].integerValue);
}

@end

NS_ASSUME_NONNULL_END
