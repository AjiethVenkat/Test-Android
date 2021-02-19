package com.datechnologies.androidtest.animation;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import com.skyfishjy.library.RippleBackground;


/**
 * Screen that displays the D & A Technologies logo.
 * The icon can be moved around on the screen as well as animated.
 */

public class AnimationActivity extends AppCompatActivity {

    //==============================================================================================
    // Class Properties
    //==============================================================================================

    ImageView image;
    private GestureDetectorCompat mDetector;

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context) {
        Intent starter = new Intent(context, AnimationActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        setTitle("Animation");

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked

        // TODO: When the fade button is clicked, you must animate the D & A Technologies logo.
        // TODO: It should fade from 100% alpha to 0% alpha, and then from 0% alpha to 100% alpha

        // TODO: The user should be able to touch and drag the D & A Technologies logo around the screen.

        // TODO: Add a bonus to make yourself stick out. Music, color, fireworks, explosions!!!

        image = (ImageView) findViewById(R.id.imageView);

        /* Final variable for Ripple Background and Music with MediaPlayer */
        final RippleBackground rippleBackground = (RippleBackground) findViewById(R.id.content);
        final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.faded);


        /* Start ripple animation */
        //rippleBackground.startRippleAnimation();

        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    ClipData data = ClipData.newPlainText("", "");

                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(image);
                    image.startDrag(data, shadowBuilder, image, 0);

                    /* Visibility of the image View */
                    image.setVisibility(View.VISIBLE);

                    /* Stop ripple animation */
                    rippleBackground.stopRippleAnimation();

                    mediaPlayer.stop();

                    return true;

                } else {

                    return false;

                }
            }
        });

        /* FadeIn button Onclick listener */
        Button fadeButton = (Button) findViewById(R.id.buttonFade);

        fadeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animfade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ic_fade_effect);

                image.startAnimation(animfade);

                /* Start ripple animation */
                rippleBackground.startRippleAnimation();

                /* Music start playing - Alan Walker (faded) */
                mediaPlayer.start();
            }
        });


    }




    /* Call back the stack on AppBar back button */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
