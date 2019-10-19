package com.example.pushpindersingh.jirawalatours;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class AddHotelDetailsActivity extends AppCompatActivity implements GetData {

    String uname;
    String ad_user = "false";
    Spinner spnr_state,spnr_loc,spnr_dav,spnr_fav,spnr_pav,spnr_qav;
    EditText et_hname,et_drent,et_frent,et_prent,et_qrent;
    CheckBox cb_duplex,cb_fduplex,cb_phouse,cb_qroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel_details);

        init();

        final BackEndWorker backEndWorker = new BackEndWorker(this);
        backEndWorker.execute("State");

        spnr_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!spnr_loc.getSelectedItem().toString().equals("Select State")) {
                    String state = spnr_state.getSelectedItem().toString();
                    state = state.replace(" ", "_");
                    final BackEndWorker backEndWorker = new BackEndWorker(AddHotelDetailsActivity.this);
                    backEndWorker.execute("Location",state);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cb_duplex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    et_drent.setVisibility(View.VISIBLE);
                    spnr_dav.setVisibility(View.VISIBLE);
                } else {
                    et_drent.setVisibility(View.INVISIBLE);
                    spnr_dav.setVisibility(View.INVISIBLE);
                }
            }
        });

        cb_fduplex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    et_frent.setVisibility(View.VISIBLE);
                    spnr_fav.setVisibility(View.VISIBLE);
                } else {
                    et_frent.setVisibility(View.INVISIBLE);
                    spnr_fav.setVisibility(View.INVISIBLE);
                }
            }
        });

        cb_phouse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    et_prent.setVisibility(View.VISIBLE);
                    spnr_pav.setVisibility(View.VISIBLE);
                } else {
                    et_prent.setVisibility(View.INVISIBLE);
                    spnr_pav.setVisibility(View.INVISIBLE);
                }
            }
        });

        cb_qroom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    et_qrent.setVisibility(View.VISIBLE);
                    spnr_qav.setVisibility(View.VISIBLE);
                } else {
                    et_qrent.setVisibility(View.INVISIBLE);
                    spnr_qav.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void init() {
        Bundle bundle = getIntent().getExtras();
        uname = bundle.getString("uname");
        ad_user = bundle.getString("ad_user");

        spnr_state = (Spinner) findViewById(R.id.spnr_state);
        spnr_loc = (Spinner) findViewById(R.id.spnr_loc);
        et_hname = (EditText) findViewById(R.id.et_hname);
        et_drent = (EditText) findViewById(R.id.et_drent);
        et_frent = (EditText) findViewById(R.id.et_frent);
        et_prent = (EditText) findViewById(R.id.et_prent);
        et_qrent = (EditText) findViewById(R.id.et_qrent);
        cb_duplex = (CheckBox) findViewById(R.id.cb_duplex);
        cb_fduplex = (CheckBox) findViewById(R.id.cb_fduplex);
        cb_phouse = (CheckBox) findViewById(R.id.cb_phouse);
        cb_qroom = (CheckBox) findViewById(R.id.cb_qroom);
        spnr_dav = (Spinner) findViewById(R.id.spnr_dav);
        spnr_fav = (Spinner) findViewById(R.id.spnr_fav);
        spnr_pav = (Spinner) findViewById(R.id.spnr_pav);
        spnr_qav = (Spinner) findViewById(R.id.spnr_qav);
    }

    @Override
    public void onGetData(String result) {
        String ar[] = result.split(" ");
        switch (ar[0]) {
            case "Location":
                ar[0] = "Select Location";
                final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_1, ar);
                spnr_loc.setAdapter(spinnerArrayAdapter);
                break;
            case "State":
                ar[0] = "Select State";
                final ArrayAdapter<String> stateArrayAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_1, ar);
                spnr_state.setAdapter(stateArrayAdapter);
                break;
            case "Error!":
                Toasty.error(this,ar[0]+"", Toast.LENGTH_LONG,true).show();
                break;
        }
    }

    public void OnNewHotel(View view) {
        String l = spnr_loc.getSelectedItem().toString();
        String h = et_hname.getText().toString();
        BackEndWorker backEndWorker = new BackEndWorker(this);
        backEndWorker.execute("InHotel",l,h);
        if(cb_duplex.isChecked()) {
            AddRoom("Duplex",spnr_dav.getSelectedItem().toString(),et_drent.getText().toString());
        }
        if(cb_fduplex.isChecked()) {
            AddRoom("Full-Duplex",spnr_fav.getSelectedItem().toString(),et_frent.getText().toString());
        }
        if(cb_phouse.isChecked()) {
            AddRoom("Penthouse",spnr_pav.getSelectedItem().toString(),et_prent.getText().toString());
        }
        if(cb_qroom.isChecked()) {
            AddRoom("Quad-Room",spnr_qav.getSelectedItem().toString(),et_qrent.getText().toString());
        }

        Toasty.success(this,"Room Updated Successfully!",Toast.LENGTH_LONG,true).show();
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra("uname",uname);
        intent.putExtra("ad_user",ad_user);
        startActivity(intent);
    }

    private void AddRoom(String r,String av,String re) {
        String h = et_hname.getText().toString();
        BackEndWorker backEndWorker = new BackEndWorker(this);
        backEndWorker.execute("InRoom",h,r,av,re);
    }
}
