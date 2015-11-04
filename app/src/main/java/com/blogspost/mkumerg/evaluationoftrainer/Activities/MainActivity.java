package com.blogspost.mkumerg.evaluationoftrainer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blogspost.mkumerg.evaluationoftrainer.R;

public class MainActivity extends AppCompatActivity {

    Button monitorButton,traineeButton;
    Intent monitorLogin,traineeLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        monitorButton = (Button)findViewById(R.id.monitor_login);
        traineeButton = (Button)findViewById(R.id.trainee_login);
    }

    public void monitorLogin(View view){
        monitorLogin = new Intent(this,LoginScreen.class);
        startActivity(monitorLogin);
    }

    public void traineeLogin(View v){
        traineeLogin = new Intent(this,LoginScreenForStudent.class);
        startActivity(traineeLogin);
    }
}
