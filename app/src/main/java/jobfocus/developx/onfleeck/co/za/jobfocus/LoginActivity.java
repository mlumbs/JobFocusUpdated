package jobfocus.developx.onfleeck.co.za.jobfocus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    public static int APP_REQUEST_CODE = 1;
    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FontHelper.setCustomTypeface(findViewById(R.id.view_root));

        loginButton = (LoginButton) findViewById(R.id.facebook_login_button);
       // loginButton.setReadPermissions("email");
        loginButton.setReadPermissions(Arrays.asList("user_friends","email"));


        // Login Button callback registration
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                launchAccountActivity();

            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
                // display error
                String toastMessage = exception.getMessage();
                Toast.makeText(LoginActivity.this, "Internet connection not available...retry later", Toast.LENGTH_LONG).show();
            }
        });

        // check for an existing access token

        com.facebook.AccessToken loginToken = com.facebook.AccessToken.getCurrentAccessToken();
        if (loginToken != null) {
            // if previously logged in, proceed to the account activity
            launchAccountActivity();
        }
        // TODO Remove the the following accesstoken
        if (loginToken == null) {
            // if previously logged in, proceed to the account activity
           // Toast.makeText(LoginActivity.this,"login token is null", Toast.LENGTH_LONG).show();
        }


        TextView skip=(TextView) findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Forward result to the callback manager for Login Button
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private void launchAccountActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
