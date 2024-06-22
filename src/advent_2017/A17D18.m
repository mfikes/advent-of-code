#import "A17D18.h"

NS_ASSUME_NONNULL_BEGIN

typedef void (^A17D18Snd)(NSNumber *val);
typedef BOOL (^A17D18Rcv)(NSMutableDictionary<NSString *, NSNumber *> *regs, NSNumber *priorVal, NSString *reg);

@interface A17D18Processor : NSObject

@property (nonatomic, assign) NSUInteger ip;
@property (nonatomic, strong) NSMutableDictionary<NSString *, NSNumber *> *registers;
@property (nonatomic, copy) A17D18Snd snd;
@property (nonatomic, copy) A17D18Rcv rcv;

- (instancetype)init NS_UNAVAILABLE;
- (instancetype)initWithSnd:(A17D18Snd)snd Rcv:(A17D18Rcv)rcv NS_DESIGNATED_INITIALIZER;

@end

@implementation A17D18Processor

- (instancetype)initWithSnd:(A17D18Snd)snd Rcv:(A17D18Rcv)rcv {
    self = [super init];
    if (self) {
        _ip = 0;
        _registers = [[NSMutableDictionary alloc] init];
        _snd = snd;
        _rcv = rcv;
    }
    return self;
}

- (NSNumber *)evaluate:(id)arg {
    return [arg isKindOfClass:[NSString class]] ? _registers[arg] : (NSNumber *)arg;
}

- (void)step:(NSArray<NSArray *> *)program {
    NSArray *fetched = program[_ip];
    NSString *op = fetched[0];
    id arg1 = fetched[1];
    id arg2 = fetched.count > 2 ? fetched[2] : nil;
    
    _ip++;
    
    if ([op isEqualToString:@"snd"]) {
        _snd([self evaluate:arg1]);
    } else if ([op isEqualToString:@"rcv"]) {
        BOOL blocked = _rcv(_registers, [self evaluate:arg1], arg1);
        if (blocked) _ip--;
    } else if ([op isEqualToString:@"set"]) {
        _registers[arg1] = [self evaluate:arg2];
    } else if ([op isEqualToString:@"jgz"]) {
        if ([self evaluate:arg1].integerValue > 0) {
            _ip--;
            _ip += [self evaluate:arg2].integerValue;
        }
    } else {
        NSInteger lhs = [self evaluate:arg1].integerValue;
        NSInteger rhs = [self evaluate:arg2].integerValue;
        NSInteger result;
        if ([op isEqualToString:@"add"]) {
            result = lhs + rhs;
        } else if ([op isEqualToString:@"mul"]) {
            result = lhs * rhs;
        } else if ([op isEqualToString:@"mod"]) {
            result = lhs % rhs;
        } else {
            @throw [NSError errorWithDomain:@"A17D18Processor"
                                       code:1
                                   userInfo:@{NSLocalizedDescriptionKey: [NSString stringWithFormat:@"Unknown operation: %@", op]}];
        }
        _registers[arg1] = @(result);
    }
}

@end

@interface A17D18 ()

@property (nonatomic, copy, readonly) NSArray *data;

@end

@implementation A17D18 {
    NSArray *_data;
}

+ (id)regOrNum:(NSString *)v {
    NSNumberFormatter *formatter = [[NSNumberFormatter alloc] init];
    return [formatter numberFromString:v] ?: v;
}

- (NSArray *)data {
    if (!_data) {
        NSMutableArray *tmp = [[NSMutableArray alloc] init];
        [self.input enumerateLinesUsingBlock:^(NSString *line, BOOL *stop) {
            NSArray *tokens = [line componentsSeparatedByString:@" "];
            [tmp addObject:[[NSArray alloc] initWithObjects:tokens[0], [A17D18 regOrNum:tokens[1]],
                            tokens.count > 2 ? [A17D18 regOrNum:tokens[2]] : nil, nil]];
        }];
        _data = [tmp copy];
    }
    return _data;
}

- (nullable id)part1 {
    __block NSNumber *lastSnd = nil;
    __block BOOL done = NO;
    
    A17D18Processor *processor = [[A17D18Processor alloc] initWithSnd:^(NSNumber *val) {
        lastSnd = val;
    } Rcv:^(NSMutableDictionary<NSString *, NSNumber *> *regs, NSNumber *priorVal, NSString *reg) {
        if (priorVal.integerValue != 0) {
            regs[reg] = lastSnd;
            done = YES;
        }
        return NO;
    }];
    
    while (!done) {
        [processor step:self.data];
    }
    
    return lastSnd;
}

- (nullable id)part2 {
    NSMutableArray *queue0 = [[NSMutableArray alloc] init];
    NSMutableArray *queue1 = [[NSMutableArray alloc] init];
    __block BOOL needInput0 = NO;
    __block BOOL needInput1 = NO;
    __block NSUInteger sndCount1 = 0;
    
    A17D18Processor *processor0 = [[A17D18Processor alloc] initWithSnd:^(NSNumber *val) {
        [queue1 addObject:val];
    } Rcv:^(NSMutableDictionary<NSString *, NSNumber *> *regs, NSNumber *priorVal, NSString *reg) {
        if (queue0.count) {
            regs[reg] = queue0[0];
            [queue0 removeObjectAtIndex:0];
            needInput0 = NO;
        } else {
            needInput0 = YES;
        }
        return needInput0;
    }];
    
    A17D18Processor *processor1 = [[A17D18Processor alloc] initWithSnd:^(NSNumber *val) {
        [queue0 addObject:val];
        sndCount1++;
    } Rcv:^(NSMutableDictionary<NSString *, NSNumber *> *regs, NSNumber *priorVal, NSString *reg) {
        if (queue1.count) {
            regs[reg] = queue1[0];
            [queue1 removeObjectAtIndex:0];
            needInput1 = NO;
        } else {
            needInput1 = YES;
        }
        return needInput1;
    }];
    
    processor1.registers[@"p"] = @1;
    
    for (;;) {
        BOOL blocked0 = needInput0 && !queue0.count;
        BOOL blocked1 = needInput1 && !queue1.count;
        if (blocked0 && blocked1) {
            return @(sndCount1);
        }
        if (blocked0) {
            [processor1 step:self.data];
        } else {
            [processor0 step:self.data];
        }
    }
}

@end

NS_ASSUME_NONNULL_END
