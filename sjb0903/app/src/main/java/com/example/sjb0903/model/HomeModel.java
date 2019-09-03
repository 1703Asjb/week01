package com.example.sjb0903.model;
/**
 * 作者：sjb
 * 时间：9.3
 * 作用：model
 */


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.sjb0903.app.App;
import com.example.sjb0903.contract.IHomeContract;
import com.example.sjb0903.utils.HttpUtil;

public class HomeModel implements IHomeContract.IModel {
    @Override
    public void getData(String path, final IHomeCallBack iHomeCallBack) {
        boolean b = HttpUtil.getInstance().isNetWork(App.context);
        if (b){
            HttpUtil.getInstance().getJson(path, new HttpUtil.ICallBack() {

                private SharedPreferences.Editor edit;
                private SharedPreferences sp;

                @Override
                public void onSuccess(Object obj) {
                    iHomeCallBack.onHomeSuccess(obj.toString());
                    Log.i("xxx", "onSuccess: "+obj.toString());
                    //存sp
                    sp = App.context.getSharedPreferences("config", Context.MODE_PRIVATE);
                    edit = sp.edit();
                    edit.putString("json",obj.toString());
                    edit.commit();
                }

                @Override
                public void onFalse(String msg) {
                    //取sp
                    String json = sp.getString("json", null);
                    iHomeCallBack.onHomeFalse(json);
                }
            });
        }
    }
}
