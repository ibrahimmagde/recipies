package com.hema.recipeapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hema on 9/25/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private ArrayList<String> dataSet;
    private ArrayList<String> photoSet;
    Context context;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ImageView photo;


        public MyViewHolder(View itemView) {
        super(itemView);
        this.textViewName = (TextView) itemView.findViewById(R.id.textRecipe);
        this.photo = (ImageView) itemView.findViewById(R.id.recipe_image);



    }
}

    public RecipeAdapter(ArrayList<String> data, ArrayList<String> pic, Context mContext) {
        this.dataSet = data;
        this.photoSet=pic;
        context=mContext;
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

        Glide.with(context)
                .load(photoSet.get(listPosition))
                .into(holder.photo);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}