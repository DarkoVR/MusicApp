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
import android.widget.Toast;

import com.example.marco.musicapp.R;
import com.example.marco.musicapp.activity.MainActivity;

public class LoginContentFragment extends Fragment {

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
        //fab.setImageDrawable();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MainContentFragment())
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

    }
}