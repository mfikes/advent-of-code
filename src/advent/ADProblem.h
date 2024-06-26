#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface ADProblem : NSObject

@property (nonatomic, copy, readonly, nullable) NSString *inputPath;
@property (nonatomic, copy, readonly) NSString *input;

- (nullable instancetype)init NS_UNAVAILABLE;
- (nullable instancetype)initWithInputPath:(nullable NSString *)inputPath NS_DESIGNATED_INITIALIZER;

- (nullable id)part1;
- (nullable id)part2;

- (void)solve;

@end

NS_ASSUME_NONNULL_END
