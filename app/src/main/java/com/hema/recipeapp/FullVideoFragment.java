package com.hema.recipeapp;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by hema on 10/6/2017.
 */

public class FullVideoFragment extends Fragment {

    public static final String ARG_ITEM_URLL = "item";


    private SimpleExoPlayer player2;

    SimpleExoPlayerView msimpleExoPlayerView;


    public static String url;

    public FullVideoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        url =  getArguments().getString(ARG_ITEM_URLL);
        Toast.makeText(getActivity(),url,Toast.LENGTH_LONG).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.full_screen, container, false);

        msimpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
        msimpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        initializePlayer(Uri.parse(url));

        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (player2 == null) {
            DefaultTrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            player2 = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            msimpleExoPlayerView.setPlayer(player2);

            String userAgent = Util.getUserAgent(getContext(), "Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            player2.prepare(mediaSource);
            player2.setPlayWhenReady(true);


        }
    }







    @Override
    public void onDetach() {
        super.onDetach();
        if (player2!=null) {
            player2.stop();
            player2.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player2!=null) {
            player2.stop();
            player2.release();
            player2=null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player2!=null) {
            player2.stop();
            player2.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player2!=null) {
            player2.stop();
            player2.release();
        }
    }




}
