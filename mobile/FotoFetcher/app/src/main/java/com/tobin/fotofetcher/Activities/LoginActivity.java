package com.tobin.fotofetcher.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tobin.fotofetcher.R;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import io.fabric.sdk.android.Fabric;

public class LoginActivity extends Activity {

    // Twitter login variables
    //This is your KEY and SECRET
    //And it would be added automatically while the configuration
    private static final String TWITTER_KEY = "kbwf3Qbb6MfjXiPirco5j3UYe";
    private static final String TWITTER_SECRET = "1Lr0HOTxQBLU22FsH2CH9RFBtnQCHwjAsidZrctvYoy9yIDX7n";

    //Tags to send the username and image url to next activity using intent
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PROFILE_IMAGE_URL = "image_url";

    //Twitter Login Button
    private TwitterLoginButton twitterLoginButton;
    // End of Twitter login variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeTwitter();
        setContentView(R.layout.activity_login);
        loginTwitter();

        // Customize the font title of the app
        TextView fotoFetcherTextView = (TextView) findViewById(R.id.foto_fetcher_title_text_view);
        Typeface chubbyCheeks = Typeface.createFromAsset(getAssets(), "fonts/CHUBBY.TTF");
        fotoFetcherTextView.setTypeface(chubbyCheeks);
    }

    public void initializeTwitter() {
        //Initializing TwitterAuthConfig, these two line will also added automatically while configuration we did
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
    }

    public void loginTwitter() {
        //Initializing twitter login button
        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);

        //Adding callback to the button
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                //If login succeeds passing the Calling the login method and passing Result object
                loginTwitterInitialize(result);
            }

            @Override
            public void failure(TwitterException exception) {
                //If failure occurs while login handle it here
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });
    }

    //The login function accepting the result object
    public void loginTwitterInitialize(Result<TwitterSession> result) {

        //Creating a twitter session with result's data
        TwitterSession session = result.data;

        //Getting the username from session
        final String username = session.getUserName();

        //This code will fetch the profile image URL
        //Getting the account service of the user logged in
        Twitter.getApiClient(session).getAccountService()
                .verifyCredentials(true, false, new Callback<User>() {
                    @Override
                    public void failure(TwitterException e) {
                        //If any error occurs handle it here
                    }

                    @Override
                    public void success(Result<User> userResult) {
                        //If it succeeds creating a User object from userResult.data
                        User user = userResult.data;

                        //Getting the profile image url
                        String profileImage = user.profileImageUrl.replace("_normal", "");

                        //Creating an Intent
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                        // Save and store the twitter user name and password in the shared preferences array to pass among activities
                        SharedPreferences sharedPreferences = getSharedPreferences("twitter_creds", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_USERNAME, username);
                        editor.putString(KEY_PROFILE_IMAGE_URL, profileImage);
                        editor.commit();

                        if(sharedPreferences.contains("user_name") && sharedPreferences.contains("user_profile_image_url")) {
                            Log.d("shared", "creds saved");
                        } else
                            Log.d("shared", "creds already exist");

                        //Starting intent
                        startActivity(intent);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    // For Development purposes we need to comment out the Twitter login implementation
    public void login(View view) {
        Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeIntent);
    }

}
