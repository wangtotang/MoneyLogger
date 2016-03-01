package com.tang.mybase.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tang.mybase.R;

/**
 * 通用Fragment
 * Created by Wangto Tang on 2015/6/16.
 */
public class BaseFragment extends Fragment {

    private Toast toast;
    private ViewGroup container;
    private Context context;
    private View contentView;
    protected LayoutInflater inflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        context = getActivity().getApplicationContext();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        if(contentView == null){
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        return contentView;
    }

    public Context getApplicationContext(){
        return context;
    }

    public void setContentView(int resId){
        setContentView(inflater.inflate(resId,container,false));
    }

    public void setContentView(View view){
        contentView = view;
    }

    public View getContentView(){
        return contentView;
    }

    public View findViewById(int id){
        if(contentView != null){
            return contentView.findViewById(id);
        }
        return null;
    }

    /**
     * 显示提醒
     * @param text 提醒文本
     */
    public void showToast(final CharSequence text){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(getActivity(), text, Toast.LENGTH_LONG);
                } else {
                    toast.setText(text);
                }
                toast.show();
            }
        });
    }

    /**
     * 显示提醒
     * @param resId 提醒资源id
     */
    public void showToast(final int resId){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(getActivity(), resId, Toast.LENGTH_LONG);
                } else {
                    toast.setText(resId);
                }
                toast.show();
            }
        });
    }

    /**
     * 打印日志
     * @param info 日志信息
     */
    public void showLog(String info){
        Log.i(getString(R.string.content_description), info);
    }


}
