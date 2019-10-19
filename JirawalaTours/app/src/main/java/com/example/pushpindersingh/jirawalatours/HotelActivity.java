package com.example.pushpindersingh.jirawalatours;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class HotelActivity extends AppCompatActivity implements GetData,GetRecyclerData {

    String uname;
    String ad_user = "false";
    Spinner sp_loc,sp_ho,sp_state;
    Calendar myCalendar = Calendar.getInstance();
    EditText et_date,et_date_out;
    static String days = new String();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<HotelRecyclerItem> mHotelRecyclerItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        init();
        getDate();

        final BackEndWorker backEndWorker = new BackEndWorker(this);
        backEndWorker.execute("State");

        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!sp_loc.getSelectedItem().toString().equals("Select State")) {
                    String state = sp_state.getSelectedItem().toString();
                    state = state.replace(" ", "_");
                    final BackEndWorker backEndWorker = new BackEndWorker(HotelActivity.this);
                    backEndWorker.execute("Location",state);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        sp_loc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!sp_loc.getSelectedItem().toString().equals("Select Location")) {
                    String location = sp_loc.getSelectedItem().toString();
                    BackEndWorker backEndWorker1 = new BackEndWorker(HotelActivity.this);
                    backEndWorker1.execute("Hotel", location);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        sp_ho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!sp_ho.getSelectedItem().toString().equals("Select Hotel")) {
                    String hotel = sp_ho.getSelectedItem().toString();
                    hotel = hotel.replace(" ", "_");
                    String date = et_date.getText().toString();
                    String date_out = et_date_out.getText().toString();
                    DateTime d1 = new DateTime(date);
                    DateTime d2 = new DateTime(date_out);
                    days = String.valueOf(Days.daysBetween(d1,d2).getDays() + 1);
                    Toasty.info(HotelActivity.this,days + " Days!",Toast.LENGTH_SHORT,true).show();
                    BackEndWorker backEndWorker2 = new BackEndWorker(HotelActivity.this);
                    backEndWorker2.execute("Room", hotel, date, days);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void back(String pid) {
        Intent intent = new Intent(HotelActivity.this,ClientActivity.class);
        intent.putExtra("uname",uname);
        intent.putExtra("dt",et_date.getText().toString());
        intent.putExtra("pid",pid);
        intent.putExtra("days",days);
        startActivity(intent);
    }

    private void init() {
        Bundle bundle = getIntent().getExtras();
        uname = bundle.getString("uname");
        ad_user = bundle.getString("ad_user");

        sp_loc = (Spinner) findViewById(R.id.sp_loc);
        sp_state = (Spinner) findViewById(R.id.sp_state);
        sp_ho = (Spinner) findViewById(R.id.sp_ho);
        et_date = (EditText) findViewById(R.id.et_date);
        et_date_out = (EditText) findViewById(R.id.et_date_out);
    }

    @Override
    public void onGetData(String result) {
        String ar[] = result.split(" ");
        switch (ar[0]) {
            case "Location":
                //String loc[] = removeElement(ar);
                ar[0] = "Select Location";
                final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_1, ar);
                sp_loc.setAdapter(spinnerArrayAdapter);
                break;
            case "Hotel":
                //String ho[] = removeElement(ar);
                ar[0] = "Select Hotel";
                ar = remove_(ar);
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_1, ar);
                sp_ho.setAdapter(arrayAdapter);
                break;
            case "Room":
                String ro[] = removeElement(ar);
                fillRecyclerView(ro);
                break;
            case "State":
                ar[0] = "Select State";
                ar = remove_(ar);
                final ArrayAdapter<String> stateArrayAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_1, ar);
                sp_state.setAdapter(stateArrayAdapter);
                break;
            case "Error!":
                Toasty.error(this,ar[0]+"", Toast.LENGTH_LONG,true).show();
                break;
        }
    }

    private void fillRecyclerView(String[] ro) {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mHotelRecyclerItem = new ArrayList<>();

        for(int i=0;i<ro.length;i+=4) {
            mHotelRecyclerItem.add(new HotelRecyclerItem(Integer.parseInt(ro[i]),ro[i+1],Integer.parseInt(ro[i+2]),Integer.parseInt(ro[i+3])));
        }

        mAdapter = new HotelRecyclerAdapter(mHotelRecyclerItem,this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private String[] remove_(String[] ar) {
        for(int i=0;i<ar.length;i++) {
            ar[i] = ar[i].replace("_"," ");
        }
        return ar;
    }

    private String[] removeElement(String ar[]) {
        String a[] = new String[ar.length-1];
        for(int i=1;i<ar.length;i++) {
            a[i-1] = ar[i];
        }
        return a;
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

        final DatePickerDialog.OnDateSetListener date_out = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCalendar.set(Calendar.YEAR, i);
                myCalendar.set(Calendar.MONTH, i1);
                myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                updateLabe2();
            }
        };

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new  DatePickerDialog(HotelActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        et_date_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et_date.getText().toString().isEmpty()) {
                    int dt = Date.valueOf(et_date.getText().toString()).getDate();
                    int y = Date.valueOf(et_date.getText().toString()).getYear();
                    int m =Date.valueOf(et_date.getText().toString()).getMonth();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(HotelActivity.this, date_out,
                            myCalendar.get(Calendar.YEAR),
                            myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMinDate(Long.valueOf(String.valueOf(new Date(y,m,dt).getTime())));
                    datePickerDialog.show();
                }
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_date.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabe2() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_date_out.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onGetRecyclerData(int id, String name, int rent, int av) {
        if(av != 0) {
            final String pname = name;
            final int pid = id;
            new AlertDialog.Builder(HotelActivity.this)
                    .setTitle("Alert!")
                    .setMessage("Do You Want To Select " + pname + " ?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (et_date.getText().toString().equals("")) {
                                Toasty.error(HotelActivity.this, "Please Select Date", Toast.LENGTH_LONG, true).show();
                            } else {
                                back(pid + "");
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        } else {
            Toasty.warning(HotelActivity.this,"Room Not Available!",Toast.LENGTH_LONG,true).show();
        }
    }

    @Override
    public void onGetMakeAdminRecyclerData(String name) {

    }
}
