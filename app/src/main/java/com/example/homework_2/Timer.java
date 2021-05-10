package com.example.homework_2;

import java.util.ArrayList;

public class Timer {

    private ArrayList<String> timeList;
    private int sec;
    private int min;
    private int hr;

    public void calcTime()
    {
        timeList = new ArrayList<String>();
        sec = 0;
        min = 0;
        hr = 0;
    }

    public ArrayList<String> getTimeList() {
        return timeList;
    }

    public int getSeconds() {
        return sec;
    }

    public int getMinutes() {
        return min;
    }

    public int getHours() {
        return hr;
    }

    public void calc()
    {
        sec++;
        if (sec == 60)
        {
            sec = 0;
            min++;
        }
        if (min == 60)
        {
            min = 0;
            hr++;
        }
    }

    public String addTime(String time)
    {
        timeList.add(time);
        return String.valueOf(timeList);
    }

    public void reset()
    {
        sec = 0;
        min = 0;
        hr = 0;
        timeList = new ArrayList<String>();
    }

}
