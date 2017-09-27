package com.hema.recipeapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hema on 9/25/2017.
 */

public class recipieListFragment extends Fragment {

    static   RecyclerView recyclerView;
    recipieListAdapter adapter;
    public ArrayList<StepModel> data;
    public static String selectedName="ok";

    RequestQueue requestQueue;
    String strtext="5";
    static private boolean isPhone;
    static View.OnClickListener myOnClickListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.recipie_list_fragment,null);
         strtext = getArguments().getString("title");

        Log.d("title",strtext);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestQueue = Volley.newRequestQueue(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view2);
        isPhone = getResources().getBoolean(R.bool.is_phone);
        myOnClickListener = new recipieListFragment.MyOnClickListener(getActivity());

        data = new ArrayList<>();
       downloadRecipes("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");


    }


    public void downloadRecipes(String JsonURL){
        // Creating the JsonArrayRequest class called arrayreq, passing the required parameters
        //JsonURL is the URL to be fetched from
        JsonArrayRequest arrayreq = new JsonArrayRequest(JsonURL,
                // The second parameter Listener overrides the method onResponse() and passes
                //JSONArray as a parameter
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
                                        Log.d("id", String.valueOf(id));
                                        Log.d("short", mShortDescription);
                                        Log.d("descc", mDescription);
                                        Log.d("url", mVideoURL);

                                    }
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                    adapter = new recipieListAdapter(data);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

                                }
                            }
                        }

                                // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        // Adds the JSON array request "arrayreq" to the request queue
        requestQueue.add(arrayreq);
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

           Log.d("detail",selectedName);

            if (isPhone) {

                Intent i = new Intent(context, stepDetailActivity.class);
              //  i.putExtra("so",selectedName);
                context.startActivity(i);
               Log.d("open detail","no");

            }
            else {

                RecipieListActivity mRecipieListActivity =new RecipieListActivity();
                mRecipieListActivity.refresh();

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



}
