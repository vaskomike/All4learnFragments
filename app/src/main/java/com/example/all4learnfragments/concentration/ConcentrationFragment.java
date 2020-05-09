package com.example.all4learnfragments.concentration;

import android.content.Context;
import android.content.SharedPreferences;
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
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static android.drm.DrmStore.Playback.START;

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
                    startTimer();
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
        int hours = numberPickerHours.getValue();
        int minutes = numberPickerMinutes.getValue();
        maxTime = hours * 60 * 60 * 1000 + minutes * 60 * 1000;
        progressBar.setMax(maxTime);
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
        timerIsRunning = true;
        start.setText(R.string.timer_stop);
        reset.setVisibility(View.INVISIBLE);

    }

    public void stopTimer() {
        countDownTimer.cancel();
        timerIsRunning = false;
        start.setText(R.string.timer_start);
        reset.setVisibility(View.VISIBLE);
        progressBar.getProgress();

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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("millisLeft", secondsToEnd);
        outState.putBoolean("isRunning", timerIsRunning);
    }

    public void updateButtons() {
        if (timerIsRunning) {
            reset.setVisibility(View.INVISIBLE);
            start.setText(R.string.timer_pause);
        } else {
            start.setText(R.string.timer_start);
            if (secondsToEnd < 100) {
                start.setVisibility(View.INVISIBLE);
            } else start.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        secondsToEnd = savedInstanceState.getLong("millisLeft");
        timerIsRunning = savedInstanceState.getBoolean("isRunning");
        updateTextTime();
        updateButtons();
    }

    private void makeText() {
        ArrayList<Integer> quotes = new ArrayList<>();
        quotes.add(R.string.concentration_motivation);
        quotes.add(R.string.concentration_motivation_2);
        quotes.add(R.string.concentration_motivation_3);
        quotes.add(R.string.concentration_motivation_4);
        motivate.setText(quotes.get((int) (Math.random() * 4)));
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isRunning", timerIsRunning);
        editor.putLong("millisLeft", secondsToEnd);
        editor.putInt("maxTime", maxTime);


    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        secondsToEnd = sharedPreferences.getLong("millisLeft", 0);
        maxTime = sharedPreferences.getInt("maxTime", 0);
        timerIsRunning = sharedPreferences.getBoolean("isRunning", false);
        updateTextTime();
        updateButtons();
    }


}


