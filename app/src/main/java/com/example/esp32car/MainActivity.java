
package com.example.esp32car;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String espUrl = "http://192.168.4.1/message";
    String speed = "2"; // Default speed

    private void sendCommand(String direction, String spd) {
        new Thread(() -> {
            try {
                URL url = new URL(espUrl + "?Cdrctn=" + direction + "&Spd=" + spd);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.getResponseCode();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void setupTouchControl(Button button, String direction) {
        button.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                sendCommand(direction, speed);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                sendCommand("S", speed);
            }
            return true;
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnForward = findViewById(R.id.btnForward);
        Button btnBackward = findViewById(R.id.btnBackward);
        Button btnLeft = findViewById(R.id.btnLeft);
        Button btnRight = findViewById(R.id.btnRight);
        Button btnStop = findViewById(R.id.btnStop);
        Button btnSpeed1 = findViewById(R.id.btnSpeed1);
        Button btnSpeed2 = findViewById(R.id.btnSpeed2);
        Button btnSpeed3 = findViewById(R.id.btnSpeed3);

        setupTouchControl(btnForward, "F");
        setupTouchControl(btnBackward, "B");
        setupTouchControl(btnLeft, "L");
        setupTouchControl(btnRight, "R");

        btnStop.setOnClickListener(v -> sendCommand("S", speed));

        btnSpeed1.setOnClickListener(v -> speed = "1");
        btnSpeed2.setOnClickListener(v -> speed = "2");
        btnSpeed3.setOnClickListener(v -> speed = "3");
    }
}
