package com.example.mocall;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class VerificationActivity extends AppCompatActivity {

    private RelativeLayout view;
    private ImageView loadgif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_verification);

        view=findViewById(R.id.view);
        loadgif=findViewById(R.id.loadgif);
        Glide.with(this)
                .load(R.raw.ballgif)
                .into(loadgif);
//        view.animate()
//                .translationY(view.getHeight())
//                .alpha(0.0f)
//                .setDuration(10000)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        view.setVisibility(View.GONE);
//                    }
//                });
    }
}