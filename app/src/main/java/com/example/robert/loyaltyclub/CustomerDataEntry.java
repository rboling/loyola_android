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
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class CustomerDataEntry extends Activity {
    public final String POSTURL = "http://stark-peak-1987.herokuapp.com/create_or_update_customer_credit";
    EditText amountOfCreditsPurchasedByUser, userPhoneNumber;
    TextView promptUserToEnterCredits;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_data_entry);
        Button submitUserCredits = (Button)this.findViewById(R.id.button_to_submit_credits_purchased);
        promptUserToEnterCredits = (TextView) findViewById(R.id.prompt_to_enter_credits_purchased_id);
        amountOfCreditsPurchasedByUser = (EditText) findViewById(R.id.amount_of_credits_purchased_by_user_id);
        userPhoneNumber = (EditText) findViewById(R.id.credits_purchased_phone_number);
        submitUserCredits.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new RequestTask().execute(POSTURL);
                    }
                });
                //String str = "The user has successfully purchased " + amountOfCreditsPurchasedByUser.getText().toString() + " of credits";
                //promptUserToEnterCredits.setText(str);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_data_entry, menu);
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

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


    private class RequestTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... uri) {
            userPhoneNumber = (EditText) findViewById(R.id.credits_purchased_phone_number);
            amountOfCreditsPurchasedByUser = (EditText) findViewById(R.id.amount_of_credits_purchased_by_user_id);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            InputStream inputStream = null;
            StatusLine statusLine;
            String responseParams = null;
            int statusLineInt = 0;
            //creditsUsedPromptText = (TextView) findViewById(R.id.using_credits_prompt);
            Log.d("THEURI", uri[0]);
            if (uri[0] == POSTURL) {
                try {
                    int merchantInt = 1;
                    //response = httpclient.execute(new HttpGet(URL)t);
                    // Add your data
                    final String phoneNumberText = userPhoneNumber.getText().toString();
                    final String amountOfCredits = amountOfCreditsPurchasedByUser.getText().toString();
                    HttpPost merchantPost = new HttpPost(POSTURL);
                    JSONObject formDetailsJson = new JSONObject();
                    JSONObject nestedFormDetailsJson = new JSONObject();
                    nestedFormDetailsJson.put("name", "");
                    nestedFormDetailsJson.put("phone_number", userPhoneNumber.getText().toString());
                    nestedFormDetailsJson.put("merchant_id", Integer.toString(merchantInt));
                    nestedFormDetailsJson.put("amount", amountOfCreditsPurchasedByUser.getText().toString());
                    //formDetailsJson.put("customer", nestedFormDetailsJson);
                    merchantPost.setHeader("Accept", "application/json");
                    merchantPost.setHeader("Content-type", "application/json");
                    StringEntity se = new StringEntity(nestedFormDetailsJson.toString());
                    merchantPost.setEntity(se);
                    response = httpclient.execute(merchantPost);
                    responseString = response.toString();
                    statusLine = response.getStatusLine();
                    statusLineInt = statusLine.getStatusCode();
                    if (statusLine.getStatusCode() == HttpStatus.SC_CREATED || statusLine.getStatusCode() == HttpStatus.SC_OK) {
                        inputStream = response.getEntity().getContent();
                        final String result = convertInputStreamToString(inputStream);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    promptUserToEnterCredits = (TextView) findViewById(R.id.prompt_to_enter_credits_purchased_id);
                                    JSONObject json = new JSONObject(result);
                                    promptUserToEnterCredits.setText("A new transaction has been made of amount: " + amountOfCredits);
                                    userPhoneNumber.setText("");
                                    amountOfCreditsPurchasedByUser.setText("");
                                } catch (JSONException e) {
                                    promptUserToEnterCredits.setText("JSONException");
                                }
                            }
                        });
                    } else {
                        //Closes the connection.
                        response.getEntity().getContent().close();
                        throw new IOException(statusLine.getReasonPhrase());
                    }
                } catch (JSONException e) {

                } catch (IOException e) {
                    Log.d("IOEXCEPTION", "an exception was thrown");
                    Log.d("STATUSCODE", Integer.toString(statusLineInt));
                }
                return responseString;
            }
            return "";
        }
    }
}
