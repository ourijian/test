package com.ourijian.startschool.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ourijian.startschool.R;
import com.ourijian.startschool.bean.StudentBean;
import com.ourijian.startschool.databinding.ActivityRegisterBinding;
import com.ourijian.startschool.util.OkHttpUtil;

import java.io.IOException;

import okhttp3.FormBody;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    private static final String TAG = "RegisterActivity";
    //不同部门的专业分别对应的数组
    private String [][] majors = {{"移动应用开发","计算机应用技术","软件技术","数字媒体"},{"艺术设计","机器人","数控","物联网"},{"酒店管理","商务英语","机场运行","工管"},{"经济管理","电子商务","市场营销","会计"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);

        //部门 下拉框选中事件
        binding.spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //根据不同的部门设置该部门的专业
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_spinner_item,majors[position]);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinnerMajor.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * 注册按钮被点击
         */
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框中的值
                final String name = binding.etUsername.getText().toString().trim();
                final String email = binding.etEmail.getText().toString().trim();
                final String phone = binding.etPhone.getText().toString().trim();
                final String password = binding.etPassword.getText().toString().trim();
                String password2 = binding.etPassword2.getText().toString().trim();
                final String major = binding.spinnerMajor.getSelectedItem().toString();
                final String department = binding.spinnerDepartment.getSelectedItem().toString();

                /**
                 * 非空判断
                 */
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)
                ||TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)){
                    Toast.makeText(RegisterActivity.this, "有未填写的数据", Toast.LENGTH_SHORT).show();
                    return;
                }

                /**
                 * 两次密码判断
                 */
                if(!password.equals(password2)){
                    Toast.makeText(RegisterActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }

                //请求注册
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String register = register(name, email, phone, password, major, department);
                            Gson gson = new Gson();
                            final StudentBean studentBean = gson.fromJson(register, StudentBean.class);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(!studentBean.isSuccess()){
                                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();



            }


        });

    }

    /**
     * 注册的方法
     * @param name
     * @param email
     * @param phone
     * @param password
     * @param major
     * @param department
     */
    private String register(String name, String email, String phone, String password, String major, String department) throws IOException {
        String url = "http://"+getResources().getString(R.string.ip)+"/freshmenserver/user/register";
        FormBody body = new FormBody.Builder()
                .add("name",name)
                .add("email",email)
                .add("telephone",phone)
                .add("password",password)
                .add("major",major)
                .add("department",department)
                .add("role","学生")
                .build();
        String result = OkHttpUtil.postForParams(url, body);
        Log.e(TAG, "register: "+result);
        return result;
    }

}
