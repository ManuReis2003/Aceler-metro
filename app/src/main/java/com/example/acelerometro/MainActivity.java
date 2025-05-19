package com.example.acelerometro;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView contadorPassos;
    private SensorManager sensorManager;
    private Sensor acelerometro;
    private SensorEventListener listener;
    private int contador;
    private TextView distancia;
    private double distanciaPercorrida;

    private ProgressBar barraDePassos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        contaPassos();
    }

    private void contaPassos() {
        contadorPassos = findViewById(R.id.textViewContador);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // type step counter
        contador = 0;

        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float eixox = event.values[0];
                float eixoy = event.values[1];
                float eixoz = event.values[2];

                double magnitude = Math.sqrt(eixox * eixox + eixoy * eixoy + eixoz * eixoz);   //magnitude = dar um número pra força física , norma euclidiana =

                if (magnitude > 12) {
                    contador++;
                    contadorPassos.setText("A quantidade de passos é:" + contador);
                    distanciaPercorrida = (contador * 0.7); // 70 cm é a média de um passo
                    distancia.setText ("A distância percorrida foi:" + distanciaPercorrida + "metros");
                    barraDePassos.setProgress(contador);
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(listener, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }
}