package com.example.healthy;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Recipe extends Activity implements
        AdapterView.OnItemSelectedListener {
    EditText recipe, instr, ingre;
    Spinner cat;
    Button add;
    String item;
    String[] category = {"", "Bread", "vegitables", "Fruits", "Soup"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);
        cat = (Spinner) findViewById(R.id.dropCategory);
        add = (Button) findViewById(R.id.btnAdd);
        recipe = (EditText) findViewById(R.id.txtRecipe);
        instr = (EditText) findViewById(R.id.txtInstructions);
        ingre = (EditText) findViewById(R.id.txtngredients);


        cat.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list  
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, category);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner  
        cat.setAdapter(aa);


        final Data db = new Data(this);
        //SQLiteDatabase db = dbhelper.getWritableDatabase();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rec, ins, ing, categ;
                rec = recipe.getText().toString();
                ins = instr.getText().toString();
                ing = ingre.getText().toString();
                categ = item;
                try {

                    db.AddRecord(rec, categ, ing, ins);
                    Toast.makeText(getApplicationContext(), "Record Saved successfully.",
                            Toast.LENGTH_LONG).show();
                    clear();
                    Intent i = new Intent(getApplicationContext(), Order.class);
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "An error occured!\n" + e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                    e.printStackTrace();
                }
            }
        });
    }

    //Performing action onItemSelected and onNothing selected
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        item = category[position];
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub  

    }

    public void clear() {
        recipe.setText("");
        instr.setText("");
        ingre.setText("");
        cat.setSelection(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.view) {

            Intent i = new Intent(getApplicationContext(), Order.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
