package com.sampleapps.basicbankingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.sampleapps.basicbankingapp.data.Contract;
import com.sampleapps.basicbankingapp.data.HelperClass;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String countQuery = "SELECT  * FROM " + Contract.USER_TABLE_NAME;
        HelperClass helperClass=new HelperClass(this);
        SQLiteDatabase db = helperClass.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        if (count==0) {
            ArrayList<User> users = new ArrayList<>();
            users.add(new User("User0", "user0@domain.com", 5500,this));
            users.add(new User("User1", "user1@domain.com", 10000,this));
            users.add(new User("User2", "user2@domain.com", 15000,this));
            users.add(new User("User3", "user3@domain.com", 14500,this));
            users.add(new User("User4", "user4@domain.com", 12000,this));
            users.add(new User("User5", "user5@domain.com", 9000,this));
            users.add(new User("User6", "user6@domain.com", 17300,this));
            users.add(new User("User7", "user7@domain.com", 10200,this));
            users.add(new User("User8", "user8@domain.com", 13900,this));
            users.add(new User("User9", "user9@domain.com", 7300,this));


            for (User i:users)
            {
                helperClass.insertUser(i.getName(),i.getEmail(),Double.toString(i.getCurrentBalance()));
            }
        }

//        RecyclerView recyclerView=findViewById(R.id.recyclerVeiw);
//        UserAdapter userAdapter=new UserAdapter(users);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(userAdapter);

//        ArrayList<Transfer> transfers = new ArrayList<>();
//        transfers.add(new Transfer(users.get(0),users.get(1),100.00,"date/time"));
//        transfers.add(new Transfer(users.get(1),users.get(4),100.00,"date/time"));
//        transfers.add(new Transfer(users.get(4),users.get(1),100.00,"date/time"));
//        transfers.add(new Transfer(users.get(3),users.get(2),100.00,"date/time"));
//        transfers.add(new Transfer(users.get(9),users.get(8),100.00,"date/time"));
//
//        TransferAdapter transferAdapter=new TransferAdapter(transfers);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(transferAdapter);
    }

    public void displayUsers(View view)
    {
        Intent intent=new Intent(MainActivity.this,UserListActivity.class);
        startActivity(intent);
    }
    public void displayTransactions(View view)
    {
        Intent intent=new Intent(MainActivity.this,TransactionListActivity.class);
        intent.putExtra("for user","all");
        startActivity(intent);
    }

}