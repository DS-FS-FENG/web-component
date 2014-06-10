package com.mahosyojyo.webclient.client;

/**
 * Created by FredFung on 2014/6/9.
 *
 * WebClient接口
 * send()：发送请求
 * close()：关闭Client
 */
public interface IWebClient {
    IResponse send(IRequest request);
    void close();
}
