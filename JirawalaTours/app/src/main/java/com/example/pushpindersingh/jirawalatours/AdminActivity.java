package com.example.pushpindersingh.jirawalatours;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class AdminActivity extends AppCompatActivity {

    ImageButton ibtn_add_user,ibtn_make_admin,ibtn_hotel,ibtn_package,ibtn_history,ibtn_add_hotel;
    String uname;
    String ad_user = "false";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String myPref = "userpref";
    public static final String s1 = "uname";
    public static final String s2 = "aduser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        init();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                View view;
                int id = item.getItemId();
                if(id == R.id.nav_logout) {
                    sharedPreferences = getSharedPreferences(myPref, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    Toasty.info(AdminActivity.this,"You Are Now Logged out!!",Toast.LENGTH_LONG,true).show();
                    Intent intent = new Intent(AdminActivity.this,LoginActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_makeadmin) {
                    ibtn_make_admin.performClick();
                } else if (id == R.id.nav_bookhotel) {
                    ibtn_hotel.performClick();
                } else if (id == R.id.nav_package) {
                    ibtn_package.performClick();
                } else if (id == R.id.nav_history) {
                    ibtn_history.performClick();
                } else if (id == R.id.nav_addhotel) {
                    ibtn_add_hotel.performClick();
                } else if (id == R.id.nav_adduser) {
                    ibtn_add_user.performClick();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void  init() {
        sharedPreferences = getSharedPreferences(myPref, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        uname = sharedPreferences.getString(s1,null);
        ad_user = sharedPreferences.getString(s2,null);
        ibtn_add_user = (ImageButton) findViewById(R.id.ibtn_add_user);
        ibtn_make_admin = (ImageButton) findViewById(R.id.ibtn_make_admin);
        ibtn_hotel = (ImageButton) findViewById(R.id.ibtn_hotel);
        ibtn_package = (ImageButton) findViewById(R.id.ibtn_package);
        ibtn_history = (ImageButton) findViewById(R.id.ibtn_history);
        ibtn_add_hotel = (ImageButton) findViewById(R.id.ibtn_add_hotel);
    }

    public void OnAddUser(View view) {
        if(ad_user.equals("true")) {
            Intent intent = new Intent(this, AddUserActivity.class);
            intent.putExtra("uname",uname);
            intent.putExtra("ad_user",ad_user);
            startActivity(intent);
        } else {
            Snackbar snackbar = Snackbar.make(ibtn_add_user,"You Don't have permission!",Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void OnMakeAdmin(View view) {
        if(ad_user.equals("true")) {
            Intent intent = new Intent(this, MakeAdminActivity.class);
            intent.putExtra("uname",uname);
            intent.putExtra("ad_user",ad_user);
            startActivity(intent);
        } else {
            Snackbar snackbar = Snackbar.make(ibtn_add_user,"You Don't have permission!",Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void OnHotel(View view) {
        Intent intent = new Intent(this,HotelActivity.class);
        intent.putExtra("uname",uname);
        intent.putExtra("ad_user",ad_user);
        startActivity(intent);
    }

    public void OnHistory(View view) {
        if(ad_user.equals("true")) {
            Intent intent = new Intent(this, BookingHistoryActivity.class);
            intent.putExtra("uname",uname);
            intent.putExtra("ad_user",ad_user);
            startActivity(intent);
        } else {
            Snackbar snackbar = Snackbar.make(ibtn_add_user,"You Don't have permission!",Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void OnAddHotel(View view) {
        if(ad_user.equals("true")) {
            Intent intent = new Intent(this, AddHotelDetailsActivity.class);
            intent.putExtra("uname",uname);
            intent.putExtra("ad_user",ad_user);
            startActivity(intent);
        } else {
            Snackbar snackbar = Snackbar.make(ibtn_add_user,"You Don't have permission!",Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    public void OnPackage(View view) {
        Snackbar snackbar = Snackbar.make(ibtn_add_user,"Feature isn't Completely Working!!",Snackbar.LENGTH_LONG);
        snackbar.show();
        Intent intent = new Intent(this, PublicActivity.class);
        intent.putExtra("uname",uname);
        intent.putExtra("ad_user",ad_user);
        startActivity(intent);
    }
}
