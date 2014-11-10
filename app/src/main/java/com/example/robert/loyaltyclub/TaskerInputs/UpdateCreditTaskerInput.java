package com.example.robert.loyaltyclub.TaskerInputs;

/**
 * Created by robert on 11/9/14.
 */
public class UpdateCreditTaskerInput {
    private int merchantId;
    private int amount;
    private String phoneNumber;
    private String name;

    public UpdateCreditTaskerInput(int merchantId, int amount, String phoneNumber, String name){
        setMerchantId(merchantId);
        setAmount(amount);
        setPhoneNumber(phoneNumber);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
