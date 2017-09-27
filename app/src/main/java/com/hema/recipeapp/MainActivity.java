package com.hema.recipeapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {

     RequestQueue requestQueue;
    static RecyclerView recyclerView;
    static RecyclerView.LayoutManager layoutManager;
    static RecipeAdapter adapter ;

    static  ArrayList<String> data;

    static View.OnClickListener myOnClickListener;

    private int columns;

    private boolean isPhone;

    static   String selectedName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isPhone = getResources().getBoolean(R.bool.is_phone);


       myOnClickListener = new MyOnClickListener(this);

        data=new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        downloadRecipes("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);





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
                    = (TextView) viewHolder.itemView.findViewById(R.id.textRecipe);
             selectedName = (String) textViewName.getText();

            Intent i = new Intent(context, RecipieListActivity.class);
            i.putExtra("TITLE",selectedName);

            context.startActivity(i);

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

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                String obj = jsonObject.getString("name");
                                data.add(obj);




                            }
                            if (isPhone) {
                                columns = 2;
                            } else {
                                columns = 3;
                            }
                            GridLayoutManager gridlm = new GridLayoutManager(getApplicationContext(),columns);
                            recyclerView.setLayoutManager(gridlm);
                            adapter = new RecipeAdapter(data);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            // Adds the data string to the TextView "results"
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




}
