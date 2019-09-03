package com.example.sjb0903.contract;

public interface IHomeContract {
    interface IVew{
        void onDataSuccess(Object obj);
        void onDataFalse(String msg);
    }
    interface IModel{
        void getData(String path,IHomeCallBack iHomeCallBack);
        interface IHomeCallBack{
            void onHomeSuccess(Object obj);
            void onHomeFalse(String msg);
        }
    }
    interface IPresenter{
        void attachView(IHomeContract.IVew view);
        void detachView();
        void getModel(String path);
    }
}
