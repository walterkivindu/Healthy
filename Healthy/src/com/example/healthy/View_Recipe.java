package com.example.healthy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class View_Recipe extends Activity {
    private Handler handler = new Handler();
    Data dbhelper = new Data(this);
    Button pre, next;
    private EditText rec, cate, ingre, inst;
    private ProgressDialog pDialog;
    int position, max, count = 0;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_recipe);
        pre = (Button) findViewById(R.id.button1);
        next = (Button) findViewById(R.id.button2);
        rec = (EditText) findViewById(R.id.editText1);
        cate = (EditText) findViewById(R.id.editText2);
        ingre = (EditText) findViewById(R.id.editText3);
        inst = (EditText) findViewById(R.id.editText4);
        category = this.getIntent().getStringExtra("cat");
        setTitle(category + " Recipe");
        next.setBackgroundResource(R.drawable.arrow_right);
        pre.setBackgroundResource(R.drawable.arrow_left);
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 0) {
                    count--;
                    position = count;
                    setPosition(position);
                    next.setEnabled(true);

                    if (count == 0) {
                        pre.setEnabled(false);
                        //pre.setBackgroundColor(Color.RED);

                    }
                    move();
                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count <= max - 1) {
                    count++;
                    position = count;
                    setPosition(position);
                    pre.setEnabled(true);
                    //
                    if (count == max - 1) {
                        next.setEnabled(false);
                        //next.setBackgroundColor(Color.RED);
                    }
                    move();
                }

            }
        });

        rec.setEnabled(false);
        cate.setEnabled(false);
        ingre.setEnabled(false);
        inst.setEnabled(false);
        rec.setFocusable(false);
        cate.setFocusable(false);
        ingre.setFocusable(false);
        inst.setFocusable(false);
    }


    public void startThread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {

                    sleep(1);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    public void run() {
                        new ShowRecipe().execute();

                    }
                });
            }
        };

        thread.start();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        String category = this.getIntent().getStringExtra("cat");
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor account = db.query("recipe", null, "cat = ?",
                new String[]{category}, null, null, null);
        max = account.getCount();
        if (max == 0)
            next.setEnabled(false);
        // startManagingCursor(accounts);
        if (account.moveToFirst()) {
            // update view
            rec.setText(account.getString(account.getColumnIndex("rname")));
            cate.setText(account.getString(account.getColumnIndex("cat")));
            ingre.setText(account.getString(account.getColumnIndex("ingre")));
            inst.setText(account.getString(account.getColumnIndex("instr")));
        }
        account.close();
        db.close();
        dbhelper.close();
        pre.setEnabled(false);
    }

    public void setPosition(int pos) {
        position = pos;
    }

    public int getPosition() {
        return position;
    }

    public void move() {
        startThread();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try {
            Cursor account = db.query("recipe", null, "cat = ?",
                    new String[]{category}, null, null, null);
            int pos = getPosition();
            if (account.moveToPosition(pos)) {

                rec.setText(account.getString(account
                        .getColumnIndex("rname")));
                cate.setText(account.getString(account
                        .getColumnIndex("cat")));
                ingre.setText(account.getString(account
                        .getColumnIndex("ingre")));
                inst.setText(account.getString(account
                        .getColumnIndex("instr")));
                if (account.isLast())
                    next.setEnabled(false);

            }
            account.close();
            db.close();
            dbhelper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    class ShowRecipe extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(View_Recipe.this);
            pDialog.setMessage("Loading ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.setProgress(0);
            pDialog.setMax(100);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... args) {
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            try {
                Cursor account = db.query("recipe", null, "cat = ?",
                        new String[]{category}, null, null, null);
                //int pos=getPosition();
                if (account.moveToFirst()) {

                    rec.setText(account.getString(account
                            .getColumnIndex("rname")));
                    cate.setText(account.getString(account
                            .getColumnIndex("cat")));
                    ingre.setText(account.getString(account
                            .getColumnIndex("ingre")));
                    inst.setText(account.getString(account
                            .getColumnIndex("instr")));
                    if (account.isLast())
                        next.setEnabled(false);

                }
                account.close();
                db.close();
                dbhelper.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
        }
    }

}
