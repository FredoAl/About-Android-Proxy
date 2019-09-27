package com.example.testproxysetting;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    TextView state, info;
    Button proxyBtn, viewBtn;
    public String attend = "https://api.ip.pe.kr/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        state = findViewById(R.id.state);
        info = findViewById(R.id.info);
        proxyBtn = findViewById(R.id.proxyBtn);
        viewBtn = findViewById(R.id.viewBtn);

        proxyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        final URL website;
                        try {

                            website = new URL(attend); // 1
                            // Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("41.73.15.130", 8080)); // 1
                            HttpsURLConnection httpURLConnection = (HttpsURLConnection) website.openConnection(); // 1
                            // httpURLConnection.connect(); // 1
                            httpURLConnection.setDoOutput(true);
                            httpURLConnection.setRequestMethod("GET");
                            info.append(httpURLConnection.getResponseMessage());

                            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                            String inputLine;
                            while((inputLine = br.readLine()) != null){

                              info.append(inputLine);

                            }

                            br.close();

                        } catch (
                                MalformedURLException e) {
                            e.printStackTrace();
                        } catch (
                                IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });


        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
