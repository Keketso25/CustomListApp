package com.example.kntombela.customlistapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Local Variables
        Button btnRecover = (Button) findViewById(R.id.btnRecover);
        Button btnRespond = (Button) findViewById(R.id.btnRespond);
        Button btnResume = (Button) findViewById(R.id.btnResume);

        //Actionlisteners for button click events
        btnRecover.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                startActivity(new Intent(v.getContext(), StepSummaryActivity.class));
            }
        });

        btnRespond.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                startActivity(new Intent(v.getContext(), StepSummaryActivity.class));
            }
        });

        btnResume.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                startActivity(new Intent(v.getContext(), StepSummaryActivity.class));
            }
        });

    }


}
