package com.tencent.paipaidai.comparator;

import com.tencent.paipaidai.bean.NewVersionLoanListDetailBean;
import com.tools.Log_Util;

import java.util.Comparator;

/**
 * Created by HWC on 2017/4/9.
 */

public class HighEarningsComparator implements Comparator<NewVersionLoanListDetailBean> {
    @Override
    public int compare(NewVersionLoanListDetailBean o1, NewVersionLoanListDetailBean o2) {
        int resultCode=0;

        NewVersionLoanListDetailBean.LoanInfosBean loanInfosBean = o1.getLoanInfos().get(0);
        NewVersionLoanListDetailBean.LoanInfosBean loanInfosBean2 = o2.getLoanInfos().get(0);

        //利率
        double currentRate = loanInfosBean.getCurrentRate();
        double currentRate2 = loanInfosBean2.getCurrentRate();
        double deltRate = currentRate2 - currentRate;
        resultCode= deltRate >0?1:(deltRate<0?-1:0);
        if (resultCode!=0){
            return resultCode;
        }
        //评级
        String creditCode1 = loanInfosBean.getCreditCode();
        String creditCode2 = loanInfosBean2.getCreditCode();
        Log_Util.showLogCompletion("creditCode1:"+creditCode1+"--"+"creditCode2:"+creditCode2);
        if (creditCode1.startsWith("A")&&creditCode2.startsWith("A")){
            resultCode=-creditCode1.compareTo(creditCode2);
        }else if(creditCode1.startsWith("A")&&!creditCode2.startsWith("A")){
            resultCode=1;
        }else if(!creditCode1.startsWith("A")&&creditCode2.startsWith("A")){
            resultCode=-1;
        }
        if (resultCode!=0){
            Log_Util.showLogCompletion("resultCode1:"+resultCode);
            return resultCode;
        }

        //首标
        String fistBidTime = loanInfosBean.getFistBidTime();
        String fistBidTime2 = loanInfosBean2.getFistBidTime();
        Log_Util.showLogCompletion("fistBidTime:"+fistBidTime+"--"+"fistBidTime2:"+fistBidTime2);
        if ((fistBidTime==null||"".equals(fistBidTime))&&(fistBidTime2==null||"".equals(fistBidTime2))){
            resultCode=0;
        }else if (fistBidTime!=null&&fistBidTime2==null){

            resultCode=1;
        }else if (fistBidTime2!=null&&fistBidTime==null){
            resultCode=-1;
        }else {
            resultCode=0;
        }
        if (resultCode!=0){
            Log_Util.showLogCompletion("resultCode3:"+resultCode);
            return resultCode;
        }
        //学历认证
        resultCode = loanInfosBean.getEducateValidate() - loanInfosBean2.getEducateValidate();
        if (resultCode!=0){
            Log_Util.showLogCompletion("resultCode2:"+resultCode);
            return resultCode;
        }
        //非男性
        int gender = loanInfosBean.getGender();
        int gender2 = loanInfosBean2.getGender();
        if (gender!=gender2){
            resultCode=gender-gender2;
        }
        //借款金额高
        double amount = loanInfosBean.getAmount();
        double amount2 = loanInfosBean2.getAmount();
        double deltAmount = amount - amount2;
        if (deltAmount!=0.0){
            resultCode=deltAmount>0?1:-1;
        }
        return resultCode;
    }
}
