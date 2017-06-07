package data;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DevelopX on 2016-08-19.
 */
public class DecodeData {
//All Functions here must be run on a service

    //you are Prompted to test this for the ISP responses for no internet access
    //Because a lack of access means that ISP values can supply different Responses on a single request
    //Trying to pass this data is fatal!
    //This class decode HTTP OK 200 response of JSON and put it in the database
    public void getDataJobsFromJson(String JsonStr, Context context) throws JSONException {

        try { //try catch for Job Entries

    //Log.v("ABB",JsonStr);
            JSONObject Json = new JSONObject(JsonStr);


            if (!Json.isNull("entries")) {

                JSONArray JArray = Json.getJSONArray("entries");//This return null if network is unavailable

                // Log.v(TAG, JArray.toString());

                // Log.v("JSON length", JArray.length() + "***" + JArray.toString());
                //Log.v("JSON v", JArray.getJSONObject(0).getString("a")+"");


                for (int z = 0; z < JArray.length(); z++) {
                    String a = JArray.getJSONObject(z).getString("a");
                    String b = JArray.getJSONObject(z).getString("b");
                    String c = JArray.getJSONObject(z).getString("c");
                    String d = JArray.getJSONObject(z).getString("d");
                    String f = JArray.getJSONObject(z).getString("f");
                    String g = JArray.getJSONObject(z).getString("g");
                    String h = JArray.getJSONObject(z).getString("h");
                    String i = JArray.getJSONObject(z).getString("i");
                    String j = JArray.getJSONObject(z).getString("j");
                    String k = JArray.getJSONObject(z).getString("k");
                    String l = JArray.getJSONObject(z).getString("l");
                    String m = JArray.getJSONObject(z).getString("m");
                    String n = JArray.getJSONObject(z).getString("n");
                    String o = JArray.getJSONObject(z).getString("o");
                    String p = JArray.getJSONObject(z).getString("p");

                    String e = JArray.getJSONObject(z).getString("e");

                    long v = Long.parseLong(e) + 2246400L;


                    DatabaseCrud.ToAaddJobEntry(context, a, b, m, n, 1L+"", p, "", "", "", "");


                }

            }


        } catch (Exception c) {
            c.printStackTrace();
        }


    }
}
