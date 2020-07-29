package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.HashMap;
import java.util.Set;

public class MainActivity3 extends AppCompatActivity {

    WebView mWebView;
//  Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 先加载JS代码
        mWebView.loadUrl("file:///android_asset/test3.html");

        mWebView.setWebChromeClient(new WebChromeClient() {

            // 拦截JS的警告框
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Uri uri = Uri.parse(message);
                System.out.println("uri:"+uri.toString());
                return super.onJsAlert(view, url, message, result);
            }

            // 拦截JS的确认框
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            // 拦截JS的输入框
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
    }
}


