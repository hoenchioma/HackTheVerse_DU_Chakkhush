package com.example.binidro.views.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.binidro.R;
import com.example.binidro.views.auth.AuthActivity;

public class WelcomeActivity extends AppCompatActivity {

    private TextView appTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // On Animation End: The Activity Will Change
        setAnimation();
    }


    void setAnimation(){
        appTitle = (TextView) findViewById(R.id.appTitle);
        Animation zoomIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        zoomIn.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                changeActivity();
            }
        });

        appTitle.startAnimation(zoomIn);
    }

    void changeActivity(){
        Intent intent = new Intent(WelcomeActivity.this, AuthActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
}