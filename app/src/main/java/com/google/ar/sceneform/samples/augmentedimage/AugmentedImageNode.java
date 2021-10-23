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

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import com.google.ar.core.AugmentedImage;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.ExternalTexture;
import com.google.ar.sceneform.rendering.ModelRenderable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


/**
 * Node for rendering an augmented image. The image is framed by placing the virtual picture frame
 * at the corners of the augmented image trackable.
 */
@SuppressWarnings({"AndroidApiChecker"})

public class AugmentedImageNode extends AnchorNode {

    private static final String TAG = "AugmentedImageNode";

    // The augmented image represented by this node.
    private AugmentedImage augmentedImage;

    // We use completable futures here to simplify
    // the error handling and asynchronous loading.  The loading is started with the
    // first construction of an instance, and then used when the image is set.

    // Each obj file needs its own CompletableFuture, regardless of whether or not
    // the obj files are being used on the same augmented image or not (ex: 4 obj files for each of the
    // corners of a frame and each needs its own CompletableFuture)


    // List of CompletableFutures, are used to add new renderables.
    // Add new CompletableFuture for each OBJ file.
    //ARTGallery Renderables
    private static CompletableFuture<ModelRenderable> sushi;
    private static CompletableFuture<ModelRenderable> fancyballroom;
    private static CompletableFuture<ModelRenderable> fancyballroom_text;
    private static CompletableFuture<ModelRenderable> beachflag;
    private static CompletableFuture<ModelRenderable> sunsetmonorail;
    private static CompletableFuture<ModelRenderable> bigger_elephant;
    private static CompletableFuture<ModelRenderable> lavaeye;
    private static CompletableFuture<ModelRenderable> firebreathingchicken;
    private static CompletableFuture<ModelRenderable> beachcroc;
    private static CompletableFuture<ModelRenderable> beachcroc_text;
    private static CompletableFuture<ModelRenderable> skater;
    private static CompletableFuture<ModelRenderable> skater_text;
    private static CompletableFuture<ModelRenderable> ufosighting;
    private static CompletableFuture<ModelRenderable> waterfall;
    private static CompletableFuture<ModelRenderable> couple;
    private static CompletableFuture<ModelRenderable> birds;
    private static CompletableFuture<ModelRenderable> frame;
    private static CompletableFuture<ModelRenderable> frame_ul;
    private static CompletableFuture<ModelRenderable> frame_ur;
    private static CompletableFuture<ModelRenderable> frame_ll;
    private static CompletableFuture<ModelRenderable> frame_lr;
    //east side gallery renderables
    private static CompletableFuture<ModelRenderable> afrikanische_weisheit;
    private static CompletableFuture<ModelRenderable> andrej_sacharow;
    private static CompletableFuture<ModelRenderable> berlin;
    private static CompletableFuture<ModelRenderable> blue_face;
    private static CompletableFuture<ModelRenderable> bunny_windows;
    private static CompletableFuture<ModelRenderable> communist_kiss;
    private static CompletableFuture<ModelRenderable> curriculum_vita;
    private static CompletableFuture<ModelRenderable> dog;
    private static CompletableFuture<ModelRenderable> erich_fried;
    private static CompletableFuture<ModelRenderable> escaping_the_east;
    private static CompletableFuture<ModelRenderable> forest;
    private static CompletableFuture<ModelRenderable> garbage_day;
    private static CompletableFuture<ModelRenderable> nathan_jones;
    private static CompletableFuture<ModelRenderable> obscure_faces;
    private static CompletableFuture<ModelRenderable> red_curtain;
    private static CompletableFuture<ModelRenderable> robot_war;
    private static CompletableFuture<ModelRenderable> seven_stages;
    private static CompletableFuture<ModelRenderable> thumbs_up;
    private static CompletableFuture<ModelRenderable> tolerance;
    private static CompletableFuture<ModelRenderable> uaine;
    private static CompletableFuture<ModelRenderable> wall_car;
    private static CompletableFuture<ModelRenderable> women_in_power;
//code.org renderables
    private static CompletableFuture<ModelRenderable> ashton_kutcher_ian;
    private static CompletableFuture<ModelRenderable> dumbbell;
    private static CompletableFuture<ModelRenderable> flag_ks;
    private static CompletableFuture<ModelRenderable> geeksphone_firefoxos;
    private static CompletableFuture<ModelRenderable> heatjerseybosh_codeposter;
    private static CompletableFuture<ModelRenderable> i_am_malala_book;
    private static CompletableFuture<ModelRenderable> lightbulb_ks;
    private static CompletableFuture<ModelRenderable> loomia_logo;
    private static CompletableFuture<ModelRenderable> mark_poster;
    private static CompletableFuture<ModelRenderable> obama_campaignlogo_ks;
    private static CompletableFuture<ModelRenderable> rk_shovel;
    private static CompletableFuture<ModelRenderable> youtube_logo;
    //plymouth400 renderables
    private static CompletableFuture<ModelRenderable> burial_hill;
    private static CompletableFuture<ModelRenderable> coles_hill;
    private static CompletableFuture<ModelRenderable> courthouse_1749;
    private static CompletableFuture<ModelRenderable> courthouse_1820;
    private static CompletableFuture<ModelRenderable> forefathers_monument;
    private static CompletableFuture<ModelRenderable> harlow_house_museum;
    private static CompletableFuture<ModelRenderable> hedge_house;
    private static CompletableFuture<ModelRenderable> howland_house;
    private static CompletableFuture<ModelRenderable> immigrant_monument;
    private static CompletableFuture<ModelRenderable> jenney_grist_mill;
    private static CompletableFuture<ModelRenderable> king_philips_war_plaque;
    private static CompletableFuture<ModelRenderable> leyden_st;
    private static CompletableFuture<ModelRenderable> massasoit_statue;
    private static CompletableFuture<ModelRenderable> mayflower_ii;
    private static CompletableFuture<ModelRenderable> mayflower_society_house;
    private static CompletableFuture<ModelRenderable> pilgrim_hall_museum;
    private static CompletableFuture<ModelRenderable> pilgrim_maiden_statue;
    private static CompletableFuture<ModelRenderable> pilgrim_mother;
    private static CompletableFuture<ModelRenderable> plimouth_plantation;
    private static CompletableFuture<ModelRenderable> plymouth_rock;
    private static CompletableFuture<ModelRenderable> sparrow_house;
    private static CompletableFuture<ModelRenderable> spooner_house_museum;
    private static CompletableFuture<ModelRenderable> town_brook;
    private static CompletableFuture<ModelRenderable> town_square;
    private static CompletableFuture<ModelRenderable> training_green;
    private static CompletableFuture<ModelRenderable> william_bradford_statue;


    public MediaPlayer nodeMediaPlayer;
    public Node videoNode;
    public ExternalTexture texture = new ExternalTexture();


    // make sure the order of this list is the same as the order of imageList (which can be found in Fragment)

    // List of renderables, used for effiency.
    public ArrayList<CompletableFuture<ModelRenderable>> renderableList = new ArrayList<CompletableFuture<ModelRenderable>>(Arrays.asList(
            afrikanische_weisheit,
            andrej_sacharow,
            ashton_kutcher_ian,
            beachcroc,
            beachcroc_text,
            beachflag,
            berlin,
            bigger_elephant,
            birds,
            blue_face,
            bunny_windows,
            burial_hill,
            coles_hill,
            communist_kiss, // should be sunset monorail
            couple,
            courthouse_1749,
            courthouse_1820,
            curriculum_vita,
            dog,
            dumbbell,
            erich_fried,
            escaping_the_east,
            fancyballroom,
            fancyballroom_text,
            firebreathingchicken,
            flag_ks,
            forefathers_monument,
            forest,
            garbage_day,
            geeksphone_firefoxos,
            harlow_house_museum,
            heatjerseybosh_codeposter, // should be sunset monorail
            hedge_house,
            howland_house,
            i_am_malala_book,
            immigrant_monument,
            jenney_grist_mill,
            king_philips_war_plaque,
            lavaeye,
            leyden_st,
            lightbulb_ks,
            loomia_logo,
            mark_poster,
            massasoit_statue,
            mayflower_ii,
            mayflower_society_house,
            nathan_jones,
            obama_campaignlogo_ks,
            obscure_faces,
            pilgrim_hall_museum,
            pilgrim_maiden_statue,
            pilgrim_mother,
            plimouth_plantation,
            plymouth_rock,
            red_curtain,
            rk_shovel,
            robot_war,
            seven_stages,
            skater,
            skater_text,
            sparrow_house,
            spooner_house_museum,
            sunsetmonorail,
            sushi,
            thumbs_up,
            tolerance,
            town_brook,
            town_square,
            training_green,
            uaine,
            ufosighting,
            wall_car,
            waterfall,
            william_bradford_statue,
            women_in_power,
            youtube_logo));


    // Change the numbers in order to translate the position of the renderable
    public static Vector3[] nodePosition = {
            new Vector3 (0.0f, 0.0f, 0.0f), //beachcroc_text
            new Vector3 (0.0f, 0.0f, 0.0f), //beachcroc
            new Vector3 (0.0f, 0.0f, 0.0f), //beachflag
            new Vector3 (0.0f, 0.0f, 0.0f), //bigger_elephant
            new Vector3 (0.0f, 0.0f, 0.0f), //birds
            new Vector3 (0.0f, 0.0f, 0.0f), //couple
            new Vector3 (0.0f, 0.0f, 0.0f), //fancyballroom_text
            new Vector3 (0.0f, 0.0f, 0.0f), //fancyballroom
            new Vector3 (0.0f, 0.0f, 0.0f), //firebreathingchicken
            new Vector3 (0.0f, 0.0f, 0.0f), //lavaeye
            new Vector3 (0.0f, 0.0f, 0.0f), //skater_text
            new Vector3 (0.0f, 0.0f, 0.0f), //skater
            new Vector3 (0.0f, 0.0f, 0.0f), //sunsetmonorail
            new Vector3 (0.0f, 0.0f, 0.0f), //sushi
            new Vector3 (0.0f, 0.0f, 0.0f), //ufosighting
            new Vector3 (0.0f, 0.0f, 0.0f), //waterfall
            new Vector3 (0.0f, 0.0f, 0.0f), //fancyballroom
            new Vector3 (0.0f, 0.0f, 0.0f), //firebreathingchicken
            new Vector3 (0.0f, 0.0f, 0.0f), //lavaeye
            new Vector3 (0.0f, 0.0f, 0.0f), //skater_text
            new Vector3 (0.0f, 0.0f, 0.0f), //skater
            new Vector3 (0.0f, 0.0f, 0.0f), //sunsetmonorail
            new Vector3 (0.0f, 0.0f, 0.0f), //sushi
            new Vector3 (0.0f, 0.0f, 0.0f), //ufosighting
            new Vector3 (0.0f, 0.0f, 0.0f),  //waterfall
            new Vector3 (0.0f, 0.0f, 0.0f), //beachcroc_text
            new Vector3 (0.0f, 0.0f, 0.0f), //beachcroc
            new Vector3 (0.0f, 0.0f, 0.0f), //beachflag
            new Vector3 (0.0f, 0.0f, 0.0f), //bigger_elephant
            new Vector3 (0.0f, 0.0f, 0.0f), //birds
            new Vector3 (0.0f, 0.0f, 0.0f), //couple
            new Vector3 (0.0f, 0.0f, 0.0f), //fancyballroom_text
            new Vector3 (0.0f, 0.0f, 0.0f), //fancyballroom
            new Vector3 (0.0f, 0.0f, 0.0f), //firebreathingchicken
            new Vector3 (0.0f, 0.0f, 0.0f), //lavaeye
            new Vector3 (0.0f, 0.0f, 0.0f), //skater_text
            new Vector3 (0.0f, 0.0f, 0.0f), //skater
            new Vector3 (0.0f, 0.0f, 0.0f), //sunsetmonorail
            new Vector3 (0.0f, 0.0f, 0.0f), //sushi
            new Vector3 (0.0f, 0.0f, 0.0f), //ufosighting
            new Vector3 (0.0f, 0.0f, 0.0f), //waterfall
            new Vector3 (0.0f, 0.0f, 0.0f), //fancyballroom
            new Vector3 (0.0f, 0.0f, 0.0f), //firebreathingchicken
            new Vector3 (0.0f, 0.0f, 0.0f), //lavaeye
            new Vector3 (0.0f, 0.0f, 0.0f), //skater_text
            new Vector3 (0.0f, 0.0f, 0.0f), //skater
            new Vector3 (0.0f, 0.0f, 0.0f), //sunsetmonorail
            new Vector3 (0.0f, 0.0f, 0.0f), //sushi
            new Vector3 (0.0f, 0.0f, 0.0f), //ufosighting
            new Vector3 (0.0f, 0.0f, 0.0f),  //waterfall
            new Vector3 (0.0f, 0.0f, 0.0f), //beachcroc_text
            new Vector3 (0.0f, 0.0f, 0.0f), //beachcroc
            new Vector3 (0.0f, 0.0f, 0.0f), //beachflag
            new Vector3 (0.0f, 0.0f, 0.0f), //bigger_elephant
            new Vector3 (0.0f, 0.0f, 0.0f), //birds
            new Vector3 (0.0f, 0.0f, 0.0f), //couple
            new Vector3 (0.0f, 0.0f, 0.0f), //fancyballroom_text
            new Vector3 (0.0f, 0.0f, 0.0f), //fancyballroom
            new Vector3 (0.0f, 0.0f, 0.0f), //firebreathingchicken
            new Vector3 (0.0f, 0.0f, 0.0f), //lavaeye
            new Vector3 (0.0f, 0.0f, 0.0f), //skater_text
            new Vector3 (0.0f, 0.0f, 0.0f), //skater
            new Vector3 (0.0f, 0.0f, 0.0f), //sunsetmonorail
            new Vector3 (0.0f, 0.0f, 0.0f), //sushi
            new Vector3 (0.0f, 0.0f, 0.0f), //ufosighting
            new Vector3 (0.0f, 0.0f, 0.0f), //waterfall
            new Vector3 (0.0f, 0.0f, 0.0f), //fancyballroom
            new Vector3 (0.0f, 0.0f, 0.0f), //firebreathingchicken
            new Vector3 (0.0f, 0.0f, 0.0f), //lavaeye
            new Vector3 (0.0f, 0.0f, 0.0f), //skater_text
            new Vector3 (0.0f, 0.0f, 0.0f), //skater
            new Vector3 (0.0f, 0.0f, 0.0f), //sunsetmonorail
            new Vector3 (0.0f, 0.0f, 0.0f), //sushi
            new Vector3 (0.0f, 0.0f, 0.0f), //ufosighting
            new Vector3 (0.0f, 0.0f, 0.0f),  //waterfall
            new Vector3 (0.0f, 0.0f, 0.0f), //beachcroc_text

    };

    // Change the numbers to rotate the renderable
    public static Vector4[] nodeRotation = {
            new Vector4 (1, 0, 0, 0f), //beachcroc_text
            new Vector4 (1, 0, 0, 0f), //beachcroc
            new Vector4 (0, 0, 0, 0f), //beachflag
            new Vector4 (0, 0, 0, 0f), //bigger_elephant
            new Vector4 (0, 0, 0, 0f), //birds
            new Vector4 (0, 0, 0, 0f), //couple
            new Vector4 (0, 0, 0, 0f), //fancyballroom_text
            new Vector4 (0, 0, 0, 0f), //fancyballroom
            new Vector4 (0, 0, 0, 0f), //firebreathingchicken
            new Vector4 (0, 0, 0, 0f), //lavaeye
            new Vector4 (0, 0, 0, 0f), //skater_text
            new Vector4 (0, 0, 0, 0f), //skater
            new Vector4 (0, 0, 0, 0f), //sunsetmonorail
            new Vector4 (0, 0, 0, 0f), //sushi
            new Vector4 (0, 0, 0, 0f), //ufosighting
            new Vector4 (0, 0, 0, 0f),  //waterfall
            new Vector4 (0, 0, 0, 0f), //fancyballroom
            new Vector4 (0, 0, 0, 0f), //firebreathingchicken
            new Vector4 (0, 0, 0, 0f), //lavaeye
            new Vector4 (0, 0, 0, 0f), //skater_text
            new Vector4 (0, 0, 0, 0f), //skater
            new Vector4 (0, 0, 0, 0f), //sunsetmonorail
            new Vector4 (0, 0, 0, 0f), //sushi
            new Vector4 (0, 0, 0, 0f), //ufosighting
            new Vector4 (0, 0, 0, 0f),  //waterfall
            new Vector4 (1, 0, 0, 0f), //beachcroc_text
            new Vector4 (1, 0, 0, 0f), //beachcroc
            new Vector4 (0, 0, 0, 0f), //beachflag
            new Vector4 (0, 0, 0, 0f), //bigger_elephant
            new Vector4 (0, 0, 0, 0f), //birds
            new Vector4 (0, 0, 0, 0f), //couple
            new Vector4 (0, 0, 0, 0f), //fancyballroom_text
            new Vector4 (0, 0, 0, 0f), //fancyballroom
            new Vector4 (0, 0, 0, 0f), //firebreathingchicken
            new Vector4 (0, 0, 0, 0f), //lavaeye
            new Vector4 (0, 0, 0, 0f), //skater_text
            new Vector4 (0, 0, 0, 0f), //skater
            new Vector4 (0, 0, 0, 0f), //sunsetmonorail
            new Vector4 (0, 0, 0, 0f), //sushi
            new Vector4 (0, 0, 0, 0f), //ufosighting
            new Vector4 (0, 0, 0, 0f),  //waterfall
            new Vector4 (0, 0, 0, 0f), //fancyballroom
            new Vector4 (0, 0, 0, 0f), //firebreathingchicken
            new Vector4 (0, 0, 0, 0f), //lavaeye
            new Vector4 (0, 0, 0, 0f), //skater_text
            new Vector4 (0, 0, 0, 0f), //skater
            new Vector4 (0, 0, 0, 0f), //sunsetmonorail
            new Vector4 (0, 0, 0, 0f), //sushi
            new Vector4 (0, 0, 0, 0f), //ufosighting
            new Vector4 (0, 0, 0, 0f),  //waterfall
            new Vector4 (1, 0, 0, 0f), //beachcroc_text
            new Vector4 (1, 0, 0, 0f), //beachcroc
            new Vector4 (0, 0, 0, 0f), //beachflag
            new Vector4 (0, 0, 0, 0f), //bigger_elephant
            new Vector4 (0, 0, 0, 0f), //birds
            new Vector4 (0, 0, 0, 0f), //couple
            new Vector4 (0, 0, 0, 0f), //fancyballroom_text
            new Vector4 (0, 0, 0, 0f), //fancyballroom
            new Vector4 (0, 0, 0, 0f), //firebreathingchicken
            new Vector4 (0, 0, 0, 0f), //lavaeye
            new Vector4 (0, 0, 0, 0f), //skater_text
            new Vector4 (0, 0, 0, 0f), //skater
            new Vector4 (0, 0, 0, 0f), //sunsetmonorail
            new Vector4 (0, 0, 0, 0f), //sushi
            new Vector4 (0, 0, 0, 0f), //ufosighting
            new Vector4 (0, 0, 0, 0f), //waterfall
            new Vector4 (0, 0, 0, 0f), //fancyballroom
            new Vector4 (0, 0, 0, 0f), //firebreathingchicken
            new Vector4 (0, 0, 0, 0f), //lavaeye
            new Vector4 (0, 0, 0, 0f), //skater_text
            new Vector4 (0, 0, 0, 0f), //skater
            new Vector4 (0, 0, 0, 0f), //sunsetmonorail
            new Vector4 (0, 0, 0, 0f), //sushi
            new Vector4 (0, 0, 0, 0f), //ufosighting
            new Vector4 (0, 0, 0, 0f),  //waterfall
            new Vector4 (0, 0, 0, 0f)  //waterfall
    };

// currentRenderable is the generic renderable variable used to store the one renderable being loaded (in AugmentedImageNode())
    public CompletableFuture<ModelRenderable> currentRenderable;

    // The augmented image represented by this node.
    private AugmentedImage image;

    // The color to filter out of the video.
    private static final Color CHROMA_KEY_COLOR = new Color(0.1843f, 1.0f, 0.098f);

    // Controls the height of the video in world space.
    private static final float VIDEO_HEIGHT_METERS = 0.2f;

    // Creates an AugmentedImageNode object. Uses the list of images (see AugmentedImageFragment) to fetch renderables
    // augmentedImageIndex is used to index imageList.
    public AugmentedImageNode(Context context, String[] imageList, Integer augmentedImageIndex) {

        // we check if sushi is null
        // sushi is used in order to check if the renderables are null
        // because the renderables are built using the same block of code all at once, if sushi (or any other completable future) is null,
        // then they all are null
        if (sushi == null) {

            if (!(AugmentedImageFragment.imagePlaysVideoBooleanList[augmentedImageIndex])) {
                currentRenderable = renderableList.get(augmentedImageIndex);
                currentRenderable =
                        // build the renderable using the image that is detected
                        ModelRenderable.builder()
                                .setSource(context, Uri.parse("models/" + imageList[augmentedImageIndex] + ".sfb"))
                                .build();
                Log.d("modelrenderable", ("conditional is running! filepath: " + Uri.parse("models/" + imageList[augmentedImageIndex] + ".sfb")));

            } else {

                frame_ul =
                        ModelRenderable.builder()
                                .setSource(context, Uri.parse("models/frame_upper_left.sfb"))
                                .build();
                Log.d("running", ("running"));
                frame_ur =
                        ModelRenderable.builder()
                                .setSource(context, Uri.parse("models/frame_upper_right.sfb"))
                                .build();
                frame_ll =
                        ModelRenderable.builder()
                                .setSource(context, Uri.parse("models/frame_lower_left.sfb"))
                                .build();
                frame_lr =
                        ModelRenderable.builder()
                                .setSource(context, Uri.parse("models/frame_lower_right.sfb"))
                                .build();

            }


        }

        // Checks if videoRenderable is null, if it is, videoRenderable is loaded from models.
        // Will be used as the thing that the video is placed on.
        if (AugmentedImageActivity.videoRenderable == null) {
            AugmentedImageActivity.videoRenderable =
                    ModelRenderable.builder()
                            .setSource(context, Uri.parse("models/chroma_key_video.sfb"))
                            .build();
        }
    }

    /**
     * Called when the AugmentedImage is detected and should be rendered. A Sceneform node tree is
     * created based on an Anchor created from the image. The corners are then positioned based on the
     * extents of the image. There is no need to worry about world coordinates since everything is
     * relative to the center of the image, which is the parent node of the corners.
     */

    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    // setImage is used to place an animated file.
    // setImage takes arguments image, and augmentedImageIndex, which is used to index the list of renderables.
    public void setImage(AugmentedImage image, Integer augmentedImageIndex) {
        this.augmentedImage = image;

        if (!currentRenderable.isDone()) {
            CompletableFuture.allOf(currentRenderable)
                    // in the case that more than one renderable is used to create a scene,
                    // use CompletableFuture.allOf(currentRenderable, other renderables needed for the scene)
                    .thenAccept((Void aVoid) -> setImage(image, augmentedImageIndex))
                    .exceptionally(
                            throwable -> {
                                Log.e(TAG, "Exception loading", throwable);
                                return null;
                            });
            }

        // Set the anchor based on the center of the image.
        setAnchor(image.createAnchor(image.getCenterPose()));


        // creates the node
        // in the case that there is more than one renderable in the scene, run the code again, replacing
        // currentRenderable with the other renderables

        Node fullnode;

        fullnode = new Node();
        fullnode.setParent(this);
        fullnode.setWorldPosition(new Vector3((this.getWorldPosition().x + (nodePosition[augmentedImageIndex].x)), (this.getWorldPosition().y + (nodePosition[augmentedImageIndex].y)), (this.getWorldPosition().z + (nodePosition[augmentedImageIndex].z))));
        fullnode.setLocalScale(new Vector3(0.1f, 0.1f, 0.1f));
        Quaternion newQuaternion = Quaternion.axisAngle(new Vector3(nodeRotation[augmentedImageIndex].x, nodeRotation[augmentedImageIndex].y, nodeRotation[augmentedImageIndex].z), nodeRotation[augmentedImageIndex].q);
        fullnode.setLocalRotation(Quaternion.multiply(fullnode.getLocalRotation(), newQuaternion));
        fullnode.setRenderable(currentRenderable.getNow(null));

        }

    public void startVideo(Context context, int augmentedImageIndex, ExternalTexture texture) {
        nodeMediaPlayer = MediaPlayer.create(context, AugmentedImageFragment.Video_list[augmentedImageIndex]);
        nodeMediaPlayer.setSurface(texture.getSurface());
        nodeMediaPlayer.setLooping(true);
    }

    // Used to stop the video.
    public void stopVideo() {
        try {
            texture.getSurfaceTexture().release();
            nodeMediaPlayer.reset();
            nodeMediaPlayer.prepare();
            nodeMediaPlayer.stop();
            nodeMediaPlayer.release();
            nodeMediaPlayer = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Used to place the renderable on the image (video is also placed on the renderable)
    public void createVideo(Context context, AugmentedImage image, int augmentedImageIndex) {
        this.image = image;



        CompletableFuture.allOf(AugmentedImageActivity.videoRenderable)
                .handle((notUsed, throwable) -> {
                    // When you build a Renderable, Sceneform loads its resources in the background while
                    // returning a CompletableFuture. Call handle(), thenAccept(), or check isDone()
                    // before calling get().

                    if (throwable != null) {
                        return null;
                    }

                    try {
                        // Sets the texture to the texture defined using (new ExternalTexture()) -> the video's surface is on that texture.
                        AugmentedImageActivity.videoPlacedRenderable = AugmentedImageActivity.videoRenderable.get();
                        AugmentedImageActivity.videoPlacedRenderable.getMaterial().setExternalTexture("videoTexture", texture);
                        AugmentedImageActivity.videoPlacedRenderable.getMaterial().setFloat4("keyColor", CHROMA_KEY_COLOR);

                        // Everything finished loading successfully.
                    } catch (InterruptedException | ExecutionException ex) {
                    }

                    return null;
                });

        if (!frame_lr.isDone() || !frame_ll.isDone() || !frame_ur.isDone() || !frame_ul.isDone()) {
            CompletableFuture.allOf(frame_ll, frame_lr, frame_ul, frame_ur)
                    .thenAccept((Void aVoid) -> setImage(image, augmentedImageIndex))
                    .exceptionally(
                            throwable -> {
                                Log.e(TAG, "Exception loading", throwable);
                                return null;
                            });
        }

        Node fullnode;

        fullnode = new Node();
        fullnode.setParent(this);
        fullnode.setWorldPosition(new Vector3(-0.5f * image.getExtentX(), 0.0f, 0.5f * image.getExtentZ()));
        fullnode.setLocalScale(new Vector3(0.1f, 0.1f, 0.1f));
        Quaternion lowerLeft = Quaternion.axisAngle(new Vector3(nodeRotation[augmentedImageIndex].x, nodeRotation[augmentedImageIndex].y, nodeRotation[augmentedImageIndex].z), nodeRotation[augmentedImageIndex].q);
        fullnode.setLocalRotation(Quaternion.multiply(fullnode.getLocalRotation(), lowerLeft));
        fullnode.setRenderable(frame_ll.getNow(null));

        fullnode = new Node();
        fullnode.setParent(this);
        fullnode.setWorldPosition(new Vector3(0.5f * image.getExtentX(), 0.0f, 0.5f * image.getExtentZ()));
        fullnode.setLocalScale(new Vector3(0.1f, 0.1f, 0.1f));
        Quaternion lowerRight = Quaternion.axisAngle(new Vector3(nodeRotation[augmentedImageIndex].x, nodeRotation[augmentedImageIndex].y, nodeRotation[augmentedImageIndex].z), nodeRotation[augmentedImageIndex].q);
        fullnode.setLocalRotation(Quaternion.multiply(fullnode.getLocalRotation(), lowerRight));
        fullnode.setRenderable(frame_lr.getNow(null));

        fullnode = new Node();
        fullnode.setParent(this);
        fullnode.setWorldPosition(new Vector3(-0.5f * image.getExtentX(), 0.0f, -0.5f * image.getExtentZ()));
        fullnode.setLocalScale(new Vector3(0.1f, 0.1f, 0.1f));
        Quaternion upperLeft = Quaternion.axisAngle(new Vector3(nodeRotation[augmentedImageIndex].x, nodeRotation[augmentedImageIndex].y, nodeRotation[augmentedImageIndex].z), nodeRotation[augmentedImageIndex].q);
        fullnode.setLocalRotation(Quaternion.multiply(fullnode.getLocalRotation(), upperLeft));
        fullnode.setRenderable(frame_ul.getNow(null));

        fullnode = new Node();
        fullnode.setParent(this);
        fullnode.setWorldPosition(new Vector3(0.5f * image.getExtentX(), 0.0f, -0.5f * image.getExtentZ()));
        fullnode.setLocalScale(new Vector3(0.1f, 0.1f, 0.1f));
        Quaternion upperRight = Quaternion.axisAngle(new Vector3(nodeRotation[augmentedImageIndex].x, nodeRotation[augmentedImageIndex].y, nodeRotation[augmentedImageIndex].z), nodeRotation[augmentedImageIndex].q);
        fullnode.setLocalRotation(Quaternion.multiply(fullnode.getLocalRotation(), upperRight));
        fullnode.setRenderable(frame_ur.getNow(null));

        // augmentedImageIndex is taken from the arguments of this method - (setImage)
        AugmentedImageActivity.augmentedImageVideoPlayerIndex = augmentedImageIndex;
        // Set the anchor based on the center of the image.
        setAnchor(image.createAnchor(image.getCenterPose()));


        // videoNode is created and will be placed on the image, directly parallel to the image.
        videoNode = new Node();
        videoNode.setParent(this);


        float videoWidth = nodeMediaPlayer.getVideoWidth();
        float videoHeight = nodeMediaPlayer.getVideoHeight();

        videoNode.setLocalScale(
                new Vector3(
                        VIDEO_HEIGHT_METERS * (videoWidth / videoHeight), VIDEO_HEIGHT_METERS, 1.4f));


        // Sets the video's position from perpendicular to the image to parallel to the detected image.
        Quaternion q1 = videoNode.getLocalRotation();
        Quaternion q2 = Quaternion.axisAngle(new Vector3(1.0f, 0f, 0f), -90f);
        videoNode.setLocalPosition(new Vector3(0.0f, 0.0f, 0.07f)); // x, z, y
        videoNode.setLocalRotation(Quaternion.multiply(q1, q2));

        // If the video is not playing, it will be started.
        if (!nodeMediaPlayer.isPlaying()) {
            nodeMediaPlayer.start();
            // Wait to set the renderable until the first frame of the  video becomes available.
            // This prevents the renderable from briefly appearing as a black quad before the video
            // plays.
            texture
                    .getSurfaceTexture()
                    .setOnFrameAvailableListener(
                            (SurfaceTexture surfaceTexture) -> {
                                videoNode.setRenderable(AugmentedImageActivity.videoPlacedRenderable);
                                texture.getSurfaceTexture().setOnFrameAvailableListener(null);

                            });
        } else {
            videoNode.setRenderable(AugmentedImageActivity.videoPlacedRenderable);
        }

        Log.d("node", "creating!!!");

    }
}
