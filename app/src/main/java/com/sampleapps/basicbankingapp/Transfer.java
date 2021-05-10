package com.sampleapps.basicbankingapp;

public class Transfer {

    private String fromUser;
    private String toUser;
    private double amount;
    String dateTime;

    public Transfer(String fromUser,String toUser, double amount, String dateTime)
    {
        this.fromUser=fromUser;
        this.toUser=toUser;
        this.amount=amount;
        this.dateTime=dateTime;
    }

    public double getAmount() {
        return amount;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getFromUser() {
        return fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
}
