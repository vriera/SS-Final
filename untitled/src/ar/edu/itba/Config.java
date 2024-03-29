package ar.edu.itba;

import org.json.JSONObject;

import java.io.*;

public class Config {
    private static final String configPath = "untitled/resources/config.json";

    public double carLength = 0;
    public double minimumDesiredDistance = 0;
    public double maximumDesiredSpeed = 0;
    public double accelerationExponent = 0;
    public double reactionTime = 0;
    public double maximumAcceleration = 0;
    public double maximumDeceleration = 0;
    public double timeStep = 0;
    public int totalBlocksWidth = 0;
    public int totalBlocksHeight = 0;
    public double yellowZoneLength = 0;
    public double yellowZoneSpeedMul = 0;
    public double redZoneLength = 0;

    public double turnWeight = 0;
    public long simulationTime = 0;
    public long spawnTime = 0;
    public int cars = 0;
    // Singleton
    public Config() {
    }

    public void initConfig() {
        // Print PWD
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        // Read the configPath file
        File file = new File(configPath);
        StringBuilder lines = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                lines.append(line);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JSONObject config = new JSONObject(lines.toString());
        carLength = config.getDouble("carLength");
        cars = config.getInt("cars");
        minimumDesiredDistance = config.getDouble("minimumDesiredDistance");
        maximumDesiredSpeed = config.getDouble("maximumDesiredSpeed");
        accelerationExponent = config.getDouble("accelerationExponent");
        reactionTime = config.getDouble("reactionTime");
        maximumAcceleration = config.getDouble("maximumAcceleration");
        maximumDeceleration = config.getDouble("maximumDeceleration");
        timeStep = config.getDouble("timeStep");
        totalBlocksHeight = config.getInt("totalBlocksHeight");
        totalBlocksWidth = config.getInt("totalBlocksWidth");
        yellowZoneLength = config.getDouble("yellowZoneLength");
        redZoneLength = config.getDouble("redZoneLength");
        yellowZoneSpeedMul = config.getDouble("yellowZoneSpeedMul");
        simulationTime = config.getLong("simulationTime");
        spawnTime = config.getLong("spawnTime");
        turnWeight = config.getDouble("turnWeight");
    }

    @Override
    public String toString() {
        return "ar.edu.itba.Config{" +
                "carLength=" + carLength +
                ", minimumDesiredDistance=" + minimumDesiredDistance +
                ", maximumDesiredSpeed=" + maximumDesiredSpeed +
                ", accelerationExponent=" + accelerationExponent +
                ", reactionTime=" + reactionTime +
                ", maximumAcceleration=" + maximumAcceleration +
                ", maximumDeceleration=" + maximumDeceleration +
                ", timeStep=" + timeStep +
                '}';
    }
}
