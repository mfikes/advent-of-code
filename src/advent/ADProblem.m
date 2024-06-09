#import "ADProblem.h"

NS_ASSUME_NONNULL_BEGIN

@implementation ADProblem

- (nullable instancetype)initWithInputPath:(nullable NSString*)inputPath
{
    if ((self = [super init])) {
        _inputPath = [inputPath copy];
    }
    return self;
}

- (nullable NSString*)input
{
    NSString* fullInputPath = _inputPath ? [NSString stringWithFormat:@"resources/%@/input", _inputPath] : nil;
    return [NSString stringWithContentsOfFile:fullInputPath encoding:NSUTF8StringEncoding error:nil];
}

- (nullable NSArray<NSString *> *)inputLines
{
    NSMutableArray<NSString *> *lines = [[self.input componentsSeparatedByCharactersInSet:[NSCharacterSet newlineCharacterSet]] mutableCopy];
    if (lines.count > 0 && [lines.lastObject isEqualToString:@""]) {
        [lines removeLastObject];
    }
    return [lines copy];
}

- (nullable id)part1
{
    return nil;
}

- (nullable id)part2
{
    return nil;
}

- (void)solve
{
    NSDate *startTime;
    NSDate *endTime;
    NSTimeInterval elapsedTime;

    startTime = [NSDate date];
    id part1Result = [self part1];
    endTime = [NSDate date];
    elapsedTime = [endTime timeIntervalSinceDate:startTime];
    NSLog(@"%@ part1: %@ (%.3f ms)", [self class], part1Result, 1000*elapsedTime);

    startTime = [NSDate date];
    id part2Result = [self part2];
    endTime = [NSDate date];
    elapsedTime = [endTime timeIntervalSinceDate:startTime];
    NSLog(@"%@ part2: %@ (%.3f ms)", [self class], part2Result, 1000*elapsedTime);
}

@end

NS_ASSUME_NONNULL_END
