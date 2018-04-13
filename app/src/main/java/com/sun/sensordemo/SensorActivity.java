package com.sun.sensordemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity {
    private static final String TAG = "SensorActivity";

    private SensorManager mSensorManager;
    private Sensor mPressure;

    private SparseArray<SensorEventListener> mSensorEventListeners = new SparseArray<>();

    private TextView mSensorAccuracyTextView;
    private TextView mSensorDataTextView;
    private TextView mAltitudeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        initViews();
        initListeners();
    }

    private void initViews() {
        mSensorAccuracyTextView = findViewById(R.id.text_sensor_accuracy);
        mSensorDataTextView = findViewById(R.id.text_sensor_data);
        mAltitudeTextView = findViewById(R.id.text_altitude);
    }

    private void initListeners() {
        mSensorEventListeners.put(Sensor.TYPE_PRESSURE, new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                logMessage("onSensorChanged event: " + event);
                final float millibars_of_pressure = event.values[0];
                mSensorDataTextView.setText(getString(R.string.sensor_data, millibars_of_pressure));
                final float altitude = SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, millibars_of_pressure);
                mAltitudeTextView.setText(getString(R.string.converted_altitude, altitude));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                logMessage("onAccuracyChanged sensor: " + sensor + " accuracy: " + accuracy);
                mSensorAccuracyTextView.setText(getString(R.string.sensor_accuracy, accuracy));
            }
        });
    }

    private void logMessage(String message) {
        Log.d(TAG, message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorEventListeners.get(Sensor.TYPE_PRESSURE), mPressure, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorEventListeners.get(Sensor.TYPE_PRESSURE));
    }
}
