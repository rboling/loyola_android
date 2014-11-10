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

import com.example.robert.loyaltyclub.TaskerInputs.CheckBalanceTaskerInput;
import com.example.robert.loyaltyclub.TaskerInputs.UpdateCreditTaskerInput;
import com.example.robert.loyaltyclub.Taskers.CheckBalanceTasker;
import com.example.robert.loyaltyclub.Taskers.UpdateCreditTasker;

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
import org.apache.http.params.HttpParams;
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
    public final String POSTURL = "http://stark-peak-1987.herokuapp.com/create_or_update_customer_credit";
    public final String CHECK_BALANCE_URL = "http://stark-peak-1987.herokuapp.com/check_balance.json";
    public String responseString = null;
    InputStream inputStream = null;
    EditText phoneNumber;
    EditText creditsUsed;
    TextView creditsUsedPromptText;
    Button checkBalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int merchantId = 1;
        final UsingCredits usingCredits = this;
        setContentView(R.layout.activity_using_credits);
        phoneNumber = (EditText) findViewById(R.id.enter_phone_number_id);
        creditsUsed = (EditText) findViewById(R.id.enter_the_amount_of_credits_used);
        creditsUsedPromptText = (TextView) findViewById(R.id.using_credits_prompt);
        checkBalance = (Button)findViewById(R.id.check_balance_button);
        Button usingCreditsButton = (Button)findViewById(R.id.using_credits_button);
        usingCreditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                creditsUsedPromptText = (TextView) findViewById(R.id.using_credits_prompt);
                int amount = Integer.parseInt(creditsUsed.getText().toString())*-1;
                UpdateCreditTaskerInput updateCreditTaskerInput = new UpdateCreditTaskerInput(merchantId,amount,phoneNumber.getText().toString(),creditsUsed.getText().toString());
                UpdateCreditTasker updateCreditTasker = new UpdateCreditTasker(usingCredits);
                updateCreditTasker.execute(updateCreditTaskerInput);
            }
        });
        checkBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBalanceTasker checkBalanceTasker = (new CheckBalanceTasker(usingCredits));
                checkBalanceTasker.execute(new CheckBalanceTaskerInput(phoneNumber.getText().toString()));
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

    // Updates the UI after the CheckBalanceTasker completes
    public void updateCheckBalance(String responseString){
        try {
            creditsUsedPromptText = (TextView) findViewById(R.id.using_credits_prompt);
            JSONObject json = new JSONObject(responseString);
            String balance = json.getString("balance");
            creditsUsedPromptText.setText("Successful get request balance is: " + balance);
            phoneNumber.setText("");
            creditsUsed.setText("");
        } catch (JSONException e) {
            creditsUsedPromptText.setText("JSONException");
        }
    }
    // Updates the UI after the UpdateCreditTasker completes
    public void updateCustomerCredit(String responseString){
        try {
            String phoneNumberText = phoneNumber.getText().toString();
            creditsUsedPromptText = (TextView) findViewById(R.id.using_credits_prompt);
            JSONObject json = new JSONObject(responseString);
            creditsUsedPromptText.setText("A customer with the phone number of " + phoneNumberText + " has been registered");
            phoneNumber.setText("");
            creditsUsed.setText("");
        } catch (JSONException e) {
            creditsUsedPromptText.setText("JSONException");
        }
    }

}
