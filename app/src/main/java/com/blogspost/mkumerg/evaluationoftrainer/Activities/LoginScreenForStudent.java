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
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginScreenForStudent extends Activity {
    //flag variable
    boolean flag = false;
    //Required variables
    EditText etemail,etpassword;
    private String email,password;
    private static final String TAG_TABLE = "traineeinfo";
    private static final String TAG_ID = "autoId";
    private static final String TAG_NAME = "traineeName";
    private static final String TAG_EMAIL = "emailId";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_TRACKID = "trackId";
    private static final String TAG_PICUTREID = "pictureId";
    private static final String TAG_BARDID = "bardId";
    private static final String TAG_GROUP_ID = "groupId";

    //progess dialog
    private ProgressDialog pDialog;

    //JSON Array
    JSONArray traineeInfo = null;

    ArrayList<HashMap<String,String>> credentialList;
//    String traineeId;
    //Url to get JSON
//    private static String url = "http://" + GlobalVariables.hostAddress + "/AndroidCheckData/traineeinfo.php";
    private static String url = "http://" + GlobalVariables.hostAddress + "/AndroidCheckData/trainee_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen_trainee);
        etemail = (EditText)findViewById(R.id.et_email);
        etpassword = (EditText)findViewById(R.id.et_password);
        credentialList = new ArrayList<HashMap<String, String>>();

        etemail.setText("mkumerg@gmail.com");
        etpassword.setText("123456");

    }

    private class GetCredential extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler sh = new ServiceHandler();

            String url1 = url;
            url1 = url1 + "?" + "emailId=" + email + "&password=" + password + "" ;

            Log.d("",url1);

            String jsonStr = sh.makeServiceCall(url1,ServiceHandler.GET);
            if(jsonStr != null){
                try{
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    traineeInfo = jsonObject.getJSONArray(TAG_TABLE);

                    //Iterating over the json array
                    for(int i = 0; i<traineeInfo.length(); i++){
                        JSONObject c = traineeInfo.getJSONObject(i);
                        String realEmail = c.getString(TAG_EMAIL);
                        String realPassword = c.getString(TAG_PASSWORD);
                        String id = c.getString(TAG_ID);
                        String traineeName = c.getString(TAG_NAME);
                        String pictureId = c.getString(TAG_PICUTREID);
                        String bardId = c.getString(TAG_BARDID);
                        String groupId = c.getString(TAG_GROUP_ID);
                        String trackId = c.getString(TAG_TRACKID);
                        HashMap<String,String> crd = new HashMap<>();
                        crd.put(TAG_ID,id);
                        crd.put(TAG_EMAIL,realEmail);
                        crd.put(TAG_PASSWORD, realPassword);
                        crd.put(TAG_NAME,traineeName);
                        crd.put(TAG_PICUTREID,pictureId);
                        crd.put(TAG_BARDID,bardId);
                        crd.put(TAG_GROUP_ID,groupId);
                        crd.put(TAG_TRACKID,trackId);
                        credentialList.add(crd);

                        GlobalVariables.traineeId = credentialList.get(i).get(TAG_ID).toString();
                        GlobalVariables.pictureId = credentialList.get(i).get(TAG_PICUTREID).toString();
                        GlobalVariables.traineeName = credentialList.get(i).get(TAG_NAME).toString();
                        GlobalVariables.traineeName = credentialList.get(i).get(TAG_NAME).toString();
                        GlobalVariables.traineeBardId = credentialList.get(i).get(TAG_BARDID).toString();
                        GlobalVariables.traineeGroupId = credentialList.get(i).get(TAG_GROUP_ID).toString();
                        GlobalVariables.trackId = credentialList.get(i).get(TAG_TRACKID).toString();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    GlobalVariables.traineeId = "";
                }catch (Exception e) {
                    e.printStackTrace();
                    GlobalVariables.traineeId = "";
                }

                finally {
                    sh = null;
                }

                Log.d("",GlobalVariables.traineeId);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginScreenForStudent.this);
            pDialog.setMessage("Please wait ....");
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

//            Intent in = new Intent(LoginScreenForStudent.this,MonitorActivity.class);

            /*
            email = etemail.getText().toString();
            password = etpassword.getText().toString();

            for(int i = 0; i < credentialList.size();i++){
                String e = credentialList.get(i).get(TAG_EMAIL).toString();
                String p = credentialList.get(i).get(TAG_PASSWORD).toString();
                if(email.equals(e) && password.equals(p))
                {
                    flag = true;
                    GlobalVariables.traineeId = credentialList.get(i).get(TAG_ID).toString();
                    GlobalVariables.pictureId = credentialList.get(i).get(TAG_PICUTREID).toString();
                    GlobalVariables.traineeName = credentialList.get(i).get(TAG_NAME).toString();
                    GlobalVariables.traineeName = credentialList.get(i).get(TAG_NAME).toString();
                    GlobalVariables.traineeBardId = credentialList.get(i).get(TAG_BARDID).toString();
                    GlobalVariables.traineeGroupId = credentialList.get(i).get(TAG_GROUP_ID).toString();
                    GlobalVariables.trackId = credentialList.get(i).get(TAG_TRACKID).toString();
//                    in.putExtra("monitorId", traineeId);
                    break;
                }
            }*/
            //if(flag)
            if(!GlobalVariables.traineeId.equals(""))
            {
//                startActivity(in);
//                finish();
                startActivity(new Intent(LoginScreenForStudent.this,StudentActivity.class));
            } else
            {
                Toast.makeText(LoginScreenForStudent.this, "Email or Passwrod mismatch.", Toast.LENGTH_SHORT).show();
            }
//            url = null;
            pDialog.dismiss();
            //finish();
        }
    }

    public void login(View v)
    {
        email = etemail.getText().toString();
        password = etpassword.getText().toString();
        new GetCredential().execute();
    }
}
