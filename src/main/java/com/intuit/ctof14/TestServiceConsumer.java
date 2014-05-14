package com.intuit.ctof14;

import org.json.JSONArray;
import org.json.JSONObject;

import com.intuit.ctof14.Application;
import com.intuit.ctof14.TestServiceConsumer;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.net.URI;

@Path("testConsumer")
public class TestServiceConsumer {
    final static String baseFeedUri = "https://activityfeed-prf.platform.intuit.com";

    @GET
    @Path("findBestRestaurant")
    @Produces("application/json")
    public Response findBestRestaurant() {
        try {
            List<JSONObject> feeds = getRestaurants();

            String winningRestaurant = "";

            // Iterate through feed list finding best restaurant
            for (JSONObject item : feeds) {
                String itemContent = item.has("content") ? item.getString("content") : "";
                if (itemContent.trim().equalsIgnoreCase("Franks and Furters")) {
                    winningRestaurant = itemContent;
                }
            }

            if (feeds.size() == 0 || winningRestaurant.equals("")) {
                winningRestaurant = "no best restaurant found";
            }

            // Return winner
            JSONObject obj = new JSONObject();
            obj.put("result", winningRestaurant);
            return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
        } catch (Throwable e) {
            return Response.status(500).build();
        }
    }

    private List<JSONObject> getRestaurants() throws Exception {
        List<JSONObject> results = new ArrayList<JSONObject>();

        // Setup request to gateway activity feed end point using the very handy
        // Unirest library http://unirest.io/
        HttpRequest request =
                Unirest.get(TestServiceConsumer.baseFeedUri + "/v1/feeds")
                        .header("accept", "application/json")
                        .header("Authorization", Application.getAuthorizationHeader());

        // Make request to gateway
        HttpResponse<JsonNode> jsonResponse = request.asJson();
        JsonNode body = jsonResponse.getBody();

        // Get the array of feed elements
        JSONArray feed = body.getObject().getJSONArray("feed");

        String winningRestaurant = "";

        // Iterate through feed list finding best restaurant
        for (int index = 0; index < feed.length(); index++) {
            JSONObject item = feed.getJSONObject(index);
            results.add(item);
        }

        return results;
    }

    @GET
    @Path("postBestRestaurant")
    @Produces("application/json")
    public Response postBestRestaurant(@Context HttpHeaders hh, @QueryParam("bestRestaurant") String bestRestaurant) {
        RequestBodyEntity request = null;
        try {
            List<JSONObject> items = getRestaurants();
            for (JSONObject item : items) {
                String itemContent = item.getString("content");
                if (itemContent.toLowerCase().startsWith(bestRestaurant.toLowerCase())) {
                    JSONObject obj = new JSONObject();
                    obj.put("result", "item already exists");
                    return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
                }
            }

            // Create a new note feed item
            JSONObject feedItem = new JSONObject();

            // Write your code here to set the feed type and content just like you did with swagger. The best restaurant is passed as a parameter
            // Hint: JSONObject is just like a dictionary. You can do feedItem.put("fieldName","fieldValue")
            // Hint: The two fields you set for a feed note from the swagger documentation are feedType and content
            // .........................................................
            String msg = bestRestaurant + " - best restaurant added on " + DateFormat.getDateTimeInstance().format(new Date());
            feedItem.put("content", msg);
            feedItem.put("feedType", "Note");

            // Setup request to gateway activity feed end point
            // Set the body of the request to the json object
            request =
                    Unirest.post(TestServiceConsumer.baseFeedUri + "/v1/feed")
                            .header("accept", "application/json")
                            .header("Authorization", Application.getAuthorizationHeader())
                            .header("Content-Type", "application/json")
                            .body(feedItem.toString());

            // Make request to gateway
            HttpResponse<JsonNode> jsonResponse = request.asJson();

            String feedId = null;
            JsonNode body = jsonResponse.getBody();
            JSONArray feed = body.getObject().getJSONArray("feed");
            if (feed.length() == 1) {
                JSONObject item = feed.getJSONObject(0);
                feedId = item.getString("feedId");
            }

            // Return success
            JSONObject obj = new JSONObject();
            obj.put("result", "success");
            //NOTE: this should be a 201 with a location header but this is a GET so...
            //URI feedItemUri = new URI(TestServiceConsumer.baseFeedUri + "/v1/feed/" + feedId);
            //return Response.created(feedItemUri).build();
            return Response.ok(obj).build();
        } catch (Throwable e) {
            return Response.status(500).build();
        }

    }
}
