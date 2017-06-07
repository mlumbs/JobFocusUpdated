package data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by DevelopX on 2015-09-04.
 * Dont forget to change the package name
 */
public class JobContracts {


    public static final String CONTENT_AUTHORITY="jobfocus.developx.onfleeck.co.za.jobfocus";
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_Jobs="DataJobs";

    public static final class JobEntry implements BaseColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_Jobs).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_Jobs;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_Jobs;

        public static final String TABLE_NAME ="DataJobs";

        public static final String COLUMN_C ="C"; //For a company
        public static final String COLUMN_Po="Po"; //For position
        public static final String COLUMN_EntryID="EntryID"; //Entry ID
        public static final String COLUMN_P="P"; //That is the body of Context
        public static final String COLUMN_Date="Date"; //Date of entry,each Entry has a lifetime of 30days
        public static final String COLUMN_F="F"; //This is the face1
        public static final String COLUMN_F2="F2"; //This is the face
        public static final String COLUMN_Contacts ="Contacts"; //this is the contact sensitive
        public static final String COLUMN_Email="Email"; //This is the email sensitive
        public static final String COLUMN_Extras="Extras"; //Extra reserved field

        public  static Uri buildJobUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


        public static Uri BuildJobId(String id){//Dont delete

            return CONTENT_URI.buildUpon().appendPath(id).build();
        }


        public static String getIDByFromUri(Uri uri){

            return uri.getPathSegments().get(1);
        }








    }






}
