package com.example.paint;


import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

public class MainActivity extends AppCompatActivity {
    SimplePaint simplePaint;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simplePaint = findViewById(R.id.simplePaint);
        button = findViewById(R.id.button);

        Button btnClear = findViewById(R.id.btn_clear);
        Button btnFree = findViewById(R.id.btn_shape_free);
        Button btnLine = findViewById(R.id.btn_shape_line);
        Button btnCircle = findViewById(R.id.btn_shape_circle);
        Button btnRect = findViewById(R.id.btn_shape_rect);



        btnFree.setOnClickListener(v -> simplePaint.setShape(SimplePaint.ShapeType.FREE));
        btnLine.setOnClickListener(v -> simplePaint.setShape(SimplePaint.ShapeType.LINE));
        btnCircle.setOnClickListener(v -> simplePaint.setShape(SimplePaint.ShapeType.CIRCLE));
        btnRect.setOnClickListener(v -> simplePaint.setShape(SimplePaint.ShapeType.RECTANGLE));

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showColorPicker();
            }

            private void showColorPicker() {
                new ColorPickerDialog.Builder(MainActivity.this)
                        .setTitle("ColorPicker Dialog")
                        .setPreferenceName("MyColorPickerDialog")
                        .setPositiveButton(getString(R.string.confirm),
                                new ColorEnvelopeListener() {
                                    @Override
                                    public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                        simplePaint.changeColor(envelope.getColor());
                                    }
                                })
                        .setNegativeButton(getString(R.string.cancel),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                        .attachAlphaSlideBar(true) // the default value is true.
                        .attachBrightnessSlideBar(true)  // the default value is true.
                        .setBottomSpace(12) // set a bottom space between the last slidebar and buttons.
                        .show();

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simplePaint.clearCanvas();
            }
        });



//
//    }

//        public void setLayoutColor (ColorEnvelope envelope){
//            simplePaint.changeColor(envelope.getColor());
//
//        }
    }
}