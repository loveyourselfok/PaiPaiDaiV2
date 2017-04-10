package com.tencent.paipaidai.comparator;

import com.tencent.paipaidai.bean.NewVersionLoanListDetailBean;
import com.tools.Log_Util;

import java.util.Comparator;

/**
 * Created by HWC on 2017/4/9.
 */

public class SoonBackComparator implements Comparator<NewVersionLoanListDetailBean> {
    @Override
    public int compare(NewVersionLoanListDetailBean o1, NewVersionLoanListDetailBean o2) {
        int resultCode=0;

        NewVersionLoanListDetailBean.LoanInfosBean loanInfosBean = o1.getLoanInfos().get(0);
        NewVersionLoanListDetailBean.LoanInfosBean loanInfosBean2 = o2.getLoanInfos().get(0);

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
        //年龄
        int age = loanInfosBean.getAge();
        int age2 = loanInfosBean2.getAge();
        int deltAge=age-age2;
        if (deltAge!=0){
            resultCode=deltAge>0?1:-1;
            return resultCode;
        }
        return resultCode;
    }
}
