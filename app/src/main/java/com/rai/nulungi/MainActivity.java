package com.rai.nulungi;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.homebutton:
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentTransaction.replace(R.id.main_frame, homeFragment, "Home Fragment");
                    fragmentTransaction.commit();
                    setTitle("Home");
                    return true;
                case R.id.history:
                    HistoryFragment historyFragment = new HistoryFragment();
                    fragmentTransaction.replace(R.id.main_frame, historyFragment,"Histori Fragment");
                    fragmentTransaction.commit();
                    setTitle("History");
                    return true;
//                case R.id.inboxbutton:
//                    InboxFragment inboxFragment = new InboxFragment();
//                    fragmentTransaction.replace(R.id.main_frame, inboxFragment, "Inbox Fragment");
//                    fragmentTransaction.commit();
//                    setTitle("Inbox");
//                    return true;
                case R.id.profilebutton:
                    ProfileFragment profileFragment = new ProfileFragment();
                    fragmentTransaction.replace(R.id.main_frame, profileFragment, "Profile Fragment");
                    fragmentTransaction.commit();
                    setTitle("Profile");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
