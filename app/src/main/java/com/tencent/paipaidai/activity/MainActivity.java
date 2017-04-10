package com.tencent.paipaidai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tencent.paipaidai.R;
import com.tools.UsualTools;

import butterknife.InjectView;

public class MainActivity extends Activity implements View.OnClickListener {

    @InjectView(R.id.bt_new_version_loan_list)
    Button btNewVersionLoanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_new_version_loan_list).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_new_version_loan_list:
                UsualTools.jumpActivity(this,NewVersionLoanListActivity.class);
                break;
        }
    }
}
