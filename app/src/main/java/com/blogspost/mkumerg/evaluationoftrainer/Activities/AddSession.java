package com.blogspost.mkumerg.evaluationoftrainer.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspost.mkumerg.evaluationoftrainer.R;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.GlobalVariables;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.JSONParser;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mkumerg on 10/14/15.
 */
public class AddSession extends Activity {
    private TextView tvDisplayDate;
    private EditText editTextComments;
    private int year;
    private int month;
    private int day;
    String workDate,comment, autoId;
    String courseName,trainerName,groupName,trackName;
    String intentOption;

    int success;
    String TAG_SUCCESS = "success";

    String options;

    //TAGS OF tables to get the Objects from the url
    private static final String TAG_TABLE_TRACK = "trackinfo";
    private static final String TAG_TABLE_GROUP = "groupinfo";
    private static final String TAG_TABLE_COURSE = "courseinfo";
    private static final String TAG_TABLE_TRAINER = "trainerinfo";
    private static final String TAG_OBJECT_NAME = "sessioninfo";

    //TAGS for data from the url
    private static final String TAG_ID = "autoId";
    private static final String TAG_GROUP_NAME = "groupName";
    private static final String TAG_GROUP_ID = "groupId";
    private static final String TAG_TRACK_NAME = "trackName";
    private static final String TAG_TRACK_ID = "trackId";
    private static final String TAG_COURSE_NAME = "courseName";
    private static final String TAG_COURSE_ID = "courseId";
    private static final String TAG_TRAINER_NAME = "trainerName";
    private static final String TAG_TRAINER_ID = "trainerId";
    private static final String TAG_SESSION_DATE = "sessionDate";
    private static final String TAG_COMMENT = "comment";

    Button btnAdd,btnEdit,btnDelete;
    //jsonArray
    JSONArray allInfo = null;

    Spinner spTrack,spGroup,spCourse, spTrainer;

    //ProgressDialog
    ProgressDialog pDialog;

    //JSONOBJECTS
    JSONArray trackInfo = null, groupInfo = null, trainerInfo = null, courseInfo = null;
    //ArrayList
    ArrayList<HashMap<String,String>> trackInfoList,groupInfoList,courseInfoList, trainerInfoList;
    ArrayList<String> trackArrayList = new ArrayList<>(),groupArrayList = new ArrayList<>(),courseArrayList = new ArrayList<>();
    ArrayList<String> trainerArrayList = new ArrayList<>();
    //    Button buttonNewSession;
//    JSONArray courseInfo;
    JSONParser jsonParser;


    String url1 = "http://" + GlobalVariables.hostAddress + "/AndroidCheckData/insertEvaluationSession.php";
    //    String url = "http://" + GlobalVariables.hostAddress + "/AndroidCheckData/add_evaluation_session.php";
    String url = "http://" + GlobalVariables.hostAddress + "/AndroidCheckData/allEvaluationData.php";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_session);
        /*    intent.putExtra("courseName","");
    intent.putExtra("trainerName","");
    intent.putExtra("autoId","");
    intent.putExtra("option","add");
    intent.putExtra("groupName","");
    intent.putExtra("comment","");
    intent.putExtra("trackName","");*/


        Intent intent = getIntent();
        courseName = intent.getStringExtra("courseName");
        trainerName = intent.getStringExtra("trainerName");
        autoId = intent.getStringExtra("autoId");
        intentOption = intent.getStringExtra("option");
        groupName = intent.getStringExtra("groupName");
        comment = intent.getStringExtra("comment");
        trackName = intent.getStringExtra("trackName");

        Log.d("CourseName"," > " + courseName);
//        Log.d("TrainerName"," > " + trainerName);
//        Log.d("trackName"," > " + trackName);
//        Log.d("GroupName"," > " + groupName);
        btnAdd = (Button)findViewById(R.id.submit_session);
        btnEdit = (Button)findViewById(R.id.edit_session);
        btnDelete = (Button)findViewById(R.id.delete_session);

        GlobalVariables.groupId = "0";
        GlobalVariables.evTrackId = "0";
        GlobalVariables.trainerId = "0";
        //GlobalVariables

        spTrack = (Spinner)findViewById(R.id.sp_track);
        spGroup = (Spinner)findViewById(R.id.sp_group);
        spCourse = (Spinner)findViewById(R.id.sp_session);
        spTrainer = (Spinner)findViewById(R.id.sp_trainer);

        tvDisplayDate = (TextView)findViewById(R.id.tvDate);
        editTextComments = (EditText)findViewById(R.id.session_comment);

        trackArrayList.add("Select");
        groupArrayList.add("Select");
//        courseArrayList.add("Select");
//        trainerArrayList.add("Select");

        //Assining the required arrayLists
        trackInfoList = new ArrayList<HashMap<String,String>>();
        groupInfoList = new ArrayList<HashMap<String,String>>();
        courseInfoList = new ArrayList<HashMap<String,String>>();
        trainerInfoList = new ArrayList<HashMap<String,String>>();


        HashMap<String,String> select = new HashMap<>();
        select.put(TAG_ID, "0");
        select.put(TAG_TRACK_NAME, "Select");
        select.put(TAG_GROUP_NAME, "Select");
//        select.put(TAG_COURSE_NAME,"Select");
//        select.put(TAG_TRAINER_NAME,"Select");
//        courseInfoList.add(select);
        trackInfoList.add(select);
        groupInfoList.add(select);

        setCurrentDateOnView();
//        trainerInfoList.add(select);
        new GetAllData().execute();

        //Working on Session spinner

    }

    public void setCurrentDateOnView() {
//        tvDisplayDate = (TextView)findViewById(R.id.tvDate);
//        tvDisplayDate.setText(GlobalVariables.workDate);

        tvDisplayDate = (TextView) findViewById(R.id.tvDate);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        workDate = String.valueOf(year);
        if(month < 9) {
            workDate = workDate + "-" + "0" + (month+1);
        }
        else
        {
            workDate = workDate + "-" + (month+1);
        }

        if(day < 9)
        {
            workDate = workDate + "-" + "0" + (day);
        }
        else
        {
            workDate = workDate + "-" + (day);
        }


        // set current date into textview
        tvDisplayDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));
        if(intentOption.equals("add")){
            btnDelete.setEnabled(false);
            btnEdit.setEnabled(false);
        }
        else{
            editTextComments.setText(comment);
            btnAdd.setEnabled(false);
        }
    }



    private class GetAllData extends AsyncTask<Void,Void,Void>{
        int selectedCourseIndex = 0;
        int selectedGroupIndex = 0;
        int selectedTrackIndex = 0;
        int selectedTrainerIndex = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddSession.this);
            pDialog.setMessage("Please wait ....");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    trackInfo = jsonObject.getJSONArray(TAG_TABLE_TRACK);
                    groupInfo = jsonObject.getJSONArray(TAG_TABLE_GROUP);
                    courseInfo = jsonObject.getJSONArray(TAG_TABLE_COURSE);
                    trainerInfo = jsonObject.getJSONArray(TAG_TABLE_TRAINER);

                    //Iterating over the json array
                    for (int i = 0; i < trackInfo.length(); i++) {
                        JSONObject c = trackInfo.getJSONObject(i);
                        String id = c.getString(TAG_ID);
                        String trackName = c.getString(TAG_TRACK_NAME);

                        //HashMap for id and trackName
                        HashMap<String, String> crd = new HashMap<>();
                        crd.put(TAG_ID, id);
                        crd.put(TAG_TRACK_NAME, trackName);
                        trackInfoList.add(crd);

                        //Adding the vlue for creating the spinner
                        trackArrayList.add(trackName);

//                        if(GlobalVariables.trackName.equals(trackName))
//                        {
//                            selectedTrackIndex = i + 1;
//                        }
                    }
                    for (int i = 0; i < groupInfo.length(); i++) {
                        JSONObject c = groupInfo.getJSONObject(i);
                        String id = c.getString(TAG_ID);
                        String groupName = c.getString(TAG_GROUP_NAME);

                        //HashMap for the id and the GroupName
                        HashMap<String, String> crd = new HashMap<>();
                        crd.put(TAG_ID, id);
                        crd.put(TAG_GROUP_NAME, groupName);
                        groupInfoList.add(crd);

                        //Adding the value for the Spinner
                        groupArrayList.add(groupName);

//                        if(GlobalVariables.groupName.equals(groupName))
//                        {
//                            selectedGroupIndex = i + 1;
//                        }
                    }
                    for (int i = 0; i < trainerInfo.length(); i++) {
                        JSONObject c = trainerInfo.getJSONObject(i);
                        String id = c.getString(TAG_ID);
                        String trainerName = c.getString(TAG_TRAINER_NAME);

//                        if(i == 0)
//                        {
//                            GlobalVariables.trainerId = id;
//                        }

                        //HashMap for the id and the sessionName
                        HashMap<String, String> crd = new HashMap<>();
                        crd.put(TAG_ID, id);
                        crd.put(TAG_TRAINER_NAME, trainerName);
                        trainerInfoList.add(crd);

                        //adding the value for the spinner
                        trainerArrayList.add(trainerName);
                        //GlobalVariables.sessionName = item;
                        //GlobalVariables.sessionId = sessionId;

//                        if(GlobalVariables.sessionName.equals(sessionName))
//                        {
//                            selectedSessionIndex = i;
//                        }
                    }
                    for (int i = 0; i < courseInfo.length(); i++) {
                        JSONObject c = courseInfo.getJSONObject(i);
                        String id = c.getString(TAG_ID);
                        String courseName = c.getString(TAG_COURSE_NAME);

//                        if(i == 0)
//                        {
//                            GlobalVariables.courseId = id;
//                        }

                        //HashMap for the id and the sessionName
                        HashMap<String, String> crd = new HashMap<>();
                        crd.put(TAG_ID, id);
                        crd.put(TAG_COURSE_NAME, courseName);
                        courseInfoList.add(crd);

                        //adding the value for the spinner
                        courseArrayList.add(courseName);
                        //GlobalVariables.sessionName = item;
                        //GlobalVariables.sessionId = sessionId;

//                        if(GlobalVariables.sessionName.equals(sessionName))
//                        {
//                            selectedSessionIndex = i;
//                        }
                    }
                    if(intentOption.equals("update")){
                        selectedGroupIndex = searchSelectedItemId(groupArrayList,groupName);
                        selectedCourseIndex = searchSelectedItemId(courseArrayList,courseName);
                        selectedTrackIndex = searchSelectedItemId(trackArrayList,trackName);
                        selectedTrainerIndex = searchSelectedItemId(trainerArrayList,trainerName);
                        Log.d("Index"," > " + "Trainer: " + selectedTrainerIndex + "Group: " + selectedGroupIndex + "Track" + selectedTrackIndex );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();

            // Application of the Array to the Spinner
            ArrayAdapter<String> spinnerTrackArrayAdapter =
                    new ArrayAdapter<String>(AddSession.this,   android.R.layout.simple_spinner_item,trackArrayList);
            spinnerTrackArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // The drop down view
            spTrack.setAdapter(spinnerTrackArrayAdapter);
            spTrack.setSelection(selectedTrackIndex);

            ArrayAdapter<String> spinnerGroupArrayAdapter =
                    new ArrayAdapter<String>(AddSession.this,   android.R.layout.simple_spinner_item,groupArrayList);
            spinnerGroupArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // The drop down view
            spGroup.setAdapter(spinnerGroupArrayAdapter);
            spGroup.setSelection(selectedGroupIndex);

            ArrayAdapter<String> spinnerCourseArrayAdapter =
                    new ArrayAdapter<String>(AddSession.this,   android.R.layout.simple_spinner_item,courseArrayList);
            spinnerCourseArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // The drop down view
            spCourse.setAdapter(spinnerCourseArrayAdapter);
            spCourse.setSelection(selectedCourseIndex);

            ArrayAdapter<String> spinnerTrainerArrayAdapter =
                    new ArrayAdapter<String>(AddSession.this,   android.R.layout.simple_spinner_item,trainerArrayList);
            spinnerTrainerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // The drop down view
            spTrainer.setAdapter(spinnerTrainerArrayAdapter);
            spTrainer.setSelection(selectedTrainerIndex);

            spCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    //Getting the items name
                    String item = parent.getItemAtPosition(position).toString();

                    //Getting the items id.
                    String courseId = searchId(courseInfoList, TAG_COURSE_NAME, TAG_ID, item);
                    GlobalVariables.courseId = courseId;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //Working on Group Spinner
            spGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();

                    //Getting the items id.
                    String groupId = searchId(groupInfoList, TAG_GROUP_NAME, TAG_ID, item);
                    GlobalVariables.groupId = groupId;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //Working on Track spinner
            spTrack.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();

                    //Getting the items id.
                    String trackId = searchId(trackInfoList, TAG_TRACK_NAME, TAG_ID, item);
                    GlobalVariables.evTrackId = trackId;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spTrainer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();

                    //Getting the items id.
                    String trainerId = searchId(trainerInfoList, TAG_TRAINER_NAME, TAG_ID, item);
                    GlobalVariables.trainerId = trainerId;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    //SearchId method for finding the id using the items name
    public String searchId(ArrayList<HashMap<String,String>> item,String TAG,String TAG_ID, String string){
        String id = "";
        for(int i = 0; i<item.size();i++){
            if(string.equals(item.get(i).get(TAG))){
                id = item.get(i).get(TAG_ID);
                break;
            }
        }
        return id;
    }
    public int searchSelectedItemId(ArrayList<String> arrayList,String name){
        int id = 0;
        for(int i = 0; i < arrayList.size(); i++){
            if(arrayList.get(i).equals(name)){
                id = i;
                break;
            }
        }
        return id;
    }


    public void add(View view) {
        options = "add";
        comment = editTextComments.getText().toString();
//        Toast.makeText(AddSession.this, "The id of Hard skill is:" + searchSelectedItemId(courseArrayList,"Hard Skill"), Toast.LENGTH_SHORT).show();
        new InsertEvaluationSession().execute();
    }

    public void edit(View view) {
        options = "edit";

        comment = editTextComments.getText().toString();
        new InsertEvaluationSession().execute();
        goBack(null);
    }

    public void delete(View view) {
        options = "delete";
        comment = editTextComments.getText().toString();
        new InsertEvaluationSession().execute();
        goBack(null);
    }

    public void goBack(View v){
        startActivity(new Intent(AddSession.this, AllSessions.class));
    }

    public void goHome(View v){
        startActivity(new Intent(AddSession.this, MonitorActivity.class));
    }


    private class InsertEvaluationSession extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddSession.this);
            switch (options) {
                case "add":
                    autoId = "0";
                    pDialog.setMessage("Adding the Session ..");
                    break;
                case "edit":
                    pDialog.setMessage("Updating the Session ..");
                    break;
                case "delete":
                    pDialog.setMessage("Deleting the Session ..");
                    break;
            }
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            jsonParser = new JSONParser();
            List<NameValuePair> variables = new ArrayList<>();
            variables.add(new BasicNameValuePair("autoId",autoId));
            variables.add(new BasicNameValuePair("options", options));
            variables.add(new BasicNameValuePair("groupId", GlobalVariables.groupId));
            variables.add(new BasicNameValuePair("trackId", GlobalVariables.evTrackId));
            variables.add(new BasicNameValuePair("courseId", GlobalVariables.courseId));
            variables.add(new BasicNameValuePair("trainerId", GlobalVariables.trainerId));
            variables.add(new BasicNameValuePair("sessionDate", workDate));
            variables.add(new BasicNameValuePair("comment", comment));


            JSONObject json = jsonParser.makeHttpRequest(url1, "POST", variables);
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
            return null;
        }

            @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (success != 0) {
                switch (options) {
                    case "add":
                        Toast.makeText(AddSession.this, "Session Added Successfully.", Toast.LENGTH_SHORT).show();
                        break;
                    case "edit":
                        Toast.makeText(AddSession.this, "Session Updated Successfully.", Toast.LENGTH_SHORT).show();
                        break;
                    case "delete":
                        Toast.makeText(AddSession.this, "Session deleted Successfully.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            pDialog.dismiss();
        }
    }
}
