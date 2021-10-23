/*
 * Copyright 2018 Google LLC
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

package com.google.ar.sceneform.samples.augmentedimage;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.samples.common.helpers.SnackbarHelper;
import com.google.ar.sceneform.ux.ArFragment;
import java.io.IOException;
import java.io.InputStream;

/**
 * Extend the ArFragment to customize the ARCore session configuration to include Augmented Images.
 */
public class AugmentedImageFragment extends ArFragment {
  private static final String TAG = "AugmentedImageFragment";

  // NOTE: The way that the correct files are loaded depends on the INDEX loaded from the database.
  // Look at the order of the images in the assets folder to find out what the order of the images/songs/videos should be.

  // Create a list of the images to be used (the order must match the order of the Music_list, videoList, and the database of images.)
  public static final String[] Image_list = {
          "afrikanische_weisheit",
          "andrej_sacharow",
          "ashton_kutcher_ian",
          "beachcroc",
          "beachcroc_text",
          "beachflag",
          "berlin",
          "bigger_elephant",
          "birds",
          "blue_face",
          "bunny_windows",
          "burial_hill",
          "coles_hill",
          "communist_kiss", // should be sunset monorail
          "couple_beach",
          "courthous_1749",
          "courthouse_1820",
          "curriculum_vita",
          "dog",
          "dumbbell",
          "erich_fried",
          "escaping_the_east",
          "fancyballroom",
          "fancyballroom_text",
          "firebreathingchicken",
          "flag_ks",
          "forefathers_munument",
          "forest",
          "garbage_day",
          "geeksphone_firefoxos",
          "harlow_house_museum",
          "heatjerseybosh_codeposter", // should be sunset monorail
          "hedge_house",
          "howland_house",
          "i_am_malala_book",
          "immigrant_monument",
          "jenney_grist_mill",
          "king_phillips_war_plaque",
          "lavaeye",
          "leyden_st",
          "lightbulb_ks",
          "loomia_logo",
          "mark_poster",
          "massasoit_statue",
          "mayflower_ii",
          "mayflower_society_house",
          "nathan_jones",
          "obama_campaignlogo_ks",
          "obscure_faces",
          "pilgrim_hall_museum",
          "pilgrim_maiden_statue",
          "pilgrim_mother",
          "plimouth_plantation",
          "plymouth_rock",
          "red_curtain",
          "rk_shovel",
          "robot_war",
          "seven_stages",
          "skater",
          "skater_text",
          "sparrow_house",
          "spooner_house_museum",
          "sunsetmonorail",
          "sushi",
          "thumbs_up",
          "tolerance",
          "town_brook",
          "town_square",
          "training_green",
          "uaine",
          "ufosighting",
          "wall_car",
          "waterfall",
          "william_bradford_statue",
          "women_in_power",
          "youtube_logo"
  };

  // Create a list of the song file paths to be used (the order must match the order of Image_list)
  // Again, the index MUST match up with the images from the database!
  public static final int[] Music_list = {
         R.raw.afrikanische_weisheit_wind_cropped,
          R.raw.andrej_sacharow_speech,
          R.raw.imagine, //ashton_kutcher
          R.raw.beachcroc_song,
          R.raw.beachcroc_song,
          R.raw.beachflag,
          R.raw.berlin_wire_zap_cropped,
          R.raw.elephant,//bigger_elephant
          R.raw.imagine, //birds
          R.raw.blue_face_wind_chime_sounds,
          R.raw.bunny_window_piano_jazz_improvisation,
          R.raw.burial_hill,
          R.raw.coles_hill,
          R.raw.communist_kiss_cold_jorge_mendez_cropped,
          R.raw.couple,
          R.raw.courthouse_1749,
          R.raw.courthouse_1820,
          R.raw.curriculum_vita_cropped,
          R.raw.dog_squeaky_toy_sound_effect,
          R.raw.imagine,//dumbbell
          R.raw.erich_fried_mellow_sound,
          R.raw.escaping_the_east_cropped_real,
          R.raw.fancyballroom_song,
          R.raw.fancyballroom_song,
          R.raw.firebreathingchicken,
          R.raw.imagine, //flag
          R.raw.forefathers_monument,
          R.raw.dh_reiter_patagonian_nature_sounds, //forrest target
          R.raw.garbage_day_cropped,
          R.raw.imagine, //geeksphone
          R.raw.harlow_house_museum,
          R.raw.imagine, //heatjersheybosh
          R.raw.hedge_house,
          R.raw.howland_house,
          R.raw.imagine,//I am malala book
          R.raw.immigrant_monument,
          R.raw.jenney_grist_mill,
          R.raw.king_philips_war_plaque,
          R.raw.lavaeye,
          R.raw.leyden_st,
          R.raw.imagine, //lightbulb_ks
          R.raw.imagine, //loomia,
          R.raw.imagine, //mark_poster,
          R.raw.massasoit_statue,
          R.raw.mayflower_ii,
          R.raw.mayflower_society_house,
          R.raw.nathan_jones_industrial_sounds_with_soul,
          R.raw.imagine, //obama_campaign
          R.raw.obscure_faces_people_talking,
          R.raw.pilgrim_hall_museum,
          R.raw.pilgrim_maiden_statue,
          R.raw.pilgrim_mother,
          R.raw.plimouth_plantation,
          R.raw.plymouth_rock,
          R.raw.red_curtain_calm_ocean,
          R.raw.imagine, //rk_shovel
          R.raw.robot_war_factory,
          R.raw.seven_stages_cropped,
          R.raw.skater_song,
          R.raw.skater_song,
          R.raw.sparrow_house,
          R.raw.spooner_house_museum,
          R.raw.sunsetmonorail,
          R.raw.sushi,
          R.raw.thumbs_up_metal_chain_sound_effect,
          R.raw.tolerance_jazz_piano,
          R.raw.town_brook,
          R.raw.town_square,
          R.raw.training_green,
          R.raw.uaine,
          R.raw.ufosighting,
          R.raw.the_berlin_wall_falls, //wall car target image sound
          R.raw.imagine, //waterfalldino
          R.raw.william_bradford_statue,
          R.raw.women_in_power_cropped,
          R.raw.imagine //youtube_logo
  };

  // Create a list of the video file paths to be used (the order must match the order of Image_list)
  // Again, the index MUST match up with the images from the database!
  public static final int[] Video_list = {
          R.raw.beachcroc,
          R.raw.beachcroc,
          R.raw.beachcroc,
          R.raw.beachcroc,
          R.raw.beachcroc,
          R.raw.fancyballroom,
          R.raw.fancyballroom,
          R.raw.fancyballroom,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.beachcroc,
          R.raw.beachcroc,
          R.raw.beachcroc,
          R.raw.beachcroc,
          R.raw.beachcroc,
          R.raw.fancyballroom,
          R.raw.fancyballroom,
          R.raw.fancyballroom,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.beachcroc,
          R.raw.beachcroc,
          R.raw.beachcroc,
          R.raw.beachcroc,
          R.raw.beachcroc,
          R.raw.fancyballroom,
          R.raw.fancyballroom,
          R.raw.fancyballroom,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.beachcroc,
          R.raw.beachcroc,
          R.raw.beachcroc,
          R.raw.beachcroc,
          R.raw.beachcroc,
          R.raw.fancyballroom,
          R.raw.fancyballroom,
          R.raw.fancyballroom,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater,
          R.raw.skater
  };


  // List of booleans used to say whether a video is playing or not.
  // If an element is true, a video will be played when that image is detected.
  public static final boolean[] imagePlaysVideoBooleanList = {
          false,
          false,
          false,
          false,
          false,//beachcroc_text
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,//beachcroc_text
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,//beachcroc_text
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,//beachcroc_text
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false
  };

  public boolean usePreloadedDatabase = true;
  // Link to database file, this file is in the assets folder.
  private static final String SAMPLE_IMAGE_DATABASE = "ARTAugmentedImages1819DB.imgdb";

  // Do a runtime check for the OpenGL level available at runtime to avoid Sceneform crashing the
  // application.
  private static final double MIN_OPENGL_VERSION = 3.0;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    // Check for Sceneform being supported on this device.  This check will be integrated into
    // Sceneform eventually.
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
      Log.e(TAG, "Sceneform requires Android N or later");
      SnackbarHelper.getInstance()
              .showError(getActivity(), "Sceneform requires Android N or later");
    }

    String openGlVersionString =
            ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE))
                    .getDeviceConfigurationInfo()
                    .getGlEsVersion();
    if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
      Log.e(TAG, "Sceneform requires OpenGL ES 3.0 or later");
      SnackbarHelper.getInstance()
              .showError(getActivity(), "Sceneform requires OpenGL ES 3.0 or later");
    }
  }

  @Override
  public View onCreateView(
          LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = super.onCreateView(inflater, container, savedInstanceState);

    // Turn off the plane discovery since we're only looking for images
    getPlaneDiscoveryController().hide();
    getPlaneDiscoveryController().setInstructionView(null);
    getArSceneView().getPlaneRenderer().setEnabled(false);
    return view;
  }

  @Override
  protected Config getSessionConfiguration(Session session) {
    Config config = super.getSessionConfiguration(session);
    if (!setupAugmentedImageDatabase(config, session)) {
      SnackbarHelper.getInstance()
              .showError(getActivity(), "Could not setup augmented image database");
    }
    return config;
  }

  private boolean setupAugmentedImageDatabase(Config config, Session session) {
    AugmentedImageDatabase augmentedImageDatabase;

    AssetManager assetManager = getContext() != null ? getContext().getAssets() : null;
    if (assetManager == null) {
      Log.e(TAG, "Context is null, cannot intitialize image database.");
      return false;
    }

    // There are two ways to configure an AugmentedImageDatabase:
    // 1. Add Bitmap to DB directly
    // 2. Load a pre-built AugmentedImageDatabase
    // Option 2) has
    // * shorter setup time
    // * doesn't require images to be packaged in apk.
    if(!usePreloadedDatabase) {
      augmentedImageDatabase = new AugmentedImageDatabase(session);

      for (int i = 0; i < Image_list.length; i++) {

        // For each item in the Image_list, add the image to the database

        Bitmap augmentedImageBitmap = loadAugmentedImageBitmap(assetManager, Image_list[i] + ".jpg");
        if (augmentedImageBitmap == null) {
          return false;
        }
        augmentedImageDatabase.addImage(Image_list[i] + ".jpg", augmentedImageBitmap);
      }
      // If the physical size of the image is known, you can instead use:
      //     augmentedImageDatabase.addImage("image_name", augmentedImageBitmap, widthInMeters);
      // This will improve the initial detection speed. ARCore will still actively estimate the
      // physical size of the image as it is viewed from multiple viewpoints.

      config.setAugmentedImageDatabase(augmentedImageDatabase);
      return true;
    }
    else{
      // This is an alternative way to initialize an AugmentedImageDatabase instance,
      // load a pre-existing augmented image database.
      try (InputStream is = getContext().getAssets().open(SAMPLE_IMAGE_DATABASE)) {
        augmentedImageDatabase = AugmentedImageDatabase.deserialize(session, is);
      } catch (IOException e) {
        Log.e(TAG, "IO exception loading augmented image database.", e);
        return false;
      }
    }

    config.setAugmentedImageDatabase(augmentedImageDatabase);
    return true;
  }

  private Bitmap loadAugmentedImageBitmap (AssetManager assetManager, String IMAGE_NAME){
    try (InputStream is = assetManager.open(IMAGE_NAME)) {
      return BitmapFactory.decodeStream(is);
    } catch (IOException e) {
      Log.e(TAG, "IO exception loading augmented image bitmap.", e);
    }
    return null;
  }
}
