package com.tang.mybase.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tang.mybase.R;


/**
 * 通用Dialog
 * Created by Wangto Tang on 2015/6/8.
 */
public class BaseDialog extends Dialog {

    public static final int noTitle = 1;//没有标题
    public static final int noMessage = 2;//没有消息
    public static final int noCustomView = 4;//没有自定义内容
    public static final int noButton = 8;//没有按钮

    protected Context context;
    protected TextView tv_dialog_title,tv_dialog_message;
    protected View v_dialog_top_line,v_dialog_bottom_line,v_dialog_middle_line;
    protected Button btn_dialog_negative_button,btn_dialog_positive_button;
    protected LinearLayout ll_custom_view;
    protected LinearLayout ll_dialog_bottom;

    public BaseDialog(Context context) {
        super(context, R.style.Alert);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_base);
        initViews();
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }


    /**
     * 初始化视图
     */
    protected void initViews(){
        tv_dialog_title = (TextView) findViewById(R.id.tv_dialog_title);
        v_dialog_top_line = findViewById(R.id.v_dialog_top_line);
        tv_dialog_message = (TextView) findViewById(R.id.tv_dialog_message);
        ll_custom_view = (LinearLayout) findViewById(R.id.ll_custom_view);
        v_dialog_bottom_line = findViewById(R.id.v_dialog_bottom_line);
        ll_dialog_bottom = (LinearLayout) findViewById(R.id.ll_dialog_bottom);
        btn_dialog_negative_button = (Button) findViewById(R.id.btn_dialog_negative_button);
        v_dialog_middle_line = findViewById(R.id.v_dialog_middle_line);
        btn_dialog_positive_button = (Button) findViewById(R.id.btn_dialog_positive_button);
    }

    /**
     * 初始化对话框样式
     * @param style 样式
     */
    public void init(int style){
        if((style & noTitle) == 1){
            tv_dialog_title.setVisibility(View.GONE);
            v_dialog_top_line.setVisibility(View.GONE);
        }
        if((style & noMessage) == 2){
            tv_dialog_message.setVisibility(View.GONE);
        }
        if((style & noCustomView) == 4){
            ll_custom_view.setVisibility(View.GONE);
        }
        if((style & noButton) == 8){
            v_dialog_bottom_line.setVisibility(View.GONE);
            ll_dialog_bottom.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题
     * @param title 标题
     */
    public void setTitle(String title){
        tv_dialog_title.setText(title);
    }

    /**
     * 设置消息
     * @param message 消息
     */
    public void setMessage(String message){
        tv_dialog_message.setText(message);
    }


    /**
     * 设置自定义内容视图
     * @param view 自定义内容视图
     */
    public void setCustomView(View view){
        ll_custom_view.addView(view);
    }

    /**
     * 设置取消按钮
     * @param text 取消按钮文本
     * @param onClickListener 取消按钮监听器
     */
    public void setNegativeButton(String text,View.OnClickListener onClickListener){
        btn_dialog_negative_button.setText(text);
        if(onClickListener!=null) {
            btn_dialog_negative_button.setOnClickListener(onClickListener);
        }else{
            btn_dialog_negative_button.setOnClickListener(new DefaultListener());
        }
    }


    /**
     * 设置确定按钮
     * @param text 确定按钮文本
     * @param onClickListener 确定按钮监听器
     */
    public void setPositiveButton(String text,View.OnClickListener onClickListener){
        btn_dialog_positive_button.setText(text);
        if(onClickListener!=null) {
            btn_dialog_positive_button.setOnClickListener(onClickListener);
        }else{
            btn_dialog_positive_button.setOnClickListener(new DefaultListener());
        }
    }

    /**
     * 设置仅有一个按钮
     * @param text 按钮文本
     * @param onClickListener 按钮监听器
     */
    public void setButtonForOnly(String text,View.OnClickListener onClickListener){
        ll_dialog_bottom.removeView(btn_dialog_negative_button);
        ll_dialog_bottom.removeView(v_dialog_middle_line);
        btn_dialog_positive_button.setText(text);
        if(onClickListener!=null) {
            btn_dialog_positive_button.setOnClickListener(onClickListener);
        }else{
            btn_dialog_positive_button.setOnClickListener(new DefaultListener());
        }
    }

    /**
     * 默认监听器（对话框消失）
     */
    private class DefaultListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            BaseDialog.this.dismiss();
        }
    }

    /**
     * 设置（相对）坐标和大小
     * @param x 左上角x坐标
     * @param y 左上角y坐标
     * @param width 宽
     * @param height 高
     */
    public void setBounds(int x,int y,int width,int height){
        WindowManager.LayoutParams params = getWindow().getAttributes();
        if(x > 0) {
            params.x = x;
        }
        if(y > 0) {
            params.y = y;
        }
        if(width > 0) {
            params.width = width;
        }
        if(height > 0) {
            params.height = height;
        }
        getWindow().setAttributes(params);
    }
}
