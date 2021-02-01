package com.spisoft.spscartbutton_master;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.spisoft.spscartbutton.SpCartButton;

public class MainActivity extends AppCompatActivity {

    private SpCartButton SpCartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpCartBtn = findViewById(R.id.spCartBtn);
        SpCartBtn.setConfig(21, 5, 4, 300, 1, 3, 100);
        SpCartBtn.setOnValueChangeListener(new SpCartButton.OnValueChangeListener() {
            @Override
            public void onEvent() {
                Toast.makeText(MainActivity.this, "SS : "+ SpCartBtn.getValue(), Toast.LENGTH_SHORT).show();
            }
        });
        SpCartBtn.setOnVeClickListener(new SpCartButton.OnVeClickListener() {
            @Override
            public void onEvent() {
                Toast.makeText(MainActivity.this, "VE : "+ SpCartBtn.getValue(), Toast.LENGTH_SHORT).show();
            }
        });
        SpCartBtn.setOnVsClickListener(new SpCartButton.OnVsClickListener() {
            @Override
            public void onEvent() {
                Toast.makeText(MainActivity.this, "VS : "+ SpCartBtn.getValue(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}