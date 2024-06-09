#import "A17D02.h"

@implementation A17D02

- (nonnull NSArray<NSArray<NSNumber*>*>*)data
{
    NSMutableArray<NSArray<NSNumber*>*>* rv = [[NSMutableArray alloc] init];
    for (NSString* line in self.inputLines) {
        NSArray<NSString*>* strings = [line componentsSeparatedByCharactersInSet:[NSCharacterSet whitespaceCharacterSet]];
        NSMutableArray<NSNumber*>* numbers = [[NSMutableArray alloc] initWithCapacity:strings.count];
        for (NSString* string in strings) {
            [numbers addObject:@([string intValue])];
        }
        [rv addObject:[numbers copy]];
    }
    return [rv copy];
}

- (id)part1
{    
    int sum = 0;
    for (NSArray<NSNumber*>* arr in self.data) {
        NSNumber *maxNumber = [arr valueForKeyPath:@"@max.self"];
        NSNumber *minNumber = [arr valueForKeyPath:@"@min.self"];
        sum += maxNumber.intValue - minNumber.intValue;
    }
    return @(sum);
}

@end
