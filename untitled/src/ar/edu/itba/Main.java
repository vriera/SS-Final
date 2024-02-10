package ar.edu.itba;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Config config = new Config();
        config.initConfig();
        Simulation simulation = new Simulation(config.timeStep, config);
        JSONObject staticData = simulation.serializeStaticData();
        saveInJSONFile(generateUniqueFileName(), staticData);
    }

    private static void saveInJSONFile(String fileName, JSONObject data) {
        try (FileWriter file = new FileWriter("outputs/" + fileName)) {
            file.write(data.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateUniqueFileName() {
        long epochTimeInSeconds = System.currentTimeMillis() / 1000; // Current epoch time in seconds

        // You can customize the date format if needed
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String formattedDate = dateFormat.format(new Date(epochTimeInSeconds * 1000));

        // Create a unique file name using epoch time and formatted date
        String uniqueFileName = "output_" + formattedDate + ".txt";

        return uniqueFileName;
    }

}