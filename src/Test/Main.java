package Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        try {
            FileReader file = new FileReader("tempanomaly_4x4grid.csv");
            BufferedReader bufRead = new BufferedReader(file);

            String line = bufRead.readLine();
            while ( line != null) {
                String[] array = line.split(",");

                // TODO Read the csv file

                line = bufRead.readLine();
            }

            bufRead.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
