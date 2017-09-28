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
        isPhone = getResources().getBoolean(R.bool.is_phone);

        recipieListFragment list = new recipieListFragment();
        StepsDetail mStepsDetail = new StepsDetail();


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle extras = getIntent().getExtras();
        title= extras.getString("TITLE");
        Bundle bundle = new Bundle();

        bundle.putString("title", title);
// set Fragmentclass Arguments
        list.setArguments(bundle);
        mStepsDetail.setArguments(bundle);

        if(isPhone){
            fragmentTransaction.add(R.id.framelayout_list,list);
            Log.d("i'm phooone","ok");

        }
        else {
          fragmentTransaction.add(R.id.framelayout_list,mStepsDetail);
            Log.d("i'm taaaaablet","ok");
            Toast.makeText(this,"tablet" +
                    "",Toast.LENGTH_LONG).show();



        }

        fragmentTransaction.commit();



    }



}
