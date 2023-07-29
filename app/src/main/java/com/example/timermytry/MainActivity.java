package com.example.timermytry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageButton start;
    private ImageButton stop;
    private ImageButton restart;
    private TextView time;
    private int seconds;
    private boolean running;
    private boolean wasRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUi();
        if(savedInstanceState!=null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        startWatch();
    }
    @Override
    protected void onStop(){
        super.onStop();
        wasRunning=running;
        running = false;

    }
    @Override
    protected void onStart(){
        super.onStart();
        if(wasRunning){
            running = true;
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        wasRunning=running;
        running = false;
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(wasRunning){
            running = true;
        }
    }
    public void startWatch(View view){
        running = true;
    }
    public void stopWatch(View view){
        running = false;
    }
    public void resetWatch(View view){
        running = false;
        seconds = 0;
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("wasRunning",wasRunning);
    }

    protected void startWatch(){

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run(){
                int hours = seconds / 3600;
                int minutes = (seconds % 60)/60;
                int secs = seconds % 60;
                String timeStr = String.format("%d:%02d:%02d",
                        hours, minutes, secs);
                time.setText(timeStr);
                if(running){
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });

    }

    protected void setUi(){
        start = findViewById(R.id.playBtn);
        stop = findViewById(R.id.stopBtn);
        restart = findViewById(R.id.resetBtn);
        time = findViewById(R.id.timeDisplay);
    }
}