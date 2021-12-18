package com.greenback.cashflow.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FeeType implements Serializable {

    @SerializedName("type")
    String durationType;

    @SerializedName("initial_fee")
    Double initialFee;

    @SerializedName("transaction_fee_percentage")
    Double transactionFeePercentage;

    @SerializedName("conversion_fee_percentage")
    Double conversionFeePercentage;

    @SerializedName("exchange_rate")
    Double exchangeRate;

    Double totalFee;
    Double recieverGet;
    Double transactionAmount;
    Double conversionAmount;

    public String getDurationType() {
        return durationType;
    }

    public void setDurationType(String durationType) {
        this.durationType = durationType;
    }

    public Double getInitialFee() {
        return initialFee;
    }

    public void setInitialFee(Double initialFee) {
        this.initialFee = initialFee;
    }

    public Double getTransactionFeePercentage() {
        return transactionFeePercentage;
    }

    public void setTransactionFeePercentage(Double transactionFeePercentage) {
        this.transactionFeePercentage = transactionFeePercentage;
    }

    public Double getConversionFeePercentage() {
        return conversionFeePercentage;
    }

    public void setConversionFeePercentage(Double conversionFeePercentage) {
        this.conversionFeePercentage = conversionFeePercentage;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public Double getRecieverGet() {
        return recieverGet;
    }

    public void setRecieverGet(Double recieverGet) {
        this.recieverGet = recieverGet;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Double getConversionAmount() {
        return conversionAmount;
    }

    public void setConversionAmount(Double conversionAmount) {
        this.conversionAmount = conversionAmount;
    }

    public void calculateFee(Double amount) {
        transactionAmount = (this.transactionFeePercentage/100)*amount;
        conversionAmount = (this.conversionFeePercentage/100)*amount;

        totalFee = initialFee + transactionAmount + conversionAmount;
        recieverGet = (amount - totalFee) * exchangeRate;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}