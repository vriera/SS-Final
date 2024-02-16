package ar.edu.itba.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Node{
    private int id;
    private double x;
    private double y;
    private List<Road> outboundRoads;
    private List<Road> inboundRoads;

    private List<Boolean> inboundRoadsStops = new ArrayList<>();
    public List<Road> getOutboundRoads() {
        return outboundRoads;
    }
    public List<Road> getInboundRoads() {
        return inboundRoads;
    }


    public Node(int id,double x, double y){
        this.id = id;
        if (id < 0) {
            throw new IllegalArgumentException("id must be non-negative");
        }
        this.x = x;
        this.y = y;
        this.outboundRoads = new ArrayList<>();
        this.inboundRoads = new ArrayList<>();
        this.inboundRoadsStops = new ArrayList<>();
    }
    public int id(){
        return id;
    }
    public double x(){
        return x;
    }
    public double y(){
        return y;
    }

    @Override
    public String toString(){
        return "Node[id=" +id + ",x=" + x + ",y=" + y + "]";
    }
    @Override
    public boolean equals(Object o ){
        return o instanceof Node && this.id == ((Node) o).id;
    }
    @Override
    public int hashCode(){
        return id;
    }

    public void addRoad(Road r){
        if(r.start().id == this.id){
            outboundRoads.add(r);
        }else{
            inboundRoads.add(r);
            inboundRoadsStops.add(false);
        }
    }

    public void initIntersectionPriority(){
        this.inboundRoads.sort((Road a, Road b) -> {
            int mod = a.direction().ordinal() - b.direction().ordinal() % 4;
            return (mod < 0) ? mod + 4 : mod;
        });
    }

    public void update() {
        // Hay autos en la red zone?
//        for (int i = 0; i < inboundRoads.size(); i++) {
//            Car car1 = inboundRoads.get(i).peekHeadCar();
//            if (car1 != null) {
//                // If it is inside the red zone and is not stopped
//                if (car1.pos() > car1.road().length() - car1.road().redZoneLength() && !this.inboundRoadsStops.get(i)) {
//                    // Set all the other stops to true
//                    for (int j = 0; j < inboundRoads.size(); j++) {
//                        if (j != i) {
//                            inboundRoadsStops.set(j, true);
//                        }else{
//                            //por las dudas el otro lo pongo en false
//                            inboundRoadsStops.set(j, false);
//                        }
//                    }
//                    return;
//                }
//            }
//        }

        // Hay autos en las yellow zone de indbounds?
        int inbounds = 0;
        for (int i = 0; i < inboundRoads.size(); i++) {
            Car car1 = inboundRoads.get(i).peekHeadCar();
            if (car1 != null) {
                // If it is inside the ywllow zone
                double distToEnd = car1.road().length() - car1.pos();
                if (distToEnd < car1.road().yellowZoneLength()) {
                    inbounds++;
                }
            }
        }

        if (inbounds == 0) {
            // Poner todos los stops en false
            for (int i = 0; i < inboundRoads.size(); i++) {
                inboundRoadsStops.set(i, false);
            }
            return;
        }

        if (inbounds == 1) {
            // Si hay un solo auto en la yellow zone, poner todos los stops en true menos el que tiene el auto
            for (int i = 0; i < inboundRoads.size(); i++) {
                if (inboundRoads.get(i).peekHeadCar() != null) {
                    inboundRoadsStops.set(i, false);
                } else {
                    inboundRoadsStops.set(i, true);
                }
            }
            return;
        }

        // Aca estoy seguro de que siempre van a ser 2
        Car c1 = inboundRoads.get(0).peekHeadCar();
        Car c2 = inboundRoads.get(1).peekHeadCar();

        if (!c1.wantsToTurn() || (c1.wantsToTurn() && c2.wantsToTurn())) {
//            System.out.println("Prioridad para quien viene de la derecha");
            inboundRoadsStops.set(0, false);
            inboundRoadsStops.set(1, true);
        }

        if (!c2.wantsToTurn()) {
//            System.out.println("Prioridad para quien viene de la izquierda");
            inboundRoadsStops.set(0, true);
            inboundRoadsStops.set(1, false);
        }
    }

    public boolean isStopped(Road r) {
        if (!inboundRoads.contains(r)) {
            throw new IllegalArgumentException("Road must be inbound");
        }
        return inboundRoadsStops.get(inboundRoads.indexOf(r));
    }
}