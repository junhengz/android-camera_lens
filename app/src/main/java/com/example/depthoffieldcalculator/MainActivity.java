package com.example.depthoffieldcalculator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.depthoffieldcalculator.model.Lens;
import com.example.depthoffieldcalculator.model.LensManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private LensManager lenses;

    public static Intent reload(Context c) {
        Intent intent = new Intent(c, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SharedPreferences saveData = getSharedPreferences("preference",MODE_PRIVATE);
        String make = saveData.getString("make","none");
        int fl = saveData.getInt("fl",0);
        float aperturef = saveData.getFloat("aperture",0);

        Button len_1 = (Button) findViewById(R.id.len_1);
        Button len_2 = (Button) findViewById(R.id.len_2);
        Button len_3 = (Button) findViewById(R.id.len_3);
        Button len_4 = (Button) findViewById(R.id.len_4);
        final Button delete = (Button) findViewById(R.id.delete1);
        final Button edit = (Button) findViewById(R.id.edit);


        //setup some default values for lenses
        lenses = LensManager.getInstance();

        if (fl==-1)// without any lenses
        {
            len_1.setVisibility(View.INVISIBLE);
            len_2.setVisibility(View.INVISIBLE);
            len_3.setVisibility(View.INVISIBLE);
            len_4.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);

            AlertDialog.Builder message = new AlertDialog.Builder(MainActivity.this);
            message.setTitle("No lenses installed");
            message.setMessage("Please start with using default lenses or adding len profile");
            message.setPositiveButton("Use Default lenses", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor editor = saveData.edit();
                    editor.putString("make","none");
                    editor.putInt("fl",0);
                    editor.putFloat("aperture", 0);

                    editor.apply();

                    Intent i = MainActivity.reload(MainActivity.this);
                    finish();
                    startActivity(i);
                }
            });
            message.setNegativeButton("Add len", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = Lens_Details.makeLaunchNewLen(MainActivity.this);
                    startActivity(i);
                }
            });
            message.show();
        }

        else if(make=="none"||fl==0|| (double)aperturef ==0.00) { // set the default len
            lenses.add(new Lens("Canon", 1.8, 50));
            lenses.add(new Lens("Tamron", 2.8, 90));
            lenses.add(new Lens("Sigma", 2.8, 200));
            lenses.add(new Lens("Nikon", 4, 200));

            delete.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);

            len_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = CalculateDoF.makeLaunchCalculator(MainActivity.this);
                    i.putExtra("selected_lens", 0);
                    startActivity(i);
                }
            });


            len_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = CalculateDoF.makeLaunchCalculator(MainActivity.this);
                    i.putExtra("selected_lens", 1);
                    startActivity(i);
                }
            });


            len_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = CalculateDoF.makeLaunchCalculator(MainActivity.this);
                    i.putExtra("selected_lens", 2);
                    startActivity(i);
                }
            });


            len_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = CalculateDoF.makeLaunchCalculator(MainActivity.this);
                    i.putExtra("selected_lens", 3);
                    startActivity(i);
                }
            });

            len_selection();

        }
        else{//set the new len
            lenses.add(new Lens(make, (double)aperturef, fl));
            delete.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);


            len_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = CalculateDoF.makeLaunchCalculator(MainActivity.this);
                    i.putExtra("selected_lens", 0);
                    startActivity(i);
                }
            });
            TextView len_1_text = findViewById(R.id.len_1);
            len_1_text.setText(String.format(lenses.get_len(0).lentoString()));

            len_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            len_2.setVisibility(View.INVISIBLE);

            len_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            len_3.setVisibility(View.INVISIBLE);
            len_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            len_4.setVisibility(View.INVISIBLE);


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = Lens_Details.makeLaunchNewLen(MainActivity.this);
                    startActivity(i);
                }
                });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = saveData.edit();

                    editor.putString("make","none");
                    editor.putInt("fl",-1);
                    editor.putFloat("aperture", -1);

                    editor.apply();

                    LensManager.reconstruct();

                    Intent i = MainActivity.reload(MainActivity.this);
                    finish();
                    startActivity(i);
                    delete.setVisibility(View.INVISIBLE);
                    edit.setVisibility(View.INVISIBLE);
                }
            });


        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = Lens_Details.makeLaunchNewLen(MainActivity.this);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void len_selection()
    {
        TextView len_1 = findViewById(R.id.len_1);
        len_1.setText(String.format(lenses.get_len(0).lentoString()));
        TextView len_2 = findViewById(R.id.len_2);
        len_2.setText(String.format(lenses.get_len(1).lentoString()));
        TextView len_3 = findViewById(R.id.len_3);
        len_3.setText(String.format(lenses.get_len(2).lentoString()));
        TextView len_4 = findViewById(R.id.len_4);
        len_4.setText(String.format(lenses.get_len(3).lentoString()));

    }
}
