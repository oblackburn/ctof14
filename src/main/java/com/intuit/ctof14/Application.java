package com.intuit.ctof14;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Application extends ResourceConfig implements ServletContextListener
{
    // ............................................................................
    // Application constants - you must set these
    //
    //
    // TEAM_ID = use TEAM_ID for your table. Please see the card. Your team id is numeric.
    public final static String TEAM_ID = "2201";
    // SERVICE_ID = come up with a unique name for your service. Use your CORP user ID and a name.
    // e.g. jeffb531-blabber
    // You will be using this name to register your service on the Intuit Service Portal
    // And your url for your service will be: https://jeffb531-blabber-service.codenvy.ctof.intuit.com
    public final static String SERVICE_ID = "rnorian-activities";
    // APP_ID = Private Auth appid - get from your on boarding exercise from the service portal
    public final static String APP_ID = "Intuit.smallbusiness.beansit.holdthepickle";
    // APP_SECRET = Private Auth secret - get from your on boarding exercise from the service portal
    public final static String APP_SECRET = "preprdIpXu5DkwDdKHZSA5jp2ELBjf0HKupz62xG";
    //
    //
    //
    // .......................................................................


    private final static String VCAP_APPLICATION = "VCAP_APPLICATION";

    public Application()
    {
        packages("com.wordnik.swagger.jersey.listing;com.intuit.ctof14");
        register(MoxyJsonFeature.class);
        register(JsonMoxyConfigurationContextResolver.class);
    }

    public static String getAuthorizationHeader() {
        return "Intuit_IAM_Authentication intuit_userid=12345,intuit_realmid="+Application.TEAM_ID+",intuit_appid="+Application.APP_ID+",intuit_app_secret="+Application.APP_SECRET;
    }

    public static String getCodenvyAppRunUrl()
    {
        String appInstance = System.getenv(VCAP_APPLICATION);
        String appUrl = null;

        if (appInstance != null)
        {
            try
            {
                JSONObject appInstanceObj = new JSONObject(appInstance);
                appUrl = appInstanceObj.getJSONArray("uris").getString(0);
            }
            catch (JSONException ex)
            {

            }
        }

        return appUrl;

    }

    public static void updateProxyUrl()
    {
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet =
                    new HttpGet("http://"+SERVICE_ID+"-service.codenvy.ctof.intuit.com/update_url/" + getCodenvyAppRunUrl());
            httpClient.execute(httpGet);
        }
        catch (Exception ex)
        {
        }
    }

    public void contextInitialized(ServletContextEvent event)
    {
        Application.updateProxyUrl();
    }

    public void contextDestroyed(ServletContextEvent event)
    {
    }
}