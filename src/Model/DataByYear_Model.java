package Model;

import javafx.util.Pair;

import java.util.*;
import java.lang.Float;


public class DataByYear_Model {
    private String year;
    private TreeMap<Location_Model, Float> anomalyByLocation;

    public DataByYear_Model(String year){
        this.year = year;
        anomalyByLocation = new TreeMap<>();
    }

    public String getYear(){
        return year;
    }

    // Add the data to the Model
    public boolean addData(Location_Model location, float anomalyValue){
        if(anomalyByLocation.containsKey(location)){  // If there is already a value for the zone given in the parameter
            return false; // Do nothing
        }else{
            anomalyByLocation.put(location, anomalyValue);  // add it to the model
            return true;
        }
    }

    // Give the value associated to the given zone
    public java.lang.Float getValue(Location_Model location){
        if(anomalyByLocation.containsKey(location)){
            return anomalyByLocation.get(location);
        }else{
            return null;
        }
    }

    // Give every anomaly of the model
    public ArrayList<Pair<Location_Model, Float>> getEveryAnomaly(){
        ArrayList<Pair<Location_Model, Float>> sortMe = new ArrayList<>();
        for(Location_Model i : anomalyByLocation.keySet()){
            sortMe.add(new Pair<>(i, anomalyByLocation.get(i)));
        }
        return sortMe;
    }
}
