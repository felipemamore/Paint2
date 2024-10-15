package com.example.paint;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class Layer {
    Paint paint;
    Path path;

    public Layer(Paint paint){
        this.path=new Path();
        this.paint = new Paint();
        this.paint.set(paint);


    }

    public Layer() {
        this.paint = new Paint();
        this.path = new Path();
    }
}
