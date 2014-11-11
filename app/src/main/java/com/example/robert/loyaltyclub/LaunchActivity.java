package com.example.robert.loyaltyclub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.beardedhen.androidbootstrap.BootstrapButton;


public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        BootstrapButton enterCreditsUsed = (BootstrapButton) findViewById(R.id.enter_credits_used_id);
        BootstrapButton enterCreditsBought = (BootstrapButton) findViewById(R.id.enter_credits_bought_id);
        final Intent launchCustomerDataEntry = new Intent(this, CustomerDataEntry.class);
        final Intent launchUsingCredits = new Intent(this, UsingCredits.class);
        enterCreditsUsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(launchUsingCredits);
            }
        });
        enterCreditsBought.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View view){
                startActivity(launchCustomerDataEntry);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.launch, menu);
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
