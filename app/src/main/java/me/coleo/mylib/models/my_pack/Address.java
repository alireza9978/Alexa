package me.coleo.mylib.models.my_pack;

public class Address {

    private double lat;
    private double lng;
    private Card card;

    public Address(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
        card = new Card(14);
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

}
