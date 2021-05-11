package com.example.healthy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class Order extends Activity {

    ListView listAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);


        listAccounts = (ListView) this.findViewById(R.id.listAccounts);
        listAccounts.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View selectedView, int arg2, long arg3) {
                TextView textAccountId = (TextView) selectedView.findViewById(R.id.txtId);
                //Log.d("Accounts", "Selected Account Id : " + textAccountId.getText().toString());
                Intent intent = new Intent(Order.this, UpdateOrder.class);
                intent.putExtra("accountid", textAccountId.getText().toString());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Order.this);
            Data dbhelper = new Data(this);
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor accounts = db.query(false, "recipe", null, null, null, null, null, null, null);

            String from[] = {"_id", "rname", "cat", "ingre", "instr"};
            int to[] = {R.id.txtId, R.id.tv_name, R.id.tv_email, R.id.tv_phone, R.id.place};

            @SuppressWarnings("deprecation")
            SimpleCursorAdapter ca = new SimpleCursorAdapter(this, R.layout.order_list, accounts, from, to);

            ListView listAccounts = (ListView) this.findViewById(R.id.listAccounts);
            listAccounts.setAdapter(ca);
            dbhelper.close();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


}
