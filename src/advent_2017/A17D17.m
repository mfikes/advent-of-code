#import "A17D17.h"

NS_ASSUME_NONNULL_BEGIN

@interface A17D17 ()

@property (nonatomic, assign, readonly) NSInteger data;

@end

@implementation A17D17

- (NSArray *)spinStep:(NSInteger)step currentPos:(NSInteger)currentPos value:(NSInteger)value buffer:(NSArray *)buffer {
    NSInteger newPos = ((currentPos + step) % value) + 1;
    
    NSMutableArray *result = [[NSMutableArray alloc] init];
    [result addObjectsFromArray:[buffer subarrayWithRange:NSMakeRange(0, newPos)]];
    [result addObject:@(value)];
    [result addObjectsFromArray:[buffer subarrayWithRange:NSMakeRange(newPos, buffer.count - newPos)]];
    return @[@(newPos), result];
}

- (NSInteger)data {
    return 345;
}

- (nullable id)part1 {
    NSInteger currentPos = 0;
    NSArray *buffer = @[@0];
    for (NSUInteger idx = 0; idx < 2017; idx++) {
        NSArray *result = [self spinStep:self.data currentPos:currentPos value:idx + 1 buffer:buffer];
        currentPos = ((NSNumber *)result[0]).integerValue;
        buffer = result[1];
    }
    return buffer[currentPos + 1];
}

- (NSArray *)spinPrimeStep:(NSInteger)step currentPos:(NSInteger)currentPos value:(NSInteger)value afterZero:(NSInteger)afterZero {
    NSInteger newPos = ((currentPos + step) % value) + 1;
    return @[@(newPos), @(newPos == 1 ? value : afterZero)];
}

- (nullable id)part2 {
    NSInteger currentPos = 0;
    NSInteger afterZero = -1;
    for (NSUInteger idx = 0; idx < 50000000; idx++) {
        NSInteger value = idx + 1;
        currentPos = ((currentPos + self.data) % value) + 1;
        afterZero = (currentPos == 1 ? value : afterZero);
    }
    return @(afterZero);
}

@end

NS_ASSUME_NONNULL_END
