package com.hema.recipeapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hema on 9/26/2017.
 */

public class stepDetailFragment extends Fragment{


    TextView desc ;
    String strtext="k";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.step_detail_fragment,null);

          return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        desc = (TextView) view.findViewById(R.id.desc);
        desc.setText(recipieListFragment.selectedName);
    }



}
