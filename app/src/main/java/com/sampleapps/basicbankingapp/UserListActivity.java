package com.sampleapps.basicbankingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;

import com.sampleapps.basicbankingapp.data.Contract;
import com.sampleapps.basicbankingapp.data.HelperClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class UserListActivity extends AppCompatActivity {


    HelperClass dbHelper;
    SQLiteDatabase db;
    ArrayList<User> users;

    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            finish();
            onRestart();
//            updateScreen();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        registerReceiver(broadcastReceiver, new IntentFilter("closeActivity"));

        getSupportActionBar().setTitle("Users");

        users = new ArrayList<>();
//        users.add(new User("User0","user0@domain.com",5500));
//        users.add(new User("User1","user1@domain.com",10000));
//        users.add(new User("User2","user2@domain.com",15000));
//        users.add(new User("User3","user3@domain.com",14500));
//        users.add(new User("User4","user4@domain.com",12000));
//        users.add(new User("User5","user5@domain.com",9000));
//        users.add(new User("User6","user6@domain.com",17300));
//        users.add(new User("User7","user7@domain.com",10200));
//        users.add(new User("User8","user8@domain.com",13900));
//        users.add(new User("User9","user9@domain.com",7300));

//
        dbHelper=new HelperClass(getApplicationContext());
        db=dbHelper.getReadableDatabase();
////        DateFormat datetime = new SimpleDateFormat("hh:mm a, dd/MMM/yyyy");
//
//        Iterator itr= users.iterator();
//        while(itr.hasNext())
//        {
//            User currentUser=(User)itr.next();
//
////            String InsertQuery = "INSERT INTO " + Contract.USER_TABLE_NAME + " VALUES(" +
////                    "'" + currentUser.getName() + "'," +
////                    "'" + currentUser.getEmail() + "'," +
////                    "'" + currentUser.getCurrentBalance() + "')";
////            db.execSQL(InsertQuery);
//            HelperClass helper = new HelperClass(this);
//            helper.insert(currentUser.getName(),currentUser.getEmail(),Double.toString(currentUser.getCurrentBalance()));
//
//        }

//        dbHelper.insert("user10","user10@domain.com","4500");
//        users.clear();

        updateScreen();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateScreen();
    }

    public void updateScreen()
    {
        users.clear();
        Cursor usercursor=db.rawQuery("SELECT * FROM "+Contract.USER_TABLE_NAME,null);
        int usernameColumn=usercursor.getColumnIndex(Contract.USER_NAME);
        int emailColumn=usercursor.getColumnIndex(Contract.EMAIL);
        int currentBalanceColumn=usercursor.getColumnIndex(Contract.CURRENT_BALANCE);

        while(usercursor.moveToNext())
        {
            String username=usercursor.getString(usernameColumn);
            String email=usercursor.getString(emailColumn);
            String currentBalance=usercursor.getString(currentBalanceColumn);

            users.add(new User(username,email,Double.parseDouble(currentBalance),this));
        }

        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        UserAdapter userAdapter=new UserAdapter(users);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);
    }

}