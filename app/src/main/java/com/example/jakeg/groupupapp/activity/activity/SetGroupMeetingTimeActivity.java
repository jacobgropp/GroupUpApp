package com.example.jakeg.groupupapp.activity.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
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

    private TextView mReminderAlert;
    private String mReminderTime;

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

        final Context context = this;
        mReminderAlert = findViewById(R.id.set_a_reminder_alert);
        mReminderAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reminderAlertDialogue(context);
            }
        });
    }

    private void reminderAlertDialogue(final Context context) {
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
                saveNotificationTime("0");
                Toast.makeText(context, "Notification alert has been set to signal at the time of the event.", Toast.LENGTH_SHORT).show();
                mReminderTime = "At the time of the event.";
                mReminderAlert.setText(mReminderTime);
                dialog.dismiss();
            }
        });

        mFiveBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotificationTime("5");
                notificationToastMessage("5 minutes");
                mReminderTime = "5 minutes before the event.";
                mReminderAlert.setText(mReminderTime);
                dialog.dismiss();
            }
        });

        mTenBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotificationTime("10");
                notificationToastMessage("10 minutes");
                mReminderTime = "10 minutes before the event.";
                mReminderAlert.setText(mReminderTime);
                dialog.dismiss();
            }
        });

        mFifteenBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotificationTime("15");
                notificationToastMessage("15 minutes");
                mReminderTime = "15 minutes before the event.";
                mReminderAlert.setText(mReminderTime);
                dialog.dismiss();
            }
        });

        mThirtyBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotificationTime("30");
                notificationToastMessage("30 minutes");
                mReminderTime = "30 minutes before the event.";
                mReminderAlert.setText(mReminderTime);
                dialog.dismiss();
            }
        });

        mHourBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotificationTime("60");
                notificationToastMessage("1 hour");
                mReminderTime = "1 hour before the event.";
                mReminderAlert.setText(mReminderTime);
                dialog.dismiss();
            }
        });

        /**
         * if you want the dialog to be specific size, do the following
         * this will cover 85% of the screen (85% width and 85% height)
         */
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dialogWidth = (int)(displayMetrics.widthPixels * 0.48);
        int dialogHeight = (int)(displayMetrics.heightPixels * 0.47);
        dialog.getWindow().setLayout(dialogWidth, dialogHeight);

        dialog.show();
    }

    private void notificationToastMessage(String time){
        Toast.makeText(this, "Notification alert has been set to " + time + " before the event.", Toast.LENGTH_SHORT).show();
    }


    private Boolean saveDateAndTime(){
        if(TextUtils.isEmpty(mDate.getText()) || TextUtils.isEmpty(mTime.getText())){
            Toast.makeText(this, "Please set a date and time.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            String meetup = mDate.getText().toString() + "\nat " + mTime.getText().toString();
            getModel().getUser().getGroup(mGroupID).setNextMeetUp(meetup);
            return true;
        }
    }

    private void saveNotificationTime(String time){
        getModel().getUser().getGroup(mGroupID).setNotification(time);
    }

    private void returnToGroupList(){
        Intent intent = new Intent(this, GroupListActivity.class);
        startActivity(intent);
    }

}


