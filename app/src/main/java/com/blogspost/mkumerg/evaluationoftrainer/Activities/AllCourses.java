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
import com.blogspost.mkumerg.evaluationoftrainer.adaptorsource.CourseNameAndDescription;
import com.blogspost.mkumerg.evaluationoftrainer.adaptorsource.CourseNameAndDescriptionBaseAdpt;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.GlobalVariables;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllCourses extends AppCompatActivity {

    private static final String TAG_OBJECT_NAME = "courseinfo";
    private static final String TAG_ID = "autoId";
    private static final String TAG_COURSE_NAME = "courseName";
    private static final String TAG_COURSE_DES = "description";

    String url = "http://" + GlobalVariables.hostAddress + "/AndroidCheckData/cNameAndDescriptionInfo.php";

    ProgressDialog pDialog;

    Button buttonNewCourse;
    JSONArray courseInfo;

    ArrayList<CourseNameAndDescription> courseNameAndDescriptions;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_courses);
        courseNameAndDescriptions = new ArrayList<>();
        buttonNewCourse = (Button)findViewById(R.id.btn_new_course);
        new SearchCourse().execute();
    }

    public void newCourse(View v){
//        buttonNewCourse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        Intent in = new Intent(AllCourses.this,AddCourse.class);
        in.putExtra("courseName", "");
        in.putExtra("courseDescription", "");
        in.putExtra("autoId","0");
        in.putExtra("option","add");
        startActivity(in);
    }
    public void goHome(View view){
        startActivity(new Intent(AllCourses.this,MonitorActivity.class));
    }

    public class SearchCourse extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(AllCourses.this);
            pDialog.setMessage("Loading data .. ");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            ServiceHandler sh = new ServiceHandler();

//            String url1 = url;

            String jsonStr = sh.makeServiceCall(url,ServiceHandler.GET);
            if(jsonStr != null)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    courseInfo = jsonObject.getJSONArray(TAG_OBJECT_NAME);

                    for(int i = 0; i < courseInfo.length(); i++)
                    {
                        JSONObject c = courseInfo.getJSONObject(i);
                        String cName = c.getString(TAG_COURSE_NAME);
                        String cDes = c.getString(TAG_COURSE_DES);
                        String cId = c.getString(TAG_ID);

                        CourseNameAndDescription nameAndDescription = new CourseNameAndDescription();
                        nameAndDescription.setCourseName(cName);
                        nameAndDescription.setCourseDescription(cDes);
                        nameAndDescription.setAutoId(cId);

                        courseNameAndDescriptions.add(nameAndDescription);
                        Log.d("Descriptions: ",courseNameAndDescriptions.toString());
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

            list = (ListView)findViewById(R.id.all_courses);
            list.setAdapter(new CourseNameAndDescriptionBaseAdpt(AllCourses.this,courseNameAndDescriptions));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CourseNameAndDescription des = (CourseNameAndDescription)parent.getAdapter().getItem(position);
                    Intent intent = new Intent(AllCourses.this, AddCourse.class);
                    intent.putExtra("courseName",des.getCourseName());
                    intent.putExtra("courseDescription",des.getCourseDescription());
                    intent.putExtra("autoId",des.getAutoId());
                    intent.putExtra("option","update");
                    startActivity(intent);
                }
            });
        }
    }
}
