package com.example.pushpindersingh.jirawalatours;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class UserDetailsActivity extends AppCompatActivity implements TaskCompleted {

    EditText et_fname,et_mname,et_lname,et_npwd,et_cpwd,et_dob,et_mobile,et_e_mail;
    RadioButton rbtn_male,rbtn_female;
    Calendar myCalendar = Calendar.getInstance();
    Button btn_submit;
    TextView er_fn,er_mn,er_ln,er_pwd,er_cpwd,er_dob,er_mo,er_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        init();
        getDate();

        et_cpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String passwrd = et_npwd.getText().toString();
                if (et_cpwd.length() > 0 && passwrd.length() > 0) {
                    if(!et_cpwd.getText().toString().equals(passwrd)){
                        //Toasty.error(UserDetailsActivity.this,passwrd,Toast.LENGTH_LONG,true).show();
                        btn_submit.setClickable(false);
                    } else {
                        //Toasty.success(UserDetailsActivity.this,"Password Matches!!!",Toast.LENGTH_LONG,true).show();
                        btn_submit.setClickable(true);
                    }
                }
            }
        });
    }

    private void init() {
        et_dob = (EditText) findViewById(R.id.et_dob);
        et_fname = (EditText) findViewById(R.id.et_fname);
        et_mname = (EditText) findViewById(R.id.et_mname);
        et_lname = (EditText) findViewById(R.id.et_lname);
        et_npwd = (EditText) findViewById(R.id.et_npwd);
        et_cpwd = (EditText) findViewById(R.id.et_cpwd);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_e_mail = (EditText) findViewById(R.id.et_e_mail);
        rbtn_male = (RadioButton) findViewById(R.id.rbtnmale);
        rbtn_female = (RadioButton) findViewById(R.id.rbtnfemale);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setClickable(false);

        er_fn = (TextView) findViewById(R.id.er_fn);
        er_mn = (TextView) findViewById(R.id.er_mn);
        er_ln = (TextView) findViewById(R.id.er_ln);
        er_pwd = (TextView) findViewById(R.id.er_pwd);
        er_cpwd = (TextView) findViewById(R.id.er_cpwd);
        er_dob = (TextView) findViewById(R.id.er_dob);
        er_mo = (TextView) findViewById(R.id.er_mo);
        er_email = (TextView) findViewById(R.id.er_email);
    }

    public void OnSubmit(View view) {
        Bundle bundle = getIntent().getExtras();
        String uname = bundle.getString("uname");
        String fn = et_fname.getText().toString();
        String mn = et_mname.getText().toString();
        String ln = et_lname.getText().toString();
        String pwd = et_npwd.getText().toString();
        String dob = et_dob.getText().toString();
        String mo = et_mobile.getText().toString();
        String em = et_e_mail.getText().toString();
        String gen = "Male";
        if(rbtn_female.isChecked()) {
            gen = "Female";
        }
        String type = "Reg";

        er_fn.setVisibility(View.INVISIBLE);
        er_mn.setVisibility(View.INVISIBLE);
        er_ln.setVisibility(View.INVISIBLE);
        er_pwd.setVisibility(View.INVISIBLE);
        er_cpwd.setVisibility(View.INVISIBLE);
        er_dob.setVisibility(View.INVISIBLE);
        er_mo.setVisibility(View.INVISIBLE);
        er_email.setVisibility(View.INVISIBLE);

        if(validate()) {
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, uname, pwd, fn, mn, ln, dob, mo, em, gen);
        } else {
            Toasty.error(this,"Fill The Require Deatils!!",Toast.LENGTH_LONG,true).show();
        }
    }

    private void getDate() {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        et_dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UserDetailsActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_dob.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onTaskComplete(String result) {
        if(result.equals("True")) {
            Bundle bundle = getIntent().getExtras();
            String uname = bundle.getString("uname");
            Toast.makeText(this,"User Data Updated!",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,AdminActivity.class);
            intent.putExtra("uname",uname);
            startActivity(intent);
        } else {
            Toast.makeText(this,"Error Updating!",Toast.LENGTH_LONG).show();
        }
    }

    private boolean validate() {
        Boolean re = true;
        if(et_fname.getText().toString().isEmpty()) {
            er_fn.setVisibility(View.VISIBLE);
            re = false;
        }
        if(et_mname.getText().toString().isEmpty()) {
            er_mn.setVisibility(View.VISIBLE);
            re = false;
        }
        if(et_lname.getText().toString().isEmpty()) {
            er_ln.setVisibility(View.VISIBLE);
            re = false;
        }
        if(et_npwd.getText().toString().isEmpty()) {
            er_pwd.setVisibility(View.VISIBLE);
            re = false;
        }
        if(et_cpwd.getText().toString().isEmpty()) {
            er_cpwd.setVisibility(View.VISIBLE);
            re = false;
        }
        if(et_dob.getText().toString().isEmpty()) {
            er_dob.setVisibility(View.VISIBLE);
            re = false;
        }
        if(et_mobile.getText().toString().isEmpty() || et_mobile.getText().toString().length() != 10) {
            er_mo.setVisibility(View.VISIBLE);
            re = false;
        }
        if(et_e_mail.getText().toString().isEmpty() || !et_e_mail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            er_email.setVisibility(View.VISIBLE);
            re = false;
        }
        return re;
    }
}
