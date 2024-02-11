package ar.edu.itba.pathfinding;

import ar.edu.itba.models.Node;
import ar.edu.itba.models.Road;

import java.util.Comparator;
import java.util.Objects;

public class PathfindingNode implements Comparable<PathfindingNode>{
    private Node node;

    private Road road;


    private PathfindingNode parent;

    private double gScore;
    private double hScore;


    public PathfindingNode(Node node, Road road, PathfindingNode parent, double gScore, double hScore) {
        this.node = node;
        this.road = road;
        this.parent = parent;
        this.gScore = gScore;
        this.hScore = hScore;
    }

    public double getfScore(){
        return gScore + hScore;
    }


    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Road getRoad() {
        return road;
    }

    public void setRoad(Road road) {
        this.road = road;
    }

    public PathfindingNode getParent() {
        return parent;
    }

    public void setParent(PathfindingNode parent) {
        this.parent = parent;
    }

    public double getgScore() {
        return gScore;
    }

    public void setgScore(double gScore) {
        this.gScore = gScore;
    }

    public double gethScore() {
        return hScore;
    }

    public void sethScore(double hScore) {
        this.hScore = hScore;
    }

    @Override
    public int compareTo(PathfindingNode o) {
        int a = Double.compare(this.getfScore() , o.getfScore());
        if(a == 0 )
            a = Double.compare(this.hScore , o.hScore);
        if(a ==0 )
            a = Integer.compare(this.node.hashCode() , o.getNode().hashCode());
        return a;
    }

    @Override
    public int hashCode(){
        return this.node.hashCode();
    }

    @Override
    public String toString(){
        return node.toString() + " == " + road.toString();
    }
}
