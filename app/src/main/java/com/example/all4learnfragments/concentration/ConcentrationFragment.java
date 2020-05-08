package com.example.all4learnfragments.concentration;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.all4learnfragments.R;

import java.util.ArrayList;
import java.util.Locale;

public class ConcentrationFragment extends Fragment {

    private NumberPicker numberPickerHours;

    private NumberPicker numberPickerMinutes;

    private Button start;

    private TextView timeLeft, motivate;

    ProgressBar progressBar;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.concentration_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        numberPickerHours = view.findViewById(R.id.picker_hours);
        numberPickerMinutes = view.findViewById(R.id.picker_minutes);
        start = (Button) view.findViewById(R.id.button);
        motivate = (TextView) view.findViewById(R.id.motivation);
        timeLeft = (TextView) view.findViewById(R.id.timeLeft);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);


        numberPickerHours.setMinValue(0);
        numberPickerHours.setMaxValue(7);

        numberPickerMinutes.setMinValue(0);
        numberPickerMinutes.setMaxValue(59);

        timeLeft.setText("0:00");

        makeText();


        start.setOnClickListener(new View.OnClickListener() {
            int clicks = 1;

            @Override
            public void onClick(View v) {
                clicks = clicks + 1;

                if (clicks % 2 == 0) {
                    start.setText("Stop");
                    handler.post(counterRunnable);
                } else {
                    start.setText("Start");
                    handler.removeCallbacks(counterRunnable);
                }
            }
        });
    }

    private final Handler handler = new Handler();
    private final Runnable counterRunnable = new Runnable() {
        @Override
        public void run() {
            int hours = numberPickerHours.getValue();
            int minutes = numberPickerMinutes.getValue();
            progressBar.setMax(hours * 60 * 60 * 1000 + minutes * 60 * 1000);
            if (hours != 0 || minutes != 0) {
                CountDownTimer timerRun = new CountDownTimer(hours * 60 * 60 * 1000 + minutes * 60 * 1000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long seconds = millisUntilFinished / 1000;
                        progressBar.setProgress((int) millisUntilFinished);
                        String time = (String.format(Locale.getDefault(), "%2d", seconds / 3600)) + ":" + (String.format(Locale.getDefault(), "%02d", (seconds / 60) % 60));
                        timeLeft.setText(time);
                    }

                    @Override
                    public void onFinish() {
                        timeLeft.setText("Time's up");
                        start.setText("Start");
                    }
                }.start();
            } else {
                Toast.makeText(getActivity(), "Chose time", Toast.LENGTH_SHORT);
            }
        }
    };

    private void makeText() {
        ArrayList<String> quotes = new ArrayList<>();
        quotes.add("Your future more valuable than this phone");
        quotes.add("Finish it!");
        quotes.add("Just do it!");
        quotes.add("Why should I motivate you?");
        motivate.setText(quotes.get((int) (Math.random() * 4)));
    }
}