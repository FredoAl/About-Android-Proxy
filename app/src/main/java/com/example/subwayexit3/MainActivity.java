package com.example.subwayexit3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private WebView exitView3;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exitView3 = (WebView)findViewById(R.id.exitView3);
        url = getIntent().getStringExtra("url");

        final URL website;
        try {
            if(url.startsWith("http://")){ }
            else if(url.startsWith("https://")) { }
            else{
                url = "http://" + url;
            }
            website = new URL(url); // 1
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.0.63", 8765)); // 1
            //  * Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8118));
            System.setProperty("http.proxyHost", "192.168.0.63"); // 2          * System.setProperty("http.proxyHost", "127.0.0.1");
            System.setProperty("http.proxyPort", "8765"); // 2                  * System.setProperty("http.proxyPort", "8118");
            HttpURLConnection httpURLConnection = (HttpURLConnection)website.openConnection(proxy); // 1
            httpURLConnection.connect(); // 1

            /*  1 = 1번 방법 , 2 = 2번 방법 , * = Tor
                    프록시를 설정하는 방법 두가지를 다 써놓고 되나 테스트 했었음.
            1. 1번 방법을 사용해서 프록시를 연결하였을때 앱상에서 어떻게 연결이 되었나 확인 할 수 있는가? .connect() 를 하면 앱에서는 어떻게 되는 것인가?
            2. 2번 방법도 어떻게 되는지, 되는건 맞는건지 찾아보면 모바일에서 사용했다기 보다 컴퓨터에서 자바 코딩할때 더 쓰였던 방법인듯 함.
            3. network_security_config 파일을 수정 할 점이 있는지.
                3 - 1.  지금 상태로 구동하면 웹뷰상에서 claertextTraffic 에러가 일어나는 남. network_security_config 파일때문이 아닌가 추측.

             */

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        exitView3.getSettings().setJavaScriptEnabled(true);
        exitView3.setWebChromeClient(new WebChromeClient());
        exitView3.setWebViewClient(new WebViewClientClass());
        exitView3.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK)&&exitView3.canGoBack()){
            exitView3.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
