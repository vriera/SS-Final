package ar.edu.itba;

import ar.edu.itba.models.Axis;
import ar.edu.itba.models.Car;
import ar.edu.itba.models.Node;
import ar.edu.itba.models.Road;
import ar.edu.itba.output.OutputGenerator;
import ar.edu.itba.pathfinding.AStar;
import ar.edu.itba.pathfinding.PathFinder;
import ar.edu.itba.pathfinding.heuristics.EuclidianDistance;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Simulation {
    private final double METERS_PER_BLOCK = 100;

    private Node[][] nodes;
    private ArrayList<Road> roads = new ArrayList<>();
    private CarPool carPool;
    private ArrayList<Road> borderRoads = new ArrayList<>();
    private PathFinder pathFinder = new AStar(new EuclidianDistance());
    private double time = 0;
    private final Config config;
    private int placedCars =0;
    public Simulation(double timeStep, Config config) {

        this.config = config;
        this.nodes = new Node[config.totalBlocksWidth + 1][config.totalBlocksHeight + 1];
        this.generateGrid();
        this.initIntersectionPriorities();
        for (Road road : roads) {
            System.out.println(road);
        }
        int i = 0;
        this.carPool = new CarPool(config.cars);
    }

    public void runStep() {
        // First, update the nodes
        Arrays.stream(nodes).parallel().forEach(row -> {
            Arrays.stream(row).parallel().forEach(Node::update);
        });
        // Then, update the cars
        this.carPool.activeCars().stream().parallel().forEach(car -> {
            car.calculateValues(config.timeStep, config);
        });
        this.carPool.activeCars().forEach( car -> car.update(config.timeStep));
        //deactivate cars
        List<Car> carsToDeactivate = this.carPool.activeCars().parallelStream().filter( x -> !x.isActive()).toList();
        carsToDeactivate.forEach(x-> carPool.removeCar(x));
        if(placedCars < config.cars)
             generateCars();

        time += config.timeStep;
    }
    private void generateCars(){
        List<Road> roadList = borderRoads.stream().filter( (road -> road.carCount() == 0)).collect(Collectors.toList());
        if(roadList.isEmpty())
            return;
        placeCar( roadList);
    }
    private Car placeCar( List<Road> roadList){
        Random rand =  new Random();
        //0 -> y border , 1 -> x border;
        Road startingRoad ;
        Road endingRoad;

        int index = rand.nextInt( 0 ,roadList.size());
        //get starting road
        startingRoad = roadList.get(index);
        roadList.remove(index);

        do {
            endingRoad = roads.get(rand.nextInt(roads.size()));
        }
        while (endingRoad.equals(startingRoad));

        List<Road> path = this.pathFinder.generatePath(nodes,startingRoad, endingRoad);

        System.out.println("\n");
        System.out.println("New path from:" + startingRoad.end() + " to: " + endingRoad.end());
        for (Road road : path) {
            System.out.println(road);
        }
        System.out.println("\n");
        Car c = carPool.getFreeCar();
        c.setupCar(path,50,50,config.carLength);
        startingRoad.addCar(c);
        this.placedCars++;
        return c;
    }

    public ArrayList<Road> generateGrid() {
        double totalWidth = config.totalBlocksWidth * METERS_PER_BLOCK;
        double totalHeight = config.totalBlocksHeight * METERS_PER_BLOCK;
        int aux = 0;

        for (int i = 0; i <= config.totalBlocksHeight; i++) {
            for (int j = 0; j <= config.totalBlocksWidth; j++) {
                Node n = new Node(aux, j * METERS_PER_BLOCK, i * METERS_PER_BLOCK);
                nodes[i][j] = n;
                aux++;
            }
        }

        for (int i = 0; i <= config.totalBlocksWidth; i++) {
            for (int j = 0; j <= config.totalBlocksHeight; j++) {
                Road auxRoad;

                //agregar el road en la direccion x
                if (j < config.totalBlocksHeight) {
                    if (i % 2 == 0) {
                        auxRoad = new Road(nodes[i][j], nodes[i][j + 1] , Axis.EAST, config.yellowZoneLength, config.redZoneLength);
                        roads.add(auxRoad);
                    } else {
                        auxRoad = new Road(nodes[i][j + 1], nodes[i][j] , Axis.WEST, config.yellowZoneLength, config.redZoneLength);
                        roads.add(auxRoad);
                    }
                    if(i == 0 || i == config.totalBlocksWidth){
                        borderRoads.add(auxRoad);
                    }
                }

                //agregar el road en la direccion y
                if (i < config.totalBlocksWidth) {
                    if (j % 2 != 0) {
                        auxRoad = new Road(nodes[i][j], nodes[i + 1][j], Axis.SOUTH, config.yellowZoneLength, config.redZoneLength);
                        roads.add(auxRoad);
                    } else {
                        auxRoad = new Road(nodes[i + 1][j], nodes[i][j], Axis.NORTH, config.yellowZoneLength, config.redZoneLength);
                        roads.add(auxRoad);
                    }
                    if (j == 0 || j == config.totalBlocksWidth) {
                        borderRoads.add(auxRoad);
                    }
                }
            }
        }
        return roads;
    }

    public void initIntersectionPriorities() {
        for (int i = 0; i < this.nodes.length; i++) {
            for (int j = 0; j < this.nodes[0].length; j++) {
                    Node n = this.nodes[i][j];
                    n.initIntersectionPriority();
            }
        }
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
        configData.put("simulationTime" , config.simulationTime);
        configData.put("spawnTime" , config.spawnTime);
        configData.put("cars" , config.cars);

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
