package com.example.jakeg.groupupapp.activity.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jakeg.groupupapp.R;
import com.example.jakeg.groupupapp.activity.model.Group;

import java.io.FileNotFoundException;

import static com.example.jakeg.groupupapp.activity.model.Model.getModel;

public class NameGroupActivity extends AppCompatActivity {

    private ImageView mGroupImage;

    private Group newGroup = null;
    private EditText mGroupName;
    private EditText mGroupDescription;
    private Drawable groupImageDrawable = null;

    private TextView mNextButton;
    private ImageView mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_group);

        mGroupImage = findViewById(R.id.new_group_image);
        mGroupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        mGroupName = findViewById(R.id.new_group_name);
        mGroupDescription = findViewById(R.id.new_group_description);

        mNextButton = findViewById(R.id.name_group_next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveGroup()){
                    startNextStep(AddContactsToGroupActivity.class);
                }
            }
        });
        mBackButton = findViewById(R.id.name_group_back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToGroupList();
            }
        });
    }

    private void returnToGroupList(){
        Intent intent = new Intent(this, GroupListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        if(newGroup != null){
            getModel().getUser().removeGroup(newGroup.getGroupID());
        }
        onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                groupImageDrawable = new BitmapDrawable(getResources(), bitmap);
                mGroupImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private Boolean saveGroup(){
        if(TextUtils.isEmpty(mGroupName.getText())){
            Toast.makeText(this, "Please enter a group name.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            newGroup = new Group(mGroupName.getText().toString());
            if(!TextUtils.isEmpty(mGroupDescription.getText().toString())){
                newGroup.setDescription(mGroupDescription.getText().toString());
            }
            if(groupImageDrawable != null){
                newGroup.setGroupImage(groupImageDrawable);
            }
            else{
                newGroup.setGroupImage(ResourcesCompat.getDrawable(getResources(), R.drawable.group, null));
            }
            getModel().getUser().addGroup(newGroup);
            return true;
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    private void startNextStep(Class givenClass){
        Intent intent = new Intent(this, givenClass);
        intent.putExtra("groupID", newGroup.getGroupID());
        startActivity(intent);
    }
}
