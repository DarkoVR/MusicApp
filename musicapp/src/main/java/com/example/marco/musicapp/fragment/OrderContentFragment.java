package com.example.marco.musicapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marco.musicapp.R;
import com.example.marco.musicapp.activity.MainActivity;
import com.example.marco.musicapp.api.adapter.AlbumAdapter;
import com.example.marco.musicapp.api.adapter.DiscountAdapter;
import com.example.marco.musicapp.api.adapter.OrderAdapter;
import com.example.marco.musicapp.api.model.Album;
import com.example.marco.musicapp.api.model.Discount;
import com.example.marco.musicapp.api.model.Order;
import com.example.marco.musicapp.web.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderContentFragment extends Fragment {
    private final String url1= Uri.protocol+ Uri.ip+ Uri.port+"/api/v1/auth/sessions";
    private final String url2= Uri.protocol+ Uri.ip+ Uri.port+"/api/v1/auth/users";
    private final String url3= Uri.protocol+ Uri.ip+ Uri.port+"/api/v1/store/method";
    String AdminToken;
    String method_name;

    List<Order> orders_list = new ArrayList<Order>();
    RecyclerView rv_order_list ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main_recicler, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_order_list = view.findViewById(R.id.rvproductos_list);

        try {
            load_admin();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void load_admin() throws JSONException {
          /*Post data*/
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final JSONObject jsonObject = new JSONObject();
        JSONObject js = new JSONObject();

        jsonObject.put("email",Uri.UserAdmin);
        jsonObject.put("password",Uri.PasswordAdmin);

        js.put("session",jsonObject);

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,url1, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            AdminToken=response.getString("access_token");
                            load_profile();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(getView(), "Error1: "+error.toString(), Snackbar.LENGTH_LONG).show();
            }
        });
        queue.add(jsonObjReq);
    }

    private void load_profile() {
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url2, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        genera_lista(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(getView(), "Error2: "+error.toString(), Snackbar.LENGTH_LONG).show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", AdminToken);

                return params;
            }
        };
        requestQueue.add(jsonArrayRequest);
    }
    public void genera_lista(JSONArray response) {
        for (int i=0;i<response.length();i++) {
            try {
                JSONObject jsonObject =response.getJSONObject(i);

                if (jsonObject.getString("email").equals(((MainActivity) getActivity()).getUser())){
                    JSONArray object = jsonObject.getJSONArray("orders");

                    //Metodo que retorne un String con el nombre del metodo
                    //load_payment(1);

                    for (int j=0;j<object.length();j++){
                        JSONObject jsonObject1 = object.getJSONObject(j);

                        if (jsonObject1.getInt("payment_id")==1){
                            method_name="Credit card";
                        }else{
                            method_name="Cash o delivery";
                        }

                        orders_list.add(new Order(
                                jsonObject1.getString("total"),
                                method_name,
                                jsonObject1.getInt("id"),
                                jsonObject1.getJSONArray("details"),
                                jsonObject1.getString("date")
                        ));
                    }
                }
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
        if (orders_list.size()>0){
            rv_order_list.setAdapter(new OrderAdapter(getContext(), orders_list));
            rv_order_list.setLayoutManager(new LinearLayoutManager(getContext()));
        }else{
            Snackbar.make(getView(),"No existe historial de ordenes",Snackbar.LENGTH_LONG).show();
        }
    }
}