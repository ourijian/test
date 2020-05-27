package com.ourijian.startschool.fragment;


import android.graphics.RenderNode;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ourijian.startschool.R;
import com.ourijian.startschool.bean.AnnouncementBean;
import com.ourijian.startschool.util.OkHttpUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnnouncementFragment extends Fragment {

    private List<AnnouncementBean.Announcement> announcements;
    private ListView lvAnnouncement;

    public AnnouncementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_announcement, null);

        lvAnnouncement = view.findViewById(R.id.lv_announcement);

        //获取数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }).start();

        return view;
    }

    //请求公告列表数据
    private void initData() {
        String url = "http://"+getResources().getString(R.string.ip)+"/freshmenserver/announcement/list";
        try {
            final String result = OkHttpUtil.postForParams(url, new FormBody.Builder().build());
            Gson gson = new Gson();
            final AnnouncementBean announcementBean = gson.fromJson(result, AnnouncementBean.class);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(announcementBean.isSuccess()){

                        announcements = announcementBean.getData();
                        MyAdapter adapter = new MyAdapter();
                        lvAnnouncement.setAdapter(adapter);
                    }else{
                        Toast.makeText(getActivity(), "请求错误"+result, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return announcements.size();
        }

        @Override
        public Object getItem(int position) {
            return announcements.get(position);
        }

        @Override
        public long getItemId(int position) {
            return announcements.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if(convertView == null){
                convertView = View.inflate(getActivity(),R.layout.announcement_item,null);
                holder = new Holder();
                holder.tvTitle = convertView.findViewById(R.id.tv_title);
                holder.tvDate = convertView.findViewById(R.id.tv_date);
                holder.tvDesc = convertView.findViewById(R.id.tv_desc);
                convertView.setTag(holder);
            }else{
                holder = (Holder) convertView.getTag();
            }
            holder.tvTitle.setText(announcements.get(position).getTitle());
            holder.tvDesc.setText(announcements.get(position).getDescription());
            holder.tvDate.setText(announcements.get(position).getDate());

            return convertView;
        }


        class Holder{
            TextView tvDate,tvTitle,tvDesc;
        }

    }

}
