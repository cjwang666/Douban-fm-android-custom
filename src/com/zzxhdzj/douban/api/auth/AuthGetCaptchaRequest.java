package com.zzxhdzj.douban.api.auth;

import java.util.List;
import java.util.Map;

import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.api.AuthApiRequest;
import com.zzxhdzj.http.TextApiResponse;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/24/13
 * Time: 11:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthGetCaptchaRequest extends AuthApiRequest{
    protected AuthGetCaptchaRequest() {
        allowRedirect = true;
        super.setBaseUrl(Constants.CAPTCHA_ID);
    }


    @Override
    public TextApiResponse createResponse(int statusCode, Map<String, List<String>> headers) {
        return new TextApiResponse(statusCode,headers);
    }
}
