package ar.edu.itba.pathfinding.heuristics;

import ar.edu.itba.models.Node;

@FunctionalInterface
public interface Heuristic {
    double distance(Node from, Node goal);
}
