package com.tencent.paipaidai.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;

import com.tencent.paipaidai.R;
import com.tencent.paipaidai.config.MConfig;
import com.tencent.paipaidai.interfaces.OnRegisterListener;
import com.tencent.paipaidai.utils.AccountHttpUtil;
import com.tools.MJsonUtil;
import com.tools.UsualTools;

import org.json.JSONException;
import org.json.JSONObject;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, OnRegisterListener {
    FloatingActionButton fab;
    CardView cvAdd;
    private EditText etUserName;
    private EditText etPassword;
    private EditText etRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        initViews();

    }


    protected void initViews() {
        fab=(FloatingActionButton) findViewById(R.id.fab);
        cvAdd=(CardView) findViewById(R.id.cv_add);
        findViewById(R.id.bt_go).setOnClickListener(this);
        etUserName = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etRole = (EditText) findViewById(R.id.et_role);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(300);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(300);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_go:

                register();
                break;
        }
    }

    private void register() {
        final String mobile = etUserName.getText().toString().trim();
        boolean phoneNumber = UsualTools.isPhoneNumber(mobile);
        if (!phoneNumber){
            UsualTools.showShortToast(this,"请输入正确的手机号！");
            return;
        }
        final String email = etPassword.getText().toString().trim();
        final String role = etRole.getText().toString().trim();
        if (!("4".equals(role)||"8".equals(role)||"12".equals(role))){
            UsualTools.showShortToast(this,"请输入正确的编号！");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                AccountHttpUtil.register(mobile,email,Integer.parseInt(role),RegisterActivity.this);
            }
        }).start();
    }

    @Override
    public void onSuccess(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            final String returnMessage = MJsonUtil.getString(jsonObject, "ReturnMessage");
            if (returnMessage ==null||returnMessage.equals("")||"null".equals(returnMessage)){
                String accessToken = MJsonUtil.getString(jsonObject, "AccessToken");
                String refreshToken = MJsonUtil.getString(jsonObject, "RefreshToken");
                String userName = MJsonUtil.getString(jsonObject, "UserName");
                String OpenID = MJsonUtil.getString(jsonObject, "OpenID");
                String ExpiresIn = MJsonUtil.getString(jsonObject, "ExpiresIn");
                SharedPreferences sp = getSharedPreferences(MConfig.USER_INFO,MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(MConfig.ACCESS_TOKEN,accessToken);
                editor.putString(MConfig.REFRESH_TOKEN,refreshToken);
                editor.putString(MConfig.USER_NAME,userName);
                editor.putString(MConfig.OPEN_ID,OpenID);
                editor.putString(MConfig.EXPIRES_IN,ExpiresIn);
                editor.commit();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UsualTools.showShortToast(RegisterActivity.this,"注册成功");
                        onBackPressed();
                    }
                });

            }else{
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UsualTools.showShortToast(RegisterActivity.this,returnMessage);
                    }
                });*/
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onFailure(String result) {

    }
}
