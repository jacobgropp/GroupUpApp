package com.example.jakeg.groupupapp.activity.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jakeg.groupupapp.R;
import com.example.jakeg.groupupapp.activity.model.Group;
import com.example.jakeg.groupupapp.activity.model.GroupMember;

import java.util.ArrayList;
import java.util.List;

import static com.example.jakeg.groupupapp.activity.model.Model.getModel;

public class GroupActivity extends AppCompatActivity {

    private ImageView mGroupBackButton;

    private ImageView mAlertButton;

    private TextView mGroupName;
    private TextView mGroupDescription;
    private ImageView mGroupImage;
    private TextView mGroupMeetupTime;

    private ListView mGroupMemberList;
    private ArrayAdapter<GroupMember> mGroupMemberListAdapter;

    private CheckBox mYesCheckBox;
    private CheckBox mNoCheckBox;

    private Group group;
    private List<GroupMember> groupMembers = new ArrayList<>();
    private GroupMember userGroupMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        //Set up navigation
        mGroupBackButton = findViewById(R.id.group_back_button);
        mGroupBackButton.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Context context = this;
        //Set up alert button
        mAlertButton = findViewById(R.id.alert);
        mAlertButton.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyDialog(context);
            }
        });

        //Grab group to populate page
        String groupID = getIntent().getStringExtra("groupID");
        group = getModel().getUser().getGroup(groupID);
        userGroupMember = getModel().getUser().getUserGroupMember();

        //Set Group information
        mGroupName = findViewById(R.id.group_name);
        mGroupDescription = findViewById(R.id.group_description);
        mGroupImage = findViewById(R.id.group_image);
        mGroupMeetupTime = findViewById(R.id.group_meetup_time);

        mGroupName.setText(group.getName());
        mGroupDescription.setText(group.getDescription());
        mGroupImage.setImageDrawable(group.getGroupImage());
        mGroupMeetupTime.setText(group.getNextMeetUp());

        //User attendance
        mYesCheckBox = findViewById(R.id.group_yes_checkbox);
        mNoCheckBox = findViewById(R.id.group_no_checkbox);

        mYesCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mNoCheckBox.isChecked()){
                    mNoCheckBox.setChecked(false);
                }
                mYesCheckBox.setClickable(false);
                mNoCheckBox.setClickable(true);
                userGroupMember.setAttending(true);
                setGroupList();
            }
        });

        mNoCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mNoCheckBox.isChecked())
                if(mYesCheckBox.isChecked()){
                    mYesCheckBox.setChecked(false);
                }
                mNoCheckBox.setClickable(false);
                mYesCheckBox.setClickable(true);
                userGroupMember.setAttending(false);
                setGroupList();
            }
        });

        if(userGroupMember.getAttending() == "Yes"){
            mYesCheckBox.setChecked(true);
            mYesCheckBox.setClickable(false);
        }
        else{
            mNoCheckBox.setChecked(true);
            mNoCheckBox.setClickable(false);
        }

        setGroupList();
    }

    private void showMyDialog(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_dialogue);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

        TextView mAtTime = dialog.findViewById(R.id.at_time_of_event);
        TextView mFiveBefore = dialog.findViewById(R.id.five_before);
        TextView mTenBefore = dialog.findViewById(R.id.ten_before);
        TextView mFifteenBefore = dialog.findViewById(R.id.fifteen_before);
        TextView mThirtyBefore = dialog.findViewById(R.id.thirty_before);
        TextView mHourBefore = dialog.findViewById(R.id.hour_before);

        mAtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group.setNotification("0");
                Toast.makeText(context, "Notification alert has been set to at the time of the event.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        mFiveBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group.setNotification("5");
                notificationToastMessage("5 minutes");
                dialog.dismiss();
            }
        });

        mTenBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group.setNotification("10");
                notificationToastMessage("10 minutes");
                dialog.dismiss();
            }
        });

        mFifteenBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group.setNotification("15");
                notificationToastMessage("15 minutes");
                dialog.dismiss();
            }
        });

        mThirtyBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group.setNotification("30");
                notificationToastMessage("30 minutes");
                dialog.dismiss();
            }
        });

        mHourBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group.setNotification("60");
                notificationToastMessage("1 hour");
                dialog.dismiss();
            }
        });

        /**
         * if you want the dialog to be specific size, do the following
         * this will cover 85% of the screen (85% width and 85% height)
         */
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dialogWidth = (int)(displayMetrics.widthPixels * 0.473);
        int dialogHeight = (int)(displayMetrics.heightPixels * 0.45);
        dialog.getWindow().setLayout(dialogWidth, dialogHeight);

        dialog.show();
    }

    private void notificationToastMessage(String time){
        Toast.makeText(this, "Notification alert has been set to " + time + " before the event.", Toast.LENGTH_SHORT).show();
    }

    private void setGroupList(){
        groupMembers = group.getGroupMembers();
        mGroupMemberListAdapter = new GroupActivity.GroupMemberArrayAdapter(this, 0, groupMembers);

        mGroupMemberList = findViewById(R.id.group_member_list);
        mGroupMemberList.setAdapter(mGroupMemberListAdapter);

        //Handle click events for the ListView items
        AdapterView.OnItemClickListener groupMemberListViewListener = new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroupMember groupMember = groupMembers.get(position);
                viewGroupMemberFromList(groupMember);
            }
        };
        mGroupMemberList.setOnItemClickListener(groupMemberListViewListener);
    }

    private void viewGroupMemberFromList(GroupMember groupMember){
        Intent intent = new Intent(this, GroupMemberActivity.class);
        intent.putExtra("groupID", group.getGroupID());
        intent.putExtra("groupMemberID", groupMember.getGroupMemberID());

        startActivity(intent);
    }

    public static class GroupMemberArrayAdapter extends ArrayAdapter<GroupMember> {

        private Context context;
        private List<GroupMember> groupMembers;

        public GroupMemberArrayAdapter(Context context, int resource, List<GroupMember> objects){
            super(context, resource, objects);

            this.context = context;
            this.groupMembers = objects;
        }

        public View getView(int position, View covertView, ViewGroup parent){
            GroupMember groupMember = groupMembers.get(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.group_member_list_item, null);

            ImageView mGroupMemberImage = view.findViewById(R.id.group_member_item_icon);
            TextView mGroupMemberName = view.findViewById(R.id.group_member_item_name);
            TextView mGroupMemberAttendance = view.findViewById(R.id.group_member_item_attendance);

            mGroupMemberImage.setImageDrawable(groupMember.getUserPicture());
            mGroupMemberName.setText(groupMember.getName());
            mGroupMemberAttendance.setText(groupMember.getAttending());

            return view;
        }
    }
}
