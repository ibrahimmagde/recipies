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

public class recAdapter  extends RecyclerView.Adapter<recAdapter.MyViewHolder> {

    private ArrayList<StepModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textRecipe2);


        }
    }

    public recAdapter(ArrayList<StepModel> data) {
        this.dataSet = data;
    }

    @Override
    public recAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipie_list_row, parent, false);
        view.setOnClickListener(StepsDetail.myOnClickListener);


        recAdapter.MyViewHolder myViewHolder = new recAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final recAdapter.MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;

        textViewName.setText(dataSet.get(listPosition).getShortDescription());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}