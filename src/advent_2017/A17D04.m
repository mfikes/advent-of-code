#import "A17D04.h"

NS_ASSUME_NONNULL_BEGIN

@implementation A17D04

- (BOOL)areAllElementsDistinct:(NSArray *)array
{
    NSSet *uniqueElements = [NSSet setWithArray:array];
    return [uniqueElements count] == [array count];
}

- (BOOL)isValidPassphrase:(NSString*)passphrase
            withTransform:(NSString* (^)(NSString*))transformBlock {
    NSMutableArray<NSString*>* components = [[NSMutableArray alloc] init];
    for (NSString* component in [passphrase componentsSeparatedByString:@" "]) {
        [components addObject:transformBlock ? transformBlock(component) : component];
    }
    return [self areAllElementsDistinct:components];
}

- (NSUInteger)solveWithTransform:(NSString* (^)(NSString*))transformBlock
{
    NSPredicate *predicate = [NSPredicate predicateWithBlock:^BOOL(NSString *line, NSDictionary *bindings) {
        return [self isValidPassphrase:line withTransform:transformBlock];
    }];
    
    return [self.inputLines filteredArrayUsingPredicate:predicate].count;
}

- (nullable id)part1
{
    return @([self solveWithTransform:^NSString* (NSString* input) {
        return input;
    }]);
}

- (nullable id)part2
{
    return @([self solveWithTransform:^NSString* (NSString* input) {
        NSMutableArray *characters = [NSMutableArray arrayWithCapacity:[input length]];
        for (int i = 0; i < [input length]; i++) {
            [characters addObject:[NSString stringWithFormat:@"%c", [input characterAtIndex:i]]];
        }
        NSArray *sortedCharacters = [characters sortedArrayUsingSelector:@selector(compare:)];
        return [sortedCharacters componentsJoinedByString:@""];
    }]);
}

@end

NS_ASSUME_NONNULL_END
