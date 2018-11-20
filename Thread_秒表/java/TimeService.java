package com.example.pc.myapplication4;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TimeService extends Service {
    private Thread timeThread;

    public TimeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return  null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timeThread = new Thread(null, backgroundThread, "timeThread");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if(!timeThread.isAlive())
            timeThread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timeThread.interrupt();
    }

    private Runnable backgroundThread = new Runnable() {
        @Override
        public void run() {
            try {
                while(!Thread.interrupted()) {
                    Thread.sleep(50);

                    if (MainActivity.flag)
                    {
                        long thisTime = System.currentTimeMillis();
                        MainActivity.msec = MainActivity.msec + (int) (thisTime / 10 - MainActivity.lastTime / 10);
                        MainActivity.lastTime = thisTime;

                        while (MainActivity.msec >= 100)
                        {
                            MainActivity.msec = MainActivity.msec - 100;
                            MainActivity.sec = MainActivity.sec + 1;
                        }

                        while (MainActivity.sec >= 60)
                        {
                            MainActivity.sec = MainActivity.sec - 60;
                            MainActivity.min = MainActivity.min + 1;
                        }
                        if (MainActivity.min == 60)
                        {
                            MainActivity.min = 0;
                        }

                        MainActivity.timeStr = String.format("%02d", MainActivity.min) + ":" + String.format("%02d", MainActivity.sec) + ":" + String.format("%02d", MainActivity.msec);
                        MainActivity.UpdateGUI(MainActivity.timeStr);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
