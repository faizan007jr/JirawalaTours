package com.example.pushpindersingh.jirawalatours;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class CompleteBookingActivity extends AppCompatActivity implements GetData {

    Bundle bundle;
    String uname,dt,pid,cmail,days;
    TextView tv_cmail,tv_ho,tv_ro,tv_date,tv_rent,tv_by;
    Button btn_book;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_booking);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        init();

        tvFill();
    }

    private void init() {
        bundle  = getIntent().getExtras();
        uname = bundle.getString("uname");
        dt = bundle.getString("dt");
        pid = bundle.getString("pid");
        cmail = bundle.getString("email");
        days = bundle.getString("days");

        tv_cmail = (TextView) findViewById(R.id.tv_cmail);
        tv_ho = (TextView) findViewById(R.id.tv_ho);
        tv_ro = (TextView) findViewById(R.id.tv_ro);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_rent = (TextView) findViewById(R.id.tv_rent);
        tv_by = (TextView) findViewById(R.id.tv_by);
        btn_book = (Button) findViewById(R.id.btn_book);
    }

    private void tvFill() {
        tv_cmail.setText("Client E-Mail: " + cmail);
        BackEndWorker backEndWorker = new BackEndWorker(this);
        backEndWorker.execute("Bo",pid);
        tv_date.setText("Booking Date: " + dt);
        tv_by.setText("Book By: " + uname);
        progressDialog.cancel();
        progressDialog.hide();
    }

    public void OnBook(View view) {
        BackEndWorker backEndWorker = new BackEndWorker(this);
        backEndWorker.execute("Book", pid, uname, cmail, dt, days);
    }

    @Override
    public void onGetData(String result) {
        String[] r = result.split(" ");
        switch(r[0]) {
            case "Details":
                r[1] = r[1].replace("_"," ");
                tv_ho.setText("Hotel: " + r[1]);
                tv_ro.setText("Room: " + r[2]);
                tv_rent.setText("Rent: $" + Integer.parseInt(r[3])*Integer.parseInt(days));
                break;
            case "Book":
                if(r[1].equals("True")) {
                    Toasty.success(this,"Booking Successfull!", Toast.LENGTH_LONG,true).show();
                    Intent intent = new Intent(CompleteBookingActivity.this,AdminActivity.class);
                    intent.putExtra("uname",uname);
                    startActivity(intent);
                } else {
                    Toasty.error(this,"Error!!!", Toast.LENGTH_LONG,true).show();
                }
                break;
        }
    }
}
