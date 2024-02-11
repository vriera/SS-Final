package ar.edu.itba.models;

import java.util.ArrayList;
import java.util.List;

public class Road {
    private Node start;
    private Node end;
    private double length;
    private double yellowZoneLength;
    private double redZoneLength;
    private List<Car> cars = new ArrayList<>();

    private Axis direction;
    public Road(Node start, Node end , Axis direction, double yellowZoneLength, double redZoneLength){
        this.start = start;
        this.end = end;
        this.length = Math.sqrt(Math.pow(start.x() - end.x(), 2) + Math.pow(start.y() - end.y(), 2));
        this.direction = direction;
        this.yellowZoneLength = yellowZoneLength;
        this.redZoneLength = redZoneLength;
        start.addRoad(this);
        end.addRoad(this);
  }

    public Node start() {
        return start;
    }

    public Node end() {
        return end;
    }

    public double length() {
        return length;
    }

    public Axis direction() {
        return direction;
    }

    public double yellowZoneLength() {
        return yellowZoneLength;
    }

    public double redZoneLength() {
        return redZoneLength;
    }

    @Override
    public boolean equals(Object o ){
        if(o instanceof  Road){
            return ((Road) o).start().equals(this.start()) && ((Road) o).end().equals(this.end());
        }
        return false;

    }
    public String toString() {
        return "Road from " + start + " to " + end + " Direction: " + direction.toString();
    }

    public void addCar(Car car){
        cars.add(car);
    }

    public Car peekHeadCar(){
        if(cars.size() == 0){
            return null;
        }
        return cars.get(cars.size()-1);
    }
}
