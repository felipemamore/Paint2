package com.example.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.PublicKey;
import java.util.ArrayList;


public class SimplePaint extends View {

    ArrayList <Layer> layers;
    Paint mPaint;
    float startX, startY, endX, endY;
    enum ShapeType {FREE, LINE, CIRCLE, RECTANGLE }
    ShapeType currentShape = ShapeType.FREE;

    public SimplePaint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        layers = new ArrayList<>();
        layers.add(new Layer(initialSetupPaint()));

    }

    public Paint initialSetupPaint(){
        Paint lpaint = new Paint();
        lpaint.setStrokeWidth(5f);
        lpaint.setColor(Color.BLACK);
        lpaint.setStyle(Paint.Style.STROKE);
        return lpaint;
    }
    public void changeColor(int color){
        mPaint = new Paint(getCurrentLayer().paint);
        mPaint.setColor(color);
        layers.add(new Layer(mPaint));

    }

    public void changeStrokeWidth(int width){
        layers.add(new Layer(getCurrentLayer().paint));
        getCurrentLayer().paint.setStrokeWidth(width);
    }

    public void setShape(ShapeType shapeType){
        this.currentShape = shapeType;
    }

    public void clearCanvas(){
        layers.clear();
        layers.add(new Layer(initialSetupPaint()));
        invalidate();
    }


    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        for (Layer layer:layers
             ) {
            canvas.drawPath(layer.path, layer.paint);
            
        }

        if(currentShape != ShapeType.FREE){
            Paint tempPaint = new Paint(getCurrentLayer().paint);
            tempPaint.setStyle(Paint.Style.STROKE);
            switch (currentShape){
                case LINE:
                    canvas.drawLine(startX, startY, endX, endY, tempPaint);
                    break;
                case CIRCLE:
                    float radius = (float) Math.hypot(endX - startX, endY - startY);
                    canvas.drawCircle(startX, startY, radius, tempPaint);
                    break;
                case RECTANGLE:
                    canvas.drawRect(startX, startY, endX, endY, tempPaint);
                    break;

            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        event.getAction();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = x;
                startY = y;
                if (currentShape == ShapeType.FREE) {
                    getCurrentLayer().path.moveTo(x, y);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (currentShape == ShapeType.FREE) {
                    getCurrentLayer().path.lineTo(x, y);
                } else {
                    endX = x;
                    endY = y;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (currentShape != ShapeType.FREE) {
                    Layer newLayer = new Layer(new Paint(getCurrentLayer().paint));
                    switch (currentShape) {
                        case LINE:
                            newLayer.path.moveTo(startX, startY);
                            newLayer.path.lineTo(endX, endY);
                            break;
                        case CIRCLE:
                            float radius = (float) Math.hypot(endX - startX, endY - startY);
                            newLayer.path.addCircle(startX, startY, radius, Path.Direction.CW);
                            break;
                        case RECTANGLE:
                            RectF rect = new RectF(startX, startY, endX, endY);
                            newLayer.path.addRect(rect, Path.Direction.CW);
                            break;
                    }
                    layers.add(newLayer);
                }
                break;
        }


        invalidate();

        return super.onTouchEvent(event);

    }
    public Layer getCurrentLayer(){

        return layers.get(layers.size()-1);
    }
    static class Layer {
        Path path;
        Paint paint;

        Layer(Paint paint) {
            this.paint = new Paint(paint);
            this.path = new Path();
        }
    }
}
