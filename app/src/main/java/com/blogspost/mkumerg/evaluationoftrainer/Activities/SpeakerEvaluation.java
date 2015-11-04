package com.blogspost.mkumerg.evaluationoftrainer.Activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspost.mkumerg.evaluationoftrainer.R;
import com.blogspost.mkumerg.evaluationoftrainer.adaptorsource.GroupSubGroupBaseAdpt;
import com.blogspost.mkumerg.evaluationoftrainer.adaptorsource.GroupSubGroupSet;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.GlobalVariables;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.JSONParser;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpeakerEvaluation extends AppCompatActivity {

    //TAGS for getting the values of subgroup property and groups
    private static final String TAG_GROUP = "Groups";
    private static final String TAG_SUB_GROUP_A = "subgroupA";
    private static final String TAG_SUB_GROUP_B = "subgroupB";
    private static final String TAG_RATINGS = "ratings";

    private static final String TAG_SUB_GROUP_NAME = "subGroupName";
    private static final String TAG_PROPERTY = "propertyName";
    private static final String TAG_GROUP_NAME = "groupName";
    private static final String TAG_PROPERTY_ID = "autoId";
    private static final String TAG_R_PROPERTY_ID = "propertyId";
    private static final String TAG_RATING_ONE = "rating1";
    private static final String TAG_RATING_TWO = "rating2";
    private static final String TAG_RATING_THREE = "rating3";
    private static final String TAG_RATING_FOUR = "rating4";
    private static final String TAG_RATING_FIVE = "rating5";


    JSONArray groupAProperty = null;
    JSONArray groupBProperty = null;
    JSONArray groupName = null;
    JSONArray allRatings = null;



    ArrayList<GroupSubGroupSet> subGroupA;
    ArrayList<GroupSubGroupSet> subGroupB;
    ArrayList<GroupSubGroupSet> subGroupName;
    ArrayList<GroupSubGroupSet> ratingsList;


    ListView subGroupAView,subGroupBView;
    TextView speakerName;
    EditText etComment;

    String comment;

    private static String url = "http://" + GlobalVariables.hostAddress + "/AndroidCheckData/trainerEvaluationInfo.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speaker_evaluation);

        subGroupA = new ArrayList<GroupSubGroupSet>();
        subGroupB = new ArrayList<GroupSubGroupSet>();
        subGroupName = new ArrayList<GroupSubGroupSet>();
        ratingsList = new ArrayList<GroupSubGroupSet>();

        etComment = (EditText)findViewById(R.id.comment);
        speakerName = (TextView)findViewById(R.id.speaker_name);
        speakerName.setText(GlobalVariables.trainerName);
        new ConnectToDatabase().execute();
    }

    private class ConnectToDatabase extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler sh = new ServiceHandler();
            url = "http://" + GlobalVariables.hostAddress + "/AndroidCheckData/trainerEvaluationInfo.php";
            url = url + "?" + "traineeId" + "=" + GlobalVariables.traineeId + "&" +"evaluationSessionId" + "=" + GlobalVariables.evaluationSessionId;
//            Log.d("Url : ",url);
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);;
            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    groupAProperty = jsonObject.getJSONArray(TAG_SUB_GROUP_A);
                    groupBProperty = jsonObject.getJSONArray(TAG_SUB_GROUP_B);
                    groupName = jsonObject.getJSONArray(TAG_GROUP);

                    //Iterating over the json array
                    for (int i = 0; i < groupAProperty.length(); i++) {
                        JSONObject c = groupAProperty.getJSONObject(i);
                        String subGroup = c.getString(TAG_SUB_GROUP_NAME);
                        String property = c.getString(TAG_PROPERTY);
                        String propertyId = c.getString(TAG_PROPERTY_ID);

                        GroupSubGroupSet subGroupAData = new GroupSubGroupSet();

                        subGroupAData.setSubGroup(subGroup);
                        subGroupAData.setProperty(property);
                        subGroupAData.setPropertyId(propertyId);

                        subGroupA.add(subGroupAData);
                    }
                    for (int i = 0; i < groupBProperty.length(); i++) {
                        JSONObject c = groupBProperty.getJSONObject(i);
                        String subGroup = c.getString(TAG_SUB_GROUP_NAME);
                        String property = c.getString(TAG_PROPERTY);
                        String propertyId = c.getString(TAG_PROPERTY_ID);

                        GroupSubGroupSet subGroupBData = new GroupSubGroupSet();

                        subGroupBData.setSubGroup(subGroup);
                        subGroupBData.setProperty(property);
                        subGroupBData.setPropertyId(propertyId);
                        subGroupB.add(subGroupBData);
                    }
                    for (int i = 0; i < groupName.length(); i++) {
                        JSONObject c = groupName.getJSONObject(i);
                        String group = c.getString(TAG_GROUP_NAME);
                        String subGroup = c.getString(TAG_SUB_GROUP_NAME);

                        GroupSubGroupSet subGroupAData = new GroupSubGroupSet();

                        subGroupAData.setSubGroup(subGroup);
                        subGroupAData.setGroup(group);
                        subGroupName.add(subGroupAData);
                    }

                    try{
                        allRatings = jsonObject.getJSONArray(TAG_RATINGS);
                        for(int i = 0; i<allRatings.length();i++) {

                            //Getting all the values of JSONArray
                            JSONObject c = allRatings.getJSONObject(i);
                            String propertyId = c.getString(TAG_R_PROPERTY_ID);
                            String rating1 = c.getString(TAG_RATING_ONE);
                            String rating2 = c.getString(TAG_RATING_TWO);
                            String rating3 = c.getString(TAG_RATING_THREE);
                            String rating4 = c.getString(TAG_RATING_FOUR);
                            String rating5 = c.getString(TAG_RATING_FIVE);

                            //Setting the values of ratings to new GroupSubGroupSet
                            GroupSubGroupSet ratings = new GroupSubGroupSet();
                            ratings.setPropertyId(propertyId);
                            ratings.setRating1(Integer.parseInt(rating1));
                            ratings.setRating2(Integer.parseInt(rating2));
                            ratings.setRating3(Integer.parseInt(rating3));
                            ratings.setRating4(Integer.parseInt(rating4));
                            ratings.setRating5(Integer.parseInt(rating5));
                            Log.d("",ratings.toString());
                            //Adding the ratings object to the ratingsList
                            ratingsList.add(ratings);
                        }

                    } catch (JSONException e){
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                        ratingsList = null;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    sh = null;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            subGroupAView = (ListView)findViewById(R.id.sub_group1);
            subGroupAView.setAdapter(new GroupSubGroupBaseAdpt(SpeakerEvaluation.this,subGroupA,ratingsList,0));

            int index = 0;
            if(ratingsList != null)
            {
                index = subGroupA.size();
            }

            subGroupBView = (ListView)findViewById(R.id.sub_group2);
            subGroupBView.setAdapter(new GroupSubGroupBaseAdpt(SpeakerEvaluation.this, subGroupB,ratingsList,index));
        }
    }

    public void submit(View view){
        new InsertDataIntoDatabase().execute();
    }




ProgressDialog pDialogInsert;

JSONParser jsonParser;
int success;
String TAG_SUCCESS = "success";
String url_insert = "http://" + GlobalVariables.hostAddress + "/AndroidCheckData/insertEvaluation.php";

    class InsertDataIntoDatabase extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            comment = etComment.getText().toString();
            pDialogInsert = new ProgressDialog(SpeakerEvaluation.this);
            pDialogInsert.setMessage("Inserting Data ... ...");
            pDialogInsert.setIndeterminate(false);
            pDialogInsert.setCancelable(true);
            pDialogInsert.show();
        }

          @Override
        protected String doInBackground(String... args) {

           jsonParser = new JSONParser();

           //Iterate over subGorupA
           for(int i = 0; i<subGroupA.size();i++) {

              //Creating NameValuePair for inserting data of subGroupA
              List<NameValuePair> params = new ArrayList<>();
              params.add(new BasicNameValuePair("traineeId", GlobalVariables.traineeId));
              params.add(new BasicNameValuePair("evaluationSessionId", GlobalVariables.evaluationSessionId));
              params.add(new BasicNameValuePair("propertyId", subGroupA.get(i).getPropertyId()));
              params.add(new BasicNameValuePair("rating1", "" + subGroupA.get(i).getRating1()));
              params.add(new BasicNameValuePair("rating2", "" + subGroupA.get(i).getRating2()));
              params.add(new BasicNameValuePair("rating3", "" + subGroupA.get(i).getRating3()));
              params.add(new BasicNameValuePair("rating4", "" + subGroupA.get(i).getRating4()));
              params.add(new BasicNameValuePair("rating5", "" + subGroupA.get(i).getRating5()));
              params.add(new BasicNameValuePair("comment", comment));


              JSONObject json = jsonParser.makeHttpRequest(url_insert, "POST", params);

              try
              {
                  success = json.getInt(TAG_SUCCESS);

              }
              catch (JSONException e)
              {
                  e.printStackTrace();
              }
              catch (Exception e)
              {
                  e.printStackTrace();
              }

           }
           for(int i = 0; i<subGroupB.size();i++) {

              //Creating NameValuePair for inserting data of subGroupA
              List<NameValuePair> params = new ArrayList<>();
              params.add(new BasicNameValuePair("traineeId", GlobalVariables.traineeId));
              params.add(new BasicNameValuePair("evaluationSessionId", GlobalVariables.evaluationSessionId));
              params.add(new BasicNameValuePair("propertyId", subGroupB.get(i).getPropertyId()));
              params.add(new BasicNameValuePair("rating1", "" + subGroupB.get(i).getRating1()));
              params.add(new BasicNameValuePair("rating2", "" + subGroupB.get(i).getRating2()));
              params.add(new BasicNameValuePair("rating3", "" + subGroupB.get(i).getRating3()));
              params.add(new BasicNameValuePair("rating4", "" + subGroupB.get(i).getRating4()));
              params.add(new BasicNameValuePair("rating5", "" + subGroupB.get(i).getRating5()));
              params.add(new BasicNameValuePair("comment", comment));


              JSONObject json = jsonParser.makeHttpRequest(url_insert, "POST", params);

              try
              {
                  success = json.getInt(TAG_SUCCESS);

              }
              catch (JSONException e)
              {
                  e.printStackTrace();
              }
              catch (Exception e)
              {
                  e.printStackTrace();
              }

           }
           return  null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialogInsert.dismiss();

            if (success == 1) {
                Toast.makeText(SpeakerEvaluation.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SpeakerEvaluation.this, "Data not inserted successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
