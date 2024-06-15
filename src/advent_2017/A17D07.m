#import "A17D07.h"

NS_ASSUME_NONNULL_BEGIN

@interface Item : NSObject

@property (nonatomic, readonly, copy) NSString* name;
@property (nonatomic, readonly, assign) NSUInteger weight;
@property (nonatomic, readonly, copy) NSArray<NSString*>* held;

- (instancetype)init NS_UNAVAILABLE;

- (instancetype)initWithName:(NSString*)name weight:(NSUInteger)weight held:(NSArray<NSString*>*)held NS_DESIGNATED_INITIALIZER;

@end

@implementation Item

- (instancetype)initWithName:(NSString*)name weight:(NSUInteger)weight held:(NSArray<NSString*>*)held
{
    if ((self = [super init])) {
        _name = [name copy];
        _weight = weight;
        _held = [held copy];
    }
    return self;
}

- (NSString*)description {
    return [NSString stringWithFormat:@"Name: %@, weight: %lu, held: %@", _name, _weight, _held];
}

@end

@implementation A17D07 {
    NSArray<Item*>* _data;
    NSDictionary<NSString*, Item*>* _index;
}

- (NSArray<Item*>*)data {
    if (!_data) {
        NSMutableArray<Item*>* result = [[NSMutableArray alloc] init];
        NSArray<NSString*>* emptyHeld = [[NSArray alloc] init];
        [self.input enumerateLinesUsingBlock:^(NSString* line, BOOL* stop) {
            NSArray<NSString*>* parts = [line componentsSeparatedByString:@" -> "];
            NSArray<NSString*>* nameAndWeight = [parts[0] componentsSeparatedByString:@" ("];
            [result addObject:[[Item alloc] initWithName:nameAndWeight[0]
                                                  weight:nameAndWeight[1].integerValue
                                                    held:parts.count == 2 ? [parts[1] componentsSeparatedByString:@", "] : emptyHeld]];
        }];
        _data = [result copy];
    }
    return _data;
}

- (nullable id)part1
{
    NSMutableSet<NSString*>* names = [[NSMutableSet alloc] init];
    NSMutableSet<NSString*>* held = [[NSMutableSet alloc] init];
    
    [[self data] enumerateObjectsUsingBlock:^(Item* item, NSUInteger idx, BOOL* stop) {
        [names addObject:item.name];
        [held addObjectsFromArray:item.held];
    }];
    
    [names minusSet:held];
    
    return [names anyObject];
}

- (Item*)lookupByName:(NSString*)name
{
    if (!_index) {
        NSMutableDictionary<NSString*, Item*>* tmp = [[NSMutableDictionary alloc] init];
        [[self data] enumerateObjectsUsingBlock:^(Item* item, NSUInteger idx, BOOL* stop) {
            tmp[item.name] = item;
        }];
        _index = [tmp copy];
    }
    return _index[name];
}

- (NSUInteger)weight:(NSString*)name
{
    Item* item = [self lookupByName:name];
    NSUInteger __block result = item.weight;
    [item.held enumerateObjectsUsingBlock:^(NSString* held, NSUInteger idx, BOOL* stop) {
        result += [self weight:held];
    }];
    return result;
}

- (nullable id)part2
{
    NSNumber* __block result;
    [[self data] enumerateObjectsUsingBlock:^(Item* item, NSUInteger idx, BOOL* stop) {
        if (item.held.count) {
            NSMutableArray<NSNumber*>* weights = [[NSMutableArray alloc] initWithCapacity:item.held.count];
            for (NSString* held in item.held) {
                [weights addObject:@([self weight:held])];
            }
            if ([[NSSet alloc] initWithArray:weights].count > 1) {
                NSMutableDictionary<NSNumber*, NSNumber*>* weightFrequencies = [[NSMutableDictionary alloc] init];
                for (NSNumber* weight in weights) {
                    weightFrequencies[weight] = @(weightFrequencies[weight].integerValue + 1);
                }
                NSNumber* __block uniqueWeight;
                [weightFrequencies enumerateKeysAndObjectsUsingBlock:^(NSNumber* weight, NSNumber *  count, BOOL * stop) {
                    if (count.integerValue == 1) {
                        uniqueWeight = weight;
                        *stop = YES;
                    }
                }];
                result = @([self lookupByName:item.held[[weights indexOfObject:uniqueWeight]]].weight +
                           (((NSNumber*)[weights valueForKeyPath:@"@min.self"]).integerValue -
                            ((NSNumber*)[weights valueForKeyPath:@"@max.self"]).integerValue));
                *stop = YES;
            }
        }
    }];
    return result;
}

@end

NS_ASSUME_NONNULL_END
