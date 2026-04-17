package com.lettr.services.emails.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

/**
 * Geolocation data derived from the IP address of an open/click event.
 * All fields may be null when the lookup was unavailable or imprecise.
 */
public class GeoIp {

    private String country;
    private String region;
    private String city;
    private Double latitude;
    private Double longitude;
    private String zip;

    @SerializedName("postal_code")
    private String postalCode;

    /** ISO 3166-1 alpha-2 country code. */
    @Nullable public String getCountry() { return country; }
    @Nullable public String getRegion() { return region; }
    @Nullable public String getCity() { return city; }
    @Nullable public Double getLatitude() { return latitude; }
    @Nullable public Double getLongitude() { return longitude; }
    @Nullable public String getZip() { return zip; }
    @Nullable public String getPostalCode() { return postalCode; }

    @Override
    public String toString() {
        return "GeoIp{country='" + country + "', city='" + city + "'}";
    }
}
