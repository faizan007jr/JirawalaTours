package com.example.pushpindersingh.jirawalatours;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity implements TaskCompleted {

    private Button btn_login;
    private EditText et_email,et_password;
    private TextView tv_guest;
    private ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static final String myPref = "userpref";
    public static final String s1 = "uname";
    public static final String s2 = "aduser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        btn_login = (Button) findViewById(R.id.btn_login);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        tv_guest = (TextView) findViewById(R.id.tv_guest);
        tv_guest.setPaintFlags(tv_guest.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);//for underline

        sharedPreferences = getSharedPreferences(myPref, Context.MODE_PRIVATE);
        if(!sharedPreferences.getString(s1,"").isEmpty()) {
            Intent intent = new Intent(LoginActivity.this,AdminActivity.class);
            startActivity(intent);
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Validating ...");
    }

    public void OnLogin(View view) {
        String username = et_email.getText().toString();
        String password = et_password.getText().toString();

        if(!username.isEmpty() && !password.isEmpty()) {
            progressDialog.show();
            String type = "login";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, username, password);
        } else {
            Toasty.error(this,"Insert your Credentials!!",Toast.LENGTH_SHORT,true).show();
        }
    }

    public void onTaskComplete(String result) {
        String ar[] = result.split(" ");
        editor = sharedPreferences.edit();
        if(ar[0].equals("True")) {
            String ad_user = "false";
            if(ar[2].equals("Yes")) {
                ad_user = "true";
            }
            if(ar[1].equals("Yes")) {
                Intent intent = new Intent(this,AdminActivity.class);
                intent.putExtra("uname", et_email.getText().toString());
                intent.putExtra("ad_user",ad_user);
                editor.putString(s1,et_email.getText().toString());
                editor.putString(s2,ad_user);
                progressDialog.cancel();
                progressDialog.hide();
                editor.commit();
                startActivity(intent);
            } else {
                Intent intent = new Intent(this,UserDetailsActivity.class);
                intent.putExtra("uname", et_email.getText().toString());
                intent.putExtra("ad_user",ad_user);
                editor.putString(s1,et_email.getText().toString());
                editor.putString(s2,ad_user);
                progressDialog.cancel();
                progressDialog.hide();
                editor.commit();
                startActivity(intent);
                Toasty.info(this, "First Time", Toast.LENGTH_SHORT,true).show();
            }
        } else if(ar[0].equals("False")){
            progressDialog.cancel();
            progressDialog.hide();
            Toasty.error(this,"Incorrect Username/Password",Toast.LENGTH_SHORT,true).show();
        } else {
            progressDialog.cancel();
            progressDialog.hide();
            Toasty.error(this,"Connection Error!!",Toast.LENGTH_SHORT,true).show();
        }
    }

    public void onBackPressed() {
        finish();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void OnNotEmployee(View view) {
        Toasty.info(this,"Our Packages!!!",Toast.LENGTH_SHORT,true).show();
        Intent intent = new Intent(this,PublicActivity.class);
        startActivity(intent);
    }
}
