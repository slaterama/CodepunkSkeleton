package com.codepunk.skeleton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.codepunk.skeleton.app.GsonRequest;
import com.codepunk.skeleton.app.OAuthToken;
import com.codepunk.skeleton.app.VolleyManager;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private VolleyManager mVolleyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVolleyManager = VolleyManager.getInstance(this);

        // TODO Wrap this in an "Api" object where I can just call an Api method with onSuccess and onFailure
        // i.e. mApi.getToken(clientId, clientSecret, username, password, onResponse, onErrorResponse);
        // Also that Api class would be one of my new "plugin" classes based on dev/prod/etc.
        try {
            URL url = new URL(
                    "http", // Local MAMP can't use https. TODO: Test for real website
                    "10.0.2.2", // For Genymotion to access localhost
                    8888,
                    "app_dev.php/oauth/v2/token?client_id=1_628lp4leuq8so888k0wso0ogow8k4ws0gokcskwcwwscgo4kw4&client_secret=2ez8ois0hvi848kgkksoskoo0sosw48go84k0gs804w0s4s4wc&grant_type=password&username=scott&password=r3stlandF");

            final GsonRequest<OAuthToken> request = new GsonRequest<>(
                    url.toString(),
                    OAuthToken.class,
                    null,
                    new Response.Listener<OAuthToken>() {
                        @Override
                        public void onResponse(OAuthToken response) {
                            Log.d("slaterama", "");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("slaterama", "");
                        }
                    });

            mVolleyManager.getRequestQueue(new VolleyManager.OnRequestQueueReadyListener() {
                @Override
                public void onRequestQueueReady(RequestQueue requestQueue) {
                    Log.d("slaterama", "");
                    requestQueue.add(request);
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
