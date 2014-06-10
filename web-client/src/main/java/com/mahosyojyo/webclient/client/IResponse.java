package com.mahosyojyo.webclient.client;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by FredFung on 2014/6/9.
 */
public interface IResponse {
    InputStream toStream();
    JSONArray toJSonArray();
    JSONObject toJSonObject();
    byte[] toBytes();
}
