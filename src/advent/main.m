#import <Foundation/Foundation.h>
#import "A17D01.h"

int main(int argc, const char * argv[]) {
    @autoreleasepool {
        A17D01* a17d01 = [[A17D01 alloc] initWithInputPath:@"advent_2017/day_01"];
        [a17d01 solve];
    }
    return 0;
}
