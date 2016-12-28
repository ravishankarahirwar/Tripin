package com.tripin.openweathemap.Model;

public class HourWeather {

    private String message;

    private String cnt;

    private String cod;

    public List[] getList() {
        return list;
    }

    public void setList(List[] list) {
        this.list = list;
    }

    private List[] list;

    private City city;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }


    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }


    public HourWeather(String message)
    {
        this.message=message;
    }

    @Override
    public String toString() {
        return "ClassPojo [message = " + message + ", cnt = " + cnt + ", cod = " + cod + ", list = " + list + ", city = " + city + "]";
    }

    public class Sys {

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        private String country="";

        private String pod;

        public String getPod() {
            return pod;
        }

        public void setPod(String pod) {
            this.pod = pod;
        }

        @Override
        public String toString() {
            return "ClassPojo [pod = " + pod + "]";
        }
    }


    public class Wind {
        private String speed;

        private String deg;

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public String getDeg() {
            return deg;
        }

        public void setDeg(String deg) {
            this.deg = deg;
        }

        @Override
        public String toString() {
            return "ClassPojo [speed = " + speed + ", deg = " + deg + "]";
        }
    }

    public class Weather {
        private String id;

        private String icon;

        private String description;

        private String main;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        @Override
        public String toString() {
            return "ClassPojo [id = " + id + ", icon = " + icon + ", description = " + description + ", main = " + main + "]";
        }
    }

    public class List {

        private Clouds clouds;

        private String dt;

        private Wind wind;

        private Sys sys;

        private Weather[] weather;

        private String dt_txt;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name="";


        private Main main;

        public Clouds getClouds() {
            return clouds;
        }

        public void setClouds(Clouds clouds) {
            this.clouds = clouds;
        }

        public String getDt() {
            return dt;
        }

        public void setDt(String dt) {
            this.dt = dt;
        }

        public Wind getWind() {
            return wind;
        }

        public void setWind(Wind wind) {
            this.wind = wind;
        }

        public Sys getSys() {
            return sys;
        }

        public void setSys(Sys sys) {
            this.sys = sys;
        }

        public Weather[] getWeather() {
            return weather;
        }

        public void setWeather(Weather[] weather) {
            this.weather = weather;
        }

        public String getDt_txt() {
            return dt_txt;
        }

        public void setDt_txt(String dt_txt) {
            this.dt_txt = dt_txt;
        }

        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        @Override
        public String toString() {
            return "ClassPojo [clouds = " + clouds + ", dt = " + dt + ", wind = " + wind + ", sys = " + sys + ", weather = " + weather + ", dt_txt = " + dt_txt + ", main = " + main + "]";
        }
    }


    public class Clouds {
        private String all;

        public String getAll() {
            return all;
        }

        public void setAll(String all) {
            this.all = all;
        }

        @Override
        public String toString() {
            return "ClassPojo [all = " + all + "]";
        }
    }


    public class Main {

        private String temp_kf;

        private String humidity;

        private String pressure;

        private String temp_max;

        private String sea_level;

        private String temp_min;

        private String temp;

        private String grnd_level;

        public String getTemp_kf() {
            return temp_kf;
        }

        public void setTemp_kf(String temp_kf) {
            this.temp_kf = temp_kf;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }

        public String getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(String temp_max) {
            this.temp_max = temp_max;
        }

        public String getSea_level() {
            return sea_level;
        }

        public void setSea_level(String sea_level) {
            this.sea_level = sea_level;
        }

        public String getTemp_min() {
            return temp_min;
        }

        public void setTemp_min(String temp_min) {
            this.temp_min = temp_min;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getGrnd_level() {
            return grnd_level;
        }

        public void setGrnd_level(String grnd_level) {
            this.grnd_level = grnd_level;
        }

        @Override
        public String toString() {
            return "ClassPojo [temp_kf = " + temp_kf + ", humidity = " + humidity + ", pressure = " + pressure + ", temp_max = " + temp_max + ", sea_level = " + sea_level + ", temp_min = " + temp_min + ", temp = " + temp + ", grnd_level = " + grnd_level + "]";
        }
    }


    public class City {

        private Coord coord;

        private String id;

        private Sys sys;

        private String name;

        private String population;

        private String country;

        public Coord getCoord() {
            return coord;
        }

        public void setCoord(Coord coord) {
            this.coord = coord;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Sys getSys() {
            return sys;
        }

        public void setSys(Sys sys) {
            this.sys = sys;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPopulation() {
            return population;
        }

        public void setPopulation(String population) {
            this.population = population;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        @Override
        public String toString() {
            return "ClassPojo [coord = " + coord + ", id = " + id + ", sys = " + sys + ", name = " + name + ", population = " + population + ", country = " + country + "]";
        }
    }


    public class Coord {
        private String lon;

        private String lat;

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        @Override
        public String toString() {
            return "ClassPojo [lon = " + lon + ", lat = " + lat + "]";
        }
    }
}
