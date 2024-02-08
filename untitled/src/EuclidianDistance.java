public class EuclidianDistance implements Heuristic{
    @Override
    public double distance(Node from, Node goal) {
        return  Math.pow(goal.x() - from.x(), 2) + Math.pow(goal.y() - from.y(), 2);
    }
}
