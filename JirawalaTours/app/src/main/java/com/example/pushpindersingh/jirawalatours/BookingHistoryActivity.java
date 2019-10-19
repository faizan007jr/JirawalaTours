package com.example.pushpindersingh.jirawalatours;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookingHistoryActivity extends AppCompatActivity implements GetData {

    String uname;
    String ad_user = "false";
    HistoryDatabase historyDatabase;
    ProgressDialog progressDialog;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<HistoryRecyclerItem> historyRecyclerItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        Bundle bundle = getIntent().getExtras();
        uname = bundle.getString("uname");
        ad_user = bundle.getString("ad_user");

        historyDatabase = new HistoryDatabase(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");

        progressDialog.show();

        BackEndWorker backEndWorker = new BackEndWorker(this);
        backEndWorker.execute("History");

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyRecyclerItems = new ArrayList<>();
    }

    @Override
    public void onGetData(String result) {
        String ar[] = result.split(" ");
        String ro[] = removeElement(ar);

        historyDatabase.wipeTable();
        for(int i=0;i<ro.length;i+=6) {
            historyDatabase.insertData(ro[i],ro[i+1],ro[i+2],ro[i+3],ro[i+4],ro[i+5]);
        }

        Cursor res = historyDatabase.getAllData();

        if (res.moveToFirst()) {
            do {
                historyRecyclerItems.add(new HistoryRecyclerItem(
                        res.getString(res.getColumnIndex(HistoryDatabase.COL_1)),
                        res.getString(res.getColumnIndex(HistoryDatabase.COL_2)),
                        res.getString(res.getColumnIndex(HistoryDatabase.COL_3)),
                        res.getString(res.getColumnIndex(HistoryDatabase.COL_4)),
                        res.getString(res.getColumnIndex(HistoryDatabase.COL_5)),
                        res.getString(res.getColumnIndex(HistoryDatabase.COL_6))
                ));
            } while (res.moveToNext());
        }
        res.close();

        mAdapter = new HistoryRecyclerAdapter(historyRecyclerItems,this);
        mRecyclerView.setAdapter(mAdapter);

        progressDialog.cancel();
        progressDialog.hide();
    }

    private String[] removeElement(String ar[]) {
        String a[] = new String[ar.length-1];
        for(int i=1;i<ar.length;i++) {
            a[i-1] = ar[i];
        }
        return a;
    }
}
