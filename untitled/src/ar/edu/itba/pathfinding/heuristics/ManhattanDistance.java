package ar.edu.itba.pathfinding.heuristics;

import ar.edu.itba.models.Node;
import ar.edu.itba.pathfinding.heuristics.Heuristic;

public class ManhattanDistance implements Heuristic {
    @Override
    public double distance(Node from, Node goal) {
        return Math.abs(goal.x() - from.x()) + Math.abs(from.y() - from.y());
    }
}
