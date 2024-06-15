#import "A17D09.h"

NS_ASSUME_NONNULL_BEGIN

typedef NS_ENUM(NSUInteger, A17D09State) {
    A17D09StateCancel,
    A17D09StateGarbage,
    A17D09StateGroup,
};

@implementation A17D09

- (NSDictionary<NSString*, NSNumber*>*)solution
{
    A17D09State __block state = A17D09StateGroup;
    NSUInteger __block level = 1;
    NSUInteger __block score = 0;
    NSUInteger __block count = 0;
    
    [self.input enumerateSubstringsInRange:NSMakeRange(0, self.input.length)
                                   options:NSStringEnumerationByComposedCharacterSequences
                                usingBlock:^(NSString* substring, NSRange substringRange, NSRange enclosingRange, BOOL* stop) {
        unichar ch = [substring characterAtIndex:0];
        switch (state) {
            case A17D09StateCancel: {
                state = A17D09StateGarbage;
                break;
            }
            case A17D09StateGarbage: {
                switch (ch) {
                    case '>': {
                        state = A17D09StateGroup;
                        break;
                    }
                    case '!': {
                        state = A17D09StateCancel;
                        break;
                    }
                    default:
                        count++;
                }
                break;
            }
            case A17D09StateGroup: {
                switch (ch) {
                    case '<': {
                        state = A17D09StateGarbage;
                        break;
                    }
                    case '{': {
                        level++;
                        break;
                    }
                    case '}': {
                        level--;
                        score += level;
                        break;
                    }
                }
                break;
            }
        }
    }];
    return @{@"score": @(score), @"count": @(count)};
}

- (nullable id)part1
{
    return [self solution][@"score"];
}

- (nullable id)part2
{
    return [self solution][@"count"];
}

@end

NS_ASSUME_NONNULL_END
