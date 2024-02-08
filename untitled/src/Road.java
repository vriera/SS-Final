public class Road {
    Node start;
    Node end;
    double length;


    public Road(Node start, Node end ) {
        this.start = start;
        this.end = end;
        this.length = Math.sqrt(Math.pow(start.x() - end.x(), 2) + Math.pow(start.y() - end.y(), 2));
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

    public String toString() {
        return "Road from " + start + " to " + end;
    }

}
