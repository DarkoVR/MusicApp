package com.example.marco.musicapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.marco.musicapp.R;
import com.example.marco.musicapp.activity.MainActivity;
import com.example.marco.musicapp.api.adapter.AlbumAdapter;
import com.example.marco.musicapp.api.adapter.CartAdapter;
import com.example.marco.musicapp.api.model.Album;
import com.example.marco.musicapp.api.model.Order_detail;
import com.example.marco.musicapp.api.model.ShoppingCart;
import com.example.marco.musicapp.web.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartContentFragment extends Fragment {
    private final String url= Uri.protocol+ Uri.ip+ Uri.port+"/api/v1/music/album";

    List<ShoppingCart> product_list1=new ArrayList<ShoppingCart>();
    RecyclerView rv_list_product ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main_recicler, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_list_product = view.findViewById(R.id.rvproductos_list);

        // Adding Floating Action Button to bottom right of main view
        FloatingActionButton fab = (FloatingActionButton) ((MainActivity) getActivity()).findViewById(R.id.fab);
        fab.setVisibility(FloatingActionButton.VISIBLE);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_done));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((MainActivity) getActivity()).getToken()!=null){
                    ((MainActivity) getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new PaymentContentFragment())
                            .commit();
                }else{
                    Snackbar.make(getView(),"¡Crea una cuenta para continuar!",Snackbar.LENGTH_LONG).show();
                    ((MainActivity) getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new RegisterContentFragment())
                            .commit();
                }

            }
        });

        product_list1=((MainActivity) getActivity()).getShoppingCartList();

        rv_list_product.setAdapter(new CartAdapter(getContext(), product_list1));
        rv_list_product.setLayoutManager(new LinearLayoutManager(getContext()));

        //load_productos();
    }

    public void load_productos() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put("Hola");

        for (int i=0;i<10;i++){
            product_list1.add(new ShoppingCart(
                    "The dark side of the moon",
                    "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Ffc01.deviantart.net%2Ffs6%2Fi%2F2005%2F110%2Fb%2Fa%2FDark_Side_Of_The_Moon_REMAKE2_by_normanbates.jpg&f=1",
                    2,
                    2,
                    2,
                    "",
                    jsonArray,
                    2
            ));
        }

        //Toast.makeText(getContext(),product_list1.toString(),Toast.LENGTH_SHORT).show();
        rv_list_product.setAdapter(new CartAdapter(getContext(), product_list1));
        rv_list_product.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}