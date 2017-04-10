package com.tencent.paipaidai.bean;

import java.util.List;

/**
 * Created by HWC on 2017/4/8.
 */

public class NewVersionLoanListDetailBean{

    private int mode=0;

    public void setMode(int mode){
        this.mode=mode;
    }
    /**
     * LoanInfos : [{"Age":39,"Amount":10000,"AmountToReceive":0,"AuditingTime":"2000-01-01T00:00:00","BorrowName":"pdu6666870788","CancelCount":0,"CertificateValidate":0,"CreditCode":"AA","CreditValidate":0,"CurrentRate":10,"DeadLineTimeOrRemindTimeStr":"19天19时55分","EducateValidate":0,"FailedCount":0,"FistBidTime":"2017-04-08T18:51:04.857","Gender":2,"LastBidTime":"2017-04-08T21:47:59.02","LenderCount":51,"ListingId":41574666,"Months":3,"NciicIdentityCheck":0,"NormalCount":0,"OverdueLessCount":0,"OverdueMoreCount":0,"OwingAmount":0,"OwingPrincipal":0,"PhoneValidate":1,"RegisterTime":"2017-04-07T12:07:26","RemainFunding":4550,"SuccessCount":0,"VideoValidate":0,"WasteCount":0}]
     * Result : 1
     * ResultMessage :
     */

    private int Result;
    private String ResultMessage;
    private List<LoanInfosBean> LoanInfos;

    public int getResult() {
        return Result;
    }

    public void setResult(int Result) {
        this.Result = Result;
    }

    public String getResultMessage() {
        return ResultMessage;
    }

    public void setResultMessage(String ResultMessage) {
        this.ResultMessage = ResultMessage;
    }

    public List<LoanInfosBean> getLoanInfos() {
        return LoanInfos;
    }

    public void setLoanInfos(List<LoanInfosBean> LoanInfos) {
        this.LoanInfos = LoanInfos;
    }


    public static class LoanInfosBean {
        /**
         * Age : 39
         * Amount : 10000.0
         * AmountToReceive : 0.0
         * AuditingTime : 2000-01-01T00:00:00
         * BorrowName : pdu6666870788
         * CancelCount : 0
         * CertificateValidate : 0
         * CreditCode : AA
         * CreditValidate : 0
         * CurrentRate : 10.0
         * DeadLineTimeOrRemindTimeStr : 19天19时55分
         * EducateValidate : 0
         * FailedCount : 0
         * FistBidTime : 2017-04-08T18:51:04.857
         * Gender : 2
         * LastBidTime : 2017-04-08T21:47:59.02
         * LenderCount : 51
         * ListingId : 41574666
         * Months : 3
         * NciicIdentityCheck : 0
         * NormalCount : 0
         * OverdueLessCount : 0
         * OverdueMoreCount : 0
         * OwingAmount : 0.0
         * OwingPrincipal : 0.0
         * PhoneValidate : 1
         * RegisterTime : 2017-04-07T12:07:26
         * RemainFunding : 4550.0
         * SuccessCount : 0
         * VideoValidate : 0
         * WasteCount : 0
         */

        private int Age;
        private double Amount;
        private double AmountToReceive;
        private String AuditingTime;
        private String BorrowName;
        private int CancelCount;
        private int CertificateValidate;
        private String CreditCode;
        private int CreditValidate;
        private double CurrentRate;
        private String DeadLineTimeOrRemindTimeStr;
        private int EducateValidate;
        private int FailedCount;
        private String FistBidTime;
        private int Gender;
        private String LastBidTime;
        private int LenderCount;
        private int ListingId;
        private int Months;
        private int NciicIdentityCheck;
        private int NormalCount;
        private int OverdueLessCount;
        private int OverdueMoreCount;
        private double OwingAmount;
        private double OwingPrincipal;
        private int PhoneValidate;
        private String RegisterTime;
        private double RemainFunding;
        private int SuccessCount;
        private int VideoValidate;
        private int WasteCount;

        public int getAge() {
            return Age;
        }

        public void setAge(int Age) {
            this.Age = Age;
        }

        public double getAmount() {
            return Amount;
        }

        public void setAmount(double Amount) {
            this.Amount = Amount;
        }

        public double getAmountToReceive() {
            return AmountToReceive;
        }

        public void setAmountToReceive(double AmountToReceive) {
            this.AmountToReceive = AmountToReceive;
        }

        public String getAuditingTime() {
            return AuditingTime;
        }

        public void setAuditingTime(String AuditingTime) {
            this.AuditingTime = AuditingTime;
        }

        public String getBorrowName() {
            return BorrowName;
        }

        public void setBorrowName(String BorrowName) {
            this.BorrowName = BorrowName;
        }

        public int getCancelCount() {
            return CancelCount;
        }

        public void setCancelCount(int CancelCount) {
            this.CancelCount = CancelCount;
        }

        public int getCertificateValidate() {
            return CertificateValidate;
        }

        public void setCertificateValidate(int CertificateValidate) {
            this.CertificateValidate = CertificateValidate;
        }

        public String getCreditCode() {
            return CreditCode;
        }

        public void setCreditCode(String CreditCode) {
            this.CreditCode = CreditCode;
        }

        public int getCreditValidate() {
            return CreditValidate;
        }

        public void setCreditValidate(int CreditValidate) {
            this.CreditValidate = CreditValidate;
        }

        public double getCurrentRate() {
            return CurrentRate;
        }

        public void setCurrentRate(double CurrentRate) {
            this.CurrentRate = CurrentRate;
        }

        public String getDeadLineTimeOrRemindTimeStr() {
            return DeadLineTimeOrRemindTimeStr;
        }

        public void setDeadLineTimeOrRemindTimeStr(String DeadLineTimeOrRemindTimeStr) {
            this.DeadLineTimeOrRemindTimeStr = DeadLineTimeOrRemindTimeStr;
        }

        public int getEducateValidate() {
            return EducateValidate;
        }

        public void setEducateValidate(int EducateValidate) {
            this.EducateValidate = EducateValidate;
        }

        public int getFailedCount() {
            return FailedCount;
        }

        public void setFailedCount(int FailedCount) {
            this.FailedCount = FailedCount;
        }

        public String getFistBidTime() {
            return FistBidTime;
        }

        public void setFistBidTime(String FistBidTime) {
            this.FistBidTime = FistBidTime;
        }

        public int getGender() {
            return Gender;
        }

        public void setGender(int Gender) {
            this.Gender = Gender;
        }

        public String getLastBidTime() {
            return LastBidTime;
        }

        public void setLastBidTime(String LastBidTime) {
            this.LastBidTime = LastBidTime;
        }

        public int getLenderCount() {
            return LenderCount;
        }

        public void setLenderCount(int LenderCount) {
            this.LenderCount = LenderCount;
        }

        public int getListingId() {
            return ListingId;
        }

        public void setListingId(int ListingId) {
            this.ListingId = ListingId;
        }

        public int getMonths() {
            return Months;
        }

        public void setMonths(int Months) {
            this.Months = Months;
        }

        public int getNciicIdentityCheck() {
            return NciicIdentityCheck;
        }

        public void setNciicIdentityCheck(int NciicIdentityCheck) {
            this.NciicIdentityCheck = NciicIdentityCheck;
        }

        public int getNormalCount() {
            return NormalCount;
        }

        public void setNormalCount(int NormalCount) {
            this.NormalCount = NormalCount;
        }

        public int getOverdueLessCount() {
            return OverdueLessCount;
        }

        public void setOverdueLessCount(int OverdueLessCount) {
            this.OverdueLessCount = OverdueLessCount;
        }

        public int getOverdueMoreCount() {
            return OverdueMoreCount;
        }

        public void setOverdueMoreCount(int OverdueMoreCount) {
            this.OverdueMoreCount = OverdueMoreCount;
        }

        public double getOwingAmount() {
            return OwingAmount;
        }

        public void setOwingAmount(double OwingAmount) {
            this.OwingAmount = OwingAmount;
        }

        public double getOwingPrincipal() {
            return OwingPrincipal;
        }

        public void setOwingPrincipal(double OwingPrincipal) {
            this.OwingPrincipal = OwingPrincipal;
        }

        public int getPhoneValidate() {
            return PhoneValidate;
        }

        public void setPhoneValidate(int PhoneValidate) {
            this.PhoneValidate = PhoneValidate;
        }

        public String getRegisterTime() {
            return RegisterTime;
        }

        public void setRegisterTime(String RegisterTime) {
            this.RegisterTime = RegisterTime;
        }

        public double getRemainFunding() {
            return RemainFunding;
        }

        public void setRemainFunding(double RemainFunding) {
            this.RemainFunding = RemainFunding;
        }

        public int getSuccessCount() {
            return SuccessCount;
        }

        public void setSuccessCount(int SuccessCount) {
            this.SuccessCount = SuccessCount;
        }

        public int getVideoValidate() {
            return VideoValidate;
        }

        public void setVideoValidate(int VideoValidate) {
            this.VideoValidate = VideoValidate;
        }

        public int getWasteCount() {
            return WasteCount;
        }

        public void setWasteCount(int WasteCount) {
            this.WasteCount = WasteCount;
        }
    }
}
