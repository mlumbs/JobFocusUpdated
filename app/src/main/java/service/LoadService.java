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
import data.DatabaseCrud;
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
    public static final String TAG = "Loading";
    SharedPreferences sharedPref;
    public boolean server_approval;
    int big_noti;
    String LastID;
    public LoadService() {
        super("LoadService");
        server_approval=false;
        big_noti=0;
        //LastID="not";
    }



    // public static final String URL_BASE = "http://jobshareapp.jobl.co.za/contentfeeds.php?";
    // public  static final String CV_url="http://192.168.43.27/content.php?";
    public  static final String URL_BASE="http://jobshareapp.jobl.co.za/JobManagerV2/main/contentfeeds.php?";

    String QUERY_PARAM = "q";
    String db;
    SharedPreferences.Editor editor;
    String db_noti;//holds the current notification id ,make sure the loading of the new noti is current

  //  String JsonDe="{\"entries\":[{\"c\":\" abc\",\"face\":\" abc\",\"po\":\" abc\",\"p\":\"       <ul style=\\\"list-style-type:square\\\" id=\\\"myList\\\">       <li>saa<\\/li><\\/ul>       <h4 style=\\\"text-align: center;\\\">as<\\/h4><p>as<\\/p>\",\"e\":\" ass\",\"n\":\" assas\",\"ex\":\"2\",\"id\":\"39\",\"created_at\":\"2017-08-12 21:13:54\"}],\"noti\":[{\"c\":\"gdfd\",\"face\":\"\",\"po\":\" dfsd\",\"p\":\"\",\"e\":\"\",\"n\":\"\",\"ex\":\"3\",\"id\":\"1\",\"created_at\":\"2017-08-13 10:34:18\"}]}";
    @Override
    protected void onHandleIntent(Intent intent) {

        sharedPref = getSharedPreferences(
                Data.PREF_FILE, Context.MODE_PRIVATE);
        db=sharedPref.getString(Data.LAST_ENTRY,"0");
        db_noti=sharedPref.getString(Data.KEY_NOTI_NUMBER,"0");
        Log.v(TAG,db+"shared");
        big_noti= Integer.parseInt(db);
        Uri builtUri = Uri.parse(URL_BASE).buildUpon().appendQueryParameter(QUERY_PARAM, db).appendQueryParameter("app_ver",sharedPref.getInt(Data.APP_VERSION,0)+"").appendQueryParameter("noti",db_noti).build();
        try {
            getDataFromJson(loadJsonFromNetworkSecond(builtUri.toString()));
            //getDataFromJson(JsonDe);
            editor =sharedPref.edit();
            editor.putString(Data.LAST_ENTRY,last_Job_entry(this)+"");
            // editor.putString(Data.LAST_ENTRY,LastID);
            editor.apply();
            Log.v(TAG,LastID+"inner  m"+LastID);
            //sendNotification("Job opportunities have Arrived");
            new NotificationUtils(this).sendNotification(sharedPref.getString(Data.KEY_NOTI_HEADER,"000/"),sharedPref.getString(Data.KEY_NOTI_BODY,"000/"),server_approval);
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


    private String loadJsonFromNetworkSecond(String urlString) throws IOException {
        Log.v(TAG,urlString);
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

    public void getDataFromJson(String JsonStr)throws JSONException
    {
        try
        {
            Log.v(TAG,JsonStr);
            JSONObject Json = new JSONObject(JsonStr);
            if(!Json.isNull("entries"))
            {
                Log.v(TAG, "This is not null");
                JSONArray JArray = Json.getJSONArray("entries");//This return null if network is unavailable
                for (int z = 0; z < JArray.length(); z++)
                {
                    String c = JArray.getJSONObject(z).getString("c");
                    String po = JArray.getJSONObject(z).getString("po");
                    String p = JArray.getJSONObject(z).getString("p");
                    String f = JArray.getJSONObject(z).getString("face");
                    String n = JArray.getJSONObject(z).getString("n");
                    String e = JArray.getJSONObject(z).getString("e");
                    String id = JArray.getJSONObject(z).getString("id");
                    String date = JArray.getJSONObject(z).getString("created_at");
                    String extras = JArray.getJSONObject(z).getString("ex");

                    //LastID=id;
                    //Log.v(TAG,id+LastID+"idtemp");
                    ToAaddJobEntry(this,c,po,id,p,date,f,"",n,e,extras);
                }
            }
        }
        catch (JSONException e)
        {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        } catch (NullPointerException b)
        {
            b.printStackTrace();
        }catch (Exception c)
        {
            c.printStackTrace();
        }

//READ THE SECOND PART :Independent database
        try {
            DatabaseCrud.removeNotification(this);
            JSONObject Json = new JSONObject(JsonStr);
            int z=0;
            if(!Json.isNull("noti")) {
                JSONArray JArray = Json.getJSONArray("noti");
                String c = JArray.getJSONObject(z).getString("c");
                String po = JArray.getJSONObject(z).getString("po");
                String p = JArray.getJSONObject(z).getString("p");
                String f = JArray.getJSONObject(z).getString("face");
                String n = JArray.getJSONObject(z).getString("n");
                String e = JArray.getJSONObject(z).getString("e");
                String id = JArray.getJSONObject(z).getString("id");
                String date = JArray.getJSONObject(z).getString("created_at");
                String extras = JArray.getJSONObject(z).getString("ex");

                if(Integer.parseInt(extras)==Data.notification)
                {
                    // data.storeNotification(c,po);
                    editor =sharedPref.edit();
                    editor.putString(Data.KEY_NOTI_HEADER,c);
                    editor.putString(Data.KEY_NOTI_BODY,po);
                    editor.apply();
                    server_approval=true;
                }

//             ToAaddJobEntry(this,c,po,big_noti+50+"",p,date,f,"",n,e,extras);
//             Log.v(TAG,big_noti+"bignoti");
//             Log.v(TAG,big_noti+50+"bignoti+");
                editor =sharedPref.edit();
                editor.putString(Data.KEY_NOTI_NUMBER,id+"");
                editor.apply();

            }
        }
        catch (JSONException e){
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        catch (NullPointerException b){
            b.printStackTrace();
        }
//READ THE Third PART :Independent database
        try {
            JSONObject Json = new JSONObject(JsonStr);
            int z=0;
            if(!Json.isNull("del")) {
                JSONArray JArray = Json.getJSONArray("del");
                String c = JArray.getJSONObject(z).getString("d");
                DatabaseCrud.ServerDeletes(this, Integer.parseInt(c));
                Log.v(TAG,c+"delete");
            }
        }
        catch (JSONException e){
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        catch (NullPointerException b){
            b.printStackTrace();
        }




    }

    public long last_Job_entry(Context c){
        long last =0;
        try {
            // DatabaseCrud.removeNotification(this);
            String  sortOrder = JobContracts.JobEntry.COLUMN_EntryID  + " DESC";
            Cursor cursor= c.getContentResolver().query(JobContracts.JobEntry.CONTENT_URI,null,null,null,sortOrder);
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
