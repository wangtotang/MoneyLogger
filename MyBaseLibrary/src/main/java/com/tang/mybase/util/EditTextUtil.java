package com.tang.mybase.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 文本编辑框输入规则工具类
 * Created by Wangto Tang on 2015/6/25.
 */
public class EditTextUtil {

    /**
     * 设置保留小数点后两位
     * @param editText 需要设置EditText
     */
    public static void setPricePoint(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }else{
                    if(s.length() > 6){
                        s = s.toString().subSequence(0,6);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

        });

    }

}
