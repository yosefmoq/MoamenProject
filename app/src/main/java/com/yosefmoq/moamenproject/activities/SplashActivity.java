package com.yosefmoq.moamenproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.yosefmoq.moamenproject.MainActivity;
import com.yosefmoq.moamenproject.R;
import com.yosefmoq.moamenproject.utils.Session;


public class SplashActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_splash);
                new CountDownTimer(3000,1000){

                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {
                                if(Session.getInstance(SplashActivity.this).getLocalSave().isLoginGuest()){
                                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                }else {
                                        startActivity(new Intent(SplashActivity.this, SigninActivity.class));
                                }
                                finish();
                        }
                }.start();
        }
}