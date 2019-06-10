package com.example.fitverse;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mainpage extends Activity {
    Button btncamera;
    Button btngallery;
    MainActivity cc = new MainActivity();




    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_main);
        btncamera = (Button)findViewById(R.id.camera);

        btncamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {

                startActivity(new Intent(mainpage.this, MainActivity.class));

            }
        });
        btngallery = (Button)findViewById(R.id.Gallery);

        btngallery.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainpage.this, image_upload.class));
            }
        }));


        }



}

