import java.util.*;

public class Lines implements LinesInterface{

    private Map<Point, List<Point>> graph = new HashMap<>();
    private Set<Point>vertexes = new HashSet<>();
    private Map<Point, Boolean>visited = new HashMap<>();
    private List<List<Point>> paths; // zapamietywanie sciezek
    private List<Point>currentPaths;

    private record PointRecord(String name) implements Point{
        @Override
        public String getName() {
            return this.name;
        }
    }

    private record SegmentRecord(Point p1, Point p2) implements Segment{

        @Override
        public Point getEndpoint1() {
            return this.p1;
        }

        @Override
        public Point getEndpoint2() {
            return this.p2;
        }
    }
    @Override
    public void addPoints(Set<Point> points) {
        for(Point p: points){
            Point currentPoint = new PointRecord(p.getName());
            graph.put(currentPoint, new LinkedList<>());
            vertexes.add(currentPoint);
        }
    }

    @Override
    public void addSegments(Set<Segment> segments) {
        for(Segment s:segments){
            Segment currentSegment = new SegmentRecord(s.getEndpoint1(), s.getEndpoint2());
            Point key1 = new PointRecord(s.getEndpoint1().getName());
            Point key2 = new PointRecord(s.getEndpoint2().getName());


            if(!graph.containsKey(key1)){
                System.out.println("Doesnt contain such a key");
            }

            if(!graph.containsKey(key2)){
                System.out.println("Doesnt contain such a key");
            }

            graph.get(key1).add(key2);
            graph.get(key2).add(key1);
        }
//        System.out.println(graph.size());
    }

    void initializeVisited(){
        Map <Point, Boolean>updateVisited = new HashMap<>();
        for (Point p : vertexes){
            updateVisited.put(p, false);
        }
        visited = new HashMap<>(updateVisited);
    }

    void findPath(Point current, Point target){
        if(!graph.containsKey(current)|| !graph.containsKey(target)){
            return;
        }
        if(current.equals(target)){
            currentPaths.add(current);
            paths.add(new LinkedList<>(currentPaths));
        }else {
            visited.replace(current, true);
            currentPaths.add(current);
            //all elements in 'adjacentList' of currentNode
            for(Point p : graph.get(current)) {
                if(visited.get(p) == false){
                    Point newCurrent = new PointRecord(p.getName());
                    findPath(newCurrent, target);
                }
            }
            visited.replace(current, false);
            currentPaths.remove(currentPaths.size() - 1);
        }
    }


    @Override
    public List<Segment> findConnection(Point start, Point end) {
        List<Segment>simpleConnection = new LinkedList<>();
        initializeVisited();
        Point destination = new PointRecord(end.getName());
        Point startPoint = new PointRecord(start.getName());
        paths = new LinkedList<>();
        currentPaths = new LinkedList<>();

        findPath(startPoint, destination);
        if(paths.size() != 0) {
            int n = paths.get(0).size();
            for (int i = 0; i < (n - 1); i++) {
                Segment newSegment = new SegmentRecord(paths.get(0).get(i), paths.get(0).get(i + 1));
                simpleConnection.add(newSegment);
            }
        }
        return simpleConnection;
    }

    @Override
    public Map<Point, Set<Segment>> getMapEndpointToSegments() {
        return null;
    }

    @Override
    public Map<Point, Map<Integer, Set<Point>>> getReachableEndpoints() {
        return null;
    }
}