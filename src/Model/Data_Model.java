package Model;

import javafx.util.Pair;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.io.*;

public class Data_Model {

    private ArrayList<DataByYear_Model> period;
    private ArrayList<Location_Model> knownZone;
    private double min = Double.POSITIVE_INFINITY;
    private double max = Double.NEGATIVE_INFINITY;

    public Data_Model(String path){
        period = new ArrayList<>();
        knownZone = new ArrayList<>();
        this.readTemperatureFile(path);
    }

    public void readTemperatureFile(String path){
        period = new ArrayList<>();
        knownZone = new ArrayList<>();
        try {
                // FIRST : Opening the CSV file
            FileReader file = new FileReader(path);
            BufferedReader bufRead = new BufferedReader(file);

            double latitude;
            double longitude;

                // SECOND : INITIALISATION OF EVERY DataByYear_Model
            // Here : reading the first line
            String line = bufRead.readLine();
            String[] year = line.split(",");

            // Here : creating every DataByYear_Model in the Data_Model
            for(int i=2; i<year.length; i++){
                period.add(new DataByYear_Model(year[i].substring(1,year[142].length()-1)));
            }

            // Here : Going to the next line for the initialisation of the values
            line = bufRead.readLine();


                // THIRD : INITIALISATION OF Data_Model and DataByYear_Model
            while ( line != null) {
            // Only for the first three line,
                String[] array = line.split(",");

            // Initialise the position of the anomalies
                latitude = Double.parseDouble(array[0]);
                longitude = Double.parseDouble(array[1]);

            // Adding the anomalies to the right position
                for(int i=2; i< array.length; i++){
                    if(array[i].equals("NA")){
                        this.addAnomaly(latitude,longitude, year[i].substring(1,year[i].length()-1),Double.NaN);
                    }else{
                        this.addAnomaly(latitude,longitude, year[i].substring(1,year[i].length()-1),Double.parseDouble(array[i]));
                    }
                }
            // Reading the next Line
                line = bufRead.readLine();
            }

                // FOURTH : CLOSING THE FILE
            bufRead.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addAnomaly(double latitude, double longitude, String year, double anomalyValue){
        if(min > anomalyValue){
            min = anomalyValue;
        }else if(max < anomalyValue){
            max = anomalyValue;
        }

        Location_Model inter = new Location_Model(latitude, longitude);

        boolean inside = false;
        for(Location_Model i : knownZone){
            if(i.equals(inter)){
                inside = true;
            }
        }
        if(!inside){
            knownZone.add(inter);
        }

        for(DataByYear_Model i : period){
            if(i.getYear().equals(year)){
                return i.addData(inter, anomalyValue);
            }
        }
        return false;
    }

    public java.lang.Double getValue(double latitude, double longitude, String year){
        for(DataByYear_Model i : period){
            if(i.getYear().equals(year)){
                System.out.println("Dans la methode getValue de Data_model");
                System.out.println("On cherche la valeur pour " + i.getYear());
                System.out.println();
                return i.getValue(new Location_Model(latitude, longitude));
            }
        }
        return null;
    }

    public double getMin(){
        return min;
    }

    public double getMax(){
        return max;
    }

    public ArrayList<Location_Model> getKnowZone(){
        return knownZone;
    }

    public int getNumberYearKnown(){
        return period.size();
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
