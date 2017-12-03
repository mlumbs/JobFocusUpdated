package jobfocus.developx.onfleeck.co.za.jobfocus;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;


import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;

import java.util.Locale;

import CompontUtils.NotificationUtils;
import data.Data;
import global.MyApp;
import service.LoadService;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Data data;

    ProfileTracker profileTracker;
    ImageView profilePic;
    TextView id;
    TextView infoLabel;
    TextView info;

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
       //


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
        View header = navigationView.getHeaderView(0);


//        Facebook
//

        profilePic = (ImageView) header.findViewById(R.id.profile_image);
        id = (TextView) header.findViewById(R.id.id);
        infoLabel = (TextView) header.findViewById(R.id.info_label);
        info = (TextView) header.findViewById(R.id.info);
        FontHelper.setCustomTypeface(header.findViewById(R.id.view_root));// Facebook
        // register a receiver for the onCurrentProfileChanged event
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged (Profile oldProfile, Profile currentProfile) {
                if (currentProfile != null) {
                    displayProfileInfo(currentProfile);
                }
            }
        };

        if (AccessToken.getCurrentAccessToken() != null) {
            // If there is an access token then Login Button was used
            // Check if the profile has already been fetched
            Profile currentProfile = Profile.getCurrentProfile();
            if (currentProfile != null) {
                displayProfileInfo(currentProfile);
            }
            else {
                // Fetch the profile, which will trigger the onCurrentProfileChanged receiver
                Profile.fetchProfileForCurrentAccessToken();
            }
        }



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

    @Override
    public void onDestroy() {
        super.onDestroy();

        // unregister the profile tracker receiver
        profileTracker.stopTracking();
    }
    public void onLogout(View view) {

        // logout of Login Button
        LoginManager.getInstance().logOut();

        launchLoginActivity();
    }

    private void displayProfileInfo(Profile profile) {
        Log.v("Facebook",profile.getName()+" id "+profile.getId());
        // get Profile ID
        String profileId = profile.getId();
        id.setText(profileId);

        //display the Profile name
        String name = profile.getName();
       info.setText(name);
       infoLabel.setText(R.string.name_label);
        // display the profile picture
        Uri profilePicUri = profile.getProfilePictureUri(100, 100);
        displayProfilePic(profilePicUri);
    }

    private void launchLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void displayProfilePic(Uri uri) {
        // helper method to load the profile pic in a circular imageview
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(30)
                .oval(false)
                .build();
        Picasso.with(MainActivity.this)
                .load(uri)
                .transform(transformation)
                .into(profilePic);
    }


}
