package jobfocus.developx.onfleeck.co.za.jobfocus;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import data.DatabaseCrud;
import data.JobContracts;

//import com.bumptech.glide.Glide;

/**
 * Created by DevelopX on 2015-12-28.
 */
public class JobDetailFragment extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private ViewGroup mContainerView;
    String all;
    Typeface Roboto1;
    Typeface Roboto2;
    Typeface Roboto3;
    Typeface Roboto4;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    private static final String LOG_TAG = JobDetailFragment.class.getSimpleName();
    static final String DETAIL_URI = "URI";
    WebView WebView;
    NestedScrollView sc;

    private String mDetail="The link is broken";

    private Uri mUri;
     String data0 = "<head><title>Hello World</title>";
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


    private TextView A ;
    private TextView B ;
    private TextView C;
    private TextView CO;
    private TextView E;
    private TextView EX;



    View rootView;
    CollapsingToolbarLayout collapsingToolbar ;


    public JobDetailFragment() {
        setHasOptionsMenu(true);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //all = "^Hi /and <bold #italics#adsfs Sometimes there is ^need XML,#code #in an Activity";


        all="";
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(JobDetailFragment.DETAIL_URI);
        }
        data0 += viewport+style+"</head>";
        data0 += "<body>"+forms+"</body>";


       rootView = inflater.inflate(R.layout.jobdetail_layout,
                container, false);

      // mContainerView = (ViewGroup) rootView.findViewById(R.id.container);

        new UIbuilder().execute(mUri);
        //new DownloadFilesTask().execute(url1, url2, url3);

        data0 += "<body>"+"</body>";
        data0 += "</html>";


        sc = (NestedScrollView) rootView.findViewById(R.id.sc);
        sc.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {


                if ((((JobDetails)getActivity()).getStatus())){

                    ((JobDetails)getActivity()).hideFAB();
                    ((JobDetails)getActivity()).setStatus(false);
                    Toast.makeText(getActivity(),"hides", Toast.LENGTH_SHORT).show();
                }
            }
        });

         WebView = (WebView) rootView.findViewById(R.id.webview);

        // Let's display the progress in the activity title bar, like the
        // browser app does.


        WebView.getSettings().setJavaScriptEnabled(true);

//        final Activity activity =getActivity();
//        WebView.setWebChromeClient(new WebChromeClient() {
//            public void onProgressChanged(WebView view, int progress) {
//                // Activities and WebViews measure progress with different scales.
//                // The progress meter will automatically disappear when we reach 100%
//                activity.setProgress(progress * 1000);
//            }
//        });
//        WebView.setWebViewClient(new WebViewClient() {
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
//            }
//        });

       // WebView.loadUrl("https://developer.android.com/");






        return rootView;
    }



    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
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
            String a_id ="i6oigle.co.za/jobsharehost.php?p="+data.getString(COL_entryid);

                 mDetail=Html.fromHtml(data.getString(COL_Po))+" : "+Html.fromHtml(data.getString(COL_c))+" or click here "+a_id +"You can download JobShare App from Play Store for similar job postings";

            String Company ="<h1 style='text-align:center;'>"+data.getString(COL_c)+"</h1>";
            String Position ="<h2 style='text-align:center;'>"+data.getString(COL_p)+"</h2>";

//            A.setText(Html.fromHtml(data.getString(COL_c)));
        // A.setTypeface(Roboto2);
         //   B.setText(Html.fromHtml(data.getString(COL_Po)));
         //   B.setTypeface(Roboto1);



//            C.setText(Html.fromHtml(data.getString(COL_p)));
           // E.setText(Html.fromHtml(data.getString(COL_email)));
            //CO.setText(Html.fromHtml(data.getString(COL_contacts)));
           // EX.setText(Html.fromHtml(data.getString(COL_extras)));
                          //or





           // WebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
//            String body_content=data.getString(COL_p);
//                    String data0 = "<!DOCTYPE html>";
//            data0 += "<head><title>Hello World</title>";
//          //  data0 += viewport+style+"</head>";
//            //data0 += "<body>"+forms+"</body>";
//            data0 += "<body>"+body_content+"</body>";
//           data0 += "</html>";
//            // args: data, mimeType, encoding
//            WebView.loadData(data0, "text/html", "UTF-8");
//            //WebView.setWebViewClient(new AppWeb(new ProgressBar(getActivity())));
//            WebSettings settings =WebView.getSettings();
//          WebView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
//            WebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//            WebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//           WebView.setFocusable(false);
//            WebView.setFocusableInTouchMode(false);
//            settings.setJavaScriptEnabled(true);

            DatabaseCrud.ToOpenEntry(getActivity(),data.getString(COL_JOB),data.getString(COL_Po));



        }

// arr[i].setMovementMethod(LinkMovementMethod.getInstance());


    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    @Override
public void onAttach(Context activity){
super.onAttach(activity);

         Roboto1 =Typeface.createFromAsset(getActivity().getAssets(),"Roboto-Light.ttf");
         Roboto2=Typeface.createFromAsset(getActivity().getAssets(),"Roboto-Regular.ttf");
         Roboto3=Typeface.createFromAsset(getActivity().getAssets(),"Roboto-Medium.ttf");
         Roboto4=Typeface.createFromAsset(getActivity().getAssets(),"RobotoCondensed-Light.ttf");

}

    @Override
    public void onResume() {
        super.onResume();


    }






    @Override
    public void onClick(View v) {

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

