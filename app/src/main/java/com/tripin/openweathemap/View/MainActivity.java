package com.tripin.openweathemap.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tripin.openweathemap.Db.MIDatabaseHandler;
import com.tripin.openweathemap.R;

public class MainActivity extends AppCompatActivity {

    MIDatabaseHandler databaseHandler;
    public static Toolbar mToolbar;
    public static TextView tv_title;

    boolean doubleBackToExitPressedOnce = false;
    private NetworkChangeReceiver receiver;
    public static boolean isConnected = false;
    public static RelativeLayout relativelayout_connection;

    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {

            databaseHandler = new MIDatabaseHandler(getApplicationContext());
            databaseHandler.getWritableDatabase();

            pref = getSharedPreferences("NearByPref", 0);
            editor = pref.edit();

        } catch (Exception e) {
            e.printStackTrace();
        }



        relativelayout_connection= (RelativeLayout) findViewById(R.id.relativelayout_connection);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);






        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_title = (TextView) mToolbar.findViewById(R.id.tv_title);
        setSupportActionBar(mToolbar);

        Fragment_Splash fragment_splash = new Fragment_Splash();
        FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
        ft_home.add(R.id.container_body, fragment_splash);
        ft_home.commit();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
               onBackPressed();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() >= 1) {

            fragmentManager.executePendingTransactions();
            fragmentManager.popBackStack();
            fragmentManager.executePendingTransactions();

            return;

        }


        if (fragmentManager.getBackStackEntryCount() == 0) {

            if (doubleBackToExitPressedOnce) {

                super.onBackPressed();
                return;
            }


            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }

    }



    /**
     * *********************************************
     */

    public static  class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {


            // isNetworkAvailable(context);
            if (isOnline(context)) {

                relativelayout_connection.setVisibility(View.GONE);

            } else {

                relativelayout_connection.setVisibility(View.VISIBLE);
            }
        }

        public static boolean isOnline(Context context) {

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in air plan mode it will be null
            return (netInfo != null && netInfo.isConnected());

        }


        private boolean isNetworkAvailable(Context context) {


            ConnectivityManager connectivity = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            if (!isConnected) {

                                relativelayout_connection.setVisibility(View.GONE);
                                isConnected = true;


                                return true;

                            }

                        } else {
                            relativelayout_connection.setVisibility(View.VISIBLE);
                        }
                    }
                }

            } else {
                relativelayout_connection.setVisibility(View.GONE);
                isConnected = false;
                return false;
            }
            return isConnected;

        }


    }

    /**
     * ************************************************
     */


    // ------------------ common method to get values from shared pref -----------------------
    public static String getFromSP(String key) {
        return pref.getString(key, null);
    }

    // ------------------ common method to set values in shared pref -----------------------
    public static void setidInSP(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }


    @Override
    protected void onDestroy() {

        unregisterReceiver(receiver);

        super.onDestroy();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    //moveToNextActivity();

                   // Toast.makeText(getApplicationContext(),"Hello Grant",Toast.LENGTH_LONG).show();

                    Intent sendLatlogBrodacast=new Intent("GetLatLogBroadcast");
                    sendBroadcast(sendLatlogBrodacast);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    //Toast.makeText(getApplicationContext(),"Hello Denied",Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}