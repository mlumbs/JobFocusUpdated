package jobfocus.developx.onfleeck.co.za.jobfocus;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
public class CustomDialogFragment extends DialogFragment implements View.OnClickListener {

    /*
    This is the dialogue for a tablet screen called differently from that of detail Activity

     */

    FloatingActionButton fab;

    @Override
    public void onClick(View v) {

    }

    static final String DETAIL_URI_TAB = "URI";
    private Uri mUri;
    WebView WebView;
    String a_id ="i6oigle.co.za/jobsharehost.php?p=";
    String mDetail;
    String data0= "";
    String style="<style>\n" +
            ".col-sm-5 {\n" +
            "    width: 41.6667%;\n" +
            "}\n" +
            ".form-control {\n" +
            "  display: block;\n" +
            "  width: 100%;\n" +
            "  height: 34px;\n" +
            "  padding: 6px 12px;\n" +
            "  font-size: 14px;\n" +
            "  line-height: 1.42857143;\n" +
            "  color: #555;\n" +
            "  background-color: #fff;\n" +
            "  background-image: none;\n" +
            "  border: 1px solid #ccc;\n" +
            "  border-radius: 4px;\n" +
            "  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);\n" +
            "          box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);\n" +
            "  -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;\n" +
            "}\n" +
            "</style>";
    String viewport ="<meta name=\"viewport\" content=\"width=device-width, user-scalable=no\" />";
    String forms ="<form class=\"col-sm-5\">\n" +
            "<input class=\"form-control\" type=\"text\" name=\"firstname\" placeholder=\"First name\">\n" +
            "<br>\n" +
            "<input class=\"form-control\" type=\"text\" name=\"lastname\" placeholder=\"First name\">\n" +
            "</form> ";
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
    String all;


    private TextView A ;
    private TextView B ;




    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment


        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(CustomDialogFragment.DETAIL_URI_TAB);
            mDetail = arguments.getString(JobAdapter.EXTRA_MESSAGE);
        }
        all="";
        data0 = "<head><title>Hello World</title>";
        data0 += viewport+style+"</head>";
       //data0 += "<body>"+forms+"</body>";
        new UIbuilder().execute(mUri);

        data0 += "<body>"+"</body>";
        data0 += "</html>";

        View rootView = inflater.inflate(R.layout.tabpop, container, false);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
//nc
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send =new Intent();
                send.setAction(Intent.ACTION_SEND);
                send.putExtra(Intent.EXTRA_TEXT,a_id+mDetail);
                send.setType("text/plain");
                getActivity().startActivity(send.createChooser(send,"Share this post with"));

            }
        });

        TextView b= (TextView) rootView.findViewById(R.id.pop_title);
        b.setText(arguments.getString(JobAdapter.EXTRA_MESSAGE_TITLE));


//        Intent send =new Intent();
//        send.setAction(Intent.ACTION_SEND);
//        send.putExtra(Intent.EXTRA_TEXT, a_id+mDetail);
//        send.setType("text/plain");
//        // startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
//        startActivityForResult(send.createChooser(send,"Share this post with"),SEND_REQUEST);





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
        //dialog.setTitle("Hi");
       // dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return dialog;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    //    getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    class UIbuilder extends AsyncTask<Uri, Void,Cursor> {
        //SQLiteDatabase db = mDbHelper.getReadableDatabase();
        protected Cursor doInBackground(Uri... id) {
            if ( null != mUri ) {
                //   Log.v("Job onCreateLoader", "mUri is"+mUri);
                // Now create and return a CursorLoader that will take care of
                // creating a Cursor for the data being displayed.
                return getActivity().getContentResolver().query(mUri,DETAIL_COLUMNS,null,null,null);

            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            // setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Cursor data) {
            if (data != null && data.moveToFirst()) {
                all=data.getString(COL_p);
                data0+=all;
                WebView.loadData(data0, "text/html", "UTF-8");

//                Snackbar.make(rootView, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }

        }
    }





}
