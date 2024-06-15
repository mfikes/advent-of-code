#import "A17D08.h"

NS_ASSUME_NONNULL_BEGIN

@interface Instruction : NSObject

@property (nonatomic, readonly, copy) NSString* target;
@property (nonatomic, readonly, copy) NSNumber* (^op)(NSNumber*);
@property (nonatomic, readonly, copy) NSString* lhs;
@property (nonatomic, readonly, copy) BOOL (^cmp)(NSNumber*);

- (instancetype)init NS_UNAVAILABLE;

- (instancetype)initWithTarget:(NSString*)target
                            op:(NSNumber* (^)(NSNumber*))op
                           lhs:(NSString*)lhs
                           cmp:(BOOL (^)(NSNumber*))cmp NS_DESIGNATED_INITIALIZER;
@end

@implementation Instruction

- (instancetype)initWithTarget:(NSString*)target
                            op:(NSNumber* (^)(NSNumber*))op
                           lhs:(NSString*)lhs
                           cmp:(BOOL (^)(NSNumber*))cmp
{
    if ((self = [super init])) {
        _target = [target copy];
        _op = [op copy];
        _lhs = [lhs copy];
        _cmp = [cmp copy];
    }
    return self;
}

@end

@implementation A17D08 {
    NSArray<Instruction*>* _data;
}

- (NSArray<Instruction*>*)data
{
    if (!_data) {
        NSMutableArray<Instruction*>* tmp = [[NSMutableArray alloc] init];
        
        [self.input enumerateLinesUsingBlock:^(NSString* line, BOOL *stop) {
            NSArray<NSString*>* components = [line componentsSeparatedByString:@" "];
            
            NSString* target = components[0];
            NSString* opStr = components[1];
            NSNumber* value = @(components[2].integerValue);
            NSString* lhs = components[4];
            NSString* cmpStr = components[5];
            NSNumber* rhs = @(components[6].integerValue);
            
            NSDictionary<NSString*, NSNumber* (^)(NSNumber*)>* opBlocks = @{
                @"inc": ^NSNumber* (NSNumber* registerValue) {
                    return @(registerValue.integerValue + value.integerValue);
                },
                @"dec": ^NSNumber* (NSNumber* registerValue) {
                    return @(registerValue.integerValue - value.integerValue);
                },
            };
            
            NSDictionary<NSString*, BOOL (^)(NSNumber*)>* comparisonBlocks = @{
                @"==": ^BOOL (NSNumber* lhs) {
                    return [lhs isEqualToNumber:rhs];
                },
                @"!=": ^BOOL (NSNumber* lhs) {
                    return ![lhs isEqualToNumber:rhs];
                },
                @"<": ^BOOL (NSNumber* lhs) {
                    return [lhs compare:rhs] == NSOrderedAscending;
                },
                @"<=": ^BOOL (NSNumber* lhs) {
                    return [lhs compare:rhs] != NSOrderedDescending;
                },
                @">": ^BOOL (NSNumber* lhs) {
                    return [lhs compare:rhs] == NSOrderedDescending;
                },
                @">=": ^BOOL (NSNumber* lhs) {
                    return [lhs compare:rhs] != NSOrderedAscending;
                }
            };
            
            Instruction* instruction = [[Instruction alloc] initWithTarget:target
                                                                        op:opBlocks[opStr]
                                                                       lhs:lhs
                                                                       cmp:comparisonBlocks[cmpStr]];
            [tmp addObject:instruction];
        }];
        _data = [tmp copy];
    }
    
    return _data;
}

- (NSDictionary<NSString*, NSNumber*>*)applyInstruction:(Instruction*)instruction toRegisters:(NSDictionary<NSString*, NSNumber*>*)registers
{
    NSMutableDictionary<NSString*, NSNumber*>* result = [registers mutableCopy];
    if (instruction.cmp(registers[instruction.lhs] ?: @0)) {
        result[instruction.target] = instruction.op(registers[instruction.target] ?: @0);
    }
    return [result copy];
}

- (nullable id)part1
{
    NSDictionary<NSString*, NSNumber*>* __block registers = @{};
    [[self data] enumerateObjectsUsingBlock:^(Instruction* instruction, NSUInteger idx, BOOL* stop) {
        registers = [self applyInstruction:instruction toRegisters:registers];
    }];
    return [registers.allValues valueForKeyPath:@"@max.self"];
}

- (nullable id)part2
{
    NSUInteger __block overallMax = 0;
    NSDictionary<NSString*, NSNumber*>* __block registers = @{};
    [[self data] enumerateObjectsUsingBlock:^(Instruction* instruction, NSUInteger idx, BOOL* stop) {
        registers = [self applyInstruction:instruction toRegisters:registers];
        overallMax = MAX(overallMax, ((NSNumber*)[registers.allValues valueForKeyPath:@"@max.self"]).integerValue);
    }];
    return @(overallMax);
}

@end

NS_ASSUME_NONNULL_END
