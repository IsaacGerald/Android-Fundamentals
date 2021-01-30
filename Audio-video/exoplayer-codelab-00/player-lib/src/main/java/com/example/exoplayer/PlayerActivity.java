/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
* limitations under the License.
 */
package com.example.exoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.PlaybackStatsListener;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;


/**
 * A fullscreen activity to play audio or video streams.
 */
public class PlayerActivity extends AppCompatActivity {
  private static final String TAG = PlayerActivity.class.getSimpleName();

  private SimpleExoPlayer player;
  private PlayerView mPlayerView;
  private boolean playWhenReady = true;
  private int currentWindow = 0;
  private long playbackPosition = 0;
  private PlaybackStateListener mPlaybackStateListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_player);
    mPlaybackStateListener = new PlaybackStateListener();

    mPlayerView = findViewById(R.id.video_view);


  }


  private void initializePlayer(){
  //Adaptive streaming
    if (player == null){
      DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);
      trackSelector.setParameters(
              trackSelector.buildUponParameters().setMaxVideoSizeSd()
      );


      player = new SimpleExoPlayer.Builder(this)
              .setTrackSelector(trackSelector)
              .build();
    }
    mPlayerView.setPlayer(player);

    MediaItem mediaItem = new MediaItem.Builder()
            .setUri(getString(R.string.media_url_dash))
            .setMimeType(MimeTypes.APPLICATION_MPD)
            .build();

//    player.setMediaItem(mediaItem);


//      player = new SimpleExoPlayer.Builder(this).build();
//      mPlayerView.setPlayer(player);

//    MediaItem mediaItem = MediaItem.fromUri(getString(R.string.media_url_mp4));
//    MediaItem secondMediaItem = MediaItem.fromUri(getString(R.string.media_url_mp3));
    player.setMediaItem(mediaItem);
    player.setPlayWhenReady(playWhenReady);
    player.seekTo(currentWindow, playbackPosition);
    player.addListener(mPlaybackStateListener);
//    player.addMediaItem(secondMediaItem);
    player.prepare();


  }

  @Override
  protected void onStart() {
    super.onStart();
    Log.d(TAG, "onStart: called ");
    if (Util.SDK_INT >= 24){
      initializePlayer();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.d(TAG, "onResume: called");
    hideSystemUi();
    if (Util.SDK_INT < 24 || player == null){
      initializePlayer();
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.d(TAG, "onPause: called");
    if (Util.SDK_INT < 24){
      releasePlayer();
    }
  }
  @Override
  protected void onStop() {
    super.onStop();
    Log.d(TAG, "onStop: called");
    if (Util.SDK_INT >=24){
      releasePlayer();
    }
  }
  private void releasePlayer() {
    if (player != null){
      playWhenReady = player.getPlayWhenReady();
      playbackPosition = player.getCurrentPosition();
      currentWindow = player.getCurrentWindowIndex();
      player.removeListener(mPlaybackStateListener);
      player.release();
      player = null;
    }
  }



  private void hideSystemUi() {

    mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
  }

  private class PlaybackStateListener implements Player.EventListener {
    String stateString;
    @Override
    public void onPlaybackStateChanged(int state) {
      switch (state){
        case ExoPlayer.STATE_IDLE:
          stateString = "ExoPlayer.STATE_IDLE  -";
          break;
        case ExoPlayer.STATE_BUFFERING:
          stateString = "ExoPlayer.STATE_BUFFERING  -";
          break;
        case ExoPlayer.STATE_READY:
          stateString =" ExoPlayer.STATE_READY  -";
          break;
        case ExoPlayer.STATE_ENDED:
          stateString = "ExoPlayer.STATE_ENDED  -";
          break;
        default:
          stateString = "UNKNOWN_STATE  -";
          break;
      }
      Log.d(TAG, "onPlaybackStateChanged: Change state to " + stateString);
    }
  }
}
