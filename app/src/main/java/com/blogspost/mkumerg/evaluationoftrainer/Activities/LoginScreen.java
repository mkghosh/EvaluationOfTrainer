package com.blogspost.mkumerg.evaluationoftrainer.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspost.mkumerg.evaluationoftrainer.R;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.GlobalVariables;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginScreen extends Activity {
    //flag variable
    boolean flag = false;
    //Required variables
    EditText etemail,etpassword;
    private String email,password;
    private static final String TAG_TABLE = "monitorinfo";
    private static final String TAG_ID = "autoId";
    private static final String TAG_NAME = "monitorName";
    private static final String TAG_EMAIL = "emailId";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_TRACKID = "trackId";

    //progess dialog
    private ProgressDialog pDialog;

    //JSON Array
    JSONArray monitorInfo = null;

    ArrayList<HashMap<String,String>> credentialList;
    String monitorId = "0";
    //Url to get JSON
    private static String url = "http://" + GlobalVariables.hostAddress + "/AndroidCheckData/monitor_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen_monitor);
        etemail = (EditText)findViewById(R.id.et_email);
        etpassword = (EditText)findViewById(R.id.et_password);
        credentialList = new ArrayList<HashMap<String, String>>();

        etemail.setText("sajjad@gmail.com");
        etpassword.setText("123456");

    }

    private class GetCredential extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            JSONParser selectUser = new JSONParser();
            List<NameValuePair> pairs = new ArrayList<>();
            pairs.add(new BasicNameValuePair("emailId", email));
            pairs.add(new BasicNameValuePair("password",password));

            JSONObject jsonObj = selectUser.makeHttpRequest(url,"POST",pairs);
            Log.d("JSON: ", jsonObj.toString());

            try {
                monitorInfo = jsonObj.getJSONArray(TAG_TABLE);

                //Iterating over the json array
                for (int i = 0; i < monitorInfo.length(); i++) {
                    JSONObject c = monitorInfo.getJSONObject(i);
                    monitorId = c.getString(TAG_ID);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginScreen.this);
            pDialog.setMessage("Please wait ....");
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(!monitorId.equals("0"))
            {
                Intent in = new Intent(LoginScreen.this,MonitorActivity.class);
                in.putExtra("monitorId", monitorId);
                GlobalVariables.monitorId = monitorId;
                startActivity(in);
            }
            else
            {
                Toast.makeText(LoginScreen.this, "Email or Passwrod mismatch.", Toast.LENGTH_SHORT).show();
            }
            pDialog.dismiss();
            //finish();
        }
    }

    public void login(View v)
    {
        email = etemail.getText().toString();
        password = etpassword.getText().toString();
        new GetCredential().execute();
        //finish();
    }
}
