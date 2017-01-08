package com.example.kntombela.customlistapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        //Retrieve data from intent
        Intent intent = getIntent();

        // The detail Activity called via intent.  Inspect the intent for step details
        if (intent != null && intent.hasExtra("Step")) {

            Step step = (Step) intent.getSerializableExtra("Step");

            //Set Step Header
            ((TextView) findViewById(R.id.detail_txtStepHeader)).setText(step.getStepHeader());

            //Set Step Detail
            ((TextView) findViewById(R.id.detail_txtDetail)).setText(step.getStepDetail());
        }
    }
}
