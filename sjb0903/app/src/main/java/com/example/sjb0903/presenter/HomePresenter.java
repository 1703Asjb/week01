package com.example.sjb0903.presenter;
/**
 * 作者：sjb
 * 时间：9.3
 * 作用：presenter
 */
import com.example.sjb0903.contract.IHomeContract;

import java.lang.ref.WeakReference;

public class HomePresenter implements IHomeContract.IPresenter {

    private WeakReference<IHomeContract.IVew> iVewWeakReference;
    private HomePresenter homePresenter;

    @Override
    public void attachView(IHomeContract.IVew view) {
        homePresenter = new HomePresenter();
        iVewWeakReference = new WeakReference<>(view);
    }
    public IHomeContract.IVew getView(){
        return iVewWeakReference.get();
    }
    @Override
    public void detachView() {
        if (iVewWeakReference!=null){
            iVewWeakReference.clear();
            iVewWeakReference=null;
        }
    }

    @Override
    public void getModel(String path) {
       getView().onDataSuccess(path);
       getView().onDataFalse("请求失败");
    }
}
