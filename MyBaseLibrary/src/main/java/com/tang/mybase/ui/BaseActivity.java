package com.tang.mybase.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tang.mybase.R;
import com.tang.mybase.ui.view.HeaderLayout;

/**
 * 通用Activity
 * Created by Wangto Tang on 2015/5/26.
 */
public class BaseActivity extends FragmentActivity {

    private Toast toast;
    private HeaderLayout mHeaderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化左标题
     * @param leftTitle 左标题
     */
    public void initLeftTitle(CharSequence leftTitle){
        mHeaderLayout = (HeaderLayout) findViewById(R.id.action_bar);
        mHeaderLayout.init(HeaderLayout.HeaderStyle.LEFT_TITLE);
        mHeaderLayout.setTitle(leftTitle);
    }

    /**
     * 初始化中间标题
     * @param middleTitle 中间标题
     */
    public void initMiddleTitle(CharSequence middleTitle){
        mHeaderLayout = (HeaderLayout) findViewById(R.id.action_bar);
        mHeaderLayout.init(HeaderLayout.HeaderStyle.MIDDLE_TITLE);
        mHeaderLayout.setTitle(middleTitle);
    }

    /**
     * 初始化带按钮的左标题
     * @param leftTitle 左标题
     * @param listener 按钮点击事件监听器
     */
    public void initLeftTitleWithButton(CharSequence leftTitle,View.OnClickListener listener){
        mHeaderLayout = (HeaderLayout) findViewById(R.id.action_bar);
        mHeaderLayout.init(HeaderLayout.HeaderStyle.LEFT_TITLE_WITH_BUTTON);
        mHeaderLayout.setTitle(leftTitle);
        mHeaderLayout.getBackup().setOnClickListener(listener);
    }

    /**
     * 初始化带菜单的左标题
     * @param leftTitle 左标题
     * @param resId 菜单Icon
     * @param listener  菜单点击事件监听器
     */
    public void initLeftTileWithMenu(CharSequence leftTitle,int resId,View.OnClickListener listener){
        mHeaderLayout = (HeaderLayout) findViewById(R.id.action_bar);
        mHeaderLayout.init(HeaderLayout.HeaderStyle.LEFT_TITLE_WITH_MENU);
        mHeaderLayout.setTitle(leftTitle);
        mHeaderLayout.setMenuIcon(resId);
        mHeaderLayout.getMenu().setOnClickListener(listener);
    }

    /**
     * 返回键监听器（默认按钮的监听器）
     */
    public class OnBackupListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            finish();
        }

    }

    /**
     * 显示提醒
     * @param text 提醒文本
     */
    public void showToast(final CharSequence text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(BaseActivity.this, text, Toast.LENGTH_LONG);
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(toast == null) {
                    toast = Toast.makeText(BaseActivity.this, resId, Toast.LENGTH_LONG);
                }else{
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

    /**
     * 初始化带按钮和菜单的左标题
     * @param leftTitle 左标题
     * @param buttonListener 按钮点击事件
     * @param resId 菜单图标资源id
     * @param menuListener 菜单点击事件
     */
    public void initLeftTitleWithButtonAndMenu(CharSequence leftTitle,View.OnClickListener buttonListener,int resId,View.OnClickListener menuListener){
        mHeaderLayout = (HeaderLayout) findViewById(R.id.action_bar);
        mHeaderLayout.init(HeaderLayout.HeaderStyle.LEFT_TITLE_WITH_BUTTON_AND_MENU);
        mHeaderLayout.setTitle(leftTitle);
        mHeaderLayout.getBackup().setOnClickListener(buttonListener);
        mHeaderLayout.setMenuIcon(resId);
        mHeaderLayout.getMenu().setOnClickListener(menuListener);
    }

    /**
     * 初始化返回键
     * @param resId 返回键图标资源id
     * @param backupListener 返回键点击事件
     */
    public void initBackup(int resId,View.OnClickListener backupListener){
        mHeaderLayout = (HeaderLayout) findViewById(R.id.action_bar);
        mHeaderLayout.init(HeaderLayout.HeaderStyle.BACKUP);
        mHeaderLayout.setBackupIcon(resId);
        mHeaderLayout.getBackup().setOnClickListener(backupListener);
    }

    /**
     * 初始化带按钮的中间标题
     * @param middleTitle 中间标题
     * @param resId 按钮图标资源id
     * @param buttonListener 按钮点击事件
     */
    public void initMiddleTitleWithButton(CharSequence middleTitle,int resId,View.OnClickListener buttonListener){
        mHeaderLayout = (HeaderLayout) findViewById(R.id.action_bar);
        mHeaderLayout.init(HeaderLayout.HeaderStyle.MIDDLE_TITLE_WITH_BUTTON);
        mHeaderLayout.setTitle(middleTitle);
        mHeaderLayout.setBackupIcon(resId);
        mHeaderLayout.getBackup().setOnClickListener(buttonListener);
    }

    /**
     * 初始化带菜单的中间标题
     * @param middleTitle 中间标题
     * @param resId 菜单图标资源id
     * @param menuListener 菜单点击事件
     */
    public void initMiddleTitleWithMenu(CharSequence middleTitle,int resId,View.OnClickListener menuListener){
        mHeaderLayout = (HeaderLayout) findViewById(R.id.action_bar);
        mHeaderLayout.init(HeaderLayout.HeaderStyle.MIDDLE_TITLE_WITH_MENU);
        mHeaderLayout.setTitle(middleTitle);
        mHeaderLayout.setMenuIcon(resId);
        mHeaderLayout.getMenu().setOnClickListener(menuListener);
    }

    /**
     * 初始化带按钮和菜单的中间标题
     * @param middleTitle 中间标题
     * @param buttonId 按钮图标资源id
     * @param buttonListener 按钮点击事件
     * @param menuId 菜单图标资源id
     * @param menuListener 菜单点击事件
     */
    public void initMiddleTitleWithButtonAndMenu(CharSequence middleTitle,int buttonId,View.OnClickListener buttonListener,int menuId,View.OnClickListener menuListener){
        mHeaderLayout = (HeaderLayout) findViewById(R.id.action_bar);
        mHeaderLayout.init(HeaderLayout.HeaderStyle.MIDDLE_TITLE_WITH_BUTTON_AND_MENU);
        mHeaderLayout.setTitle(middleTitle);
        mHeaderLayout.setBackupIcon(buttonId);
        mHeaderLayout.getBackup().setOnClickListener(buttonListener);
        mHeaderLayout.setMenuIcon(menuId);
        mHeaderLayout.getMenu().setOnClickListener(menuListener);
    }

    /**
     * 初始化带菜单的返回键
     * @param buttonId 返回键图标资源id
     * @param buttonListener 返回键点击事件
     * @param menuId 菜单图标资源id
     * @param menuListener 菜单点击事件
     */
    public void initBackupWithMenu(int buttonId,View.OnClickListener buttonListener,int menuId,View.OnClickListener menuListener){
        mHeaderLayout = (HeaderLayout) findViewById(R.id.action_bar);
        mHeaderLayout.init(HeaderLayout.HeaderStyle.BACKUP_WITH_MENU);
        mHeaderLayout.setBackupIcon(buttonId);
        mHeaderLayout.getBackup().setOnClickListener(buttonListener);
        mHeaderLayout.setMenuIcon(menuId);
        mHeaderLayout.getMenu().setOnClickListener(menuListener);
    }

    /**
     * 初始化带菜单的自定义中间控件
     * @param layout 中间自定义控件
     * @param viewListener 自定义控件监听器
     * @param menuId 菜单图标资源
     * @param menuListener 菜单点击事件
     */
    public void initMiddleViewWithMenu(int layout,View.OnClickListener viewListener,int menuId,View.OnClickListener menuListener){
        mHeaderLayout = (HeaderLayout) findViewById(R.id.action_bar);
        mHeaderLayout.init(HeaderLayout.HeaderStyle.MIDDLE_VIEW_WITH_MENU);
        View view = View.inflate(this,layout,null);
        mHeaderLayout.setMiddleView(view);
        view.setOnClickListener(viewListener);
        mHeaderLayout.setMenuIcon(menuId);
        mHeaderLayout.getMenu().setOnClickListener(menuListener);
    }

    /**
     * 开始新Activity
     * @param cls 新Activity的Class
     */
    public void startActivity(Class cls){
        Intent intent = new Intent(this,cls);
        super.startActivity(intent);
    }

    public HeaderLayout getHeaderLayout(){
        mHeaderLayout = (HeaderLayout) findViewById(R.id.action_bar);
        return  mHeaderLayout;
    }

}
