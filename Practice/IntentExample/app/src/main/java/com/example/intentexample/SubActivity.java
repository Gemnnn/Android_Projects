package com.example.intentexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {

    private Button btn_subMove;
    private TextView tv_sub;

    private TextView tv_random;

    TextView random_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        tv_sub = findViewById(R.id.tv_sub);
        tv_random = findViewById(R.id.tv_random);


        Intent intent = getIntent();
//        String str = intent.getStringExtra("str");
        String str = intent.getStringExtra("str");
        int num = intent.getIntExtra("num", 0);

        tv_sub.setText(str);
        tv_random.setText(String.valueOf(num));

        btn_subMove = findViewById(R.id.btn_subMove);
        btn_subMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}