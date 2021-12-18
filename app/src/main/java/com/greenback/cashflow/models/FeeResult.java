package com.greenback.cashflow.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FeeResult implements Serializable {

    @SerializedName("bank_name")
    String bankName;

    @SerializedName("bank_image_url")
    String bankImageUrl;

    @SerializedName("type")
    String type;

    @SerializedName("transaction_fees")
    List<FeeType> feeTypeList;

    public boolean doesHaveDurationOption(String duration) {
        boolean have = false;
        for (FeeType feeType: feeTypeList) {
            if (feeType.getDurationType().equals(duration)) {
                have = true;
            }
        }
        return have;
    }

    public Double getMaxUserGets() {
        Double max = 0.0;
        for (FeeType feeType: feeTypeList) {
            if (feeType.getRecieverGet() > max) {
                max = feeType.getRecieverGet();
            }
        }
        return max;
    }

    public void calculateFee(Double amount) {

        for (int i = 0; i<feeTypeList.size(); i++) {
            feeTypeList.get(i).calculateFee(amount);
        }
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankImageUrl() {
        return bankImageUrl;
    }

    public void setBankImageUrl(String bankImageUrl) {
        this.bankImageUrl = bankImageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<FeeType> getFeeTypeList() {
        return feeTypeList;
    }

    public void setFeeTypeList(List<FeeType> feeTypeList) {
        this.feeTypeList = feeTypeList;
    }
}