import java.util.*;
import java.lang.Integer;

//musze aktualizowac moje pozycje na biezaco pionse
public class Meeting implements MeetingInterface {
    private Position meetingPoint;
    private List<PawnPosition>myPositions = new ArrayList<>();
    private Map<Integer, PawnPosition>myMappedPositions = new HashMap<>();
    public PawnPosition newPos = new PawnPosition2D(-1,-1,-1);
    public int movedCount = 1;
    public boolean moved;
    private int moves;
    private int actualMove;
    private Position searchedPos = new PawnPosition2D(-1,-1,-1);

    public Map<Integer, PawnPosition> addPawnsToHashMap(List<PawnPosition> positions){
        Map<Integer, PawnPosition> updatedMap = new HashMap<>();
        for(PawnPosition pos : positions){
            updatedMap.put(pos.pawnId(), pos);
        }
        return new HashMap<>(updatedMap);
    }

    public boolean doesMeetingsPointChanged(Position pos) {
        return true;
    }
    public int dx(PawnPosition pos){
        return Math.abs(meetingPoint.x()-pos.x());
    }

    public int dy(PawnPosition pos){
        return Math.abs(meetingPoint.y()- pos.y());
    }

    @Override
    public void addPawns(List<PawnPosition> positions) {
        myPositions = new ArrayList<>(positions);
    }

    @Override
    public void addMeetingPoint(Position meetingPointPosition) {
        meetingPoint = new Position2D(meetingPointPosition.x(), meetingPointPosition.y());
    }

    public boolean canMove(PawnPosition pos) {
        for(PawnPosition el : myPositions) {
            if((el.x() == pos.x()) && (el.y() == pos.y())){
                return false;
            }
        }
        return true;
    }

    public void moveToX(PawnPosition pos) {
        List<PawnPosition>actualPositions = new ArrayList<>();

        if(meetingPoint.x() > pos.x()) {
            newPos = new PawnPosition2D(pos.pawnId(), pos.x() + 1, pos.y());
        }

        if(meetingPoint.x() < pos.x()) {
            newPos = new PawnPosition2D(pos.pawnId(), pos.x() -1, pos.y());
        }

        for(PawnPosition el: myPositions) {
            if(el.equals(pos) && canMove(newPos)){
                actualPositions.add(newPos);
                movedCount ++;
            } else {
                actualPositions.add(el);
            }
        }
        myPositions = new ArrayList<>(actualPositions);
    }

    public void moveToY(PawnPosition pos){
        List<PawnPosition>actualPositions = new ArrayList<>();

        if(meetingPoint.y() > pos.y()) {
            newPos = new PawnPosition2D(pos.pawnId(), pos.x(), pos.y()+1);
        }

        if(meetingPoint.y() < pos.y()) {
            newPos = new PawnPosition2D(pos.pawnId(), pos.x(), pos.y()-1);
        }

        for(PawnPosition el: myPositions) {
            if(el.equals(pos) && canMove(newPos)){
                actualPositions.add(newPos);
                movedCount++;
            } else {
                actualPositions.add(el);
            }
        }
        myPositions = new ArrayList<>(actualPositions);
    }
    public int oneMove() {
        movedCount = 0;
        for(PawnPosition el : myPositions){
            //ruch:
            int dx = dx(el);
            int dy = dy(el);

            if((dx == 0) && (dy == 0)){

            }else {
                if (dx > dy) {
                    moveToX(el);
                }
                if(dy>=dx){
                    moveToY(el);
                }
            }
        }
        Collections.reverse(myPositions);
        return movedCount;
    }
    @Override
    public void move() {
        moved = true;
        moves = 0;
        actualMove = 0;
        int j = 0;
        while(movedCount !=0){
//            moved = (oneMove() == 0) ? false : true;
//            Collections.reverse(myPositions);
            moves++;
            actualMove = oneMove();
//            Collections.reverse(myPositions);
            if(actualMove == 0){
                j++;
            }
            moved = j==3 ? false : true;
        }
    }

    @Override
    public Set<PawnPosition> getAllPawns() {
        return new HashSet<>(myPositions);
    }

    @Override
    public Set<PawnPosition> getNeighbours(int pawnId) {
//        myMappedPositions = addPawnsToHashMap(myPositions);

//        Position searchedPos = myMappedPositions.get(pawnId)
//
//        for(PawnPosition pos : myPositions) {
//            if(pos.pawnId() == pawnId){
//                searchedPos = new PawnPosition2D(pos.pawnId(), pos.x(), pos.y());
//            }
//        }
//        Set<PawnPosition>neighbours = new HashSet<>();
//
//        for(PawnPosition pos : myPositions){
//            if(Math.abs(pos.x() - searchedPos.x())<=1 && Math.abs(pos.y() - searchedPos.y()) <= 1) {
//                if(pos.pawnId() != pawnId) {
//                    neighbours.add(pos);
//                }
//            }
//        }

//        return new HashSet<>(neighbours);
        //szukam pionka o podanym id
        Set<PawnPosition>neighbours = new HashSet<>();
        for(PawnPosition pos : myPositions){
            if((pos.pawnId() - pawnId) == 0) {
                searchedPos = new PawnPosition2D(pos.pawnId(), pos.x(), pos.y());
            }
        }

        //System.out.println(myPositions);
        for(PawnPosition el : myPositions) {
            int dx = Math.abs(el.x() - searchedPos.x());
            int dy = Math.abs(el.y() - searchedPos.y());

            if (el.pawnId() == pawnId) {

            } else {
                if ((dx <= 1) & (dy <= 1)) {
//                    System.out.println("Element: " + el + " searched pos: " + searchedPos);
//                    System.out.println("Dx: " + dx + "Dy: " + dy);
                    neighbours.add(el);
                }
            }
        }
        return new HashSet<>(neighbours);
    }
}