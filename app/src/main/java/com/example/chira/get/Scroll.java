package com.example.chira.get;


import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.widget.Toast.LENGTH_LONG;


public class Scroll extends AppCompatActivity {
    public String url;
    private int i = 0;
    private static String hr;
    private static String db;
    private static String accx;
    private static String accy;
    private static String accz;
    private static String gx;
    private static String gy;
    private static String gz;
    private static String rw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        contactList = new ArrayList<>();

        url = "http://www.cs.odu.edu/~snagendra/sensorreading.php?patientid=1";
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (i <= 120) {
                    i++;
                    new GetContacts().execute();
                    //Toast.makeText(getApplicationContext(),"executing",Toast.LENGTH_SHORT).show();
                    handler.postDelayed(this, 1000);
                }
                // Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_SHORT).show();
            }
        }, 0);
    }



    private String TAG = Scroll.class.getSimpleName();

    // URL to get contacts JSON

    ArrayList<HashMap<String, String>> contactList;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9;


    protected void onPause(){
        super.onPause();

    }
    protected void onResume(){
        super.onResume();
        new GetContacts().execute();
    }
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            tv1=(TextView)findViewById(R.id.hr);
            tv2=(TextView)findViewById(R.id.db);
            tv3=(TextView)findViewById(R.id.accx);
            tv4=(TextView)findViewById(R.id.accy);
            tv5=(TextView)findViewById(R.id.accz);
            tv6=(TextView)findViewById(R.id.gx);
            tv7=(TextView)findViewById(R.id.gy);
            tv8=(TextView)findViewById(R.id.gz);
            tv9=(TextView)findViewById(R.id.rw);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray contacts = jsonObj.getJSONArray("result");

                    JSONObject c = contacts.getJSONObject(0);
                    hr = c.getString("hr");
                    db = c.getString("db");
                    accx = c.getString("accx");
                    accy = c.getString("accy");
                    accz = c.getString("accz");
                    gx = c.getString("gx");
                    gy = c.getString("gy");
                    gz = c.getString("gz");
                    rw = c.getString("rw");


                    HashMap<String, String> contact = new HashMap<>();

                    contact.put("hr", hr);
                    contact.put("db", db);
                    contact.put("accx", accx);
                    contact.put("accy", accy);
                    contact.put("accz", accz);
                    contact.put("gx", gx);
                    contact.put("gy", gy);
                    contact.put("gz", gz);
                    contact.put("rw",rw);

                    contactList.add(contact);

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv1.setText(hr);
                    tv2.setText(db);
                    tv3.setText(accx);
                    tv4.setText(accy);
                    tv5.setText(accz);
                    tv6.setText(gx);
                    tv7.setText(gy);
                    tv8.setText(gz);
                    tv9.setText(rw);



                }
            });
        }


    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
}
