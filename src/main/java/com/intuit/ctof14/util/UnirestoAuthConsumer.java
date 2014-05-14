package com.intuit.ctof14.util;

import oauth.signpost.AbstractOAuthConsumer;
import oauth.signpost.http.HttpRequest;
import com.intuit.ctof14.util.HttpRequestAdapter;

import javax.xml.crypto.dsig.SignatureMethod;

public class UnirestoAuthConsumer extends AbstractOAuthConsumer
{
    public UnirestoAuthConsumer(String consumerKey, String consumerSecret) {
        super(consumerKey, consumerSecret);
    }

    @Override
    protected HttpRequest wrap(Object request) {
        if (!(request instanceof com.mashape.unirest.request.HttpRequest)) {
            throw new IllegalArgumentException(
                    "The default consumer expects requests of type com.mashape.unirest.request.HttpRequest ");
        }
        return new HttpRequestAdapter((com.mashape.unirest.request.HttpRequest) request);
    }
}