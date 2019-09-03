package com.example.sjb0903.utils;
/**
 * 作者：sjb
 * 时间：9.3
 * 作用：util
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {
    //
    private static HttpUtil httpUtil=null;
    private HttpUtil(){};
    public static HttpUtil getInstance(){
        if (httpUtil == null) {
            synchronized (HttpUtil.class){
                if (httpUtil == null){
                    httpUtil=new HttpUtil();
                }
            }
        }return httpUtil;
    }
    //判断网络
    public boolean isNetWork(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info.isAvailable();
    }
    //接口回调
    public interface ICallBack{
        void onSuccess(Object obj);
        void onFalse(String msg);
    }
    public void getJson(final String path, final ICallBack iCallBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);
                    connection.setRequestMethod("GET");
                    int code = connection.getResponseCode();
                    if (code==200){
                        InputStream stream = connection.getInputStream();
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] bytes = new byte[1024];
                        int len=0;
                        while ((len=stream.read(bytes))!=-1){
                            bos.write(bytes,0,len);
                        }
                        stream.close();
                        bos.close();
                        if (iCallBack != null) {
                            iCallBack.onSuccess(bos.toString());
                        }
                    }else {
                        iCallBack.onFalse("请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
