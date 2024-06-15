#import "A17D01.h"

NS_ASSUME_NONNULL_BEGIN

@implementation A17D01

- (NSArray<NSNumber*>*)data {
    NSMutableArray* rv = [[NSMutableArray alloc] init];
    [self.input enumerateSubstringsInRange:NSMakeRange(0, self.input.length-1) options:NSStringEnumerationByComposedCharacterSequences usingBlock:^(NSString* substring, NSRange substringRange, NSRange enclosingRange, BOOL* stop) {
        [rv addObject:@(substring.integerValue)];
    }];
    return [rv copy];
}

- (nullable id)solveWithData:(NSArray<NSNumber*>*) data offset:(NSUInteger)offset {
    NSInteger __block sum = 0;
    [data enumerateObjectsUsingBlock:^(NSNumber* number, NSUInteger idx, BOOL* stop) {
        if ([number isEqualToNumber:data[(idx + offset)%data.count]]) {
            sum += number.integerValue;
        }
    }];
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
