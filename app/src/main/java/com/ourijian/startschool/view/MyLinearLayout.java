package com.ourijian.startschool.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.ourijian.startschool.R;

/**
 * FragmentMe界面的一项
 */
public class MyLinearLayout extends LinearLayoutCompat {

    private final TextView tvName;
    private final TextView tvValue;
    private final ImageView ivRight;

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取布局文件中填写的数据（其实没用 死数据）
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.MyLinearLayout);
        String name = ta.getString(R.styleable.MyLinearLayout_name);
        String value = ta.getString(R.styleable.MyLinearLayout_value);
        Drawable drawable = ta.getDrawable(R.styleable.MyLinearLayout_rightImage);

        View view = inflate(context, R.layout.me_item, this);

        tvName = view.findViewById(R.id.tv_name);
        tvValue = view.findViewById(R.id.tv_value);
        ivRight = view.findViewById(R.id.iv_right);

        tvName.setText(name);
        tvValue.setText(value);
        ivRight.setImageDrawable(drawable);

    }

    /**
     * 修改name属性
     */
    public void setName(String name){
        tvName.setText(name);
    }

    /**
     * 修改value属性
     * @param value
     */
    public void setValue(String value){
        tvValue.setText(value);
    }

}
