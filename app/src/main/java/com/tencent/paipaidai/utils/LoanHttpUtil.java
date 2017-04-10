package com.tencent.paipaidai.utils;


import com.ppdai.open.core.OpenApiClient;
import com.ppdai.open.core.PropertyObject;
import com.ppdai.open.core.Result;
import com.ppdai.open.core.RsaCryptoHelper;
import com.ppdai.open.core.ValueTypeEnum;
import com.tools.Log_Util;

import java.util.ArrayList;
import java.util.List;

import static com.tencent.paipaidai.config.MConfig.accessToken;
import static com.tencent.paipaidai.config.MConfig.appid;
import static com.tencent.paipaidai.config.MConfig.clientPrivateKey;
import static com.tencent.paipaidai.config.MConfig.serverPublicKey;

/**
 * BidService.Bidding	需要	投标接口
 * BidService.BidList	需要	我的投标接口
 * BidService.BuyDebt	需要	债转购买接口
 * BidService.BatchLenderBidList	需要	（跟投）用户最近投资标的信息（批量）
 *
 * LLoanInfoService.LoanList	不需要	新版投标列表接口（默认每页2000条）
 * LLoanInfoService.BatchListingInfos	不需要	新版散标详情批量接口（请求列表不大于10）
 * LLoanInfoService.DebtList	不需要	新版债转列表接口（默认每页2000条）
 * LLoanInfoService.BatchDebtInfos	不需要	新版债转详情批量接口（请求列表不大于20）
 * LLoanInfoService.BatchListingBidInfos	不需要	新版列表投标详情批量接口（请求列表大小不大于5）
 * LLoanInfoService.BatchListingStatusInfos	不需要	新版列表状态查询批量接口（请求列表大小不大于20条）
 */

public class LoanHttpUtil {


    /**
     * 获取新版投标列表接口（默认每页2000条）
     * @param page 页码
     * @param time 如果有则查询该时间之后的列表，精确到毫秒
     */
    public static String getLoanList(int page,String time){
        Result result = null;
        try {
            //初始化操作
            OpenApiClient.Init(appid, RsaCryptoHelper.PKCSType.PKCS8, serverPublicKey, clientPrivateKey);
            //请求url
            String url = "http://gw.open.ppdai.com/invest/LLoanInfoService/LoanList";
            result = OpenApiClient.send(url,
                    new PropertyObject("PageIndex", page, ValueTypeEnum.Int32),
                    new PropertyObject("StartDateTime", time, ValueTypeEnum.DateTime));
        } catch (Exception e) {
            e.printStackTrace();
//            Log.e("LOGUTIL",e.toString());
            Log_Util.showLogCompletion(e.toString());
        }
        Log_Util.showLogCompletion(result.isSucess() ? result.getContext() : result.getErrorMessage());
        return result.isSucess() ? result.getContext() : result.getErrorMessage();
//        Log.e("LOGUTIL",String.format("返回结果:%s", result.isSucess() ? result.getContext() : result.getErrorMessage()));
    }

    /**
     * 新版散标详情批量接口（请求列表不大于10）
     * @param ListingIds
     * @return
     * @throws Exception
     */
    public static String getBatchListingInfos(int ListingIds) throws Exception {
        //请求url
        String url = "http://gw.open.ppdai.com/invest/LLoanInfoService/BatchListingInfos";
        List<Integer> listIds = new ArrayList<Integer>(1);
        listIds.add(ListingIds);
        Result result = OpenApiClient.send(url,
                new PropertyObject("ListingIds", listIds, ValueTypeEnum.Other));
        Log_Util.showLogCompletion(result.isSucess() ? result.getContext() : result.getErrorMessage());
        return result.isSucess() ? result.getContext() : result.getErrorMessage();
    }


    public static String sendBid(int ListingIds,double money){
        Result result=null;
        try {
//            //初始化操作
            OpenApiClient.Init(appid, RsaCryptoHelper.PKCSType.PKCS8, serverPublicKey, clientPrivateKey);
            //请求url
            String url = "http://gw.open.ppdai.com/invest/BidService/Bidding";
             result = OpenApiClient.send(url, accessToken,
                    new PropertyObject("ListingId", ListingIds, ValueTypeEnum.Int32),
                    new PropertyObject("Amount",money, ValueTypeEnum.Double));
            Log_Util.showLogCompletion(result.isSucess() ? result.getContext() : result.getErrorMessage());
        } catch (Exception e) {
            Log_Util.showLogCompletion(e.toString());
        }
        return result.isSucess() ? result.getContext() : result.getErrorMessage();
    }
}
