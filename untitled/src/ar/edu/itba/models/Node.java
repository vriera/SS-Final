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

    private List<Boolean> inboundRoadsStops;
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

    }
}