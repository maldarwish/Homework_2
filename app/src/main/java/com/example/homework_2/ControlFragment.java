package com.example.homework_2;

import android.app.Fragment;
import android.os.AsyncTask;
import android.widget.TextView;

public class ControlFragment extends Fragment {
    /**
     * In addition to other fields, you will need these:
     */
    private TimerAsyncTask asynctask; // will use this in controller to execute timer.
    boolean running; // boolean to check whether timer should be running or not (changed by clicking on start/stop button).
    Timer timer; // timer used in AsyncTask and Controller.
    TextView timerText; // the textview in your UI to display time.

    private class TimerAsyncTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            String curr_time = String.format("%02d:%02d:%02d", values[0], values[1], values[2]); // used to properly format string.
            timerText.setText(curr_time); // update UI.
        }

        @Override
        protected Void doInBackground(Integer... times) {
            while (running) { // running boolean must be updated in controller.
                timer.calc(); // calculate time.
                publishProgress(timer.getHours(), timer.getMinutes(), timer.getSeconds()); // publish progress fomr onProgressUpdate method to be triggered.
                try {
                    Thread.sleep(1000); // sleep for 1 second (1000 milliseconds).
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
            return null;
        }
    }
}
