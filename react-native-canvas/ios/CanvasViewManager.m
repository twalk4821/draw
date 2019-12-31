//
//  CanvasViewManager.m
//  react-native-canvas
//
//  Created by Tyler Walker on 12/26/19.
//


#import <React/RCTViewManager.h>
#import <React/RCTUIManager.h>
#import "CanvasView.m"
#import <React/RCTLog.h>

@interface CanvasViewManager : RCTViewManager
+(CanvasView*)canvasView;
@end

@implementation CanvasViewManager

RCT_EXPORT_MODULE(CanvasView)

+ (BOOL)requiresMainQueueSetup
{
    return YES;
}

- (CanvasView *)view
{
    return [[CanvasView alloc] initWithFrame:CGRectMake(0, 0, 200, 200)];
}

RCT_EXPORT_METHOD(addDrop:(nonnull NSNumber *)reactTag x: (float)x y: (float)y)
{
    NSLog(@"add drop");
}
RCT_EXPORT_METHOD(addDrop1:(nonnull NSNumber *)reactTag x: (float)x y: (float)y)
{
    NSLog(@"add drop");
}
RCT_EXPORT_METHOD(addDrop2:(nonnull NSNumber *)reactTag x: (float)x y: (float)y)
{
    NSLog(@"add drop");
}
RCT_EXPORT_METHOD(addDrop3:(nonnull NSNumber *)reactTag data: (id)data)
{
    NSNumber *x = [data objectForKey:@"x"];
    NSNumber *y = [data objectForKey:@"y"];
    [self runCanvas:reactTag block:^(CanvasView *canvas) {
        [((CanvasView*)[self view]) createDrop:[x intValue] y:[y intValue]];
                                [self.bridge.uiManager synchronouslyUpdateViewOnUIThread:reactTag viewName:@"CanvasView" props:@{}];
    }];
    
    [self.bridge.uiManager setNeedsLayout];
    [[self view] reactSetFrame:CGRectMake(0, 0, 200, 200)];
    [[self view] setFrame:CGRectMake(0, 0, 200, 200)];
    [[self view] setBounds:CGRectMake(0, 0, 200, 200)];
    [[self view] invalidateIntrinsicContentSize];
    UIView *sup = [[self view] superview];
    [[self view] removeFromSuperview];
    [sup addSubview:[self view]];
    NSLog(@"finished method");

    




}

- (void)runCanvas:(nonnull NSNumber *)reactTag block:(void (^)(CanvasView *canvas))block {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, CanvasView *> *viewRegistry){

         CanvasView *view = viewRegistry[reactTag];
         if (!view || ![view isKindOfClass:[CanvasView class]]) {
             RCTLogError(@"Cannot find RNSketchCanvas with tag #%@", reactTag);
             return;
         }

         block(view);
     }];
}
@end
