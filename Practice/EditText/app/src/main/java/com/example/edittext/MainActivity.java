package com.example.edittext;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText et_id;
    Button btn_test;
    TextView tv_id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_id = findViewById(R.id.et_id);
        btn_test = findViewById(R.id.btn_test);
        tv_id = findViewById(R.id.tv_id);

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_id.setText(et_id.getText());
            }
        });
    }
}