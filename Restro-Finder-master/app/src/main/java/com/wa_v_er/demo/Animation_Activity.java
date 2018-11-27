package com.wa_v_er.demo;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Animation_Activity extends AppCompatActivity {
    Animation translate,fade;
    TextView restro,quote;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_);
        translate = AnimationUtils.loadAnimation(this,R.anim.translate);
        fade = AnimationUtils.loadAnimation(this,R.anim.fade);
        imageView = findViewById(R.id.location);
        restro = findViewById(R.id.restro);
        quote = findViewById(R.id.quote);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"KaushanScript-Regular.otf");
        Typeface billy = Typeface.createFromAsset(getAssets(),"Billy Ohio.ttf");
        restro.setTypeface(typeface);
        quote.setTypeface(billy);

        imageView.startAnimation(translate);
        quote.startAnimation(fade);
        restro.startAnimation(fade);

        Thread mythread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(5000);
                    Intent intent =new Intent(Animation_Activity.this,MapsActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mythread.start();
    }


}
