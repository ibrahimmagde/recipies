package com.hema.recipeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by hema on 9/28/2017.
 */

public class StepsDetail  extends Fragment{


   static TextView desc ;


   static private SimpleExoPlayerView msimpleExoPlayerView;
    static  private SimpleExoPlayer player;
   static private BandwidthMeter bandwidthMeter;
   static private Handler mainHandler;

    static RecyclerView recyclerView;
    recAdapter adapter;
    static  public ArrayList<StepModel> data;

    public static String descr="ok";
    public static String url="ok";
    public static String sho="ok";


    RequestQueue requestQueue;
    static  RequestQueue requestQueue2;

    String strtext="5";

     static View.OnClickListener myOnClickListener;

    static String  selectedName;



    public StepsDetail() {

        mainHandler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.steps_detais,null);
        desc = (TextView) view.findViewById(R.id.desc);

        strtext = getArguments().getString("title");


        mainHandler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();
        msimpleExoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.playerView);
        msimpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        msimpleExoPlayerView.setVisibility(View.INVISIBLE);

        if(getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            desc.setVisibility(View.GONE);


        }
        requestQueue = Volley.newRequestQueue(getContext());

        requestQueue2 = Volley.newRequestQueue(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view2);

        myOnClickListener = new MyOnClickListener(getActivity());

        data = new ArrayList<>();
        data.add(new StepModel(-2,"Ingredients","",""));

        downloadRecipes("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private static void initializePlayer(Uri mediaUri,Context c) {
        if (player == null) {
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();

            player = ExoPlayerFactory.newSimpleInstance(c, trackSelector, loadControl);
            msimpleExoPlayerView.setPlayer(player);

            String userAgent = Util.getUserAgent(c, "Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(c, userAgent), new DefaultExtractorsFactory(), null, null);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);


        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player!=null) {
            player.stop();
            player.release();
            player=null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player!=null) {
            player.stop();
            player.release();
        }
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

                                    }
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                    adapter = new recAdapter(data);
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

            Toast.makeText(context,selectedName,Toast.LENGTH_LONG).show();

            for (StepModel s : data) {
                if (s.getShortDescription().equals(selectedName)) {
                    if(s.getShortDescription().equals("Ingredients")){

                        SharedPreferences preferences = context.getSharedPreferences("SHARED", MODE_PRIVATE);
                        String ingre=  preferences.getString("ingredients", "choose your Favorite Recipe ");
                        desc.setText(ingre);
                        msimpleExoPlayerView.setVisibility(View.INVISIBLE);


                    }
                    else {
                        msimpleExoPlayerView.setVisibility(View.VISIBLE);

                        initializePlayer(Uri.parse(s.getVideoURL()), context);
                        desc.setText(s.getShortDescription());

                    }



                }
            }

            Log.d("detail",selectedName);


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
