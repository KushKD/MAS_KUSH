package com.dit.hp.janki.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.dit.hp.janki.R;
import com.dit.hp.janki.presentation.CustomDialog;
import com.dit.hp.janki.utilities.Preferences;


public class SplashScreen extends AppCompatActivity {

    CustomDialog CD = new CustomDialog();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Preferences.getInstance().loadPreferences(SplashScreen.this);
        if(Preferences.getInstance().isLoggedIn){
            System.out.println("Loed In "+Preferences.getInstance().isLoggedIn);

        }else{
            loadPrefrence();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                 if(Preferences.getInstance().isLoggedIn){
                 Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                 SplashScreen.this.startActivity(mainIntent);
                 SplashScreen.this.finish();
                 }else{
                     Intent mainIntent = new Intent(SplashScreen.this, Registration.class);
                     SplashScreen.this.startActivity(mainIntent);
                     SplashScreen.this.finish();
                 }





            }
        }, 3000);


    }



    private void loadPrefrence() {
        Preferences.getInstance().loadPreferences(SplashScreen.this);

        Preferences.getInstance().isLoggedIn = false;
        Preferences.getInstance().savePreferences(SplashScreen.this);
    }




}

