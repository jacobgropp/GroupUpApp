package com.example.jakeg.groupupapp.activity.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jakeg.groupupapp.R;
import com.example.jakeg.groupupapp.activity.model.Group;
import com.example.jakeg.groupupapp.activity.model.User;

import java.util.ArrayList;
import java.util.List;

import static com.example.jakeg.groupupapp.activity.model.Model.getModel;

public class GroupActivity extends AppCompatActivity {

    private ImageView mGroupBackButton;

    private TextView mGroupName;
    private TextView mGroupDescription;
    private ImageView mGroupImage;
    private TextView mGroupMeetupTime;

    private Group group;
    private ArrayList<User> groupMembers = new ArrayList<>();

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

        //Grab group to populate page
        String groupID = getIntent().getStringExtra("groupID");
        group = getModel().getGroup(groupID);

        mGroupName = findViewById(R.id.group_name);
        mGroupDescription = findViewById(R.id.group_description);
        mGroupImage = findViewById(R.id.group_image);
        mGroupMeetupTime = findViewById(R.id.group_meetup_time);

        mGroupName.setText(group.getName());
        mGroupDescription.setText(group.getDescription());
        mGroupImage.setImageDrawable(group.getGroupImage());
        mGroupMeetupTime.setText(group.getNextMeetUp());

        groupMembers = group.getGroupMembers();
        ArrayAdapter<User> groupMemberListAdapter = new GroupActivity.GroupMemberArrayAdapter(this, 0, groupMembers);

        ListView mGroupMemberList = findViewById(R.id.group_member_list);
        mGroupMemberList.setAdapter(groupMemberListAdapter);

        //Handle click events for the ListView items
        AdapterView.OnItemClickListener groupMemberListViewListener = new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = groupMembers.get(position);
                viewGroupMemberFromList(user);
            }
        };
        mGroupMemberList.setOnItemClickListener(groupMemberListViewListener);
    }

    private void viewGroupMemberFromList(User user){
        Intent intent = new Intent(this, GroupMemberActivity.class);
        intent.putExtra("groupID", group.getGroupID());
        intent.putExtra("userID", user.getUserID());

        startActivity(intent);
    }

    public static class GroupMemberArrayAdapter extends ArrayAdapter<User> {

        private Context context;
        private List<User> groupMembers;

        public GroupMemberArrayAdapter(Context context, int resource, ArrayList<User> objects){
            super(context, resource, objects);

            this.context = context;
            this.groupMembers = objects;
        }

        public View getView(int position, View covertView, ViewGroup parent){
            User user = groupMembers.get(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.group_member_list_item, null);

            ImageView mUserImage = view.findViewById(R.id.group_member_item_icon);
            TextView mUserName = view.findViewById(R.id.group_member_item_name);

            mUserImage.setImageDrawable(user.getUserPicture());
            mUserName.setText(user.getUsername());

            return view;
        }
    }
}
