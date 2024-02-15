package ar.edu.itba.pathfinding;

import ar.edu.itba.models.Node;

import java.util.*;

public class HashedTreeSet {
    private HashMap<Node,PathfindingNode> hashMap;
    private TreeSet<PathfindingNode> treeSet;

    public HashedTreeSet() {
        this.hashMap = new HashMap<>();
        this.treeSet = new TreeSet<>();
    }

    public void add(PathfindingNode element) {
        if (hashMap.containsKey(element.getNode())) {
            treeSet.remove(hashMap.get(element.getNode()));
        }
        hashMap.put(element.getNode(),element);
        treeSet.add(element);
    }

    public void remove(PathfindingNode element) {
        if (hashMap.containsKey(element.getNode())) {
            hashMap.remove(element.getNode());
        }
        treeSet.remove(element);
    }

    public void update(PathfindingNode element) {
        PathfindingNode node = hashMap.put(element.getNode() , element);
        if (node != null) {
            treeSet.remove(node);  // Remove node with old F score
        }
        treeSet.add(element);

    }

    public boolean contains(PathfindingNode element) {
        return hashMap.containsKey(element.getNode());
    }
    public boolean contains(Node node) {
        return hashMap.containsKey(node);
    }
    public PathfindingNode get(Node node) {
        return hashMap.get(node);
    }

    public Iterator<PathfindingNode> iterator() {
        return treeSet.iterator();
    }

    public int size() {
        return treeSet.size();
    }

    public boolean isEmpty() {
        return treeSet.isEmpty();
    }

    public void clear() {
        hashMap.clear();
        treeSet.clear();
    }

    public PathfindingNode first() {
        return treeSet.first();
    }

    public PathfindingNode last() {
        return treeSet.last();
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        int i = 0;
        s.append("TreeSet:\n");
        for (PathfindingNode n: treeSet) {
            s.append(i);
            s.append(" - ");
            s.append(n.getfScore());
            s.append(" : ");
            s.append(n.getNode());
            s.append("\n");
        }
        s.append("HasMap:\n");
        for(Node n : hashMap.keySet()){
            s.append(n);
            s.append("\n");
        }

        return s.toString();

    }
    // Add other methods as needed
}