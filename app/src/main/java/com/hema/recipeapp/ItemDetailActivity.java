package com.hema.recipeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;




public class ItemDetailActivity extends AppCompatActivity {

    Button back;
    Button up;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);



        final Bundle arguments = new Bundle();

        back=(Button) findViewById(R.id.back);
        up =(Button) findViewById(R.id.next);

        if(ItemListActivity.counter==1) back.setVisibility(View.INVISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             ItemListActivity.counter--;

                back.setVisibility(View.VISIBLE);
                up.setVisibility(View.VISIBLE);

                if(ItemListActivity.counter==1) back.setVisibility(View.INVISIBLE);


                Toast.makeText(getApplicationContext(),ItemListActivity.data.get(ItemListActivity.counter).getShortDescription(),Toast.LENGTH_LONG).show();

                 arguments.putString(ItemDetailFragment.ARG_ITEM_URL, ItemListActivity.data.get(ItemListActivity.counter).getVideoURL());

                arguments.putString(ItemDetailFragment.ARG_ITEM_DESC,
                        ItemListActivity.data.get(ItemListActivity.counter).getDescription());

                arguments.putString(ItemDetailFragment.ARG_ITEM_SHOW,
                        ItemListActivity.data.get(ItemListActivity.counter).getShortDescription());

                ItemDetailFragment fragment = new ItemDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();




            }
        });

        if(ItemListActivity.counter==((ItemListActivity.Size)-1)) up.setVisibility(View.INVISIBLE);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemListActivity.counter++;

                up.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
                if(ItemListActivity.counter==((ItemListActivity.Size)-1)) up.setVisibility(View.INVISIBLE);


                Toast.makeText(getApplicationContext(),ItemListActivity.data.get(ItemListActivity.counter).getShortDescription(),Toast.LENGTH_LONG).show();

                  arguments.putString(ItemDetailFragment.ARG_ITEM_URL, ItemListActivity.data.get(ItemListActivity.counter).getVideoURL());

                    arguments.putString(ItemDetailFragment.ARG_ITEM_DESC,
                            ItemListActivity.data.get(ItemListActivity.counter).getDescription());

                    arguments.putString(ItemDetailFragment.ARG_ITEM_SHOW,
                            ItemListActivity.data.get(ItemListActivity.counter).getShortDescription());



                ItemDetailFragment fragment = new ItemDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)

                        .commit();


            }
        });

        if(ItemListActivity.imIngr==1){
            back.setVisibility(View.INVISIBLE);
            up.setVisibility(View.INVISIBLE);
        }

      if (savedInstanceState == null) {

           arguments.putString(ItemDetailFragment.ARG_ITEM_URL,
                    getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_URL));

            arguments.putString(ItemDetailFragment.ARG_ITEM_DESC,
                    getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_DESC));

            arguments.putString(ItemDetailFragment.ARG_ITEM_SHOW,
                    getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_SHOW));

            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
