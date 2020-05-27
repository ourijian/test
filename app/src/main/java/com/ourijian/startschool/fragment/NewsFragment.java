package com.ourijian.startschool.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.ourijian.startschool.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {


    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        final WebView wvNews = view.findViewById(R.id.wv_news);
        wvNews.loadUrl("https://news.sina.cn/zt_d/yiqing0121");
        WebSettings settings = wvNews.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        wvNews.setWebChromeClient(new WebChromeClient());

        wvNews.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK){
                    if(wvNews.canGoBack()){
                        wvNews.goBack();
                        return true;
                    }
                }
                return false;
            }
        });

        return view;
    }
}
