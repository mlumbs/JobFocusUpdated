package responsebensorsbroadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import debugging.Logs;

public class NetworkSensor extends BroadcastReceiver {
    private static String TAG="NETWORK";
    int networkSpeed;
    ConnectivityManager cm;

    public NetworkSensor() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

          Log.v("ISP","Network sensor detection placed ");
        try {
            if (activeNetwork != null && activeNetwork.isConnected()) {
                Log.v("ISP",activeNetwork.getType()+""); //Returns  Mobile or Wifi or Wifimax as int
                Log.v("ISP",activeNetwork.getExtraInfo()+"");//Returns MTN or Voda or cell C
                Log.v("ISP",activeNetwork.getTypeName()+"");//Retuns the Decoded int of type WIFI or MOBILE
                Log.v("ISP",activeNetwork.getSubtypeName()+"");//subtype of the network are HSPA or GPRS or HSPA+
                Log.v("ISP",activeNetwork.getDetailedState()+""); //CONNECTED or
               // Logs.appendLog(Logs.UnixToDate(System.currentTimeMillis())+" Connected ",activeNetwork.getSubtypeName()+" "+activeNetwork.getExtraInfo());

               // Log.v("Net",activeNetwork.getType());

               //START THE SERVICE OF LOADING THE NETWORK

                //Intent service = new Intent(context, LoadService.class);


               // context.startService(service);

                //Log.v(TAG, activeNetwork.toString() + Loaded);
                //Log.v(TAG, Loaded + "<==");
            }

            if(activeNetwork != null &&activeNetwork.isConnectedOrConnecting()){
                //Logs.appendLog(Logs.UnixToDate(System.currentTimeMillis())+" ConnectedOrConnecting ",activeNetwork.getTypeName()+" "+activeNetwork.getExtraInfo());
            }

            if(activeNetwork != null &&activeNetwork.isAvailable()){
                //Logs.appendLog(Logs.UnixToDate(System.currentTimeMillis())+" isAvailable ",activeNetwork.getTypeName()+" "+activeNetwork.getExtraInfo());
            }



        }
        catch (NullPointerException e){
            Log.v(TAG,e.toString());
        }
//        boolean isConnected = activeNetwork != null &&
//                activeNetwork.isConnectedOrConnecting();




       // throw new UnsupportedOperationException("Not yet implemented");
    }


}
