#import "ADProblem.h"

@implementation ADProblem

- (nullable instancetype)initWithInputPath:(nullable NSString*)inputPath
{
    if ((self = [super init])) {
        _inputPath = [inputPath copy];
    }
    return self;
}

- (nullable NSString*)input {
    NSString* fullInputPath = _inputPath ? [NSString stringWithFormat:@"resources/%@/input", _inputPath] : nil;
    return [NSString stringWithContentsOfFile:fullInputPath encoding:NSUTF8StringEncoding error:nil];
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
    NSLog(@"%@ part1: %@", [self class], [self part1]);
    NSLog(@"%@ part2: %@", [self class], [self part2]);
}

@end
