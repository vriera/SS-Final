package ar.edu.itba;

import ar.edu.itba.models.Axis;
import ar.edu.itba.output.OutputGenerator;
import org.json.Cookie;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Config config = new Config();
        config.initConfig();
        Simulation simulation = new Simulation(config.timeStep, config);
        long i = 10000000000L;
        while (i>0){
            simulation.runStep();
            i--;
        }
        JSONObject staticData = simulation.serializeStaticData();
        String folder = OutputGenerator.createStaticInfo(null,  staticData);
        OutputGenerator.initializeDynamicWriter(folder);
        List<JSONObject> snapshots = OutputGenerator.saveSnapshot(Collections.emptySet(), null , folder);

        OutputGenerator.generateDynamic(snapshots);
        OutputGenerator.closeDynamicWriter();
    }


}