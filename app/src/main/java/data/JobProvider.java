package data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by DevelopX on 2015-09-04.
 */
public class JobProvider extends ContentProvider {


    private static final int jobs = 101;
    private static final int JobWithId=102;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private JobEntryDb mDbHelper;


    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = JobContracts.CONTENT_AUTHORITY;


        matcher.addURI(authority, JobContracts.PATH_Jobs ,jobs);
        matcher.addURI(authority, JobContracts.PATH_Jobs+ "/*",JobWithId);

        return matcher;
    }

//content://za.co.onfleeck.developx.jobshared/Commands/0
   //

    @Override
    public boolean onCreate() {
        mDbHelper = new JobEntryDb(getContext());
        return true;
    }


    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case jobs:
                return JobContracts.JobEntry.CONTENT_TYPE;
            case JobWithId:
                return JobContracts.JobEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }



    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

          case jobs:
            {
                retCursor = mDbHelper.getReadableDatabase().query(
                        JobContracts.JobEntry.TABLE_NAME,
                        null,
                        null,//mSelectionClause
                        null,//mSelectionArgs
                        null,
                        null,
                        sortOrder);
                break;
            }

            case JobWithId:
            {
                retCursor = getJobWithCategoryId(uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown " + uri);
        }

        uri= JobContracts.JobEntry.CONTENT_URI;
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    private Cursor getJobWithCategoryId(Uri uri) {
        String Id = JobContracts.JobEntry.getIDByFromUri(uri);
        String[] mSelectionArgs = {""};
        mSelectionArgs[0] = Id;
        String mSelectionClause = JobContracts.JobEntry.TABLE_NAME + "." + JobContracts.JobEntry._ID + " = ?";


        return mDbHelper.getReadableDatabase().query(
                JobContracts.JobEntry.TABLE_NAME,
                null,
                mSelectionClause,
                mSelectionArgs,
                null,
                null,
                null);

    }



    @Override
    public Uri insert(Uri uri, ContentValues values) {


        final SQLiteDatabase db =mDbHelper.getWritableDatabase();
        final int match =sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {

            case jobs:{

                long _id=db.insert(JobContracts.JobEntry.TABLE_NAME, null, values);
                if(_id>0){
                    returnUri= JobContracts.JobEntry.buildJobUri(_id);
                }
                else
                    throw new android.database.SQLException("Failed to insert A row");
                break;
            }


            default:
                throw new UnsupportedOperationException("Unknown uri "+uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);//notifies conten observer that the data ha changed see vid 24
        //db.close();
        return returnUri;




    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db =mDbHelper.getWritableDatabase();
        final int match =sUriMatcher.match(uri);
        int rowsDeleted=0;

        switch (match) {

            case JobWithId:
                rowsDeleted=db.delete(JobContracts.JobEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case jobs:
                rowsDeleted=db.delete(JobContracts.JobEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri"+uri);
        }
        if (null==selection ||0!=rowsDeleted){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {


            final SQLiteDatabase db=mDbHelper.getWritableDatabase();
            final int match =sUriMatcher.match(uri);
            int rowsUpdated;

            switch (match) {
                case JobWithId:
                   rowsUpdated = db.update(JobContracts.JobEntry.TABLE_NAME, values, selection,
                           selectionArgs);
                    //Log.v("UPDATE","UPDATE OPN");
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri"+uri);
            }
            if (null==selection ||0!=rowsUpdated){
                getContext().getContentResolver().notifyChange(uri, null);
            }


            return rowsUpdated;

    }


}
