//import java.util.*;
//
//public class Main implements MeetingInterface{
//
//    List<PawnPosition> allPawns;
//    List<PawnPosition> movedPawns;
//    Set<Integer> movedPawnsIds;
//    Position meetingPoint;
//    int turnNum = 0;
//    int moveCounter = 0;
//
//    public Meeting() {
//        allPawns = new ArrayList<>();
//        meetingPoint = new Position2D(0, 0);
//    }
//
//    @Override
//    public void addPawns(List<PawnPosition> positions) {
//        allPawns.addAll(positions);
//    }
//
//    @Override
//    public void addMeetingPoint(Position meetingPointPosition) {
//        meetingPoint = meetingPointPosition;
//    }
//
//    public boolean isOneOff(Position A, Position B) {
//        if (A.equals(B))
//            return false;
//        return Math.abs(A.x() - B.x()) <= 1 & Math.abs(A.y() - B.y()) <= 1;
//    }
//
//    @Override
//    public Set<PawnPosition> getNeighbours(int pawnId) {
//        PawnPosition xPawn = new PawnPosition2D(-1, 0, 0);
//        Set<PawnPosition> xsNeighbours = new HashSet<>();
//
//        for (PawnPosition X : allPawns) {
//            if (X.pawnId() == pawnId) {
//                xPawn = X;
//            }
//        }
//
//        for (PawnPosition Y : allPawns) {
//            if (!movedPawnsIds.contains(Y.pawnId()) & isOneOff(xPawn, Y)) {
//                xsNeighbours.add(Y);
//            }
//        }
//
//        for (PawnPosition Y : movedPawns) {
//            if (isOneOff(xPawn, Y)) {
//                xsNeighbours.add(Y);
//            }
//        }
//
//        return xsNeighbours;
//    }
//
//    boolean blocking(PawnPosition P, Set<PawnPosition> N) {
//        for (PawnPosition neighbour : N) {
//            if(P.x() == neighbour.x() & P.y() == neighbour.y())
//                return true;
//        }
//        return false;
//    }
//
//    PawnPosition movePawn(PawnPosition pawnToMove) {
//        int dx = meetingPoint.x() - pawnToMove.x();
//        int dy = meetingPoint.y() - pawnToMove.y();
//
//        if (Math.abs(dx) == 0 & Math.abs(dy) == 0) {
//            return pawnToMove;
//        }
//
//        Set<PawnPosition> theNeighbours = getNeighbours(pawnToMove.pawnId());
//        List<PawnPosition> N = new ArrayList<>(theNeighbours);
//
//        if (Math.abs(dx) > Math.abs(dy)) {
//            PawnPosition pawnToMoveNewPosition = pawnToMove;
//
//            if (dx < 0)
//                pawnToMoveNewPosition = new PawnPosition2D(pawnToMove.pawnId(), pawnToMove.x() - 1, pawnToMove.y());
//
//            if (dx > 0)
//                pawnToMoveNewPosition = new PawnPosition2D(pawnToMove.pawnId(), pawnToMove.x() + 1, pawnToMove.y());
//
//            if (!blocking(pawnToMoveNewPosition, theNeighbours)) {
//                moveCounter++;
//                return pawnToMoveNewPosition;
//            }
//        }
//
//        if (Math.abs(dx) <= Math.abs(dy)) {
//            PawnPosition pawnToMoveNewPosition = pawnToMove;
//
//            if (dy < 0)
//                pawnToMoveNewPosition = new PawnPosition2D(pawnToMove.pawnId(), pawnToMove.x(), pawnToMove.y() - 1);
//
//            if (dy > 0)
//                pawnToMoveNewPosition = new PawnPosition2D(pawnToMove.pawnId(), pawnToMove.x(), pawnToMove.y() + 1);
//
//            if (!blocking(pawnToMoveNewPosition, theNeighbours)) {
//                moveCounter++;
//                return pawnToMoveNewPosition;
//            }
//        }
//        return pawnToMove;
//    }
//
//    @Override
//    public void move() {
//        while (true) {
//            oneMove();
//
//            if(moveCounter == 0)
//                return;
//        }
//    }
//
//    void oneMove() {
//        moveCounter = 0;
//        movedPawns = new ArrayList<>();
//        movedPawnsIds = new HashSet<>();
//
//        if(turnNum % 2 == 0) {
//            for (PawnPosition pawnToMove : allPawns) {
//                movedPawns.add(movePawn(pawnToMove));
//                movedPawnsIds.add(pawnToMove.pawnId());
//            }
//        }
//        else {
//            for (int i = (allPawns.size() - 1); i >= 0; i--) {
//                PawnPosition pawnToMove = allPawns.get(i);
//                movedPawns.add(movePawn(pawnToMove));
//                movedPawnsIds.add(pawnToMove.pawnId());
//            }
//        }
//        turnNum++;
//        allPawns = new ArrayList<>(movedPawns);
//    }
//
//    @Override
//    public Set<PawnPosition> getAllPawns() {
//        return new HashSet<>(allPawns);
//    }
//}