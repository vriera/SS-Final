package ar.edu.itba;

import ar.edu.itba.output.OutputGenerator;

import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Config config = new Config();
        config.initConfig();
//        Integer[] cars = {3000};
//        for (int i = 0; i < 6; i++) {
//            for (int j = 0; j < cars.length; j++) {
//                double startTime = System.currentTimeMillis();
//                config.cars = cars[j];
                Simulation simulation = new Simulation(config.timeStep, config);
                simulation.runSimulation("VIS_5"); //  speed 5
//                double endTime = System.currentTimeMillis();
//                System.out.println("CARS_" + cars[j] + "_" + i + " done in " + (endTime - startTime) / 1000 + " seconds");
//
//            }
//        }
//        Double[] velocities = {5.55555, 11.111111, 16.666666, 22.222222};
//        for (int i = 0; i < 1; i++) {
//            for (int j = 0; j < 1; j++) {
//                double startTime = System.currentTimeMillis();
//                config.maximumDesiredSpeed = 22.222222;
//                Simulation simulation = new Simulation(config.timeStep, config);
//                simulation.runSimulation("VIS_" + 1);
//                double endTime = System.currentTimeMillis();
//                System.out.println("VIS_" + 1 + " done in " + (endTime - startTime) / 1000 + " seconds");
//            }
//        }

//          Integer[] turnWeights = {0, 25, 50, 75};
//          for (int i = 0; i < 8; i++) {
//              for (int j = 0; j < turnWeights.length; j++) {
//                  double startTime = System.currentTimeMillis();
//                  config.turnWeight = turnWeights[j];
//                  Simulation simulation = new Simulation(config.timeStep, config);
//                  simulation.runSimulation("TURN_" + turnWeights[j] + "_" + i);
//                  double endTime = System.currentTimeMillis();
//                  System.out.println("TURN_" + turnWeights[j] + "_" + i + " done in " + (endTime - startTime) / 1000 + " seconds");
//              }
//          }
//        simulation.runSimulation(null);
    }


}