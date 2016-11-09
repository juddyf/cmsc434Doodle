package com.example.jfan.myapplication;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    int curColor;
    DoodleView d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View colorPreview = (View) findViewById(R.id.colorPreview);
        curColor = ((ColorDrawable) colorPreview.getBackground()).getColor();

        d = (DoodleView) findViewById(R.id.doodleView);
    }

    public void onClickBrushSet(View v) {
        BrushSettingsView bsv = (BrushSettingsView) findViewById(R.id.brushSettings);
        Button brushButton = (Button) findViewById(R.id.brushSet);

        if (bsv.getVisibility() == View.VISIBLE) {
            bsv.setVisibility(View.GONE);
            brushButton.setText("Brush");
        } else {
            bsv.setVisibility(View.VISIBLE);
            brushButton.setText("Close");

            SeekBar hueSeekbar = (SeekBar) findViewById(R.id.hueSeekbar);
            hueSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    int red = Color.red(curColor);
                    int green = Color.green(curColor);
                    int blue = Color.blue(curColor);

                    float[] hsv = new float[3];
                    Color.RGBToHSV(red, green, blue, hsv);

                    hsv[0] = (float) i;

                    int color = Color.HSVToColor(hsv);

                    d.changeColor(color);

                    View colorPrev = findViewById(R.id.colorPreview);
                    colorPrev.setBackgroundColor(color);

                    curColor = color;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            SeekBar satSeekbar = (SeekBar) findViewById(R.id.saturationSeekbar);
            satSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    int red = Color.red(curColor);
                    int green = Color.green(curColor);
                    int blue = Color.blue(curColor);

                    float[] hsv = new float[3];
                    Color.RGBToHSV(red, green, blue, hsv);

                    hsv[1] = (float) i / 100;

                    int color = Color.HSVToColor(hsv);

                    d.changeColor(color);

                    View colorPrev = findViewById(R.id.colorPreview);
                    colorPrev.setBackgroundColor(color);
                    curColor = color;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            SeekBar brightnessSeekbar = (SeekBar) findViewById(R.id.brightessSeekbar);
            brightnessSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    int red = Color.red(curColor);
                    int green = Color.green(curColor);
                    int blue = Color.blue(curColor);

                    float[] hsv = new float[3];
                    Color.RGBToHSV(red, green, blue, hsv);

                    hsv[2] = (float) i / 100;

                    int color = Color.HSVToColor(hsv);

                    d.changeColor(color);

                    View colorPrev = findViewById(R.id.colorPreview);
                    colorPrev.setBackgroundColor(color);
                    curColor = color;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

        SeekBar sizeSeekbar = (SeekBar) findViewById(R.id.sizeSeekbar);
        sizeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                d.changeSize(i);

                View sizePrev =  findViewById(R.id.sizePreview);
                ViewGroup.LayoutParams p = sizePrev.getLayoutParams();
                p.height = i;
                p.width = i;
                sizePrev.setLayoutParams(p);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar opacitySeekbar = (SeekBar) findViewById(R.id.opacitySeekbar);
        opacitySeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                d.changeOpacity(i);

                View opacPrev = findViewById(R.id.colorPreview);
                opacPrev.getBackground().setAlpha(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void onClickClearButton(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Clear drawing?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                d.clear();
                dialog.cancel();
            }
        })
        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }

    public void onClickRandomize(View v) {
        ToggleButton toggle = (ToggleButton) findViewById(R.id.randomToggle);

        d.toggleRandomize(toggle.isChecked());
    }
}
