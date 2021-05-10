package com.sampleapps.basicbankingapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelperClass extends SQLiteOpenHelper {

    public static final String SQL_USER_TABLE="CREATE TABLE IF NOT EXISTS "+ Contract.USER_TABLE_NAME + "(" +
//            Contract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Contract.USER_NAME + " TEXT PRIMARY KEY," +
            Contract.EMAIL + " TEXT," +
            Contract.CURRENT_BALANCE + " TEXT)";

    public static final String SQL_TRANSFER_TABLE="CREATE TABLE IF NOT EXISTS "+ Contract.TRANSFER_TABLE_NAME + "(" +
            Contract.TRANSFER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Contract.FROM_USER + " TEXT," +
            Contract.TO_USER + " TEXT," +
            Contract.DATE + " TEXT," +
            Contract.AMOUNT + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_USER_TABLE="DROP TABLE IF EXISTS "+Contract.USER_TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES_TRANSFER_TABLE="DROP TABLE IF EXISTS "+Contract.USER_TABLE_NAME;

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="bank.db";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_USER_TABLE);
        db.execSQL(SQL_TRANSFER_TABLE);
    }

    public HelperClass(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_TRANSFER_TABLE);
        db.execSQL(SQL_DELETE_ENTRIES_USER_TABLE);
        onCreate(db);
    }

    public boolean insertUser(String name, String email,String currentBalance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.USER_NAME, name);
        contentValues.put(Contract.EMAIL, email);
        contentValues.put(Contract.CURRENT_BALANCE,currentBalance);
        db.insert(Contract.USER_TABLE_NAME, null, contentValues);
        return true;
    }
    public boolean insertTransaction(String from, String to,String amount,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.FROM_USER, from);
        contentValues.put(Contract.TO_USER, to);
        contentValues.put(Contract.AMOUNT,amount);
        contentValues.put(Contract.DATE,date);
        db.insert(Contract.TRANSFER_TABLE_NAME, null, contentValues);
        return true;
    }

    public  boolean updateUserTable(String fromUser,String toUser,String fromUserAmount,String toUserAmount)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        db.execSQL("UPDATE " + Contract.USER_TABLE_NAME +
                " SET "+Contract.CURRENT_BALANCE + " = " + "'" + fromUserAmount +"'" +
                " WHERE "+ Contract.USER_NAME + " = " + "'"+ fromUser +"'");
        db.execSQL("UPDATE " + Contract.USER_TABLE_NAME +
                " SET "+Contract.CURRENT_BALANCE + " = " + "'" + toUserAmount +"'" +
                " WHERE "+ Contract.USER_NAME + " = " + "'"+ toUser +"'");
        return true;
    }
}
