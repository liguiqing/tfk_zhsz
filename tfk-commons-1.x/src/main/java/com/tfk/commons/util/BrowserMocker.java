package com.tfk.commons.util;

import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.lang.Closer;
import com.tfk.commons.lang.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.List;

/**
 * 浏览器访问行为模拟
 *
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
public class BrowserMocker implements  AutoCloseable{
    private CloseableHttpClient client;

    private RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();

    private CookieStore cookieStore = new BasicCookieStore();

    private HttpClientContext context = HttpClientContext.create();

    protected BrowserMocker() {
        setContextCookieStore();
        HttpClientBuilder httpClientBuilder = HttpClients.custom()
                .setKeepAliveStrategy(createKeepAliveStrategy())
                .setRetryHandler(createRetryHandler())
                .setDefaultRequestConfig(globalConfig)
                .setDefaultCookieStore(cookieStore);
        client = httpClientBuilder.build();
    }

    public HttpRequestResult execute(HttpUriRequest httpUriRequest) {
        HttpRequestResult requestResult = new HttpRequestResult();
        CloseableHttpResponse response = null;
        try {
            printCookie();
            response = client.execute(httpUriRequest, context);
            AssertionConcerns.assertArgumentEquals(404,response.getStatusLine().getStatusCode(),
                    "地址无法访问:" + httpUriRequest.getURI().toString());
            printResponse(response);
            HttpEntity httpEntity = response.getEntity();
            String content = EntityUtils.toString(httpEntity, Consts.UTF_8);
            requestResult.setContent(content);
            EntityUtils.consume(httpEntity);
        } catch (Exception e) {
            log.error("地址访问失败:", Throwables.toString(e));
            throw new RuntimeException("地址访问失败" + httpUriRequest.getURI().toString());
        } finally {
            Closer.close(response);
        }
        return requestResult;
    }

    private void setContextCookieStore() {
        context.setCookieStore(cookieStore);
    }

    private HttpRequestRetryHandler createRetryHandler() {
        return new StandardHttpRequestRetryHandler(3, false);
    }

    private ConnectionKeepAliveStrategy createKeepAliveStrategy() {
        return  (response, context)-> {
            HeaderElementIterator it = new BasicHeaderElementIterator(
                    response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    try {
                        return Long.parseLong(value) * 1000;
                    } catch (NumberFormatException e) {
                        log.error(Throwables.toString(e));
                    }
                }
            }
            return 30 * 1000;
        };
    }

    private void printResponse(CloseableHttpResponse response) {
        HeaderIterator it = response.headerIterator();
        log.debug("=======================Response=======================");
        while (it.hasNext()) {
            log.debug(it.next().toString());
        }
        log.debug("=======================Response=======================");
    }

    private void printCookie() {
        List<Cookie> cookies = cookieStore.getCookies();
        log.debug("=======================Cookies=======================");
        for (Cookie cookie : cookies) {
            log.debug("Cookie:{}:{};{};{}", cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath());
        }
        log.debug("=======================Cookies=======================");
    }

    @Override
    public void close(){
        Closer.close(this.client);
    }
}