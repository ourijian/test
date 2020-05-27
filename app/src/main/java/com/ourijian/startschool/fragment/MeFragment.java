package com.ourijian.startschool.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ourijian.startschool.MyApplication;
import com.ourijian.startschool.R;
import com.ourijian.startschool.activity.UpdateActivity;
import com.ourijian.startschool.bean.StudentBean;
import com.ourijian.startschool.view.MyLinearLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    private StudentBean.Student user;
    private MyLinearLayout mLLName;
    private MyLinearLayout mLLDepartment;
    private MyLinearLayout mLLMajor;
    private MyLinearLayout mLLTelephone;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        //获取控件
        mLLName = view.findViewById(R.id.item_name);
        mLLDepartment = view.findViewById(R.id.item_department);
        mLLMajor = view.findViewById(R.id.item_major);
        mLLTelephone = view.findViewById(R.id.item_telephone);

        //设置点击事件
        mLLName.setOnClickListener(new MyOnclickListen());
        mLLDepartment.setOnClickListener(new MyOnclickListen());
        mLLMajor.setOnClickListener(new MyOnclickListen());
        mLLTelephone.setOnClickListener(new MyOnclickListen());

        //初始化显示内容
        initView();


        return view;
    }

    private void initView() {
        user = MyApplication.getUser(getActivity());
        mLLName.setValue(user.getName());
        mLLDepartment.setValue(user.getDepartment());
        mLLMajor.setValue(user.getMajor());
        mLLTelephone.setValue(user.getTelephone());
    }

    //点击事件
    class MyOnclickListen implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //点击不同的控件，跳转修改Activity中修改不同的数据
            Intent intent = new Intent(getActivity(), UpdateActivity.class);
            switch (v.getId()){
                case R.id.item_name:
                    intent.putExtra("update","name");
                    break;
                case R.id.item_department:
                    intent.putExtra("update","department");
                    break;
                case R.id.item_major:
                    intent.putExtra("update","major");
                    break;
                case R.id.item_telephone:
                    intent.putExtra("update","telephone");
                    break;
            }
            startActivity(intent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }
}
