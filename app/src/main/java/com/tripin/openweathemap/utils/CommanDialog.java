package com.tripin.openweathemap.utils;

import android.content.Context;
import android.view.View;

import com.tripin.openweathemap.R;

import me.drakeet.materialdialog.MaterialDialog;


/**
 * Created by User_2 on 08-10-2015.
 */
public class CommanDialog {

    static MaterialDialog mMaterialDialog;

    public static void displaydialogwithouttitlehoutnegative(Context context,String message)
    {
        mMaterialDialog = new MaterialDialog(context)

                    .setMessage(message)
                   .setPositiveButton("Ok", new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           mMaterialDialog.dismiss();

                       }
                   });

                    mMaterialDialog.show();
                    mMaterialDialog.setBackgroundResource(R.color.white);
    }


    public static void displaydialogall(Context context,String title,String message)
    {
        mMaterialDialog = new MaterialDialog(context)

                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();

                    }
                })
                .setNegativeButton("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();

                    }
                });

        mMaterialDialog.show();
        mMaterialDialog.setBackgroundResource(R.color.white);
    }


}
