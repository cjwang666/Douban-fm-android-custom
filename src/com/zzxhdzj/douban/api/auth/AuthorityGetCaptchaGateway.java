package com.zzxhdzj.douban.api.auth;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.CommonTextApiResponseCallback;
import com.zzxhdzj.douban.api.base.ApiRespErrorCode;
import com.zzxhdzj.douban.api.base.BaseApiGateway;
import com.zzxhdzj.http.ApiGateway;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.TextApiResponse;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/26/13
 * Time: 12:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class AuthorityGetCaptchaGateway extends BaseApiGateway {

    public static final Gson GSON = new Gson();

    public AuthorityGetCaptchaGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway,null);
    }

    public void newCaptchaId(Callback callback) {
        apiGateway.makeRequest(new AuthGetCaptchaRequest(), new AuthorityGetCaptchaCallback(callback, this, douban));
    }


    private class AuthorityGetCaptchaCallback extends CommonTextApiResponseCallback<Douban> {

        public AuthorityGetCaptchaCallback(Callback bizCallback, BaseApiGateway gateway, Douban apiInstance) {
            super(bizCallback, gateway, apiInstance);
        }

        @Override
        public void _extractRespData(TextApiResponse response) {
            Object obj = GSON.fromJson(response.getResp(), Object.class);
            douban.captchaId = obj.toString();
        }

        @Override
        public boolean _handleRespData(TextApiResponse response){
            if (TextUtils.isEmpty(douban.captchaId)) {
                douban.mApiRespErrorCode = ApiRespErrorCode.createBizError("-1", "验证码ID获取失败");
                return false;
            }else {
                douban.captchaImageUrl = Constants.CAPTCHA_URL + "&id=" + douban.captchaId;
                return true;
            }
        }


    }

}
