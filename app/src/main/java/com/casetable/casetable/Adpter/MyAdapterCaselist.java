package com.casetable.casetable.Adpter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.casetable.casetable.Model.CaseDisplayModel;
import com.casetable.casetable.R;
import com.casetable.casetable.View.AddCaseFragment;
import com.casetable.casetable.View.CaseDetailsFragment;
import com.casetable.casetable.View.CaseListFragment;
import com.casetable.casetable.View.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MyAdapterCaselist extends BaseAdapter {
    List<CaseDisplayModel.Data> dataArrayList=new ArrayList<CaseDisplayModel.Data>();;
    public ArrayList<CaseDisplayModel.Data> arraylist=new ArrayList<CaseDisplayModel.Data>();;
    static Context context;
    LayoutInflater inflater = null;




    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapterCaselist(Context context, List<CaseDisplayModel.Data> data) {
        this.inflater = LayoutInflater.from(context);
        this.context=context;
        this.dataArrayList = data;
        this.arraylist.addAll(dataArrayList);


    }


    @Override
    public int getCount() {

        return dataArrayList.size();
    }

    @Override
    public CaseDisplayModel.Data getItem(int position) {
        return dataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        MyViewHolder mViewHolder;


        if (convertView == null) {



            if(MainActivity.getFromSP("UserType").equals("Admin"))
            {
                convertView = inflater.inflate(R.layout.caselist_row, null);
                mViewHolder = new MyViewHolder();

                mViewHolder.bt_edit= (Button) convertView.findViewById(R.id.bt_edit);
                mViewHolder.bt_delete= (Button) convertView.findViewById(R.id.bt_delete);


            }else
            {
                convertView = inflater.inflate(R.layout.caselistuser_row, null);
                mViewHolder = new MyViewHolder();

            }



            mViewHolder.rl_root=(LinearLayout)convertView.findViewById(R.id.rl_root);
            mViewHolder.tv_casename = (TextView) convertView.findViewById(R.id.tv_casename);
            mViewHolder.tv_caseno= (TextView) convertView.findViewById(R.id.tv_caseno);
            mViewHolder.tv_claimamount = (TextView) convertView.findViewById(R.id.tv_claimamount);
            mViewHolder.tv_currentstage = (TextView) convertView.findViewById(R.id.tv_currentstage);
            mViewHolder.expactednextstage= (TextView) convertView.findViewById(R.id.tv_expactednextstage);
            mViewHolder.cv= (CardView) convertView.findViewById(R.id.cv);



            convertView.setTag(mViewHolder);
        }
        else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        final CaseDisplayModel.Data bData=getItem(position);




        mViewHolder.tv_casename.setText(bData.getCaseName());
        mViewHolder.tv_caseno.setText(bData.getCaseNo());
        mViewHolder.tv_claimamount.setText(bData.getCaseAmount());
        mViewHolder.tv_currentstage.setText(bData.getCaseStage());
        mViewHolder.expactednextstage.setText(bData.getExpactedStage());


        if(MainActivity.getFromSP("UserType").equals("Admin"))
        {
            mViewHolder.bt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();

                    bundle.putString("From", "Edit");
                    bundle.putString("CaseID", bData.getCaseId());
                    bundle.putString("CaseNo",bData.getCaseNo());
                    bundle.putString("CaseName", bData.getCaseName());
                    bundle.putString("CaseAmount", bData.getCaseAmount());
                    bundle.putString("CaseStage", bData.getCaseStage());
                    bundle.putString("CaseNextstage",bData.getExpactedStage());
                    bundle.putString("LawFirm", bData.getLawFirm());

                    FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    AddCaseFragment addCaseFragment = new AddCaseFragment();
                    addCaseFragment.setArguments(bundle);
                    ft.add(R.id.container_body, addCaseFragment);
                    ft.addToBackStack("null");
                    ft.commit();
                }
            });

            mViewHolder.bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CaseListFragment.DeleteCase(context, dataArrayList.get(position).getCaseId());


                }
            });
        }
        mViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putString("From", "Edit");
                bundle.putString("CaseID", bData.getCaseId());
                bundle.putString("CaseNo", bData.getCaseNo());
                bundle.putString("LawFirm", bData.getLawFirm());
                bundle.putString("CaseName", bData.getCaseName());
                bundle.putString("CaseAmount", bData.getCaseAmount());
                bundle.putString("CaseStage", bData.getCaseStage());
                bundle.putString("CaseNextstage", bData.getExpactedStage());


                FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                CaseDetailsFragment caseDetailsFragment = new CaseDetailsFragment();
                caseDetailsFragment.setArguments(bundle);
                ft.add(R.id.container_body, caseDetailsFragment);
                ft.addToBackStack("null");
                ft.commit();
            }
        });



        return convertView;



    }

    private class MyViewHolder {
        TextView tv_casename,tv_caseno,tv_claimamount,tv_currentstage,expactednextstage;
        LinearLayout rl_root;
        Button bt_edit,bt_delete;
        CardView cv;

    }

    /// Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        dataArrayList.clear();
        if (charText.length() == 0) {
            dataArrayList.addAll(arraylist);
        } else {



            for(int i=0;i<arraylist.size();i++)
            {
                if (arraylist.get(i).getCaseName().toLowerCase(Locale.getDefault())
                        .contains(charText) || arraylist.get(i).getCaseNo().toLowerCase(Locale.getDefault())
                        .contains(charText) ) {
                    dataArrayList.add(arraylist.get(i));
                }
            }

       }

        notifyDataSetChanged();
    }



}