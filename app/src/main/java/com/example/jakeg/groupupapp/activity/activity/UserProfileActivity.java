package com.example.jakeg.groupupapp.activity.activity;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jakeg.groupupapp.R;
import com.example.jakeg.groupupapp.activity.model.User;

import static com.example.jakeg.groupupapp.activity.model.Model.getModel;

public class UserProfileActivity extends AppCompatActivity {

    private EditText mUsername;
    private TextWatcher usernameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            getModel().changeUserUsername(editable.toString());
        }
    };

    private EditText mEmailAddress;
    private TextWatcher emailWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            getModel().changeUserEmailAddress(editable.toString());
        }
    };

    private EditText mPhoneNumber;
    private TextWatcher phoneNumberWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            getModel().changeUserPhoneNumber(editable.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //Navigation
        ImageView mBackButton = findViewById(R.id.user_profile_back_button);
        mBackButton.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Grab user to populate page
        User user = getModel().getUser();

        //View elements
        ImageView mUserImage = findViewById(R.id.user_profile_image);
        mUserImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.john_smith, null));

        //Will need to add some way to change this photo in the future

        mUsername = findViewById(R.id.user_profile_name);
        mUsername.setText(user.getUsername());
        mUsername.addTextChangedListener(usernameWatcher);

        mEmailAddress = findViewById(R.id.user_profile_email_address);
        mEmailAddress.setText(user.getEmailAddress());
        mEmailAddress.addTextChangedListener(emailWatcher);

        mPhoneNumber = findViewById(R.id.user_profile_phone_number);
        mPhoneNumber.setText(user.getPhoneNumber());
        mPhoneNumber.addTextChangedListener(phoneNumberWatcher);
    }
}
