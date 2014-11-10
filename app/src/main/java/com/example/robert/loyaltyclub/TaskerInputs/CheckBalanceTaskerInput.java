package com.example.robert.loyaltyclub.TaskerInputs;

import com.example.robert.loyaltyclub.Taskers.CheckBalanceTasker;

/**
 * Created by robert on 11/8/14.
 */
public class CheckBalanceTaskerInput {
    private String phoneNumber;
    public CheckBalanceTaskerInput(String phoneNumber){
        setPhoneNumber(phoneNumber);
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
