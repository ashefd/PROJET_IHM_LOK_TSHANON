package Model;

public class Location_Model {
    private double latitude;
    private double longitude;

    public Location_Model(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    @Override
    public String toString() {
        String a = "Latitude = ";
        a += getLatitude() + "\nLongitude = " + getLongitude() + "\n";
        return a;
    }

    // TODO override equals()
}
