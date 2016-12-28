package com.tripin.openweathemap.View;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripin.openweathemap.R;



/**
 * Created by INTEL on 3/30/2016.
 */
public class Fragment_Splash extends Fragment {

    count c;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup,
                             Bundle savedInstanceState) {

        MainActivity.mToolbar.setVisibility(View.GONE);

        View rootView = inflater.inflate(R.layout.fragment_splash, parentViewGroup, false);


        return rootView;
    }


    class count extends CountDownTimer {

        public count(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {


            Fragment_Dashboard fragment_dashboard = new Fragment_Dashboard();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment_dashboard);
            fragmentTransaction.commit();


        }

        @Override
        public void onTick(long millisUntilFinished) {

        }


    }

    @Override
    public void onDestroy() {

        if (c != null) {
            c.cancel();

        }
        super.onDestroy();
    }


    @Override
    public void onPause() {

        if (c != null) {
            c.cancel();

        }

        super.onPause();
    }


    @Override
    public void onResume() {

        c = new count(3000, 1000);
        c.start();

        super.onPause();
    }


}
