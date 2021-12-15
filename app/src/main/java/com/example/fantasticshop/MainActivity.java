package com.example.fantasticshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        progress();
    }

    // the propressBar loading function
    public void progress(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                counter ++;
                progressBar.setMax(100);
                progressBar.setProgress(counter);
                if (counter == progressBar.getMax()){
                    timer.cancel();

                    // Takes user to UserLoginActivity when progress = 100
                    Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
                    startActivity(intent);
                }
            }
        };

        timer.schedule(timerTask, 2, 20);

    }


}