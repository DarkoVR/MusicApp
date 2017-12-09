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
import android.widget.Button;
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
import com.example.marco.musicapp.api.model.Album;
import com.example.marco.musicapp.web.uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginContentFragment extends Fragment {
    private final String url= uri.protocol+uri.ip+uri.port+"/api/v1/auth/users";
    final String AdminToken="SFMyNTY.eyJzaWduZWQiOjE1MTI4NTI4OTgsImRhdGEiOjN9.0SGXpDztGZGw7VVnt919US5QcE5RY3i8qQ4Ny6YQ2w0";

    //String[] productos={"Celular","Tablet","Televisor"};
    List<Album> productos_list =new ArrayList<Album>();
    List<Album> product_list1=new ArrayList<Album>();
    RecyclerView rv_list_product ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btIngresar = (Button) view.findViewById(R.id.btLogin);

        // Adding Floating Action Button to bottom right of main view
        FloatingActionButton fab = (FloatingActionButton) ((MainActivity) getActivity()).findViewById(R.id.fab);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getContext(), "Registro", Toast.LENGTH_SHORT);
                toast.show();

                ((MainActivity) getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new RegisterContentFragment())
                        .commit();

            }
        });

        btIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "Button", Snackbar.LENGTH_LONG).show();
                Toast toast = Toast.makeText(getContext(), "Button", Toast.LENGTH_SHORT);
                toast.show();
                ((MainActivity) getActivity()).doIncrease();

            }
        });

        load_users();
    }

    private void load_users() {
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                //Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                                /*System.out.println(response.toString());*/
                                genera_lista(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
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
        String imagen= "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fcdn.churchm.ag%2Fwp-content%2Fuploads%2F2015%2F01%2FBethel-Music-We-Will-Not-Be-Shaken-cover-art.jpg&f=1";
        List<String> list =new ArrayList<String>();
        for (int i=0;i<response.length();i++) {
            try {
                JSONObject jsonObject =response.getJSONObject(i);
                String dateStr = jsonObject.getString("release");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                JSONArray arr2 = jsonObject.getJSONArray("tracklist");

                /*Toast.makeText(getContext(),
                        jsonObject.getString("title")+"\n"+
                                jsonObject.getString("cover")+"\n"+
                                jsonObject.getInt("sale_price")+"\n"+
                                jsonObject.getInt("purchase_price")+"\n"+
                                jsonObject.getInt("id")+"\n"+
                                jsonObject.getString("release")+"\n"+
                                jsonObject.getJSONArray("tracklist"), Toast.LENGTH_SHORT).show();*/

                //Date birthDate;

                product_list1.add(new Album(
                        jsonObject.getString("title"),
                        jsonObject.getString("cover"),
                        jsonObject.getInt("sale_price"),
                        jsonObject.getInt("purchase_price"),
                        jsonObject.getInt("id"),
                        jsonObject.getString("release"),
                        jsonObject.getJSONArray("tracklist")
                ));
                list.add(jsonObject.getString("name"));
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
        rv_list_product.setAdapter(new AlbumAdapter(getContext(), product_list1));
        rv_list_product.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}