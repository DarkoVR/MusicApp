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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niluxer on 11/12/17.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ProductHolder> {

    Context context;
    List<Album> productList = new ArrayList<>();

    public AlbumAdapter(Context context, List<Album> productList){
        this.context = context;
        this.productList = productList;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.album_row, parent, false);
        ProductHolder productHolder = new ProductHolder(view);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {
        Album product = productList.get(position);
        holder.tvProductName.setText(product.getTitle());
        holder.tvProductQuantity.setText(product.getTracklist() + "");

        Picasso.with(context).load(product.getCover()).into(holder.ivImage);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{

        TextView tvProductName, tvProductQuantity;
        ImageView ivImage;
        public ProductHolder(View itemView) {
            super(itemView);
            tvProductName     = itemView.findViewById(R.id.tv_product_name);
            tvProductQuantity = itemView.findViewById(R.id.tv_product_quantity);
            ivImage           = itemView.findViewById(R.id.iv_image);
        }
    }

}
