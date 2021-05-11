package com.example.healthy;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.widget.RelativeLayout;

public class Splash extends Activity {
    RelativeLayout lt;
    Thread thread;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        lt = (RelativeLayout) findViewById(R.id.splash);
        lt.setBackgroundResource(R.drawable.img);
        thread = new Thread(runable);
        thread.start();

        mp = new MediaPlayer();
        mp = MediaPlayer.create(Splash.this, R.raw.do1);
        mp.start();
    }

    public Runnable runable = new Runnable() {
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {

                startActivityForResult(new Intent(Splash.this, MainActivity.class), 0);
                finish();


            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    };

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mp.release();

        finish();

    }

}
