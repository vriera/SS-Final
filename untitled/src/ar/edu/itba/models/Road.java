package ar.edu.itba.models;

public class Road {
    private Node start;
    private Node end;
    private double length;

    private Axis direction;
    public Road(Node start, Node end , Axis direction){
        this.start = start;
        this.end = end;
        this.length = Math.sqrt(Math.pow(start.x() - end.x(), 2) + Math.pow(start.y() - end.y(), 2));
        this.direction = direction;
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

}
