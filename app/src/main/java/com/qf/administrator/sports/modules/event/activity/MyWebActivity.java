package com.qf.administrator.sports.modules.event.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.qf.administrator.sports.R;

/**
 * Created by Chigo on 10/14/2016.
 */

public class MyWebActivity extends AppCompatActivity {
    private TextView tvTitle;

    private View progressBar;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_myweb);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        progressBar = findViewById(R.id.progress_bar);
        webView = (WebView) findViewById(R.id.webview);
//        webView.getSettings().setJavaScriptEnabled(true);// 设置js可用
        initWeb();//初始化webview

        Intent intent=getIntent();
       String url=intent.getStringExtra("url");
        String title=intent.getStringExtra("title");
       if (title!=null){
           tvTitle.setText(title);
       }
        webView.loadUrl(url);
    }

    private void initWeb() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //开始加载页面的回调
                Log.i("info", "结束加载=" + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //结束加载页面的回调
                Log.i("info", "结束加载=" + url);
                setProgress(0.0f);
            }
        });


        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                //返回加载的进度
                setProgress(newProgress * 1.0f / 100);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
//                tvTitle.setText(title);
            }
        });
    }


    /**
     * 设置progressBar的宽度
     *
     * @param progress
     */
    private void setProgress(float progress) {
        int width = webView.getWidth();
        ViewGroup.LayoutParams params = progressBar.getLayoutParams();
        params.width = (int) (progress * width);
        progressBar.setLayoutParams(params);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //当物理返回被点击
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //如果是有上一个页面
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}