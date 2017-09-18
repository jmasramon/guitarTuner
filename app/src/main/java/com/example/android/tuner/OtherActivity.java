package com.example.android.tuner;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        getSupportActionBar().setTitle("Other");

    }

    public void go(View view) {
        TextView textView5 = (TextView) findViewById(R.id.textView5);
        TextView textView6 = (TextView) findViewById(R.id.textView6);
        LinearLayout layoutView5 = (LinearLayout) findViewById(R.id.layout5);
        LinearLayout layoutView6 = (LinearLayout) findViewById(R.id.layout6);
        Button strings4button = (Button) findViewById(R.id.strings4button);
        Button strings5button = (Button) findViewById(R.id.strings5button);
        Button strings6button = (Button) findViewById(R.id.strings6button);
        CheckBox checkBox4 = (CheckBox) findViewById(R.id.number4);
        CheckBox checkBox5 = (CheckBox) findViewById(R.id.number5);
        CheckBox checkBox6 = (CheckBox) findViewById(R.id.number6);

        boolean strings4 = checkBox4.isChecked();
        boolean strings5 = checkBox5.isChecked();
        boolean strings6 = checkBox6.isChecked();

        if((strings4==true && strings5==true)||(strings4==true && strings6==true)||
            (strings6==true && strings5==true)||(strings4==true && strings6==true)||
            (strings6==true && strings5==true && strings4==true)){
            Context toasty = OtherActivity.this;
            int duration = Toast.LENGTH_SHORT;
            CharSequence error = "You cannot select more than one amount of strings at once";
            Toast toast = Toast.makeText(toasty, error, duration);
            toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 30);
            toast.show();
        }

        else if (strings4 ==true){
            textView5.setVisibility(View.GONE);
            textView6.setVisibility(View.GONE);
            layoutView5.setVisibility(View.GONE);
            layoutView6.setVisibility(View.GONE);
            strings4button.setVisibility(View.VISIBLE);
            strings5button.setVisibility(View.GONE);
            strings6button.setVisibility(View.GONE);
        }
        else if (strings5 == true) {
            textView5.setVisibility(View.VISIBLE);
            textView6.setVisibility(View.GONE);
            layoutView5.setVisibility(View.VISIBLE);
            layoutView6.setVisibility(View.GONE);
            strings4button.setVisibility(View.GONE);
            strings5button.setVisibility(View.VISIBLE);
            strings6button.setVisibility(View.GONE);
        }
        else if (strings6 == true) {
            textView5.setVisibility(View.VISIBLE);
            textView6.setVisibility(View.VISIBLE);
            layoutView5.setVisibility(View.VISIBLE);
            layoutView6.setVisibility(View.VISIBLE);
            strings4button.setVisibility(View.GONE);
            strings5button.setVisibility(View.GONE);
            strings6button.setVisibility(View.VISIBLE);
        }
    }

    public void tune(View view) {
        Context toasty = OtherActivity.this;
        int duration = Toast.LENGTH_SHORT;
        CharSequence tuning = "Tuning in process";
        Toast toast = Toast.makeText(toasty, tuning, duration);
        toast.show();
    }


}
