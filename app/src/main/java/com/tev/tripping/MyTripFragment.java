package com.tev.tripping;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public class MyTripFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        //load default map as nearby places
//        getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.frame_trip_container, new DisplayListFragment())
//                .commit();
        return inflater.inflate(R.layout.fragment_my_trip, null);
    }
}
