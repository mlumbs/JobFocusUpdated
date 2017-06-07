package NetworkUtil;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by DevelopX on 2016-08-17.
 */
public class FetchUriJson {
    //This Method returns a Json from the specified urlString from the server
    //urlString should be an endpoint string in the endPoint class
    //The Example is to pass Uri builtUri = Uri.parse(URL_BASE).buildUpon().appendQueryParameter(QUERY_PARAM, Long.toString(db)).appendQueryParameter(NOTI_QUERY,Long.toString(noti_id)).build();
    //To call this class use loadJsonFromNetwork(builtUri); or builtUri(EndPoints.etc);

    public static String loadJsonFromNetwork(String urlString) throws IOException {
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

}
