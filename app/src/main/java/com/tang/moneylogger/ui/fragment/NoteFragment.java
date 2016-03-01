package com.tang.moneylogger.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tang.moneylogger.R;
import com.tang.moneylogger.adapter.ActivityAdapter;
import com.tang.moneylogger.bean.Activities;
import com.tang.moneylogger.bean.Total;
import com.tang.moneylogger.config.MyApplication;
import com.tang.moneylogger.dao.ActivityDao;
import com.tang.moneylogger.dao.TotalDao;
import com.tang.moneylogger.dao.impl.ActivityDaoImpl;
import com.tang.moneylogger.dao.impl.TotalDaoImpl;
import com.tang.moneylogger.ui.AddActivity;
import com.tang.mybase.db.DBManager;
import com.tang.mybase.ui.dialog.BaseDialog;
import com.tang.mybase.ui.fragment.BaseFragment;

import java.util.List;

/**
 * Created by Wangto Tang on 2015/6/16.
 */
public class NoteFragment extends BaseFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    ListView lv_activity;
    List<Activities> activityList = null;
    DBManager dbManager = null;
    ActivityDao dao = null;
    ActivityAdapter activityAdapter;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_note);
        initViews();
        init();
    }

    public void initViews(){
        lv_activity = (ListView) findViewById(R.id.lv_activity);
    }

    public void init(){
        context = getActivity();
        dbManager = MyApplication.getDBManager();
        dao = new ActivityDaoImpl(dbManager);
        activityList = dao.queryAll();
        activityAdapter = new ActivityAdapter(activityList,getActivity());
        lv_activity.setAdapter(activityAdapter);
        lv_activity.setOnItemClickListener(this);
        lv_activity.setOnItemLongClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        activityList = dao.queryAll();
        activityAdapter.update(activityList);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Activities activity = (Activities) activityAdapter.getItem(position);
        Intent intent = new Intent(context, AddActivity.class);
        intent.putExtra("from", "update");
        intent.putExtra("to",activity.getType_id());
        intent.putExtra("activity",activity);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final Activities activity = (Activities) activityAdapter.getItem(position);
        final BaseDialog dialog = new BaseDialog(getActivity());
        dialog.show();
        dialog.init(BaseDialog.noTitle | BaseDialog.noCustomView);
        dialog.setMessage("确定要删除吗？");
        dialog.setNegativeButton("取消", null);
        dialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.deleteActivity(activity);
                TotalDao totalDao = new TotalDaoImpl(dbManager);
                Total total = null;
                switch (activity.getType_id()) {
                    case 0:
                        total = totalDao.queryForTimeAndTypeId(activity.getTime(),0);
                        total.setAmount(total.getAmount()-activity.getAmount());
                        totalDao.updateTotal(total);
                        break;
                    case 1:
                        total = totalDao.queryForTimeAndTypeId(activity.getTime(),1);
                        total.setAmount(total.getAmount()-activity.getAmount());
                        totalDao.updateTotal(total);
                        break;
                    case 2:
                        break;
                }
                activityAdapter.remove(activity);
                dialog.dismiss();
            }
        });
        return true;
    }
}
