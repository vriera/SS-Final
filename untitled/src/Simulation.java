import java.util.ArrayList;

public class Simulation {
    private ArrayList<Node> nodes;
    private ArrayList<Road> roads;
    private double time = 0;
    private final double timeStep;

    public Simulation(ArrayList<Node> nodes, ArrayList<Road> roads, double timeStep) {
        this.nodes = nodes;
        this.roads = roads;
        this.timeStep = timeStep;
    }

    public Simulation(double timeStep) {
        this.nodes = new ArrayList<>();
        this.roads = new ArrayList<>();
        this.timeStep = timeStep;
    }


}
