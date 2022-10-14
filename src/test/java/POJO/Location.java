package POJO;

import java.util.ArrayList;

 public class Location {

    private String postcode;
    private String country;
    private String countryabbrevition;
    private ArrayList<Place> places;

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryabbrevition() {
        return countryabbrevition;
    }

    public void setCountryabbrevition(String countryabbrevition) {
        this.countryabbrevition = countryabbrevition;
    }
}
