package Model;


public class Location_Model implements Comparable<Location_Model> {
    private Float latitude;
    private Float longitude;

    public Location_Model(float latitude, float longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public float getLatitude(){
        return latitude;
    }

    public float getLongitude(){
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

    @Override
    public int compareTo(Location_Model o) {
        if(this.getLatitude() == o.getLatitude()){
            return (int) (this.getLongitude() - o.getLongitude());
        }else{
            return (int) (this.getLatitude() - o.getLatitude());
        }
    }

}
