package com.example.jfan.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by jfan on 11/1/16.
 */

public class DoodleView extends View {
    private Paint _paintDoodle = new Paint();
    private Path _path = new Path();
    private boolean clearCanvas = false;
    private Canvas _canvas;
    private Bitmap _bitmap;
    private boolean randomize = false;
    private int curColor = 0;
    private int curSize = 1;
    private int curOpacity = 100;

    public DoodleView(Context context) {
        super(context);
        init(null, 0);
    }

    public DoodleView(Context context, AttributeSet attr) {
        super(context, attr);
        init(attr, 0);
    }

    public DoodleView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr);
        init(attr, defStyle);
    }

    private void init(AttributeSet attr, int defStyle) {
        _paintDoodle.setColor(Color.BLACK);
        _paintDoodle.setAntiAlias(true);
        _paintDoodle.setStyle(Paint.Style.STROKE);
        _paintDoodle.setStrokeJoin(Paint.Join.ROUND);
        _paintDoodle.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        _bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        _canvas = new Canvas(_bitmap);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (clearCanvas) {
            _bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            _canvas = new Canvas(_bitmap);
            clearCanvas = false;
            _path.reset();
        } else {
            canvas.drawBitmap(_bitmap, 0, 0, _paintDoodle);
            canvas.drawPath(_path, _paintDoodle);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float touchX = motionEvent.getX();
        float touchY = motionEvent.getY();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                _path.moveTo(touchX, touchY);

                if (randomize) {
                    Random rand = new Random();

                    _paintDoodle.setColor(Color.argb(1, rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                    _paintDoodle.setStrokeWidth(rand.nextInt(50));
                    _paintDoodle.setAlpha(rand.nextInt(100));
                }

                break;
            case MotionEvent.ACTION_MOVE:
                _path.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                _path.lineTo(touchX, touchY);
                _canvas.drawPath(_path, _paintDoodle);
                _path.reset();

                break;
        }

        invalidate();
        return true;
    }

    public void changeColor(int color) {
        invalidate();

        _paintDoodle.setColor(color);
        curColor = color;
    }

    public void changeSize(float size) {
        invalidate();

        _paintDoodle.setStrokeWidth(size);
        curSize = (int) size;
    }

    public void changeOpacity(int opacity) {
        invalidate();
        _paintDoodle.setAlpha(opacity);
        curOpacity = opacity;
    }

    public void clear() {
        invalidate();

        clearCanvas = true;
    }

    public void toggleRandomize(boolean random) {
        if (random) {
            curColor = _paintDoodle.getColor();
            curSize = (int) _paintDoodle.getStrokeWidth();
            curOpacity = _paintDoodle.getAlpha();
        } else {
            _paintDoodle.setColor(curColor);
            _paintDoodle.setStrokeWidth(curSize);
            _paintDoodle.setAlpha(curOpacity);
        }

        randomize = random;
    }
}
