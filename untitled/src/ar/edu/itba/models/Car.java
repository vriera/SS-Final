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

    public double pos() {
        return pos;
    }

    public double vel() {
        return vel;
    }

    public double acc() {
        return acc;
    }

    public Road road() {
        return route.get(currentRoadIndex);
    }

    public void setPos(double pos) {
        this.pos = pos;
    }

    public void setVel(double vel) {
        this.vel = vel;
    }

    public void setAcc(double acc) {
        this.acc = acc;
    }

    public boolean wantsToTurn() {
        if (currentRoadIndex == route.size() - 1) {
            return false;
        }
        Road currentRoad = route.get(currentRoadIndex);
        Road nextRoad = route.get(currentRoadIndex + 1);
        return !currentRoad.direction().equals(nextRoad.direction());
    }
}
