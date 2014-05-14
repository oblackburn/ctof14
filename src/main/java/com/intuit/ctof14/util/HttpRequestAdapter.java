package com.intuit.ctof14.util;

import oauth.signpost.http.HttpRequest;

import org.apache.commons.lang.StringUtils;

import com.mashape.unirest.http.Unirest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestAdapter implements HttpRequest
{
    protected com.mashape.unirest.request.HttpRequest request;

    public HttpRequestAdapter(com.mashape.unirest.request.HttpRequest request) {
        this.request = request;
    }
    public String getMethod() {
        return request.getHttpMethod().name();
    }

    public String getRequestUrl() {
        return request.getUrl();
    }

    public  void setRequestUrl(String url) {
        throw new RuntimeException("Not supported.");
    }

    public void setHeader(String name, String value) {
        request.header(name, value);
    }

    public String getHeader(String name) {
        return StringUtils.join(request.getHeaders().get(name), ",");
    }

    public Map<String, String> getAllHeaders() {
        Map<String, String> result = new HashMap<String, String>();
        for (String key : this.request.getHeaders().keySet()) {
            result.put(key, StringUtils.join(this.request.getHeaders().get(key), ","));
        }
        return result;
    }

    public InputStream getMessagePayload() throws IOException {
        return request.getBody().getEntity().getContent();
    }

    public String getContentType() {
        return request.getHeaders().get("content-type").get(0);
    }

    public Object unwrap() {
        return this.request;
    }
}