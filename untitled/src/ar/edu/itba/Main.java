package ar.edu.itba;

import ar.edu.itba.output.OutputGenerator;

import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Config config = new Config();
        config.initConfig();
        Simulation simulation = new Simulation(config.timeStep, config);
        simulation.runSimulation(null);
    }


}