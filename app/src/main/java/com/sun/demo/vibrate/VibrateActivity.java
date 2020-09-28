package com.sun.demo.vibrate;

import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sun.demo.R;
/*
~ Nilesh Deokar @nieldeokar on 03/06/18 11:22 PM
*/

/*
*  To test this :
*  1. Paste this activity inside your project.
*  2. Paste `activity_simple_vibrate_demo.xml` into resources folder.
*  3. Add entry of SimpleVibrateDemoActivity inside AndroidManifest.xml
*  4. Add Vibrate permission inside AndroidManifest.xml
*
*   <uses-permission android:name="android.permission.VIBRATE" />
*
*  5. Build & run.  
* */


public class VibrateActivity extends AppCompatActivity {

    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibrate);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            // Enable buttons

            // Enable new api buttons
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            }
        } else {
            Toast.makeText(this, "Device does not support vibration", Toast.LENGTH_SHORT).show();
        }
    }

    private void vibrateFor500ms() {
        vibrator.vibrate(500);
    }

    private void customVibratePatternNoRepeat() {

        // 0 : Start without a delay
        // 400 : Vibrate for 400 milliseconds
        // 200 : Pause for 200 milliseconds
        // 400 : Vibrate for 400 milliseconds
        long[] mVibratePattern = new long[]{0, 400, 200, 400};

        // -1 : Do not repeat this pattern
        // pass 0 if you want to repeat this pattern from 0th index
        vibrator.vibrate(mVibratePattern, -1);

    }

    private void customVibratePatternRepeatFromSpecificIndex() {
        long[] mVibratePattern = new long[]{0, 400, 800, 600, 800, 800, 800, 1000};

        // 3 : Repeat this pattern from 3rd element of an array
        vibrator.vibrate(mVibratePattern, 3);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createOneShotVibrationUsingVibrationEffect() {
        // 1000 : Vibrate for 1 sec
        // VibrationEffect.DEFAULT_AMPLITUDE - would perform vibration at full strength
        VibrationEffect effect = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);
        vibrator.vibrate(effect);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createWaveFormVibrationUsingVibrationEffect() {
        long[] mVibratePattern = new long[]{0, 400, 1000, 600, 1000, 800, 1000, 1000};
        // -1 : Play exactly once
        VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern, -1);
        vibrator.vibrate(effect);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createWaveFormVibrationUsingVibrationEffectAndAmplitude() {

        long[] mVibratePattern = new long[]{0, 400, 800, 600, 800, 800, 800, 1000};
        int[] mAmplitudes = new int[]{0, 255, 0, 255, 0, 255, 0, 255};
        // -1 : Play exactly once

        if (vibrator.hasAmplitudeControl()) {
            VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern, mAmplitudes, -1);
            vibrator.vibrate(effect);
        }
    }

    public void onVibrateFor500ms(View view) {
        vibrateFor500ms();
    }

    public void onCustomVibratePatternNoRepeat(View view) {
        customVibratePatternNoRepeat();
    }

    public void onCustomVibratePatternRepeatFromSpecificIndex(View view) {
        customVibratePatternRepeatFromSpecificIndex();
    }

    public void onCreateOneShotVibrationUsingVibrationEffect(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createOneShotVibrationUsingVibrationEffect();
        }
    }

    public void onCreateWaveFormVibrationUsingVibrationEffect(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createWaveFormVibrationUsingVibrationEffect();
        }
    }

    public void onCreateWaveFormVibrationUsingVibrationEffectAndAmplitude(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createWaveFormVibrationUsingVibrationEffectAndAmplitude();
        }
    }
}
