package jobfocus.developx.onfleeck.co.za.jobfocus;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import data.JobContracts;



/**
 * Created by DevelopX on 2015-12-27.
 */
public class List_recycle extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

//Have a list attribute or value of the notification

    public static final String LOG= List_recycle.class.getSimpleName();
    public static final int JOB_LOADER = 0;
    boolean front;
    private static final String[] JOB_COLUMNS = {
            JobContracts.JobEntry._ID ,
            JobContracts.JobEntry.COLUMN_C,
            JobContracts.JobEntry.COLUMN_Po,
            JobContracts.JobEntry.COLUMN_EntryID,
            JobContracts.JobEntry.COLUMN_P,
            JobContracts.JobEntry.COLUMN_Date,
            JobContracts.JobEntry.COLUMN_F,
            JobContracts.JobEntry.COLUMN_F2,
            JobContracts.JobEntry.COLUMN_Contacts,
            JobContracts.JobEntry.COLUMN_Email,
            JobContracts.JobEntry.COLUMN_Extras,
    };

    static final int COL_JOB = 0;
    static final int COL_C = 1;
    static final int COL_PO= 2;
    static final int COL_EntryID=3;
    static final int COL_P= 4;
    static final int COL_DATE= 5;
    static final int COL_F = 6;
    static final int COL_F2 = 7;
    static final int COL_CONTACTS = 8;
    static final int COL_EMAIL= 9;
    static final int COL_EXTRAS= 10;


    private JobAdapter mAdapter;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        front= getArguments().getBoolean("fr");
        View rootView = inflater.inflate(R.layout.list_recycle_feed,
                container, false);

        if(getResources().getBoolean(R.bool.is_tab_)){
            LinearLayout r =(LinearLayout)rootView.findViewById(R.id.list_recycled_feed);
            r.setBackgroundColor(getResources().getColor(R.color.color_tab));
        }
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.lrecycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new JobAdapter(getContext(),front);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        getLoaderManager().restartLoader(JOB_LOADER, null, this);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(JOB_LOADER, null, this);

    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
      String sortOrder = JobContracts.JobEntry._ID + " DESC";
        return new CursorLoader(getActivity(),
                JobContracts.JobEntry.CONTENT_URI,
                JOB_COLUMNS,
                null,
                null,
                sortOrder);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }




    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
