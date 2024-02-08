public class Node{
    private int id;
    private double x;
    private double y;
    @Override
    public String toString(){
        return "Node[id=" +id + ",x=" + x + ",y=" + y + "]";
    }

    public Node(int id,double x, double y){
        this.id = id;
        if (id < 0) {
            throw new IllegalArgumentException("id must be non-negative");
        }
        this.x = x;
        this.y = y;
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
    public boolean equals(Object o ){
        return o instanceof Node && this.id == ((Node) o).id;
    }
    @Override
    public int hashCode(){
        return id;
    }
}