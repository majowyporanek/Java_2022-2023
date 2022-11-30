import java.util.*;
import java.lang.Integer;

//musze aktualizowac moje pozycje na biezaco pionse
public class Meeting implements MeetingInterface {
    private Position meetingPoint;
    private List<PawnPosition>myPositions;
    private Map<Integer, PawnPosition>myMappedPositions = new HashMap<>();
    private int moveCounter;
    public int changes;

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

    public void actualizeMyPos(List<PawnPosition> positions){
        myPositions = new ArrayList<>(positions);
    }

    @Override
    public void addPawns(List<PawnPosition> positions) {
        myPositions = new ArrayList<>(positions);
    }

    @Override
    public void addMeetingPoint(Position meetingPointPosition) {
        meetingPoint = new Position2D(meetingPointPosition.x(), meetingPointPosition.y());
    }

    public PawnPosition onePawnMove(PawnPosition pos){
        int dx, dy;
        PawnPosition movedPawn = new PawnPosition2D(pos.pawnId(), pos.x(), pos.y()); // na poczatku nie wiemy czy bedziemy ruszac
        dx = dx(pos);
        dy = dy(pos);
        if((dx == 0) && (dy == 0)){
           return movedPawn;
        }

        if(dx > dy) {
           return (meetingPoint.x() > pos.x()) ? new PawnPosition2D(pos.pawnId(), pos.x() + 1, pos.y()) : new PawnPosition2D(pos.pawnId(), pos.x()-1, pos.y() );
        }else {
            return (meetingPoint.y()> pos.y()) ? new PawnPosition2D(pos.pawnId(), pos.x(), pos.y()+1) : new PawnPosition2D(pos.pawnId(), pos.x(), pos.y()-1);
        }
    }

    public List<PawnPosition>actualMove(PawnPosition pos){
        List<PawnPosition> actualizedList = new ArrayList<>();
        Set<PawnPosition>currentN = this.getNeighbours(pos.pawnId());
        for(PawnPosition current : myPositions){
            if(current.equals(pos)){
               if(canPawnMove(currentN, onePawnMove(pos))) {
                   this.changes++;
                   actualizedList.add(onePawnMove(pos));
               }else {
                   actualizedList.add(current);
               }
            }
        }
        return new ArrayList<>(actualizedList);
    }

    public boolean canPawnMove(Set<PawnPosition>neighbours, PawnPosition pos){
        for(PawnPosition n : neighbours) {
            int xN, yN, posX, posY;
            xN = n.x();
            yN = n.y();
            posX = pos.x();
            posY = pos.y();

            if((xN == posX) && yN == posY){
                return false;
            }
        }
        return true;
    }

    private boolean canIMove(PawnPosition pos){
        Set<PawnPosition>actualSet = new HashSet<>(this.getAllPawns());
        actualSet.add(pos);
        return this.getAllPawns().size() < actualSet.size();
    }

    @Override
    public void move() {
        while(this.changes != 0) {
            this.changes = 0;
            for (PawnPosition position : myPositions) {
                myPositions = new ArrayList<>(actualMove(position));
            }
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
