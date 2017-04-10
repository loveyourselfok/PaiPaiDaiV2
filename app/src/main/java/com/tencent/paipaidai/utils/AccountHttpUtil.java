package com.tencent.paipaidai.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ppdai.open.core.OpenApiClient;
import com.ppdai.open.core.PropertyObject;
import com.ppdai.open.core.Result;
import com.ppdai.open.core.RsaCryptoHelper;
import com.ppdai.open.core.ValueTypeEnum;
import com.tencent.paipaidai.config.MConfig;
import com.tencent.paipaidai.interfaces.OnAutoLoginListener;
import com.tencent.paipaidai.interfaces.OnRegisterListener;
import com.tencent.paipaidai.interfaces.OnSendDynamicCodeListener;
import com.tools.Log_Util;

import java.util.Date;

import static com.tencent.paipaidai.config.MConfig.appid;
import static com.tencent.paipaidai.pub.MConfig.clientPrivateKey;
import static com.tencent.paipaidai.pub.MConfig.serverPublicKey;

/**
 * Created by HWC on 2017/4/8.
 */

public class AccountHttpUtil {
    public static void register(String mobile, String email, int Role, OnRegisterListener onRegisterListener){
        try {
            //初始化操作
            OpenApiClient.Init(appid, RsaCryptoHelper.PKCSType.PKCS8, MConfig.serverPublicKey, MConfig.clientPrivateKey);
            //请求url
            String url = "http://gw.open.ppdai.com/auth/registerservice/register";
            Result result = OpenApiClient.send(url
                    , new PropertyObject("Mobile", mobile, ValueTypeEnum.String)
                    , new PropertyObject("Email", email, ValueTypeEnum.String)
                    , new PropertyObject("Role", Role, ValueTypeEnum.Int32));

            String resultInfo = result.isSucess() ? result.getContext() : result.getErrorMessage();
            Log_Util.showLogCompletion(resultInfo);
            if (onRegisterListener!=null){
                onRegisterListener.onSuccess(resultInfo);
            }
        } catch (Exception e) {
            Log_Util.showLogCompletion(e.toString());
        }
    }

    /**
     * 自动登录
     * @param context
     * @param listener
     */
    public static void autoLogin(Context context, OnAutoLoginListener listener){
        try {
            //初始化操作
            OpenApiClient.Init(appid, RsaCryptoHelper.PKCSType.PKCS8, serverPublicKey, clientPrivateKey);
            SharedPreferences sp = context.getSharedPreferences(MConfig.USER_INFO, Context.MODE_PRIVATE);
            String accessToken = sp.getString(MConfig.ACCESS_TOKEN, "");
            //请求url
            String url = "http://gw.open.ppdai.com/auth/LoginService/AutoLogin";
            Result result = OpenApiClient.send(url, accessToken
                    , new PropertyObject("Timestamp", new Date(), ValueTypeEnum.DateTime));
            String resultInfo = result.isSucess() ? result.getContext() : result.getErrorMessage();
            Log_Util.showLogCompletion(resultInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送动态登录密码
     * @param mobile
     * @param machineId
     */
    public static void sendDynamicPassword(String mobile, String machineId, OnSendDynamicCodeListener listener){
        Log_Util.showLogCompletion(mobile+"+++"+machineId);
        //请求url
        String url = "http://gw.open.ppdai.com/auth/authservice/sendsmsauthcode";
        try {
            //初始化（执行一次即可）
            OpenApiClient.Init(appid, RsaCryptoHelper.PKCSType.PKCS8, serverPublicKey, clientPrivateKey);
            Result result = OpenApiClient.send(url
                    , new PropertyObject("Mobile", mobile, ValueTypeEnum.String)
                    , new PropertyObject("DeviceFP", machineId, ValueTypeEnum.String));
            String resultInfo = result.isSucess() ? result.getContext() : result.getErrorMessage();
            if (listener!=null){
                listener.onSuccess(resultInfo);
            }
            Log_Util.showLogCompletion("resultInfo:"+resultInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
