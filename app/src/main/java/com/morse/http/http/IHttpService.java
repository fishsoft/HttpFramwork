package com.morse.http.http;

/**
 * Created by morse on 2018/1/20.
 * 处理请求
 */

public interface IHttpService {

    void setUrl(String url);
    void setRequestData(byte[] requestData);
    void execute();
    //需要设置两个接口之间的关系
    void setHttpCallBack(IHttpListener httpCallBack);
}
