package com.reactlibrary;

import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.SimpleViewManager;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CanvasView extends SimpleViewManager<CanvasViewInternal> {
    @Nonnull
    @Override
    public String getName() {
        return "CanvasView";
    }

    @Nonnull
    @Override
    protected CanvasViewInternal createViewInstance(@Nonnull ThemedReactContext reactContext) {
        return new CanvasViewInternal(reactContext);
    }

    @Override
    public Map<String,Integer> getCommandsMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("tap", 0);
        return map;
    }

    @Override
    public void receiveCommand(CanvasViewInternal view, int commandType, @Nullable ReadableArray args) {
        switch (commandType) {
            case 0: {
                view.onTap();
                return;
            }
            default:
                throw new IllegalArgumentException(String.format(
                        "Unsupported command %d received by %s.",
                        commandType,
                        getClass().getSimpleName()));
        }
    }
}
