#import <Foundation/Foundation.h>
#import "A17D01.h"
#import "A17D02.h"
#import "A17D03.h"

int main(int argc, const char * argv[]) {
    @autoreleasepool {
        
        NSArray<ADProblem*>* problems = @[
            //[[A17D01 alloc] initWithInputPath:@"advent_2017/day_01"],
            //[[A17D02 alloc] initWithInputPath:@"advent_2017/day_02"],
            [[A17D03 alloc] initWithInputPath:nil],
        ];
        
        [problems makeObjectsPerformSelector:@selector(solve)];
    }
    return 0;
}
