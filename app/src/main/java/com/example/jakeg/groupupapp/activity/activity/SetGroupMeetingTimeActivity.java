package com.example.jakeg.groupupapp.activity.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.jakeg.groupupapp.R;

import java.util.Calendar;

import static com.example.jakeg.groupupapp.activity.model.Model.getModel;

public class SetGroupMeetingTimeActivity extends AppCompatActivity {

    //Navigation
    private ImageView mBackButton;
    private TextView mCreateGroupButton;

    //Meeting Time
    TextView mDate;
    TextView mTime;

    private String mGroupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_group_meeting_time);

        this.mGroupID = getIntent().getExtras().getString("Group ID");

        mBackButton = findViewById(R.id.set_group_meeting_back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCreateGroupButton = findViewById(R.id.create_group_button);
        mCreateGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(saveDateAndTime()){
                    returnToGroupList();
                }
            }
        });

        mDate = findViewById(R.id.in_date);
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(SetGroupMeetingTimeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String givenMonth = " ";
                        switch(month){
                            case 0:
                                givenMonth = "January";
                                break;
                            case 1:
                                givenMonth = "February";
                                break;
                            case 2:
                                givenMonth = "March";
                                break;
                            case 3:
                                givenMonth = "April";
                                break;
                            case 4:
                                givenMonth = "May";
                                break;
                            case 5:
                                givenMonth = "June";
                                break;
                            case 6:
                                givenMonth = "July";
                                break;
                            case 7:
                                givenMonth = "August";
                                break;
                            case 8:
                                givenMonth = "September";
                                break;
                            case 9:
                                givenMonth = "October";
                                break;
                            case 10:
                                givenMonth = "November";
                                break;
                            case 11:
                                givenMonth = "December";
                                break;
                        }
                        String date = givenMonth + " " + day + ", " + year;
                        mDate.setText(date);
                    }
                }, year, month, dayOfMonth);

                datePickerDialog.show();
            };
        });

        mTime = findViewById(R.id.in_time);
        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final Calendar c= Calendar.getInstance();
                int currentHour = c.get(Calendar.HOUR_OF_DAY);
                int currentMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(SetGroupMeetingTimeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        String amPM;
                        String hour = Integer.toString(hourOfDay);
                        String minutesOnHour = Integer.toString(minutes);
                        if (hourOfDay >= 12){
                            amPM = "PM";
                        } else{
                            amPM = "AM";
                        }
                        if (hourOfDay > 12){
                            hour = Integer.toString(hourOfDay - 12);
                        }
                        if (minutes < 10){
                            minutesOnHour = "0" + minutesOnHour;
                        }
                        String time = hour + ":" + minutesOnHour + " " + amPM;
                        mTime.setText(time);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            };
        });

    }

    private Boolean saveDateAndTime(){
        if(TextUtils.isEmpty(mDate.getText()) || TextUtils.isEmpty(mTime.getText())){
            Toast.makeText(this, "Please set a date and time.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            String meetup = mDate.getText().toString() + "\n at " + mTime.getText().toString();
            getModel().getGroup(mGroupID).setNextMeetUp(meetup);
            return true;
        }
    }

    private void returnToGroupList(){
        Intent intent = new Intent(this, GroupListActivity.class);
        startActivity(intent);
    }

}


