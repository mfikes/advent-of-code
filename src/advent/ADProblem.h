#import <Foundation/Foundation.h>

@interface ADProblem : NSObject

@property (nonatomic, copy, readonly, nullable) NSString* inputPath;

@property (nonatomic, copy, readonly, nullable) NSString* input;

- (nullable instancetype)init NS_UNAVAILABLE;

- (nullable instancetype)initWithInputPath:(nullable NSString *)inputPath NS_DESIGNATED_INITIALIZER;

- (nullable id)part1;
- (nullable id)part2;

- (void)solve;

@end
