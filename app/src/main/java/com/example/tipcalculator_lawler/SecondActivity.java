package com.example.tipcalculator_lawler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private TextView tip;
    private TextView total;
    private TextView totalsplit;
    private Button returnButton;

    private double billAmount;
    private int pepnum;
    private double totalnum;
    private double tipAmount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tip = findViewById(R.id.tip);
        total = findViewById(R.id.total);
        totalsplit = findViewById(R.id.totalsplit);
        returnButton = findViewById(R.id.returnbutton);

        Intent i = getIntent();

        billAmount =  i.getExtras().getDouble("billamount");
        pepnum = i.getExtras().getInt("numpeople");
        tipAmount = i.getExtras().getDouble("tipamount");

        tip.setText(Double.toString(billAmount * tipAmount));
        total.setText(Double.toString(billAmount + (billAmount * tipAmount)));
        totalsplit.setText(Double.toString((billAmount + (billAmount * tipAmount)) / pepnum));

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
