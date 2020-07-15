package com.zk.mvp.http;

import android.util.Log;

import com.zk.mvp.base.BaseContract;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class RxPresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {
    private static final String TAG = "RxPresenter";
    protected T mView;
    //观察者订阅管理对象
    protected CompositeSubscription mCompositeSubscription;
    //订阅者
    private Subscription subscription;

    /**
     * 取消订阅
     */
    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * 添加订阅
     * @param subscription
     */
    protected void addSubscribe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        this.subscription = subscription;
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }

    public void cancelCurrent(){
        if (subscription == null) {
            return;
        }
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            Log.i(TAG, "cancelCurrent: 您取消了一个订阅");
        }
    }
}
