package com.tencent.paipaidai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;

import com.tencent.paipaidai.R;
import com.tencent.paipaidai.dialog.SettingBidMoneyDialog;
import com.tencent.paipaidai.utils.LoanHttpUtil;
import com.tools.Log_Util;
import com.tools.MJsonUtil;
import com.tools.UsualTools;
import com.tools.ViewTools;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NewVersionLoanListDetailActivity extends Activity implements SettingBidMoneyDialog.OnBidMoneySureListener {

    @InjectView(R.id.post_btn)
    Button postBtn;
    private int listingId;
    private String batchListingInfos;
    private SettingBidMoneyDialog dialog;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            resolveResult(batchListingInfos);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_version_loan_list_detail);
        ButterKnife.inject(this);
        Bundle bundle = UsualTools.getIntentBundle(this);
        listingId = bundle.getInt("listing_id");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    batchListingInfos = LoanHttpUtil.getBatchListingInfos(listingId);
                    mHandler.sendEmptyMessage(0);
                } catch (Exception e) {
                    Log_Util.showLogCompletion(e.toString());
                }
            }
        }).start();
    }

    /**
     * 解析第i条数据的所对应的详情数据
     *
     * @param
     */
    private void resolveResult(String batchListingInfos) {
        try {
            JSONObject singleObject = new JSONObject(batchListingInfos);
            JSONArray loanInfoArray = singleObject.getJSONArray("LoanInfos");
            JSONObject loanInfoJSONObject = loanInfoArray.getJSONObject(0);

            String AuditingTime = MJsonUtil.getString(loanInfoJSONObject, "AuditingTime");
            String BorrowName = MJsonUtil.getString(loanInfoJSONObject, "BorrowName");
            String creditCode = MJsonUtil.getString(loanInfoJSONObject, "CreditCode");
            String DeadLineTimeOrRemindTimeStr = MJsonUtil.getString(loanInfoJSONObject, "DeadLineTimeOrRemindTimeStr");
            String FistBidTime = MJsonUtil.getString(loanInfoJSONObject, "FistBidTime");
            String RegisterTime = MJsonUtil.getString(loanInfoJSONObject, "RegisterTime");
            String LastBidTime = MJsonUtil.getString(loanInfoJSONObject, "LastBidTime");

            int age = MJsonUtil.getInt(loanInfoJSONObject, "Age");
            int educateValidate = MJsonUtil.getInt(loanInfoJSONObject, "EducateValidate");
            int CancelCount = MJsonUtil.getInt(loanInfoJSONObject, "CancelCount");
            int CertificateValidate = MJsonUtil.getInt(loanInfoJSONObject, "CertificateValidate");
            int CreditValidate = MJsonUtil.getInt(loanInfoJSONObject, "CreditValidate");
            int FailedCount = MJsonUtil.getInt(loanInfoJSONObject, "FailedCount");
            int gender = MJsonUtil.getInt(loanInfoJSONObject, "Gender");
            int LenderCount = MJsonUtil.getInt(loanInfoJSONObject, "LenderCount");
            int listingId = MJsonUtil.getInt(loanInfoJSONObject, "ListingId");
            int months = MJsonUtil.getInt(loanInfoJSONObject, "Months");
            int NciicIdentityCheck = MJsonUtil.getInt(loanInfoJSONObject, "NciicIdentityCheck");
            int NormalCount = MJsonUtil.getInt(loanInfoJSONObject, "NormalCount");
            int OverdueLessCount = MJsonUtil.getInt(loanInfoJSONObject, "OverdueLessCount");
            int OverdueMoreCount = MJsonUtil.getInt(loanInfoJSONObject, "OverdueMoreCount");
            int PhoneValidate = MJsonUtil.getInt(loanInfoJSONObject, "PhoneValidate");
            int SuccessCount = MJsonUtil.getInt(loanInfoJSONObject, "SuccessCount");
            int VideoValidate = MJsonUtil.getInt(loanInfoJSONObject, "VideoValidate");
            int WasteCount = MJsonUtil.getInt(loanInfoJSONObject, "WasteCount");

            double amount = MJsonUtil.getDouble(loanInfoJSONObject, "Amount");
            double AmountToReceive = MJsonUtil.getDouble(loanInfoJSONObject, "AmountToReceive");
            double OwingAmount = MJsonUtil.getDouble(loanInfoJSONObject, "OwingAmount");
            double OwingPrincipal = MJsonUtil.getDouble(loanInfoJSONObject, "OwingPrincipal");
            double RemainFunding = MJsonUtil.getDouble(loanInfoJSONObject, "RemainFunding");
            double currentRate = MJsonUtil.getDouble(loanInfoJSONObject, "CurrentRate");

            ViewTools.setStringToTextView(this, R.id.text1, "借款人：" + BorrowName);
            ViewTools.setStringToTextView(this, R.id.text2, "借款金额：" + amount);
            ViewTools.setStringToTextView(this, R.id.text3, "借款期限：" + months);
            ViewTools.setStringToTextView(this, R.id.text4, "借款利率：" + currentRate + "%");
            ViewTools.setStringToTextView(this, R.id.text5, "初始评级：" + creditCode);
            ViewTools.setStringToTextView(this, R.id.text6, "年龄：" + age);
            ViewTools.setStringToTextView(this, R.id.text7, "性别：" + gender);
            ViewTools.setStringToTextView(this, R.id.text8, "发起时间：" + RegisterTime);
            ViewTools.setStringToTextView(this, R.id.text9, "首次借款时间：" + FistBidTime);
            ViewTools.setStringToTextView(this, R.id.text10, "最近一次借款时间：" + LastBidTime);
            ViewTools.setStringToTextView(this, R.id.text11, "手机认证：" + (PhoneValidate == 0 ? "未认证" : "已认证"));
            ViewTools.setStringToTextView(this, R.id.text12, "户口认证：" + (NciicIdentityCheck == 0 ? "未认证" : "已认证"));
            ViewTools.setStringToTextView(this, R.id.text13, "视频认证：" + (VideoValidate == 0 ? "未认证" : "已认证"));
            ViewTools.setStringToTextView(this, R.id.text14, "学历认证：" + (educateValidate == 0 ? "未认证" : "已认证"));
            ViewTools.setStringToTextView(this, R.id.text15, "征信认证：" + (CreditValidate == 0 ? "未认证" : "已认证"));
            ViewTools.setStringToTextView(this, R.id.text16, "正常还款期数：" + NormalCount);
            ViewTools.setStringToTextView(this, R.id.text17, "历史逾期未还款次数：" + OverdueLessCount);
            ViewTools.setStringToTextView(this, R.id.text18, "总待还本金：" + OwingPrincipal);
            ViewTools.setStringToTextView(this, R.id.text19, "剩余投资额：" + RemainFunding);
            ViewTools.setStringToTextView(this, R.id.text20, "历史成功借款次数：" + SuccessCount);
        } catch (Exception e) {
            Log_Util.showLogCompletion(e.toString());
        }
    }

    @OnClick(R.id.post_btn)
    public void onViewClicked() {
        showDialog();
    }

    private void showDialog() {
        dialog = new SettingBidMoneyDialog(this);
        dialog.show();
        dialog.setListener(this);
    }

    @Override
    public void sendBidMoney(String money) {
        dialog.dismiss();
        final int bidMoney = Integer.parseInt(money);
        UsualTools.showShortToast(this,"投标失败，请稍后再试！");
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                String result = LoanHttpUtil.sendBid(listingId, bidMoney);
            }
        }).start();*/
    }
}
