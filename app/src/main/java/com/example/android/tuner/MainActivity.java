package com.example.android.tuner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView guitar = (TextView) findViewById(R.id.guitar);
        guitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent guitarIntent = new Intent(MainActivity.this, GuitarActivity.class);
                startActivity(guitarIntent);
            }
        });

        TextView guitalele = (TextView) findViewById(R.id.guitalele);
        guitalele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent guitaleleIntent = new Intent(MainActivity.this, GuitaleleActivity.class);
                startActivity(guitaleleIntent);
            }
        });

        TextView ukulele = (TextView) findViewById(R.id.ukulele);
        ukulele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ukuleleIntent = new Intent(MainActivity.this, UkuleleActivity.class);
                startActivity(ukuleleIntent);
            }
        });

        TextView bass = (TextView) findViewById(R.id.bass);
        bass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bassIntent = new Intent(MainActivity.this, BassActivity.class);
                startActivity(bassIntent);
            }
        });

        TextView other = (TextView) findViewById(R.id.other);
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent otherIntent = new Intent(MainActivity.this, OtherActivity.class);
                startActivity(otherIntent);
            }
        });
    }
}
