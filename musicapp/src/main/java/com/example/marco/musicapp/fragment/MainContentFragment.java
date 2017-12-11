package com.example.marco.musicapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.marco.musicapp.R;

import android.widget.ImageView;

import com.example.marco.musicapp.activity.MainActivity;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class MainContentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = (FloatingActionButton) ((MainActivity) getActivity()).findViewById(R.id.fab);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_autorenew));
        fab.setVisibility(FloatingActionButton.INVISIBLE);

        //((MainActivity) getActivity())
    }
}