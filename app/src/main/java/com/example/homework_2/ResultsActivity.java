package com.example.homework_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    ArrayList<String> times;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        times =(ArrayList<String>) this.getIntent().getSerializableExtra("timeList");
        textView = (TextView) findViewById(R.id.textView);

        setText();
    }

    public void switchBack(View view) {
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }

    public void setText() {
        for (int i = 0; i < times.size(); i++) {
            textView.append((String.valueOf(i + 1)));
            textView.append(". ");
            textView.append(times.get(i));
            textView.append("\n");
        }
    }
}