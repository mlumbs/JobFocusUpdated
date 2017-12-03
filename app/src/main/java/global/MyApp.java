package global;

import android.app.Application;

import data.Data;
import debugging.Logs;
import jobfocus.developx.onfleeck.co.za.jobfocus.AlarmReceiver;

/**
 * Created by DevelopX on 2016-03-14.
 */
public class MyApp extends Application {


    public Data mdata;


    public void Initialize(){

        if(mdata==null){
           mdata =new Data(getApplicationContext());
            AlarmReceiver alarm =new AlarmReceiver();
            alarm.setAlarm(getApplicationContext());
            Logs.appendLog(Logs.UnixToDate(System.currentTimeMillis()),"App instance started");

        }
    }


    public Data getData(){
        Initialize(); //Make sure the it exists

        return mdata; //Return the data
    }



}
