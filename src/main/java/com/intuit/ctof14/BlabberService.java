package com.intuit.ctof14;

import java.net.URLEncoder;

import oauth.signpost.AbstractOAuthConsumer;
import oauth.signpost.OAuthConsumer;
import com.intuit.ctof14.util.UnirestoAuthConsumer;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.MultipartBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("v1/blabber")
@Api(value="v1/blabber", description="Blabber API - this allows you to re-post messages. v1 will just tweet your message,")
public class BlabberService
{
    final static String CONSUMER_KEY = "AeRAWsFntWKrnjSG4Ou1kv70h";
    final static String CONSUMER_SECRET = "nkAJxGFVNYlAh44rjhM8IL2f76vTfAe2xoBaLtrbIFhGFriJZK";
    final static String TOKEN = "2468137056-Aj8s854RYAXIUETlxt5YTGKQjGI6CJEHrCTcaFD";
    final static String TOKEN_SECRET = "Dn08A7rRi3XESo0dtidTcxMd2DhWRlfiwnaL1i2EWJzEV";

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Blab a message", notes = "More notes about this method")
    public Response blab(String message) {
        MultipartBody request = null;
        try {
            request = Unirest.post("https://api.twitter.com/1.1/statuses/update.json")
                    .header("content-type", "application/x-www-form-urlencoded")
                    .field("status",message);
            OAuthConsumer consumer = new UnirestoAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
            consumer.setTokenWithSecret(TOKEN, TOKEN_SECRET);
            consumer.sign(request.getHttpRequest());
            HttpResponse<JsonNode> response = request.asJson();

            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }
}