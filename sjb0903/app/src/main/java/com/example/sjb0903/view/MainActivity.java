package com.example.sjb0903.view;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.bwei.xlistview.XlistView;
import com.example.sjb0903.R;
import com.example.sjb0903.adapter.MyAdapter;
import com.example.sjb0903.base.BaseActivity;
import com.example.sjb0903.bean.Goods;
import com.example.sjb0903.contract.IHomeContract;
import com.example.sjb0903.presenter.HomePresenter;
import com.example.sjb0903.utils.HttpUtil;
import com.google.gson.Gson;

import java.util.List;

/**
 * 作者：sjb
 * 时间：9.3
 * 作用：view
 */
public class MainActivity extends BaseActivity implements IHomeContract.IVew {

    private SharedPreferences sp;
    private HomePresenter homePresenter;
    private String path1="http://blog.zhaoliang5156.cn/api/shop/shop";
    private int count=1;
    private String path2=".json";
    private XlistView xlv;

    @Override
    protected void initData() {
        //判断网络
         if (HttpUtil.getInstance().isNetWork(MainActivity.this)){
             getJson(count);
             xlv.setPullLoadEnable(true);
             xlv.setPullRefreshEnable(true);
             xlv.setXListViewListener(new XlistView.IXListViewListener() {
                 @Override
                 public void onRefresh() {
                     getJson(1);
                     xlv.stopRefresh();
                 }

                 @Override
                 public void onLoadMore() {
                     getJson(count++);
                     xlv.stopLoadMore();
                 }
             });
         }else {
             //取sp
             String json = sp.getString("json", null);
             //解析
             Gson gson = new Gson();
             Goods goods = gson.fromJson(json, Goods.class);
             List<Goods.GoodsInfo> data = goods.getData();
             MyAdapter adapter = new MyAdapter(MainActivity.this, data);
             xlv.setAdapter(adapter);
         }
    }

    private void getJson(int count) {
        final String path=path1+count+path2;
        Log.i("xxx", "onSuccess: "+path);
        HttpUtil.getInstance().getJson(path, new HttpUtil.ICallBack() {
            @Override
            public void onSuccess(Object obj) {
                Log.i("xxx", "onSuccess: "+obj.toString());
                String json = obj.toString();
                //存sp
                sp = getSharedPreferences("config", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("json",obj.toString());
                edit.commit();
                //解析
                Gson gson = new Gson();
                Goods goods = gson.fromJson(json, Goods.class);
                List<Goods.GoodsInfo> data = goods.getData();
               MyAdapter adapter = new MyAdapter(MainActivity.this, data);
                //xlv.setAdapter(adapter);
                xlv.setAdapter(adapter);
            }

            @Override
            public void onFalse(String msg) {

            }
        });
    }

    @Override
    protected void initView() {
        xlv = findViewById(R.id.xlv);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onDataSuccess(Object obj) {
        Log.i("xxx", "onDataSuccess: "+obj.toString());
    }

    @Override
    public void onDataFalse(String msg) {

    }
    //销毁
    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.detachView();
    }
}
