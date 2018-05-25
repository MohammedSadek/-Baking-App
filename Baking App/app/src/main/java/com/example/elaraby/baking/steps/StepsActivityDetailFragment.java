package com.example.elaraby.baking.steps;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.elaraby.baking.R;
import com.example.elaraby.baking.main.MainActivity;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class StepsActivityDetailFragment extends Fragment {

    public static final String ARG_ITEM_INDEX = "item_index";
    public static final String ARG_ITEM_POSITION = "item_position";
    int index, position;
    long seekingPostion = 0;
    boolean whenReady = true;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    public StepsActivityDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            index = savedInstanceState.getInt(ARG_ITEM_INDEX);
            position = savedInstanceState.getInt(ARG_ITEM_POSITION);
            seekingPostion = savedInstanceState.getLong("pos1");
            whenReady = savedInstanceState.getBoolean("whenReady");
        } else {
            index = getArguments().getInt(ARG_ITEM_INDEX);
            position = getArguments().getInt(ARG_ITEM_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.stepsactivity_detail, container, false);

        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);


        ((TextView) rootView.findViewById(R.id.description)).setText(MainActivity.modelArrayList.get(index).getSteps().get(position).getDescription());
        rootView.findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < MainActivity.modelArrayList.get(index).getSteps().size() - 1) {
                    position++;
                    ((TextView) rootView.findViewById(R.id.description)).setText(MainActivity.modelArrayList.get(index).getSteps().get(position).getDescription());
                    if (!TextUtils.isEmpty(MainActivity.modelArrayList.get(index).getSteps().get(position).getVideoURL()))
                        setUri(Uri.parse(MainActivity.modelArrayList.get(index).getSteps().get(position).getVideoURL()));
                    else
                        mExoPlayer.stop();
                }
            }
        });
        rootView.findViewById(R.id.prev_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > 0) {
                    position--;
                    ((TextView) rootView.findViewById(R.id.description)).setText(MainActivity.modelArrayList.get(index).getSteps().get(position).getDescription());
                    if (!TextUtils.isEmpty(MainActivity.modelArrayList.get(index).getSteps().get(position).getVideoURL()))
                        setUri(Uri.parse(MainActivity.modelArrayList.get(index).getSteps().get(position).getVideoURL()));
                    else
                        mExoPlayer.stop();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mPlayerView.getLayoutParams().height = getActivity().getWindowManager().getDefaultDisplay().getHeight();
            ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
            if(actionBar != null)
                actionBar.hide();
        }
    }

    private void initializePlayer() {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
        }
        if (!TextUtils.isEmpty(MainActivity.modelArrayList.get(index).getSteps().get(position).getVideoURL()))
            setUri(Uri.parse(MainActivity.modelArrayList.get(index).getSteps().get(position).getVideoURL()));
    }

    private void setUri(Uri mediaUri) {
        String userAgent = Util.getUserAgent(getContext(), "Baking");
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(whenReady);
        mExoPlayer.seekTo(seekingPostion);
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            if (mExoPlayer.isLoading())
                mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            seekingPostion = mExoPlayer.getCurrentPosition();
            whenReady = mExoPlayer.getPlayWhenReady();
        }
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_ITEM_INDEX, index);
        outState.putInt(ARG_ITEM_POSITION, position);
        outState.putLong("pos1", seekingPostion);
        outState.putBoolean("whenReady",whenReady);
    }

}
