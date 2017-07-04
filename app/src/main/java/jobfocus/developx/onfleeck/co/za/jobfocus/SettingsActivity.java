package jobfocus.developx.onfleeck.co.za.jobfocus;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import CompontUtils.NotificationUtils;
import data.Data;
import global.MyApp;

/**
 * Created by DevelopX on 2016-01-24.
 */
public class SettingsActivity extends AppCompatActivity {
    Data data;
public static String TAG="SETTING";




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        final CompoundButton mNotification;

        data =((MyApp)getApplication()).getData();

        final CompoundButton mReceive;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNotification = (CheckBox) findViewById(R.id.notification);
        mReceive=(CheckBox) findViewById(R.id.receive);



        mNotification.setChecked(data.getTone());


        mReceive.setChecked(data.getReceive());



mNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.isChecked()){

        data.storeTone(true);
        }
        else{
            mNotification.setChecked(false);

            data.storeTone(false);
        }

    }

  });
        mReceive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if(buttonView.isChecked()){
                   data.storeReceive(true);
                }
                else{
                    mReceive.setChecked(false);
                    data.storeReceive(false);
                }

            }

        });



    }

    @Override
    protected void onResume() {

        super.onResume();
        data.EditBack(false);//The device is alive
        NotificationUtils.clearNotificationsID(this,NotificationUtils.NOTIFICATION_ID);
    }

    @Override
    protected void onPause() {

        super.onPause();
        data.EditBack(true); //device is not alive
    }



}
