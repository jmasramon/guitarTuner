package com.example.android.tuner;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class UkuleleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ukulele);
        getSupportActionBar().setTitle("Ukulele");
    }

    Context toasty = UkuleleActivity.this;
    int duration = Toast.LENGTH_SHORT;

    public void pressString1(View view) {
        CharSequence pressed = "Tuning to G";
        Toast.makeText(toasty, pressed, duration).show();
    }

    public void pressString2(View view) {
        CharSequence pressed = "Tuning to C";
        Toast.makeText(toasty, pressed, duration).show();
    }

    public void pressString3(View view) {
        CharSequence pressed = "Tuning to E";
        Toast.makeText(toasty, pressed, duration).show();
    }

    public void pressString4(View view) {
        CharSequence pressed = "Tuning to A";
        Toast.makeText(toasty, pressed, duration).show();
    }
}
