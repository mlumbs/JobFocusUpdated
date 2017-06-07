package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by DevelopX on 2016-08-17.
 * All CRUD Functions will be called as the  Service Sequences
 */
public class DatabaseCrud {
    //Write the shared preference automatically thus not saving to the database!!
    //So we need only need Noti1,Noti2 and Delete entry Preferences(This is cammnd to delete obsolete values or entries)
    //everytime the the NotificationUtil is called the SharePreference values must be re-writtern just delete old text and give it new text
    //they write only once
    //the Database must not take action of saving when C and Po and EntryID are Empty
    //this means that NotificationUtils can be sent without the need of a Job Entry
    //Also if The NotificationUtils has been clicked or viewed it should be automiatically be deleted
    //Thus the NotificationUtils only fires if and only if the notification is not empty
    //Therfore if the notification is empty it should not fire

    public static void ToAaddJobEntry(Context c, String C, String Po,
                                      String EntryID, String P, String Date, String F, String F2,
                                      String Contacts, String Email, String Extras) {
        ContentValues JobValues = new ContentValues();
        JobValues.put(JobContracts.JobEntry.COLUMN_C, C);
        JobValues.put(JobContracts.JobEntry.COLUMN_Po, Po);
        JobValues.put(JobContracts.JobEntry.COLUMN_EntryID, EntryID);
        JobValues.put(JobContracts.JobEntry.COLUMN_P, P);
        JobValues.put(JobContracts.JobEntry.COLUMN_Date, Date);
        //Remember that this date is important ,this date mark each entry as date of creation from the server
        //Secondary Date is obsolete since all entries will depend on one value date thus date will be fetched as singular value and stored in
        //in  SharedPreference key ,thus auto delete will be locally once and for all
        JobValues.put(JobContracts.JobEntry.COLUMN_F, F);
        JobValues.put(JobContracts.JobEntry.COLUMN_F2,"<b>"+Po+"</b>");
        JobValues.put(JobContracts.JobEntry.COLUMN_Contacts, Contacts);
        JobValues.put(JobContracts.JobEntry.COLUMN_Email, Email);
        JobValues.put(JobContracts.JobEntry.COLUMN_Extras, Extras);
        c.getContentResolver().insert(JobContracts.JobEntry.CONTENT_URI, JobValues);

    }


    public static void ToOpenEntry(Context c,String id,String Po)
    {
        String selection = JobContracts.JobEntry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };
        ContentValues JobValues = new ContentValues();
        JobValues.put(JobContracts.JobEntry.COLUMN_F2,Po);
        c.getContentResolver().update(JobContracts.JobEntry.BuildJobId(id),JobValues,selection,selectionArgs);
    }


    /*
    Auto delete funtions is not that specific or savvy ,That is the date in the device may be wrong resulting in all data deleted!
    this funtion is aborted as a primary operator on this deleting operation
    A solution to this is to depend on one singular date which will be fetched and put automatically on the shared preference value
    This Function will run if and only if the server issued such date that is if the sharedpreference is not empty
    After this function this function sharedpreference will be reset to no value or empyty value
    therefore Server loads data into the db by starting a service than it will call auto delete within the server seconadry Service
     */
    public void AutoDelete(Context c){
        c.getContentResolver().delete(JobContracts.JobEntry.CONTENT_URI,
                JobContracts.JobEntry.COLUMN_Date + " <= ?",
                new String[]{Long.toString(System.currentTimeMillis() / 1000L)});
    }

    //Write this to a SharedPreference as it will help globalize the updating history
    //returns the last updates
    //should be the last call of the call of service updates after the insert session
    public String last_entry_id(Context c){
        String l ;
        try {
            String  sortOrder = JobContracts.JobEntry.COLUMN_EntryID  + " DESC";
            Cursor cursor= c.getContentResolver().query(JobContracts. JobEntry.CONTENT_URI,null,null,null,sortOrder);
            if(cursor==null)
                return "-1";
                cursor.moveToFirst();
                l =cursor.getString(cursor.getColumnIndex(JobContracts.JobEntry.COLUMN_EntryID));
                return l;

        }catch (Exception e){
            return "-200";
        }
    }






}
