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
import com.example.jakeg.groupupapp.activity.model.GroupMember;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.jakeg.groupupapp.activity.model.Model.getModel;

public class ContactsActivity extends AppCompatActivity {

    private ImageView mContactsBackButton;

    private ListView mContactList;
    private ArrayAdapter<GroupMember> mContactListAdapter;

    private Map<String, GroupMember> contacts = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        mContactsBackButton = findViewById(R.id.contacts_back_button);
        mContactsBackButton.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setContactList();
    }

    private void setContactList(){
        contacts = getModel().getUser().getContacts();
        final ArrayList<GroupMember> valueList = new ArrayList<>(contacts.values());

        mContactListAdapter = new ContactsActivity.ContactArrayAdapter(this, 0, valueList);

        mContactList = findViewById(R.id.contact_list);
        mContactList.setAdapter(mContactListAdapter);

        //Handle click events for the ListView items
        AdapterView.OnItemClickListener groupMemberListViewListener = new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroupMember groupMember = valueList.get(position);
                viewContactsFromList(groupMember);
            }
        };

        mContactList.setOnItemClickListener(groupMemberListViewListener);
    }

    private void viewContactsFromList(GroupMember groupMember){
        Intent intent = new Intent(this, GroupMemberActivity.class);
        intent.putExtra("groupMemberID", groupMember.getGroupMemberID());

        startActivity(intent);
    }

    public static class ContactArrayAdapter extends ArrayAdapter<GroupMember> {

        private Context context;
        private List<GroupMember> contacts;

        public ContactArrayAdapter(Context context, int resource, List<GroupMember> objects){
            super(context, resource, objects);

            this.context = context;
            this.contacts = objects;
        }

        public View getView(int position, View covertView, ViewGroup parent){
            GroupMember contact = contacts.get(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.contact_list_item, null);

            ImageView mContactImage = view.findViewById(R.id.contact_item_icon);
            TextView mContactName = view.findViewById(R.id.contact_item_name);

            mContactImage.setImageDrawable(contact.getUserPicture());
            mContactName.setText(contact.getName());

            return view;
        }
    }
}
