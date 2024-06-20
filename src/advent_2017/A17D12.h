#import <Foundation/Foundation.h>
#import "ADProblem.h"

NS_ASSUME_NONNULL_BEGIN

@interface A17D12 : ADProblem

@end

@interface UFStructure : NSObject

- (NSUInteger)find:(NSUInteger)x;
- (void)union:(NSUInteger)x with:(NSUInteger)y;
- (NSUInteger)distinctSetCount;

@end

NS_ASSUME_NONNULL_END
