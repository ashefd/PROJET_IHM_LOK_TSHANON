package Model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Double;

public class DataByYear_Model {
    private String year;
    private HashMap<Location_Model, Double> anomalyByLocation;

    public DataByYear_Model(String year){
        this.year = year;
        anomalyByLocation = new HashMap<>();
    }

    // method to add
    public boolean addData(Location_Model location, double anomalyValue){
        if(anomalyByLocation.containsKey(location)){
            return false;
        }else{
            anomalyByLocation.put(location, anomalyValue);
            return true;
        }
    }

    public java.lang.Double getValue(Location_Model location){
        if(anomalyByLocation.containsKey(location)){
            return anomalyByLocation.get(location);
        }else{
            return null;
        }
    }

    public String getYear(){
        return year;
    }

    // TODO : method getEveryAnomaly
    public ArrayList<Pair<Location_Model, Double>> getEveryAnomaly(){
        ArrayList<Pair<Location_Model, Double>> result = new ArrayList<>();
        for(Location_Model i : anomalyByLocation.keySet()){
            result.add(new Pair<>(i, anomalyByLocation.get(i)));
        }
        return result;
    }

}
