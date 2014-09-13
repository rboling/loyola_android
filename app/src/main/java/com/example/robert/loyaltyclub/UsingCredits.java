package com.example.robert.loyaltyclub;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class UsingCredits extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_credits);
        Button usingCreditsButton = (Button)findViewById(R.id.using_credits_button);
        usingCreditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText phoneNumber = (EditText) findViewById(R.id.enter_phone_number_id);
                EditText creditsUsed = (EditText) findViewById(R.id.enter_the_amount_of_credits_used);
                TextView creditsUsedPromptText = (TextView) findViewById(R.id.using_credits_prompt);
                creditsUsedPromptText.setText("the user's phone number is: " + phoneNumber.getText().toString() + " and the credits used is: " + creditsUsed.getText().toString());
            }
        });
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
}
