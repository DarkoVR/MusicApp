package com.example.marco.musicapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginContentFragment extends Fragment {
    private final String url= Uri.protocol+ Uri.ip+ Uri.port+"/api/v1/auth/sessions";
    //final String AdminToken="SFMyNTY.eyJzaWduZWQiOjE1MTI4NTI4OTgsImRhdGEiOjN9.0SGXpDztGZGw7VVnt919US5QcE5RY3i8qQ4Ny6YQ2w0";
    String token;
    TextView txtUser,txtPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Toast toast = Toast.makeText(getContext(), url, Toast.LENGTH_SHORT);
        //toast.show();

        Button btIngresar = (Button) view.findViewById(R.id.btLogin);
        txtUser = (TextView) view.findViewById(R.id.txtUser);
        txtPassword = (TextView) view.findViewById(R.id.txtPassword);

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
                //((MainActivity) getActivity()).doIncrease();
                try {
                    load_users();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void load_users() throws JSONException {
          /*Post data*/
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final JSONObject jsonObject = new JSONObject();
        JSONObject js = new JSONObject();

        jsonObject.put("email",txtUser.getText());
        jsonObject.put("password",txtPassword.getText());

        js.put("session",jsonObject);

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //JSONObject responseJSONObject =response.getJSONObject(i);

                        Snackbar.make(getView(), "¡Has iniciado sesión!", Snackbar.LENGTH_LONG).show();
                        //Toast.makeText(getContext(),response.toString(),Toast.LENGTH_SHORT).show();

                        try {
                            ((MainActivity) getActivity()).setToken(response.getString("access_token"));
                            ((MainActivity) getActivity()).setUser(txtUser.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ((MainActivity) getActivity()).addMenuItems();
                        ((MainActivity) getActivity()).ChangeUserAccount(txtUser.getText().toString());
                        ((MainActivity) getActivity()).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new MainContentFragment())
                                .commit();


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(getView(), "Revisa usuario o contraseña", Snackbar.LENGTH_LONG).show();
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", token);
                return headers;
            }
        };
        queue.add(jsonObjReq);
    }
}