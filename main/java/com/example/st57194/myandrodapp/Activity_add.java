package com.example.st57194.myandrodapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Activity_add extends Activity {

    private static final int CAMERA_REQUEST = 1888;
    ImageView mimageView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_adapter);

            super.onCreate(savedInstanceState);
            setContentView(R.layout.list_view_adapter);

            mimageView = (ImageView) this.findViewById(R.id.image_from_camera);
            Button button = (Button) this.findViewById(R.id.take_image_from_camera);

        Button next = (Button) findViewById(R.id.Button_back);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        Button save = (Button) findViewById(R.id.Button_save);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(Activity_add.this, Activity_main.class);
                EditText name = (EditText) findViewById(R.id.name);
                String nn = name.getText().toString();
                EditText surname = (EditText) findViewById(R.id.surname);
                String ss = surname.getText().toString();


                if (name.getText().length() == 0 || surname.getText().length() == 0) {
                    Toast toast = Toast.makeText(Activity_add.this, "You must fill all fields!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    intent.putExtra("name", nn);
                    intent.putExtra("surname", ss);
                    setResult(Activity.RESULT_OK, intent);

                    finish();
                }
            }

        });

    }

    public void takeImageFromCamera(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            mimageView.setImageBitmap(mphoto);
        }
    }


}
