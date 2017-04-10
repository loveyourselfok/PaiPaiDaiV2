package com.tencent.paipaidai.utils;


import com.ppdai.open.core.OpenApiClient;
import com.ppdai.open.core.PropertyObject;
import com.ppdai.open.core.Result;
import com.ppdai.open.core.RsaCryptoHelper;
import com.ppdai.open.core.ValueTypeEnum;
import com.tools.Log_Util;

import java.util.ArrayList;
import java.util.List;

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
    // 服务端公钥
    private static final String serverPublicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCeoW5Ch/JxZm5gklBFUnzn8G8sPHjwrLHOTydvIIPKPUh/0AkRTWPSrzQ4QnyVKkz+pxmRB05hktc1SiiScoY3gecnmbpyHyzoZZG9tnlysMwNLH29f4FhTTzVwpUzlHO1qLtDVztgfZZCLmmijUInPDULARm42RPLbxHKDxLQiQIDAQAB";
    // 客户端私钥
    private static final  String clientPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAN2lAqvY+GN7SpXKtK0z/lqe1xxNeDTv23Ukpf3RlbCFEKFrx/iCBvOBs/hiX3QEQzEvt760g32cQ+9M9uRSKPwdVnJyk1Lp+fAKqIci0Ohgsg9Zvvr8AQI6MHOE1Xq5bEYSkuV9yaH+33u3CEoK0u2T8Z9173gj0tEcuuSurCXLAgMBAAECgYATzE+GxiS5ziOFacvlxMUtlw6j6o+YOuyhdBqXp4b1c41evd/o5MR2L9nBkBfypwbRd++zyzALok+3KmrLTkN2xyFva8+2QZ3j0NTFWvDNfQynE8Jc484uBlPxgBuhnvKj1VpNVDsX9FPHph3O1ydje55W35UNkmpZCe29N7CJcQJBAPChHgW7Nb5hczbYy/M5UJF/n21VhKs02tJYFPAoP0ONoxS2jYt0yqsJHIZjskT14ARJCjarcupyUXEyLzYA/9cCQQDrzXHuwzUqA9PoVtnlxIeMSGJvt1yumNJIiQyghZ/A5nlcOc9lUyvXN14lHywQzts6CpscSMfDC4KLUGvEKJstAkEAzLpX/8pTJIJm80Aq7epSuSgbLXu4H64dix8z2Ux03vXPzZyv+vnIQRLEeTazguaqzRqTnQXsBLp0vllTP6BECwJAE1fHAM8CjW/C8kwtk6uLcvZ9HKzt7WSb21dischEUo6VZftYB/fKoNzp3CGye658TUaRcsCFonpPOx5duLmcAQJABX4JA3miLSdkaWCRPhDIT2vjYM52BcegtR47kiT/7YHUDICwEXOqQzLZ82oHulKr74VDJzZiU+2XHrlK1j9d+Q==";
    // 应用ID
    private static final String appid="2b170885d23b48af917eeeb3ea8c03e9";
    //AccessToken
    private static final String  accessToken = "8b59725b-ae17-49a0-a113-afb57178f05f";

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
