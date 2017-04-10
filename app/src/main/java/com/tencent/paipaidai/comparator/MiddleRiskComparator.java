package com.tencent.paipaidai.comparator;

import com.tencent.paipaidai.bean.NewVersionLoanListDetailBean;
import com.tools.Log_Util;

import java.util.Comparator;

/**
 * Created by HWC on 2017/4/9.
 */

public class MiddleRiskComparator implements Comparator<NewVersionLoanListDetailBean> {
    @Override
    public int compare(NewVersionLoanListDetailBean o1, NewVersionLoanListDetailBean o2) {
        int resultCode=0;

        NewVersionLoanListDetailBean.LoanInfosBean loanInfosBean = o1.getLoanInfos().get(0);
        NewVersionLoanListDetailBean.LoanInfosBean loanInfosBean2 = o2.getLoanInfos().get(0);

        //学历认证
        resultCode = loanInfosBean.getEducateValidate() - loanInfosBean2.getEducateValidate();
        if (resultCode!=0){
            Log_Util.showLogCompletion("resultCode2:"+resultCode);
            return resultCode;
        }
        //借款期限
        int months1 = loanInfosBean.getMonths();
        int months2 = loanInfosBean2.getMonths();
        int deltMonth=months1-months2;
        if (deltMonth!=0){
            resultCode=deltMonth>0?-1:1;
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
        return resultCode;
    }
}
