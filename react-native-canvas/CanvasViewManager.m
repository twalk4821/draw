//
//  CanvasViewManager.m
//  react-native-canvas
//
//  Created by Tyler Walker on 12/26/19.
//


#import <React/RCTViewManager.h>

@interface CanvasViewManager : RCTViewManager
@end

@implementation CanvasViewManager

RCT_EXPORT_MODULE(CanvasView)

- (UILabel *)view
{
    NSString *str = @"Native iOS View";
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 50, 50)];
    label.text = str;
    return label;
}

@end
