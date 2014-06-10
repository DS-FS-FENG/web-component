package com.mahosyojyo.webclient.impl;

import com.mahosyojyo.webclient.client.IResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by FredFung on 2014/6/9.
 */
public class HttpResponse implements IResponse {
    private CloseableHttpResponse response;

    public HttpResponse(CloseableHttpResponse response) {
        this.response = response;
    }

    @Override
    public InputStream toStream() {
        return null;
    }

    @Override
    public JSONArray toJSonArray() {
        return null;
    }

    @Override
    public JSONObject toJSonObject() {
        return null;
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }
}
