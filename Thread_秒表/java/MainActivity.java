package com.example.pc.myapplication4;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static Handler handler = new Handler();
    public static TextView timeTv = null;
    public static int min = 0, sec = 0, msec = 0;
    public static String timeStr;
    public static long lastTime;
    public static boolean flag = false;
    Intent serviceIntent;

    public static void UpdateGUI(String string)
    {
        timeStr = string;
        handler.post(RefreshLable);
    }

    private static Runnable RefreshLable = new Runnable() {
        @Override
        public void run() {
            timeTv.setText(timeStr);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        timeTv = (TextView)findViewById(R.id.timeTv);
        final Button resetBtn = (Button)findViewById(R.id.resetBtn);
        final Button startBtn = (Button)findViewById(R.id.startBtn);
        final Button stopBtn = (Button)findViewById(R.id.stopBtn);

        serviceIntent = new Intent(MainActivity.this, TimeService.class);

        resetBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                flag = false;
                min = sec = msec = 0;
                lastTime = System.currentTimeMillis();
                timeTv.setText("00:00:00");
                stopService(serviceIntent);
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                flag = true;
                lastTime = System.currentTimeMillis();
                startService(serviceIntent);
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                flag = false;
                stopService(serviceIntent);
            }
        });
    }
}
