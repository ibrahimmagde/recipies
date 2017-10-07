package com.hema.recipeapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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



public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    static RequestQueue requestQueue2;

    static RecyclerView recyclerView;
    static RecyclerView.LayoutManager layoutManager;
    static RecipeAdapter adapter ;

    static  ArrayList<String> data;
    static  ArrayList<String> pics;
    static View.OnClickListener myOnClickListener;

    private int columns;

    private boolean isPhone;
    static  public ArrayList<String> ingrdients=new ArrayList<>();

   public static   String selectedName;

    static public String mIngredients="";

  static   SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isPhone = getResources().getBoolean(R.bool.is_phone);
        editor = getSharedPreferences("SHARED", MODE_PRIVATE).edit();




        myOnClickListener = new MyOnClickListener(this);

        data=new ArrayList<>();
        pics=new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        requestQueue2 = Volley.newRequestQueue(this);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        if (isOnline()) {

            downloadRecipes("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
        }
        else{
            Toast.makeText(this,"Please connect to the internet",Toast.LENGTH_LONG).show();

        }




    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
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

            Intent i = new Intent(context, ItemListActivity.class);
            i.putExtra("title",selectedName);
            context.startActivity(i);

            downloadIngrediesn("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json",context);



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
                                String photo = jsonObject.getString("image");

                                data.add(obj);
                                pics.add(photo);

                            }
                            if (isPhone) {
                                columns = 1;
                            } else {
                                columns = 3;
                            }
                            GridLayoutManager gridlm = new GridLayoutManager(getApplicationContext(),columns);
                            recyclerView.setLayoutManager(gridlm);
                            adapter = new RecipeAdapter(data,pics,getApplicationContext());
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


    public static void downloadIngrediesn(String JsonURL, final Context mContext){
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
                                Log.d("nam", nam);
                                Log.d("selectedname", selectedName);
                                if (nam.equals(selectedName) ){

                                    JSONArray recArry = rec.getJSONArray("ingredients");
                                    Log.d("ingrediennts", recArry.getJSONObject(j).getString("quantity"));

                                    for (int i = 0; i < recArry.length(); i++) {
                                        JSONObject jsonObject = recArry.getJSONObject(i);

                                        String quanttiy = jsonObject.getString("quantity");
                                        String measure = jsonObject.getString("measure");
                                        String ingredient = jsonObject.getString("ingredient");

                                        ingrdients.add(quanttiy+measure +" of "+ingredient);
                                        mIngredients +=quanttiy+measure +" of "+ingredient;
                                        mIngredients+="\n";
                                        Log.d("ingrediennt", quanttiy+measure +" of "+ingredient);
                                    }
                                    editor.putString("ingredients",mIngredients  );

                                    editor.apply();
                                    mIngredients="";



                                    Intent intentwidget = new Intent(mContext, widget.class);
                                    intentwidget.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                                    int ids[] = AppWidgetManager.getInstance(mContext).getAppWidgetIds(new ComponentName(mContext, widget.class));
                                    intentwidget.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                                    mContext.sendBroadcast(intentwidget);



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
        requestQueue2.add(arrayreq);
    }









}
