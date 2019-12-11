package com.example.jakeg.groupupapp.activity.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jakeg.groupupapp.R;
import com.example.jakeg.groupupapp.activity.model.Group;
import com.example.jakeg.groupupapp.activity.model.GroupMember;
import com.example.jakeg.groupupapp.activity.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static com.example.jakeg.groupupapp.activity.model.Model.getModel;

public class GroupListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Observer {

    //Buttons
    private DrawerLayout mMenuList;
    private View mHeaderView;

    private ArrayList<Group> groupListItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        mMenuList = findViewById(R.id.drawer_layout);

        getModel().addObserver(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mHeaderView = navigationView.getHeaderView(0);
        LinearLayout userProfile = mHeaderView.findViewById(R.id.user_profile);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserProfile();
            }
        });

        //Initialize Menu and Add Group buttons
        ImageView mMenuButton = findViewById(R.id.menu_button);
        ImageView mAddGroupButton = findViewById(R.id.add_group_button);

        mMenuButton.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuList.openDrawer(Gravity.LEFT);
            }
        });

        mAddGroupButton.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewGroup(NameGroupActivity.class);
            }
        });

        //Fill the list view
        if(getModel().getUser().getGroups().isEmpty()){
            generateGroups();
        }

        groupListItems = getModel().getUser().getGroups();
        ArrayAdapter<Group> groupListAdapter = new GroupArrayAdapter(this, 0, groupListItems);

        ListView mGroupList = findViewById(R.id.group_list);
        mGroupList.setAdapter(groupListAdapter);

        //Handle click events for the ListView items
        AdapterView.OnItemClickListener groupListViewListener = new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Group group = groupListItems.get(position);
                viewGroupFromList(group);

            }
        };
        mGroupList.setOnItemClickListener(groupListViewListener);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_contacts) {
            createNewGroup(ContactsActivity.class);
        }
        else if (id == R.id.nav_settings) {
            createNewGroup(SettingsActivity.class);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void update(Observable observable, Object c){
        //Set values of user in drawer layout
        User user = getModel().getUser();
        ImageView userImage = mHeaderView.findViewById(R.id.user_picture);
        //userImage.setImageDrawable(user.getUserPicture()); This is currently nonfunctional. Need to set user image.
        TextView username = mHeaderView.findViewById(R.id.user_name);
        username.setText(user.getUsername());
        TextView userEmail = mHeaderView.findViewById(R.id.user_email);
        userEmail.setText(user.getEmailAddress());
        TextView userPhonenumber = mHeaderView.findViewById(R.id.user_phone_number);
        userPhonenumber.setText(user.getPhoneNumber());
    }

    private void openUserProfile(){
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }

    private void createNewGroup(Class givenClass){
        Intent intent = new Intent(this, givenClass);
        startActivity(intent);
    }

    private void viewGroupFromList(Group group){
        Intent intent = new Intent(this, GroupActivity.class);
        intent.putExtra("groupID", group.getGroupID());

        startActivity(intent);
    }

    public static class GroupArrayAdapter extends ArrayAdapter<Group> {

        private Context context;
        private List<Group> groups;

        public GroupArrayAdapter(Context context, int resource, ArrayList<Group> objects) {
            super(context, resource, objects);

            this.context = context;
            this.groups = objects;
        }

        public View getView(int position, View covertView, ViewGroup parent) {
            Group group = groups.get(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.group_list_item, null);

            ImageView mGroupIcon = view.findViewById(R.id.group_item_icon);
            TextView mGroupName = view.findViewById(R.id.group_item_name);
            TextView mGroupMeetupTime = view.findViewById(R.id.group_item_time);

            mGroupIcon.setImageDrawable(group.getGroupImage());
            mGroupName.setText(group.getName());
            mGroupMeetupTime.setText(group.getNextMeetUp());

            return view;
        }
    }

    //Quick and dirty hardcode to generate some groups on startup for testing and demoing purposes.
    public void generateGroups(){
        //Generate fake users for groups.
        GroupMember john_smith = new GroupMember("John Smith", "john.smith@gmail.com", "+1 (208) 956-2533");
        john_smith.setUserPicture(ResourcesCompat.getDrawable(getResources(), R.drawable.john_smith, null));
        getModel().getUser().setUserGroupMember(john_smith);


        GroupMember joe_allen = new GroupMember("Joe Allen", "joe.allen@gmail.com", "+1 (487) 978-6174");
        joe_allen.setUserPicture(ResourcesCompat.getDrawable(getResources(), R.drawable.joe_allen, null));
        getModel().getUser().getContacts().put(joe_allen.getGroupMemberID(), joe_allen);

        GroupMember katie_allen = new GroupMember("Katie Allen", "katie.allen@gmail.com", "+1 (298) 896-5590");
        katie_allen.setUserPicture(ResourcesCompat.getDrawable(getResources(), R.drawable.katie_allen, null));
        getModel().getUser().getContacts().put(katie_allen.getGroupMemberID(), katie_allen);

        GroupMember morgan_blake = new GroupMember("Morgan Blake", "morgan.blake@gmail.com", "+1 (274) 738-7504");
        morgan_blake.setUserPicture(ResourcesCompat.getDrawable(getResources(), R.drawable.morgan_blake, null));
        getModel().getUser().getContacts().put(morgan_blake.getGroupMemberID(), morgan_blake);

        GroupMember paul_keith = new GroupMember("Paul Keith", "paul.keith@gmail.com", "+1 (444) 243-8347");
        paul_keith.setUserPicture(ResourcesCompat.getDrawable(getResources(), R.drawable.paul_keith, null));
        getModel().getUser().getContacts().put(paul_keith.getGroupMemberID(), paul_keith);

        GroupMember jessica_taylor = new GroupMember("Jessica Taylor", "jessica.taylor@gmail.com", "+1 (424) 900-4445");
        jessica_taylor.setUserPicture(ResourcesCompat.getDrawable(getResources(), R.drawable.jessica_taylor, null));
        getModel().getUser().getContacts().put(jessica_taylor.getGroupMemberID(), jessica_taylor);

        GroupMember alan_tuttle = new GroupMember("Alan Tuttle", "alan.tuttle@gmail.com", "+1 (908) 305-2090");
        alan_tuttle.setUserPicture(ResourcesCompat.getDrawable(getResources(), R.drawable.alan_tuttle, null));
        getModel().getUser().getContacts().put(alan_tuttle.getGroupMemberID(), alan_tuttle);

        //Create dinner group
        Group dinner_group = new Group("Dinner Group");
        dinner_group.setDescription("Everybody join up for some weekly grub!");
        dinner_group.setGroupImage(ResourcesCompat.getDrawable(getResources(), R.drawable.dinner_group_img, null));
        john_smith.setAttending(false);
        dinner_group.addGroupMember(john_smith);
        joe_allen.setAttending(false);
        dinner_group.addGroupMember(joe_allen);
        katie_allen.setAttending(true);
        dinner_group.addGroupMember(katie_allen);
        morgan_blake.setAttending(true);
        dinner_group.addGroupMember(morgan_blake);
        morgan_blake.setAttending(true);
        dinner_group.addGroupMember(paul_keith);
        jessica_taylor.setAttending(false);
        dinner_group.addGroupMember(jessica_taylor);
        alan_tuttle.setAttending(true);
        dinner_group.addGroupMember(alan_tuttle);
        dinner_group.setNextMeetUp("November 20, 2019\nat 7:30PM");
        getModel().getUser().addGroup(dinner_group);

        //Create movie night group
        Group movie_night_group = new Group("Movie Night");
        movie_night_group.setDescription("Going through the top A5 films!");
        movie_night_group.setGroupImage(ResourcesCompat.getDrawable(getResources(), R.drawable.movie_night_img, null));
        john_smith.setAttending(false);
        movie_night_group.addGroupMember(john_smith);
        joe_allen.setAttending(true);
        movie_night_group.addGroupMember(joe_allen);
        katie_allen.setAttending(true);
        movie_night_group.addGroupMember(katie_allen);
        morgan_blake.setAttending(false);
        movie_night_group.addGroupMember(morgan_blake);
        paul_keith.setAttending(true);
        movie_night_group.addGroupMember(paul_keith);
        jessica_taylor.setAttending(false);
        movie_night_group.addGroupMember(jessica_taylor);
        movie_night_group.setNextMeetUp("November 21, 2019\nat 8:00PM");
        getModel().getUser().addGroup(movie_night_group);

        //Create dnd group
        Group dnd_group = new Group("DnD Group");
        dnd_group.setDescription("+5 to wisdom and perception for joining!");
        dnd_group.setGroupImage(ResourcesCompat.getDrawable(getResources(), R.drawable.dnd_group_img, null));
        john_smith.setAttending(true);
        dnd_group.addGroupMember(john_smith);
        joe_allen.setAttending(true);
        dnd_group.addGroupMember(joe_allen);
        katie_allen.setAttending(true);
        dnd_group.addGroupMember(katie_allen);
        paul_keith.setAttending(false);
        dnd_group.addGroupMember(paul_keith);
        alan_tuttle.setAttending(false);
        dnd_group.addGroupMember(alan_tuttle);
        dnd_group.setNextMeetUp("November 23, 2019\nat 6:00PM");
        getModel().getUser().addGroup(dnd_group);

        Group study_group = new Group("Study Group");
        study_group.setDescription("Study group for BIO 100");
        study_group.setGroupImage(ResourcesCompat.getDrawable(getResources(), R.drawable.study_group_img, null));
        john_smith.setAttending(true);
        study_group.addGroupMember(john_smith);
        joe_allen.setAttending(true);
        study_group.addGroupMember(joe_allen);
        katie_allen.setAttending(false);
        study_group.addGroupMember(katie_allen);
        morgan_blake.setAttending(true);
        study_group.addGroupMember(morgan_blake);
        jessica_taylor.setAttending(false);
        study_group.addGroupMember(jessica_taylor);
        study_group.setNextMeetUp("November 25, 2019\nat 5:00PM");
        getModel().getUser().addGroup(study_group);

        Group wow_group = new Group("WoW Team");
        wow_group.setDescription("Let's pwn some noobs.");
        wow_group.setGroupImage(ResourcesCompat.getDrawable(getResources(), R.drawable.wow_group_img, null));
        john_smith.setAttending(true);
        wow_group.addGroupMember(john_smith);
        joe_allen.setAttending(true);
        wow_group.addGroupMember(joe_allen);
        paul_keith.setAttending(true);
        wow_group.addGroupMember(paul_keith);
        jessica_taylor.setAttending(true);
        wow_group.addGroupMember(jessica_taylor);
        wow_group.setNextMeetUp("November 26, 2019\nat 8:00PM");
        getModel().getUser().addGroup(wow_group);
    }

}
