package com.example.jakeg.groupupapp.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.jakeg.groupupapp.R;

public class GroupListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    //Buttons
    private ImageView mMenuButton;
    private ImageView mAddGroupButton;
    private DrawerLayout mMenuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        mMenuList = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Initialize Menu and Add Group buttons
        mMenuButton = (ImageView) findViewById(R.id.menu_button);
        mAddGroupButton = (ImageView) findViewById(R.id.add_group_button);

        mMenuButton.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuList.openDrawer(Gravity.LEFT);
            }
        });

        mAddGroupButton.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(NameGroupActivity.class);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        if (id == R.id.nav_groups) {
            // Handle the camera action
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchActivity(Class givenClass){
        Intent intent = new Intent(this, givenClass);
        startActivity(intent);
    }

}
