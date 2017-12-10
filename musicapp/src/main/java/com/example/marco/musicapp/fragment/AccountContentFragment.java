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
import android.widget.DatePicker;
import android.widget.TextView;
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
import com.example.marco.musicapp.api.model.Album;
import com.example.marco.musicapp.web.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountContentFragment extends Fragment {
    private final String url1= Uri.protocol+ Uri.ip+ Uri.port+"/api/v1/auth/sessions";
    private final String url2= Uri.protocol+ Uri.ip+ Uri.port+"/api/v1/auth/users";
    //final String AdminToken="SFMyNTY.eyJzaWduZWQiOjE1MTI4NTI4OTgsImRhdGEiOjN9.0SGXpDztGZGw7VVnt919US5QcE5RY3i8qQ4Ny6YQ2w0";
    TextView txtUserR,txtPasswordR,txtName,txtLastName,txtPhone,txtAddress,txtZip,txtCountry,txtmainTopic,tvPassword;
    DatePicker dtBirth;
    List<Album> product_list1=new ArrayList<Album>();
    String AdminToken;
    //RecyclerView rv_list_product ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_register, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btRegister = view.findViewById(R.id.btRegister);
        txtUserR = view.findViewById(R.id.txtUserR);
        txtPasswordR = view.findViewById(R.id.txtPasswordR);
        txtName = view.findViewById(R.id.txtName);
        txtLastName = view.findViewById(R.id.txtLastName);
        dtBirth = view.findViewById(R.id.dtBirth);
        txtPhone = view.findViewById(R.id.txtPhone);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtZip = view.findViewById(R.id.txtZip);
        txtCountry = view.findViewById(R.id.txtCountry);
        txtCountry.setText("MEX");
        txtmainTopic = view.findViewById(R.id.txtmainTopic);
        tvPassword = view.findViewById(R.id.tvPassword);


        txtmainTopic.setText("Perfil");
        btRegister.setText("Aceptar");
        txtPasswordR.setText("123456");
        FloatingActionButton fab = (FloatingActionButton) ((MainActivity) getActivity()).findViewById(R.id.fab);
        //fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_autorenew));
        fab.setVisibility(FloatingActionButton.INVISIBLE);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getView(), "¡Datos actualizados!", Snackbar.LENGTH_LONG).show();

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
        List<String> list =new ArrayList<String>();
        for (int i=0;i<response.length();i++) {
            try {
                JSONObject jsonObject =response.getJSONObject(i);

                Snackbar.make(getView(), ((MainActivity) getActivity()).getUser(), Snackbar.LENGTH_LONG).show();
                if (jsonObject.getString("email").equals(((MainActivity) getActivity()).getUser())){
                    txtUserR.setText(jsonObject.getString("email"));
                    //txtPasswordR.setText(jsonObject.getString("password"));
                    txtName.setText(jsonObject.getString("name"));
                    txtLastName.setText(jsonObject.getString("last_name"));
                    txtPhone.setText(jsonObject.getString("phone"));
                    txtAddress.setText(jsonObject.getString("location"));
                    txtZip.setText(jsonObject.getString("postal_code"));
                    txtCountry.setText(jsonObject.getString("country_code"));
                }

            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
    }
}