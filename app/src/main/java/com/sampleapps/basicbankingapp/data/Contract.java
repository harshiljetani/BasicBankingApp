package com.sampleapps.basicbankingapp.data;

import android.provider.BaseColumns;

public class Contract implements BaseColumns {

    public static final String USER_TABLE_NAME="Users",
    _ID=BaseColumns._ID,
    USER_NAME="username",
    EMAIL="email",
    CURRENT_BALANCE="currentbalance";

    public static final String TRANSFER_TABLE_NAME="transactions",
    TRANSFER_ID=BaseColumns._ID,
    FROM_USER="fromuser",
    TO_USER="touser",
    AMOUNT="amount",
    DATE="datetime";

}
