import java.util.*;

public class Lines implements LinesInterface{

    private Map<Point, List<Point>> graph = new HashMap<>();
    private Set<Point>vertexes = new HashSet<>();
    private Map<Point, Boolean>visited = new HashMap<>();
    private List<List<Point>> paths; // zapamietywanie sciezek
    private List<Point>currentPaths;
    private Map<Point, Map<Integer, Set<Point>>>reachableEndPoints;
    private Map<Point, Set<Segment>>endpointsToSegments;

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

    public void initializeEndpointsToSegments() {
        endpointsToSegments = new HashMap<>();
        for(Point p : graph.keySet()){
            endpointsToSegments.put(p, new HashSet<>());
        }
    }

    @Override
    public Map<Point, Set<Segment>> getMapEndpointToSegments() {
        Set<Segment>segments = new HashSet<>();
        initializeEndpointsToSegments();

        for(Point p1 : graph.keySet()){
            for(Point adj : graph.get(p1)){
//                segments.add(new SegmentRecord(p1, adj));
//                segments.add(new SegmentRecord(adj, p1));
                endpointsToSegments.get(p1).add(new SegmentRecord(p1, adj));
            }
        }

        return endpointsToSegments;
    }

    @Override
    public Map<Point, Map<Integer, Set<Point>>> getReachableEndpoints() {
        initializeReachableEndPointsMap();

        for(Point start : graph.keySet()){
            for(Point end: graph.keySet()){
                if(start.equals(end) == false){
                    initializeVisited();
                    List<Point>pathsList = new ArrayList<>();
                    pathsList.add(start);
                    allSimplePaths(start, end, pathsList);
                }
            }
        }

        return reachableEndPoints;
    }


    public boolean checkConnection(Point a, Point b){
        return graph.get(a).contains(b);
    }

    public void allSimplePaths(Point p, Point d, List<Point>currentPoints){
        if(p.equals(d)){
            addPathToMap(currentPoints);
            return;
        }

        visited.replace(p, true);

        for(Point point : graph.get(p)){
            if(!visited.get(point)){
                currentPoints.add(point);
                allSimplePaths(point, d, currentPoints);

                currentPoints.remove(point);
            }
        }
        visited.replace(p, false);
    }

    public void addPathToMap(List<Point>path){
        int sizeOfPath = path.size() - 1;
        if(sizeOfPath >=1 && sizeOfPath <= 4){
            reachableEndPoints.get(path.get(0)).get(sizeOfPath).add(path.get(path.size()-1));
        }
    }


    void initializeReachableEndPointsMap(){
        reachableEndPoints = new HashMap<>();
        Map<Integer, Set<Point>>innerMap = new HashMap<>();
        for(Point p: graph.keySet()) {
            reachableEndPoints.put(p, new HashMap<>());
            for (int i = 1; i < 5; i++) {
                Set<Point> innerSet = new HashSet<>();
                reachableEndPoints.get(p).put(i,innerSet);
            }
        }

    }


}