package com.example.kevin.moleseeker;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class WelcomeActivity extends AppCompatActivity {

    ImageView mole1;
    ImageView mole2;
    ImageView mole3;
    Button skipBtn;
    private static int timer = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setupSkipBtn();
        startWelcomeAnimation();

    }

    private void startWelcomeAnimation(){
        Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.welcome_animation);
        mole1 = findViewById(R.id.welcomeMole1);
        mole2 = findViewById(R.id.welcomeMole2);
        mole3 = findViewById(R.id.welcomeMole3);

        mole1.setAnimation(rotate);
        mole2.setAnimation(rotate);
        mole3.setAnimation(rotate);

        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(),MainMenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },timer);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void setupSkipBtn(){
        skipBtn = findViewById(R.id.skipBtn);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),MainMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
