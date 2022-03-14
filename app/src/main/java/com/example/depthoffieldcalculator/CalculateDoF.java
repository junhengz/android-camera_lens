package com.example.depthoffieldcalculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.depthoffieldcalculator.model.DepthofFieldCalculator;
import com.example.depthoffieldcalculator.model.Lens;
import com.example.depthoffieldcalculator.model.LensManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Locale;

public class CalculateDoF extends AppCompatActivity {
    private LensManager lens;
    private static Lens len;

    public static Intent makeLaunchCalculator (Context c)
    {
        Intent intent = new Intent (c, CalculateDoF.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_do_f);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Calculate DoF");

        int selected_len = getIntent().getIntExtra("selected_lens",-1);
        if (selected_len == -1)
        {
            String newMake = getIntent().getStringExtra("addLensMake");
            int newFL = getIntent().getIntExtra("addLensFL",0);
            double newAperture = getIntent().getDoubleExtra("addLensAperture",0.0);
            len = new Lens(newMake,newAperture,newFL);
        }
        else {
            lens = lens.getInstance();
            len = lens.get_len(selected_len);
        }
        TextView len_select = findViewById(R.id.len_select);
        len_select.setText(String.format(len.lentoString()));




        Button calculate = (Button) findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText COC = (EditText) findViewById(R.id.value_COC);
                double coc = Double.parseDouble(COC.getText().toString());
                EditText Distance = (EditText) findViewById(R.id.value_distance);
                double distance = Double.parseDouble(Distance.getText().toString());
                EditText Aperture = (EditText) findViewById(R.id.value_aperture);
                double aperture = Double.parseDouble(Aperture.getText().toString());

                if (coc <= 0) {
                    AlertDialog.Builder message = new AlertDialog.Builder(CalculateDoF.this);
                    message.setTitle("Invalid Circle of Confusion");
                    message.setMessage("The Circle of Confusion value should greater than 0");
                    message.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    message.show();
                } else if (distance <= 0) {
                    AlertDialog.Builder message = new AlertDialog.Builder(CalculateDoF.this);
                    message.setTitle("Invalid Distance to Subject");
                    message.setMessage("The Distance to Subject value should greater than 0");
                    message.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    message.show();
                } else if (aperture < 1.4) {
                    AlertDialog.Builder message = new AlertDialog.Builder(CalculateDoF.this);
                    message.setTitle("Invalid Aperture");
                    message.setMessage("The Aperture value should greater or equal to 1.4");
                    message.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    message.show();
                } else {
                    if (aperture < len.get_max_aperture()) {
                        TextView result1 = findViewById(R.id.result_NFD);
                        result1.setText(String.format("Invalid aperture"));
                        TextView result2 = findViewById(R.id.result_FFD);
                        result2.setText(String.format("Invalid aperture"));
                        TextView result3 = findViewById(R.id.result_dof);
                        result3.setText(String.format("Invalid aperture"));
                        TextView result4 = findViewById(R.id.result_hd);
                        result4.setText(String.format("Invalid aperture"));
                    } else {
                        DepthofFieldCalculator calculator = new DepthofFieldCalculator();
                        double i = calculator.In_focus_near(len, aperture, coc, distance);
                        calculator.In_focus_far(len, aperture, coc, distance);
                        calculator.DoF(len, aperture, coc, distance);
                        calculator.Hyperfocal(len, aperture, coc);

                        TextView result1 = findViewById(R.id.result_NFD);
                        result1.setText(String.format(formatM(calculator.getIn_focus_near()) + "m"));
                        TextView result2 = findViewById(R.id.result_FFD);
                        result2.setText(String.format(formatM(calculator.getIn_focus_far()) + "m"));
                        TextView result3 = findViewById(R.id.result_dof);
                        result3.setText(String.format(formatM(calculator.getDoF()) + "m"));
                        TextView result4 = findViewById(R.id.result_hd);
                        result4.setText(String.format(formatM(calculator.getHyperfocal()) + "m"));
                    }
                }
            }
        });




    }
    public String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(distanceInM);
    }
}
