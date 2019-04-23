package com.rai.nulungi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {


    private LinearLayout linearLayout_setting;
    private FrameLayout frameLayout_profilesettings;
    private TextView btnLogOut;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        frameLayout_profilesettings = (FrameLayout) view.findViewById(R.id.main_frame);

        linearLayout_setting = (LinearLayout) view.findViewById(R.id.linearsettings);

        btnLogOut = (TextView) view.findViewById(R.id.btnLogOut);

        Button btnSetting = (Button) view.findViewById(R.id.buttonsetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_setting.setVisibility(View.VISIBLE);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(),LandingPage.class);
                startActivity(intent);
            }
        });

        frameLayout_profilesettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_setting.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }

}
