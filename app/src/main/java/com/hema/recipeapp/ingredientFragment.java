package com.hema.recipeapp;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.hema.recipeapp.MainActivity.selectedName;

/**
 * Created by hema on 9/28/2017.
 */

public class ingredientFragment extends Fragment {


    static RecyclerView recyclerView;
    static ingredientListAdapter mIngredientListAdapter;
    static RequestQueue requestQueue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.ingredient_fragment,null);
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view_ingredientss);
        requestQueue = Volley.newRequestQueue(getContext());

        LinearLayoutManager gridlm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(gridlm);
        mIngredientListAdapter = new ingredientListAdapter(MainActivity.ingrdients);
        recyclerView.setAdapter(mIngredientListAdapter);
        mIngredientListAdapter.notifyDataSetChanged();

        return view;
    }



}
