package com.example.st57194.myandrodapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by st57194 on 16.03.2017..
 */

public class Activity_details extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        Bundle bundle = getIntent().getExtras();

        final String data = bundle.getString("PersonInfo");

        TextView myTextViewName= (TextView) findViewById(R.id.info_name);
        myTextViewName.setText(data);


        Button share = (Button) findViewById(R.id.Button_share);
        share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, data);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        Button back = (Button) findViewById(R.id.Button_back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}

