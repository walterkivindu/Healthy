package com.example.healthy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateOrder extends Activity {
    private ProgressDialog pDialog;
    private String accountId;
    private EditText editAcno, editCno, editFname, editStation;
    ProgressDialog prgDialog;
    Button update, delete;
    Data dbhelper = new Data(this);
    Thread thread;
    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_order);
        editAcno = (EditText) this.findViewById(R.id.editAcno);
        editCno = (EditText) this.findViewById(R.id.editCno);
        editFname = (EditText) this.findViewById(R.id.editFname);
        editStation = (EditText) this.findViewById(R.id.editStationName);
        thread = new Thread(runable);
        thread.start();
        editAcno.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                // code to execute when EditText loses focus
                if (!hasFocus) {
                    startThread();
                }
            }

        });
        editCno.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                // code to execute when EditText loses focus
                if (!hasFocus) {
                    startThread();
                }
            }

        });
        editFname.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                // code to execute when EditText loses focus
                if (!hasFocus) {
                    startThread();
                }
            }

        });
        editStation.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                // code to execute when EditText loses focus
                if (!hasFocus) {
                    startThread();
                }
            }

        });

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
                        new UpdateRecipe().execute();
                        Toast.makeText(UpdateOrder.this,
                                "Recipe updated Successfully!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        thread.start();
    }

    public Runnable runable = new Runnable() {
        public void run() {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {

                new UpdateRecipe().execute();
                Toast.makeText(UpdateOrder.this,
                        "Recipe updated Successfully!", Toast.LENGTH_SHORT)
                        .show();

            } catch (Exception e) {

            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        accountId = this.getIntent().getStringExtra("accountid");
        // sLog.d("Accounts", "Account Id : " + accountId);
        Data dbhelper = new Data(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor account = db.query("recipe", null, "_id = ?",
                new String[]{accountId}, null, null, null);
        // startManagingCursor(accounts);
        if (account.moveToFirst()) {
            // update view
            editAcno.setText(account.getString(account.getColumnIndex("rname")));
            editCno.setText(account.getString(account.getColumnIndex("cat")));
            editFname
                    .setText(account.getString(account.getColumnIndex("ingre")));
            editStation.setText(account.getString(account
                    .getColumnIndex("instr")));
        }
        account.close();
        db.close();
        dbhelper.close();

    }

    public void deleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this recipe?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // syncDelete();
                                deleteCurrentAccount();

                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void deleteCurrentAccount() {
        try {
            final Data dbhelper = new Data(this);
            final SQLiteDatabase db = dbhelper.getWritableDatabase();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    UpdateOrder.this);
            // Setting Dialog Title
            alertDialog.setTitle("Sure?");
            // Setting Dialog Message
            alertDialog.setMessage("Record will be deleted!");
            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.warning);
            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            dialog.cancel();
                        }
                    });

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            int rows = db.delete("recipe", "_id=?",
                                    new String[]{accountId});
                            dbhelper.close();
                            if (rows == 1) {
                                Toast.makeText(UpdateOrder.this,
                                        "recipe Deleted Successfully!",
                                        Toast.LENGTH_LONG).show();

                                finish();
                            } else
                                Toast.makeText(UpdateOrder.this,
                                        "Could not recipe!", Toast.LENGTH_LONG)
                                        .show();

                        }
                    });
            alertDialog.show();

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        // Display alert message when back button has been pressed
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                UpdateOrder.this);
        // Setting Dialog Title
        alertDialog.setTitle("Exit?");
        // Setting Dialog Message
        alertDialog.setMessage("Move out of this page?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.confirm);
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent;
                        intent = new Intent(getApplicationContext(),
                                Order.class);
                        startActivity(intent);
                        finish();

                        // System.exit(0);
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.update_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.del) {
            deleteAccount();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class UpdateRecipe extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateOrder.this);
            pDialog.setMessage("Saving ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.setProgress(0);
            pDialog.setMax(100);
            pDialog.show();
        }

        /**
         * Saving product
         */
        protected String doInBackground(String... args) {
            SQLiteDatabase db = dbhelper.getWritableDatabase();
            // getting updated data from EditTexts
            String name = editAcno.getText().toString();
            String category = editCno.getText().toString();
            String ingre = editFname.getText().toString();
            String instr = editStation.getText().toString();

            try {
                ContentValues values = new ContentValues();
                values.put("rname", name);
                values.put("cat", category);
                values.put("ingre", ingre);
                values.put("instr", instr);

                long rows = db.update("recipe", values, "_id = ?",
                        new String[]{accountId});

                db.close();

                if (rows > 0) {
                    Toast.makeText(UpdateOrder.this,
                            "Recipe updated Successfully!", Toast.LENGTH_LONG)
                            .show();
                    finish();
                } else {
                    // failed to update product
                }
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
