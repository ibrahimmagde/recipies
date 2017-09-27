package com.hema.recipeapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class RecipieListActivity extends AppCompatActivity {

    boolean isPhone=false;
    String title;
    stepDetailFragment animalDetailFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie_list);

        recipieListFragment animalListFragment = new recipieListFragment();

         FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle extras = getIntent().getExtras();
        title= extras.getString("TITLE");
        Bundle bundle = new Bundle();

        bundle.putString("title", title);
// set Fragmentclass Arguments
        animalListFragment.setArguments(bundle);

         fragmentTransaction.add(R.id.framelayout_left,animalListFragment);



        if(findViewById(R.id.framelayout_right)!=null){



             animalDetailFragment = new stepDetailFragment();

            fragmentTransaction.add(R.id.framelayout_right,animalDetailFragment);
        }

        fragmentTransaction.commit();



    }

    public void refresh(){
        stepDetailFragment frag_name = new stepDetailFragment();
        FragmentManager manager = this.getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.framelayout_right, frag_name, frag_name.getTag()).commit();
    }



}
