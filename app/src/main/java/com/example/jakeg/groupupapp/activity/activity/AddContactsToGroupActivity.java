package com.example.jakeg.groupupapp.activity.activity;

import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jakeg.groupupapp.R;
import com.example.jakeg.groupupapp.activity.model.Group;
import com.example.jakeg.groupupapp.activity.model.GroupMember;

import static com.example.jakeg.groupupapp.activity.model.Model.getModel;

public class AddContactsToGroupActivity extends AppCompatActivity {

    private Group mNewGroup;

    //Navigation
    private ImageView mBackButton;
    private TextView mNextButton;

    private CheckBox mCheckBoxJoeAllen;
    private CheckBox mCheckBoxKatieAllen;
    private CheckBox mCheckBoxMorganBlake;
    private CheckBox mCheckBoxPaulKeith;
    private CheckBox mCheckBoxJessicaTaylor;
    private CheckBox mCheckBoxAlanTuttle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts_to_group);

        String newGroupID = getIntent().getExtras().getString("groupID");

        mNewGroup = getModel().getUser().getGroup(newGroupID);

        //First add the current user to the new group.
        mNewGroup.addGroupMember(getModel().getUser().getUserGroupMember());

        mBackButton = findViewById(R.id.add_contacts_back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mNextButton = findViewById(R.id.add_contacts_next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGroupMembers();
                startNextStep(SetGroupMeetingTimeActivity.class);
            }
        });

        mCheckBoxJoeAllen = findViewById(R.id.checkBox_joe_allen);
        mCheckBoxKatieAllen = findViewById(R.id.checkBox_katie_allen);
        mCheckBoxMorganBlake = findViewById(R.id.checkBox_morgan_blake);
        mCheckBoxPaulKeith = findViewById(R.id.checkBox_paul_keith);
        mCheckBoxJessicaTaylor = findViewById(R.id.checkBox_jessica_taylor);
        mCheckBoxAlanTuttle = findViewById(R.id.checkBox_alan_tuttle);

    }

    private Boolean addGroupMembers(){

        //Hard coded function for prototyping purposes
        if(mCheckBoxJoeAllen.isChecked()){
            GroupMember joe_allen = new GroupMember("Joe Allen", "joe.allen@gmail.com", "+1 (487) 978-6174");
            joe_allen.setUserPicture(ResourcesCompat.getDrawable(getResources(), R.drawable.joe_allen, null));
            mNewGroup.addGroupMember(joe_allen);
        }
        if(mCheckBoxKatieAllen.isChecked()){
            GroupMember katie_allen = new GroupMember("Katie Allen", "katie.allen@gmail.com", "+1 (298) 896-5590");
            katie_allen.setUserPicture(ResourcesCompat.getDrawable(getResources(), R.drawable.katie_allen, null));
            mNewGroup.addGroupMember(katie_allen);
        }
        if(mCheckBoxMorganBlake.isChecked()){
            GroupMember morgan_blake = new GroupMember("Morgan Blake", "morgan.blake@gmail.com", "+1 (274) 738-7504");
            morgan_blake.setUserPicture(ResourcesCompat.getDrawable(getResources(), R.drawable.morgan_blake, null));
            mNewGroup.addGroupMember(morgan_blake);
        }
        if(mCheckBoxPaulKeith.isChecked()){
            GroupMember paul_keith = new GroupMember("Paul Keith", "paul.keith@gmail.com", "+1 (444) 243-8347");
            paul_keith.setUserPicture(ResourcesCompat.getDrawable(getResources(), R.drawable.paul_keith, null));
            mNewGroup.addGroupMember(paul_keith);
        }
        if(mCheckBoxJessicaTaylor.isChecked()){
            GroupMember jessica_taylor = new GroupMember("Jessica Taylor", "jessica.taylor@gmail.com", "+1 (424) 900-4445");
            jessica_taylor.setUserPicture(ResourcesCompat.getDrawable(getResources(), R.drawable.jessica_taylor, null));
            mNewGroup.addGroupMember(jessica_taylor);
        }
        if(mCheckBoxAlanTuttle.isChecked()){
            GroupMember alan_tuttle = new GroupMember("Alan Tuttle", "alan.tuttle@gmail.com", "+1 (908) 305-2090");
            alan_tuttle.setUserPicture(ResourcesCompat.getDrawable(getResources(), R.drawable.alan_tuttle, null));
            mNewGroup.addGroupMember(alan_tuttle);
        }
        if(mNewGroup.getGroupMembers().isEmpty()){
            Toast.makeText(this, "Pick at least one other group member.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void startNextStep(Class givenClass){
        Intent intent = new Intent(this, givenClass);
        intent.putExtra("Group ID", mNewGroup.getGroupID());
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
