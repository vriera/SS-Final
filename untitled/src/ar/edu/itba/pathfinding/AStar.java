package ar.edu.itba.pathfinding;

import ar.edu.itba.models.Node;
import ar.edu.itba.models.Road;
import ar.edu.itba.pathfinding.heuristics.Heuristic;
import com.sun.tools.jconsole.JConsoleContext;

import java.util.*;

public class AStar implements PathFinder{


    private Heuristic heuristic;
//    private Node[][] nodes;
//    private List<Road> roadList;

    private HashMap<Integer , List<Road> > calculatedPath;

    public AStar(Heuristic heuristic) {
        this.heuristic = heuristic;
        calculatedPath = new HashMap<>();

    }

//    private int getKeyForPath(Node staringNode, Node goalNode){
//        return Objects.hash(staringNode,goalNode);
//    }

//    private void setNewPath(List<Road> roads){
//        int key = getKeyForPath(roads.get(0).end() ,roads.get(roads.size()-1).end());
//        calculatedPath.put(key , roads);
//    }

    private List<Road> getRoadsFromNode(PathfindingNode node){
        List<Road> out = new ArrayList<>();
        do {
            out.add(node.getRoad());
            node = node.getParent();
        }while (node != null);
        Collections.reverse(out);
//        setNewPath(out);
        return out;
    }

    @Override
    public List<Road> generatePath(Node[][] nodes, Road startingRoad, Road targetRoad) {
        Node targetNode = targetRoad.end();
        Node targetRoadStart= targetRoad.start();
//        int key = getKeyForPath(startingRoad.end() , targetNode);
//        if(calculatedPath.containsKey(key))
//                return calculatedPath.get(key);

        PathfindingNode startingNode = new PathfindingNode(startingRoad.end() ,
                startingRoad ,
                null ,
                0 ,
                heuristic.distance(startingRoad.end(),targetNode));

        System.out.println("Path from: "  + startingRoad.toString() + " to: " + targetRoad.toString() );
        Set<Node> visitedNodes = new HashSet<>();
        HashedTreeSet openedOrdered = new HashedTreeSet();


        openedOrdered.add(startingNode);
        PathfindingNode current;
        while(!openedOrdered.isEmpty()) {
            current = openedOrdered.first();
            System.out.println("Looking at node: " + current.getNode().toString());
            System.out.println("Looking with outbound roads: ");
            for(Road r : current.getNode().getOutboundRoads())
                System.out.println(r.toString());

            if(current.getNode() == targetNode)
                return getRoadsFromNode(current);

            openedOrdered.remove(current);
            visitedNodes.add(current.getNode());
            System.out.println("visited nodes: ");
            for( Node n : visitedNodes)
                System.out.println(n.toString());

            for(Road r : current.getNode().getOutboundRoads()){
                System.out.println("looking at road: " + r.toString() );
                Node neighbourNode = r.end();
                if(!visitedNodes.contains(neighbourNode)){
                    PathfindingNode neighbourPath = new PathfindingNode(neighbourNode ,
                            r ,
                            current ,
                            r.length() + current.getgScore(),
                            heuristic.distance(neighbourNode,targetRoadStart));

                    PathfindingNode pastPath = openedOrdered.get(neighbourNode);
                    if(pastPath == null || neighbourPath.compareTo(pastPath) < 0)
                        openedOrdered.update(neighbourPath);
                }else{
                    System.out.println("node has been already visited");
                }

            }
            System.out.println(openedOrdered);

        }
        throw new RuntimeException("Path not found");
    }
}
