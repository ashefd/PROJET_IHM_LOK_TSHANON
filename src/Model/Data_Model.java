package Model;

import java.util.ArrayList;

public class Data_Model {
    private ArrayList<DataByYear_Model> period;
    private ArrayList<Location_Model> knowZone;
    private double min;
    private double max;

    // TODO : constructor
    // TODO : add

    public double getMin(){
        return min;
    }

    public double getMax(){
        return max;
    }

    public java.lang.Double getValue(double latitude, double longitude, String year){
        Location_Model inter = new Location_Model(latitude, longitude);
        for(DataByYear_Model i : period){
            if(i.getYear().equals(year)){
                return i.getValue(inter);
            }
        }
        return null;
    }



}
