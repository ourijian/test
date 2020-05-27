package com.ourijian.startschool.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ourijian.startschool.R;
import com.ourijian.startschool.bean.StudentBean;
import com.ourijian.startschool.databinding.ActivityClassmateMsgBinding;
import com.ourijian.startschool.util.OkHttpUtil;

import java.io.IOException;

import okhttp3.FormBody;

public class ClassmateMsgActivity extends AppCompatActivity {

    ActivityClassmateMsgBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_classmate_msg);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {

                initData();

            }
        }).start();


    }

    /**
     * 请求 同学详情
     */
    private void initData() {
        int id = getIntent().getIntExtra("id", -1);
        String url = "http://"+getResources().getString(R.string.ip)+"/freshmenserver/classmate/detail/"+id;
        try {
            final String result = OkHttpUtil.postForParams(url, new FormBody.Builder().build());
            Gson gson = new Gson();
            final StudentBean studentBean = gson.fromJson(result, StudentBean.class);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(studentBean.isSuccess()){
                        //请求成功后展示数据
                        StudentBean.Student student = studentBean.getData();
                        binding.tvName.setText(student.getName());
                        binding.tvTelephone.setText(student.getTelephone());
                        binding.tvDepartment.setText(student.getDepartment());
                        binding.tvMajor.setText(student.getMajor());
                        binding.tvEmail.setText(student.getEmail());

                    }else{
                        Toast.makeText(ClassmateMsgActivity.this, "请求失败"+result, Toast.LENGTH_SHORT).show();
                    }

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
