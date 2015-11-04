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
import com.blogspost.mkumerg.evaluationoftrainer.adaptorsource.TrainerBaseAdapt;
import com.blogspost.mkumerg.evaluationoftrainer.adaptorsource.TrainerDetails;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.GlobalVariables;
import com.blogspost.mkumerg.evaluationoftrainer.connectivity.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllTrainers extends AppCompatActivity {

    private static final String TAG_OBJECT_NAME = "trainerinfo";
    private static final String TAG_ID = "autoId";
    private static final String TAG_TRAINER_NAME = "trainerName";
    private static final String TAG_TRAINER_DES = "description";

    String url = "http://" + GlobalVariables.hostAddress + "/AndroidCheckData/trainerinfo.php";

    ProgressDialog pDialog;

    Button buttonNewTrainer;
    JSONArray trainerInfo;

    ArrayList<TrainerDetails> trainerNameAndDescriptions;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_trainers);
        trainerNameAndDescriptions = new ArrayList<>();
        buttonNewTrainer = (Button)findViewById(R.id.btn_new_trainer);
        new SearchTrainer().execute();
    }

    public void newTrainer(View v){
        Intent in = new Intent(AllTrainers.this,AddTrainer.class);
        in.putExtra("trainerName","");
        in.putExtra("trainerDescription","");
        in.putExtra("autoId","");
        in.putExtra("option","add");
        startActivity(in);
    }
    public void goHome(View view){
        startActivity(new Intent(AllTrainers.this,MonitorActivity.class));
    }

    public class SearchTrainer extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(AllTrainers.this);
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
                    trainerInfo = jsonObject.getJSONArray(TAG_OBJECT_NAME);

                    for(int i = 0; i < trainerInfo.length(); i++)
                    {
                        JSONObject c = trainerInfo.getJSONObject(i);
                        String cName = c.getString(TAG_TRAINER_NAME);
                        String cDes = c.getString(TAG_TRAINER_DES);
                        String cId = c.getString(TAG_ID);

                        TrainerDetails nameAndDescription = new TrainerDetails();
                        nameAndDescription.setTrainerName(cName);
                        nameAndDescription.setTrainerDescription(cDes);
                        nameAndDescription.setTrainerId(cId);

                        trainerNameAndDescriptions.add(nameAndDescription);
                        Log.d("Descriptions: ", trainerNameAndDescriptions.toString());
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

            list = (ListView)findViewById(R.id.all_trainers);
            list.setAdapter(new TrainerBaseAdapt(AllTrainers.this,trainerNameAndDescriptions));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TrainerDetails des = (TrainerDetails)parent.getAdapter().getItem(position);
                    Intent intent = new Intent(AllTrainers.this, AddTrainer.class);
                    intent.putExtra("trainerName",des.getTrainerName());
                    intent.putExtra("trainerDescription",des.getTrainerDescription());
                    intent.putExtra("autoId",des.getTrainerId());
                    intent.putExtra("option","update");
                    startActivity(intent);
                }
            });
        }
    }
}
