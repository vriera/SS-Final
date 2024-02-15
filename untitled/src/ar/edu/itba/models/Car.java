package ar.edu.itba.models;

import ar.edu.itba.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Car {
    private double pos;
    private double vel;
    private double acc;
    private double len;
    private List<Road> route;
    private double finalRoadPos;
    private int currentRoadIndex;
    private int id;
    private boolean isStopped = false;

    private double nextAcc = 0;

    private boolean active;

    public Car(List<Road> route, double startRoadPos, double finalRoadPos, double carLength , int id) {
        this.id = id;
        setupCar(route,startRoadPos,finalRoadPos,carLength);
    }
    public Car(int id){ this.id = id;}

    public void setupCar(List<Road> route, double startRoadPos, double finalRoadPos, double carLength){
        if (route == null || route.size() == 0) {
            throw new IllegalArgumentException("route must be non-empty");
        }
        StringBuilder routeString = new StringBuilder();
        for (Road r : route){
            routeString.append(r.toString());
            routeString.append("-->");
        }
        this.route = route;
        this.pos = startRoadPos;
        this.vel = 0;
        this.acc = 0;
        this.currentRoadIndex = 0;
        this.finalRoadPos = finalRoadPos;
        this.len= carLength;
        active = true;
    }

    public void deactivate(){
        active = false;
//        this.currentRoadIndex = -1;
    }

    public boolean isStopped(){
        return isStopped;
    }
    public int id(){
        return this.id;
    }

    public boolean isActive(){
        return active;
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

    public double len() { return len;}

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

    public void calculateValues(double deltaTime, Config config) {
        Road currentRoad = this.road();
        // If I am in the red zone, I must stop
        if (pos >= currentRoad.length() - currentRoad.redZoneLength()) {
            Node endNode = currentRoad.end();
            if (!endNode.isStopped(currentRoad)) {
                isStopped = true;
                this.nextAcc = - config.maximumDeceleration * this.vel / (config.maximumDesiredSpeed);
            }
        } else {
            double velMax = config.maximumDesiredSpeed;
            if (pos >= currentRoad.length() - currentRoad.yellowZoneLength()) {
//                System.out.println("YELLOW ZONE!");
                velMax *= config.yellowZoneSpeedMul;
            }
            isStopped = false;
            boolean isLeader = currentRoad.peekHeadCar().equals(this);
            double alpha = 0; // Alpha is the interaction acceleration
            Car nextCar = null;
            if (!isLeader) {
                nextCar = currentRoad.getCarAhead(this);
            } else {
                if (currentRoadIndex < route.size() - 1) {
                    nextCar = route.get(currentRoadIndex + 1).peekLastCar();
                }
            }

            // Even if is a leader, the next road could be empty
            if (nextCar != null) {
                double deltaX = nextCar.pos() - this.pos() - nextCar.len();
                double deltaV = this.vel() - nextCar.vel();
                alpha = (config.minimumDesiredDistance + Math.max(0, config.reactionTime * this.vel + deltaV * this.vel / Math.sqrt(2 * config.maximumAcceleration * config.maximumDeceleration))) / deltaX;
            }
            this.nextAcc = config.maximumAcceleration * (1 - Math.pow(this.vel / velMax, config.accelerationExponent)) - Math.pow(alpha, 2);
        }
    }

    public void update(double deltaTime) {
        double auxPos = this.pos + this.vel * deltaTime + this.acc * Math.pow(deltaTime, 2) / 2;
        if (auxPos > this.road().length()) {
            this.pos = (auxPos - this.road().length()) + len;
            this.road().removeCar(this);
            if (this.currentRoadIndex < this.route.size() - 1) {
                this.currentRoadIndex++;
                this.route.get(this.currentRoadIndex).addCar(this);
            }
        } else {
            this.pos = auxPos;
        }
        this.acc = this.nextAcc;
        this.vel = Math.max(0, this.vel + this.acc * deltaTime);
        if(this.currentRoadIndex == this.route.size() - 1 && this.pos >= this.finalRoadPos){
            this.road().removeCar(this);
            deactivate();
            System.out.println("llegue!: " + this);
        }else{
//            StringBuilder b = new StringBuilder();
//            b.append("Car placed at: ");
//            b.append(route.get(this.currentRoadIndex));
//            b.append(" pos: ");
//            b.append(this.pos);
//            b.append(" vel: ");
//            b.append(this.vel);
//            b.append(" acc: ");
//            b.append(this.acc);
//            System.out.println(b);
        }
        this.nextAcc = 0;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id);
    }

    @Override
    public String toString(){
        return "Car: id " + id();
    }
}
