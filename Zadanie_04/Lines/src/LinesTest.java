import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class LinesTest {
    private Lines lines;

    private record TestPoint(String name) implements LinesInterface.Point {
        @Override
        public String getName() {
            return this.name;
        }
    }

    private record TestSegment(LinesInterface.Point p1, LinesInterface.Point p2) implements LinesInterface.Segment {
        @Override
        public LinesInterface.Point getEndpoint1() {
            return this.p1;
        }

        @Override
        public LinesInterface.Point getEndpoint2() {
            return this.p2;
        }
    }

    @Before
    public void setUp() {
        this.lines = new Lines();
    }

    @Test
    public void doesNotFindConnectionWhenNoLines() {
        var A = new TestPoint("A");
        var B = new TestPoint("B");

        assertEquals(0, this.lines.findConnection(A, B).size());
    }

    @Test
    public void findsSimpleConnection() {
        var A = new TestPoint("A");
        var B = new TestPoint("B");
        var C = new TestPoint("C");
        var D = new TestPoint("D");
        var E = new TestPoint("E");

        var AB = new TestSegment(A, B);
        var AC = new TestSegment(A, C);
        var BC = new TestSegment(B, C);
        var BD = new TestSegment(B, D);
        var CE = new TestSegment(C, E);

        Set<LinesInterface.Point> points = new HashSet<>(List.of(A, B, C, D, E));

        // A -> B -> D
        // A -> B -> C -> E
        // A -> C -> E
        Set<LinesInterface.Segment> segments = new HashSet<>(List.of(AB, AC, BC, BD, CE));

        lines.addPoints(points);
        lines.addSegments(segments);

        var actual = this.lines.findConnection(A, D);
        var expected = new ArrayList<>(List.of(AB, BD));

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void findsShortConnectionAndAvoidsCycles() {
        var A = new TestPoint("A");
        var B = new TestPoint("B");
        var C = new TestPoint("C");
        var D = new TestPoint("D");
        var E = new TestPoint("D");

        var AB = new TestSegment(A, B);
        var AC = new TestSegment(A, C);
        var BC = new TestSegment(B, C);
        var CB = new TestSegment(C, B);
        var CD = new TestSegment(C, D);

        Set<LinesInterface.Point> points = new HashSet<>(List.of(A, B, C, D, E));

        Set<LinesInterface.Segment> segments = new HashSet<>(List.of(AB, AC, BC, CB, CD));

        lines.addPoints(points);
        lines.addSegments(segments);

        var actual = this.lines.findConnection(A, D);
        var expected1 = new ArrayList<>(List.of(AC, CD));
        var expected2 = new ArrayList<>(List.of(AB, BC, CD));

        assertArrayEquals(expected1.toArray(), actual.toArray());
    }

    @Test
    public void findsFarConnectionAndAvoidsCycles() {
        var A = new TestPoint("A");
        var B = new TestPoint("B");
        var C = new TestPoint("C");
        var D = new TestPoint("D");
        var E = new TestPoint("E");
        var F = new TestPoint("F");

        var AB = new TestSegment(A, B);
        var AC = new TestSegment(A, C);
        var BC = new TestSegment(B, C);
        var CB = new TestSegment(C, B);
        var CD = new TestSegment(C, D);
        var DE = new TestSegment(D, E);
        var EF = new TestSegment(E, F);
        var CA = new TestSegment(C, A);

        Set<LinesInterface.Point> points = new HashSet<>(List.of(A, B, C, D, E, F));

        Set<LinesInterface.Segment> segments = new HashSet<>(List.of(AB, AC, BC, CA, CB, CD, DE, EF));

        lines.addPoints(points);
        lines.addSegments(segments);

        var actual = this.lines.findConnection(A, F);
//        var expected = new ArrayList<>(List.of(AC, CD, DE, EF));
        // second solution
         var expected = new ArrayList<>(List.of(AB, BC, CD, DE, EF));

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void getsMapEndpointToSegments() {
        var A = new TestPoint("A");
        var B = new TestPoint("B");
        var C = new TestPoint("C");
        var D = new TestPoint("D");

        var AB = new TestSegment(A, B);
        var AC = new TestSegment(A, C);
        var BC = new TestSegment(B, C);
        var CB = new TestSegment(C, B);
        var CD = new TestSegment(C, D);

        Set<LinesInterface.Point> points = new HashSet<>(List.of(A, B, C, D));

        Set<LinesInterface.Segment> segments = new HashSet<>(List.of(AB, AC, BC, CB, CD));

        lines.addPoints(points);
        lines.addSegments(segments);

        var actual = this.lines.getMapEndpointToSegments();
        var expected = new HashMap<LinesInterface.Point, Set<LinesInterface.Segment>>();
        expected.put(A, new HashSet<>(List.of(AB, AC)));
        expected.put(B, new HashSet<>(List.of(AB, BC, CB)));
        expected.put(C, new HashSet<>(List.of(AC, BC, CB, CD)));
        expected.put(D, new HashSet<>(List.of(CD)));

        assertEquals(expected, actual);
    }

    @Test
    public void getsReachableEndpointsEasy() {
        var A = new TestPoint("A");
        var B = new TestPoint("B");
        var C = new TestPoint("C");
        var D = new TestPoint("D");

        var AB = new TestSegment(A, B);
        var AC = new TestSegment(A, C);
        var BC = new TestSegment(B, C);

        Set<LinesInterface.Point> points = new HashSet<>(List.of(A, B, C, D));

        Set<LinesInterface.Segment> segments = new HashSet<>(List.of(AB, AC, BC));

        lines.addPoints(points);
        lines.addSegments(segments);

        var actual = this.lines.getReachableEndpoints();
        Map<LinesInterface.Point, Map<Integer, Set<LinesInterface.Point>>> expected = new HashMap<>();
        expected.put(A, new HashMap<>(Map.of(
                1, new HashSet<>(List.of(B, C)),
                2, new HashSet<>(List.of(B, C)),
                3, new HashSet<>(),
                4, new HashSet<>()
        )));
        expected.put(B, new HashMap<>(Map.of(
                1, new HashSet<>(List.of(A, C)),
                2, new HashSet<>(List.of(A, C)),
                3, new HashSet<>(),
                4, new HashSet<>()
        )));
        expected.put(C, new HashMap<>(Map.of(
                1, new HashSet<>(List.of(B, A)),
                2, new HashSet<>(List.of(B, A)),
                3, new HashSet<>(),
                4, new HashSet<>()
        )));
        expected.put(D, new HashMap<>(Map.of(
                1, new HashSet<>(),
                2, new HashSet<>(),
                3, new HashSet<>(),
                4, new HashSet<>()
        )));

        assertEquals(expected, actual);
    }

    @Test
    public void getsReachableEndpointsHard() {
        var A = new TestPoint("A");
        var B = new TestPoint("B");
        var C = new TestPoint("C");
        var D = new TestPoint("D");
        var E = new TestPoint("E");
        var F = new TestPoint("F");
        var X = new TestPoint("X");
        var Y = new TestPoint("Y");
        var Z = new TestPoint("Z");

        var AB = new TestSegment(A, B);
        var AC = new TestSegment(A, C);
        var BC = new TestSegment(B, C);
        var CB = new TestSegment(C, B);
        var CD = new TestSegment(C, D);
        var DE = new TestSegment(D, E);
        var EF = new TestSegment(E, F);
        var CA = new TestSegment(C, A);
        var XY = new TestSegment(X, Y);

        Set<LinesInterface.Point> points = new HashSet<>(List.of(A, B, C, D, E, F, X, Y, Z));

        Set<LinesInterface.Segment> segments = new HashSet<>(List.of(AB, AC, BC, CA, CB, CD, DE, EF, XY));

        lines.addPoints(points);
        lines.addSegments(segments);

        var actual = this.lines.getReachableEndpoints();
        Map<LinesInterface.Point, Map<Integer, Set<LinesInterface.Point>>> expected = new HashMap<>();
        expected.put(A, new HashMap<>(Map.of(
                1, new HashSet<>(List.of(B, C)),
                2, new HashSet<>(List.of(B, C, D)),
                3, new HashSet<>(List.of(D, E)),
                4, new HashSet<>(List.of(E, F))
        )));
        expected.put(B, new HashMap<>(Map.of(
                1, new HashSet<>(List.of(A, C)),
                2, new HashSet<>(List.of(A, C, D)),
                3, new HashSet<>(List.of(D, E)),
                4, new HashSet<>(List.of(E, F))
        )));
        expected.put(C, new HashMap<>(Map.of(
                1, new HashSet<>(List.of(A, B, D)),
                2, new HashSet<>(List.of(A, B, E)),
                3, new HashSet<>(List.of(F)),
                4, new HashSet<>()
        )));
        expected.put(D, new HashMap<>(Map.of(
                1, new HashSet<>(List.of(C, E)),
                2, new HashSet<>(List.of(A, B, F)),
                3, new HashSet<>(List.of(A, B)),
                4, new HashSet<>()
        )));
        expected.put(E, new HashMap<>(Map.of(
                1, new HashSet<>(List.of(D, F)),
                2, new HashSet<>(List.of(C)),
                3, new HashSet<>(List.of(A, B)),
                4, new HashSet<>(List.of(A, B))
        )));
        expected.put(F, new HashMap<>(Map.of(
                1, new HashSet<>(List.of(E)),
                2, new HashSet<>(List.of(D)),
                3, new HashSet<>(List.of(C)),
                4, new HashSet<>(List.of(A, B))
        )));
        expected.put(X, new HashMap<>(Map.of(
                1, new HashSet<>(List.of(Y)),
                2, new HashSet<>(),
                3, new HashSet<>(),
                4, new HashSet<>()
        )));
        expected.put(Y, new HashMap<>(Map.of(
                1, new HashSet<>(List.of(X)),
                2, new HashSet<>(),
                3, new HashSet<>(),
                4, new HashSet<>()
        )));
        expected.put(Z, new HashMap<>(Map.of(
                1, new HashSet<>(),
                2, new HashSet<>(),
                3, new HashSet<>(),
                4, new HashSet<>()
        )));

        assertEquals(expected, actual);
    }
}
