package jobfocus.developx.onfleeck.co.za.jobfocus;

import android.app.Dialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;

import data.JobContracts;

/**
 * Created by DevelopX on 2016-07-22.
 */
public class CustomDialogFragment extends DialogFragment implements View.OnClickListener ,LoaderManager.LoaderCallbacks<Cursor>{

    /*
    This is the dialogue for a tablet screen called differently from that of detail Activity

     */


    @Override
    public void onClick(View v) {

    }

    static final String DETAIL_URI_TAB = "URI";
    private Uri mUri;
    WebView WebView;
    private static final int DETAIL_LOADER = 0;

    private static final String[] DETAIL_COLUMNS = {
            JobContracts.JobEntry._ID ,
            JobContracts.JobEntry.COLUMN_C ,
            JobContracts.JobEntry.COLUMN_Po ,
            JobContracts.JobEntry.COLUMN_EntryID,
            JobContracts.JobEntry.COLUMN_P,
            JobContracts.JobEntry.COLUMN_Date,
            JobContracts.JobEntry.COLUMN_F,
            JobContracts.JobEntry.COLUMN_Contacts,
            JobContracts.JobEntry.COLUMN_Email,
            JobContracts.JobEntry.COLUMN_Extras,
    };

    static final int COL_JOB = 0;
    public static final int COL_c = 1;
    static final int COL_Po= 2;
    static final int COL_entryid=3;// Not UI
    static final int COL_p= 4;
    static final int COL_date= 5;//Date+UI
    static final int COL_F = 6;
    static final int COL_contacts = 7;
    static final int COL_email= 8;
    static final int COL_extras= 9;


    private TextView A ;
    private TextView B ;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if ( null != mUri ) {
            //   Log.v("Job onCreateLoader", "mUri is"+mUri);
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    DETAIL_COLUMNS,
                    null,
                    null,
                    null
            );

        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
           // A.setText(Html.fromHtml(data.getString(COL_c)));
           // B.setText(Html.fromHtml(data.getString(COL_Po)));
       String temp ="Note that JavaScript's same origin policy means that script running in a page loaded using this method will " +
                    "be unable to access content loaded using any scheme other than 'data'," +
                    "including 'http(s)'. To avoid this restriction, use loadDataWithBaseURL() with an appropriate base URL.";
            String data0;
            data0 = "<!DOCTYPE html>";
            String font="<style type=\\\"text/css\\\">@font-face {font-family: Roboto;src: url(\\\"file:///android_asset/Roboto-Light.ttf\\\")}body {font-family: Roboto;font-size: medium;text-align: justify;}</style>";
            data0 += "<head>"+font+"</head>";
            data0 += "<body style='background-color: #d0e4fe;'><div style='margin-top:50px;'>"+"<p>"+temp+data.getString(COL_c)+"</p>"+"<p>"+data.getString(COL_Po)+temp+temp+temp+temp+temp+"</p>"+data.getString(COL_p)+"</div></body>";
            data0 += "</html>";
            // args: data, mimeType, encoding
           // WebView.loadData(data0, "text/html", "UTF-8");
            WebView.loadDataWithBaseURL(null,data0,"text/html","UTF-8",null);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment


        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(CustomDialogFragment.DETAIL_URI_TAB);
        }
        View rootView = inflater.inflate(R.layout.tabpop, container, false);

//nc


       // A= (TextView) rootView.findViewById(R.id.c);
       // B = (TextView) rootView.findViewById(R.id.p);
        WebView = (WebView) rootView.findViewById(R.id.webview);
        WebView.setBackgroundColor(0);
        return rootView;
    }

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       // dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return dialog;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }






}
