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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.casetable.casetable.Model.Case;
import com.casetable.casetable.Model.CaseDisplayModel;
import com.casetable.casetable.R;
import com.casetable.casetable.View.AddCaseFragment;
import com.casetable.casetable.View.CaseDetailsFragment;
import com.casetable.casetable.View.CaseListFragment;
import com.casetable.casetable.View.MainActivity;
import com.daimajia.swipe.SwipeLayout;


import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MyAdapterCase extends RecyclerView.Adapter<MyAdapterCase.ViewHolder> {
    List<CaseDisplayModel.Data> dataArrayList =new ArrayList<>();
    private ArrayList<CaseDisplayModel.Data> arraylist;
    static Context context;



    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapterCase(Activity context, List<CaseDisplayModel.Data> data) {

        this.context=context;
        this.dataArrayList = data;
        this.arraylist = new ArrayList<CaseDisplayModel.Data>();
        this.arraylist.addAll(data);

    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case


        TextView tv_casename,tv_caseno,tv_claimamount,tv_currentstage,expactednextstage;
        LinearLayout rl_root;
        Button bt_edit,bt_delete;
        CardView cv;

        ViewHolder(View v) {
            super(v);


                rl_root=(LinearLayout)v.findViewById(R.id.rl_root);
                tv_casename = (TextView) v.findViewById(R.id.tv_casename);
                tv_caseno= (TextView) v.findViewById(R.id.tv_caseno);
                tv_claimamount = (TextView) v.findViewById(R.id.tv_claimamount);
                tv_currentstage = (TextView) v.findViewById(R.id.tv_currentstage);
                expactednextstage= (TextView) v.findViewById(R.id.tv_expactednextstage);
                cv= (CardView) v.findViewById(R.id.cv);

            if(MainActivity.getFromSP("UserType").equals("Admin"))
            {
                bt_edit= (Button) v.findViewById(R.id.bt_edit);
                bt_delete= (Button) v.findViewById(R.id.bt_delete);

            }


        }
    }






    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapterCase.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v=null;

        if(MainActivity.getFromSP("UserType").equals("Admin"))
        {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.caselist_row, parent, false);

        }else
        {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.caselistuser_row, parent, false);

        }


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {

            holder.tv_casename.setText(dataArrayList.get(position).getCaseName());
            holder.tv_caseno.setText(dataArrayList.get(position).getCaseNo());
            holder.tv_claimamount.setText(dataArrayList.get(position).getCaseAmount());
            holder.tv_currentstage.setText(dataArrayList.get(position).getCaseStage());
            holder.expactednextstage.setText(dataArrayList.get(position).getExpactedStage());


        if(MainActivity.getFromSP("UserType").equals("Admin"))
        {
            holder.bt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();

                    bundle.putString("From", "Edit");
                    bundle.putString("CaseID", dataArrayList.get(position).getCaseId());
                    bundle.putString("CaseNo",dataArrayList.get(position).getCaseNo());
                    bundle.putString("CaseName", dataArrayList.get(position).getCaseName());
                    bundle.putString("CaseAmount", dataArrayList.get(position).getCaseAmount());
                    bundle.putString("CaseStage", dataArrayList.get(position).getCaseStage());
                    bundle.putString("CaseNextstage",dataArrayList.get(position).getExpactedStage());
                    bundle.putString("LawFirm", dataArrayList.get(position).getLawFirm());

                    FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    AddCaseFragment addCaseFragment = new AddCaseFragment();
                    addCaseFragment.setArguments(bundle);
                    ft.add(R.id.container_body, addCaseFragment);
                    ft.addToBackStack("null");
                    ft.commit();
                }
            });

            holder.bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CaseListFragment.DeleteCase(context, dataArrayList.get(position).getCaseId());


                }
            });
        }
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putString("From", "Edit");
                bundle.putString("CaseID", dataArrayList.get(position).getCaseId());
                bundle.putString("CaseNo", dataArrayList.get(position).getCaseNo());
                bundle.putString("LawFirm", dataArrayList.get(position).getLawFirm());
                bundle.putString("CaseName", dataArrayList.get(position).getCaseName());
                bundle.putString("CaseAmount", dataArrayList.get(position).getCaseAmount());
                bundle.putString("CaseStage", dataArrayList.get(position).getCaseStage());
                bundle.putString("CaseNextstage", dataArrayList.get(position).getExpactedStage());


                FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                CaseDetailsFragment caseDetailsFragment = new CaseDetailsFragment();
                caseDetailsFragment.setArguments(bundle);
                ft.add(R.id.container_body, caseDetailsFragment);
                ft.addToBackStack("null");
                ft.commit();
            }
        });


    }




    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return dataArrayList.size();
    }


    // Filter Class
    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());

        System.out.println("==bf dataArrayList=" + dataArrayList.size());
        System.out.println("==bf arraylist="+arraylist.size());

        dataArrayList.clear();
        if (charText.length() == 0) {
            dataArrayList.addAll(arraylist);
        } else {

            for(int i=0;i<arraylist.size();i++)
            {
                if (arraylist.get(i).getCaseName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    dataArrayList.add(arraylist.get(i));
                }
            }

        }
        System.out.println("==af dataArrayList=" + dataArrayList.size());
        System.out.println("==af arraylist=" + arraylist.size());
        notifyDataSetChanged();
    }


    private class MyViewHolder {
        TextView tv_custname,tv_current_plan,tv_amount;

    }


}