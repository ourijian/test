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
import com.google.gson.JsonObject;
import com.ourijian.startschool.MyApplication;
import com.ourijian.startschool.R;
import com.ourijian.startschool.bean.StudentBean;
import com.ourijian.startschool.databinding.ActivityUpdateBinding;
import com.ourijian.startschool.util.OkHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;

public class UpdateActivity extends AppCompatActivity {

    private String update;
    ActivityUpdateBinding binding;
    private StudentBean.Student user;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_update);

        //获取要修改的属性
        Intent intent = getIntent();
        if(intent!=null){
            update = intent.getStringExtra("update");
        }
        //得到当前对象
        user = MyApplication.getUser(UpdateActivity.this);
        String update2Chinese = ""; //修改的属性的中文意思
        String oldValue = ""; //修改的属性 旧值

        switch (update){ //根据修改的属性，设置属性值
            case "name":
                update2Chinese = "姓名";
                oldValue = user.getName();
                break;
            case "department":
                update2Chinese = "部门";
                oldValue = user.getDepartment();
                break;
            case "major":
                update2Chinese = "专业";
                oldValue = user.getMajor();
                break;
            case "telephone":
                update2Chinese = "手机号";
                oldValue = user.getTelephone();
                break;
        }

        binding.tvHint.setText("提示，您的旧"+update2Chinese+"为"+oldValue);
        binding.toolbar.setTitle("修改"+update2Chinese);
        binding.etUpdate.setHint("请输入您的新"+update2Chinese);

        //保存按钮被点击
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框并非空判断
                final String newValue = binding.etUpdate.getText().toString().trim();
                if(TextUtils.isEmpty(newValue)){
                    Toast.makeText(UpdateActivity.this, "没有填写新数据", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String result = update(newValue);
                            Gson gson = new Gson();
                            final StudentBean studentBean = gson.fromJson(result, StudentBean.class);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(studentBean.isSuccess()){
                                        Toast.makeText(UpdateActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                        SharedPreferences sp = getSharedPreferences("user.db",MODE_PRIVATE);
                                        boolean isRemember = sp.getBoolean("isRemember", false);
                                        MyApplication.saveUser(studentBean.getData(),UpdateActivity.this,isRemember);
                                    }else{
                                        Toast.makeText(UpdateActivity.this, "修改失败"+result, Toast.LENGTH_SHORT).show();
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

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * 请求修改数据
     * @param newValue
     */
    private String update(String newValue) throws IOException {
        //根据用户修改的属性保存到内存
        switch (update){
            case "name":
                user.setName(newValue);
                break;
            case "department":
                user.setDepartment(newValue);
                break;
            case "major":
                user.setMajor(newValue);
                break;
            case "telephone":
                user.setTelephone(newValue);
                break;
        }

        String url = "http://"+getResources().getString(R.string.ip)+"/freshmenserver/user/update";
        FormBody body = new FormBody.Builder()
                .add("id",user.getId()+"")
                .add("name",user.getName())
                .add("password",user.getPassword())
                .add("email",user.getEmail())
                .add("telephone",user.getTelephone())
                .add("department",user.getDepartment())
                .add("major",user.getMajor())
                .add("role",user.getRole())
                .build();

        String result = OkHttpUtil.postForParams(url, body);
        return result;
    }

}
