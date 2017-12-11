package com.example.marco.musicapp.api.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marco.musicapp.R;
import com.example.marco.musicapp.activity.MainActivity;
import com.example.marco.musicapp.api.model.Album;
import com.example.marco.musicapp.api.model.ShoppingCart;
import com.example.marco.musicapp.fragment.CartContentFragment;
import com.example.marco.musicapp.fragment.DetailContentFragment;
import com.example.marco.musicapp.fragment.RegisterContentFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 11/12/17.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ProductHolder> {

    Context context;
    List<Album> albumList = new ArrayList<>();
    ShoppingCart shoppingCart;
    List<ShoppingCart> shoppingCartList = new ArrayList<ShoppingCart>();
    Boolean existList;

    public AlbumAdapter(Context context, List<Album> productList){
        this.context = context;
        this.albumList = productList;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.album_row, parent, false);
        ProductHolder productHolder = new ProductHolder(view);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(final ProductHolder holder, final int position) {
        final Album product = albumList.get(position);
        holder.tvProductName.setText(product.getTitle());
        holder.tvProductQuantity.setText(product.getTracklist() + "");
        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,product.getTitle(),Snackbar.LENGTH_LONG).show();
                ((MainActivity) context).setIdDetail(product.getId());
                ((MainActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new DetailContentFragment())
                        .commit();
            }
        });
        holder.btAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Alguna acción",Snackbar.LENGTH_LONG).show();
            }
        });
        holder.btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Agregado chavo :)",Snackbar.LENGTH_LONG).show();
                ((MainActivity) context).doIncrease();
                existList=false;

                shoppingCartList=((MainActivity) context).getShoppingCartList();

                for (int i=0;i<((MainActivity) context).getShoppingCartList().size();i++){
                    if (((MainActivity) context).getShoppingCartList().get(i).getId()==product.getId()){
                        ((MainActivity) context).getShoppingCartList().get(i).setQuantity(
                                ((MainActivity) context).getShoppingCartList().get(i).getQuantity()+1
                        );
                        existList=true;
                    }
                }

                if (!existList){
                    shoppingCartList.add(new ShoppingCart(
                            product.getTitle(),
                            product.getCover(),
                            product.getSale_price(),
                            product.getPurchase_price(),
                            product.getId(),
                            product.getRelease(),
                            product.getTracklist(),
                            0
                    ));
                }

                ((MainActivity) context).setShoppingCartList(shoppingCartList);
            }
        });
        holder.btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shoppingCartList=((MainActivity) context).getShoppingCartList();

                for (int i=0;i<((MainActivity) context).getShoppingCartList().size();i++){
                    if (((MainActivity) context).getShoppingCartList().get(i).getId()==product.getId()){
                        if (((MainActivity) context).getShoppingCartList().get(i).getQuantity()>0){
                            ((MainActivity) context).getShoppingCartList().get(i).setQuantity(
                                    ((MainActivity) context).getShoppingCartList().get(i).getQuantity()-1
                            );
                            Snackbar.make(view,"Removido chavo :(",Snackbar.LENGTH_LONG).show();
                            ((MainActivity) context).doDecrease();
                        }else{
                            shoppingCartList.remove(i);
                            Snackbar.make(view,"¡Ya no tienes elementos de este producto!",Snackbar.LENGTH_LONG).show();
                        }
                    }
                }

                ((MainActivity) context).setShoppingCartList(shoppingCartList);

            }
        });

        Picasso.with(context).load(product.getCover()).into(holder.ivImage);

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{

        TextView tvProductName, tvProductQuantity;
        ImageView ivImage;
        Button btAction;
        ImageButton btAdd,btRemove;
        public ProductHolder(View itemView) {
            super(itemView);
            tvProductName     = itemView.findViewById(R.id.tv_product_name);
            tvProductQuantity = itemView.findViewById(R.id.tv_product_quantity);
            ivImage           = itemView.findViewById(R.id.iv_image);
            btAction          = itemView.findViewById(R.id.action_button);
            btAdd             = itemView.findViewById(R.id.add_button);
            btRemove          = itemView.findViewById(R.id.remove_button);
        }
    }

}
