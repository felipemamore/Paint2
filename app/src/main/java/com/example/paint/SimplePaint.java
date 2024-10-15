package com.example.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
    public SimplePaint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        layers = new ArrayList<Layer>();
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
        layers.add(new Layer(getCurrentLayer().paint));
        getCurrentLayer().paint.setColor(color);
    }

    public void changeStrokeWidth(int width){
        layers.add(new Layer(getCurrentLayer().paint));
        getCurrentLayer().paint.setStrokeWidth(width);
    }


    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        for (Layer cLayer:layers
             ) {
            canvas.drawPath(cLayer.path, cLayer.paint);
            
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        event.getAction();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                getCurrentLayer().path.moveTo(x,y);
                return true;
            case MotionEvent.ACTION_MOVE:
                getCurrentLayer().path.lineTo(x,y);

                break;
            case MotionEvent.ACTION_UP:
                break;
        }


        invalidate();

        return super.onTouchEvent(event);

    }
    public Layer getCurrentLayer(){
        return layers.get(layers.size()-1);
    }

}
