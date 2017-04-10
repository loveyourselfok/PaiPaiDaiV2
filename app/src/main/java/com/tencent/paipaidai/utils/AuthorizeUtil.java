package com.tencent.paipaidai.utils;

import com.ppdai.open.core.AuthInfo;
import com.ppdai.open.core.OpenApiClient;
import com.ppdai.open.core.RsaCryptoHelper;

import static com.tencent.paipaidai.pub.MConfig.appId;

/**
 *
 */

public class AuthorizeUtil {

    //
    private static final String serverPublicKey="your_public_key";
    //
    private static final  String clientPrivateKey = "your_private_key";
    private static final String appid="your_appid";
    //AccessToken
    private static final String  accessToken = "your_accesstoken";

    public static String getAccessToken(String code) throws Exception {
        OpenApiClient.Init(appId, RsaCryptoHelper.PKCSType.PKCS8, serverPublicKey, clientPrivateKey);
        AuthInfo authInfo = OpenApiClient.authorize(code);
        String accessToke = authInfo.getAccessToken();
        return accessToke;
    }
}
