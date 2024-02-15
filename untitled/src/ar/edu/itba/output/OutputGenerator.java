package ar.edu.itba.output;


import ar.edu.itba.Config;
import ar.edu.itba.models.Car;
import ar.edu.itba.models.Node;
import ar.edu.itba.models.Road;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OutputGenerator {

    private final static Integer CHUNK_SIZE = 1000;
    private final static String DIRECTORY = "./outputs";
    private static FileWriter SNAPSHOT_WRITER;
//    private static boolean comma = false;
    //Returns folder

    public static String generateUniqueFolderName() {
        long epochTimeInSeconds = System.currentTimeMillis() / 1000; // Current epoch time in seconds

        // You can customize the date format if needed
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return dateFormat.format(new Date(epochTimeInSeconds * 1000));
    }
    public static String createStaticInfo(String folder, JSONObject data) {
        File dir = new File(DIRECTORY);

        dir.mkdir();
        if (folder == null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm-ss");
            LocalDateTime time = LocalDateTime.now();
            folder = dtf.format(time);
        }
//        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject = data;
        String filePath = DIRECTORY + "/" + folder + "/static.json";
        File dir2 = new File(DIRECTORY + "/" + folder);
        dir2.mkdir();

        File myObj = new File(filePath);
        try {
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                FileWriter myWriter = new FileWriter(filePath);
                myWriter.write(jsonObject.toString());
                myWriter.close();
            } else {
                System.out.println("File not created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return folder;
    }

    public static List<JSONObject> saveSnapshot(Set<Car> cars,
                                                List<JSONObject> pastSnapshots,
                                                String folder) {
        if (pastSnapshots == null) {
            pastSnapshots = new ArrayList<>();
        }
        JSONObject snapshot = new JSONObject();
        String format = "%.2f";
        JSONArray c_id = new JSONArray();
        JSONArray c_stopped = new JSONArray();
        JSONArray c_pos = new JSONArray();
        JSONArray c_vel = new JSONArray();
        JSONArray c_acc = new JSONArray();
        JSONArray road_id = new JSONArray();

        for (Car c : cars) {
            c_id.put(c.id());
            c_stopped.put(c.isStopped() ? 1 : 0);
            c_pos.put(Double.valueOf(String.format(Locale.ENGLISH, format, c.pos())));
            c_vel.put(Double.valueOf(String.format(Locale.ENGLISH, format, c.vel())));
            c_acc.put(Double.valueOf(String.format(Locale.ENGLISH, format, c.acc())));
            road_id.put(c.road().id());
        }
        snapshot.put("id", c_id);
        snapshot.put("p", c_pos);
        snapshot.put("v", c_vel);
        snapshot.put("a", c_acc);
        snapshot.put("s", c_stopped);
        snapshot.put("r", road_id);
        pastSnapshots.add(snapshot);
        if (pastSnapshots.size() > CHUNK_SIZE) {
            generateDynamic(pastSnapshots);
            pastSnapshots = null;
        }
        return pastSnapshots;
    }


    public static void initializeDynamicWriter(String folder) {
        String filePath = DIRECTORY + "/" + folder + "/snapshots.json";
        File myObj = new File(filePath);
        try {
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                SNAPSHOT_WRITER = new FileWriter(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void generateDynamic(List<JSONObject> snapshots) {
        if (snapshots == null || snapshots.size() == 0) {
            throw new RuntimeException("No snapshots to write");
        }
        try {
            JSONArray array = new JSONArray();
            for (JSONObject o : snapshots) {
                array.put(o);
            }
            SNAPSHOT_WRITER.write(array.toString());
            SNAPSHOT_WRITER.flush();
            SNAPSHOT_WRITER.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing snapshots", e);
        }
    }
}
