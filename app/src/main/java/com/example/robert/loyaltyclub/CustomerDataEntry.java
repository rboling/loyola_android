package com.example.robert.loyaltyclub;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class CustomerDataEntry extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_data_entry);
        Button submitUserCredits = (Button)this.findViewById(R.id.button_to_submit_credits_purchased);
        submitUserCredits.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                TextView promptUserToEnterCredits = (TextView) findViewById(R.id.prompt_to_enter_credits_purchased_id);
                EditText amountOfCreditsPurchasedByUser = (EditText) findViewById(R.id.amount_of_credits_purchased_by_user_id);
                String str = "The user has successfully purchased " + amountOfCreditsPurchasedByUser.getText().toString() + " of credits";
                promptUserToEnterCredits.setText(str);
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
}
