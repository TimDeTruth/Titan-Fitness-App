package com.bcit.titan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private static int DELAY_TIME = 2000;

    Animation topAnim, bottomAnim;
    ImageView imageView;
    TextView app_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imageView = findViewById(R.id.imageView_splashscreen);
        app_name = findViewById(R.id.textView_splashscreen_logo);

        imageView.setAnimation(topAnim);
        app_name.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, HomeActivity.class);

                Pair[] pairs= new Pair[2];

                pairs[0] = new Pair(imageView, "transition_splash_image");

                pairs[1] = new Pair(app_name, "transition_splash_text");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this,
                        pairs);

                startActivity(i, options.toBundle());
                finish();

            }
        },DELAY_TIME);
    }
}