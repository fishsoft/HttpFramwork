package com.morse.http.http;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

/**
 * Created by morse on 2018/1/20.
 */

public class HttpTask<T> implements Runnable {
    private IHttpService httpService;
    private IHttpListener httpListener;

    public <T> HttpTask(T requestInfo, String url, IHttpService service, IHttpListener listener) {
        httpService = service;
        httpListener = listener;
        httpService.setUrl(url);
        httpService.setHttpCallBack(listener);

        //吧请求信息对象转换成json格式到网络上进行发出去
        String requestContent = JSON.toJSONString(requestInfo);
        if (null != requestInfo) {
            try {
                httpService.setRequestData(requestContent.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        httpService.execute();
    }
}
