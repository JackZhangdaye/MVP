package com.zk.mvp.http;

import com.zk.mvp.base.Base;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;

public abstract class ObserverImp<T> implements Observer<T> {
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;
    private static final int ERR_CODE_NET = 0x110;
    private static final int ERR_CODE_UNKNOWN = 0x111;
    private static final int ERR_CODE_LOGIC = 0x112;
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Throwable throwable = e;
        //获取最根源的异常
        while(throwable.getCause() != null){
            if (e instanceof HttpException) {
                break;
            }
            e = throwable;
            throwable = throwable.getCause();
        }
        if (e instanceof HttpException){
            HttpException httpException = (HttpException) e;
            switch(httpException.code()){
                case UNAUTHORIZED:
                    onErr(UNAUTHORIZED,"");
                    break;
                case FORBIDDEN:
                    onErr(FORBIDDEN,"权限错误");
                    break;
                case NOT_FOUND:
                    onErr(NOT_FOUND,"");
                    break;
                case REQUEST_TIMEOUT:
                    onErr(REQUEST_TIMEOUT,"");
                    break;
                case GATEWAY_TIMEOUT:
                    onErr(GATEWAY_TIMEOUT,"");
                    break;
                case INTERNAL_SERVER_ERROR:
                    onErr(INTERNAL_SERVER_ERROR,"");
                    break;
                case BAD_GATEWAY:
                    onErr(BAD_GATEWAY,"");
                    break;
                case SERVICE_UNAVAILABLE:
                    onErr(SERVICE_UNAVAILABLE,"");
                    break;
                default:
                    onErr(ERR_CODE_NET,"");
                    break;
            }
        }else if (e instanceof SocketTimeoutException){
            onErr(GATEWAY_TIMEOUT,"请求超时!");
        }else if(e instanceof UnknownHostException){
            onErr(ERR_CODE_NET,"网络连接失败!");
        }else{
            onErr(ERR_CODE_UNKNOWN,"逻辑错误!");
        }
    }

    @Override
    public void onNext(T t) {
        Base base  = (Base) t;
        if (base.getCode() == 200) {
            doNext(t);
        }else{//网络接口内部逻辑出错
            onErr(ERR_CODE_LOGIC,base.getMessage());
        }
    }

    /**
     * 出错回调
     * @param errCode
     * @param str
     */
    protected abstract void onErr(int errCode, String str);

    /**
     * 在已经实现了接口业务逻辑出错判断后开始进行后面的流程
     * @see #onNext(T t)
     * @param t
     */
    protected abstract void doNext(T t);
}
