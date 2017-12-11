package com.example.marco.musicapp.api.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marco.musicapp.R;
import com.example.marco.musicapp.api.model.Album;
import com.example.marco.musicapp.api.model.Discount;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 11/12/17.
 */

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.DiscountHolder> {

    Context context;
    List<Discount> discountList = new ArrayList<>();

    public DiscountAdapter(Context context, List<Discount> productList){
        this.context = context;
        this.discountList = productList;
    }
    
    @Override
    public DiscountHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.discount_row, parent, false);
        DiscountHolder productHolder = new DiscountHolder(view);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(DiscountHolder holder, int position) {
        Discount product = discountList.get(position);
        holder.tvPercentageValue.setText(product.getPercentage_value() + "");

    }

    @Override
    public int getItemCount() {
        return discountList.size();
    }

    class DiscountHolder extends RecyclerView.ViewHolder{

        TextView tvPercentageValue;
        public DiscountHolder(View itemView) {
            super(itemView);
            tvPercentageValue = itemView.findViewById(R.id.tv_percentage_value);
        }
    }

}
