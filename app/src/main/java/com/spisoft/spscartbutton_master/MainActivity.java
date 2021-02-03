package com.spisoft.spscartbutton_master;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
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

        Typeface TF_B = Typeface.createFromAsset(this.getAssets(), "font/" + "Amine.ttf" + "");
        Typeface TF_BN = Typeface.createFromAsset(this.getAssets(), "font/" + "BNazanin.ttf" + "");

        SpCartBtn = findViewById(R.id.spCartBtn);
        SpCartBtn.setConfig(21, 2, 0, 300, 1, 0, 100);
        SpCartBtn.setFaceText(TF_B, getResources().getDimension(R.dimen.sps_txt_sz_14), Color.WHITE);
        SpCartBtn.setFaceTextDesc(TF_BN, getResources().getDimension(R.dimen.sps_txt_sz_10), Color.LTGRAY);
        SpCartBtn.setOnValueChangeListener(new SpCartButton.OnValueChangeListener() {
            @Override
            public void onEvent() {
//                Toast.makeText(MainActivity.this, "SS : "+ SpCartBtn.getValue(), Toast.LENGTH_SHORT).show();
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