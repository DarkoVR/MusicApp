package com.example.marco.musicapp.api.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marco.musicapp.R;
import com.example.marco.musicapp.api.model.Discount;
import com.example.marco.musicapp.api.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 11/12/17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {

    Context context;
    List<Order> ordersList = new ArrayList<>();

    public OrderAdapter(Context context, List<Order> productList){
        this.context = context;
        this.ordersList = productList;
    }
    
    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_row, parent, false);
        OrderHolder productHolder = new OrderHolder(view);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, int position) {
        Order product = ordersList.get(position);
        holder.tvId.setText(product.getId() + "");
        holder.tvTotal.setText(product.getTotal() + "");
        holder.tvPayment.setText(product.getPayment() + "");
        holder.tvDate.setText(product.getDate());

    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    class OrderHolder extends RecyclerView.ViewHolder{

        TextView tvId,tvTotal,tvPayment,tvDate;
        public OrderHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_order_id);
            tvTotal = itemView.findViewById(R.id.tv_total);
            tvPayment = itemView.findViewById(R.id.tv_payment);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
}
