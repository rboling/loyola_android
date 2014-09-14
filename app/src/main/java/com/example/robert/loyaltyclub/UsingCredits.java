package com.example.robert.loyaltyclub;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

public class UsingCredits extends Activity {
    public final String URL = "http://stark-peak-1987.herokuapp.com/merchants.json";
    public final String POSTURL = "http://stark-peak-1987.herokuapp.com/customers";
    public String responseString = null;
    InputStream inputStream = null;
    EditText phoneNumber;
    EditText creditsUsed;
    TextView creditsUsedPromptText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneNumber = (EditText) findViewById(R.id.enter_phone_number_id);
        creditsUsed = (EditText) findViewById(R.id.enter_the_amount_of_credits_used);
        creditsUsedPromptText = (TextView) findViewById(R.id.using_credits_prompt);
        setContentView(R.layout.activity_using_credits);
        Button usingCreditsButton = (Button)findViewById(R.id.using_credits_button);
        usingCreditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                creditsUsedPromptText = (TextView) findViewById(R.id.using_credits_prompt);
                if (creditsUsedPromptText == null) {
                    Log.d("CREDITSUSEDPROMPTTEXT", "it is null");
                }
                else{
                    Log.d("CREDITSUSEDPROMPTTEXT", "it is not null");
                }
                //creditsUsedPromptText.setText("foo");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new RequestTask().execute(URL);
                    }
                });
                //creditsUsedPromptText.setText("the user's phone number is: " + phoneNumber.getText().toString() + " and the credits used is: " + creditsUsed.getText().toString());
            }
        });
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.using_credits, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private class RequestTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... uri) {
            phoneNumber = (EditText) findViewById(R.id.enter_phone_number_id);
            creditsUsed = (EditText) findViewById(R.id.enter_the_amount_of_credits_used);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            InputStream inputStream = null;
            StatusLine statusLine;
            String responseParams = null;
            int statusLineInt = 0;
            //creditsUsedPromptText = (TextView) findViewById(R.id.using_credits_prompt);
            try {
                //response = httpclient.execute(new HttpGet(URL)t);
                // Add your data
                HttpPost merchantPost = new HttpPost(POSTURL);
                JSONObject formDetailsJson = new JSONObject();
                JSONObject nestedFormDetailsJson = new JSONObject();
                nestedFormDetailsJson.put("name",creditsUsed.getText().toString());
                nestedFormDetailsJson.put("phone_number",phoneNumber.getText().toString());
                formDetailsJson.put("customer", nestedFormDetailsJson);
                merchantPost.setHeader("Accept", "application/json");
                merchantPost.setHeader("Content-type", "application/json");
                StringEntity se = new StringEntity( formDetailsJson.toString());
                merchantPost.setEntity(se);
                response = httpclient.execute(merchantPost);
                responseString = response.toString();
                statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_CREATED){
                    inputStream = response.getEntity().getContent();
                    final String result = convertInputStreamToString(inputStream);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                creditsUsedPromptText = (TextView) findViewById(R.id.using_credits_prompt);
                                JSONObject json = new JSONObject(result);
                                creditsUsedPromptText.setText("great success");
                                phoneNumber.setText("");
                                creditsUsed.setText("");
                            }
                            catch (JSONException e){
                                creditsUsedPromptText.setText("JSONException");
                            }
                        }
                    });
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            }catch (JSONException e){

            }
            catch (IOException e) {
                Log.d("IOEXCEPTION","an exception was thrown");
            }
            return responseString;
        }
    }
}
