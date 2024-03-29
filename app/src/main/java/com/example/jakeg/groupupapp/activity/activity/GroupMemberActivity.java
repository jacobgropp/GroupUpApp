package com.example.jakeg.groupupapp.activity.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jakeg.groupupapp.R;
import com.example.jakeg.groupupapp.activity.model.Group;
import com.example.jakeg.groupupapp.activity.model.GroupMember;

import static com.example.jakeg.groupupapp.activity.model.Model.getModel;

public class GroupMemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member);

        //Navigation
        ImageView mBackButton = findViewById(R.id.group_member_back_button);
        mBackButton.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Get groupMember from ID
        String contactID = getIntent().getStringExtra("groupMemberID");
        GroupMember groupMember = getModel().getUser().getContacts().get(contactID);

        TextView mGroupMemberName = findViewById(R.id.group_member_name);
        mGroupMemberName.setText(groupMember.getName());

        ImageView mGroupMemberImage = findViewById(R.id.group_member_image);
        mGroupMemberImage.setImageDrawable(groupMember.getUserPicture());

        TextView mGroupMemberEmail = findViewById(R.id.group_member_email_address);
        mGroupMemberEmail.setText(groupMember.getEmailAddress());

        final TextView mGroupMemberPhone = findViewById(R.id.group_member_phone_number);
        mGroupMemberPhone.setText(groupMember.getPhoneNumber());
    }
}
