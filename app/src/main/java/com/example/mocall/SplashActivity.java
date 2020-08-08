package com.example.mocall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    private ImageView loadgif;
    private MotionLayout motion_base;
    private Button verify_button,generate_otp_button;
    private TextView verificationText2,resend_code,step_number,subtitle,title;
    private in.aabhasjindal.otptextview.OtpTextView otp_plate;
    private LinearLayout number_plate;
    private com.hbb20.CountryCodePicker country_code;
    private EditText phone_number;
    private com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar line_progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        Glide.with(this)
                .load(R.raw.ballgif)
                .into(loadgif);
        animation(true);
        line_progressBar.setProgress(50);
        generate_otp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generate_btn_func();
            }
        });


    }

    private void generate_btn_func() {
        title.setText("Verification");
        subtitle.setText("Please type the verification code send to "+ phone_number.getText().toString());
        step_number.setText("Step 2 of 2");
        line_progressBar.setProgress(100);
        number_plate.setVisibility(View.GONE);
        otp_plate.setVisibility(View.VISIBLE);
        verificationText2.setVisibility(View.GONE);
        resend_code.setVisibility(View.VISIBLE);
        generate_otp_button.setVisibility(View.GONE);
        verify_button.setVisibility(View.VISIBLE);
    }

    private void init(){
        motion_base = findViewById(R.id.motion_base);
        loadgif = findViewById(R.id.loadgif);
        verify_button=findViewById(R.id.verify);
        generate_otp_button=findViewById(R.id.generateOTP);
        verificationText2=findViewById(R.id.verificationText2);
        resend_code=findViewById(R.id.re_send_code);
        otp_plate=findViewById(R.id.otp_plate);
        number_plate=findViewById(R.id.number_plate);
        country_code=findViewById(R.id.ccp);
        phone_number=findViewById(R.id.phoneText);
        line_progressBar=findViewById(R.id.progress_bar);
        step_number=findViewById(R.id.steps_number);
        subtitle=findViewById(R.id.subtitle);
        title=findViewById(R.id.title);
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
