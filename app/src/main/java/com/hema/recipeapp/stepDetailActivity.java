package com.hema.recipeapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class stepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

       FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        stepDetailFragment animalDetailFragment = new stepDetailFragment();
         ingredientFragment mIngredientFragment = new ingredientFragment();

        Log.d("sho",recipieListFragment.sho);
        Toast.makeText(this,recipieListFragment.sho,Toast.LENGTH_LONG).show();



        if(recipieListFragment.sho.equals("Ingredients")) {
           fragmentTransaction.replace(R.id.framelayout_detail, mIngredientFragment);

       }
       else
       {
           fragmentTransaction.replace(R.id.framelayout_detail, animalDetailFragment);

       }
        fragmentTransaction.commit();


    }
}
