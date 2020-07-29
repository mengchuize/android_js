package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Set;

public class MainActivity2 extends AppCompatActivity {

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //加载JS代码
        mWebView.loadUrl("file:///android_asset/test2.html");

        // 复写WebViewClient类的shouldOverrideUrlLoading方法
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

              Uri uri = Uri.parse(url);
              // 按照预先设定好的协议解析url
              if ( uri.getScheme().equals("js")) {
                  if (uri.getAuthority().equals("webview")) {
                      // 可以在协议上带有参数并传递到Android上
                      Set<String> collection = uri.getQueryParameterNames();
                      String arg1=null;
                      String arg2=null;
                      for (String value : collection) {
                         if (value.equals("arg1")){
                             arg1 = uri.getQueryParameter("arg1");
                         }else if (value.equals("arg2")){
                             arg2 = uri.getQueryParameter("arg2");
                         }
                      }
                      // 执行JS所需要调用的逻辑
                      Toast.makeText(MainActivity2.this, "js调用android方法，传参为："+arg1+"&"+arg2, Toast.LENGTH_SHORT).show();

                  }

                  return true;
              }
              return super.shouldOverrideUrlLoading(view, url);
            }
            }
        );
    }
}

