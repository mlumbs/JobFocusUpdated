package service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import CompontUtils.NotificationUtils;
import data.Data;
import data.JobContracts;
import global.MyApp;

import static data.DatabaseCrud.ToAaddJobEntry;

/**
 * Created by DevelopX on 2015-12-24.
 */
public class LoadService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * param name Used to name the worker thread, important only for debugging.
     */
    public static final String TAG = "Loading ";
    public LoadService() {
        super("LoadService");
    }



    public static final String URL_BASE = "http://jobshareapp.jobl.co.za/contentfeeds.php?";
   // public  static final String CV_url="http://192.168.43.27/content.php?";
    String QUERY_PARAM = "q";
    String db;
    SharedPreferences.Editor editor;

    @Override
    protected void onHandleIntent(Intent intent) {
        Context context=this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                Data.PREF_FILE, Context.MODE_PRIVATE);
        db=sharedPref.getString(Data.LAST_ENTRY,"0");
        Uri builtUri = Uri.parse(URL_BASE).buildUpon().appendQueryParameter(QUERY_PARAM, db).build();
        try {
            //sendNotification("Job opportunities have Arrived");
            getDataFromJson(loadJsonFromNetworkSecond(builtUri.toString()));
            editor =sharedPref.edit();
            editor.putString(Data.LAST_ENTRY,last_Job_entry(this)+"");
            editor.commit();

        } catch (JSONException e) {
            Log.v("JSON","Failed");
            e.printStackTrace();
        }
        catch (IOException j) {
            j.printStackTrace();
        }
        catch (NullPointerException e) {
            Log.v(TAG,"Network is absent");
        }

    }






    //this delete by force entries ,specified by values given by the server or some specific data
    //We aim to do this fuction instead of AutoDelete ,The deleting mechanism depends on the server data feeds
    //this must be an array preffered
   public void DeleteIDbyForceK(Context c,String kvalue[]){

       //c.getContentResolver().delete(JobContracts.JobEntry.BuildJobId("IT"),//Same as above reason ,this point to the job matche entry specifically
         //      JobContracts.JobEntry.COLUMN_EntryID + " = ?",
          //     new String[]{kvalue});

       c.getContentResolver().delete(JobContracts.JobEntry.CONTENT_URI,//Same as above reason ,this point to the job matche entry specifically
               JobContracts.JobEntry.COLUMN_EntryID + " = ?",
               kvalue);

   }

   //This is delete less than Id,this msg receives one id from a server to delete less than entries
    public void DeleteLessIDbyForceK(Context c,String kvalue){

        c.getContentResolver().delete(JobContracts.JobEntry.CONTENT_URI,//Same as above reason ,this point to the job matche entry specifically
                JobContracts.JobEntry.COLUMN_EntryID + " < ?",
                new String[]{kvalue});

    }

    private String loadJsonFromNetworkSecond(String urlString) throws IOException {
        Log.v("ABB",urlString);
        //Log.v(TAG,urlString);
        URL url =new URL(urlString);
        String JsonString =null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

          // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();


            if (inputStream == null) {
                // Nothing to do.
                JsonString = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                JsonString = null;
            }
            JsonString = buffer.toString();


        }catch (IOException e){
            e.getMessage();
        }finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }




        return JsonString;//returns JSON
    }



    public void getDataFromJson(String JsonStr)throws JSONException{

try { //try catch for Job Entries

Log.v("ABB",JsonStr);
    JSONObject Json = new JSONObject(JsonStr);


    if(!Json.isNull("entries")) {
        Log.v("Z3", "This is not indeed really null");
        JSONArray JArray = Json.getJSONArray("entries");//This return null if network is unavailable
        for (int z = 0; z < JArray.length(); z++) {
            String c = JArray.getJSONObject(z).getString("c");
            String po = JArray.getJSONObject(z).getString("po");
            String p = JArray.getJSONObject(z).getString("p");
            String f = JArray.getJSONObject(z).getString("face");
            String n = JArray.getJSONObject(z).getString("n");
            String e = JArray.getJSONObject(z).getString("e");
            String id = JArray.getJSONObject(z).getString("id");
            String date = JArray.getJSONObject(z).getString("created_at");
            ToAaddJobEntry(this,c,po,id,p,date,f,"",n,e,"");

        }

    }



}
catch (JSONException e){
    Log.e(TAG, e.getMessage(), e);
    e.printStackTrace();
} catch (NullPointerException b){
    b.printStackTrace();
}catch (Exception c){
    c.printStackTrace();
}




    }

    public long last_Job_entry(Context c){
        long last =0;
        try {
            String  sortOrder = JobContracts.JobEntry.COLUMN_EntryID  + " DESC";
            Cursor cursor= c.getContentResolver().query(JobContracts. JobEntry.CONTENT_URI,null,null,null,sortOrder);

            if(cursor!=null){
                cursor.moveToFirst();

                last =Long.parseLong(cursor.getString(cursor.getColumnIndex(JobContracts.JobEntry.COLUMN_EntryID)));

                Log.v("Last Entry",last+"");

                return last;

            }
        }catch (Exception e){

            Log.v(TAG,e.toString()+"IS CAUGHT");
            return 0;
        }

        return last;
    }


}
