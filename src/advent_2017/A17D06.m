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

- (NSDictionary<NSNumber*, NSNumber*>*)redistribute:(NSDictionary<NSNumber*, NSNumber*>*)banks
{
    NSNumber* maxValue = [[banks allValues] valueForKeyPath:@"@max.self"];
    NSMutableArray<NSNumber*>* keys = [[NSMutableArray alloc] init];
    [banks enumerateKeysAndObjectsUsingBlock:^(NSNumber* key, NSNumber* value, BOOL *stop) {
        if ([value isEqualToNumber:maxValue]) {
            [keys addObject:key];
        }
    }];
    NSNumber* maxIndex = [keys valueForKeyPath:@"@min.self"];
    NSMutableArray<NSNumber*>* targetIndexes = [[NSMutableArray alloc] init];
    for (NSUInteger i = 0; i<banks[maxIndex].intValue; i++) {
        [targetIndexes addObject:@((maxIndex.integerValue + 1 + i) % banks.count)];
    }
    NSMutableDictionary<NSNumber*, NSNumber*>* result = [banks mutableCopy];
    result[maxIndex] = @0;
    for (NSNumber* targetIndex in targetIndexes) {
        result[targetIndex] = @(result[targetIndex].integerValue + 1);
    }
    return [result copy];
}

- (NSArray<NSNumber*>*)solve:(NSArray<NSNumber*>*)banks
{
    NSMutableDictionary<NSNumber*, NSNumber*>* temp = [[NSMutableDictionary alloc] init];
    for (NSUInteger i=0; i<[banks count]; i++) {
        temp[@(i)] = banks[i];
    }
    NSDictionary<NSNumber*, NSNumber*>* banksMap = [temp copy];
    NSUInteger steps = 0;
    NSMutableDictionary<NSDictionary<NSNumber*, NSNumber*>*, NSNumber*>* lastSeen = [[NSMutableDictionary alloc] init];
    for (;;) {
        NSNumber* s = lastSeen[banksMap];
        if (s) {
            return @[@(steps), s];
        }
        lastSeen[banksMap] = @(steps);
        banksMap = [self redistribute:banksMap];
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
