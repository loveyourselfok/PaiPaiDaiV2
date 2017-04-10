package com.tencent.paipaidai.comparator;

import com.tencent.paipaidai.bean.NewVersionLoanListDetailBean;
import com.tools.Log_Util;

import java.util.Comparator;

/**
 * Created by HWC on 2017/4/9.
 */

public class LowRiskComparator implements Comparator<NewVersionLoanListDetailBean> {
    @Override
    public int compare(NewVersionLoanListDetailBean o1, NewVersionLoanListDetailBean o2) {
        int resultCode=0;
        String creditCode1 = o1.getLoanInfos().get(0).getCreditCode();
        String creditCode2 = o2.getLoanInfos().get(0).getCreditCode();
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

        resultCode = o1.getLoanInfos().get(0).getEducateValidate() - o2.getLoanInfos().get(0).getEducateValidate();
        if (resultCode!=0){
            Log_Util.showLogCompletion("resultCode2:"+resultCode);
            return resultCode;
        }
        String fistBidTime = o1.getLoanInfos().get(0).getFistBidTime();
        String fistBidTime2 = o2.getLoanInfos().get(0).getFistBidTime();
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
        return resultCode;
    }
}
