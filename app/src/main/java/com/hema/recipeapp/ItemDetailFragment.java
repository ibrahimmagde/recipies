package com.hema.recipeapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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



public class ItemDetailFragment extends Fragment {

    public static final String ARG_ITEM_URL = "item_id";
    public static final String ARG_ITEM_DESC = "item_id2";
    public static final String ARG_ITEM_SHOW = "item_id3";
    public static final String ARG_ITEM_TAB= "yes";


    TextView desc;


 //   private SimpleExoPlayerView msimpleExoPlayerView;
    private SimpleExoPlayer player;
    public BandwidthMeter bandwidthMeter;
    public Handler mainHandler;
    private boolean isPhone;


     SimpleExoPlayerView msimpleExoPlayerView;




    public static String url,dec,sho,tab;

    int pos;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        url =  getArguments().getString(ARG_ITEM_URL);
           dec= getArguments().getString(ARG_ITEM_DESC);
           sho =getArguments().getString(ARG_ITEM_SHOW);
           tab=getArguments().getString(ARG_ITEM_TAB);
        //Toast.makeText(getContext(),String.valueOf(ItemListActivity.counter),Toast.LENGTH_LONG).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);


        desc= (TextView) rootView.findViewById(R.id.item_detail);
        isPhone = getResources().getBoolean(R.bool.is_phone);


        msimpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
        msimpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);



        if(url.equals("")){
            msimpleExoPlayerView.setVisibility(View.GONE);

        }
        else {
            msimpleExoPlayerView.setVisibility(View.VISIBLE);
        }

            desc.setText(dec);

            initializePlayer(Uri.parse(url));


        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (player == null) {
            DefaultTrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            msimpleExoPlayerView.setPlayer(player);

            String userAgent = Util.getUserAgent(getContext(), "Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
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




}
