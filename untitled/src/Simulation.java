import java.util.ArrayList;

public class Simulation {
    private ArrayList<Node> nodes;
    private ArrayList<Road> roads;
    private double time = 0;
    private final Config config;

    public Simulation(Config config, ArrayList<Node> nodes, ArrayList<Road> roads, double timeStep) {
        this.nodes = nodes;
        this.roads = roads;
        this.config = config;
    }

    public Simulation(double timeStep, Config config) {
        this.config = config;
        this.nodes = new ArrayList<>();
        this.roads = new ArrayList<>();
    }


}
