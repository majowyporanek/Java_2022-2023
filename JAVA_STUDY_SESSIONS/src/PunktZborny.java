import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PunktZborny {
    private static class Point implements Runnable {
        private int position;
        private final int meetingPoint;
        private final Vector commonVector;
        private static int id;
        private final char name;

        public Point(int position, int meetingPoint, PunktZborny.Vector commonVector) {
            this.position = position;
            this.meetingPoint = meetingPoint;
            this.commonVector = commonVector;
            name = (char)( id++ + 65 );
            commonVector.move(this, position);
        }

        public int getPosition() {
            return position;
        }

        public void startThread() {
            Thread th = new Thread( this );
            th.start();
        }

        public char getName() {
            return name;
        }

        @Override
        public void run() {
            while( true ) {
                int nextPosition = nextPosition();
                /*
                 * Czekamy na zwolnienie nastÄpnej pozycji
                 */

                synchronized( commonVector ) {
                    while( ! commonVector.isEmpty( nextPosition ) ) {
                        try {
                            System.out.println( "Przed wait() " + name);
                            commonVector.wait();
                            System.out.println( "Po wait() " + name);
                        } catch ( InterruptedException e ) {}
                    }
                    doNotChangeOrRemoveThisLine();
                    /*
                     * I siÄ na nastÄpnÄ pozycjÄ przenosimy
                     */
                    commonVector.move(this, nextPosition);

                    commonVector.notifyAll();
                }

                position = nextPosition;
            }
        }

        /**
         * Tu wyliczamy nastÄpnÄ pozycjÄ. JeĹli nie dotarliĹmy
         * do punktu zbornego, to siÄ do niego staramy przysunÄÄ.
         * @return kolejna pozycja na drodze do punktu zbornego
         */
        private int nextPosition() {
            if ( position > meetingPoint ) {
                return position-1;
            }
            if ( position < meetingPoint ) {
                return position+1;
            }
            return position;
        }
    }

    private static final int SIZE = 80;
    private static final char EMPTY = '.';
    private static final long SLEEP = 20;

    private static class Vector {
        private final Point[] tbl = new Point[SIZE];
        private final Lock blokada =
                new ReentrantLock();

        public boolean isEmpty( int idx ) {
            blokada.lock();
            try {
                return tbl[ idx ] == null;
            } finally {
                blokada.unlock();
            }
        }

        public void move( Point point, int newPosition ) {
            blokada.lock();
            tbl[ point.getPosition() ] = null;
            doNotChangeOrRemoveThisLine();
            tbl[ newPosition ] = point;
            doNotChangeOrRemoveThisLine();
            blokada.unlock();
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            blokada.lock();
            for ( int i = 0; i < SIZE; i++ ) {
                if ( ! isEmpty( i ) ) {
                    sb.append(tbl[i].getName());
                } else {
                    sb.append( EMPTY );
                }
            }
            blokada.unlock();

            return sb.toString();
        }
    }

    public static void main(String[] args) {

        Map<Integer,Integer> histogram;

        histogram = Collections.synchronizedMap( new HashMap<>() );

        int key = 123;

        if ( ! histogram.containsKey( key )) {
            histogram.put(key, 1);
        }

        List<Integer> lista = new ArrayList<>( List.of(1,2,3,34,4,56));

        for ( Integer value : lista ) {
            if ( value % 2 == 0 )
                lista.remove( value );
        }

        Vector v = new Vector();
        List<Point> pointsList = createPointsInGroups(v);
        startThreads(pointsList);
        showPointsPositions(v);
    }




    //////////////// tej czÄĹci kodu nie zmieniamy!!!

    private static void showPointsPositions(Vector v) {
        while ( true ) {
            System.out.println( v );
            TimeHelper.sleep(50);
        }
    }

    private static void startThreads(List<Point> pointsList) {
        for ( Point point : pointsList ) {
            point.startThread();
        }
    }

    private static List<Point> createPointsInGroups(Vector v) {
        int positionOfMeetingPoint = SIZE - 1;
        List<Point> pointsList = new ArrayList<>();

        for ( int i = 0; i < 15; i++ ) {
            pointsList.add( new Point( i, positionOfMeetingPoint,v));
        }

        // int pointsInGroup = 4;
        // int groups = 5;
        // int step = ( SIZE - pointsInGroup* groups )/ groups + 1;
        // int pos = 0;

        // for ( int group = 0; group < groups; group++ ) {
        // 	for ( int pointInGroup = 0; pointInGroup < pointsInGroup; pointInGroup++ ) {
        // 		pos ++;
        // 		if ( pos != positionOfMeetingPoint ) {
        // 			pointsList.add( new Point(pos, positionOfMeetingPoint, v));
        // 		}
        // 	}
        // 	pos += step;
        // }

        return pointsList;
    }

    /**
     * Tej metody nie wolno zmieniÄ!
     */
    private static void doNotChangeOrRemoveThisLine() {
        ThreadsHelper.sleep(SLEEP);         // tak ma byÄ, nie wolno usuwaÄ
    }
}