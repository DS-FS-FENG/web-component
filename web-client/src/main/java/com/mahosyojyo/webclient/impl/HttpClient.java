package com.mahosyojyo.webclient.impl;

import com.mahosyojyo.webclient.client.IRequest;
import com.mahosyojyo.webclient.client.IResponse;
import com.mahosyojyo.webclient.client.IWebClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import java.io.IOException;

/**
 * Created by FredFung on 2014/6/9.
 */
public class HttpClient implements IWebClient {
    private static int MAXTOTALCONNECTIONS = 10;

    private static int CONNECTION_TIMEOUT_MS = 30 * 1000;
    private static int SOCKET_TIMEOUT_MS = 30 * 1000;

    private CloseableHttpClient mClient;
    private HttpClientContext localContext;

    public HttpClient() {
        prepareHttpClient();
        prepareContext();
    }

    private void prepareHttpClient() {
        PlainConnectionSocketFactory sf = PlainConnectionSocketFactory.getSocketFactory();
        SSLContext sslContext = SSLContexts.createSystemDefault();
        LayeredConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.STRICT_HOSTNAME_VERIFIER);
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", sf).register("https", sslsf).build();

        HttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);

        mClient = HttpClients.custom()
                .setMaxConnTotal(MAXTOTALCONNECTIONS)
                .setConnectionManager(cm)
                .build();

        //TODO add auth interceptor
    }

    private void prepareContext() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(SOCKET_TIMEOUT_MS)
                .setConnectTimeout(CONNECTION_TIMEOUT_MS)
                .build();

        localContext = HttpClientContext.create();
        localContext.setRequestConfig(requestConfig);
    }

    @Override
    public IResponse send(IRequest request) {
        if(!request.getClass().equals(HttpClient.class))
            throw new IllegalArgumentException("unexpected request class");

        try {
            return new HttpResponse(mClient.execute((HttpUriRequest)request.getExecuteRequest(), localContext));
        } catch (IOException e) {
            //TODO define error and throw it
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {
        try {
            mClient.close();
        } catch (IOException e) {
            //TODO define exception and throw it
            e.printStackTrace();
        }
    }
}
