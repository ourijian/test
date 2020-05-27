package com.ourijian.startschool;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.ourijian.startschool.bean.StudentBean;

public class MyApplication extends Application {

    /**
     * 保存当前登录用户
     * @param student
     * @param context
     * @param isRemember 是否记住用户名和密码
     */
    public static void saveUser(StudentBean.Student student, Context context,boolean isRemember){
        SharedPreferences sp = context.getSharedPreferences("user.db",MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("name",student.getName());
        edit.putString("telephone",student.getTelephone());
        edit.putInt("id",student.getId());
        edit.putString("email",student.getEmail());
        edit.putString("department",student.getDepartment());
        edit.putString("major",student.getMajor());
        edit.putString("role",student.getRole());
        edit.putString("password",student.getPassword());
        //是否记住密码
        if(isRemember){
            edit.putBoolean("isRemember",true);
        }else{
            edit.putBoolean("isRemember",false);
        }
        edit.commit();
    }

    /**
     * 得到当前登录的用户
     * @param context
     * @return
     */
    public static StudentBean.Student getUser(Context context){
        SharedPreferences sp = context.getSharedPreferences("user.db",MODE_PRIVATE);
        StudentBean.Student student = new StudentBean.Student();
        student.setName(sp.getString("name", ""));
        student.setTelephone(sp.getString("telephone", ""));
        student.setPassword(sp.getString("password",""));
        student.setId(sp.getInt("id",0));
        student.setDepartment(sp.getString("department",""));
        student.setEmail(sp.getString("email",""));
        student.setMajor(sp.getString("major",""));
        student.setRole(sp.getString("role",""));
        return student;
    }
}
