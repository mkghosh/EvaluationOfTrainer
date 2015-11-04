package com.blogspost.mkumerg.evaluationoftrainer.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspost.mkumerg.evaluationoftrainer.R;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.GlobalVariables;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MonitorActivity extends AppCompatActivity {

    private static final String TAG_SUCCESS = "success";
    private int success;

    Button btnAddCourse,btnAddSession,btnAddTrainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitor_activity);

        btnAddCourse = (Button)findViewById(R.id.add_course);
        btnAddSession = (Button)findViewById(R.id.add_session);
        btnAddTrainer = (Button)findViewById(R.id.add_trainer);
    }

    public void addCourse(View view){
        startActivity(new Intent(MonitorActivity.this,AllCourses.class));
    }

    public void addSession(View view){
        startActivity(new Intent(MonitorActivity.this,AllSessions.class));
    }

    public void addTrainer(View view){
        startActivity(new Intent(MonitorActivity.this,AllTrainers.class));
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
                    new ChangePassword().execute(newPassword, GlobalVariables.monitorId,url);
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
            Log.d("MonitorId: "," > " + autoId);
            Log.d("url: "," > " + url);

            JSONParser seletUser = new JSONParser();
            List<NameValuePair> pairs = new ArrayList<>();
            pairs.add(new BasicNameValuePair("autoId", autoId));
            pairs.add(new BasicNameValuePair("password",password));
            pairs.add(new BasicNameValuePair("tableName","monitorinfo"));

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
                Toast.makeText(MonitorActivity.this, "Your Password Changed Successfully.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
