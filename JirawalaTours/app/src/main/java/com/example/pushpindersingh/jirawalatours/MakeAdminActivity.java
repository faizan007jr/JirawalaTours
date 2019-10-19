package com.example.pushpindersingh.jirawalatours;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class MakeAdminActivity extends AppCompatActivity implements TaskCompleted, GetRecyclerData {

    String uname;
    String ad_user = "false";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<MakeAdminRecyclerItem> makeAdminRecyclerItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_admin);

        Bundle bundle = getIntent().getExtras();
        uname = bundle.getString("uname");
        ad_user = bundle.getString("ad_user");

        String type = "getemp";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type);
    }

    @Override
    public void onTaskComplete(String result) {
        if(result.equals("True")) {
            Toasty.success(this,"Admin Rights Assigned Successfully!",Toast.LENGTH_LONG,true).show();
            Intent intent = new Intent(this,AdminActivity.class);
            intent.putExtra("uname",uname);
            intent.putExtra("ad_user",ad_user);
            startActivity(intent);
        } else if(result.equals("False")) {
            Toasty.error(this,"Error Assigning Admin Rights!",Toast.LENGTH_LONG,true).show();
        } else {
            String urs[] = result.split(" ");
            /*final ArrayAdapter<String> gridViewArrayAdapter = new ArrayAdapter<String>
                    (this,android.R.layout.simple_list_item_1,urs);
            gv_user.setAdapter(gridViewArrayAdapter);*/

            mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            mRecyclerView.setHasFixedSize(true);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            makeAdminRecyclerItems = new ArrayList<>();

            for(int i=0;i<urs.length;i++) {
                makeAdminRecyclerItems.add(new MakeAdminRecyclerItem(urs[i]));
            }

            mAdapter = new MakeAdminRecyclerAdapter(makeAdminRecyclerItems,this);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private void back(String uname) {
        String type = "makeadmin";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,uname);
    }

    @Override
    public void onGetRecyclerData(int id, String name, int rent, int av) {
        //Tried to use one interface for 2 get data
    }

    @Override
    public void onGetMakeAdminRecyclerData(String name) {
        final String uname = name;

        new AlertDialog.Builder(this)
                .setTitle("Alert!")
                .setMessage("Do you really want to give Admin Rights to " + uname + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        back(uname);
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }
}
