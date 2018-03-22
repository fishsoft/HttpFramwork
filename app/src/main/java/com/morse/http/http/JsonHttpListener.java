package com.morse.http.http;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by morse on 2018/3/22.
 */

public class JsonHttpListener<M> implements IHttpListener {

    Class<M> responseClass;
    IDataListener<M> dataListener;
    Handler handler=new Handler(Looper.getMainLooper());

    public JsonHttpListener(Class<M> responseClass, IDataListener<M> dataListener) {
        this.responseClass = responseClass;
        this.dataListener = dataListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        String content = getContent(inputStream);
        final M response = JSON.parseObject(content, responseClass);
        handler.post(new Runnable() {
            @Override
            public void run() {
                dataListener.onSucess(response);
            }
        });
    }

    private String getContent(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error=" + e.toString());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    @Override
    public void onFailure() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailure();
            }
        });
    }
}
