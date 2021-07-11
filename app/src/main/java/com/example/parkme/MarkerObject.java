package com.example.parkme;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class MarkerObject implements Serializable {
    private String address;
    private String title;

    public MarkerObject() {
    }

    public MarkerObject(String address, String title, String free, LatLng latLng) {
        this.address = address;
        this.title = title;
        this.free = free;
        this.latLng = latLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    private String free;
    private LatLng latLng;

}
