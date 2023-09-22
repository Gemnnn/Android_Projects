package com.example.counterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txt_count;
    Button btn_click;

    private int counter = 0;

    private final int interval = 1000;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_click = findViewById(R.id.btn_click);
        txt_count = findViewById(R.id.txt_count);

        handler = new Handler(Looper.getMainLooper());

        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                       txt_count.setText(String.valueOf(counter));
                       counter++;
                       handler.postDelayed(this, 1000);
                    }
                });
            }
        });
    }


}