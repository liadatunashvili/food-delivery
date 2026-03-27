package models;

import java.util.Objects;

public class Address extends LocationInformation {

    private String city;
    private String street;
    private String details;

    public Address(String city, String street, String details) {
        super("Address");
        this.city = city;
        this.street = street;
        this.details = details;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String fullAddress() {
        return city + ", " + street + ", " + details;
    }


    @Override
    public String formatLocation() {
        return city + ", " + street + ", " + details;
    }

    @Override
    public String toString() {
        return formatLocation();
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return city.equals(address.city) &&
                street.equals(address.street) &&
                details.equals(address.details);
    }

    @Override
    public int hashCode(){
        return Objects.hash(city, street, details);
    }

}

