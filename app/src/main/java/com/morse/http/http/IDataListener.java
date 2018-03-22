package com.morse.http.http;

/**
 * 用于把结果回调给调用层
 * Created by morse on 2018/1/20.
 */

public interface IDataListener<M> {
    void onSucess(M m);
    void onFailure();
}
