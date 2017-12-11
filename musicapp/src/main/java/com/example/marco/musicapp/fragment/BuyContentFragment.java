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
import android.widget.DatePicker;
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
import com.example.marco.musicapp.api.adapter.DiscountAdapter;
import com.example.marco.musicapp.api.adapter.OrderAdapter;
import com.example.marco.musicapp.api.model.Discount;
import com.example.marco.musicapp.api.model.Order;
import com.example.marco.musicapp.api.model.ShoppingCart;
import com.example.marco.musicapp.web.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyContentFragment extends Fragment {
    private final String url1= Uri.protocol+ Uri.ip+ Uri.port+"/api/v1/auth/sessions";
    private final String url2= Uri.protocol+ Uri.ip+ Uri.port+"/api/v1/auth/users";
    private final String url3= Uri.protocol+ Uri.ip+ Uri.port+"/api/v1/store/order";
    private final String url4= Uri.protocol+ Uri.ip+ Uri.port+"/api/v1/store/order_detail";
    String AdminToken;

    List<Discount> discounts_list = new ArrayList<Discount>();
    List<ShoppingCart> shoppingCarts = new ArrayList<ShoppingCart>();
    RecyclerView rv_discount_list ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_buy, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_discount_list = view.findViewById(R.id.rvproductos_list);

        // Adding Floating Action Button to bottom right of main view
        FloatingActionButton fab = (FloatingActionButton) ((MainActivity) getActivity()).findViewById(R.id.fab);
        fab.setVisibility(FloatingActionButton.VISIBLE);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_exit));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MainContentFragment())
                        .commit();
            }
        });

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
                    ((MainActivity) getActivity()).setIdUser(jsonObject.getInt("id"));
                    register_order();
                }
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //Datos para registrar una orden

    private void register_order() throws JSONException {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        final JSONObject jsonObject = new JSONObject();
        JSONObject js = new JSONObject();

        jsonObject.put("user_id",((MainActivity) getActivity()).getIdUser());

        int total=0,precio;
        shoppingCarts=((MainActivity) getActivity()).getShoppingCartList();
        for (int i=0;i<shoppingCarts.size();i++){
            precio=shoppingCarts.get(i).getQuantity()*shoppingCarts.get(i).getSale_price();
            total=total+precio;
        }

        Toast.makeText(getContext(),"Total: "+total,Toast.LENGTH_SHORT).show();

        if (((MainActivity) getActivity()).getIdDiscount()!=0){
            jsonObject.put("discount_id",((MainActivity) getActivity()).getIdDiscount());
            total=total-(total*((MainActivity) getActivity()).getPercentage_value())/100;
        }

        Toast.makeText(getContext(),"Descuento aplicado: "+total,Toast.LENGTH_SHORT).show();

        jsonObject.put("total",total);
        jsonObject.put("date", "2017-12-11T00:00:00Z");
        jsonObject.put("payment_id",((MainActivity) getActivity()).getIdPayment());

        JSONArray jsonArray = new JSONArray();

        jsonObject.put("details",jsonArray);

        js.put("order",jsonObject);

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,url3, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //JSONObject responseJSONObject =response.getJSONObject(i);

                        Snackbar.make(getView(), "¡Error fatal!", Snackbar.LENGTH_LONG).show();
                        //Toast.makeText(getContext(),response.toString(),Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(getView(), "¡La orden se ha registrado!", Snackbar.LENGTH_LONG).show();
                load_last_order();
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", AdminToken);
                return headers;
            }
        };
        queue.add(jsonObjReq);
    }

    //Busqueda por la ultima orden

    private void load_last_order() {
        final RequestQueue requestQueue= Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url2, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        genera_last_order(response);
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
    public void genera_last_order(JSONArray response) {
        for (int i=0;i<response.length();i++) {
            try {
                JSONObject jsonObject =response.getJSONObject(i);

                if (jsonObject.getString("email").equals(((MainActivity) getActivity()).getUser())){
                    JSONArray object = jsonObject.getJSONArray("orders");

                    //Metodo que retorne un String con el nombre del metodo
                    //load_payment(1);

                    for (int j=0;j<object.length();j++){
                        JSONObject jsonObject1 = object.getJSONObject(j);

                        if (j==object.length()-1){
                            register_order_details(jsonObject1.getInt("id"));
                        }

                    }
                }
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //Registro de los detalles de la orden

    private void register_order_details(int id_order) throws JSONException {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JSONObject js = new JSONObject();

        shoppingCarts=((MainActivity) getActivity()).getShoppingCartList();

        for (int i=0;i<shoppingCarts.size();i++) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("quantity", shoppingCarts.get(i).getQuantity());
            jsonObject.put("order_id", id_order);
            jsonObject.put("album_id", shoppingCarts.get(i).getId());


            js.put("order_detail", jsonObject);

            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url4, js,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //JSONObject responseJSONObject =response.getJSONObject(i);

                            Snackbar.make(getView(), "¡Se han agregado los detalles!", Snackbar.LENGTH_LONG).show();
                            ((MainActivity) getActivity()).doClear();
                            Toast.makeText(getContext(), "¡Compra exitosa!", Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar.make(getView(), "Error fatal", Snackbar.LENGTH_LONG).show();
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
    }
}