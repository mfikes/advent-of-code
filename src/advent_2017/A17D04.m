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
    [passphrase enumerateSubstringsInRange:NSMakeRange(0, passphrase.length)
                                   options:NSStringEnumerationByWords
                                usingBlock:^(NSString* substring, NSRange substringRange, NSRange enclosingRange, BOOL* stop) {
        [components addObject:transformBlock ? transformBlock(substring) : substring];
    }];
    return [self areAllElementsDistinct:components];
}

- (NSUInteger)solveWithTransform:(NSString* (^)(NSString*))transformBlock
{
    NSUInteger __block count = 0;
    [self.input enumerateLinesUsingBlock:^(NSString*line, BOOL* stop) {
        if ([self isValidPassphrase:line withTransform:transformBlock]) {
            count++;
        }
    }];
    return count;
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
        [input enumerateSubstringsInRange:NSMakeRange(0, input.length)
                                  options:NSStringEnumerationByComposedCharacterSequences
                               usingBlock:^(NSString* substring, NSRange substringRange, NSRange enclosingRange, BOOL *stop) {
            [characters addObject:substring];
        }];
        NSArray *sortedCharacters = [characters sortedArrayUsingSelector:@selector(compare:)];
        return [sortedCharacters componentsJoinedByString:@""];
    }]);
}

@end

NS_ASSUME_NONNULL_END
