package com.example.jakeg.groupupapp.activity.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jakeg.groupupapp.R;
import com.example.jakeg.groupupapp.activity.model.Group;
import com.example.jakeg.groupupapp.activity.model.User;

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

        //Get group from ID
        String groupID = getIntent().getStringExtra("groupID");
        Group group = getModel().getGroup(groupID);

        //Get user from ID
        String userID = getIntent().getStringExtra("userID");
        User user = group.getGroupMember(userID);

        TextView mGroupMemberName = findViewById(R.id.group_member_name);
        mGroupMemberName.setText(user.getUsername());

        ImageView mGroupMemberImage = findViewById(R.id.group_member_image);
        mGroupMemberImage.setImageDrawable(user.getUserPicture());

        TextView mGroupMemberEmail = findViewById(R.id.group_member_email_address);
        mGroupMemberEmail.setText(user.getEmailAddress());

        TextView mGroupMemberPhone = findViewById(R.id.group_member_phone_number);
        mGroupMemberPhone.setText(user.getPhoneNumber());
    }
}
