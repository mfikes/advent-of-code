#import "A17D01.h"

NS_ASSUME_NONNULL_BEGIN

@implementation A17D01

- (NSArray<NSNumber*>*)data {
    NSString* input = self.input;
    NSMutableArray* rv = [[NSMutableArray alloc] initWithCapacity:input.length - 1];
    for (NSUInteger i=0; i<input.length - 1; i++) {
        [rv addObject:@([input characterAtIndex:i] - '0')];
    }
    return [rv copy];
}

- (nullable id)solveWithData:(NSArray<NSNumber*>*) data offset:(NSUInteger)offset {
    int sum = 0;
    for (NSUInteger i=0; i<data.count; i++) {
        if ([data[i] isEqualToNumber:data[(i + offset)%data.count]]) {
            sum += data[i].intValue;
        }
    }
    return @(sum);
}

- (nullable id)part1
{
    NSArray<NSNumber*>* data = [self data];
    return [self solveWithData:data offset:1];
}

- (nullable id)part2
{
    NSArray<NSNumber*>* data = [self data];
    return [self solveWithData:data offset:data.count/2];
}

@end

NS_ASSUME_NONNULL_END
