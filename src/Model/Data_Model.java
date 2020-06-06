package Model;

import javafx.util.Pair;

import javax.xml.crypto.Data;
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

    public ArrayList<Location_Model> getKnowZone(){
        return knowZone;
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

    public ArrayList<java.lang.Double> getEveryAnomaly(double latitude, double longitude){
        ArrayList<java.lang.Double> result = new ArrayList<>();
        Location_Model inter = new Location_Model(latitude,longitude);
        for(DataByYear_Model i : period){
            result.add(i.getValue(inter));
        }
        return result;
    }

    public ArrayList<Pair<Location_Model, Double>> getAnomalyPerYear(String year){
        for(DataByYear_Model i : period){
            if(i.getYear().equals(year)){
                return i.getEveryAnomaly();
            }
        }
        return null;
    }

}
