#import <Foundation/Foundation.h>
#import "A17D01.h"
#import "A17D02.h"
#import "A17D03.h"
#import "A17D04.h"
#import "A17D05.h"
#import "A17D06.h"
#import "A17D07.h"
#import "A17D08.h"
#import "A17D09.h"
#import "A17D10.h"
#import "A17D11.h"
#import "A17D12.h"
#import "A17D13.h"
#import "A17D14.h"
#import "A17D15.h"
#import "A17D16.h"
#import "A17D17.h"
#import "A17D18.h"
#import "A17D19.h"
#import "A17D20.h"
#import "A17D20.h"
#import "A17D21.h"
#import "A17D22.h"
#import "A17D23.h"
#import "A17D24.h"
#import "A17D25.h"

int main(int argc, const char * argv[]) {
    @autoreleasepool {
        
        NSArray<ADProblem *> *problems = @[
            //[[A17D01 alloc] initWithInputPath:@"advent_2017/day_01"],
            //[[A17D02 alloc] initWithInputPath:@"advent_2017/day_02"],
            //[[A17D03 alloc] initWithInputPath:nil],
            //[[A17D04 alloc] initWithInputPath:@"advent_2017/day_04"],
            //[[A17D05 alloc] initWithInputPath:@"advent_2017/day_05"],
            //[[A17D06 alloc] initWithInputPath:@"advent_2017/day_06"],
            //[[A17D07 alloc] initWithInputPath:@"advent_2017/day_07"],
            //[[A17D08 alloc] initWithInputPath:@"advent_2017/day_08"],
            //[[A17D09 alloc] initWithInputPath:@"advent_2017/day_09"],
            //[[A17D10 alloc] initWithInputPath:nil],
            //[[A17D11 alloc] initWithInputPath:@"advent_2017/day_11"],
            //[[A17D12 alloc] initWithInputPath:@"advent_2017/day_12"],
            //[[A17D13 alloc] initWithInputPath:@"advent_2017/day_13"],
            //[[A17D14 alloc] initWithInputPath:nil],
            //[[A17D15 alloc] initWithInputPath:nil],
            //[[A17D16 alloc] initWithInputPath:@"advent_2017/day_16"],
            //[[A17D17 alloc] initWithInputPath:nil],
            [[A17D18 alloc] initWithInputPath:@"advent_2017/day_18"],
            //[[A17D19 alloc] initWithInputPath:@"advent_2017/day_19"],
            //[[A17D20 alloc] initWithInputPath:@"advent_2017/day_20"],
            //[[A17D21 alloc] initWithInputPath:@"advent_2017/day_21"],
            //[[A17D22 alloc] initWithInputPath:@"advent_2017/day_22"],
            //[[A17D23 alloc] initWithInputPath:@"advent_2017/day_23"],
            //[[A17D24 alloc] initWithInputPath:@"advent_2017/day_24"],
            //[[A17D25 alloc] initWithInputPath:@"advent_2017/day_25"],
        ];
        
        [problems makeObjectsPerformSelector:@selector(solve)];
    }
    return 0;
}
