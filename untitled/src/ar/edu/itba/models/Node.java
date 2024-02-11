package ar.edu.itba.models;

import java.util.ArrayList;
import java.util.List;

public class Node{
    private int id;
    private double x;
    private double y;
    private List<Road> outboundRoads;
    private List<Road> inboundRoads;


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
        }
    }
}