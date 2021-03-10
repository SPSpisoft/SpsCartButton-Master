package com.spisoft.spscartbutton_master;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.spisoft.spscartbutton.SpCartButton;

public class MainActivity extends AppCompatActivity {

    private SpCartButton SpCartBtn, SpCartBtnCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface TF_B = Typeface.createFromAsset(this.getAssets(), "font/" + "Amine.ttf" + "");
        Typeface TF_BN = Typeface.createFromAsset(this.getAssets(), "font/" + "BNazanin.ttf" + "");

        SpCartBtnCart = findViewById(R.id.spCartBtn0);
        SpCartBtnCart.setConfig(0, 0, 3, 0, 1, 2, 10, true);
        SpCartBtnCart.setOnStartTaskListener(new SpCartButton.OnStartTaskListener() {
            @Override
            public void onEvent(double newValue) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SpCartBtnCart.resetValues(new Handler() ,20, 0, newValue, 0, newValue < 5);
                    }
                }, 2000);
            }
        });

        SpCartBtn = findViewById(R.id.spCartBtn);
        SpCartBtn.setConfig(21, 0, 0, 0, 1, 1, 7, true);
        SpCartBtn.setFaceText(TF_B, getResources().getDimension(R.dimen.sps_txt_sz_14), Color.WHITE).setActFillColor(Color.parseColor("#012B03"));
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

        SpCartBtn.setOnStartTaskListener(new SpCartButton.OnStartTaskListener() {
            @Override
            public void onEvent(double newValue) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SpCartBtn.resetValues(null ,20,0, newValue, 0, true);
                        SpCartBtn.postDelayed(SpCartBtn.resetToNormalMode, 5000);
                    }
                }, 2000);
            }
        });
    }
}