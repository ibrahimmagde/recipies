package com.hema.recipeapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hema on 9/28/2017.
 */

public class ingredientListAdapter extends RecyclerView.Adapter<ingredientListAdapter.MyViewHolder> {

    private ArrayList<String> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textRecipe2);


        }
    }

    public ingredientListAdapter(ArrayList<String> data) {
        this.dataSet = data;
    }

    @Override
    public ingredientListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipie_list_row, parent, false);
        view.setOnClickListener(recipieListFragment.myOnClickListener);


        ingredientListAdapter.MyViewHolder myViewHolder = new ingredientListAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ingredientListAdapter.MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;

        textViewName.setText(dataSet.get(listPosition));

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}