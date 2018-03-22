package com.morse.http.http;

/**
 * Created by morse on 2018/3/22.
 */

public class JettNet {

    public static <T,M> void sendJsonRequest(T requestInfo,String url,Class<M> response,IDataListener<M> dataListener){
        IHttpService httpService=new JsonHttpService();
        IHttpListener httpListener=new JsonHttpListener<>(response,dataListener);
        HttpTask<T> httpTask=new HttpTask<T>(requestInfo,url,httpService,httpListener);
        ThreadPoolManager.getInstance().execute(httpTask);
    }

    public static void upload(){

    }

}
