package com.tencent.paipaidai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tencent.paipaidai.R;
import com.tencent.paipaidai.bean.NewVersionLoanInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/8/008.
 */

public class NewVersionLoanInfoAdapter extends BaseAdapter {
    private  LayoutInflater inflater;
    private  List<NewVersionLoanInfoBean> list;
    private  Context context;

    public NewVersionLoanInfoAdapter(Context context, List<NewVersionLoanInfoBean> list) {
        this.context=context;
        this.list=list;
        inflater = LayoutInflater.from(context);
    }
    public void dataChange(List<NewVersionLoanInfoBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_new_version_loan_info_item,null);
            holder.TitleView = (TextView) convertView.findViewById(R.id.Title);
            holder.CreditCodeView = (TextView) convertView.findViewById(R.id.CreditCode);
            holder.AmountView = (TextView) convertView.findViewById(R.id.Amount);
            holder.MonthsView = (TextView) convertView.findViewById(R.id.Months);
            holder.PayWayView = (TextView) convertView.findViewById(R.id.PayWay);
            holder.RemainFundingView = (TextView) convertView.findViewById(R.id.RemainFunding);
            holder.RateView = (TextView) convertView.findViewById(R.id.Rate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        NewVersionLoanInfoBean bean = list.get(position);

        holder.TitleView.setText("Title:"+bean.getTitle());
        holder.CreditCodeView.setText("CreditCode:"+bean.getCreditCode());
        holder.AmountView.setText("Amount:"+bean.getAmount());
        holder.MonthsView.setText("Months:"+bean.getMonths());
        holder.PayWayView.setText("PayWay:"+bean.getPayWay());
        holder.RemainFundingView.setText("RemainFunding:"+bean.getRemainFunding());
        holder.RateView.setText("Rate:"+bean.getRate());
        return convertView;
    }

    class ViewHolder {
        private TextView TitleView,CreditCodeView,AmountView,RateView,MonthsView,PayWayView,RemainFundingView;
    }
}
