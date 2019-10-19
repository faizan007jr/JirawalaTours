package com.example.pushpindersingh.jirawalatours;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ClientActivity extends AppCompatActivity implements GetData {

    RadioButton rbtnyes,rbtnno;
    RadioGroup rgclient;
    LinearLayout llold,llnew;
    EditText et_cmail,et_cfname,et_cmname,et_clname,et_cmobile,et_ccity;
    TextView er_cfn,er_cmn,er_cln,er_cmail,er_cmo,er_ccity;
    RadioButton rbtncmale,rbtncfemale;
    Button btn_newsubmit;
    Spinner sp_old;
    private ProgressDialog progressDialog;

    Bundle bundle;
    String uname,dt,pid,days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        init();

        rgclient.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(rbtnno.isChecked()) {
                    llold.setVisibility(View.GONE);
                    llnew.setVisibility(View.VISIBLE);
                } else if(rbtnyes.isChecked()){
                    llnew.setVisibility(View.GONE);
                    llold.setVisibility(View.VISIBLE);
                    fil_sp_old();
                }
            }
        });

        sp_old.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!sp_old.getSelectedItem().toString().equals("Select")) {
                    final String h = sp_old.getSelectedItem().toString();
                    new AlertDialog.Builder(ClientActivity.this)
                            .setTitle("Alert!")
                            .setMessage("Do You Want To Select " + h + " ?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Intent intent = new Intent(ClientActivity.this,CompleteBookingActivity.class);
                                    intent.putExtra("uname",uname);
                                    intent.putExtra("dt",dt);
                                    intent.putExtra("pid",pid);
                                    intent.putExtra("email",h);
                                    intent.putExtra("days",days);
                                    startActivity(intent);
                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void init() {
        bundle  = getIntent().getExtras();
        uname = bundle.getString("uname");
        dt = bundle.getString("dt");
        pid = bundle.getString("pid");
        days = bundle.getString("days");

        rbtnyes = (RadioButton) findViewById(R.id.rbtnyes);
        rbtnno = (RadioButton) findViewById(R.id.rbtnno);
        rgclient = (RadioGroup) findViewById(R.id.rgclient);
        llold = (LinearLayout) findViewById(R.id.llold);
        llnew = (LinearLayout) findViewById(R.id.llnew);

        et_cmail = (EditText) findViewById(R.id.et_cmail);
        et_cfname = (EditText) findViewById(R.id.et_cfname);
        et_cmname = (EditText) findViewById(R.id.et_cmname);
        et_clname = (EditText) findViewById(R.id.et_clname);
        et_cmobile = (EditText) findViewById(R.id.et_cmobile);
        et_ccity = (EditText) findViewById(R.id.et_ccity);
        rbtncmale = (RadioButton) findViewById(R.id.rbtncmale);
        rbtncfemale = (RadioButton) findViewById(R.id.rbtncfemale);
        btn_newsubmit = (Button) findViewById(R.id.btn_newsubmit);

        er_cmail = (TextView) findViewById(R.id.er_cmail);
        er_cfn = (TextView) findViewById(R.id.er_cfn);
        er_cmn = (TextView) findViewById(R.id.er_cmn);
        er_cln = (TextView) findViewById(R.id.er_cln);
        er_cmo = (TextView) findViewById(R.id.er_cmo);
        er_ccity = (TextView) findViewById(R.id.er_ccity);

        sp_old = (Spinner) findViewById(R.id.sp_old);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
    }

    private void fil_sp_old() {
        BackEndWorker backEndWorker = new BackEndWorker(this);
        backEndWorker.execute("OldClient");
    }

    public void OnNewSubmit(View view) {
        String em = et_cmail.getText().toString();
        String f = et_cfname.getText().toString();
        String m = et_cmname.getText().toString();
        String l = et_clname.getText().toString();
        String mo = et_cmobile.getText().toString();
        String gen = "Male";
        if(rbtncfemale.isChecked()) {
            gen = "Female";
        }
        String c = et_ccity.getText().toString();
        String type = "NewClient";

        er_cmail.setVisibility(View.INVISIBLE);
        er_cfn.setVisibility(View.INVISIBLE);
        er_cmn.setVisibility(View.INVISIBLE);
        er_cln.setVisibility(View.INVISIBLE);
        er_cmo.setVisibility(View.INVISIBLE);
        er_ccity.setVisibility(View.INVISIBLE);

        if(validate()) {
            progressDialog.show();
            BackEndWorker backEndWorker = new BackEndWorker(this);
            backEndWorker.execute(type, em, f, m, l, gen, mo, c);
        } else {
            Toasty.error(this,"Fill The Require Deatils!!",Toast.LENGTH_LONG,true).show();
        }
    }

    @Override
    public void onGetData(String result) {
        String r[] = result.split(" ");
        if(r[0].equals("New")) {
            if(r[1].equals("True")) {
                Intent intent = new Intent(ClientActivity.this,CompleteBookingActivity.class);
                intent.putExtra("uname",uname);
                intent.putExtra("dt",dt);
                intent.putExtra("pid",pid);
                intent.putExtra("days",days);
                intent.putExtra("email",et_cmail.getText().toString());

                progressDialog.dismiss();
                Toasty.success(this,"Inserted Successfully!",Toast.LENGTH_LONG,true).show();
                startActivity(intent);
            } else {
                progressDialog.dismiss();
                Toasty.error(this,"Insertion Failed!",Toast.LENGTH_LONG,true).show();
            }
        } else if(r[0].equals("Old")) {
            r[0] = "Select";
            //Toasty.warning(this,result,Toast.LENGTH_LONG,true).show();
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_1, r);
            sp_old.setAdapter(spinnerArrayAdapter);
        }
    }

    private boolean validate() {
        Boolean re = true;
        if(et_cfname.getText().toString().isEmpty()) {
            er_cfn.setVisibility(View.VISIBLE);
            re = false;
        }
        if(et_cmname.getText().toString().isEmpty()) {
            er_cmn.setVisibility(View.VISIBLE);
            re = false;
        }
        if(et_clname.getText().toString().isEmpty()) {
            er_cln.setVisibility(View.VISIBLE);
            re = false;
        }
        if(et_ccity.getText().toString().isEmpty()) {
            er_ccity.setVisibility(View.VISIBLE);
            re = false;
        }
        if(et_cmobile.getText().toString().isEmpty() || et_cmobile.getText().toString().length() != 10) {
            er_cmo.setVisibility(View.VISIBLE);
            re = false;
        }
        if(et_cmail.getText().toString().isEmpty() || !et_cmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            er_cmail.setVisibility(View.VISIBLE);
            re = false;
        }
        return re;
    }
}
