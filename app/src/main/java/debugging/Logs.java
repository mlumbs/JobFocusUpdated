package debugging;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
/**
 * Created by DevelopX on 2016-08-25.
 */
public class Logs {

    public static void appendLog(String d,String mes)
    {

        File logFile = new File("sdcard/record_network_frequency.txt");
        if (!logFile.exists())
        {
            try
            {
                logFile.createNewFile();

            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try
        {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(d+"\t,"+mes);
            buf.newLine();
            buf.close();

        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String UnixToDate(long unixMilli){
        Date date = new Date(unixMilli); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"); // the format of your date ("yyyy-MM-dd HH:mm:ss z")
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+2")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        //System.out.println(formattedDate);
        return formattedDate;
    }

    public static String getCarrier(Context c){
        TelephonyManager tManager = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
       return tManager.getNetworkOperatorName();
    }


}
