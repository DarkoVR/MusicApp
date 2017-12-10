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
import android.widget.DatePicker;
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

public class RegisterContentFragment extends Fragment {
    private final String url= Uri.protocol+ Uri.ip+ Uri.port+"/api/v1/auth/users";
    TextView txtUserR,txtPasswordR,txtName,txtLastName,txtPhone,txtAddress,txtZip,txtCountry;
    DatePicker dtBirth;
    String formatMonth, formatDay;
    int increaser;

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

        FloatingActionButton fab = (FloatingActionButton) ((MainActivity) getActivity()).findViewById(R.id.fab);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_autorenew));
        fab.setVisibility(FloatingActionButton.INVISIBLE);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                increaser=dtBirth.getMonth()+1;
                if (increaser<10){
                    formatMonth="0"+increaser;
                }else{
                    formatMonth=""+increaser;
                }
                if (dtBirth.getDayOfMonth()<10){
                    formatDay="0"+dtBirth.getDayOfMonth();
                }else{
                    formatDay=""+dtBirth.getDayOfMonth();
                }

                Toast toast = Toast.makeText(getContext(),dtBirth.getYear()+"-"+formatMonth+"-"+formatDay+"T00:00:00Z",Toast.LENGTH_SHORT);
                toast.show();
                //((MainActivity) getActivity()).doIncrease();
                try {
                    register_user();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void register_user() throws JSONException {

        increaser=dtBirth.getMonth()+1;
        if (increaser<10){
            formatMonth="0"+increaser;
        }else{
            formatMonth=""+increaser;
        }
        if (dtBirth.getDayOfMonth()<10){
            formatDay="0"+dtBirth.getDayOfMonth();
        }else{
            formatDay=""+dtBirth.getDayOfMonth();
        }

        RequestQueue queue = Volley.newRequestQueue(getContext());
        final JSONObject jsonObject = new JSONObject();
        JSONObject js = new JSONObject();

        jsonObject.put("email",txtUserR.getText());
        jsonObject.put("password",txtPasswordR.getText());
        jsonObject.put("name",txtName.getText());
        jsonObject.put("last_name",txtLastName.getText());
        jsonObject.put("birth_date",dtBirth.getYear()+"-"+formatMonth+"-"+formatDay+"T00:00:00Z");
        jsonObject.put("phone",txtPhone.getText());
        jsonObject.put("location",txtAddress.getText());
        jsonObject.put("postal_code",txtZip.getText());
        jsonObject.put("country_code",txtCountry.getText());

        js.put("user",jsonObject);

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //JSONObject responseJSONObject =response.getJSONObject(i);

                        //Snackbar.make(getView(), "¡Has iniciado sesión!", Snackbar.LENGTH_LONG).show();
                        Toast.makeText(getContext(),response.toString(),Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(getView(), "¡El usuario se ha registrado!", Snackbar.LENGTH_LONG).show();
                ((MainActivity) getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new LoginContentFragment())
                        .commit();
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