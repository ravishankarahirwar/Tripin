package com.casetable.casetable.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.casetable.casetable.Api.WebService;
import com.casetable.casetable.Db.DbParam;
import com.casetable.casetable.Db.MIDatabaseHandler;
import com.casetable.casetable.Model.CaseAddModel;
import com.casetable.casetable.Model.CaseDisplayModel;
import com.casetable.casetable.Model.CaseEditModel;
import com.casetable.casetable.R;
import com.casetable.casetable.utils.CommanDialog;
import com.casetable.casetable.utils.CommonMethods;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddCaseFragment extends Fragment implements View.OnClickListener {

    EditText edit_case, edit_caseno, edit_lawfirm,edit_caseamount, edit_casestage, edit_excasestage;
    Button button_save;
    MIDatabaseHandler databaseHandler;
    Bundle bundle;
    String CaseID;

    public AddCaseFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addcase, container, false);

        Init(v);

        return v;
    }

    public void Init(View v) {

        bundle = this.getArguments();



        databaseHandler=new MIDatabaseHandler(getActivity());
        edit_case = (EditText) v.findViewById(R.id.edit_case);
        edit_caseno = (EditText) v.findViewById(R.id.edit_caseno);
        edit_lawfirm = (EditText) v.findViewById(R.id.edit_lawfirm);
        edit_caseamount = (EditText) v.findViewById(R.id.edit_caseamount);
        edit_casestage = (EditText) v.findViewById(R.id.edit_casestage);
        edit_excasestage = (EditText) v.findViewById(R.id.edit_excasestage);
        button_save = (Button) v.findViewById(R.id.button_save);
        button_save.setOnClickListener(this);


        if(bundle!=null)
        {


            if(bundle.getString("From").equals("Edit"))
            {
                CaseID=bundle.getString("CaseID");
                edit_case.setText(bundle.getString("CaseName"));
                edit_caseno.setText(bundle.getString("CaseNo"));
                edit_lawfirm.setText(bundle.getString("LawFirm"));

                edit_caseamount.setText(bundle.getString("CaseAmount"));
                edit_casestage.setText(bundle.getString("CaseStage"));
                edit_excasestage.setText(bundle.getString("CaseNextstage"));
            }else
            {
                edit_case.setText("");
                edit_caseno.setText("");
                edit_caseamount.setText("");
                edit_casestage.setText("");
                edit_excasestage.setText("");
            }


        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        if(bundle.getString("From").equals("Edit"))
        {
            MainActivity.tv_title.setText("Edit Case");
        }else
        {
            MainActivity.tv_title.setText("Add Case");
        }

        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.button_save:

                if(!MainActivity.NetworkChangeReceiver.isOnline(getActivity()))
                {
                    CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(),"Please check your internet connection or try again later!");

                }else{

                    if (edit_case.getText().toString().trim().length() == 0) {
                        CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), "Please Enter Case!");

                    }  else if (edit_lawfirm.getText().toString().trim().length() == 0) {

                        CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), "Please Enter LawFirm!");

                    }
                    else if (edit_caseno.getText().toString().trim().length() == 0) {

                        CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), "Please Enter Case No.!");

                    } else if (edit_caseamount.getText().toString().trim().length() == 0) {

                        CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), "Please Enter Claim Amount!");

                    }
                    else if (edit_casestage.getText().toString().trim().length() == 0) {

                        CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), "Please Enter Now/Result!");

                    }
                    else if (edit_excasestage.getText().toString().trim().length() == 0) {

                        CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), "Please Enter Chances of Success!");

                    }
                    else {


                        try {


                            if(bundle.getString("From").equals("Add"))
                            {
                                SetData();
                            }else
                            {
                                EditData();
                            }




                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                }
                break;
        }
    }


    private void SetData() {

        CommonMethods.isProgressShow(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebService.BASE_URL + WebService.AddCase.Sub_URL_AddCase,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            Gson gson = new Gson();
                            CaseAddModel caseAddModel = gson.fromJson(response.toString(), CaseAddModel.class);
                            if (caseAddModel.getStatus().equals("1")) {


                                CommonMethods.isProgressHide();
                                CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), caseAddModel.getMessage());

                                edit_case.setText("");
                                edit_caseno.setText("");
                                edit_caseamount.setText("");
                                edit_casestage.setText("");
                                edit_excasestage.setText("");
                                edit_lawfirm.setText("");

                                Intent RefreshUpdateOrder = new Intent("RefreshView");
                                getActivity().sendBroadcast(RefreshUpdateOrder);



                            } else {
                                CommonMethods.isProgressHide();
                                CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), caseAddModel.getMessage());
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

                params.put(WebService.AddCase.CASENO,edit_caseno.getText().toString().trim());
                params.put(WebService.AddCase.CASELAWFIRM,edit_lawfirm.getText().toString().trim());
                params.put(WebService.AddCase.CASENAME,edit_case.getText().toString().trim());
                params.put(WebService.AddCase.CASEAMOUNT,edit_caseamount.getText().toString().trim());
                params.put(WebService.AddCase.CASEStAGE,edit_casestage.getText().toString().trim());
                params.put(WebService.AddCase.CASENEXTSTAGE,edit_excasestage.getText().toString().trim());

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    private void EditData() {

        CommonMethods.isProgressShow(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebService.BASE_URL + WebService.EditCase.Sub_URL_EditCase,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            Gson gson = new Gson();
                            CaseEditModel caseEditModel = gson.fromJson(response.toString(), CaseEditModel.class);
                            if (caseEditModel.getStatus().equals("1")) {


                                CommonMethods.isProgressHide();
                                CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), caseEditModel.getMessage());

                                edit_case.setText("");
                                edit_caseno.setText("");
                                edit_caseamount.setText("");
                                edit_casestage.setText("");
                                edit_excasestage.setText("");
                                edit_lawfirm.setText("");

                                Intent RefreshUpdateOrder = new Intent("RefreshView");
                                getActivity().sendBroadcast(RefreshUpdateOrder);



                            } else {
                                CommonMethods.isProgressHide();
                                CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), caseEditModel.getMessage());
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

                params.put(WebService.EditCase.CASEID,CaseID);
                params.put(WebService.EditCase.CASENO,edit_caseno.getText().toString().trim());
                params.put(WebService.EditCase.CASELAWFIRM,edit_lawfirm.getText().toString().trim());
                params.put(WebService.EditCase.CASENAME,edit_case.getText().toString().trim());
                params.put(WebService.EditCase.CASEAMOUNT,edit_caseamount.getText().toString().trim());
                params.put(WebService.EditCase.CASEStAGE,edit_casestage.getText().toString().trim());
                params.put(WebService.EditCase.CASENEXTSTAGE,edit_excasestage.getText().toString().trim());

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }
}
