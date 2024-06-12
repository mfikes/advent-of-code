#import "A17D03.h"

NS_ASSUME_NONNULL_BEGIN

@interface Location : NSObject<NSCopying>

@property (nonatomic, assign, readonly) int x;

@property (nonatomic, assign, readonly) int y;

@property (nonatomic, readonly) int distance;

- (instancetype)init NS_UNAVAILABLE;

- (instancetype)initWithX:(int)x y:(int)y NS_DESIGNATED_INITIALIZER;

@end

@implementation Location

@dynamic distance;

- (instancetype)initWithX:(int)x y:(int)y
{
    if ((self = [super init])) {
        _x = x;
        _y = y;
    }
    return self;
}

- (BOOL)isEqual:(id)object
{
    if (self == object) return YES;
    if ([self class] != [object class]) return NO;
    
    Location* otherLocation = (Location*)object;
    
    return _x == otherLocation.x && _y == otherLocation.y;
}

- (NSUInteger)hash
{
    return 71993 * (_x + 13752) + 933199 * _y;
}

- (Location*)add:(Location*)other
{
    return [[Location alloc] initWithX:_x + other.x y:_y + other.y];
}

- (NSArray<Location*>*)addLocations:(NSArray<Location*>*)others
{
    NSMutableArray<Location*>* rv = [[NSMutableArray alloc] initWithCapacity:others.count];
    for (Location* other in others) {
        [rv addObject:[self add:other]];
    }
    return [rv copy];
}

- (int)distance
{
    return abs(_x) + abs(_y);
}

- (NSString*)description
{
    return [NSString stringWithFormat:@"[%d %d]", _x, _y];
}

- (id)copyWithZone:(nullable NSZone *)zone {
    return self;
}

@end

@implementation A17D03

- (int)data
{
    return 368078;
}

- (NSArray<Location*>*)candidateLocations:(Location*)location
{
    return [location addLocations:@[
        [[Location alloc] initWithX:0 y:-1],
        [[Location alloc] initWithX:-1 y:0],
        [[Location alloc] initWithX:0 y:1],
        [[Location alloc] initWithX:1 y:0]
    ]];
}

- (Location*)nthSpiral:(int)n
{
    Location* location = [[Location alloc] initWithX:1 y:0];
    
    NSMutableSet<Location*>* usedLocations = [[NSMutableSet<Location*> alloc] init];
    [usedLocations addObject:[[Location alloc] initWithX:0 y:0]];
    [usedLocations addObject:[[Location alloc] initWithX:1 y:0]];
    
    for (int i=0; i<n; i++) {
        NSArray<Location*>* candidateLocations = [self candidateLocations:location];
        NSUInteger idx = 0;
        while (![usedLocations containsObject:candidateLocations[idx]]) {
            idx++;
            idx %= candidateLocations.count;
        }
        while ([usedLocations containsObject:candidateLocations[idx]]) {
            idx++;
            idx %= candidateLocations.count;
        }
        location = candidateLocations[idx];
        [usedLocations addObject:location];
    }
    
    return location;
}

- (Location*)locationForSquare:(int)square
{
    return [self nthSpiral:square - 2];
}

- (nullable id)part1
{
    return @([self locationForSquare:[self data]].distance);
}

- (NSArray<Location*>*)adjacentLocations:(Location*)location
{
    return [location addLocations:@[
        [[Location alloc] initWithX:-1 y:1],
        [[Location alloc] initWithX:0 y:1],
        [[Location alloc] initWithX:1 y:1],
        [[Location alloc] initWithX:-1 y:0],
        [[Location alloc] initWithX:1 y:0],
        [[Location alloc] initWithX:-1 y:-1],
        [[Location alloc] initWithX:0 y:-1],
        [[Location alloc] initWithX:1 y:-1]
    ]];
}

- (nullable id)part2
{
    NSMutableDictionary<Location*, NSNumber*>* acc = [[NSMutableDictionary alloc] init];
    [acc setObject:@1 forKey:[[Location alloc] initWithX:0 y:0]];
    
    int n = 0;
    for (;;) {
        Location* location = [self nthSpiral:n++];
        int sum = 0;
        for (Location* adjacentLocation in [self adjacentLocations:location]) {
            sum += acc[adjacentLocation].intValue;
        }
        if ([self data] < sum) {
            return @(sum);
        } else {
            acc[location] = @(sum);
        }
    }
    return nil;
}

@end

NS_ASSUME_NONNULL_END
