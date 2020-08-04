package com.example.mocall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    private ImageView loadgif;
    private MotionLayout motion_base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        motion_base = findViewById(R.id.motion_base);
        loadgif = findViewById(R.id.loadgif);
        Glide.with(this)
                .load(R.raw.ballgif)
                .into(loadgif);
        animation(true);


    }

    public void animation(final boolean bool) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadgif.performClick();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (bool) {
                            loadgif.setVisibility(View.INVISIBLE);
                            motion_base.performClick();
                        }
                        else {
                            loadgif.setVisibility(View.INVISIBLE);
                            Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }, 1250);
            }
        }, 4000);
    }
}
