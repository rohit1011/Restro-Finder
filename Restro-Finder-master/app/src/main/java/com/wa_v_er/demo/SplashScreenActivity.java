package com.wa_v_er.demo;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.TextureView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private TextView restro,quote;
      @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash_screen);
        restro = findViewById(R.id.restro);
        quote = findViewById(R.id.quote);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"KaushanScript-Regular.otf");
        Typeface billy = Typeface.createFromAsset(getAssets(),"Billy Ohio.ttf");
        restro.setTypeface(typeface);
        quote.setTypeface(billy);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreenActivity.this, MapsActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}