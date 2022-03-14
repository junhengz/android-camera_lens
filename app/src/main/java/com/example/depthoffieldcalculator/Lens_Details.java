package com.example.depthoffieldcalculator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Lens_Details extends AppCompatActivity {

    public static Intent makeLaunchNewLen(Context c) {
        Intent intent = new Intent(c, Lens_Details.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lens__details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Lens Details");

        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText Make = (EditText) findViewById(R.id.add_len_make);
                String make = Make.getText().toString();
                EditText FL = (EditText) findViewById(R.id.add_len_FL);
                int fl = Integer.parseInt(FL.getText().toString());
                EditText Max_Aperture = (EditText) findViewById(R.id.add_len_aperture);
                double aperture = Double.parseDouble(Max_Aperture.getText().toString());

                float aper = (float)aperture;
                saveLens(make,fl,aper);

                if (make.length()==0)
                {
                    AlertDialog.Builder message = new AlertDialog.Builder(Lens_Details.this);
                    message.setTitle("Invalid Make Name");
                    message.setMessage("Please enter the make name correctly");
                    message.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    message.show();
                }
                else if (fl<=0)
                {
                    AlertDialog.Builder message = new AlertDialog.Builder(Lens_Details.this);
                    message.setTitle("Invalid Focal Length");
                    message.setMessage("The focal length value should greater than 0");
                    message.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    message.show();
                }
                else if (aperture<1.4)
                {
                    AlertDialog.Builder message = new AlertDialog.Builder(Lens_Details.this);
                    message.setTitle("Invalid Aperture");
                    message.setMessage("The aperture value should greater or equal to 1.4");
                    message.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });
                    message.show();
                }
                else{
                Intent i = CalculateDoF.makeLaunchCalculator(Lens_Details.this);
                i.putExtra("addLensMake", make);
                i.putExtra("addLensFL", fl);
                i.putExtra("addLensAperture", aperture);
                startActivity(i);}
            }
        });


        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void saveLens(String make, int fl, float aperture)
    {
        SharedPreferences saveData = getSharedPreferences("preference",MODE_PRIVATE);
        SharedPreferences.Editor editor = saveData.edit();

        editor.putString("make",make);
        editor.putInt("fl",fl);
        editor.putFloat("aperture", aperture);

        editor.apply();
    }

}
