package jobfocus.developx.onfleeck.co.za.jobfocus;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.NestedScrollView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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


    private String mDetail="The link is broken";

    private Uri mUri;

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




    CollapsingToolbarLayout collapsingToolbar ;


    public JobDetailFragment() {
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        all = "^Hi /and <bold #italics#adsfs Sometimes there is ^need  XML,  #code #in an Activity  ";

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(JobDetailFragment.DETAIL_URI);
        }


        View rootView = inflater.inflate(R.layout.jobdetail_layout,
                container, false);

        mContainerView = (ViewGroup) rootView.findViewById(R.id.container);

        new UIbuilder().execute(mUri);
        //new DownloadFilesTask().execute(url1, url2, url3);





//
//        FloatingActionButton fab = (FloatingActionButton)getActivity().findViewById(R.id.fabshare);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // alarm.setAlarm(getApplicationContext());
//
//            }
//        });
//



        // A= (TextView) rootView.findViewById(R.id.c);
        // B = (TextView) rootView.findViewById(R.id.p);
        // C=(TextView)rootView.findViewById(R.id.po);
        // E=(TextView)rootView.findViewById(R.id.e);
        // CO = (TextView) rootView.findViewById(R.id.contacts);
        // EX = (TextView) rootView.findViewById(R.id.ex);

                //or
         //WebView = (WebView) rootView.findViewById(R.id.webview);


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
        //    A.setTypeface(Roboto2);
         //   B.setText(Html.fromHtml(data.getString(COL_Po)));
         //   B.setTypeface(Roboto1);



//            C.setText(Html.fromHtml(data.getString(COL_p)));
           // E.setText(Html.fromHtml(data.getString(COL_email)));
            //CO.setText(Html.fromHtml(data.getString(COL_contacts)));
           // EX.setText(Html.fromHtml(data.getString(COL_extras)));
                          //or
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
           // data.getString(COL_p)


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
        int id = v.getId();
        switch (id){
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1:

                Log.d("Raj", "Fab 1");
                break;
            case R.id.fab2:

                Log.d("Raj", "Fab 2");
                break;
        }
    }


    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }


    }

    void addT(int j,int tag){
        StringBuilder b = new StringBuilder();
        char bre ='^';
        char bre2='#';
        char bre3='<';
        char bre4='/';
        for(int i=j+1;i<all.length();i++){
            if(all.charAt(i)==bre||all.charAt(i)==bre2||all.charAt(i)==bre3||all.charAt(i)==bre4)
                break;
            b.append(all.charAt(i));
            //System.out.println(i);
        }
        //System.out.println(b);
        Log.v("values",b.toString());

        switch(tag){
            case 0:
                TextView tv = new TextView(getActivity());
                tv.setText(b.toString());
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                tv.setTypeface(Typeface.DEFAULT_BOLD);
                tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                mContainerView.addView(tv);
                break;
            case 1:
                TextView tv0 = new TextView(getActivity());
                tv0.setText(b.toString());
                tv0.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                tv0.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                tv0.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                mContainerView.addView(tv0);
                break;
            case 2:
                TextView tv1 = new TextView(getActivity());
                tv1.setText(b.toString());
                tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
                mContainerView.addView(tv1);
                break;
            case 3:
                TextView tv2 = new TextView(getActivity());
                tv2.setText(b.toString());
                tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
                mContainerView.addView(tv2);
                break;
            case 4:

                break;
            default:

        }


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
                //all=data.getString(COL_p);

                for(int i=0;i<all.length();i++){
                    //System.out.print(all.charAt(i) );

                    switch (all.charAt(i)) {
                        case '^':
                            addT(i,0); //0 is Title
                            break;
                        case '#':
                            addT(i,1);//1 is List
                            break;
                        case '/':
                            addT(i,2);//2 is Italics
                            break;
                        case '<':
                            addT(i,3); //3 is Bold
                            break;
                        default:
                            break;
                    }
                }




            }

        }
    }







}

