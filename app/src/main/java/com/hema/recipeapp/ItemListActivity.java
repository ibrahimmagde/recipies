package com.hema.recipeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class ItemListActivity extends AppCompatActivity {

   static private boolean mTwoPane;
    static  public ArrayList<StepModel> data;
    static RecyclerView recyclerView;
    RequestQueue requestQueue;
    String strtext;
    static  String selectedName,url="kpkp",sho="lplp",descr="mlml";
    static boolean isPhone =false;
    public static int counter=0;
    public static int Size;
    public static int imIngr =0;




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
        data.add(new StepModel(-2,"Ingredients","","",""));
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
                                        String photo = jsonObject.getString("thumbnailURL");

                                     /*   if(mShortDescription.equals("Finish filling prep"))
                                            Toast.makeText(getApplicationContext(),photo,Toast.LENGTH_LONG).show();*/


                                        data.add(new StepModel(id,mShortDescription,mDescription,mVideoURL,photo));

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
            public final ImageView photo;


            public ViewHolder(View view) {
                super(view);
                mView = view;
                mContentView = (TextView) view.findViewById(R.id.content);
                photo =(ImageView) view.findViewById(R.id.step_image);

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
            Glide.with(getApplicationContext())
                    .load(mValues.get(position).getPhoto())
                    .into(holder.photo);



            holder.mItem= mValues.get(position);

            Size=mValues.size();


            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    counter =position;

                    Toast.makeText(getApplication(),mValues.get(position).getPhoto(),Toast.LENGTH_LONG).show();  ;

                    //  Toast.makeText(getApplicationContext(),String.valueOf(counter) + " pos "+String.valueOf(position),Toast.LENGTH_LONG).show();


                    if (mTwoPane) {

                        Bundle arguments = new Bundle();

                        if(position==0) {
                        //      ItemDetailFragment.msimpleExoPlayerView.setVisibility(View.INVISIBLE);

                            SharedPreferences preferences = getApplicationContext().getSharedPreferences("SHARED", MODE_PRIVATE);
                            String ingre = preferences.getString("ingredients", "choose your Favorite Recipe ");
                         //   Toast.makeText(getApplicationContext(), ingre, Toast.LENGTH_LONG).show();

                            arguments.putString(ItemDetailFragment.ARG_ITEM_URL, "");

                            arguments.putString(ItemDetailFragment.ARG_ITEM_DESC, ingre);

                            arguments.putString(ItemDetailFragment.ARG_ITEM_SHOW,
                                    "Ingredients");
                            imIngr=1;

                        }

                        else {

                             imIngr=0;
                            arguments.putString(ItemDetailFragment.ARG_ITEM_URL, holder.mItem.getVideoURL());
                            arguments.putString(ItemDetailFragment.ARG_ITEM_DESC, holder.mItem.getDescription());
                            arguments.putString(ItemDetailFragment.ARG_ITEM_SHOW, holder.mItem.getShortDescription());

                                }
                            arguments.putString(ItemDetailFragment.ARG_ITEM_TAB, "yes");

                            ItemDetailFragment fragment = new ItemDetailFragment();
                            fragment.setArguments(arguments);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.item_detail_container, fragment)
                                    .commit();



                    } else {
                        Context context = v.getContext();


                        Intent intent = new Intent(context, ItemDetailActivity.class);
                        intent.putExtra(ItemDetailFragment.ARG_ITEM_TAB, "no");

                        if(position==0) {
                            //      ItemDetailFragment.msimpleExoPlayerView.setVisibility(View.INVISIBLE);

                            SharedPreferences preferences = getApplicationContext().getSharedPreferences("SHARED", MODE_PRIVATE);
                            String ingre = preferences.getString("ingredients", "choose your Favorite Recipe ");
                           // Toast.makeText(getApplicationContext(), ingre, Toast.LENGTH_LONG).show();

                            intent.putExtra(ItemDetailFragment.ARG_ITEM_URL, "");

                            intent.putExtra(ItemDetailFragment.ARG_ITEM_DESC, ingre);

                            intent.putExtra(ItemDetailFragment.ARG_ITEM_SHOW,
                                    "Ingredients");
                            imIngr=1;


                        }
                        else {

                            imIngr=0;

                            intent.putExtra(ItemDetailFragment.ARG_ITEM_URL, holder.mItem.getVideoURL());
                            intent.putExtra(ItemDetailFragment.ARG_ITEM_DESC, holder.mItem.getDescription());
                            intent.putExtra(ItemDetailFragment.ARG_ITEM_SHOW, holder.mItem.getShortDescription());
                        }



                        context.startActivity(intent);


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
