package com.example.all4learnfragments.concentration;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.all4learnfragments.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ConcentrationFragment extends Fragment {

    private NumberPicker numberPickerHours;

    private NumberPicker numberPickerMinutes;

    private Button start;

    private TextView timeLeft, motivate;

    ProgressBar progressBar;

    private ImageButton reset;

    private Boolean timerIsRunning;

    private CountDownTimer countDownTimer;

    private long secondsToEnd;

    private int maxTime;

    private Handler handler;

//    private HashMap<String,Object> hashMap=new HashMap<>();

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
        reset = (ImageButton) view.findViewById(R.id.reset);
        timerIsRunning = false;

        numberPickerHours.setMinValue(0);
        numberPickerHours.setMaxValue(7);

        numberPickerMinutes.setMinValue(0);
        numberPickerMinutes.setMaxValue(59);


        timeLeft.setText("0:00");


        makeText();


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerIsRunning) {
                    stopTimer();
                } else {
                    handler = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            startTimer();
                        }
                    };
                    handler.post(runnable);
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        updateTextTime();
    }


    public void startTimer() {

//        if (hashMap.isEmpty()) {
        int hours = numberPickerHours.getValue();
        int minutes = numberPickerMinutes.getValue();
        maxTime = hours * 60 * 60 * 1000 + minutes * 60 * 1000;
        progressBar.setMax(maxTime);
        if (maxTime != 0) {
            countDownTimer = new CountDownTimer(maxTime, 100) {
                @Override
                public void onTick(long millisUntilFinished) {
                    secondsToEnd = millisUntilFinished / 1000;
                    updateTextTime();
                    progressBar.setProgress((int) (millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    timerIsRunning = false;
                    start.setText(R.string.timer_start);
                    timeLeft.setText(R.string.timer_end);
                }
            }.start();
        } else
            Toast.makeText(getActivity(), R.string.enter_time, Toast.LENGTH_SHORT).show();
        timerIsRunning = false;
        start.setText(R.string.timer_stop);
        reset.setVisibility(View.INVISIBLE);
    }/*else {
            progressBar.setMax((Integer) hashMap.get("maxTime"));
            countDownTimer = new CountDownTimer((Long) hashMap.get("maxTime"), 100) {

                @Override
                public void onTick(long millisUntilFinished) {
                    millisUntilFinished= (long) hashMap.get("secondsToEnd")*1000;
                    updateTextTime();
                    progressBar.setProgress((int) (millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    timerIsRunning = false;
                    start.setText(R.string.timer_start);
                    timeLeft.setText(R.string.timer_end);
                }
            }.start();
        }*/



    public void stopTimer() {

        countDownTimer.cancel();
        timerIsRunning = false;
        start.setText(R.string.timer_start);
        reset.setVisibility(View.VISIBLE);
//        hashMap.put("secondsToEnd",secondsToEnd);
//        hashMap.put("maxTime",maxTime);
    }

    public void resetTimer() {
        secondsToEnd = 0;
        updateTextTime();
        reset.setVisibility(View.INVISIBLE);
    }

    public void updateTextTime() {
        String left = String.format(Locale.getDefault(), "%2d", secondsToEnd / 3600) + ":" + String.format(Locale.getDefault(), "%02d", (secondsToEnd / 60) % 60);
        timeLeft.setText(left);
    }


    private void makeText() {
        ArrayList<Integer> quotes = new ArrayList<Integer>();
        quotes.add(R.string.concentration_motivation);
        quotes.add(R.string.concentration_motivation_2);
        quotes.add(R.string.concentration_motivation_3);
        quotes.add(R.string.concentration_motivation_4);
        quotes.add(R.string.concentration_motivation_7);
        quotes.add(R.string.concentration_motivation_6);
        quotes.add(R.string.concentration_motivation_5);

        motivate.setText(quotes.get((int) (Math.random() * 7)));

    }
}


