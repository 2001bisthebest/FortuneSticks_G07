package com.example.fortunesticks_g07;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    SensorInfo sensorInfo = new SensorInfo();
    private static final int shake_threshold = 10;
    Boolean shown_dialog = false;
    private Handler hdr = new Handler();
    private static final int POLL_INTERVAL = 500;
    Resources res = getResources();
    private Runnable pollTask = new Runnable() {
        public void run() {
            showDialog();
            hdr.postDelayed(pollTask, POLL_INTERVAL);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int type = sensorEvent.sensor.getType();

        if (type == Sensor.TYPE_ACCELEROMETER) {
            sensorInfo.accX = sensorEvent.values[0];
            sensorInfo.accY = sensorEvent.values[1];
            sensorInfo.accZ = sensorEvent.values[2];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void showDialog(){
        if( ( Math.abs(sensorInfo.accX) > shake_threshold) || ( Math.abs(sensorInfo.accY) > shake_threshold ) || ( Math.abs(sensorInfo.accZ) > shake_threshold ) ) {
            if(!shown_dialog) {
                shown_dialog = true;
                int max = 9;
                int min = 0;
                Random randomNumber = new Random();
                int resultRandom = randomNumber.nextInt(max - min + 1) + min;
                String[] arr = res.getStringArray(R.array.randomText);
                final AlertDialog.Builder viewDialog = new AlertDialog.Builder(this);
//                viewDialog.setIcon(android.R.drawable.btn_star_big_on);
                viewDialog.setTitle("ผลการทำนาย");
                if(resultRandom == 0){
                    viewDialog.setMessage(arr[0]);
                } else if (resultRandom == 1) {
                    viewDialog.setMessage(arr[1]);
                } else if (resultRandom == 2) {
                    viewDialog.setMessage(arr[2]);
                } else if (resultRandom == 3) {
                    viewDialog.setMessage(arr[3]);
                } else if (resultRandom == 4) {
                    viewDialog.setMessage(arr[4]);
                } else if (resultRandom == 5) {
                    viewDialog.setMessage(arr[5]);
                } else if (resultRandom == 6) {
                    viewDialog.setMessage(arr[6]);
                } else if (resultRandom == 7) {
                    viewDialog.setMessage(arr[7]);
                } else if (resultRandom == 8) {
                    viewDialog.setMessage(arr[8]);
                } else {
                    viewDialog.setMessage(arr[9]);
                }
//
                viewDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                shown_dialog = false;
                            }
                        });
                viewDialog.show();
            }
        }
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        hdr.postDelayed(pollTask, POLL_INTERVAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        hdr.removeCallbacks(pollTask);
    }

    static class SensorInfo{
        float accX, accY, accZ;
    }
}