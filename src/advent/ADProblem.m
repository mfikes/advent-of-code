#import "ADProblem.h"

NS_ASSUME_NONNULL_BEGIN

@implementation ADProblem {
    NSString *_input;
    NSArray<NSString *> *_inputLines;
}

- (nullable instancetype)initWithInputPath:(nullable NSString *)inputPath {
    self = [super init];
    if (self) {
        _inputPath = [inputPath copy];
        _input = nil;
        _inputLines = nil;
    }
    return self;
}

- (NSString *)input {
    if (!_input) {
        NSString *fullInputPath = _inputPath ? [NSString stringWithFormat:@"resources/%@/input", _inputPath] : nil;
        _input = [NSString stringWithContentsOfFile:fullInputPath encoding:NSUTF8StringEncoding error:nil];
        if (!_input) {
            _input = @"";
        }
    }
    return _input;
}

- (nullable id)part1 {
    return nil;
}

- (nullable id)part2 {
    return nil;
}

- (void)solve {
    NSDate *startTime = [NSDate date];
    id part1Result = [self part1];
    NSDate *endTime = [NSDate date];
    NSTimeInterval elapsedTime = [endTime timeIntervalSinceDate:startTime];
    NSLog(@"%@ part1: %@ (%.3f ms)", [self class], part1Result, 1000 * elapsedTime);

    startTime = [NSDate date];
    id part2Result = [self part2];
    endTime = [NSDate date];
    elapsedTime = [endTime timeIntervalSinceDate:startTime];
    NSLog(@"%@ part2: %@ (%.3f ms)", [self class], part2Result, 1000 * elapsedTime);
}

@end

NS_ASSUME_NONNULL_END
