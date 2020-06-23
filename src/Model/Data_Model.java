package Model;

import javafx.util.Pair;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.io.*;

public class Data_Model {

    private ArrayList<DataByYear_Model> period;
    private ArrayList<Location_Model> knownZone;
    private float min = Float.POSITIVE_INFINITY;
    private float max = Float.NEGATIVE_INFINITY;

    public Data_Model(String path){
        period = new ArrayList<>();
        knownZone = new ArrayList<>();
        this.readTemperatureFile(path);
    }

    // Methode to read the Temperature File CSV
    public void readTemperatureFile(String path){
        period = new ArrayList<>();
        knownZone = new ArrayList<>();
        try {
                // FIRST : Opening the CSV file
            FileReader file = new FileReader(path);
            BufferedReader bufRead = new BufferedReader(file);

            float latitude;
            float longitude;

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
                latitude = Float.parseFloat(array[0]);
                longitude = Float.parseFloat(array[1]);

            // Adding the anomalies to the right position
                for(int i=2; i< array.length; i++){
                    if(array[i].equals("NA")){
                        this.addAnomaly(latitude,longitude, year[i].substring(1,year[i].length()-1),Float.NaN);
                    }else{
                        this.addAnomaly(latitude,longitude, year[i].substring(1,year[i].length()-1),Float.parseFloat(array[i]));
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

    // Adding the value of an anomaly to a particular year and zone
    public boolean addAnomaly(float latitude, float longitude, String year, float anomalyValue){
        if(min > anomalyValue){
            min = anomalyValue;
        }else if(max < anomalyValue){
            max = anomalyValue;
        }

        Location_Model inter = new Location_Model(latitude, longitude);

        boolean inside = false;

        // Adding a position to the knownZone ArrayList if it is not already inside
        for(Location_Model i : knownZone){
            if(i.equals(inter)){
                inside = true;
            }
        }
        if(!inside){
            knownZone.add(inter);
        }

        // Adding the value of the anomaly to the DataByYear Model
        for(DataByYear_Model i : period){
            if(i.getYear().equals(year)){
                return i.addData(inter, anomalyValue);
            }
        }
        return false;
    }

    // Return the value of a position in a particular year
    public java.lang.Float getValue(float latitude, float longitude, String year){
        for(DataByYear_Model i : period){
            if(i.getYear().equals(year)){  // if that is the year that we want
                return i.getValue(new Location_Model(latitude, longitude));
            }
        }
        return null;
    }

    public float getMin(){
        return min;
    }

    public float getMax(){
        return max;
    }

    // Return the position that is known in the CSV file
    public ArrayList<Location_Model> getKnowZone(){
        return knownZone;
    }

    // Return the number of year that is considered for this project
    public int getNumberYearKnown(){
        return period.size();
    }

    // Return an arraylist which represents the anomalies of a position from 1880 to 2020
    public ArrayList<java.lang.Float> getEveryAnomaly(float latitude, float longitude){
        ArrayList<java.lang.Float> result = new ArrayList<>();
        Location_Model inter = new Location_Model(latitude,longitude);
        for(DataByYear_Model i : period){
            result.add(i.getValue(inter));
        }
        return result;
    }

    // Return an arraylist which represents the anomalies of the globe of a particular year
    public ArrayList<Pair<Location_Model, Float>> getAnomalyPerYear(String year){
        for(DataByYear_Model i : period){
            if(i.getYear().equals(year)){
                return i.getEveryAnomaly();
            }
        }
        return null;
    }

}
