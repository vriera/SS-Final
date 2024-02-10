package ar.edu.itba.pathfinding.heuristics;

import ar.edu.itba.models.Node;
import ar.edu.itba.pathfinding.heuristics.Heuristic;

public class EuclidianDistance implements Heuristic {
    @Override
    public double distance(Node from, Node goal) {
        return  Math.pow(goal.x() - from.x(), 2) + Math.pow(goal.y() - from.y(), 2);
    }
}
