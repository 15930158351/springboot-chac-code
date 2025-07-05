package com.chac.util;

import com.chac.util.HttpsSSLUtilFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

@Slf4j
@Component
public class HttpUtil {

    public static final String HTTPS = "https";

    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_JSON_VALUE = "application/json;chartset=UTF-8";
    public static final String CONTENT_TYPE_FORM_URL_ENCODED = "application/x-www-form-urlencoded";


    public static String getHttpResponseString(String url, String encryptData, Map<String, Object> headerMap) {
        String body = encryptData;
        Protocol myHttps = new Protocol(HTTPS, new HttpsSSLUtilFactory(), 443);
        Protocol.registerProtocol(HTTPS, myHttps);
        InputStream in = null;
        HttpClient client = null;
        PostMethod postMethod = null;
        try {

            client = new HttpClient();
            client.getHttpConnectionManager().getParams().setConnectionTimeout(20000);
            postMethod = new PostMethod(url);
            postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
            postMethod.addRequestHeader(HEADER_CONTENT_TYPE, "application/json;chartset=UTF-8");

            //增加header信息
            if (MapUtils.isNotEmpty(headerMap)) {
                for (String headerKey : headerMap.keySet()) {
                    postMethod.addRequestHeader(headerKey, headerMap.get(headerKey).toString());
                }
            }

            RequestEntity entity = new StringRequestEntity(body, CONTENT_TYPE_JSON, "UTF-8");
            postMethod.setRequestEntity(entity);
            client.executeMethod(postMethod);

            in = postMethod.getResponseBodyAsStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            return response.toString();
        } catch (Exception e) {
            log.error("http请求出错 ", e);
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            postMethod.releaseConnection();
            client.getHttpConnectionManager().closeIdleConnections(0);
        }
        return null;

    }

    public static String getHttpResByFormParams(String url, Map<String, Object> params, Map<String, Object> headerMap) {
        Protocol myHttps = new Protocol(HTTPS, new HttpsSSLUtilFactory(), 443);
        Protocol.registerProtocol(HTTPS, myHttps);
        InputStream in = null;
        HttpClient client = null;
        PostMethod postMethod = null;
        try {
            client = new HttpClient();
            client.getHttpConnectionManager().getParams().setConnectionTimeout(20000);
            postMethod = new PostMethod(url);
            postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
            postMethod.addRequestHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON_VALUE);

            // 增加header信息
            if (MapUtils.isNotEmpty(headerMap)) {
                for (String headerKey : headerMap.keySet()) {
                    postMethod.addRequestHeader(headerKey, headerMap.get(headerKey).toString());
                }
            }

            // 构建请求体
            if (MapUtils.isNotEmpty(params)) {
                NameValuePair[] data = new NameValuePair[params.size()];
                int i = 0;
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    data[i++] = new NameValuePair(entry.getKey(), entry.getValue().toString());
                }
                postMethod.setRequestBody(data);
            }

            client.executeMethod(postMethod);

            in = postMethod.getResponseBodyAsStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            return response.toString();
        } catch (Exception e) {
            log.error("http请求出错 ", e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
            if (client != null) {
                client.getHttpConnectionManager().closeIdleConnections(0);
            }
        }
        return null;
    }
}
