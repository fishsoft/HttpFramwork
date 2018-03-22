package com.morse.http.http;

import java.io.InputStream;

/**
 * 处理响应结果
 * Created by morse on 2018/1/20.
 */

public interface IHttpListener {
    //接受上一个接口的结果
    void onSuccess(InputStream inputStream);

    void onFailure();
}
