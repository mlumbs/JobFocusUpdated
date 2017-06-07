package ResponseSensorsBroadCastReceivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;

import java.util.Calendar;

import debugging.Logs;
import service.LoadService;

/**
 * Created by DevelopX on 2015-12-24.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {


    private AlarmManager alarmMgr;
    // The pending intent that is triggered when the alarm fires.
    private PendingIntent alarmIntent;

    private static String TAG="Alarm Receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.v(TAG, "OnReceive Method for alarm  has Started");
        //Logs.appendLog(Logs.UnixToDate(System.currentTimeMillis()),"Alarm process in motion");
        Intent service = new Intent(context, LoadService.class);



        startWakefulService(context, service);
    }
    public void setAlarm(Context context) {
        //Logs.appendLog(Logs.UnixToDate(System.currentTimeMillis()),"Alarm set begin");
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime()+60*1000, alarmIntent);



        // Enable {@code SampleBootReceiver} to automatically restart the alarm when the
        // device is rebooted.
        ComponentName receiver = new ComponentName(context,BootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

       // Logs.appendLog(Logs.UnixToDate(System.currentTimeMillis()),"Alarm set ends");
    }


}
