package com.sampleapps.basicbankingapp;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class User {
    private String name;
    private String email;
    private double currentBalance;
    private Context context;

    public User(String name, String email, double currentBalance, Context context)
    {
        this.name=name;
        this.email=email;
        this.currentBalance=currentBalance;
        this.context=context;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public Context getContext() {
        return context;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setCurrentBalance(float currentBalance) {
        this.currentBalance = currentBalance;
    }

}
