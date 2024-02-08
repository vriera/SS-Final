import org.json.JSONArray;
import org.json.JSONObject;

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

    public JSONObject serializeStaticData() {
        JSONObject data = new JSONObject();
        JSONObject configData = new JSONObject();
        data.put("config", configData);
        configData.put("totalBlocksWidth", config.totalBlocksWidth);
        configData.put("totalBlocksHeight", config.totalBlocksHeight);
        configData.put("carLength", config.carLength);
        configData.put("minimumDesiredDistance", config.minimumDesiredDistance);
        configData.put("maximumDesiredSpeed", config.maximumDesiredSpeed);
        configData.put("accelerationExponent", config.accelerationExponent);
        configData.put("reactionTime", config.reactionTime);
        configData.put("maximumAcceleration", config.maximumAcceleration);
        configData.put("maximumDeceleration", config.maximumDeceleration);
        configData.put("timeStep", config.timeStep);

        JSONArray nodesData = new JSONArray();
        data.put("nodes", nodesData);
        for (int i = 0; i <= config.totalBlocksWidth; i++) {
            for (int j = 0; j <= config.totalBlocksHeight; j++) {
                Node node = nodes[i][j];
                JSONObject nodeData = new JSONObject();
                nodesData.put(node.id(), nodeData);
                nodeData.put("x", node.x());
                nodeData.put("y", node.y());
            }
        }

        JSONArray roadsData = new JSONArray();
        data.put("roads", roadsData);
        for (Road road : roads) {
            JSONObject roadData = new JSONObject();
            roadsData.put(roadData);
            roadData.put("start", road.start().id());
            roadData.put("end", road.end().id());
        }
        return data;
    }
}
