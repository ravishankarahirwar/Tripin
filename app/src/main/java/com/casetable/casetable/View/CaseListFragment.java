package com.casetable.casetable.View;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.casetable.casetable.Adpter.MyAdapterCase;
import com.casetable.casetable.Adpter.MyAdapterCaselist;
import com.casetable.casetable.Api.WebService;
import com.casetable.casetable.Model.Case;
import com.casetable.casetable.Model.CaseDisplayModel;
import com.casetable.casetable.R;
import com.casetable.casetable.utils.CommanDialog;
import com.casetable.casetable.utils.CommonMethods;
import com.google.gson.Gson;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class CaseListFragment extends Fragment implements SearchView.OnQueryTextListener{


    ListView recyclerView;
    MyAdapterCaselist myAdapterCase;
    Button bt_addcase;
    private SearchView mSearchView;
    List<Case> billArrayList = new ArrayList<>();
    AlphaInAnimationAdapter alphaAdapter;
    ScaleInAnimationAdapter scaleAdapter;
    ArrayList<CaseDisplayModel.Data> caseArrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> hashMapArrayList;
    TextView tv_casecount,tv_totalamount;
    EditText et_search;

    public CaseListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_caselist, container, false);
        getActivity().registerReceiver(RefreshView, new IntentFilter("RefreshView"));


        Init(v);

        return v;
    }


    public void Init(View v) {


        et_search=(EditText)v.findViewById(R.id.et_search);
        et_search.setVisibility(View.GONE);
        myAdapterCase = new MyAdapterCaselist(getActivity(), caseArrayList);

        tv_totalamount=(TextView)v.findViewById(R.id.tv_totalamount);
        tv_casecount=(TextView)v.findViewById(R.id.tv_casecount);
        recyclerView = (ListView) v.findViewById(R.id.rv);
        bt_addcase = (Button) v.findViewById(R.id.bt_addcase);

        if(MainActivity.getFromSP("UserType").equals("Admin"))
        {
            bt_addcase.setVisibility(View.VISIBLE);

        }else
        {
            bt_addcase.setVisibility(View.GONE);
        }

       // recyclerView.setHasFixedSize(true);
      // LinearLayoutManager llm = new LinearLayoutManager(getActivity());
      //  llm.setOrientation(LinearLayoutManager.VERTICAL);


       // alphaAdapter = new AlphaInAnimationAdapter(myAdapterCase);
      //  scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
      //  recyclerView.setAdapter(scaleAdapter);
      //  recyclerView.setLayoutManager(llm);

        SetData();


        bt_addcase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("From", "Add");


                AddCaseFragment addCaseFragment = new AddCaseFragment();
                addCaseFragment.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.add(R.id.container_body, addCaseFragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });


        // Capture Text in EditText
        et_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = et_search.getText().toString().toLowerCase(Locale.getDefault());
               // myAdapterCase.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void SetData() {

        CommonMethods.isProgressShow(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebService.BASE_URL + WebService.ViewCaseList.Sub_URL_ViewCaseList,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            Gson gson = new Gson();
                            CaseDisplayModel caseDisplayModel = gson.fromJson(response.toString(), CaseDisplayModel.class);
                            if (caseDisplayModel.getStatus().equals("1")) {

                                caseArrayList.clear();
                                tv_casecount.setText("" + caseDisplayModel.getData().length);
                                tv_totalamount.setText("" + NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(caseDisplayModel.getTotal())));

                                for (int i=0;i<caseDisplayModel.getData().length;i++)
                                {


                                    caseArrayList.add(new CaseDisplayModel().new Data(

                                            caseDisplayModel.getData()[i].getCaseId(),
                                            caseDisplayModel.getData()[i].getCaseNo(),
                                            caseDisplayModel.getData()[i].getCaseName(),
                                            caseDisplayModel.getData()[i].getCaseAmount(),
                                            caseDisplayModel.getData()[i].getCaseStage(),
                                            caseDisplayModel.getData()[i].getExpactedStage(),
                                            caseDisplayModel.getData()[i].getLawFirm()));

                                }



                                CommonMethods.isProgressHide();
                              // myAdapterCase = new MyAdapterCase(getActivity(), caseArrayList);
                               //scaleAdapter.notifyDataSetChanged();


                                myAdapterCase = new MyAdapterCaselist(getActivity(), caseArrayList);
                                recyclerView.setAdapter(myAdapterCase);
                               // myAdapterCase.notifyDataSetChanged();

                            } else {
                                CommonMethods.isProgressHide();
                                CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), caseDisplayModel.getMessage());
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CommonMethods.isProgressHide();
                        CommanDialog.displaydialogall(getActivity(), "Error:", error.getMessage());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

}


    private void RefreshData() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebService.BASE_URL + WebService.ViewCaseList.Sub_URL_ViewCaseList,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            Gson gson = new Gson();
                            CaseDisplayModel caseDisplayModel = gson.fromJson(response.toString(), CaseDisplayModel.class);
                            if (caseDisplayModel.getStatus().equals("1")) {
                                caseArrayList.clear();

                                tv_casecount.setText("" + caseDisplayModel.getData().length);
                             //   tv_totalamount.setText("" + NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(caseDisplayModel.getTotal())));


                                for (int i=0;i<caseDisplayModel.getData().length;i++)
                                {


                                    caseArrayList.add(new CaseDisplayModel().new Data(

                                            caseDisplayModel.getData()[i].getCaseId(),
                                            caseDisplayModel.getData()[i].getCaseNo(),
                                            caseDisplayModel.getData()[i].getCaseName(),
                                            caseDisplayModel.getData()[i].getCaseAmount(),
                                            caseDisplayModel.getData()[i].getCaseStage(),
                                            caseDisplayModel.getData()[i].getExpactedStage(),
                                            caseDisplayModel.getData()[i].getLawFirm()));

                                }


                                myAdapterCase.notifyDataSetChanged();
                                //scaleAdapter.notifyDataSetChanged();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {



                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        MainActivity.tv_title.setText("Case List");
        inflater.inflate(R.menu.menu_search, menu);

        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        setupSearchView();

        super.onCreateOptionsMenu(menu, inflater);
    }


    private void setupSearchView() {

        mSearchView.setIconifiedByDefault(true);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        mSearchView.setOnQueryTextListener(this);

    }

    public boolean onQueryTextChange(String newText) {

        myAdapterCase.filter(newText);


        return false;
    }

    public boolean onQueryTextSubmit(String query) {


        return false;
    }

    public boolean onClose() {

        return false;
    }

    BroadcastReceiver RefreshView = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub


            try{


               // SetData();
                RefreshData();

            }catch (Exception e)
            {
                e.printStackTrace();
            }


        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(RefreshView);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }


    public static void DeleteCase(final Context context,final String caseid) {

        CommonMethods.isProgressShow(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebService.BASE_URL + WebService.DeleteCase.Sub_URL_DeleteCase,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {

                            Gson gson = new Gson();
                            CaseDisplayModel caseDisplayModel = gson.fromJson(response.toString(), CaseDisplayModel.class);
                            if (caseDisplayModel.getStatus().equals("1")) {


                                CommonMethods.isProgressHide();

                                Intent RefreshUpdateOrder = new Intent("RefreshView");
                                context.sendBroadcast(RefreshUpdateOrder);


                            } else {
                                CommonMethods.isProgressHide();
                                CommanDialog.displaydialogwithouttitlehoutnegative(context, caseDisplayModel.getMessage());
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CommonMethods.isProgressHide();
                        CommanDialog.displaydialogall(context, "Error:", error.getMessage());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(WebService.DeleteCase.CASEID,caseid);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }





}
