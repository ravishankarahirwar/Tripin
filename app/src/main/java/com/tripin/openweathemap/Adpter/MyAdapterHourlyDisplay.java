package com.tripin.openweathemap.Adpter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.tripin.openweathemap.Model.HourWeather;
import com.tripin.openweathemap.R;
import com.tripin.openweathemap.utils.CommonMethods;

import java.util.ArrayList;
import java.util.List;


public class MyAdapterHourlyDisplay extends RecyclerView.Adapter<MyAdapterHourlyDisplay.ViewHolder>/* implements StickyHeaderAdapter<MyAdapterProductDisplay.HeaderHolder> */ {

   HourWeather weather;
    Context context;

    // ObjectAnimator textColorAnim;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case


        TextView tv_lastupdate,tv_temp, tv_desc, tv_main, tv_pressure, tv_visiblity, tv_tempmax, tv_tempmini, tv_humidity, tv_wind, tv_sunrise, tv_sunset;
        ImageView iv_icon;

        ViewHolder(View v) {
            super(v);


            tv_lastupdate = (TextView) v.findViewById(R.id.tv_lastupdate);
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
            iv_icon = (ImageView) v.findViewById(R.id.iv_icon);


        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapterHourlyDisplay(Activity context, HourWeather weather) {

        this.context = context;
        this.weather = weather;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapterHourlyDisplay.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_hourly, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.tv_lastupdate.setText(""+(weather.getList()[position].getName()!=null?weather.getList()[position].getName()+",":" ")
                +" "+(weather.getList()[position].getSys().getCountry()!=null?weather.getList()[position].getSys().getCountry():"")+" "+
                CommonMethods.Timestamptotime_To_AM_PM(Long.parseLong(weather.getList()[position].getDt())));
        holder.tv_temp.setText("" + weather.getList()[position].getMain().getTemp()+(char) 0x00B0);
        holder.tv_desc.setText("" + weather.getList()[position].getWeather()[0].getDescription());
        holder.tv_main.setText("" + weather.getList()[position].getWeather()[0].getMain());
        holder.tv_pressure.setText("Pressure : " + weather.getList()[position].getMain().getPressure() + " hPa");
        holder.tv_tempmax.setText("Temp(Max) : " + weather.getList()[position].getMain().getTemp_max()+(char) 0x00B0);
        holder.tv_tempmini.setText("Temp(Min) : " + weather.getList()[position].getMain().getTemp_min()+(char) 0x00B0);
        holder.tv_humidity.setText(" " + weather.getList()[position].getMain().getHumidity() + " %");
        holder.tv_wind.setText(" " + weather.getList()[position].getWind().getSpeed() + " m/sec");


        Glide.with(context)
                .load("http://openweathermap.org/img/w/" + weather.getList()[position].getWeather()[0].getIcon() + ".png")
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
                .into(holder.iv_icon);



    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return weather.getList().length;

    }

}