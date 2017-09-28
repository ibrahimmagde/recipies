package com.hema.recipeapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class ItemListActivity extends AppCompatActivity {

   static private boolean mTwoPane;
    static  public ArrayList<StepModel> data;
    static RecyclerView recyclerView;
    RequestQueue requestQueue;
    String strtext;
    static  String selectedName,url="kpkp",sho="lplp",descr="mlml";
    static boolean isPhone =false;

    static View.OnClickListener myOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        requestQueue = Volley.newRequestQueue(this);

        Bundle extras = getIntent().getExtras();
        strtext= extras.getString("title");

        isPhone = getResources().getBoolean(R.bool.is_phone);

        recyclerView = (RecyclerView)findViewById(R.id.item_list);
        myOnClickListener = new MyOnClickListener(this);


        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }


        data = new ArrayList<>();
        data.add(new StepModel(-2,"Ingredients","",""));
        downloadRecipes("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");



    }

    public   static  class MyOnClickListener implements View.OnClickListener {

        private final Context context;


        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {

            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.textRecipe2);
            selectedName = (String) textViewName.getText();


            for (StepModel s : data) {
                if (s.getShortDescription().equals(selectedName)) {
                    url=s.getVideoURL();
                    descr=s.getDescription();
                    sho=s.getShortDescription();

                    Log.d("url",url);
                    Log.d("desc",descr);
                    Log.d("sho",sho);

                }
            }

            Log.d("detail",selectedName);

            if (mTwoPane) {


            }
            else {



            }


        }

       /* private void removeItem(View v) {

            int selectedItemId = -1;
            for (int i = 0; i < MyData.nameArray.length; i++) {
                if (selectedName.equals(MyData.nameArray[i])) {
                    selectedItemId = MyData.id_[i];
                }
            }
            removedItems.add(selectedItemId);
            data.remove(selectedItemPosition);
            adapter.notifyItemRemoved(selectedItemPosition);
        }*/
    }



    public void downloadRecipes(String JsonURL){

        JsonArrayRequest arrayreq = new JsonArrayRequest(JsonURL,

                new Response.Listener<JSONArray>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int j = 0; j < response.length(); j++) {
                                JSONObject rec = response.getJSONObject(j);
                                String nam = rec.getString("name");

                                if (nam.equals( strtext)) {

                                    JSONArray recArry = rec.getJSONArray("steps");

                                    for (int i = 0; i < recArry.length(); i++) {
                                        JSONObject jsonObject = recArry.getJSONObject(i);

                                        String mShortDescription = jsonObject.getString("shortDescription");
                                        int id = jsonObject.getInt("id");
                                        String mDescription = jsonObject.getString("description");
                                        String mVideoURL = jsonObject.getString("videoURL");

                                        data.add(new StepModel(id,mShortDescription,mDescription,mVideoURL));

                                    }

                                    recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(data));


                                }
                            }
                        }

                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        requestQueue.add(arrayreq);

    }


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ArrayList<StepModel> mValues;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mContentView;
            public  StepModel mItem;


            public ViewHolder(View view) {
                super(view);
                mView = view;
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }

        public SimpleItemRecyclerViewAdapter(ArrayList<StepModel> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            view.setOnClickListener(ItemListActivity.myOnClickListener);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            holder.mContentView.setText(mValues.get(position).getShortDescription());
            holder.mItem = mValues.get(position);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (mTwoPane) {

                        Bundle arguments = new Bundle();
                        arguments.putString(ItemDetailFragment.ARG_ITEM_URL, holder.mItem.getVideoURL());
                        arguments.putString(ItemDetailFragment.ARG_ITEM_DESC, holder.mItem.getDescription());
                        arguments.putString(ItemDetailFragment.ARG_ITEM_SHOW, holder.mItem.getShortDescription());
                        arguments.putString(ItemDetailFragment.ARG_ITEM_TAB, "yes");



                        ItemDetailFragment fragment = new ItemDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();

                        Toast.makeText(getApplicationContext(),"two_panes",Toast.LENGTH_LONG).show();

                    } else {
                        Context context = v.getContext();

                        Intent intent = new Intent(context, ItemDetailActivity.class);

                        intent.putExtra(ItemDetailFragment.ARG_ITEM_URL, holder.mItem.getVideoURL());
                        intent.putExtra(ItemDetailFragment.ARG_ITEM_DESC, holder.mItem.getDescription());
                        intent.putExtra(ItemDetailFragment.ARG_ITEM_SHOW, holder.mItem.getShortDescription());
                        intent.putExtra(ItemDetailFragment.ARG_ITEM_TAB, "no");


                        context.startActivity(intent);
                        Toast.makeText(getApplicationContext(),holder.mItem.getDescription(),Toast.LENGTH_LONG).show();


                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }


    }



}
