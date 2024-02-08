import java.util.ArrayList;

public class Simulation {
    private final double METERS_PER_BLOCK = 100;

    private Node[][] nodes;
    private ArrayList<Road> roads;
    private double time = 0;
    private final Config config;

    public Simulation(double timeStep, Config config) {
        this.config = config;
        this.nodes = new Node[config.totalBlocksWidth + 1][config.totalBlocksHeight + 1];
        this.roads = generateGrid();
        for (Road road : roads) {
            System.out.println(road);
        }
    }


    public ArrayList<Road> generateGrid() {
        ArrayList<Road> roads = new ArrayList<>();
        double totalWidth = config.totalBlocksWidth * METERS_PER_BLOCK;
        double totalHeight = config.totalBlocksHeight * METERS_PER_BLOCK;
        int aux = 0;

        for (int i = 0; i <= config.totalBlocksWidth; i++) {
            for (int j = 0; j <= config.totalBlocksHeight; j++) {
                Node n = new Node(aux, i * METERS_PER_BLOCK, j * METERS_PER_BLOCK);
                nodes[i][j] = n;
                aux++;
            }
        }

        for (int i = 0; i <= config.totalBlocksWidth; i++) {
            for (int j = 0; j <= config.totalBlocksHeight; j++) {
                if (i < config.totalBlocksWidth) {
                    if (j % 2 == 0) {
                        roads.add(new Road(nodes[i][j], nodes[i + 1][j]));
                    } else {
                        roads.add(new Road(nodes[i + 1][j], nodes[i][j]));
                    }
                }
                if (j < config.totalBlocksHeight) {
                    if (i % 2 == 0) {
                        roads.add(new Road(nodes[i][j], nodes[i][j + 1]));
                    } else {
                        roads.add(new Road(nodes[i][j + 1], nodes[i][j]));
                    }
                }
            }
        }
        return roads;
    }


}
