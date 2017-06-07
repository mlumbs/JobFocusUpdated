package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import android.database.sqlite.SQLiteDatabase;

/**
 * Created by DevelopX on 2015-09-04.
 */
public class JobEntryDb extends SQLiteOpenHelper {

    static final int DATABASE_VERSION =1;
    static final String DATABASE_NAME="jobfocus.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String TIMESTAMP = " INTEGER";

    public JobEntryDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {




        String  CREATE_TABLE =  "CREATE TABLE " + JobContracts.JobEntry.TABLE_NAME + " (" +
                JobContracts.JobEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +COMMA_SEP+
                JobContracts.JobEntry.COLUMN_C + TEXT_TYPE + COMMA_SEP +
                JobContracts.JobEntry.COLUMN_Po + TEXT_TYPE + COMMA_SEP +
                JobContracts.JobEntry.COLUMN_EntryID+TIMESTAMP+COMMA_SEP+
                JobContracts.JobEntry.COLUMN_P+TEXT_TYPE+COMMA_SEP+
                JobContracts.JobEntry.COLUMN_Date+TEXT_TYPE+COMMA_SEP+
                JobContracts.JobEntry.COLUMN_F+TEXT_TYPE+COMMA_SEP+
                JobContracts.JobEntry.COLUMN_F2+TEXT_TYPE+COMMA_SEP+
                JobContracts.JobEntry.COLUMN_Contacts+TEXT_TYPE+COMMA_SEP+
                JobContracts.JobEntry.COLUMN_Email+TEXT_TYPE+COMMA_SEP+
                JobContracts.JobEntry.COLUMN_Extras+TEXT_TYPE+
                " )";

        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ JobContracts.JobEntry.TABLE_NAME);
        onCreate(db);
    }
}
