package com.tripin.openweathemap.Api;

public class WebService {



    //LIVE URLs
    public static String BASE_URL="http://api.openweathermap.org/data/2.5/";
    public static String APPID="0a5de1d6a386df47697f596710b8d825";



    public static class GetCurrentTimeWeather {
        // URL
        public static final String Sub_API_Weather = "weather?";
        public static final String Lat = "lat";
        public static final String Lon = "lon";
        public static final String AppId = "APPID";
        public static final String Units = "units";



    }


    public static class HistoryDate {
        // URL
        public static final String Sub_API_HistoryDate = "forecast?";
        public static final String Lat = "lat";
        public static final String Lon = "lon";
        public static final String AppId = "APPID";
        public static final String Units = "units";
        public static final String Cnt = "cnt";




    }

    public static class SearchCity {
        // URL
        public static final String Sub_API_SearchCity = "weather?";
        public static final String City = "q";
        public static final String AppId = "APPID";
        public static final String Units = "units";
        public static final String Cnt = "cnt";


    }


    public static class MultiCityWeathe {
        // URL
        public static final String Sub_API_SearchCity = "group?";
        public static final String Id = "id";
        public static final String AppId = "APPID";
        public static final String Units = "units";

    }









}
