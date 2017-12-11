package com.example.marco.musicapp.api.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marco.musicapp.R;
import com.example.marco.musicapp.api.model.Order;
import com.example.marco.musicapp.api.model.Order_detail;
import com.example.marco.musicapp.api.model.ShoppingCart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 11/12/17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.OrderHolder> {

    Context context;
    List<ShoppingCart> ordersList = new ArrayList<ShoppingCart>();

    public CartAdapter(Context context, List<ShoppingCart> productList){
        this.context = context;
        this.ordersList = productList;
    }
    
    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_row, parent, false);
        OrderHolder productHolder = new OrderHolder(view);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, int position) {
        ShoppingCart product = ordersList.get(position);
        holder.tvTitle.setText(product.getTitle());
        int precio=product.getQuantity()*product.getSale_price();
        holder.tvPrize.setText("$ "+precio);
        holder.tvQuantity.setText(product.getQuantity()+"");

        Picasso.with(context).load(product.getCover()).into(holder.ivImage);

    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    class OrderHolder extends RecyclerView.ViewHolder{

        TextView tvTitle,tvQuantity,tvPrize;
        ImageView ivImage;
        public OrderHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_cart_image);
            tvTitle = itemView.findViewById(R.id.tv_cart_title);
            tvQuantity = itemView.findViewById(R.id.tv_cart_quantity);
            tvPrize = itemView.findViewById(R.id.tv_cart_prize);
        }
    }
}
