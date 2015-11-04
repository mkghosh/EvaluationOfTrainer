package com.blogspost.mkumerg.evaluationoftrainer.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspost.mkumerg.evaluationoftrainer.R;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.GlobalVariables;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginScreenForMonitor extends Activity {
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
    String monitorId;
    //Url to get JSON
    private static String url = "http://" + GlobalVariables.hostAddress + "/AndroidCheckData/monitorinfo.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen_monitor);
        etemail = (EditText)findViewById(R.id.et_email);
        etpassword = (EditText)findViewById(R.id.et_password);
        credentialList = new ArrayList<HashMap<String, String>>();

        etemail.setText("mijan@gmail.com");
        etpassword.setText("123456");

    }

    private class GetCredential extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(url,ServiceHandler.GET);
            if(jsonStr != null){
                try{
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    monitorInfo = jsonObject.getJSONArray(TAG_TABLE);

                    //Iterating over the json array
                    for(int i = 0; i<monitorInfo.length(); i++){
                        JSONObject c = monitorInfo.getJSONObject(i);
                        String realEmail = c.getString(TAG_EMAIL);
                        String realPassword = c.getString(TAG_PASSWORD);
                        String id = c.getString(TAG_ID);
                        HashMap<String,String> crd = new HashMap<>();
                        crd.put(TAG_ID,id);
                        crd.put(TAG_EMAIL,realEmail);
                        crd.put(TAG_PASSWORD, realPassword);
                        credentialList.add(crd);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finally {
                    sh = null;
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginScreenForMonitor.this);
            pDialog.setMessage("Please wait ....");
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Intent in = new Intent(LoginScreenForMonitor.this,MonitorActivity.class);


            email = etemail.getText().toString();
            password = etpassword.getText().toString();

            for(int i = 0; i < credentialList.size();i++){
                String e = credentialList.get(i).get(TAG_EMAIL).toString();
                String p = credentialList.get(i).get(TAG_PASSWORD).toString();
                if(email.equals(e) && password.equals(p))
                {
                    flag = true;
                    monitorId = credentialList.get(i).get(TAG_ID).toString();
                    GlobalVariables.monitorId = monitorId;
//                    in.putExtra("monitorId", monitorId);
                    break;
                }
            }
            if(flag){
                startActivity(in);
            } else {
                Toast.makeText(LoginScreenForMonitor.this, "Email or Passwrod mismatch.", Toast.LENGTH_SHORT).show();
            }
//            url = null;
            pDialog.dismiss();
            //finish();
//            Log.d("MointorId:", "The Monitor Id is: " + GlobalVariables.monitorId);
        }
    }

    public void login(View v){
        new GetCredential().execute();
    }
}
