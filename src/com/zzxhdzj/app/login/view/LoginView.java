package com.zzxhdzj.app.login.view;

import com.zzxhdzj.douban.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/1/14
 * To change this template use File | Settings | File Templates.
 */
public class LoginView extends LinearLayout {

    
    EditText mLoginUsernameEt;    
    EditText mLoginPasswordEt;    
    EditText mLoginCaptchaEt;    
    ImageView mCaptchaImageView;    
    TextView mLoginSkip;
    Button mLoginSubmitBtn;
    ProgressBar mLoading;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(getContext(), R.layout.login_view, this);        
        mLoginUsernameEt = (android.widget.EditText) view.findViewById(R.id.login_username_et);        
        mLoginPasswordEt = (android.widget.EditText) view.findViewById(R.id.login_password_et);        
        mLoginCaptchaEt = (android.widget.EditText) view.findViewById(R.id.login_captcha_et);        
        mCaptchaImageView = (android.widget.ImageView) view.findViewById(R.id.captcha_image_view);        
        mLoginSkip = (android.widget.TextView) view.findViewById(R.id.login_skip);        
        mLoginSubmitBtn = (android.widget.Button) view.findViewById(R.id.login_submit_btn);        
        mLoading = (android.widget.ProgressBar) view.findViewById(R.id.loading);
    }


    public EditText getLoginUsernameEt() {
        return mLoginUsernameEt;
    }

    public EditText getLoginPasswordEt() {
        return mLoginPasswordEt;
    }

    public EditText getLoginCaptchaEt() {
        return mLoginCaptchaEt;
    }

    public ImageView getCaptchaImageView() {
        return mCaptchaImageView;
    }

    public ProgressBar getLoading() {
        return mLoading;
    }

    public TextView getLoginSkip() {
        return mLoginSkip;
    }

    public Button getLoginSubmitBtn() {
        return mLoginSubmitBtn;
    }
}
