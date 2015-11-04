package com.blogspost.mkumerg.evaluationoftrainer.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspost.mkumerg.evaluationoftrainer.R;
import com.blogspost.mkumerg.evaluationoftrainer.adaptorsource.CourseNameAndDescription;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.GlobalVariables;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mkumerg on 10/14/15.
 */
public class AddCourse extends Activity {

    private static final String TAG_OBJECT_NAME = "courseDescription";
    private static final String TAG_ID = "autoId";
    private static final String TAG_COURSE_NAME = "courseName";
    private static final String TAG_COURSE_DES = "description";
//    private static final String TAG_COURSE_DES = "courseDescription";


    EditText etCourseName, etCourseDescription;
    Button buttonAdd,buttonEdit,buttonDelete;
    String courseName, courseDescription;

    ProgressDialog pDialog;
    String options;
    String autoId = "0";

    ArrayList<CourseNameAndDescription> courseNameAndDescriptions;
    ArrayAdapter<CourseNameAndDescription> adapter;

    JSONParser jsonParser = new JSONParser();
    String optoins;

    String url = "http://" + GlobalVariables.hostAddress + "/AndroidCheckData/addCourse.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course);
        courseNameAndDescriptions = new ArrayList<CourseNameAndDescription>();
        etCourseName = (EditText) findViewById(R.id.et_course_name);
        etCourseDescription = (EditText) findViewById(R.id.et_course_description);
        buttonAdd = (Button)findViewById(R.id.submit_course);
        buttonEdit = (Button)findViewById(R.id.edit_course);
        buttonDelete = (Button)findViewById(R.id.delete_course);

        Intent in = getIntent();
        etCourseName.setText(in.getStringExtra("courseName").toString());
        etCourseDescription.setText(in.getStringExtra("courseDescription").toString());
        autoId = in.getStringExtra("autoId").toString();
        options = in.getStringExtra("option").toString();

        if(options.equals("add"))
        {
            buttonEdit.setEnabled(false);
            buttonDelete.setEnabled(false);
        }
        else
        {
            buttonAdd.setEnabled(false);
        }

    }


    public void add(View view) {
        courseName = etCourseName.getText().toString();
        courseDescription = etCourseDescription.getText().toString();
        options = "add";
        autoId = "0";
        new InsertCourse().execute();
    }

    public void edit(View view) {
        courseName = etCourseName.getText().toString();
        courseDescription = etCourseDescription.getText().toString();
        options = "edit";
        new InsertCourse().execute();
        goBack(null);
    }

    public void delete(View view) {
        courseName = etCourseName.getText().toString();
        courseDescription = etCourseDescription.getText().toString();
        options = "delete";
        new InsertCourse().execute();
        goBack(null);
    }

    public void goBack(View v){
        startActivity(new Intent(AddCourse.this, AllCourses.class));
    }

    public void goHome(View v){
        startActivity(new Intent(AddCourse.this,MonitorActivity.class));
    }

    int success;
    String TAG_SUCCESS = "success";


    private class InsertCourse extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddCourse.this);
            switch (options) {
                case "add":
                    pDialog.setMessage("Adding the course ..");
                    break;
                case "edit":
                    pDialog.setMessage("Updating the course ..");
                    break;
                case "delete":
                    pDialog.setMessage("Deleting the course ..");
                    break;
            }
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonParser = new JSONParser();

            //Creating NameValuePair for inserting data of subGroupA
            List<NameValuePair> variables = new ArrayList<>();
            variables.add(new BasicNameValuePair("courseName", courseName));
            variables.add(new BasicNameValuePair("description", courseDescription));
            variables.add(new BasicNameValuePair("options", options));
            variables.add(new BasicNameValuePair("autoId", autoId));

            //POSTing the value to server through php file
            JSONObject json = jsonParser.makeHttpRequest(url, "POST", variables);

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
                        Toast.makeText(AddCourse.this, "Course Added Successfully.", Toast.LENGTH_SHORT).show();
                        break;
                    case "edit":
                        Toast.makeText(AddCourse.this, "Course Updated Successfully.", Toast.LENGTH_SHORT).show();
                        break;
                    case "delete":
                        Toast.makeText(AddCourse.this, "Course deleted Successfully.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            pDialog.dismiss();
        }
    }
}
