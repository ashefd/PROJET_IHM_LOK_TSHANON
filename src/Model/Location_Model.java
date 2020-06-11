package Model;

public class Location_Model {
    private Double latitude;
    private Double longitude;

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
        //System.out.println("The equals method is being used");
        if(this == obj) return true;
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        Location_Model other = (Location_Model) obj;
        if(this.latitude != other.getLatitude()){
            return false;
        }else if(this.longitude != other.getLongitude()){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int hash = 31;
        int result = 1;
        result = (int) (hash * result + (latitude + longitude));
        return result;
    }


}
