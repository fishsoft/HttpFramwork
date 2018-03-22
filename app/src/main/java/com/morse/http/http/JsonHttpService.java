package com.morse.http.http;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Handler;

/**
 * Created by morse on 2018/1/20.
 */

public class JsonHttpService implements IHttpService {

    private String url;
    private byte[] requestData;
    private IHttpListener httpListener;

    private HttpURLConnection urlConnection = null;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.requestData = requestData;
    }

    @Override
    public void execute() {
        httpUrlConnectionPost();

    }

    private void httpUrlConnectionPost() {
        URL url = null;
        try {
            url = new URL(this.url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(6000);
            urlConnection.setUseCaches(false);//不适用缓存
            urlConnection.setInstanceFollowRedirects(true);
            urlConnection.setReadTimeout(3000);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json;charset-utf-8");
            urlConnection.connect();
            OutputStream out = urlConnection.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out);
            if (requestData != null) {
                bos.write(requestData);
            }
            bos.flush();
            out.close();
            bos.close();
            if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStream in=urlConnection.getInputStream();
                httpListener.onSuccess(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            httpListener.onFailure();
        } catch (IOException e) {
            e.printStackTrace();
            httpListener.onFailure();
        }finally {
            urlConnection.disconnect();
        }
    }

    @Override
    public void setHttpCallBack(IHttpListener httpCallBack) {
        this.httpListener = httpCallBack;
    }
}
