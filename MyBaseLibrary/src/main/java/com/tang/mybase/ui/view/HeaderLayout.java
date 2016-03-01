package com.tang.mybase.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tang.mybase.R;

/**
 * 设置ActionBar的通用布局
 * Created by Wangto Tang on 2015/5/25.
 */
public class HeaderLayout extends LinearLayout {

    private LayoutInflater mInflater;
    private View mHeader;
    private LinearLayout action_bar_left;
    private LinearLayout action_bar_middle;
    private LinearLayout action_bar_right;
    private TextView tv_header_title;
    private Button btn_backup;
    private HeaderStyle headerStyle;
    private ImageButton ib_menu,ib_backup;
    /**
     * ActionBar样式
     */
    public enum HeaderStyle{
        LEFT_TITLE,LEFT_TITLE_WITH_BUTTON,LEFT_TITLE_WITH_MENU,LEFT_TITLE_WITH_BUTTON_AND_MENU,
        MIDDLE_TITLE,MIDDLE_TITLE_WITH_BUTTON,MIDDLE_TITLE_WITH_MENU,MIDDLE_TITLE_WITH_BUTTON_AND_MENU,
        BACKUP,BACKUP_WITH_MENU,MIDDLE_VIEW_WITH_MENU
    }

    public HeaderLayout(Context context) {
        super(context);
        initViews(context);
    }

    public HeaderLayout(Context context,AttributeSet attrs){
        super(context, attrs);
        initViews(context);
    }

    /**
     * 获取布局
     * @param context
     */
    public void initViews(Context context) {
        mInflater = LayoutInflater.from(context);
        mHeader = mInflater.inflate(R.layout.view_common_header, null);
        addView(mHeader);
        action_bar_left = (LinearLayout) mHeader.findViewById(R.id.action_bar_left);
        action_bar_middle = (LinearLayout) mHeader.findViewById(R.id.action_bar_middle);
        action_bar_right = (LinearLayout) mHeader.findViewById(R.id.action_bar_right);
    }

    /**
     * 根据不同Style设置不同Header样式
     * @param style Header样式
     */
    public void init(HeaderStyle style) {
        setDefault();
        switch (style){
            case LEFT_TITLE:
                headerStyle = HeaderStyle.LEFT_TITLE;
                setLeftTitle();
                break;
            case LEFT_TITLE_WITH_BUTTON:
                headerStyle = HeaderStyle.LEFT_TITLE_WITH_BUTTON;
                setLeftTitleWithButton();
                break;
            case LEFT_TITLE_WITH_MENU:
                headerStyle = HeaderStyle.LEFT_TITLE_WITH_MENU;
                setLeftTitleWithMenu();
                break;
            case LEFT_TITLE_WITH_BUTTON_AND_MENU:
                headerStyle = HeaderStyle.LEFT_TITLE_WITH_BUTTON_AND_MENU;
                setLeftTitleWithButtonAndMenu();
                break;
            case MIDDLE_TITLE:
                headerStyle = HeaderStyle.MIDDLE_TITLE;
                setMiddleTitle();
                break;
            case MIDDLE_TITLE_WITH_BUTTON:
                headerStyle = HeaderStyle.MIDDLE_TITLE_WITH_BUTTON;
                setMiddleTitleWithButton();
                break;
            case MIDDLE_TITLE_WITH_MENU:
                headerStyle = HeaderStyle.MIDDLE_TITLE_WITH_MENU;
                setMiddleTitleWithMenu();
                break;
            case MIDDLE_TITLE_WITH_BUTTON_AND_MENU:
                headerStyle = HeaderStyle.MIDDLE_TITLE_WITH_BUTTON_AND_MENU;
                setMiddleTitleWithButtonAndMenu();
                break;
            case BACKUP:
                headerStyle = HeaderStyle.BACKUP;
                setBackup();
                break;
            case BACKUP_WITH_MENU:
                headerStyle = HeaderStyle.BACKUP_WITH_MENU;
                setBackupWithMenu();
                break;
            case MIDDLE_VIEW_WITH_MENU:
                headerStyle = HeaderStyle.MIDDLE_VIEW_WITH_MENU;
                setMenu();
                break;
        }
    }

    /**
     * 还原成默认Header
     */
    public void setDefault(){
        action_bar_left.removeAllViews();
        action_bar_middle.removeAllViews();
        action_bar_right.removeAllViews();
    }

    /**
     * 设置标题在左边
     */
    public void setLeftTitle(){
        View view = mInflater.inflate(R.layout.widget_left_title,null);
        action_bar_left.addView(view);
        tv_header_title = (TextView) view.findViewById(R.id.tv_header_title);
    }

    /**
     * 设置标题
     * @param title 标题
     */
    public void setTitle(CharSequence title){
      if(headerStyle == HeaderStyle.LEFT_TITLE||headerStyle == HeaderStyle.MIDDLE_TITLE
              ||headerStyle == HeaderStyle.LEFT_TITLE_WITH_MENU||headerStyle == HeaderStyle.MIDDLE_TITLE_WITH_BUTTON
              ||headerStyle == HeaderStyle.MIDDLE_TITLE_WITH_MENU||headerStyle == HeaderStyle.MIDDLE_TITLE_WITH_BUTTON_AND_MENU) {
          if (title != null && title.length() != 0) {
              tv_header_title.setText(title);
          } else {
              tv_header_title.setVisibility(View.GONE);
          }
      }else if(headerStyle == HeaderStyle.LEFT_TITLE_WITH_BUTTON
              ||headerStyle == HeaderStyle.LEFT_TITLE_WITH_BUTTON_AND_MENU){
          btn_backup.setText(title);
      }
    }

    /**
     * 设置标题在中间
     */
    public void setMiddleTitle(){
        View view = mInflater.inflate(R.layout.widget_middle_title,null);
        action_bar_middle.addView(view);
        tv_header_title = (TextView) view.findViewById(R.id.tv_header_title);
    }

    /**
     * 设置带按钮的左边标题
     */
    public void setLeftTitleWithButton(){
        View view = mInflater.inflate(R.layout.widget_left_title_with_button,null);
        action_bar_left.addView(view);
        btn_backup = (Button) view.findViewById(R.id.btn_backup);
    }

    /**
     * 设置带菜单的左边标题
     */
    public void setLeftTitleWithMenu(){
        View title = mInflater.inflate(R.layout.widget_left_title,null);
        action_bar_left.addView(title);
        tv_header_title = (TextView) title.findViewById(R.id.tv_header_title);
        View menu = mInflater.inflate(R.layout.widget_menu,null);
        action_bar_right.addView(menu);
        ib_menu = (ImageButton) menu.findViewById(R.id.ib_menu);
    }

    /**
     * 设置菜单图标
     * @param id 图标资源id
     */
    public void setMenuIcon(int id){
        if(id > 0) {
            ib_menu.setImageResource(id);
        }else{
            ib_menu.setVisibility(View.GONE);
        }
    }

    /**
     * 设置带按钮和菜单的左边标题
     */
    public void setLeftTitleWithButtonAndMenu(){
        View backup = mInflater.inflate(R.layout.widget_left_title_with_button,null);
        action_bar_left.addView(backup);
        btn_backup = (Button) backup.findViewById(R.id.btn_backup);
        View menu = mInflater.inflate(R.layout.widget_menu,null);
        action_bar_right.addView(menu);
        ib_menu = (ImageButton) menu.findViewById(R.id.ib_menu);
    }

    /**
     * 获得返回键
     * @return Backup
     */
    public View getBackup(){
        if(headerStyle == HeaderStyle.LEFT_TITLE_WITH_BUTTON||headerStyle == HeaderStyle.LEFT_TITLE_WITH_BUTTON_AND_MENU) {
            return btn_backup;
        }else if(headerStyle == HeaderStyle.BACKUP||headerStyle == HeaderStyle.MIDDLE_TITLE_WITH_BUTTON
                ||headerStyle == HeaderStyle.MIDDLE_TITLE_WITH_BUTTON_AND_MENU||headerStyle == HeaderStyle.BACKUP_WITH_MENU){
            return ib_backup;
        }
        return null;
    }

    /**
     * 获得菜单
     * @return Menu
     */
    public ImageButton getMenu(){
        return ib_menu;
    }

    /**
     * 设置返回键
     */
    public void setBackup(){
        View view = mInflater.inflate(R.layout.widget_backup,null);
        action_bar_left.addView(view);
        ib_backup = (ImageButton) view.findViewById(R.id.ib_backup);
    }

    /**
     * 设置返回键图标
     * @param id 图标资源id
     */
    public void setBackupIcon(int id){
        if(headerStyle == HeaderStyle.BACKUP||headerStyle == HeaderStyle.MIDDLE_TITLE_WITH_BUTTON
                ||headerStyle == HeaderStyle.MIDDLE_TITLE_WITH_BUTTON_AND_MENU||headerStyle == HeaderStyle.BACKUP_WITH_MENU) {
            if (id > 0) {
                ib_backup.setImageResource(id);
            } else {
                ib_backup.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置带按钮的中间标题
     */
    public void setMiddleTitleWithButton(){
        View backup = mInflater.inflate(R.layout.widget_backup,null);
        action_bar_left.addView(backup);
        ib_backup = (ImageButton) backup.findViewById(R.id.ib_backup);
        View title = mInflater.inflate(R.layout.widget_middle_title,null);
        action_bar_middle.addView(title);
        tv_header_title = (TextView) title.findViewById(R.id.tv_header_title);
    }

    /**
     * 设置带菜单的中间标题
     */
    public void setMiddleTitleWithMenu(){
        View title = mInflater.inflate(R.layout.widget_middle_title,null);
        action_bar_middle.addView(title);
        tv_header_title = (TextView) title.findViewById(R.id.tv_header_title);
        View menu = mInflater.inflate(R.layout.widget_menu,null);
        action_bar_right.addView(menu);
        ib_menu = (ImageButton) menu.findViewById(R.id.ib_menu);
    }

    /**
     * 设置带按钮和菜单的中间标题
     */
    public void setMiddleTitleWithButtonAndMenu(){
        View backup = mInflater.inflate(R.layout.widget_backup,null);
        action_bar_left.addView(backup);
        ib_backup = (ImageButton) backup.findViewById(R.id.ib_backup);
        View title = mInflater.inflate(R.layout.widget_middle_title,null);
        action_bar_middle.addView(title);
        tv_header_title = (TextView) title.findViewById(R.id.tv_header_title);
        View menu = mInflater.inflate(R.layout.widget_menu,null);
        action_bar_right.addView(menu);
        ib_menu = (ImageButton) menu.findViewById(R.id.ib_menu);
    }

    /**
     * 设置带菜单的返回键
     */
    public void setBackupWithMenu(){
        View backup = mInflater.inflate(R.layout.widget_backup,null);
        action_bar_left.addView(backup);
        ib_backup = (ImageButton) backup.findViewById(R.id.ib_backup);
        View menu = mInflater.inflate(R.layout.widget_menu,null);
        action_bar_right.addView(menu);
        ib_menu = (ImageButton) menu.findViewById(R.id.ib_menu);
    }

    public void setMenu(){
        View menu = mInflater.inflate(R.layout.widget_menu,null);
        action_bar_right.addView(menu);
        ib_menu = (ImageButton) menu.findViewById(R.id.ib_menu);
    }

    public void setMiddleView(View view){
        action_bar_middle.addView(view);
    }

}
