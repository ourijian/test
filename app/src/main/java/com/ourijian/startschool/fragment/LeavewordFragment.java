package com.ourijian.startschool.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ourijian.startschool.MyApplication;
import com.ourijian.startschool.R;
import com.ourijian.startschool.bean.LeaveWordBean;
import com.ourijian.startschool.bean.StudentBean;
import com.ourijian.startschool.util.OkHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.FormBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeavewordFragment extends Fragment {

    List<LeaveWordBean.DataBean> dataBeans = new ArrayList<LeaveWordBean.DataBean>();
    private StudentBean.Student user;
    private ListView lvWord;
    private MyAdapter adapter;
    private EditText etMsg;

    public LeavewordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaveword, container, false);

        lvWord = view.findViewById(R.id.lv_leaveword);
        etMsg = view.findViewById(R.id.et_leaveword);
        Button btnSend = view.findViewById(R.id.btn_send);
        user = MyApplication.getUser(getActivity());

        //查询留言列表
        initData();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etMsg.getText().toString().trim();
                if(TextUtils.isEmpty(content)){
                    Toast.makeText(getActivity(), "留言的信息不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendLeaveword(content);
            }
        });


        return view;
    }

    /**
     * 将参数日期转为指定格式字符串
     * @param date
     * @return
     */
    private String date2String(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 添加留言
     * @param content
     */
    private void sendLeaveword(String content) {
        final String url = "http://"+getResources().getString(R.string.ip)+"/freshmenserver/messages/add";
        final FormBody body = new FormBody.Builder()
                .add("content",content)
                .add("date",date2String(new Date()))
                .add("userid",user.getId()+"")
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = OkHttpUtil.postForParams(url, body);
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.optBoolean("success")){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "发表成功", Toast.LENGTH_SHORT).show();
                                initData();
                                etMsg.setText("");
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    /**
     * 请求留言列表
     */
    private void initData() {
        final String url = "http://192.168.56.1:8080/freshmenserver/messages/list";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String result = OkHttpUtil.postForParams(url, new FormBody.Builder().build());
                    Gson gson = new Gson();
                    final LeaveWordBean leaveWordBean = gson.fromJson(result, LeaveWordBean.class);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(leaveWordBean.isSuccess()){
                                //设置listView的适配器
                                dataBeans = leaveWordBean.getData();
                                adapter = new MyAdapter();
                                lvWord.setAdapter(adapter);
                                lvWord.setSelection(dataBeans.size()-1);
                            }else{
                                Toast.makeText(getActivity(), "请求失败"+result, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return dataBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return dataBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return dataBeans.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if(convertView == null){
                convertView = View.inflate(getActivity(),R.layout.leaveword_item,null);
                holder = new Holder();
                holder.tvName = convertView.findViewById(R.id.tv_name);
                holder.tvData = convertView.findViewById(R.id.tv_date);
                holder.tvContent = convertView.findViewById(R.id.tv_content);
                holder.tvMy = convertView.findViewById(R.id.tv_my);
                convertView.setTag(holder);
            }else{
                holder = (Holder) convertView.getTag();
            }

            holder.tvData.setText(dataBeans.get(position).getDate());

            //如果单前评论是自己曾评论过的
            if(dataBeans.get(position).getUser().getId()==user.getId()){
                //右边的TextView显示
                holder.tvMy.setVisibility(View.VISIBLE);
                holder.tvMy.setText(dataBeans.get(position).getUser().getName());
                //隐藏左边的TextView
                holder.tvName.setVisibility(View.GONE);
                holder.tvContent.setBackgroundResource(R.drawable.chat_right_bg_normal);
            }else{
                holder.tvName.setText(dataBeans.get(position).getUser().getName());
            }
            holder.tvContent.setText(dataBeans.get(position).getContent());

            return convertView;

        }

        class Holder {
            TextView tvName,tvData,tvContent,tvMy;
        }

    }

}
