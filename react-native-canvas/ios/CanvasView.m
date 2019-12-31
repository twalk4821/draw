//
//  CanvasView.m
//  react-native-canvas
//
//  Created by Tyler Walker on 12/26/19.
//

#import <Foundation/Foundation.h>
#import <React/RCTLog.h>
#import <React/RCTEventDispatcher.h>
#import <React/RCTView.h>
#import <React/UIView+React.h>
@interface CanvasView : UIView
+(RCTEventDispatcher*) eventDispatcher;
@end

@implementation CanvasView

NSMutableArray *drops;
int num;
RCTEventDispatcher *eventDispatcher;

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    drops = [[NSMutableArray alloc] init];
    [self setContentMode:UIViewContentModeRedraw];
    return self;
}

- (instancetype)initWithEventDispatcher:(RCTEventDispatcher *)eventDispatcher
{
    self = [super init];
    if (self) {
        eventDispatcher = eventDispatcher;

        self.backgroundColor = [UIColor clearColor];
        self.clearsContextBeforeDrawing = YES;
    }
    return self;
}

- (void)drawRect:(CGRect)rect
{
    RCTLog(@"drawRect()");
    CGContextRef context = UIGraphicsGetCurrentContext();
    CGContextFlush(context);
    CGRect bounds = self.bounds;
    CGContextClearRect(context, bounds);
    CGRect rectangle = CGRectMake(0, 0, bounds.size.width, bounds.size.height);
    CGContextSetRGBFillColor(context, 1.0, 1.0, 1.0, 1.0);
    CGContextFillRect(context, rectangle);
    CGContextSetStrokeColorWithColor(context, [UIColor blueColor].CGColor);
    CGContextSetLineWidth(context, 2);
    CGContextBeginPath(context);
    CGContextMoveToPoint(context, 0, 0);
    NSUInteger dropsCount = drops.count;
    for (NSUInteger i = 0; i < dropsCount; i++) {
        CGContextClearRect(context, bounds);
        CGContextAddLineToPoint(context, [drops[i] intValue], 25);
    }
    CGContextStrokePath(context);
    NSLog(@"finished method?");
}

- (void)createDrop:(int)x y:(int)y
{
    NSString *pretty = [NSString stringWithFormat:@"add drop: %d,%d", x, y];
    RCTLog(pretty);
    
    [drops addObject:[NSNumber numberWithInt:x]];
    dispatch_async(dispatch_get_main_queue(), ^{
                [self setNeedsDisplay];
    });
    [self reactSetFrame:[self frame]];
    [self didUpdateReactSubviews];
    [self reloadInputViews];
    UIAccessibilityPostNotification(UIAccessibilityLayoutChangedNotification, nil);
    UIAccessibilityPostNotification(UIAccessibilityScreenChangedNotification, nil);
}
@end

//CGRectMake(0, 0, 50, 50)
