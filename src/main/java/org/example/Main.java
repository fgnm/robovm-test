package org.example;

import org.robovm.apple.coreanimation.CADisplayLink;
import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSRunLoop;
import org.robovm.apple.foundation.NSRunLoopMode;
import org.robovm.apple.uikit.*;
import org.robovm.objc.Selector;
import org.robovm.objc.annotation.Method;

public class Main extends UIApplicationDelegateAdapter {

    @Override
    public boolean didFinishLaunching(UIApplication application, UIApplicationLaunchOptions launchOptions) {
        System.out.println("Hello iOS!");
        UIViewController viewController = new UIViewController();

        UIWindow uiWindow = new UIWindow(UIScreen.getMainScreen().getBounds());
        uiWindow.makeKeyAndVisible();
        application.getDelegate().setWindow(uiWindow);
        uiWindow.setRootViewController(viewController);

        UIView view = new UIView() {
            @Override
            public void draw(CGRect rect) {
                System.out.println("UIView draw");
                super.draw(rect);
            }
        };
        view.setBackgroundColor(UIColor.blue());
        viewController.setView(view);

        CADisplayLink timer = UIScreen.getMainScreen().getDisplayLink(this, Selector.register("redraw"));
        timer.addToRunLoop(NSRunLoop.getMain(), NSRunLoopMode.Default);
        return true;
    }

    float count;

    @Method
    private void redraw() {
        count += 0.01f;
        if(count > 0.2F){
            long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            System.out.println(used);
            count = 0;
        }
    }

    public static void main(String[] args) {
        try (NSAutoreleasePool pool = new NSAutoreleasePool()) {
            UIApplication.main(args, null, Main.class);
        }
    }
}
