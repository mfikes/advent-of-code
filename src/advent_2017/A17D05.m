#import "A17D05.h"

NS_ASSUME_NONNULL_BEGIN

@implementation A17D05

- (NSArray<NSNumber *> *)data {
    NSMutableArray<NSNumber *> *rv = [[NSMutableArray alloc] init];
    [self.input enumerateLinesUsingBlock:^(NSString *line, BOOL *stop) {
        [rv addObject:@([line intValue])];
    }];
    return [rv copy];
}

- (NSNumber *)solve:(NSNumber *(^)(NSNumber *))transform {
    NSUInteger counter = 0;
    NSUInteger ndx = 0;
    NSMutableArray<NSNumber *> *maze = [[self data] mutableCopy];
    for (;;) {
        if (0 <= ndx && ndx < maze.count) {
            NSUInteger offset = maze[ndx].integerValue;
            maze[ndx] = transform(maze[ndx]);
            ndx += offset;
        } else {
            return @(counter);
        }
        counter++;
    }
}

- (nullable id)part1 {
    return [self solve:^NSNumber* (NSNumber *x) {
        return @(x.intValue + 1);
    }];
}

- (nullable id)part2 {
    return [self solve:^NSNumber* (NSNumber *x) {
        return @(x.intValue >= 3 ? x.intValue - 1 : x.intValue + 1);
    }];
}

@end

NS_ASSUME_NONNULL_END
