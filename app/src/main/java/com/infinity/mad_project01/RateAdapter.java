package com.infinity.mad_project01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.RateViewHolder> {

    Context context;
    ArrayList<PaymentComplete>RateArrayList;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public RateAdapter(Context context, ArrayList<PaymentComplete> rateArrayList) {
        this.context = context;
        RateArrayList = rateArrayList;
    }

    @NonNull
    @Override
    public RateAdapter.RateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.pay_completed_item,parent,false);


        return new RateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RateAdapter.RateViewHolder holder, @SuppressLint("RecyclerView") int position) {

        PaymentComplete paymentComplete=RateArrayList.get(position);

        holder.ItemName.setText(paymentComplete.productName);
        holder.Description.setText(paymentComplete.productDescription);

        holder.RateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,AddRateMainActivity.class);
                intent.putExtra("detail",RateArrayList.get(position));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return RateArrayList.size();
    }

    public static class RateViewHolder extends RecyclerView.ViewHolder{

        TextView ItemName, Description;

        Button RateBtn;

        public RateViewHolder(@NonNull View itemView) {
            super(itemView);
            ItemName = itemView.findViewById(R.id.tvItemName);
            Description =itemView.findViewById(R.id.tvDescription);

            RateBtn = itemView.findViewById(R.id.RateBtn);
        }
    }
}
