package ar.edu.itba.models;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private double pos;
    private double vel;
    private double acc;
    private List<Road> route;
    private double finalRoadPos;
    private int currentRoadIndex;
    private boolean isStopped = false;

    public Car(List<Road> route, double startRoadPos, double finalRoadPos) {
        if (route == null || route.size() == 0) {
            throw new IllegalArgumentException("route must be non-empty");
        }
        StringBuilder routeString = new StringBuilder();
        for (Road r : route){
            routeString.append(r.toString());
            routeString.append("-->");
        }
        System.out.println(routeString.toString());
        this.route = route;
        this.pos = 0;
        this.vel = 0;
        this.acc = 0;
        this.currentRoadIndex = 0;
        this.finalRoadPos = finalRoadPos;
    }
}