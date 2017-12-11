package com.example.marco.musicapp.api.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.marco.musicapp.R;
import com.example.marco.musicapp.activity.MainActivity;
import com.example.marco.musicapp.api.model.Discount;
import com.example.marco.musicapp.fragment.BuyContentFragment;
import com.example.marco.musicapp.fragment.DiscountDinamicContentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 11/12/17.
 */

public class DicountDinamicAdapter extends RecyclerView.Adapter<DicountDinamicAdapter.DiscountHolder> {

    Context context;
    List<Discount> discountList = new ArrayList<>();

    public DicountDinamicAdapter(Context context, List<Discount> productList){
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
        final Discount product = discountList.get(position);
        holder.tvPercentageValue.setText(product.getPercentage_value() + "");
        holder.rvDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).setIdDiscount(product.getId());
                ((MainActivity) context).setPercentage_value(product.getPercentage_value());
                ((MainActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new BuyContentFragment())
                        .commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return discountList.size();
    }

    class DiscountHolder extends RecyclerView.ViewHolder{

        TextView tvPercentageValue;
        RelativeLayout rvDiscount;
        public DiscountHolder(View itemView) {
            super(itemView);
            tvPercentageValue = itemView.findViewById(R.id.tv_percentage_value);
            rvDiscount = itemView.findViewById(R.id.rv_discount);
        }
    }

}
