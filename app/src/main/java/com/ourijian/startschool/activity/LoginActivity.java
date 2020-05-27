package com.ourijian.startschool.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ourijian.startschool.MyApplication;
import com.ourijian.startschool.R;
import com.ourijian.startschool.bean.StudentBean;
import com.ourijian.startschool.databinding.ActivityLoginBinding;
import com.ourijian.startschool.util.OkHttpUtil;

import java.io.IOException;

import okhttp3.FormBody;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        initData();//判断之前是否记住用户名和密码

        binding.tvGotoRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = binding.etUsername.getText().toString().trim();
                final String password = binding.etPassword.getText().toString().trim();
                final boolean isRemember = binding.cbRemember.isChecked();
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "用户名或密码未输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                //开启线程进行网络访问
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        login(name,password,isRemember);
                    }
                }).start();
            }
        });

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    /**
     * 是否记住用户名和密码
     */
    private void initData() {
        SharedPreferences sp = getSharedPreferences("user.db",MODE_PRIVATE);
        boolean isRemember = sp.getBoolean("isRemember", false);
        if (isRemember) {
            //如果之前记住过用户名和密码就设置输入框和复选框内容
            StudentBean.Student user = MyApplication.getUser(LoginActivity.this);
            binding.etUsername.setText(user.getName());
            binding.etPassword.setText(user.getPassword());
            binding.cbRemember.setChecked(true);
        }
    }

    /**
     * 网络请求登录
     * @param name
     * @param password
     * @param isRemember
     */
    private void login(String name, String password, final boolean isRemember) {
        String url = "http://"+getResources().getString(R.string.ip)+"/freshmenserver/user/login";
        FormBody body = new FormBody.Builder()
                .add("name",name)
                .add("password",password)
                .build();
        try {
            String result = OkHttpUtil.postForParams(url, body);
            Gson gson = new Gson();
            final StudentBean studentBean = gson.fromJson(result, StudentBean.class);
            final boolean success = studentBean.isSuccess();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(success){
                        MyApplication.saveUser(studentBean.getData(),LoginActivity.this,isRemember);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "登录失败，用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
