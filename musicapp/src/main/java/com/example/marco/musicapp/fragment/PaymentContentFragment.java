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

import com.example.marco.musicapp.R;
import com.example.marco.musicapp.activity.MainActivity;

public class PaymentContentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_payment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btCreditCard,btCashOnDelivery;

        btCreditCard = view.findViewById(R.id.bt_credit_card);
        btCashOnDelivery = view.findViewById(R.id.bt_cash_on_delivery);


        // Adding Floating Action Button to bottom right of main view
        FloatingActionButton fab = (FloatingActionButton) ((MainActivity) getActivity()).findViewById(R.id.fab);
        fab.setVisibility(FloatingActionButton.INVISIBLE);

        btCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity) getActivity()).setIdPayment(1);
                ((MainActivity) getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new BuyContentFragment())
                        .commit();
            }
        });

        btCashOnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity) getActivity()).setIdPayment(2);
                ((MainActivity) getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new BuyContentFragment())
                        .commit();
            }
        });
    }
}