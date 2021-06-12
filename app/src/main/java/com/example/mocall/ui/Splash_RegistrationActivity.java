package com.example.mocall.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.bumptech.glide.Glide;
import com.example.mocall.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Splash_RegistrationActivity extends AppCompatActivity {

    private ImageView loadgif;
    private MotionLayout motion_base;
    private Button verify_button,generate_otp_button;
    private TextView verificationText2,resend_code,step_number,subtitle,title;
    private in.aabhasjindal.otptextview.OtpTextView otp_plate;
    private LinearLayout number_plate;
    private com.hbb20.CountryCodePicker country_code;
    private EditText phone_text;
    private com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar line_progressBar;

    private String phoneNumber="";

    //for auth
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private ProgressDialog loadingDialogue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_registration);
        init();
        mAuth=FirebaseAuth.getInstance();
        loadingDialogue =new ProgressDialog(this);
        Glide.with(this)
                .load(R.raw.ballgif)
                .into(loadgif);
        animation(mAuth.getCurrentUser() == null);
        line_progressBar.setProgress(50);
        generate_otp_button.setOnClickListener(view -> {
            phoneNumber=country_code.getFullNumberWithPlus();
            if(!(phoneNumber.equals("") || phoneNumber.length()<10)){
                loadingDialogue.setTitle("Phone Number Verification");
                loadingDialogue.setMessage("Please Wait....");
                loadingDialogue.setCanceledOnTouchOutside(false);
                loadingDialogue.show();

                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber(phoneNumber)       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(Splash_RegistrationActivity.this)                 // Activity (for callback binding)
                                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);

            }
            else {
                Toast.makeText(Splash_RegistrationActivity.this, "Enter valid Phone number", Toast.LENGTH_SHORT).show();
            }
        });
        verify_button.setOnClickListener(view -> {
            String verificationCode = otp_plate.getOTP();
             if(verificationCode.equals("")){
                 Toast.makeText(Splash_RegistrationActivity.this, "Enter verification code", Toast.LENGTH_SHORT).show();
             }
             else {
                 loadingDialogue.setTitle("OTP Verification");
                 loadingDialogue.setMessage("Please Wait....");
                 loadingDialogue.setCanceledOnTouchOutside(false);
                 loadingDialogue.show();

                 PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId,verificationCode);
                 signInWithPhoneAuthCredential(credential);
             }
        });


        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Splash_RegistrationActivity.this, "Invalid Number", Toast.LENGTH_SHORT).show();
                loadingDialogue.dismiss();
                failed_case();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mVerificationId=s;
                mResendToken=forceResendingToken;
                loadingDialogue.dismiss();
                Toast.makeText(Splash_RegistrationActivity.this, "Code Send!", Toast.LENGTH_SHORT).show();
                generate_btn_func();
            }
        };


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        loadingDialogue.dismiss();
                        Toast.makeText(Splash_RegistrationActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                        sendUserToMainActivity();
                    } else {
                        loadingDialogue.dismiss();
                        String e= Objects.requireNonNull(task.getException()).toString();
                        Toast.makeText(Splash_RegistrationActivity.this, "Error :"+e, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void sendUserToMainActivity(){
        Intent intent = new Intent(Splash_RegistrationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void generate_btn_func() {
        title.setText("Verification");
        subtitle.setText("Please type the verification code send to " + country_code.getFullNumberWithPlus());
        step_number.setText("Step 2 of 2");
        line_progressBar.setProgress(100);
        number_plate.setVisibility(View.GONE);
        otp_plate.setVisibility(View.VISIBLE);
        verificationText2.setVisibility(View.GONE);
        resend_code.setVisibility(View.VISIBLE);
        generate_otp_button.setVisibility(View.GONE);
        verify_button.setVisibility(View.VISIBLE);
    }

    private void failed_case(){
        title.setText("Your Number");
        subtitle.setText("Please confirm you phone country code and enter your phone number.");
        step_number.setText("Step 1 of 2");
        line_progressBar.setProgress(50);
        number_plate.setVisibility(View.VISIBLE);
        otp_plate.setVisibility(View.GONE);
        verificationText2.setVisibility(View.VISIBLE);
        resend_code.setVisibility(View.GONE);
        generate_otp_button.setVisibility(View.VISIBLE);
        verify_button.setVisibility(View.GONE);
    }

    private void init(){
        motion_base = findViewById(R.id.motion_base);
        loadgif = findViewById(R.id.loadgif);
        verify_button=findViewById(R.id.verify);
        generate_otp_button=findViewById(R.id.generateOTP);
        verificationText2=findViewById(R.id.verificationText2);
        resend_code=findViewById(R.id.re_send_code);

        otp_plate=findViewById(R.id.otp_plate);
        otp_plate.setVisibility(View.GONE);

        number_plate=findViewById(R.id.number_plate);
        country_code=findViewById(R.id.ccp);
        phone_text=findViewById(R.id.phoneText);
        line_progressBar=findViewById(R.id.progress_bar);
        step_number=findViewById(R.id.steps_number);
        subtitle=findViewById(R.id.subtitle);
        title=findViewById(R.id.title);

        country_code=findViewById(R.id.ccp);
        country_code.registerCarrierNumberEditText(phone_text);
    }

    public void animation(final boolean bool) {
        new Handler().postDelayed(() -> {
            loadgif.performClick();
            new Handler().postDelayed(() -> {
                if (bool) {
                    loadgif.setVisibility(View.INVISIBLE);
                    motion_base.performClick();
                }
                else {
                    loadgif.setVisibility(View.INVISIBLE);
                    sendUserToMainActivity();
                }
            }, 1250);
        }, 4000);
    }
}
