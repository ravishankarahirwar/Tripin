package com.casetable.casetable.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.casetable.casetable.Db.MIDatabaseHandler;
import com.casetable.casetable.R;

public class MainActivity extends AppCompatActivity {
    public static Toolbar mToolbar;
    public static TextView tv_title;
    MIDatabaseHandler databaseHandler;
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


        pref = getSharedPreferences("NearByPref", 0);
        editor = pref.edit();

        relativelayout_connection= (RelativeLayout) findViewById(R.id.relativelayout_connection);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);



                } else {

                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);


                }
            }
        });

        try {

            databaseHandler = new MIDatabaseHandler(getApplicationContext());
            databaseHandler.getWritableDatabase();

        } catch (Exception e) {
            e.printStackTrace();
        }


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_title = (TextView) mToolbar.findViewById(R.id.tv_title);
        setSupportActionBar(mToolbar);

        LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
        ft_home.add(R.id.container_body, loginFragment);
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
       /* int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
*/
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
               onBackPressed();
                return true;


            case R.id.action_settings:
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                LoginFragment loginFragment=new LoginFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body, loginFragment).commit();

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


}