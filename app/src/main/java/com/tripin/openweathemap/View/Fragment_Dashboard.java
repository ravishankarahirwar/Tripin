package com.tripin.openweathemap.View;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.tripin.openweathemap.Adpter.MyAdapterHourlyDisplay;
import com.tripin.openweathemap.Api.WebService;
import com.tripin.openweathemap.Model.CurrentWeather;
import com.tripin.openweathemap.Model.HourWeather;
import com.tripin.openweathemap.R;
import com.tripin.openweathemap.utils.CommonMethods;
import com.tripin.openweathemap.utils.TrackGPS;


import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;


/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment_Dashboard extends Fragment {

    private static final int REQUEST_FINE_LOCATION = 1;
    private static String[] Permissions = {Manifest.permission.ACCESS_FINE_LOCATION};

    TextView tv_city, tv_lastupdate, tv_time, tv_temp, tv_desc, tv_main, tv_pressure, tv_visiblity, tv_tempmax, tv_tempmini, tv_humidity, tv_wind, tv_sunrise, tv_sunset;
    ImageView iv_icon;
    RecyclerView recyclerView;
    static MyAdapterHourlyDisplay myAdapterHourlyDisplay;
    FloatingActionButton fab;
    HourWeather hourWeather = new HourWeather("");

    public Fragment_Dashboard() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        MainActivity.mToolbar.setVisibility(View.VISIBLE);

        getActivity().registerReceiver(GetLatLogBroadcast, new IntentFilter("GetLatLogBroadcast"));

        Init(v);

        return v;
    }


    public void Init(View v) {


        iv_icon = (ImageView) v.findViewById(R.id.iv_icon);
        tv_city = (TextView) v.findViewById(R.id.tv_city);
        tv_lastupdate = (TextView) v.findViewById(R.id.tv_lastupdate);
        tv_time = (TextView) v.findViewById(R.id.tv_time);
        tv_temp = (TextView) v.findViewById(R.id.tv_temp);
        tv_desc = (TextView) v.findViewById(R.id.tv_desc);
        tv_main = (TextView) v.findViewById(R.id.tv_main);
        tv_pressure = (TextView) v.findViewById(R.id.tv_pressure);
        tv_visiblity = (TextView) v.findViewById(R.id.tv_visiblity);
        tv_tempmax = (TextView) v.findViewById(R.id.tv_tempmax);
        tv_tempmini = (TextView) v.findViewById(R.id.tv_tempmini);
        tv_humidity = (TextView) v.findViewById(R.id.tv_humidity);
        tv_wind = (TextView) v.findViewById(R.id.tv_wind);
        tv_sunrise = (TextView) v.findViewById(R.id.tv_sunrise);
        tv_sunset = (TextView) v.findViewById(R.id.tv_sunset);

        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Fragment_Search homeFragment = new Fragment_Search();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.add(R.id.container_body, homeFragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        recyclerView = (RecyclerView) v.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);


        checkPermissions();


    }

    private void SetData(String Lon, String Lat) {


        try {
            URI uri = null;
            final String URL = WebService.BASE_URL + WebService.GetCurrentTimeWeather.Sub_API_Weather
                    + WebService.GetCurrentTimeWeather.Lat + "=" + Lat
                    + "&" + WebService.GetCurrentTimeWeather.Lon + "=" + Lon
                    + "&" + WebService.GetCurrentTimeWeather.Units + "=" + "metric"
                    + "&" + WebService.GetCurrentTimeWeather.AppId + "=" + WebService.APPID;


            try {
                uri = new URI(URL.replace(" ", "%20").replace("\n", "%0D%0A"));

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    CommonMethods.isProgressShow(getActivity());


                }
            });


            JsonObjectRequest req = new JsonObjectRequest(uri.toString(), null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        CommonMethods.isProgressHide();

                                    }
                                });


                                Gson gson = new Gson();
                                CurrentWeather currentWeather = gson.fromJson(response.toString(), CurrentWeather.class);
                                tv_city.setText("" + currentWeather.getName() + "," + currentWeather.getSys().getCountry());
                                tv_lastupdate.setText("Last Update : " + CommonMethods.TimestampTOCurrentTime(Long.parseLong(currentWeather.getDt())));
                                tv_time.setText("" + CommonMethods.CurrentTime());
                                tv_temp.setText("" + currentWeather.getMain().getTemp() + (char) 0x00B0);
                                tv_desc.setText("" + currentWeather.getWeather()[0].getDescription());
                                tv_main.setText("" + currentWeather.getWeather()[0].getMain());
                                tv_pressure.setText("Pressure : " + currentWeather.getMain().getPressure() + " hPa");
                                tv_visiblity.setText("Visiblity : " + currentWeather.getMain().getPressure() + " hPa");
                                tv_tempmax.setText("Temp(Max) : " + currentWeather.getMain().getTemp_max() + (char) 0x00B0);
                                tv_tempmini.setText("Temp(Min) : " + currentWeather.getMain().getTemp_min() + (char) 0x00B0);
                                tv_sunrise.setText("" + CommonMethods.Timestamptotime_To_AM_PM(Long.parseLong(currentWeather.getSys().getSunrise())));
                                tv_sunset.setText("" + CommonMethods.Timestamptotime_To_AM_PM(Long.parseLong(currentWeather.getSys().getSunset())));
                                tv_humidity.setText("" + currentWeather.getMain().getHumidity() + " %");
                                tv_wind.setText("" + currentWeather.getWind().getSpeed() + " m/sec");

                                Glide.with(getActivity())
                                        .load("http://openweathermap.org/img/w/" + currentWeather.getWeather()[0].getIcon() + ".png")
                                        .listener(new RequestListener<String, GlideDrawable>() {
                                            @Override
                                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {


                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {


                                                return false;
                                            }
                                        })
                                        .into(iv_icon);


                            } catch (Exception e) {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        CommonMethods.isProgressHide();

                                    }
                                });

                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            CommonMethods.isProgressHide();

                        }
                    });

                    VolleyLog.e("Error: ", error.getMessage());
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.getCache().clear();
            req.setRetryPolicy(new DefaultRetryPolicy(50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(req);


        } catch (Exception ignored) {

            ignored.printStackTrace();

        }
    }


    private void ForecastData(String Lon, String Lat) {


        try {
            URI uri = null;
            final String URL = WebService.BASE_URL + WebService.HistoryDate.Sub_API_HistoryDate
                    + WebService.HistoryDate.Lat + "=" + Lat
                    + "&" + WebService.HistoryDate.Lon + "=" + Lon
                    + "&" + WebService.HistoryDate.Units + "=" + "metric"
                    + "&" + WebService.HistoryDate.AppId + "=" + WebService.APPID
                    + "&" + WebService.HistoryDate.Cnt + "=" + "10";


            try {
                uri = new URI(URL.replace(" ", "%20").replace("\n", "%0D%0A"));

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }


            JsonObjectRequest req = new JsonObjectRequest(uri.toString(), null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {


                                Gson gson = new Gson();
                                hourWeather = gson.fromJson(response.toString(), HourWeather.class);
                                myAdapterHourlyDisplay = new MyAdapterHourlyDisplay(getActivity(), hourWeather);

                                recyclerView.setAdapter(myAdapterHourlyDisplay);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    VolleyLog.e("Error: ", error.getMessage());
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.getCache().clear();
            req.setRetryPolicy(new DefaultRetryPolicy(50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(req);


        } catch (Exception ignored) {

            ignored.printStackTrace();

        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        MainActivity.tv_title.setText("Dashboard");
        inflater.inflate(R.menu.menu_main, menu);


        super.onCreateOptionsMenu(menu, inflater);
    }


    static public void refresh() {


        Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {

                myAdapterHourlyDisplay.notifyDataSetChanged();

            }
        };

        handler.post(r);

    }


    public void checkPermissions() {


        // Verify that all required contact permissions have been granted.
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Contacts permissions have not been granted.
            requestPermissions();


        } else {
            final TrackGPS gps = new TrackGPS(getActivity());
            if (gps.canGetLocation()) {


                new Thread(new Runnable() {
                    public void run() {

                        SetData(Double.toString(gps.getLongitude()), Double.toString(gps.getLatitude()));

                    }
                }).start();


                new Thread(new Runnable() {
                    public void run() {

                        ForecastData(Double.toString(gps.getLongitude()), Double.toString(gps.getLatitude()));

                    }
                }).start();


            } else {

                gps.showSettingsAlert();
            }
        }
    }

    public void requestPermissions() {

        // BEGIN_INCLUDE(contacts_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example, if the request has been denied previously.

            ActivityCompat.requestPermissions(getActivity(), Permissions, REQUEST_FINE_LOCATION);

        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(getActivity(), Permissions, REQUEST_FINE_LOCATION);


        }


    }




    BroadcastReceiver GetLatLogBroadcast = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {


                final TrackGPS gps = new TrackGPS(getActivity());
                if (gps.canGetLocation()) {


                    new Thread(new Runnable() {
                        public void run() {

                            SetData(Double.toString(gps.getLongitude()), Double.toString(gps.getLatitude()));

                        }
                    }).start();


                    new Thread(new Runnable() {
                        public void run() {

                            ForecastData(Double.toString(gps.getLongitude()), Double.toString(gps.getLatitude()));

                        }
                    }).start();

                }else {

                    gps.showSettingsAlert();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    };

    @Override
    public void onDestroy() {

        getActivity().unregisterReceiver(GetLatLogBroadcast);
        super.onDestroy();
    }
}
