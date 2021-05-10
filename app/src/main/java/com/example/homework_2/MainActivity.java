package com.example.homework_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private Chronometer timer;
    private boolean running = false;
    private long pauseOffset;
    private Button button;
    private ArrayList<String> timeList;
    private TextView tv;
    private int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeList = new ArrayList<String>();

        tv = (TextView) findViewById(R.id.textView2);

        button = (Button) findViewById(R.id.startButton);
        timer = (Chronometer) findViewById(R.id.chronometer);

        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer cArg) {
                long time = SystemClock.elapsedRealtime() - cArg.getBase();
                int h   = (int)(time /3600000);
                int m = (int)(time - h*3600000)/60000;
                int s= (int)(time - h*3600000- m*60000)/1000 ;
                String hh = h < 10 ? "0"+h: h+"";
                String mm = m < 10 ? "0"+m: m+"";
                String ss = s < 10 ? "0"+s: s+"";
                cArg.setText(hh+":"+mm+":"+ss);
            }
        });

//        timer.setBase(SystemClock.elapsedRealtime());

        updateButtons();
    }

    public void startTimer() {
        if (!running) {
            timer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            timer.start();
            running = true;
            updateButtons();
        }
        else {
            pauseTimer();
        }
    }

    public void start(View view) {
        startTimer();
    }

    public void pauseTimer() {
        if (running) {
            timer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - timer.getBase();
            running = false;
            updateButtons();
        }
    }

    public void resetTimer(View view) {
        timer.stop();
        timer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        running = false;
        updateButtons();
        timeList = new ArrayList<String>();

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            tv.setText("");
        }
    }

    private void updateButtons() {
        if (running) {
            button.setText("Stop");
        }
        else {
            button.setText("Start");
        }
    }

    public void switchActivities(View view) {
        Intent switchActivityIntent = new Intent(this, ResultsActivity.class);
        switchActivityIntent.putExtra("timeList", getTimeList());
        startActivity(switchActivityIntent);
    }

    public ArrayList<String> getTimeList() {
        return timeList;
    }

    public void addTime(View view) {
        String time = (String) timer.getText();
        timeList.add(time);
        Toast.makeText(this, "saved " + time, Toast.LENGTH_SHORT).show();

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setText();
        }

        counter++;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putLong("offset", pauseOffset);
        outState.putBoolean("isRunning", running);
        outState.putStringArrayList("array", getTimeList());
        outState.putLong("base", timer.getBase());
        outState.putInt("counter", counter);
    }

    @Override
    public void onRestoreInstanceState(Bundle inState){
        super.onRestoreInstanceState(inState);
        long offset = inState.getLong("offset");
        boolean isRunning = inState.getBoolean("isRunning");
        long base = inState.getLong("base");
        int count = inState.getInt("counter");
        ArrayList<String> array = inState.getStringArrayList("array");
        running = isRunning;
        pauseOffset = offset;
        timeList = array;
        counter = count;
        if (running) {
            timer.setBase(base);
            timer.start();
        }
        else {
            timer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - base;
            timer.setBase(timer.getBase() - pauseOffset);
        }
        updateButtons();
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setText2();
        }
    }

    public void setText() {
        tv.append((String.valueOf(counter)));
        tv.append(". ");
        tv.append(timeList.get(counter - 1));
        tv.append("\n");
    }

    public void setText2() {
        for (int i = 0; i < timeList.size(); i++) {
            tv.append((String.valueOf(i + 1)));
            tv.append(". ");
            tv.append(timeList.get(i));
            tv.append("\n");
        }
    }

}