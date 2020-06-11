package Model;

import javafx.util.Pair;

import java.util.*;
import java.lang.Double;


public class DataByYear_Model {
    private String year;
    private HashMap<Location_Model, Double> anomalyByLocation;

    public DataByYear_Model(String year){
        this.year = year;
        anomalyByLocation = new HashMap<>();
    }

    public String getYear(){
        return year;
    }

    public boolean addData(Location_Model location, double anomalyValue){
        if(anomalyByLocation.containsKey(location)){
            // The location has already been initialized
            return false;
        }else{
            // The location has not already been initialized
            anomalyByLocation.put(location, anomalyValue);
            /*System.out.print(year + " ");
            System.out.println(anomalyByLocation.get(location));*/
            return true;
        }
    }

    public java.lang.Double getValue(Location_Model location){
        /*
        System.out.println("On est dans la methode getValue de DataByYear\nLe but est de determiner si on peut localiser certains points\n");
        System.out.println("Nous sommes dans la DataByYear = " + year);
        System.out.println("La localisation recherchee : \n"+location.toString());
        System.out.println("\nDans la hashmap, les localisations contenues sont : \n"+anomalyByLocation.keySet());
        System.out.println();
        System.out.println("Est ce que la localisation recherchee fait partie de notre hashmap : " + anomalyByLocation.containsKey(location));
        System.out.println("La valeur associee " + anomalyByLocation.get(location));
        System.out.println("Hash Code de notre objet " + location.hashCode());*/
        if(anomalyByLocation.containsKey(location)){
            return anomalyByLocation.get(location);
        }else{
            return null;
        }
    }

    // TODO : method getEveryAnomaly -> sort every key
    public List<Pair<Location_Model, Double>> getEveryAnomaly(){

        List<Pair<Location_Model, Double>> sortMe = new ArrayList<>();

        for(Location_Model i : anomalyByLocation.keySet()){
            sortMe.add(new Pair<>(i, anomalyByLocation.get(i)));
        }

        sortMe.sort(new Comparator<Pair<Location_Model, Double>>() {
            @Override
            public int compare(Pair<Location_Model, Double> o1, Pair<Location_Model, Double> o2) {
                if(o1.getKey().getLatitude() == o2.getKey().getLatitude()){

                    if(o1.getKey().getLongitude()<o2.getKey().getLongitude()){
                        return (int) (o1.getKey().getLongitude()-o2.getKey().getLongitude());
                    }else{
                        return (int) (o2.getKey().getLongitude()-o1.getKey().getLongitude());
                    }

                }else if(o1.getKey().getLatitude()<o2.getKey().getLatitude()){
                    return (int) (o1.getKey().getLatitude()-o2.getKey().getLatitude());

                }else{
                    return (int) (o2.getKey().getLatitude()-o1.getKey().getLatitude());
                }
            }
        });
        return sortMe;
    }



}
