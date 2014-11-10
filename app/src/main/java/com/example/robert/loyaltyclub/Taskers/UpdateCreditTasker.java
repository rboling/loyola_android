package com.example.robert.loyaltyclub.Taskers;

import android.os.AsyncTask;

import com.example.robert.loyaltyclub.CustomerDataEntry;
import com.example.robert.loyaltyclub.Metadata.AppMetadata;
import com.example.robert.loyaltyclub.TaskerHelpers.WriteContentHelper;
import com.example.robert.loyaltyclub.TaskerInputs.UpdateCreditTaskerInput;
import com.example.robert.loyaltyclub.UsingCredits;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

/**
 * Created by robert on 11/9/14.
 */
public class UpdateCreditTasker extends AsyncTask<UpdateCreditTaskerInput,Integer,String> {
    private final String REQUEST_ENDPOINT = "create_or_update_customer_credit";
    private HttpPost httpPost;
    private HttpClient httpClient;
    private UsingCredits usingCredits;
    private CustomerDataEntry customerDataEntry;

    public UpdateCreditTasker(UsingCredits usingCredits){
        this.usingCredits = usingCredits;
    }

    public UpdateCreditTasker(CustomerDataEntry customerDataEntry){
        this.customerDataEntry = customerDataEntry;
    }

    public String doInBackground(UpdateCreditTaskerInput...params){
        UpdateCreditTaskerInput updateCreditTaskerInput = params[0];
        httpClient = new DefaultHttpClient();
        httpPost = new HttpPost(AppMetadata.HEROKU_URL + REQUEST_ENDPOINT);
        JSONObject formDetailsJson = new JSONObject();
        JSONObject nestedFormDetailsJson = new JSONObject();
        try {
            nestedFormDetailsJson.put("name", updateCreditTaskerInput.getName());
            nestedFormDetailsJson.put("amount", (updateCreditTaskerInput.getAmount()));
            nestedFormDetailsJson.put("merchant_id", updateCreditTaskerInput.getMerchantId());
            nestedFormDetailsJson.put("phone_number", updateCreditTaskerInput.getPhoneNumber());
            formDetailsJson.put("customer", nestedFormDetailsJson);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            StringEntity se = new StringEntity(nestedFormDetailsJson.toString());
            httpPost.setEntity(se);
            return WriteContentHelper.writeContent(httpClient.execute(httpPost));
        }
        catch (Exception e){
            return "EXCEPTION";
        }
    }
    public void onPostExecute(String resultString){
        if (usingCredits != null) {
            usingCredits.updateCustomerCredit(resultString);
        }
        else{
            customerDataEntry.updateCustomerDataEntryUi(resultString);
        }
    }
}
