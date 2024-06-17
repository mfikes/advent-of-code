#import "A17D13.h"

NS_ASSUME_NONNULL_BEGIN

@interface A17D13 ()

@property (nonatomic, copy) NSArray<NSArray<NSNumber *> *> *data;

@end

@implementation A17D13 {
    NSArray<NSArray<NSNumber *> *> *_data;
}

- (NSArray<NSArray<NSNumber *> *> *)data {
    if (!_data) {
        NSMutableArray<NSArray<NSNumber *> *> *tmp = [[NSMutableArray alloc] init];
        [self.input enumerateLinesUsingBlock:^(NSString *line, BOOL *stop) {
            NSArray<NSString *> *components = [line componentsSeparatedByString:@": "];
            [tmp addObject:@[@(components[0].integerValue), @(components[1].integerValue)]];
        }];
        _data = [tmp copy];
    }
    return _data;
}

- (BOOL)isCaughtDepth:(NSInteger)depth range:(NSInteger)range {
    return !(depth % (2 * (range - 1)));
}

- (nullable id)part1 {
    NSInteger sum = 0;
    for (NSArray<NSNumber *> *arr in self.data) {
        NSInteger depth = arr[0].integerValue;
        NSInteger range = arr[1].integerValue;
        if ([self isCaughtDepth:depth range:range]) {
            sum += depth * range;
        }
    }
    return @(sum);
}

- (nullable id)part2 {
    NSInteger delay = 0;
    for (;;) {
        BOOL caught = NO;
        for (NSArray<NSNumber *> *arr in self.data) {
            NSInteger depth = arr[0].integerValue;
            NSInteger range = arr[1].integerValue;
            if ([self isCaughtDepth:(depth + delay) range:range]) {
                caught = YES;
                break;
            }
        }
        if (!caught) {
            return @(delay);
        }
        delay++;
    }
}

@end

NS_ASSUME_NONNULL_END
