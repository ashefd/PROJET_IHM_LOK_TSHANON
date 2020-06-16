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

    public boolean addData(Location_Model location, float anomalyValue){
        if(anomalyByLocation.containsKey(location)){
            return false;
        }else{
            anomalyByLocation.put(location, anomalyValue);
            /*System.out.print(year + " ");
            System.out.println(anomalyByLocation.get(location));*/
            return true;
        }
    }

    public java.lang.Float getValue(Location_Model location){
        if(anomalyByLocation.containsKey(location)){
            return anomalyByLocation.get(location);
        }else{
            return null;
        }
    }

    public ArrayList<Pair<Location_Model, Float>> getEveryAnomaly(){
        ArrayList<Pair<Location_Model, Float>> sortMe = new ArrayList<>();
        for(Location_Model i : anomalyByLocation.keySet()){
            sortMe.add(new Pair<>(i, anomalyByLocation.get(i)));
        }
        return sortMe;
    }
}
