package ar.edu.itba.pathfinding;

import ar.edu.itba.models.Node;
import ar.edu.itba.models.Road;

import java.util.List;

public interface PathFinder {
    List<Road> generatePath(Node[][] nodes , Road startingRoad , Road targetRoad);
}
