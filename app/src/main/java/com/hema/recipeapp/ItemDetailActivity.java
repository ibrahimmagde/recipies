package com.hema.recipeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import static com.hema.recipeapp.ItemListActivity.imIngr;
import static com.hema.recipeapp.MainActivity.selectedName;


public class ItemDetailActivity extends AppCompatActivity {

    Button back;
    Button up;
    private boolean isPhone;
    ItemDetailFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);


        final Bundle arguments = new Bundle();

        back = (Button) findViewById(R.id.back);
        up = (Button) findViewById(R.id.next);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(ItemListActivity.data.get(ItemListActivity.counter).getShortDescription());

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ItemListActivity.class);
                i.putExtra("title",selectedName);
                startActivity(i);

            }
        });

        if (ItemListActivity.counter == 1) back.setVisibility(View.INVISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ItemListActivity.counter--;

                back.setVisibility(View.VISIBLE);
                up.setVisibility(View.VISIBLE);

                if (ItemListActivity.counter == 1) back.setVisibility(View.INVISIBLE);


                Toast.makeText(getApplicationContext(), ItemListActivity.data.get(ItemListActivity.counter).getShortDescription(), Toast.LENGTH_LONG).show();

                arguments.putString(ItemDetailFragment.ARG_ITEM_URL, ItemListActivity.data.get(ItemListActivity.counter).getVideoURL());

                arguments.putString(ItemDetailFragment.ARG_ITEM_DESC,
                        ItemListActivity.data.get(ItemListActivity.counter).getDescription());

                arguments.putString(ItemDetailFragment.ARG_ITEM_SHOW,
                        ItemListActivity.data.get(ItemListActivity.counter).getShortDescription());

                 fragment = new ItemDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();


            }
        });

        if (ItemListActivity.counter == ((ItemListActivity.Size) - 1))
            up.setVisibility(View.INVISIBLE);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemListActivity.counter++;

                up.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
                if (ItemListActivity.counter == ((ItemListActivity.Size) - 1))
                    up.setVisibility(View.INVISIBLE);


                Toast.makeText(getApplicationContext(), ItemListActivity.data.get(ItemListActivity.counter).getShortDescription(), Toast.LENGTH_LONG).show();

                arguments.putString(ItemDetailFragment.ARG_ITEM_URL, ItemListActivity.data.get(ItemListActivity.counter).getVideoURL());

                arguments.putString(ItemDetailFragment.ARG_ITEM_DESC,
                        ItemListActivity.data.get(ItemListActivity.counter).getDescription());

                arguments.putString(ItemDetailFragment.ARG_ITEM_SHOW,
                        ItemListActivity.data.get(ItemListActivity.counter).getShortDescription());


                 fragment = new ItemDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)

                        .commit();


            }
        });

        if (imIngr == 1) {
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

             fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }else{
              fragment = (ItemDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "my_fragment");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        }

        isPhone = getResources().getBoolean(R.bool.is_phone);


        if (isPhone && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE&&imIngr==0) {

            up.setVisibility(View.GONE);
            back.setVisibility(View.GONE);

           /* arguments.putString(FullVideoFragment.ARG_ITEM_URLL,
                    ItemListActivity.data.get(ItemListActivity.counter).getVideoURL());

            FullVideoFragment fragment = new FullVideoFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();*/
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        getSupportFragmentManager().putFragment(outState, "my_fragment", fragment);

    }


}
