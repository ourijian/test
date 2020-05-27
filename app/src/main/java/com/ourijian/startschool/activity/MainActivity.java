package com.ourijian.startschool.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ourijian.startschool.R;
import com.ourijian.startschool.databinding.ActivityMainBinding;
import com.ourijian.startschool.fragment.AnnouncementFragment;
import com.ourijian.startschool.fragment.ClassmateFragment;
import com.ourijian.startschool.fragment.LeavewordFragment;
import com.ourijian.startschool.fragment.MeFragment;
import com.ourijian.startschool.fragment.NewsFragment;

public class MainActivity extends AppCompatActivity {

    public ActivityMainBinding binding;
    public Fragment [] fragments;
    RadioButton [] radioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        //Fragment数组
        ClassmateFragment classmateFragment = new ClassmateFragment();
        LeavewordFragment leavewordFragment = new LeavewordFragment();
        NewsFragment newsFragment = new NewsFragment();
        AnnouncementFragment announcementFragment = new AnnouncementFragment();
        MeFragment meFragment = new MeFragment();
        fragments = new Fragment[]{classmateFragment,leavewordFragment,newsFragment,announcementFragment,meFragment};

        //单选按钮数组 顺序与Fragment对应
        radioButtons = new RadioButton[]{binding.rbClassmate,binding.rbLeaveword,binding.rbNews,binding.rbAnnouncement,binding.rbMe};

        //设置适配器
        binding.viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        //单选按钮改变显示指定的Fragment
        binding.rgNav.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_classmate:
                        binding.viewPager.setCurrentItem(0,true);
                        binding.toolbar.setTitle("同学");
                        break;
                    case R.id.rb_leaveword:
                        binding.toolbar.setTitle("留言");
                        binding.viewPager.setCurrentItem(1,true);
                        break;
                    case R.id.rb_news:
                        binding.viewPager.setCurrentItem(2,true);
                        binding.toolbar.setTitle("新闻");
                        break;
                    case R.id.rb_announcement:
                        binding.toolbar.setTitle("通知公告");
                        binding.viewPager.setCurrentItem(3,true);
                        break;
                    case R.id.rb_me:
                        binding.toolbar.setTitle("我的");
                        binding.viewPager.setCurrentItem(4,true);
                        break;
                }
            }
        });

        //设置默认选中news单选按钮
        binding.rbNews.setChecked(true);

        //ViewPager内容改变事件
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            //当界面发生改变时的回调
            public void onPageSelected(int position) {
                //修改对应单选按钮为check
                radioButtons[position].setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * ViewPager适配器
     */
    public class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }


}
