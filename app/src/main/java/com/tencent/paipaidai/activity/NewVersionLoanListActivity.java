package com.tencent.paipaidai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.tencent.paipaidai.R;
import com.tencent.paipaidai.adapter.NewVersionLoanInfoAdapter;
import com.tencent.paipaidai.bean.NewVersionLoanInfoBean;
import com.tencent.paipaidai.bean.NewVersionLoanListDetailBean;
import com.tencent.paipaidai.comparator.HighEarningsComparator;
import com.tencent.paipaidai.comparator.LowRiskComparator;
import com.tencent.paipaidai.comparator.MiddleRiskComparator;
import com.tencent.paipaidai.comparator.SoonBackComparator;
import com.tencent.paipaidai.utils.LoanHttpUtil;
import com.tools.Log_Util;
import com.tools.MJsonUtil;
import com.tools.UsualTools;
import com.view.XListView.XListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class NewVersionLoanListActivity extends Activity implements  AdapterView.OnItemClickListener {
    @InjectView(R.id.tv_low_risk)
    TextView tvLowRisk;
    @InjectView(R.id.tv_high_earnings)
    TextView tvHighEarnings;
    @InjectView(R.id.tv_middle_risk)
    TextView tvMiddleRisk;
    @InjectView(R.id.tv_soon_back)
    TextView tvSoonBack;
    @InjectView(R.id.list_view)
    XListView listView;
    private String loanStr;
    private List<NewVersionLoanInfoBean> list = new ArrayList<>();
    private List<NewVersionLoanInfoBean> tempList = new ArrayList<>();
    ArrayList<NewVersionLoanListDetailBean> detailBeanList=new ArrayList<>();
    private NewVersionLoanInfoAdapter adapter;
//    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_version_loan_list);
        ButterKnife.inject(this);
        adapter = new NewVersionLoanInfoAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        initLoanList(adapter);//初始化标的列表
//        initChar();
//        loadingDialog = new LoadingDialog(this);
//        loadingDialog.initProgressDialog();
//        loadingDialog.setMessage("正在加载数据.....");
//        loadingDialog.show(2);
    }

    private void initLoanList(final NewVersionLoanInfoAdapter adapter) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                loanStr = LoanHttpUtil.getLoanList(1, "2017-04-10 11:00:00.000");
                try {
                    JSONObject resultObject = new JSONObject(loanStr);
                    JSONArray loanInfos = resultObject.getJSONArray("LoanInfos");
                    for (int i = 0; i < 50; i++) {
                        JSONObject singleObject=null;
                        try{
                             singleObject = loanInfos.getJSONObject(i);
                        }catch (Exception e){
                            continue;
                        }
                        int listingId = MJsonUtil.getInt(singleObject, "ListingId");
                        String title = MJsonUtil.getString(singleObject, "Title");
                        String CreditCode = MJsonUtil.getString(singleObject, "CreditCode");
                        double Amount = MJsonUtil.getDouble(singleObject, "Amount");
                        double Rate = MJsonUtil.getDouble(singleObject, "Rate");
                        double RemainFunding = MJsonUtil.getDouble(singleObject, "RemainFunding");
                        int payWay = MJsonUtil.getInt(singleObject, "PayWay");

                        NewVersionLoanInfoBean bean = new NewVersionLoanInfoBean();

                        bean.setTitle(title);
                        bean.setListingId(listingId);
                        bean.setCreditCode(CreditCode);
                        bean.setAmount(Amount);
                        bean.setRate(Rate);
                        bean.setPayWay(payWay);
                        bean.setRemainFunding(RemainFunding);

                        list.add(bean);


                        String batchListingInfos = LoanHttpUtil.getBatchListingInfos(listingId);
                        resolveResult(batchListingInfos);

                    }
//                    loadingDialog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
//                    UsualTools.showDataErrorToast(NewVersionLoanListActivity.this);
                }
            }
        }).start();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        NewVersionLoanInfoBean newVersionLoanInfoBean = list.get(position);
        bundle.putInt("listing_id", newVersionLoanInfoBean.getListingId());
        UsualTools.jumpActivity(this, NewVersionLoanListDetailActivity.class, bundle);
    }

    @OnClick({R.id.tv_low_risk, R.id.tv_high_earnings, R.id.tv_middle_risk, R.id.tv_soon_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_low_risk://低风险
                getLowRiskLoanList();
                break;
            case R.id.tv_high_earnings:
                getHighEarningsLoanList();
                break;
            case R.id.tv_middle_risk:
                getMiddleRiskLoanList();
                break;
            case R.id.tv_soon_back:
                getSoonBackLoanList();
                break;
        }
    }

    /**
     * 解析第i条数据的所对应的详情数据
     * @param
     */
    private void resolveResult(String batchListingInfos) {

        try {
//            String batchListingInfos = LoanHttpUtil.getBatchListingInfos(infoBean.getListingId());//获取到详情数据

            JSONObject singleObject = new JSONObject(batchListingInfos);
            JSONArray loanInfoArray = singleObject.getJSONArray("LoanInfos");
            JSONObject loanInfoJSONObject = loanInfoArray.getJSONObject(0);

            String fistBidTime = MJsonUtil.getString(loanInfoJSONObject, "FistBidTime");
            String creditCode = MJsonUtil.getString(loanInfoJSONObject, "CreditCode");
            int educateValidate = MJsonUtil.getInt(loanInfoJSONObject, "EducateValidate");
            int months = MJsonUtil.getInt(loanInfoJSONObject, "Months");
            int currentRate = MJsonUtil.getInt(loanInfoJSONObject, "CurrentRate");
            int gender = MJsonUtil.getInt(loanInfoJSONObject, "Gender");
            double amount = MJsonUtil.getDouble(loanInfoJSONObject, "Amount");
            int age = MJsonUtil.getInt(loanInfoJSONObject, "Age");
            int ListingId = MJsonUtil.getInt(loanInfoJSONObject, "ListingId");

            NewVersionLoanListDetailBean bean=new NewVersionLoanListDetailBean();

            List<NewVersionLoanListDetailBean.LoanInfosBean> loanInfosBeens = new ArrayList<NewVersionLoanListDetailBean.LoanInfosBean>();
            NewVersionLoanListDetailBean.LoanInfosBean newVersionLoanInfoBean=new NewVersionLoanListDetailBean.LoanInfosBean();

            newVersionLoanInfoBean.setAge(age);
            newVersionLoanInfoBean.setCreditCode(creditCode);
            newVersionLoanInfoBean.setEducateValidate(educateValidate);
            newVersionLoanInfoBean.setFistBidTime(fistBidTime);
            newVersionLoanInfoBean.setMonths(months);
            newVersionLoanInfoBean.setCurrentRate(currentRate);
            newVersionLoanInfoBean.setGender(gender);
            newVersionLoanInfoBean.setAmount(amount);
            newVersionLoanInfoBean.setListingId(ListingId);

            loanInfosBeens.add(newVersionLoanInfoBean);
            bean.setLoanInfos(loanInfosBeens);

            detailBeanList.add(bean);
        } catch (Exception e) {
            Log_Util.showLogCompletion(e.toString());
        }
    }

    /**
     * 获取风险中性数据标的
     *params:EducateValidate  Month  CreditCode
     */
    private void getMiddleRiskLoanList() {
        tempList=new ArrayList<>();
        MiddleRiskComparator comparator=new MiddleRiskComparator();
        Collections.sort(detailBeanList,comparator);
        Log_Util.showLogCompletion(detailBeanList.size()+"");
        int size2 = detailBeanList.size();
        for (int i = 0; i < size2; i++) {
            int listingId = detailBeanList.get(i).getLoanInfos().get(0).getListingId();

            int size1 = list.size();
            B:  for (int j = 0; j < size1; j++) {
                int listingId1 = list.get(j).getListingId();
                Log_Util.showLogCompletion("listingId:"+listingId+"--"+i+"--"+"listingId1:"+listingId1+"--"+j);
                if (listingId==listingId1){
                    tempList.add(list.get(j));
                    break ;
                }
            }
        }
        adapter.dataChange(tempList);
        Log_Util.showLogCompletion(tempList.size()+"");
    }

    /**
     * 获取高收益数据标的
     * params:CurrentRate  CreditCode  FistBidTime  EducateValidate Gender Amount
     */
    private void getHighEarningsLoanList() {
        tempList=new ArrayList<>();
        HighEarningsComparator comparator=new HighEarningsComparator();
        Collections.sort(detailBeanList,comparator);
        Log_Util.showLogCompletion(detailBeanList.size()+"");
        int size2 = detailBeanList.size();
        for (int i = 0; i < size2; i++) {
            int listingId = detailBeanList.get(i).getLoanInfos().get(0).getListingId();

            int size1 = list.size();
            B:  for (int j = 0; j < size1; j++) {
                int listingId1 = list.get(j).getListingId();
                Log_Util.showLogCompletion("listingId:"+listingId+"--"+i+"--"+"listingId1:"+listingId1+"--"+j);
                if (listingId==listingId1){
                    tempList.add(list.get(j));
                    break ;
                }
            }
        }
        adapter.dataChange(tempList);
        Log_Util.showLogCompletion(tempList.size()+"");
    }
    /**
     * 获取快速回笼数据标的
     *params:FistBidTime  EducateValidate  CreditCode  Age
     *
     */
    private void getSoonBackLoanList() {
        tempList=new ArrayList<>();
        SoonBackComparator comparator=new SoonBackComparator();
        Collections.sort(detailBeanList,comparator);
        Log_Util.showLogCompletion(detailBeanList.size()+"");
        int size2 = detailBeanList.size();
        for (int i = 0; i < size2; i++) {
            int listingId = detailBeanList.get(i).getLoanInfos().get(0).getListingId();

            int size1 = list.size();
            B:  for (int j = 0; j < size1; j++) {
                int listingId1 = list.get(j).getListingId();
                Log_Util.showLogCompletion("listingId:"+listingId+"--"+i+"--"+"listingId1:"+listingId1+"--"+j);
                if (listingId==listingId1){
                    tempList.add(list.get(j));
                    break ;
                }
            }
        }
        adapter.dataChange(tempList);
        Log_Util.showLogCompletion(tempList.size()+"");
    }
    /**
     * 获取低风险的数据标的
     * params:CreditCode EducateValidate  FistBidTime
     * params:EducateValidate  Months
     */
    private void getLowRiskLoanList() {
        tempList=new ArrayList<>();
        LowRiskComparator comparator=new LowRiskComparator();
        Collections.sort(detailBeanList,comparator);
        Log_Util.showLogCompletion(detailBeanList.size()+"");
        int size2 = detailBeanList.size();
        for (int i = 0; i < size2; i++) {
            int listingId = detailBeanList.get(i).getLoanInfos().get(0).getListingId();
            int size1 = list.size();
            B:  for (int j = 0; j < size1; j++) {
                int listingId1 = list.get(j).getListingId();
                Log_Util.showLogCompletion("listingId:"+listingId+"--"+i+"--"+"listingId1:"+listingId1+"--"+j);
                if (listingId==listingId1){
                    tempList.add(list.get(j));
                    break ;
                }
            }
        }
        adapter.dataChange(tempList);
        Log_Util.showLogCompletion(tempList.size()+"");

    }
}
