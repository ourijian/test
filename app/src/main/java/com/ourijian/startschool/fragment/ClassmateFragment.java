package com.ourijian.startschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.ourijian.startschool.MyApplication;
import com.ourijian.startschool.R;
import com.ourijian.startschool.activity.ClassmateMsgActivity;
import com.ourijian.startschool.activity.MainActivity;
import com.ourijian.startschool.bean.ClassmateBean;
import com.ourijian.startschool.bean.StudentBean;
import com.ourijian.startschool.util.OkHttpUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;

public class ClassmateFragment extends Fragment {

    private static final String TAG = "ClassmateFragment";
    private List<ClassmateBean.Student> students;
    private StudentBean.Student user;
    private ListView lvClassMate;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classmate, container, false);
        user = MyApplication.getUser(getActivity());//单前对象

        TextView tvDepartment = view.findViewById(R.id.tv_department);
        TextView tvMajor = view.findViewById(R.id.tv_major);
        lvClassMate = view.findViewById(R.id.lv_classmate);

        tvDepartment.setText(user.getDepartment());
        tvMajor.setText(user.getMajor());

        //请求获取同班的同学的数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String result = postData();
                    Gson gson = new Gson();
                    final ClassmateBean classmateBean = gson.fromJson(result, ClassmateBean.class);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(classmateBean.isSuccess()){ //如果请求成功
                                //如果返回有数据
                                if(classmateBean.getData()!=null && classmateBean.getData().size()>0){
                                    //listView设置适配器
                                    students = classmateBean.getData();
                                    MyAdapter adapter = new MyAdapter();
                                    lvClassMate.setAdapter(adapter);
                                }else{//没有数据
                                    Toast.makeText(getActivity(), "没有数据", Toast.LENGTH_SHORT).show();
                                }
                            }else{ //请求失败
                                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //listView中点击某一项时
        lvClassMate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ClassmateMsgActivity.class);
                intent.putExtra("id",students.get(position).getId());
                getActivity().startActivity(intent);
            }
        });

        return view;
    }

    /**
     * 请求一个同部门同班级的同学数据
     */
    private String postData() throws IOException {
        String url = "http://"+getResources().getString(R.string.ip)+"/freshmenserver/classmate/list";
        FormBody body = new FormBody.Builder()
                .add("department",user.getDepartment())
                .add("major",user.getMajor())
                .build();
        String result = OkHttpUtil.postForParams(url, body);
        return result;
    }

    /**
     * listView适配器
     */
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return students.size();
        }

        @Override
        public Object getItem(int position) {
            return students.get(position);
        }

        @Override
        public long getItemId(int position) {
            return students.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if(convertView == null){
                convertView = View.inflate(getActivity(),R.layout.classmate_item,null);
                holder = new Holder();
                holder.tvName = convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            }else{
                holder = (Holder) convertView.getTag();
            }
            holder.tvName.setText(students.get(position).getName());
            return convertView;
        }

        class Holder{
            TextView tvName;
        }

    }

}
