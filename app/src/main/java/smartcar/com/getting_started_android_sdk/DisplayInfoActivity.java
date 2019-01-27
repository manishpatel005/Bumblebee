/**
 * Copyright (c) 2017-present, Smartcar, Inc. All rights reserved.

 * You are hereby granted a limited, non-exclusive, worldwide, royalty-free
 * license to use, copy, modify, and distribute this software in source code or
 * binary form, for the limited purpose of this software's use in connection
 * with the web services and APIs provided by Smartcar.
 *
 * As with any software that integrates with the Smartcar platform, your use of
 * this software is subject to the Smartcar Developer Agreement. This copyright
 * notice shall be included in all copies or substantial portions of the software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package smartcar.com.getting_started_android_sdk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.smartcar.sdk.SmartcarAuth;
import com.smartcar.sdk.SmartcarCallback;
import com.smartcar.sdk.SmartcarResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DisplayInfoActivity  extends AppCompatActivity {

    TextView locationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);

        Intent intent = getIntent();
        String response = intent.getStringExtra("INFO");
        TextView modelTextView = (TextView) findViewById(R.id.model_text);
        modelTextView.setTextSize(30);
        modelTextView.setText(response);

        locationTextView = new TextView(this);
        locationTextView.setVisibility(View.GONE);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_info);
        layout.addView(locationTextView);

        Button UnlockButton = (Button) findViewById(R.id.unlock_button);
        Button LockButton = (Button) findViewById(R.id.lock_button);
    }

    public void onUnlock(View view){
        final OkHttpClient client = new OkHttpClient();

        // Request can not run on the Main Thread
        // Main Thread is used for UI and therefore can not be blocked
        new Thread(new Runnable() {
            @Override
            public void run() {

                // send request to retrieve the vehicle info
                Request infoRequest = new Request.Builder()
                        .url(getString(R.string.app_server) + "/unlock")
                        .build();

                try {
                    Response response = client.newCall(infoRequest).execute();

                    String jsonBody = response.body().string();
                    System.out.println(jsonBody);
                    //JSONObject JObject = new JSONObject(jsonBody);

                    /*String make = JObject.getString("make");
                    String model = JObject.getString("model");
                    String year = JObject.getString("year");
                    */
                    System.out.println("Unlocked in client");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void onLock(View view){
        final OkHttpClient client = new OkHttpClient();

        // Request can not run on the Main Thread
        // Main Thread is used for UI and therefore can not be blocked
        new Thread(new Runnable() {
            @Override
            public void run() {

                // send request to retrieve the vehicle info
                Request infoRequest = new Request.Builder()
                        .url(getString(R.string.app_server) + "/lock")
                        .build();

                try {
                    Response response = client.newCall(infoRequest).execute();

                    String jsonBody = response.body().string();
                    System.out.println(jsonBody);
                    //JSONObject JObject = new JSONObject(jsonBody);

                    /*String make = JObject.getString("make");
                    String model = JObject.getString("model");
                    String year = JObject.getString("year");
                    */
                    System.out.println("Locked in client");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void onGetLoc(View view) throws InterruptedException {
        final OkHttpClient client = new OkHttpClient();
        final String[] latitude = new String[1];
        final String[] longitude = new String[1];

        // Request can not run on the Main Thread
        // Main Thread is used for UI and therefore can not be blocked
        Thread locationGetter = new Thread(new Runnable() {
            @Override
            public void run() {

                // send request to retrieve the vehicle info
                Request infoRequest = new Request.Builder()
                        .url(getString(R.string.app_server) + "/getLocation")
                        .build();

                try {
                    Response response = client.newCall(infoRequest).execute();

                    String jsonBody = response.body().string();
                    System.out.println(jsonBody);
                    JSONObject JObject = new JSONObject(jsonBody);
                    JSONObject data = JObject.getJSONObject("data");

                    latitude[0] = data.getString("latitude");
                    longitude[0] = data.getString("longitude");

                    System.out.println("Latitude: "+ latitude[0] +"\nLongitude: "+ longitude[0]);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        locationGetter.start();
        try {
            locationGetter.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        locationTextView.setText("Latitude: "+ latitude[0] +"\nLongitude: "+ longitude[0]);
        locationTextView.setVisibility(View.VISIBLE);
    }
}
