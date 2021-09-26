package com.infinity.mad_project01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.RateViewHolder> {

    Context context;
    ArrayList<PaymentComplete>RateArrayList;

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
    public void onBindViewHolder(@NonNull RateAdapter.RateViewHolder holder, int position) {

        PaymentComplete paymentComplete=RateArrayList.get(position);

        holder.ItemName.setText(paymentComplete.productName);
        holder.Description.setText(paymentComplete.productDescription);

    }

    @Override
    public int getItemCount() {
        return RateArrayList.size();
    }

    public static class RateViewHolder extends RecyclerView.ViewHolder{

        TextView ItemName, Description;

        public RateViewHolder(@NonNull View itemView) {
            super(itemView);
            ItemName = itemView.findViewById(R.id.tvItemName);
            Description =itemView.findViewById(R.id.tvDescription);
        }
    }
}
