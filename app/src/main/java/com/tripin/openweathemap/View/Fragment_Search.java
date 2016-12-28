package com.tripin.openweathemap.View;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import com.tripin.openweathemap.Db.DbParam;
import com.tripin.openweathemap.Db.MIDatabaseHandler;
import com.tripin.openweathemap.Model.CurrentWeather;
import com.tripin.openweathemap.Model.HourWeather;
import com.tripin.openweathemap.R;
import com.tripin.openweathemap.utils.CommanDialog;
import com.tripin.openweathemap.utils.CommonMethods;

import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;


/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment_Search extends Fragment implements SearchView.OnQueryTextListener {


    MIDatabaseHandler databaseHandler;
    RecyclerView recyclerView;
    static MyAdapterHourlyDisplay myAdapterHourlyDisplay;
    TextView tv_nodata;

    private SearchView mSearchView;


    public Fragment_Search() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);


        Init(v);

        return v;
    }


    public void Init(View v) {


        try {

            databaseHandler = new MIDatabaseHandler(getActivity());


        } catch (Exception e) {
            e.printStackTrace();
        }


        tv_nodata = (TextView) v.findViewById(R.id.tv_nodata);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);


        SetData();


    }

    //Set Data from table then notify chanage after api responce
    private void SetData() {


        ArrayList<HashMap<String, String>> arraydata_city = databaseHandler.getDB(getActivity()).getTableData(getActivity(), DbParam.Table_Search, null, null, null, null, null);

        if (arraydata_city.size() > 0) {
            tv_nodata.setVisibility(View.GONE);

            final ArrayList<String> cityid = new ArrayList<String>();
            for (int i = 0; i < arraydata_city.size(); i++) {
                cityid.add(arraydata_city.get(i).get(DbParam.CityID));

            }

            new Thread(new Runnable() {
                public void run() {

                    ForecastByCityIDData(cityid.toString().replaceAll("\\[", "").replaceAll("\\]", ""));


                }
            }).start();

        } else {
            tv_nodata.setVisibility(View.VISIBLE);
        }
    }


    private void ForecastData(String search) {


        try {
            URI uri = null;
            final String URL = WebService.BASE_URL + WebService.SearchCity.Sub_API_SearchCity
                    + WebService.SearchCity.City + "=" + search
                    + "&" + WebService.SearchCity.Units + "=" + "metric"
                    + "&" + WebService.SearchCity.AppId + "=" + WebService.APPID
                    + "&" + WebService.SearchCity.Cnt + "=" + "10";


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
                                CurrentWeather currentWeather = gson.fromJson(response.toString(), CurrentWeather.class);
                                addviewCitySearchDB(currentWeather);


                                CommanDialog.displaydialogall(getActivity(), "Success", "Successfully added your city.");


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

    private void ForecastByCityIDData(String CityID) {


        try {
            URI uri = null;
            final String URL = WebService.BASE_URL + WebService.MultiCityWeathe.Sub_API_SearchCity
                    + WebService.MultiCityWeathe.Id + "=" + CityID
                    + "&" + WebService.MultiCityWeathe.Units + "=" + "metric"
                    + "&" + WebService.MultiCityWeathe.AppId + "=" + WebService.APPID;


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
                                HourWeather hourWeather = gson.fromJson(response.toString(), HourWeather.class);
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
        MainActivity.tv_title.setText("Search");
        inflater.inflate(R.menu.menu_search, menu);

        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setIconified(true);
        mSearchView.setOnQueryTextListener(this);

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

    @Override
    public boolean onQueryTextSubmit(final String query) {


        if (query.length() > 0) {


            //Body of your click handler
            new Thread(new Runnable() {
                public void run() {

                    ForecastData(query);

                }
            }).start();
        } else {
            CommanDialog.displaydialogall(getActivity(), "Oops!", "Please Enter City Name!");
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;
    }


    private void addviewCitySearchDB(CurrentWeather data) {


        if (data != null) {


            try {


                Hashtable<String, String> hashtable = new Hashtable<String, String>();

                hashtable.put(DbParam.CityID, data.getId());

                ArrayList<String> arrayList = new ArrayList<String>();
                arrayList.add(DbParam.CityID + "=" + "'" + data.getId() + "'");

                Cursor c_get_data = databaseHandler.getTableDataAsCursor(getActivity(), DbParam.Table_Search, null, arrayList, null, null);

                if (c_get_data.moveToFirst()) {

                    databaseHandler.editData(hashtable, DbParam.CityID, "'" + data.getId() + "'", DbParam.Table_Search);

                } else {

                    databaseHandler.insertData(hashtable, DbParam.Table_Search);
                }

                SetData();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }

}
