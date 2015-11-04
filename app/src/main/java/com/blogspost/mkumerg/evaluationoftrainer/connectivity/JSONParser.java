package com.blogspost.mkumerg.evaluationoftrainer.connectivity;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by User on 10/3/2015.
 */
public class JSONParser {
    static InputStream is=null;
    static JSONObject jObj;
    static String json="";

    //constructor
    public JSONParser(){

    }

    //function get json from url
    //by making HTTP POST or GET method
    public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params) {

        //Making HTTP request
        try {

            //check for request method
            if(method == "POST"){
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));


                HttpResponse httpResponse= httpClient.execute(httpPost);
                HttpEntity httpEntity= httpResponse.getEntity();
                is= httpEntity.getContent();


            }
            else if(method == "GET"){
                //request method is get
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url+= "?" + paramString;
                HttpGet httpGet =new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity= httpResponse.getEntity();
                is = httpEntity.getContent();

            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();

        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        //try parse the string to a JSON object
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader( is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
//                Log.d("SB: ", " > " + sb);

            }
            is.close();
            json = sb.toString();
            jObj = new JSONObject(json);
//            Log.d("JSONOBJECT: "," > " + jObj.toString());

        }catch (Exception e){
            Log.e("Buffer Error","Error parsing data " + e.toString());
        }

        //return JSON String
        return jObj;
    }
}
