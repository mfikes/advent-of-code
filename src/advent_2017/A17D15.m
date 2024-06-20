#import "A17D15.h"

NS_ASSUME_NONNULL_BEGIN

static const NSInteger INPUT_A = 512;
static const NSInteger INPUT_B = 191;

@interface A17D15 ()

@property (nonatomic, copy) NSInteger (^genA)(NSInteger);
@property (nonatomic, copy) NSInteger (^genB)(NSInteger);

@end

@implementation A17D15

+ (NSInteger (^)(NSInteger))makeGenMultiplier:(NSInteger)multiplier {
    return ^(NSInteger a) {
        return (a * multiplier) % 2147483647;
    };
}

- (nullable instancetype)initWithInputPath:(nullable NSString *)inputPath {
    self = [super initWithInputPath:inputPath];
    if (self) {
        _genA = [A17D15 makeGenMultiplier:16807];
        _genB = [A17D15 makeGenMultiplier:48271];
    }
    return self;
}

+ (NSInteger (^)(NSInteger))makeGenBaseGen:(NSInteger (^)(NSInteger))baseGen multiples:(NSInteger)multiples {
    return ^(NSInteger x) {
        NSInteger result = baseGen(x);
        while (result % multiples != 0) {
            result = baseGen(result);
        }
        return result;
    };
}

- (NSInteger)solveMaxCount:(NSUInteger)maxCount genA:(NSInteger (^)(NSInteger))genA genB:(NSInteger (^)(NSInteger))genB {
    NSUInteger count = 0;
    NSInteger sum = 0;
    NSInteger a = INPUT_A;
    NSInteger b = INPUT_B;
    for (;;) {
        if (count == maxCount) {
            return sum;
        } else {
            a = genA(a);
            b = genB(b);
            if ((a & 0xFFFF) == (b & 0xFFFF)) {
                sum++;
            }
            count++;
        }
    }
}

- (nullable id)part1 {
    return @([self solveMaxCount:40e6 genA:self.genA genB:self.genB]);
}

- (nullable id)part2 {
    return @([self solveMaxCount:5e6
                            genA:[A17D15 makeGenBaseGen:_genA multiples:4]
                            genB:[A17D15 makeGenBaseGen:_genB multiples:8]]);
}

@end

NS_ASSUME_NONNULL_END
