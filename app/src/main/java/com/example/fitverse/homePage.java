package com.example.fitverse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * This class is responsible for implementing the
 * homepage.
 */
public class homePage extends Activity {
    Button btnCamera;
    Button btnGallery;
    realTimeDetection cc = new realTimeDetection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        btnCamera = (Button) findViewById(R.id.camera);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homePage.this, realTimeDetection.class));

            }
        });
        btnGallery = (Button) findViewById(R.id.Gallery);

        btnGallery.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homePage.this, calorieDetection.class));
            }
        }));
    }
}

