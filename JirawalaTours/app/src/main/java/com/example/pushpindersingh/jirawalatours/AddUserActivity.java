package com.example.pushpindersingh.jirawalatours;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddUserActivity extends AppCompatActivity implements TaskCompleted {

    EditText et_user,et_pwd;
    CheckBox cb_admin;
    Button btn_add;
    Spinner sp_branch;
    String uname;
    String ad_user = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        init();
    }

    private void init() {
        et_user = (EditText) findViewById(R.id.et_user);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        cb_admin = (CheckBox) findViewById(R.id.cb_admin);
        btn_add = (Button) findViewById(R.id.btn_add);
        sp_branch = (Spinner) findViewById(R.id.sp_branch);

        Bundle bundle = getIntent().getExtras();
        uname = bundle.getString("uname");
        ad_user = bundle.getString("ad_user");
    }

    public void OnAdd(View view) {
        String u = et_user.getText().toString();
        String p = et_pwd.getText().toString();
        String ad = "FALSE";
        String branch = sp_branch.getSelectedItem().toString();
        String type = "add_user";
        if(cb_admin.isChecked()) {
            ad = "TRUE";
        }
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,u,p,ad,branch);
    }

    @Override
    public void onTaskComplete(String result) {
        if(result.equals("True")) {
            Toast.makeText(this,"User Added",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,AdminActivity.class);
            intent.putExtra("uname",uname);
            intent.putExtra("ad_user",ad_user);
            startActivity(intent);
        } else {
            Toast.makeText(this,result,Toast.LENGTH_LONG).show();
        }
    }
}
