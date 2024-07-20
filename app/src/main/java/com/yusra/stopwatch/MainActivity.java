package com.yusra.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView textViewStopwatch;
    private Button buttonStart, buttonStop, buttonReset;

    private Handler handler = new Handler();
    private Runnable runnable;

    private long startTime, elapsedTime;
    private boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewStopwatch = findViewById(R.id.textViewStopwatch);
        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        buttonReset = findViewById(R.id.buttonReset);

        buttonStart.setOnClickListener(v -> startStopwatch());
        buttonStop.setOnClickListener(v -> stopStopwatch());
        buttonReset.setOnClickListener(v -> resetStopwatch());
    }

    private void startStopwatch() {
        if (!running) {
            startTime = System.currentTimeMillis() - elapsedTime;
            handler.post(runnable = new Runnable() {
                @Override
                public void run() {
                    elapsedTime = System.currentTimeMillis() - startTime;
                    int seconds = (int) (elapsedTime / 1000);
                    int minutes = seconds / 60;
                    int hours = minutes / 60;
                    seconds = seconds % 60;
                    minutes = minutes % 60;

                    textViewStopwatch.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

                    handler.postDelayed(this, 1000);
                }
            });
            running = true;
        }
    }

    private void stopStopwatch() {
        if (running) {
            handler.removeCallbacks(runnable);
            running = false;
        }
    }

    private void resetStopwatch() {
        stopStopwatch();
        elapsedTime = 0;
        textViewStopwatch.setText("00:00:00");
    }
}
