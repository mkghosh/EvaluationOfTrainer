package com.blogspost.mkumerg.evaluationoftrainer.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.blogspost.mkumerg.evaluationoftrainer.R;
import com.blogspost.mkumerg.evaluationoftrainer.adaptorsource.NameSubjectBaseAdaptor;
import com.blogspost.mkumerg.evaluationoftrainer.adaptorsource.TrainerNameAndSub;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.GlobalVariables;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    private static final String TAG_SUCCESS = "success";
    private int success;

    private int year,month,day;
    ArrayList<TrainerNameAndSub> evaluationSession;

    ProgressDialog pDialog;
    ListView lst;

    JSONParser jsonParser;
    String TAG_TRAINER = "trainerName";
    String TAG_COURSE = "courseName";
    String TAG_TABLE = "sessioninfo";
    String TAG_ID = "sessionAutoId";

    JSONArray trainerSub = null;


    String url = "http://" + GlobalVariables.hostAddress +"/AndroidCheckData/showSession.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        GlobalVariables.workDate = year + "-" + (month+1) + "-" + day;
//        Log.d("workDate: ", GlobalVariables.workDate);
        evaluationSession = new ArrayList<TrainerNameAndSub>();
        new ShowData().execute();
    }

    public class ShowData extends AsyncTask<Void,Void,Void> {
        String message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StudentActivity.this);
            pDialog.setMessage("Please wait ....");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            jsonParser = new JSONParser();
            List<NameValuePair> parameter = new ArrayList<>();
            parameter.add(new BasicNameValuePair("workDate", GlobalVariables.workDate));
//            Log.d("WorkDate: ", year + "-" + (month + 1) + "-" + day);
            parameter.add(new BasicNameValuePair("traineeId", GlobalVariables.traineeId));
//            Log.d("traineeAutoId: ", GlobalVariables.traineeId);

            //getting JSON Object
            JSONObject json = jsonParser.makeHttpRequest(url, "POST", parameter);
            Log.d("Json Obj: ", " > " + json);

            try {
                if (json == null) {
                    message = null;
                } else {
                    message = "notNull";
                    trainerSub = json.getJSONArray(TAG_TABLE);
                    for (int i = 0; i < trainerSub.length(); i++) {
                        JSONObject c = trainerSub.getJSONObject(i);
                        String tName = c.getString(TAG_TRAINER);
                        String sub = c.getString(TAG_COURSE);
//                        GlobalVariables.evaluationSessionId = c.getString(TAG_ID);
                        String sessionId = c.getString(TAG_ID);

                        TrainerNameAndSub t = new TrainerNameAndSub();
                        t.setTrainerName(tName);
                        t.setSubject(sub);
                        t.setSessionId(sessionId);

                        evaluationSession.add(t);

                        Log.d("data: ", t.toString());
                    }
                }
                }catch(JSONException e){
                    e.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lst = (ListView)findViewById(R.id.listviewfortrainername);

            final NameSubjectBaseAdaptor adaptor = new NameSubjectBaseAdaptor(StudentActivity.this,evaluationSession);
            lst.setAdapter(adaptor);



            lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                   GlobalVariables.trainerName = parent.getAdapter().getItem(position).getClass().getName();
                    TrainerNameAndSub session = (TrainerNameAndSub) adaptor.getItem(position);
                    GlobalVariables.trainerName = session.getTrainerName();
                    GlobalVariables.evaluationSessionId = session.getSessionId();
//                    Log.d(" TrainerName: ", " > " + GlobalVariables.trainerName);

                    startActivity(new Intent(StudentActivity.this, SpeakerEvaluation.class));
                }
            });
            pDialog.dismiss();
        }
    }

    public void changePassword(View view){
        final Dialog changePassword = new Dialog(this);
        changePassword.setContentView(R.layout.change_password_dialog);
        changePassword.setTitle("Changing Password");
        final EditText etNewPassword = (EditText)changePassword.findViewById(R.id.edit_text_new_password);
        final EditText etConfirmPassword = (EditText)changePassword.findViewById(R.id.edit_text_confrim_password);
        final Button buttonDone = (Button)changePassword.findViewById(R.id.button_done);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = etNewPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                String url = "http://" + GlobalVariables.hostAddress + "/AndroidCheckData/change_password.php";
                if(newPassword.equals(confirmPassword))
                    new ChangePassword().execute(newPassword, GlobalVariables.traineeId,url);
                changePassword.dismiss();
            }
        });
        changePassword.show();
    }

    private class ChangePassword extends AsyncTask<String ,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(String... params) {
            String password = params[0];
            String autoId = params[1];
            String url = params[2];
            Log.d("Password: ", " > " + password);
            Log.d("TraineeId: ", " > " + autoId);
            Log.d("url: ", " > " + url);

            JSONParser seletUser = new JSONParser();
            List<NameValuePair> pairs = new ArrayList<>();
            pairs.add(new BasicNameValuePair("autoId", autoId));
            pairs.add(new BasicNameValuePair("password",password));
            pairs.add(new BasicNameValuePair("tableName", "traineeinfo"));
            JSONObject jsonObj = seletUser.makeHttpRequest(url,"POST",pairs);

            try {
                success = jsonObj.getInt(TAG_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(success == 1){
                Toast.makeText(StudentActivity.this, "Your Password Changed Successfully.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
