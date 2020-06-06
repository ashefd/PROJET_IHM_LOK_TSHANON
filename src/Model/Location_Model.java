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
        return "Latitude = " + getLatitude() + "\nLongitude = " + getLongitude() + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Location_Model){
            if(this.latitude != ((Location_Model) obj).latitude){
                return false;
            }else if(this.longitude != ((Location_Model) obj).longitude){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    // TODO : methode hashCode()  but not obligatory
    @Override
    public int hashCode() {
        return super.hashCode();
    }


}
