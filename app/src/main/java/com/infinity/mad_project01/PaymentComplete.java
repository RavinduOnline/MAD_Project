package com.infinity.mad_project01;

import android.content.Intent;
import android.view.View;

import java.io.Serializable;

public class PaymentComplete implements Serializable {

    String productName, productDescription;

    public PaymentComplete(){

    }

    public PaymentComplete(String productName, String productDescription) {
        this.productName = productName;
        this.productDescription = productDescription;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }


}
