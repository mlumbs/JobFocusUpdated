package jobfocus.developx.onfleeck.co.za.jobfocus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import CompontUtils.NotificationUtils;
import data.Data;
import global.MyApp;
import service.LoadService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Data data;
    AlarmReceiver alarm =new AlarmReceiver();
    //public static final String URL_BASE = "http://i6oigle.co.za/entryq.php?";
    //Uri builtUri = Uri.parse(URL_BASE).buildUpon().appendQueryParameter("q", Long.toString(0)).appendQueryParameter("n",Long.toString(0)).build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data =((MyApp)getApplication()).getData();
        //Initiate the App instances(Singleton) if it does not exist
       // data.storeEntry("800");//Store this in a Preference Automatically

    // Log.v("URL",builtUri+"");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        //collapsingToolbar.setCollapsedTitleGravity(2);
        Log.v("Tracking Data singleton", Data.track_objects+"");
       // alarm.setAlarm(getApplicationContext());


        Bundle front =new Bundle();
        front.putBoolean("fr",data.getFront());
        List_recycle fragment =  new List_recycle();
        fragment.setArguments(front);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, fragment).commit();



        //Logs.appendLog(Logs.UnixToDate(System.currentTimeMillis()),Logs.getCarrier(getApplicationContext()));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            Intent Load =new Intent(getApplicationContext(), LoadService.class);
            startService(Load);
            return true;
        }

//        if(id==R.id.start_action){
//            alarm.setAlarm(this);
//             Intent Load =new Intent(getApplicationContext(), LoadService.class);
//             startService(Load);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notification) {
            // Handle the camera action
        }
//        else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        }
//        else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void Dismiss(View view){
        //  (CustomDialogFragment)DialogFragment.dismiss();
        try {
            CustomDialogFragment close = (CustomDialogFragment) getSupportFragmentManager().findFragmentByTag("dialog");
            close.dismiss();
        }catch (Exception e){

        }



    }


    @Override
    protected void onResume() {
        super.onResume();
        data.EditBack(false);//The device is alive
        NotificationUtils.clearNotificationsID(this,NotificationUtils.NOTIFICATION_ID);
    }

    @Override
    protected void onPause() {
        super.onPause();
        data.EditBack(true); //device is not alive
    }



}
