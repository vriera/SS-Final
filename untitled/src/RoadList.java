import java.util.ArrayList;

public class RoadList extends ArrayList<Road> {
    private ArrayList<Road> borderRoads = new ArrayList<>();

    @Override
    public boolean add(Road r) {

        return true;
    }
}
