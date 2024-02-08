public record Node (int id, double x, double y) {
    public Node {
        if (id < 0) {
            throw new IllegalArgumentException("id must be non-negative");
        }
    }
}
