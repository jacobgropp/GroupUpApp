package com.example.jakeg.groupupapp.activity.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.jakeg.groupupapp.R;

public class SettingsActivity extends AppCompatActivity {

    private ImageView mSettingsBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSettingsBackButton = findViewById(R.id.settings_back_button);
        mSettingsBackButton.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
