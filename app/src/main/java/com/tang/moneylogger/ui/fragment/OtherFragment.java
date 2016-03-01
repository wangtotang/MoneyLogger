package com.tang.moneylogger.ui.fragment;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.tang.moneylogger.R;
import com.tang.moneylogger.adapter.OtherAdapter;
import com.tang.moneylogger.ui.AboutActivity;
import com.tang.moneylogger.ui.ReportActivity;
import com.tang.moneylogger.ui.SettingActivity;
import com.tang.mybase.ui.BaseActivity;
import com.tang.mybase.ui.fragment.BaseFragment;

/**
 * Created by Wangto Tang on 2015/6/22.
 */
public class OtherFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    GridView gv_other;
    OtherAdapter otherAdapter;
    BaseActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_other);
        initViews();
        init();
    }

    public void initViews(){
        gv_other = (GridView) findViewById(R.id.gv_other);
    }

    private void init() {
        otherAdapter = new OtherAdapter(getActivity());
        gv_other.setAdapter(otherAdapter);
        gv_other.setOnItemClickListener(this);
        gv_other.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return MotionEvent.ACTION_MOVE == event.getAction() ? true : false;
            }
        });
        activity = (BaseActivity) getActivity();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                activity.startActivity(ReportActivity.class);
                break;
            case 1:
                activity.startActivity(SettingActivity.class);
                break;
            case 2:
                activity.startActivity(AboutActivity.class);
                break;
        }
    }
}
