package com.blogspost.mkumerg.evaluationoftrainer.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.blogspost.mkumerg.evaluationoftrainer.R;
import com.blogspost.mkumerg.evaluationoftrainer.adaptorsource.SessionBaseAdpt;
import com.blogspost.mkumerg.evaluationoftrainer.adaptorsource.SessionDetails;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.GlobalVariables;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllSessions extends AppCompatActivity {

    private static final String TAG_OBJECT_NAME = "sessioninfo";
    private static final String TAG_ID = "autoId";
    private static final String TAG_GROUP_NAME = "groupName";
    private static final String TAG_TRACK_NAME = "trackName";
    private static final String TAG_COURSE_NAME = "courseName";
    private static final String TAG_TRAINER_NAME = "trainerName";
    private static final String TAG_SESSION_DATE = "sessionDate";
    private static final String TAG_COMMENT = "comment";

    String url = "http://" + GlobalVariables.hostAddress + "/AndroidCheckData/add_evaluation_session.php";

    ProgressDialog pDialog;

    Button buttonNewSession;
    JSONArray courseInfo;

    ArrayList<SessionDetails> sessionDetailses;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sessions);
        sessionDetailses = new ArrayList<>();
        buttonNewSession = (Button)findViewById(R.id.btn_new_sessoin);
        new SearchCourse().execute();
    }

    public void newSession(View v){
        Intent intent = new Intent(AllSessions.this,AddSession.class);
        intent.putExtra("courseName","");
        intent.putExtra("trainerName","");
        intent.putExtra("autoId","");
        intent.putExtra("option","add");
        intent.putExtra("groupName","");
        intent.putExtra("comment","");
        intent.putExtra("trackName","");
        startActivity(intent);
    }
    public void goHome(View view){
        startActivity(new Intent(AllSessions.this,MonitorActivity.class));
    }

    public class SearchCourse extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(AllSessions.this);
            pDialog.setMessage("Loading data .. ");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            ServiceHandler sh = new ServiceHandler();

//            String url1 = url;

            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            if(jsonStr != null)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    courseInfo = jsonObject.getJSONArray(TAG_OBJECT_NAME);

                    for(int i = 0; i < courseInfo.length(); i++)
                    {
                        JSONObject c = courseInfo.getJSONObject(i);
                        SessionDetails sessionDetails = new SessionDetails();
                        sessionDetails.setTrainerName(c.getString(TAG_TRAINER_NAME));
                        sessionDetails.setSessionId(c.getString(TAG_ID));
                        sessionDetails.setCourseName(c.getString(TAG_COURSE_NAME));
                        sessionDetails.setGroupName(c.getString(TAG_GROUP_NAME));
                        sessionDetails.setTrackName(c.getString(TAG_TRACK_NAME));
                        sessionDetails.setWorkDate(c.getString(TAG_SESSION_DATE));
                        sessionDetails.setComment(c.getString(TAG_COMMENT));


                        sessionDetailses.add(sessionDetails);
                        Log.d("Descriptions: ", sessionDetailses.toString());
                    }
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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();

            list = (ListView)findViewById(R.id.all_sessoins);
            list.setAdapter(new SessionBaseAdpt(AllSessions.this,sessionDetailses));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SessionDetails session = (SessionDetails)parent.getAdapter().getItem(position);
                    Intent intent = new Intent(AllSessions.this, AddSession.class);
                    intent.putExtra("courseName",session.getCourseName());
                    intent.putExtra("trainerName",session.getTrainerName());
                    intent.putExtra("autoId",session.getSessionId());
                    intent.putExtra("option","update");
                    intent.putExtra("groupName",session.getGroupName());
                    intent.putExtra("comment",session.getComment());
                    intent.putExtra("trackName",session.getTrackName());
                    startActivity(intent);
                }
            });
        }
    }
}
