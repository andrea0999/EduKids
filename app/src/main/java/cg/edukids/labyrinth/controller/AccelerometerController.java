package cg.edukids.labyrinth.controller;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import cg.edukids.labyrinth.utils.Player;

public class AccelerometerController implements SensorEventListener {

    private final SensorManager sensorManager;
    private final Sensor accelerometer;
    private final Player player;

    public AccelerometerController(Context context, Player player) {
        this.player = player;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometer == null) {
            throw new UnsupportedOperationException("Accelerometer not available on this device");
        }
    }

    public void register() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    public void unregister() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float sensitivity = 0.10f;

        float dx = -event.values[0] * sensitivity;
        float dy = event.values[1] * sensitivity;

        player.move(dx, dy);
    }

    @Override public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
