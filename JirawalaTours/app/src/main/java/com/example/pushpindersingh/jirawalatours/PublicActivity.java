package com.example.pushpindersingh.jirawalatours;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PublicActivity extends AppCompatActivity {

    private ViewPager fragmentContainer;
    private FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);

        fragmentContainer = (ViewPager) findViewById(R.id.fragmentContainer);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        setupViewPager(fragmentContainer);
    }

    private void setupViewPager(ViewPager viewPager) {
        fragmentAdapter.addFragment(new Package1());
        fragmentAdapter.addFragment(new Package2());
        fragmentAdapter.addFragment(new Package3());
        viewPager.setAdapter(fragmentAdapter);
    }
}
