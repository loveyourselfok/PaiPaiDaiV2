package com.tencent.paipaidai.bean;

/**
 * Created by Administrator on 2017/4/8/008.
 */

public class NewVersionLoanInfoBean {

    /**
     * ListingId : 41148289
     * Title : aiyehui的应收款安全标
     * CreditCode : AAA
     * Amount : 50000.0
     * Rate : 7.0
     * Months : 12
     * PayWay : 0
     * RemainFunding : 33913.0
     */

    private int ListingId;
    private String Title;
    private String CreditCode;
    private double Amount;
    private double Rate;
    private int Months;
    private int PayWay;
    private double RemainFunding;

    public int getListingId() {
        return ListingId;
    }

    public void setListingId(int ListingId) {
        this.ListingId = ListingId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getCreditCode() {
        return CreditCode;
    }

    public void setCreditCode(String CreditCode) {
        this.CreditCode = CreditCode;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public double getRate() {
        return Rate;
    }

    public void setRate(double Rate) {
        this.Rate = Rate;
    }

    public int getMonths() {
        return Months;
    }

    public void setMonths(int Months) {
        this.Months = Months;
    }

    public int getPayWay() {
        return PayWay;
    }

    public void setPayWay(int PayWay) {
        this.PayWay = PayWay;
    }

    public double getRemainFunding() {
        return RemainFunding;
    }

    public void setRemainFunding(double RemainFunding) {
        this.RemainFunding = RemainFunding;
    }
}
