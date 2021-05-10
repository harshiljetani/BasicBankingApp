package com.sampleapps.basicbankingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sampleapps.basicbankingapp.data.Contract;
import com.sampleapps.basicbankingapp.data.HelperClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

public class TransactionListActivity extends AppCompatActivity {

    HelperClass dbHelper;
    SQLiteDatabase db;

    String transType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        getSupportActionBar().setTitle("Transactions");
        Intent intent=getIntent();
        transType=intent.getStringExtra("for user");

        dbHelper=new HelperClass(getApplicationContext());
        db=dbHelper.getReadableDatabase();

        updateScreen();
    }

    public void updateScreen()
    {
        ArrayList<Transfer> transfers = new ArrayList<>();

        if (transType.equals("all"))
        {
            Cursor cursor = db.rawQuery("SELECT * FROM " + Contract.TRANSFER_TABLE_NAME, null);
            int fromColumn = cursor.getColumnIndex(Contract.FROM_USER);
            int toColumn = cursor.getColumnIndex(Contract.TO_USER);
            int amountColumn = cursor.getColumnIndex(Contract.AMOUNT);
            int dateColumn = cursor.getColumnIndex(Contract.DATE);

            while (cursor.moveToNext()) {
                String from = cursor.getString(fromColumn);
                String to = cursor.getString(toColumn);
                String amount = cursor.getString(amountColumn);
                String date = cursor.getString(dateColumn);

                transfers.add(new Transfer(from, to, Double.parseDouble(amount), date));
            }
        }
        else
        {
            Cursor cursor = db.rawQuery("SELECT * FROM " + Contract.TRANSFER_TABLE_NAME +
                    " WHERE "+ Contract.FROM_USER +" = " + "'"+ transType + "' OR "
                    + Contract.TO_USER + " = " + "'"+ transType +"'"
                    , null);
            int fromColumn = cursor.getColumnIndex(Contract.FROM_USER);
            int toColumn = cursor.getColumnIndex(Contract.TO_USER);
            int amountColumn = cursor.getColumnIndex(Contract.AMOUNT);
            int dateColumn = cursor.getColumnIndex(Contract.DATE);

            while (cursor.moveToNext()) {
                String from = cursor.getString(fromColumn);
                String to = cursor.getString(toColumn);
                String amount = cursor.getString(amountColumn);
                String date = cursor.getString(dateColumn);

                transfers.add(new Transfer(from, to, Double.parseDouble(amount), date));
            }
        }

        Collections.reverse(transfers);
        TextView nothingToDisplay=findViewById(R.id.nothingToDisplayTextView);

        if (transfers.size()==0)
        {
            nothingToDisplay.setVisibility(View.VISIBLE);
        }
        else
        {
            nothingToDisplay.setVisibility(View.GONE);
        }

        RecyclerView recyclerView=findViewById(R.id.transactionRecyclerView);
        TransferAdapter userAdapter=new TransferAdapter(transfers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clear_option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (transType.equals("all"))
        {
            db.execSQL("DELETE FROM "+ Contract.TRANSFER_TABLE_NAME);
        }
        else
        {
            db.delete(Contract.TRANSFER_TABLE_NAME,Contract.FROM_USER + " = " + "'"+ transType +"'" + " OR " + Contract.TO_USER + " = " + "'"+ transType +"'",null);
        }

        updateScreen();
        return true;
    }
}