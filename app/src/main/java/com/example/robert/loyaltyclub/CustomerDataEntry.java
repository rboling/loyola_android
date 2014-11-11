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

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.robert.loyaltyclub.TaskerInputs.UpdateCreditTaskerInput;
import com.example.robert.loyaltyclub.Taskers.UpdateCreditTasker;

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
    BootstrapEditText amountOfCreditsPurchasedByUser, userPhoneNumber;
    TextView promptUserToEnterCredits;
    BootstrapButton submitUserCredits;
    private final CustomerDataEntry customerDataEntry = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_data_entry);
        submitUserCredits = (BootstrapButton)this.findViewById(R.id.button_to_submit_credits_purchased);
        promptUserToEnterCredits = (TextView) findViewById(R.id.prompt_to_enter_credits_purchased_id);
        amountOfCreditsPurchasedByUser = (BootstrapEditText) findViewById(R.id.amount_of_credits_purchased_by_user_id);
        userPhoneNumber = (BootstrapEditText) findViewById(R.id.credits_purchased_phone_number);
        submitUserCredits.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                String name = "";
                int merchantId = 1;
                int amount = Integer.parseInt(amountOfCreditsPurchasedByUser.getText().toString());
                UpdateCreditTaskerInput updateCreditTaskerInput = new UpdateCreditTaskerInput(merchantId,amount,userPhoneNumber.getText().toString(),name);
                UpdateCreditTasker updateCreditTasker = new UpdateCreditTasker(customerDataEntry);
                updateCreditTasker.execute(updateCreditTaskerInput);
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

    public void updateCustomerDataEntryUi(String responseString){
        try {
            String amountOfCredits = amountOfCreditsPurchasedByUser.getText().toString();
            promptUserToEnterCredits = (TextView) findViewById(R.id.prompt_to_enter_credits_purchased_id);
            JSONObject json = new JSONObject(responseString);
            promptUserToEnterCredits.setText("A new transaction has been made of amount: " + amountOfCredits);
            userPhoneNumber.setText("");
            amountOfCreditsPurchasedByUser.setText("");
        }
        catch (JSONException e){
            promptUserToEnterCredits.setText("JSONException");
        }
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


}
