#import "A17D12.h"

NS_ASSUME_NONNULL_BEGIN

@interface A17D12Node : NSObject

@property (nonatomic, readonly, copy) NSNumber* identifier;

@property (nonatomic, readonly, copy) NSArray<NSNumber*>* related;

- (instancetype)init NS_UNAVAILABLE;

- (instancetype)initWithIdentifier:(NSNumber*)identifier related:(NSArray<NSNumber*>*)related NS_DESIGNATED_INITIALIZER;

@end

@implementation A17D12Node

- (instancetype)initWithIdentifier:(NSNumber*)identifier related:(NSArray<NSNumber*>*)related
{
    if ((self = [super init])) {
        _identifier = [identifier copy];
        _related = [related copy];
    }
    return self;
}

- (NSString*)description
{
    return [NSString stringWithFormat:@"<%@: [%@ %@]>", [self className], _identifier, _related];
}

@end

@interface A17D12 ()

@property (nonatomic, readonly, copy) NSArray<A17D12Node*>* data;

@end

@implementation A17D12 {
    NSArray<A17D12Node*>* _data;
}

- (NSArray<A17D12Node*>*)data
{
    if (!_data) {
        NSMutableArray<A17D12Node*>* tmp = [[NSMutableArray alloc] init];
        
        [self.input enumerateLinesUsingBlock:^(NSString* line, BOOL* stop) {
            NSArray<NSString*>* components = [line componentsSeparatedByString:@" <-> "];
            NSArray<NSString*>* relatedComponents = [components[1] componentsSeparatedByString:@","];
            
            NSMutableArray<NSNumber*>* related = [[NSMutableArray alloc] init];
            for (NSString* relatedComponent in relatedComponents) {
                [related addObject:@(relatedComponent.integerValue)];
            }
            
            [tmp addObject:[[A17D12Node alloc] initWithIdentifier:@(components[0].integerValue) related:related]];
        }];
        _data = [tmp copy];
    }
    return _data;
}

- (nullable id)part1
{
    NSLog(@"%@", self.data);
    return nil;
}

- (nullable id)part2
{
    return nil;
}

@end

NS_ASSUME_NONNULL_END
