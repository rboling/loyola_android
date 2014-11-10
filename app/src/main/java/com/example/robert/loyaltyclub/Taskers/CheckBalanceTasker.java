package com.example.robert.loyaltyclub.Taskers;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;
import android.util.Log;
import android.view.inputmethod.InputBinding;

import com.example.robert.loyaltyclub.Metadata.AppMetadata;
import com.example.robert.loyaltyclub.TaskerHelpers.WriteContentHelper;
import com.example.robert.loyaltyclub.TaskerInputs.CheckBalanceTaskerInput;
import com.example.robert.loyaltyclub.UsingCredits;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by robert on 11/8/14.
 */
public class CheckBalanceTasker extends AsyncTask<CheckBalanceTaskerInput, Integer, String> {
    private static final String REQUEST_ENDPOINT_URL = "check_balance.json?";
    private static final String FULL_REQUEST_URL = AppMetadata.HEROKU_URL + CheckBalanceTasker.REQUEST_ENDPOINT_URL;
    private HttpClient httpClient;
    private HttpGet httpGet;
    private ArrayList<NameValuePair> httpParams;
    private static byte[] buff = new byte[1024];
    private UsingCredits usingCredits;
    public CheckBalanceTasker(UsingCredits usingCredits){
        this.usingCredits = usingCredits;
    }
    public String doInBackground(CheckBalanceTaskerInput...params){
        String phoneNumber = params[0].getPhoneNumber();
        String formattedRequest = String.format(FULL_REQUEST_URL,phoneNumber);
        httpParams = new ArrayList<NameValuePair>();
        httpParams.add(new BasicNameValuePair("phone_number", phoneNumber));
        String query = URLEncodedUtils.format(httpParams, "utf-8");
        httpGet = new HttpGet(FULL_REQUEST_URL + query);
        httpClient = new DefaultHttpClient();
        try {
            HttpResponse response = httpClient.execute(httpGet);
            return WriteContentHelper.writeContent(response);
        }
        catch (ClientProtocolException clientProtocolException){
            return AppMetadata.BALANCE_ERROR_RESPONSE;
        }
        catch (IOException ioException){
            return AppMetadata.BALANCE_ERROR_RESPONSE;
        }
    }
    public void onPostExecute(String responseString){
        usingCredits.updateCheckBalance(responseString);
    }
}
