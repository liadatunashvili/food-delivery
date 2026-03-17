package models;

public class LocationInformation {

    protected String locationType;

    public LocationInformation(String locationType) {
        this.locationType = locationType;
    }

    public String getLocationType() {
        return locationType;
    }

    public String formatLocation() {
        return locationType;
    }
}
