import java.util.*;

public class Lines implements LinesInterface {
    private Map<Point, List<Point>> graph = new HashMap<>();


    private class PointClass implements Point{
        String name;
        PointClass(String n){this.name = n;}
        @Override
        public String getName() {
            return this.name;
        }
    }

    private class SegmentClass implements Segment{
        Point start;
        Point end;

        //constructor
        SegmentClass(Point p1, Point p2){
            this.start = p1;
            this.end = p2;
        }
        @Override
        public Point getEndpoint1() {
            return start;
        }

        @Override
        public Point getEndpoint2() {
            return end;
        }
    }
    @Override
    public void addPoints(Set<Point> points) {
        //???
        for(Point p:points) graph.put(new PointClass(p.getName()), null);
    }

    @Override
    public void addSegments(Set<Segment> segments) {

    }

    @Override
    public List<Segment> findConnection(Point start, Point end) {
        return null;
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