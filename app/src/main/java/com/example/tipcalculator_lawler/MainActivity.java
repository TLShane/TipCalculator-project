package com.example.tipcalculator_lawler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    final Context context = this;
    //buttons
    private RadioButton tenButton;
    private RadioButton fifteenButton;
    private RadioButton twentyFiveButton;
    private RadioButton customButton;
    private Button resetButton;
    private Button calcButton;
    //radiogroup
    private RadioGroup buttonGroup;
    //intent
    private Intent i;
    //editText
    private EditText amount;
    private EditText numPeople;
    //layoutInflater
    private LayoutInflater li;
    //textViews
    private TextView tipView;
    private TextView total;
    private TextView tipPrice;
    private TextView totalPerPerson;
    //extras
    private double tipAmount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //textviews view
        tipView = findViewById(R.id.textView4);
        //edittexts view
        amount = findViewById(R.id.amountEdit);
        numPeople = findViewById(R.id.peopleEdit);
        //buttons view
        resetButton = findViewById(R.id.resetB);
        tenButton = findViewById(R.id.tenButton);
        fifteenButton = findViewById(R.id.fifteenButton);
        twentyFiveButton = findViewById(R.id.twentyFiveButton);
        customButton = findViewById(R.id.customButton);
        calcButton = findViewById(R.id.calcB);



        i = new Intent(this, SecondActivity.class);
        //reset button code
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sets everything to base values
                tipView.setText("0%");
                tipAmount = 0;
                tenButton.setChecked(false);
                fifteenButton.setChecked(false);
                twentyFiveButton.setChecked(false);
                customButton.setChecked(false);
                amount.setText("");
                numPeople.setText("");
            }
        });

        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String amo = amount.getText().toString();
                String pep = numPeople.getText().toString();
                //if Bill amount it empty or less than 1 then it generates an error, once the error is closed the proper edit text is selected
                if (amo.equals("") || Double.parseDouble(amo) < 1)
                {
                    //
                    String error = "value in bill amount must be not empty and greater than 1 ";
                    int errorid = R.id.amountEdit;
                    showErrorAlert(error,errorid);

                }
                //if amount of people it empty or less than 1 then it generates an error, once the error is closed the proper edit text is selected
                else if (pep.equals("") || Double.parseDouble(pep)< 1)
                {
                    String error = "value in number of people must be not empty and greater than 0";
                    int errorid = R.id.peopleEdit;
                    showErrorAlert(error,errorid);
                }
                //if tip is less than 1 then it generates an error, however i couldnt think of a way to select the button
                else if (tipAmount < .01){
                    String error = "Tip must be greater than 1%";
                    int errorid = R.id.customButton;
                    showErrorAlert(error,errorid);
                }
                //else it puts all the amounts into the intent and starts activity two
                else {
                    i.putExtra("billamount", Double.parseDouble(amount.getText().toString()));
                    i.putExtra("tipamount", tipAmount);
                    i.putExtra("numpeople", Integer.parseInt(numPeople.getText().toString()));
                    startActivity(i);
                }
            }
        });

    }
    public void onRadioButtonClicked(View view) {
        // check if button is pressed
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked and apply the corresponding percentage
        switch(view.getId()) {
            case R.id.tenButton:
                if (checked) {
                    tipAmount = .10;
                    break;
                }
            case R.id.fifteenButton:
                if (checked) {
                    tipAmount = .15;
                    break;
                }
            case R.id.twentyFiveButton:
                if (checked)
                    tipAmount = .25;
                break;
            case R.id.customButton:
                if (checked) {
                    //get the prompt layout
                    li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.prompt, null);
                    // initialize alertdialog builder
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);
                    //set view
                    alertDialogBuilder.setView(promptsView);
                    //set up user input
                    final EditText userInput = (EditText) promptsView
                            .findViewById(R.id.editTextDialogUserInput);

                    alertDialogBuilder
                            .setCancelable(true)
                            .setPositiveButton("Confirm",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            // get user input and set it to result
                                            // edit text
                                            try {
                                                tipView.setText(userInput.getText() + "%");
                                                tipAmount = Double.parseDouble(userInput.getText().toString()) / 100;
                                            }catch(NumberFormatException ex){
                                                String error = "input numbers only";
                                                int errorid = R.id.customButton;
                                                showErrorAlert(error,errorid);

                                            }
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    alertDialog.show();

                }
                break;
        }

    }
    private void showErrorAlert(String errorMessage, final int fieldId) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(errorMessage)
                .setNeutralButton("Go To",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                findViewById(fieldId).requestFocus();
                            }
                        }).show();
    }
}