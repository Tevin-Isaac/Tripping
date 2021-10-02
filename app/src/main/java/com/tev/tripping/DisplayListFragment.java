package com.tev.tripping;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;


import java.lang.reflect.Array;
import java.util.ArrayList;

public class DisplayListFragment extends Fragment {


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_trip, container, false);
//
//        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
////        getActivity().setSupportActionBar(toolbar);
//
//        listView=(ListView)rootView.findViewById(R.id.list);
//
//        ApiController api = ApiController.getInstance();
//        ArrayList<Place> places = api.getApiList();
//
//        adapter= new CustomAdapter(places, rootView);
//
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
////                Place place = dataModels.get(position);
////
////                Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
////                        .setAction("No action", null).show();
//            }
//        });

        return rootView;
    }

}
