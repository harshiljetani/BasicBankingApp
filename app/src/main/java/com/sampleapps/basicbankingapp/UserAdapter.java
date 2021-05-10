package com.sampleapps.basicbankingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private ArrayList<User> users;

    public UserAdapter(ArrayList<User> users)
    {
        this.users=users;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.user_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user=users.get(position);
        holder.username.setText(user.getName());
        holder.email.setText(user.getEmail());
        holder.currentBalance.setText(Double.toString(user.getCurrentBalance()));
        holder.displayUserConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(user.getContext(),UserDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("name",user.getName());
                bundle.putString("email",user.getEmail());
                bundle.putDouble("currentBalance",user.getCurrentBalance());
//                bundle.putParcelableArrayList("user list",users);
//                intent.putExtra("User", user);
                intent.putExtras(bundle);
                user.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public TextView email;
        public TextView currentBalance;
        public ConstraintLayout displayUserConstraintLayout;

        public ViewHolder(View itemView)
        {
            super(itemView);
            this.username=itemView.findViewById(R.id.username);
            this.email=itemView.findViewById(R.id.email);
            this.currentBalance=itemView.findViewById(R.id.currentBalance);
            this.displayUserConstraintLayout=itemView.findViewById(R.id.displayUserConstraintLayout);
        }
    }

}

