package com.sampleapps.basicbankingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.sampleapps.basicbankingapp.data.Contract;
import com.sampleapps.basicbankingapp.data.HelperClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserDetailsActivity extends AppCompatActivity {

    String name,email;
//    ArrayList<User> users;
    double currentBalance;

    TextView currentBalanceTextView;
    Button viewTransaction;

    Spinner sendtoSpinner;

    HelperClass dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);


        getSupportActionBar().setTitle("User Details");
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);




        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        name=bundle.getString("name");
        email=bundle.getString("email");
        currentBalance=bundle.getDouble("currentBalance");

        viewTransaction = findViewById(R.id.ViewTransactionButton);
        viewTransaction.setText("View Transactions by "+name);
//        users=bundle.getParcelableArrayList("user list");

        ArrayList<String> usersList=new ArrayList<>();

        dbHelper=new HelperClass(this);
        Cursor c=dbHelper.getReadableDatabase().rawQuery("SELECT "+ Contract.USER_NAME+" FROM "+ Contract.USER_TABLE_NAME,null);
        while(c.moveToNext())
        {
            usersList.add(c.getString(0));
        }
        c.close();
        usersList.remove(name);

//        for (User i:users)
//        {
//            if (!i.getName().equals(name))
//            {
//                usersList.add(i.getName());
//            }
//        }

        sendtoSpinner=findViewById(R.id.editTextSendTo);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, usersList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sendtoSpinner.setAdapter(dataAdapter);
//        sendtoEditText.setAdapter(new ArrayAdapter<String>(UserDetailsActivity.this, android.R.layout.simple_expandable_list_item_1,usersList));

        TextView usernameTextView=findViewById(R.id.usernameTextView);
        usernameTextView.setText(name);

        TextView emailTextView=findViewById(R.id.emailTextView);
        emailTextView.setText(email);

        currentBalanceTextView=findViewById(R.id.currentBalanceTextView);
        currentBalanceTextView.setText(Double.toString(currentBalance));


    }

    public void sendClicked(View view)
    {
        String toUserName=String.valueOf(sendtoSpinner.getSelectedItem());
        if (toUserName.equals(name))
        {
            new AlertDialog.Builder(this).setTitle("User not Allowed")
                    .setMessage("Cannot transfer to selected user!").show();
        }
        else
        {
            EditText amount = findViewById(R.id.editTextAmount);
            double amt = Double.parseDouble(amount.getText().toString());
            if (currentBalance<amt)
            {
                new AlertDialog.Builder(this).setTitle("Insufficient Balance")
                        .setMessage("You don't have enough balance to complete the transaction!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
            else
            {
                String fromUserAmount = Double.toString(currentBalance - amt);
                Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT " + Contract.CURRENT_BALANCE + " FROM " +
                        Contract.USER_TABLE_NAME + " WHERE " + Contract.USER_NAME + " = " + "'" + toUserName + "'", null);
                c.moveToNext();
                String toUserAmount = Double.toString(Double.parseDouble(c.getString(0)) + amt);
                c.close();
                dbHelper.updateUserTable(name, toUserName, fromUserAmount, toUserAmount);
                currentBalance = Double.parseDouble(fromUserAmount);
                currentBalanceTextView.setText(fromUserAmount);
                dbHelper.insertTransaction(name, toUserName, Double.toString(amt), new SimpleDateFormat("hh:mm a, dd MMM, yyyy").format(new Date()));

                Intent broadcastIntent=new Intent("closeActivity");
                sendBroadcast(broadcastIntent);

                new AlertDialog.Builder(this).setTitle("Transaction Successful")
                        .setMessage(amt + " Transferred to " + toUserName + " Successfully!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                amount.getText().clear();
                                amount.clearFocus();

                                finish();
                            }
                        })
                        .show();
            }
        }
    }

    public void viewTransactionClicked(View view)
    {
        Intent intent=new Intent(UserDetailsActivity.this,TransactionListActivity.class);
        intent.putExtra("for user",name);
        startActivity(intent);
    }

}