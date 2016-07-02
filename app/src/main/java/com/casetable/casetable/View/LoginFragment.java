package com.casetable.casetable.View;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import com.casetable.casetable.Model.CaseAddModel;
import com.casetable.casetable.Model.CaseLoginModel;
import com.casetable.casetable.R;
import com.casetable.casetable.utils.CommanDialog;
import com.casetable.casetable.utils.CommonMethods;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends Fragment {

    EditText edit_username,edit_pass;
    Button button_login;


    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_main, container, false);

        Init(v);

        return  v;
    }

    public void Init(View v) {

        edit_username=(EditText) v.findViewById(R.id.edit_username);
        edit_pass=(EditText) v.findViewById(R.id.edit_pass);
        button_login=(Button) v.findViewById(R.id.button_login);


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!MainActivity.NetworkChangeReceiver.isOnline(getActivity())) {
                    CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), "Please check your internet connection or try again later!");

                } else {

                    if (edit_username.getText().toString().trim().length() == 0) {
                        CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), "Please Enter UserName!");

                    } else if (edit_pass.getText().toString().trim().length() == 0) {

                        CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), "Please Enter Password.!");

                    } else {

                        Loginapi();


                    }
                }

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        MainActivity.tv_title.setText("Login");
        //inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    private void Loginapi() {

        CommonMethods.isProgressShow(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebService.BASE_URL + WebService.Login.Sub_URL_Login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {

                            Gson gson = new Gson();
                            CaseLoginModel caseLoginModel = gson.fromJson(response.toString(), CaseLoginModel.class);
                            if (caseLoginModel.getStatus().equals("1")) {


                                CommonMethods.isProgressHide();
                              //  CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), caseLoginModel.getMessage());

                                MainActivity.setidInSP("UserType", caseLoginModel.getData()[0].getUserType());
                                MainActivity.setidInSP("UserId", caseLoginModel.getData()[0].getUserId());


                                edit_username.setText("");
                                edit_pass.setText("");

                                CaseListFragment caseListFragment = new CaseListFragment();
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.add(R.id.container_body, caseListFragment);
                                ft.commit();


                            } else {

                                edit_username.setText("");
                                edit_pass.setText("");
                                CommonMethods.isProgressHide();
                                CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), caseLoginModel.getMessage());
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

                params.put(WebService.Login.EMAIL, edit_username.getText().toString().trim());
                params.put(WebService.Login.PASSWORD, edit_pass.getText().toString().trim());

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);


    }

}
