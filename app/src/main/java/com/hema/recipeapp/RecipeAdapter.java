package com.hema.recipeapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hema on 9/25/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

private ArrayList<String> dataSet;

public static class MyViewHolder extends RecyclerView.ViewHolder {

    TextView textViewName;

    public MyViewHolder(View itemView) {
        super(itemView);
        this.textViewName = (TextView) itemView.findViewById(R.id.textRecipe);


    }
}

    public RecipeAdapter(ArrayList<String> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_row, parent, false);

        view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;

        textViewName.setText(dataSet.get(listPosition));

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}