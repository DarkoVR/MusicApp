package com.example.marco.musicapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marco.musicapp.R;
import com.example.marco.musicapp.activity.MainActivity;
import com.example.marco.musicapp.web.Uri;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailContentFragment extends Fragment {
    private final String url= Uri.protocol+ Uri.ip+ Uri.port+"/api/v1/music/album";

    RecyclerView rv_list_product ;
    TextView tvTitle,tvTracks,tvPrice,tvRelease;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_list_product = view.findViewById(R.id.rvproductos_list);
        tvTitle = view.findViewById(R.id.tv_detail_title);
        tvTracks = view.findViewById(R.id.tv_detail_description);
        tvPrice = view.findViewById(R.id.tv_detail_price);
        tvRelease = view.findViewById(R.id.tv_detail_other);
        imageView = view.findViewById(R.id.tv_detail_image);

        FloatingActionButton fab = (FloatingActionButton) ((MainActivity) getActivity()).findViewById(R.id.fab);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_cart_white));
        //fab.setVisibility(FloatingActionButton.INVISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).doIncrease();
            }
        });

        try {
            load_productos(url+"/"+((MainActivity) getActivity()).getIdDetail());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void load_productos(final String url) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JSONObject js = new JSONObject();

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Snackbar.make(getView(), url, Snackbar.LENGTH_LONG).show();
                        genera_lista(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(getView(), "Error", Snackbar.LENGTH_LONG).show();
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Authorization", token);
                return headers;
            }
        };
        queue.add(jsonObjReq);
    }
    public void genera_lista(JSONObject jsonObject) {
            try {
                String tracklist="";

                Picasso.with(getContext()).load(jsonObject.getString("cover")).into(imageView);
                tvTitle.setText(jsonObject.getString("title"));
                for (int i=0;i<jsonObject.getJSONArray("tracklist").length();i++){
                    tracklist+=jsonObject.getJSONArray("tracklist").get(i)+"\n";
                }
                tvTracks.setText(tracklist);
                tvPrice.setText("A tan solo $"+jsonObject.getInt("sale_price"));
                tvRelease.setText(jsonObject.getString("release"));

            } catch(JSONException e) {
                e.printStackTrace();
            }
        //rv_list_product.setAdapter(new DetailAdapter(getContext(), product_list1));
        //rv_list_product.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}