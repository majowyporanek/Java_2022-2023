import java.util.*;
import java.lang.Integer;

//musze aktualizowac moje pozycje na biezaco pionse
public class Meeting implements MeetingInterface {
    private Position meetingPoint;
    private List<PawnPosition>myPositions;
    private Map<Integer, PawnPosition>myMappedPositions = new HashMap<>();
    public PawnPosition newPos = new PawnPosition2D(-1,-1,-1);
    public int movedCount;
    public boolean moved;

    public Map<Integer, PawnPosition> addPawnsToHashMap(List<PawnPosition> positions){
        Map<Integer, PawnPosition> updatedMap = new HashMap<>();
        for(PawnPosition pos : positions){
            updatedMap.put(pos.pawnId(), pos);
        }
        return new HashMap<>(updatedMap);
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
            if(el.x() == pos.x() && el.y() == pos.y()){
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
            newPos = new PawnPosition2D(pos.pawnId(), pos.x(), pos.y() + 1);
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
        for(PawnPosition el : myPositions){
            //ruch:
            movedCount = 0;
            int dx = dx(el);
            int dy = dy(el);

            if(dx == 0 && dy == 0){

            }else {
                if (dx > dy) {
                    moveToX(el);
                }
                if (dy >= dx) {
                    moveToY(el);
                }
            }
        }

        return movedCount;
    }
    @Override
    public void move() {
        moved = true;

        while(moved){
            moved = (oneMove() == 0) ? false : true;
            Collections.reverse(myPositions);
        }
    }

    @Override
    public Set<PawnPosition> getAllPawns() {
        return new HashSet<>(myPositions);
    }

    @Override
    public Set<PawnPosition> getNeighbours(int pawnId) {
        myMappedPositions = addPawnsToHashMap(myPositions);
        Position searchedPos = myMappedPositions.get(pawnId);
        Set<PawnPosition>neighbours = new HashSet<>();

        for(PawnPosition pos : myPositions){
            if(Math.abs(pos.x() - searchedPos.x())<=1 && Math.abs(pos.y() - searchedPos.y()) <= 1) {
                if(pos.pawnId() != pawnId) {
                    neighbours.add(pos);
                }
            }
        }
        return new HashSet<>(neighbours);
    }
}
