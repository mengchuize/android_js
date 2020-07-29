package com.example.test;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class MainActivity extends Activity {
    private WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //方法1：WebView的loadUrl（）
        //JS代码调用一定要在 onPageFinished（） 回调之后才能调用，否则不会调用。
        //onPageFinished()属于WebViewClient类的方法，主要在页面加载结束时调用
        mWebView = (WebView) findViewById(R.id.webview);
        // 启用javascript
        mWebView.getSettings().setJavaScriptEnabled(true);
        // 从assets目录下面的加载html
        mWebView.loadUrl("file:///android_asset/test.html");
        mWebView.addJavascriptInterface(this, "test");
        Button button = (Button) findViewById(R.id.button);
        Button buttonWithParam = (Button) findViewById(R.id.buttonWithParam);
        button.setOnClickListener(new Button.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(View v) {
                // 无参数调用
                //获取js返回结果，效率高，android4.4以上
                final int version = Build.VERSION.SDK_INT;
                if (version < 18) {

                    // ui线程中运行
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mWebView.loadUrl("javascript:actionFromAndroid()");
                        }
                    });

                } else {
                    mWebView.evaluateJavascript("javascript:actionFromAndroid()", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            //此处为 js 返回的结果
                            Toast.makeText(MainActivity.this, "js返回值："+value, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        buttonWithParam.setOnClickListener(new Button.OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                // 传递参数调用
                //获取js返回结果，效率高，android4.4以上
                final int version = Build.VERSION.SDK_INT;
                if (version < 18) {
                    mWebView.loadUrl("javascript:actionFromAndroidWithParam(" + "'come from android'" + ")");
                } else {
                    mWebView.evaluateJavascript("javascript:actionFromAndroidWithParam(" + "'come from android'" + ")", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            //此处为 js 返回的结果
                            Toast.makeText(MainActivity.this, "js返回值："+value, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    @JavascriptInterface
    public void actionFromJs() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "js调用了android函数", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @JavascriptInterface
    public void actionFromJsWithParam(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "js调用了android函数传递参数：" + str, Toast.LENGTH_SHORT).show();

            }
        });

    }
}