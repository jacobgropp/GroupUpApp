package com.example.jakeg.groupupapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jakeg.groupupapp.R;
import com.example.jakeg.groupupapp.activity.model.Group;

import java.io.FileNotFoundException;

import static com.example.jakeg.groupupapp.activity.model.Model.getModel;
import static java.security.AccessController.getContext;

public class NameGroupActivity extends AppCompatActivity {

    private ImageView mGroupImage;

    private EditText mGroupName;
    private EditText mGroupDescription;

    private Button mNextButton;

    private Group newGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_group);

        mGroupImage = (ImageView) findViewById(R.id.new_group_image);
        mGroupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        mGroupName = (EditText) findViewById(R.id.group_name);
        mGroupDescription = (EditText) findViewById(R.id.group_description);

        mNextButton = (Button) findViewById(R.id.name_group_next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGroup();
            }
        });
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
                mGroupImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void saveGroup(){
        if(TextUtils.isEmpty(mGroupName.getText())){
            Toast.makeText(this, "Please enter a group name.", Toast.LENGTH_SHORT).show();
            return;
        }

        this.newGroup = new Group(mGroupName.getText().toString());

        if(!TextUtils.isEmpty(mGroupDescription.getText())){
            newGroup.setDescription(mGroupDescription.getText().toString());
        }
        getModel().addGroup(newGroup);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
