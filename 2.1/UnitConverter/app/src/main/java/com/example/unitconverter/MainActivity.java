package com.example.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    TextView output1, output2, output3;
    EditText inputValue;
    Spinner spinner;
    int option;

    boolean initialClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output1 = findViewById(R.id.output1);
        output2 = findViewById(R.id.output2);
        output3 = findViewById(R.id.output3);

        inputValue = findViewById(R.id.inputValue);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.unit_entries));
        spinner.setAdapter(adapter);
    }


    public void temperature(View view) {
        DecimalFormat f = new DecimalFormat("#.##");
        if (spinner.getSelectedItemPosition() != 0) {
            Toast.makeText(getApplicationContext(),"Select the correct option to do this conversion. (Celsius)", Toast.LENGTH_SHORT).show();
            return;
        }
       try {
           float inputFloat = Float.parseFloat(inputValue.getText().toString());
           output1.setText(f.format((inputFloat * 9/5) + 32) + " Fahrenheit");
           output2.setText(f.format(inputFloat + 273.15) + " Kelvin");
       }catch (final NumberFormatException e) {
           Toast.makeText(getApplicationContext(),"Please enter a number!", Toast.LENGTH_SHORT).show();
       }
    }

    public void weight(View view) {
        DecimalFormat f = new DecimalFormat("#.##");
        if (spinner.getSelectedItemPosition() != 1) {
            Toast.makeText(getApplicationContext(),"Select the correct option to do this conversion. (Weight)", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            float inputFloat = Float.parseFloat(inputValue.getText().toString());
            output1.setText(f.format(inputFloat * 1000)  + " Grams");
            output2.setText(f.format(inputFloat * 35.274) + " Ounces");
            output3.setText(f.format(inputFloat * 2.205) + " Pounds");
        }catch (final NumberFormatException e) {
            Toast.makeText(getApplicationContext(),"Please enter a number!", Toast.LENGTH_SHORT).show();
        }
    }

    public void measurement(View view) {
        DecimalFormat f = new DecimalFormat("#.##");
        if (spinner.getSelectedItemPosition() != 2) {
            Toast.makeText(getApplicationContext(),"Select the correct option to do this conversion. (Measurement)", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            float inputFloat = Float.parseFloat(inputValue.getText().toString());
            output1.setText(f.format(inputFloat * 100) + " Centimeters");
            output2.setText(f.format(inputFloat * 3.281) + " Feet");
            output3.setText(f.format(inputFloat * 39.37) + " Inches");
        }catch (final NumberFormatException e) {
            Toast.makeText(getApplicationContext(),"Please enter a number!", Toast.LENGTH_SHORT).show();
        }
    }
}