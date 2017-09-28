package com.hema.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;


public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

      if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
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
