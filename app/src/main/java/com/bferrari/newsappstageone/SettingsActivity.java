package com.bferrari.newsappstageone;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by bferrari on 18/11/17.
 */

public class SettingsActivity extends AppCompatActivity {

    private Button mSaveBtn;
    private RadioGroup mCountryRadioGroup;
    private RadioGroup mCategoryRadioGroup;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        bindUi();

        mSharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedCountryId = mCountryRadioGroup.getCheckedRadioButtonId();
                int selectedCategoryId = mCategoryRadioGroup.getCheckedRadioButtonId();

                RadioButton rbCountry = findViewById(selectedCountryId);
                RadioButton rbCategory = findViewById(selectedCategoryId);

                if (selectedCountryId > -1) {
                    mEditor.putString(String.valueOf(PreferencesKeys.COUNTRY),
                            rbCountry.getText().toString().toLowerCase().replaceAll(" ",""));
                }

                if (selectedCategoryId > -1) {
                    mEditor.putString(String.valueOf(PreferencesKeys.CATEGORY),
                            rbCategory.getText().toString().toLowerCase().replaceAll(" ", ""));
                }

                mEditor.apply();
            }
        });
    }

    private void bindUi() {
        mSaveBtn = findViewById(R.id.button);
        mCountryRadioGroup = findViewById(R.id.radio_group_country);
        mCategoryRadioGroup = findViewById(R.id.radio_group_category);
    }

}
