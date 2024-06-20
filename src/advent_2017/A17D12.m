#import "A17D12.h"

NS_ASSUME_NONNULL_BEGIN

@interface A17D12Node : NSObject

@property (nonatomic, readonly, assign) NSUInteger identifier;
@property (nonatomic, readonly, copy) NSArray<NSNumber *> *related;

- (instancetype)init NS_UNAVAILABLE;
- (instancetype)initWithIdentifier:(NSUInteger)identifier related:(NSArray<NSNumber *> *)related NS_DESIGNATED_INITIALIZER;

@end

@implementation A17D12Node

- (instancetype)initWithIdentifier:(NSUInteger)identifier related:(NSArray<NSNumber *> *)related {
    self = [super init];
    if (self) {
        _identifier = identifier;
        _related = [related copy];
    }
    return self;
}

- (NSString *)description {
    return [NSString stringWithFormat:@"<%@: id=%lu related=(%@)>", [self className], (unsigned long)_identifier, [_related componentsJoinedByString:@", "]];
}

@end

@interface UFValue : NSObject

@property (nonatomic, assign) NSUInteger parent;
@property (nonatomic, assign) NSUInteger rank;

@end

@implementation UFValue

- (NSString *)description {
    return [NSString stringWithFormat:@"%lu %lu", (unsigned long)_parent, (unsigned long)_rank];
}

@end

@interface UFStructure ()

@property (nonatomic) NSMutableDictionary<NSNumber *, UFValue *> *uf;

@end

@implementation UFStructure

- (instancetype)init {
    self = [super init];
    if (self) {
        _uf = [[NSMutableDictionary alloc] init];
    }
    return self;
}

- (NSString *)description {
    return _uf.description;
}

- (NSUInteger)find:(NSUInteger)x {
    UFValue *v = _uf[@(x)];
    if (!v) {
        v = [[UFValue alloc] init];
        v.parent = x;
        _uf[@(x)] = v;
        return x;
    } else if (v.parent == x) {
        return x;
    } else {
        NSUInteger xRoot = [self find:v.parent];
        v.parent = xRoot;
        return xRoot;
    }
}

- (void)union:(NSUInteger)x with:(NSUInteger)y {
    NSUInteger xRoot = [self find:x];
    NSUInteger yRoot = [self find:y];
    UFValue *xValue = _uf[@(xRoot)];
    UFValue *yValue = _uf[@(yRoot)];
    if (xRoot != yRoot) {
        if (xValue.rank < yValue.rank) {
            xValue.parent = yRoot;
        } else {
            yValue.parent = xRoot;
            if (xRoot == yRoot) {
                xValue.rank++;
            }
        }
    }
}

- (NSSet<NSNumber *> *)elementsWith:(NSUInteger)x
{
    NSMutableSet<NSNumber *> *result = [[NSMutableSet alloc] init];
    NSUInteger xRoot = [self find:x];
    for (NSNumber *identifier in [_uf allKeys]) {
        if ([self find:identifier.integerValue] == xRoot) {
            [result addObject:identifier];
        }
    }
    return [result copy];
}

- (NSUInteger)distinctSetCount {
    NSMutableSet<NSNumber *> *set = [[NSMutableSet alloc] init];
    for (NSNumber *identifier in [_uf allKeys]) {
        [set addObject:@([self find:identifier.integerValue])];
    }
    return set.count;
}

@end

@interface A17D12 ()

@property (nonatomic, readonly, copy) NSArray<A17D12Node *> *data;
@property (nonatomic, readonly, copy) UFStructure *uf;

@end

@implementation A17D12 {
    NSArray<A17D12Node *> *_data;
    UFStructure *_uf;
}

- (NSArray<A17D12Node *> *)data {
    if (!_data) {
        NSMutableArray<A17D12Node *> *tmp = [[NSMutableArray alloc] init];
        
        [self.input enumerateLinesUsingBlock:^(NSString *line, BOOL *stop) {
            NSArray<NSString *> *components = [line componentsSeparatedByString:@" <-> "];
            NSArray<NSString *> *relatedComponents = [components[1] componentsSeparatedByString:@","];
            
            NSMutableArray<NSNumber *> *related = [[NSMutableArray alloc] init];
            for (NSString *relatedComponent in relatedComponents) {
                [related addObject:@(relatedComponent.integerValue)];
            }
            
            [tmp addObject:[[A17D12Node alloc] initWithIdentifier:components[0].integerValue related:related]];
        }];
        _data = [tmp copy];
    }
    return _data;
}

- (UFStructure *)uf {
    if (!_uf) {
        _uf = [[UFStructure alloc] init];
        for (A17D12Node *node in self.data) {
            for (NSNumber *related in node.related) {
                [_uf union:node.identifier with:related.integerValue];
            }
        }
    }
    return _uf;
}

- (nullable id)part1 {
    return @([self.uf elementsWith:0].count);
}

- (nullable id)part2 {
    return @([self.uf distinctSetCount]);
}

@end

NS_ASSUME_NONNULL_END
