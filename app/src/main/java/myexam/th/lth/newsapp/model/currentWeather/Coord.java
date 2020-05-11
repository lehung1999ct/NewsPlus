package myexam.th.lth.newsapp.model.currentWeather;

import androidx.annotation.NonNull;

public class Coord {
    private double lat,lon;

    public Coord() {
    }

    public Coord(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @NonNull
    @Override
    public String toString() {
        return new StringBuilder( "[" ).append( this.lat ).append( "," ).append( this.lon ).append( "]" ).toString();
    }
}
