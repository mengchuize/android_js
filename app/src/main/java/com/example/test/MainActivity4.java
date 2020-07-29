package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;

public class MainActivity4 extends AppCompatActivity {
    private EditText et;
    private Button bt,btsend;
    private BridgeWebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        et = (EditText) findViewById(R.id.et);
        bt = (Button) findViewById(R.id.bt);
        btsend = (Button) findViewById(R.id.btsend);
        webview = (BridgeWebView)findViewById(R.id.webview);
        webview.setDefaultHandler(new DefaultHandler());
        webview.setWebChromeClient(new WebChromeClient());
        webview.loadUrl("file:///android_asset/test4.html");
//      注册监听方法当js中调用callHandler方法时会调用此方法（handlerName必须和js中相同）
        webview.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示js传递给Android的消息
                Toast.makeText(MainActivity4.this, "js正在调用android方法:" + data, Toast.LENGTH_LONG).show();
                //Android返回给JS的消息
                function.onCallBack("我是js调用Android返回数据：" + et.getText().toString());
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webview.callHandler("functionInJs", "android调用js方法：functionInJs()", new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        Toast.makeText(MainActivity4.this,"js的返回值："+data, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webview.send("这是android通过send发过来的信息",new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        System.out.println("android在send后，获取到的返回值："+data);
                    }
                });
            }
        });
    }
}