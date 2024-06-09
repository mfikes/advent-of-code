#import <Foundation/Foundation.h>
#import "A17D01.h"
#import "A17D02.h"

int main(int argc, const char * argv[]) {
    @autoreleasepool {
        A17D01* a17d01 = [[A17D01 alloc] initWithInputPath:@"advent_2017/day_01"];
        [a17d01 solve];
        
        A17D02* a17d02 = [[A17D02 alloc] initWithInputPath:@"advent_2017/day_02"];
        [a17d02 solve];
    }
    return 0;
}
