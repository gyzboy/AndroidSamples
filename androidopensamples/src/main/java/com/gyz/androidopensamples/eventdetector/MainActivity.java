/*
 * Copyright (C) 2016 Nishant Srivastava
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gyz.androidopensamples.eventdetector;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.gyz.androidopensamples.R;
import com.gyz.androidopensamples.eventdetector.sensey.Sensey;
import java.text.DecimalFormat;

import static com.gyz.androidopensamples.eventdetector.sensey.ChopDetector.ChopListener;
import static com.gyz.androidopensamples.eventdetector.sensey.FlipDetector.FlipListener;
import static com.gyz.androidopensamples.eventdetector.sensey.LightDetector.LightListener;
import static com.gyz.androidopensamples.eventdetector.sensey.MovementDetector.MovementListener;
import static com.gyz.androidopensamples.eventdetector.sensey.OrientationDetector.OrientationListener;
import static com.gyz.androidopensamples.eventdetector.sensey.ProximityDetector.ProximityListener;
import static com.gyz.androidopensamples.eventdetector.sensey.ShakeDetector.ShakeListener;
import static com.gyz.androidopensamples.eventdetector.sensey.SoundLevelDetector.SoundLevelListener;
import static com.gyz.androidopensamples.eventdetector.sensey.WaveDetector.WaveListener;
import static com.gyz.androidopensamples.eventdetector.sensey.WristTwistDetector.WristTwistListener;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity
    implements OnCheckedChangeListener, ShakeListener, FlipListener, LightListener,
    OrientationListener, ProximityListener, WaveListener, SoundLevelListener, MovementListener,
    ChopListener, WristTwistListener {

  private static final String LOGTAG = "MainActivity";
  private static final boolean DEBUG = true;
  private Handler handler;

  private TextView txtViewResult;
  private SwitchCompat swt1, swt2, swt3, swt4, swt5, swt6, swt7, swt8, swt9, swt10;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_eventdetector_main);

    // Init Sensey
    Sensey.getInstance().init(this);

    // Init UI controls,views and handler
    handler = new Handler();
    txtViewResult = (TextView) findViewById(R.id.textView_result);

    swt1 = (SwitchCompat) findViewById(R.id.Switch1);
    swt1.setOnCheckedChangeListener(this);
    swt1.setChecked(false);

    swt2 = (SwitchCompat) findViewById(R.id.Switch2);
    swt2.setOnCheckedChangeListener(this);
    swt2.setChecked(false);

    swt3 = (SwitchCompat) findViewById(R.id.Switch3);
    swt3.setOnCheckedChangeListener(this);
    swt3.setChecked(false);

    swt4 = (SwitchCompat) findViewById(R.id.Switch4);
    swt4.setOnCheckedChangeListener(this);
    swt4.setChecked(false);

    swt5 = (SwitchCompat) findViewById(R.id.Switch5);
    swt5.setOnCheckedChangeListener(this);
    swt5.setChecked(false);

    swt6 = (SwitchCompat) findViewById(R.id.Switch6);
    swt6.setOnCheckedChangeListener(this);
    swt6.setChecked(false);

    swt7 = (SwitchCompat) findViewById(R.id.Switch7);
    swt7.setOnCheckedChangeListener(this);
    swt7.setChecked(false);

    swt8 = (SwitchCompat) findViewById(R.id.Switch8);
    swt8.setOnCheckedChangeListener(this);
    swt8.setChecked(false);

    swt9 = (SwitchCompat) findViewById(R.id.Switch9);
    swt9.setOnCheckedChangeListener(this);
    swt9.setChecked(false);

    swt10 = (SwitchCompat) findViewById(R.id.Switch10);
    swt10.setOnCheckedChangeListener(this);
    swt10.setChecked(false);

    Button btnTouchEvent = (Button) findViewById(R.id.btn_touchevent);
    btnTouchEvent.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, TouchActivity.class));
      }
    });
  }

  @Override
  public void onCheckedChanged(CompoundButton switchbtn, boolean isChecked) {
    int i = switchbtn.getId();
    if (i == R.id.Switch1) {
      if (isChecked) {
        Sensey.getInstance().startShakeDetection(10, 2000, this);
      } else {
        Sensey.getInstance().stopShakeDetection(this);
      }

    } else if (i == R.id.Switch2) {
      if (isChecked) {
        Sensey.getInstance().startFlipDetection(this);
      } else {
        Sensey.getInstance().stopFlipDetection(this);
      }


    } else if (i == R.id.Switch3) {
      if (isChecked) {
        Sensey.getInstance().startOrientationDetection(this);
      } else {
        Sensey.getInstance().stopOrientationDetection(this);
      }


    } else if (i == R.id.Switch4) {
      if (isChecked) {
        Sensey.getInstance().startProximityDetection(this);
      } else {
        Sensey.getInstance().stopProximityDetection(this);
      }

    } else if (i == R.id.Switch5) {
      if (isChecked) {
        Sensey.getInstance().startLightDetection(10, this);
      } else {
        Sensey.getInstance().stopLightDetection(this);
      }

    } else if (i == R.id.Switch6) {
      if (isChecked) {
        Sensey.getInstance().startWaveDetection(this);
      } else {
        Sensey.getInstance().stopWaveDetection(this);
      }

    } else if (i == R.id.Switch7) {
      if (isChecked) {
        Sensey.getInstance().startSoundLevelDetection(this);
      } else {
        Sensey.getInstance().stopSoundLevelDetection();
      }

    } else if (i == R.id.Switch8) {
      if (isChecked) {
        Sensey.getInstance().startMovementDetection(this);
      } else {
        Sensey.getInstance().stopMovementDetection(this);
      }

    } else if (i == R.id.Switch9) {
      if (isChecked) {
        Sensey.getInstance().startChopDetection(30f, 500, this);
      } else {
        Sensey.getInstance().stopChopDetection(this);
      }

    } else if (i == R.id.Switch10) {
      if (isChecked) {
        Sensey.getInstance().startWristTwistDetection(this);
      } else {
        Sensey.getInstance().stopWristTwistDetection(this);
      }

    } else {// Do nothing

    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    // Stop Detections
    Sensey.getInstance().stopShakeDetection(this);
    Sensey.getInstance().stopFlipDetection(this);
    Sensey.getInstance().stopOrientationDetection(this);
    Sensey.getInstance().stopProximityDetection(this);
    Sensey.getInstance().stopLightDetection(this);
    Sensey.getInstance().stopWaveDetection(this);
    Sensey.getInstance().stopSoundLevelDetection();
    Sensey.getInstance().stopMovementDetection(this);
    Sensey.getInstance().stopChopDetection(this);
    Sensey.getInstance().stopWristTwistDetection(this);

    // Set the all switches to off position
    swt1.setChecked(false);
    swt2.setChecked(false);
    swt3.setChecked(false);
    swt4.setChecked(false);
    swt5.setChecked(false);
    swt6.setChecked(false);
    swt7.setChecked(false);
    swt8.setChecked(false);
    swt9.setChecked(false);
    swt10.setChecked(false);

    // Reset the result view
    resetResultInView(txtViewResult);

    Toast.makeText(this, "Stopping all detectors!", Toast.LENGTH_SHORT).show();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // *** IMPORTANT ***
    // Stop Sensey and release the context held by it
    Sensey.getInstance().stop();
  }

  @Override
  public void onFaceUp() {
    setResultTextView("Face UP", false);
  }

  @Override
  public void onFaceDown() {
    setResultTextView("Face Down", false);
  }

  @Override
  public void onDark() {
    setResultTextView("Dark", false);
  }

  @Override
  public void onLight() {
    setResultTextView("Not Dark", false);
  }

  @Override
  public void onTopSideUp() {
    setResultTextView("Top Side UP", false);
  }

  @Override
  public void onBottomSideUp() {
    setResultTextView("Bottom Side UP", false);
  }

  @Override
  public void onRightSideUp() {
    setResultTextView("Right Side UP", false);
  }

  @Override
  public void onLeftSideUp() {
    setResultTextView("Left Side UP", false);
  }

  @Override
  public void onNear() {
    setResultTextView("Near", false);
  }

  @Override
  public void onFar() {
    setResultTextView("Far", false);
  }

  @Override
  public void onShakeDetected() {
    setResultTextView("Shake Detected!", false);
  }

  @Override
  public void onShakeStopped() {
    setResultTextView("Shake Stopped!", false);
  }

  @Override
  public void onWave() {
    setResultTextView("Wave Detected!", false);
  }

  @Override
  public void onSoundDetected(float level) {

    setResultTextView(new DecimalFormat("##.##").format(level) + "dB", true);
  }

  @Override
  public void onMovement() {
    setResultTextView("Movement Detected!", false);
  }

  @Override
  public void onStationary() {
    setResultTextView("Device Stationary!", false);
  }

  @Override
  public void onChop() {
    setResultTextView("Chop Detected!", false);
  }

  @Override
  public void onWristTwist() {
    setResultTextView("Wrist Twist Detected!", false);
  }

  private void setResultTextView(final String text, final boolean realtime) {
    if (txtViewResult != null) {
      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          txtViewResult.setText(text);
          if (!realtime) {
            resetResultInView(txtViewResult);
          }
        }
      });

      if (DEBUG) {
        Log.i(LOGTAG, text);
      }
    }
  }

  private void resetResultInView(final TextView txt) {

    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        txt.setText(getString(R.string.results_show_here));
      }
    }, 3000);
  }
}
