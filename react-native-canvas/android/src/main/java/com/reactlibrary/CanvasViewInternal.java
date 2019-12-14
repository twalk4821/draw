package com.reactlibrary;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.facebook.react.uimanager.ThemedReactContext;

public class CanvasViewInternal extends View {
    private ThemedReactContext _context;
    private Paint paint = new Paint();
    private Paint basePaint = new Paint();

    private float size = 200F;
    private float baseSize = 200F;
    private Canvas _canvas;

    public CanvasViewInternal(ThemedReactContext context) {
        super(context);
        _context = context;
        paint.setStrokeWidth(10F);
        paint.setColor(Color.RED);

        basePaint.setStrokeWidth(10F);
        basePaint.setColor(Color.WHITE);
    }

    public void onTap() {
        size = size - 1F;
        draw(_canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        _canvas = canvas;
        canvas.drawRect(0F, 0F, baseSize, baseSize, basePaint);
        canvas.drawRect(0F, 0F, size, size, paint);
    }

    @Override
    public int getMinimumWidth() {
        return 200;
    }

    @Override
    public int getMinimumHeight() {
        return 200;
    }
}
