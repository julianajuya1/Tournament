package models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileReading {

    public static ArrayList<String> readFile(String route) throws FileNotFoundException, IOException {
        FileReader readDatas = new FileReader(route);
        BufferedReader procesador = new BufferedReader(readDatas);
        String line;
        ArrayList<String> array = new ArrayList<>();
        while ((line = procesador.readLine()) != null) {
            array.add(line);
        }
        procesador.close();
        return array;
    }

}