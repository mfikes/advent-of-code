#import "A17D11.h"

NS_ASSUME_NONNULL_BEGIN

typedef NS_ENUM(NSUInteger, AD17D11Dir) {
    AD17D11DirN,
    AD17D11DirNE,
    AD17D11DirE,
    AD17D11DirSE,
    AD17D11DirS,
    AD17D11DirSW,
    AD17D11DirW,
    AD17D11DirNW
};

NSString* NSStringFromAD17D11Dir(AD17D11Dir dir) {
    switch (dir) {
        case AD17D11DirN:
            return @"N";
        case AD17D11DirNE:
            return @"NE";
        case AD17D11DirE:
            return @"E";
        case AD17D11DirSE:
            return @"SE";
        case AD17D11DirS:
            return @"S";
        case AD17D11DirSW:
            return @"SW";
        case AD17D11DirW:
            return @"W";
        case AD17D11DirNW:
            return @"NW";
        default:
            return @"Unknown";
    }
}

@interface NSValue (AD17D11Dir)

+ (instancetype)valueWithAD17D11Dir:(AD17D11Dir)dir;
- (AD17D11Dir)AD17D11DirValue;

@end

@implementation NSValue (AD17D11Dir)

+ (instancetype)valueWithAD17D11Dir:(AD17D11Dir)dir {
    return [NSValue value:&dir withObjCType:@encode(AD17D11Dir)];
}

- (AD17D11Dir)AD17D11DirValue {
    AD17D11Dir dir;
    [self getValue:&dir];
    return dir;
}

- (NSString *)description {
    return NSStringFromAD17D11Dir([self AD17D11DirValue]);
}

@end

@interface AD17D11Location : NSObject<NSCopying>

@property (nonatomic, assign, readonly) int x;

@property (nonatomic, assign, readonly) int y;

@property (nonatomic, assign, readonly) int z;

@property (nonatomic, readonly) int distance;

- (instancetype)init NS_UNAVAILABLE;

- (instancetype)initWithX:(int)x y:(int)y z:(int)z NS_DESIGNATED_INITIALIZER;

@end

@implementation AD17D11Location

@dynamic distance;

- (instancetype)initWithX:(int)x y:(int)y z:(int)z
{
    if ((self = [super init])) {
        _x = x;
        _y = y;
        _z = z;
    }
    return self;
}

- (BOOL)isEqual:(id)object
{
    if (self == object) return YES;
    if ([self class] != [object class]) return NO;
    
    AD17D11Location* otherLocation = (AD17D11Location*)object;
    
    return _x == otherLocation.x && _y == otherLocation.y && _z == otherLocation.z;
}

- (NSUInteger)hash
{
    return 71993 * (_x + 13752) + 933199 * _y + 3212 * _z;
}

- (AD17D11Location*)add:(AD17D11Location*)other
{
    return [[AD17D11Location alloc] initWithX:_x + other.x y:_y + other.y z:_z + other.z];
}

- (AD17D11Location*)move:(AD17D11Dir)dir
{
    switch (dir) {
        case AD17D11DirN:
            return [self add:[[AD17D11Location alloc] initWithX:0 y:1 z:-1]];
        case AD17D11DirNE:
            return [self add:[[AD17D11Location alloc] initWithX:1 y:0 z:-1]];
        case AD17D11DirSE:
            return [self add:[[AD17D11Location alloc] initWithX:1 y:-1 z:0]];
        case AD17D11DirS:
            return [self add:[[AD17D11Location alloc] initWithX:0 y:-1 z:1]];
        case AD17D11DirSW:
            return [self add:[[AD17D11Location alloc] initWithX:-1 y:0 z:1]];
        case AD17D11DirNW:
            return [self add:[[AD17D11Location alloc] initWithX:-1 y:1 z:0]];
        default:
            return nil;
    }
}

- (int)distance
{
    return (abs(_x) + abs(_y) + abs(_z))/2;
}

- (NSString*)description
{
    return [NSString stringWithFormat:@"[%d %d %d]", _x, _y, _z];
}

- (id)copyWithZone:(nullable NSZone *)zone {
    return self;
}

@end

@implementation A17D11 {
    NSArray<NSValue*>* _data;
}

- (NSArray<NSValue*>*) data {
    if (_data == nil) {
        NSMutableArray<NSValue*>* tmp = [[NSMutableArray alloc] init];
        NSString* inputWithoutNewline = [self.input substringToIndex:[self.input length] - 1];
        for (NSString* dir in [inputWithoutNewline componentsSeparatedByString:@","]) {
            //NSLog(@"%@", dir);
            if ([dir isEqualToString:@"n"]) {
                [tmp addObject:[NSValue valueWithAD17D11Dir:AD17D11DirN]];
            } else if ([dir isEqualToString:@"ne"]) {
                [tmp addObject:[NSValue valueWithAD17D11Dir:AD17D11DirNE]];
            } else if ([dir isEqualToString:@"e"]) {
                [tmp addObject:[NSValue valueWithAD17D11Dir:AD17D11DirE]];
            } else if ([dir isEqualToString:@"se"]) {
                [tmp addObject:[NSValue valueWithAD17D11Dir:AD17D11DirSE]];
            } else if ([dir isEqualToString:@"s"]) {
                [tmp addObject:[NSValue valueWithAD17D11Dir:AD17D11DirS]];
            } else if ([dir isEqualToString:@"sw"]) {
                [tmp addObject:[NSValue valueWithAD17D11Dir:AD17D11DirSW]];
            } else if ([dir isEqualToString:@"w"]) {
                [tmp addObject:[NSValue valueWithAD17D11Dir:AD17D11DirW]];
            } else if ([dir isEqualToString:@"nw"]) {
                [tmp addObject:[NSValue valueWithAD17D11Dir:AD17D11DirNW]];
            } else {
                NSLog(@"Unexpected dir: [%@]", dir);
            }
        }
        _data = [tmp copy];
    }
    return _data;
}

- (nullable id)part1
{
    AD17D11Location* loc = [[AD17D11Location alloc] initWithX:0 y:0 z:0];
    for (NSValue* dir in [self data]) {
        loc = [loc move:[dir AD17D11DirValue]];
    }
    return @(loc.distance);
}

- (nullable id)part2
{
    NSUInteger maxDistance = 0;
    AD17D11Location* loc = [[AD17D11Location alloc] initWithX:0 y:0 z:0];
    for (NSValue* dir in [self data]) {
        loc = [loc move:[dir AD17D11DirValue]];
        maxDistance = MAX(maxDistance, loc.distance);
    }
    return @(maxDistance);
}

@end

NS_ASSUME_NONNULL_END
