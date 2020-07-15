package com.zk.mvp.mvp.contract;

import com.zk.mvp.base.Base;
import com.zk.mvp.base.BaseContract;
import com.zk.mvp.mvp.model.LoginModel;

/**
 * 登录契约接口：
 *      1、获取验证码
 *      2、登录
 */

public interface LoginContract {
    interface View extends BaseContract.BaseView{
        void loginSus(LoginModel loginModel);
    }
    interface Presenter extends BaseContract.BasePresenter<View>{
        void login(String mobile,String sign,String type,String password,String code);
    }
}
