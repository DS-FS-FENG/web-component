package com.mahosyojyo.webclient.impl;

import com.mahosyojyo.webclient.client.IRequest;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by FredFung on 2014/6/9.
 */
public class HttpRequest implements IRequest<HttpUriRequest> {

    //TODO 添加认证
    private HttpUriRequest excuteRequest;

    public HttpRequest(HttpUriRequest request) {
        excuteRequest = request;
    }

    @Override
    public HttpUriRequest getExecuteRequest() {
        return excuteRequest;
    }

    /**
     * 创建一个Http请求
     *
     * @param url
     *          目标地址
     * @param params
     *          post或者get的parameter( 注：get最多传递10个参数）
     * @param httpMethod
     *          POST、GET
     * @return HttpRequest
     */
    public static IRequest createRequest(String url, ArrayList<BasicNameValuePair> params, String httpMethod) {
        //TODO 添加Log

        if(httpMethod.equalsIgnoreCase(HttpPost.METHOD_NAME)) {
            //POST
            return createPost(url, params);
        } else if(httpMethod.equalsIgnoreCase(HttpGet.METHOD_NAME)) {
            return createGet(url, params);
        } else {
            //TODO define error and throw it
            return null;
        }
    }

    private static IRequest createPost(String url, ArrayList<BasicNameValuePair> params) {
        HttpPost post = new HttpPost(url);
        HttpEntity entity = null;
        if(null != params)
            entity = new UrlEncodedFormEntity(params, Consts.UTF_8);
        post.setEntity(entity);
        return new HttpRequest(post);
    }

    private static IRequest createGet(String url, ArrayList<BasicNameValuePair> params) {
        try {
            URIBuilder builder = new URIBuilder(url);

            if(null != params){
                if(params.size() > 10)
                    //TODO define Exception
                    throw new IllegalArgumentException("too much paramters");

                for (BasicNameValuePair param : params) {
                    builder.setParameter(param.getName(), param.getValue());
                }
            }
            URI uri = builder.build();
            HttpGet get = new HttpGet(uri);
            return new HttpRequest(get);
        } catch (URISyntaxException e) {
            //TODO define Exception
            e.printStackTrace();
            return null;
        }
    }
}
