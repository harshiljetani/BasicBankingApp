package com.sampleapps.basicbankingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransferAdapter extends RecyclerView.Adapter<TransferAdapter.ViewHolder> {

    private ArrayList<Transfer> transfers;

    public TransferAdapter(ArrayList<Transfer> transfers)
    {
        this.transfers=transfers;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.transfer_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Transfer transfer=transfers.get(position);
        holder.toUser.setText(transfer.getToUser());
        holder.fromUser.setText(transfer.getFromUser());
        holder.amount.setText(Double.toString(transfer.getAmount()));
        holder.dateTime.setText(transfer.getDateTime());
    }


    @Override
    public int getItemCount() {
        return transfers.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView fromUser,toUser;
        public TextView amount;
        public TextView dateTime;

        public ViewHolder(View itemView)
        {
            super(itemView);
            this.fromUser=itemView.findViewById(R.id.fromUser);
            this.toUser=itemView.findViewById(R.id.toUser);
            this.amount=itemView.findViewById(R.id.transferAmount);
            this.dateTime=itemView.findViewById(R.id.dateTime);
        }
    }
}
