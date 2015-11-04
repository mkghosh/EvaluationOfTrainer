package com.blogspost.mkumerg.evaluationoftrainer.connectivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

public class ServiceHandler {

    String response = "";
    public final static int GET = 1;
    public final static int POST = 2;

    public ServiceHandler() {

    }

    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     * */
    public String makeServiceCall(String url, int method) {
        try {
            URL url1 = new URL(url);
            URLConnection conn = (URLConnection) url1.openConnection();
            InputStream is = conn.getInputStream();
            BufferedReader reader =new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String webPage = "",data="";

            while ((data = reader.readLine()) != null){
                response += data;
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;

    }
}