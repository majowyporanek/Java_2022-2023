import javax.sound.midi.SysexMessage;
import java.util.*;

public class Meeting222 implements MeetingInterface {
    private Position meeting; // pozycja do ktorej bedą dązyc pionki
    private List<PawnPosition> myPositions;
    private Map<Integer, PawnPosition>myMappedPositions;
    public int changes = 1;


    private int dx(Position position){
        return Math.abs(meeting.x() - position.x());
    }

    private int dy(Position position){
        return  Math.abs(meeting.y() - position.y());
    }
    @Override
    public void addPawns(List<PawnPosition> positions) {
        myPositions = new ArrayList<>();
        myPositions.addAll(positions);
    }

    public Map<Integer, PawnPosition> addPawnsToHashMap(List<PawnPosition> positions){
        Map<Integer, PawnPosition> updatedMap = new HashMap<>();
        for(PawnPosition pos : positions){
            updatedMap.put(pos.pawnId(), pos);
        }
        return new HashMap<>(updatedMap);
    }

    @Override
    public void addMeetingPoint(Position meetingPointPosition) {
        meeting = meetingPointPosition;
    }


    @Override
    public void move() {
        int moveNum = 0;
        while (changes!=0) {
            List<PawnPosition>newArray = moveTurn();
            myPositions = new ArrayList<>(newArray);
//            moveNum++;
            Collections.reverse(myPositions);
        }

//        if(moveNum%2 ==0){
//            Collections.reverse(myPositions);
//        }
    }

    private PawnPosition2D moveToX(PawnPosition position){
     if(meeting.x() > position.x()){
         return new PawnPosition2D(position.pawnId(), position.x() + 1, position.y());
     }
     if(meeting.x()<position.x()){
         return  new PawnPosition2D(position.pawnId(), position.x() - 1, position.y());
     }
     return null;
    }

    private PawnPosition2D moveToY(PawnPosition position){
        if(meeting.y() > position.y()){
            return new PawnPosition2D(position.pawnId(), position.x(), position.y()+1);
        }

        if(meeting.y()<position.y()){
            return  new PawnPosition2D(position.pawnId(), position.x(), position.y()-1 );
        }
        return null;
    }

    private boolean canIMove(PawnPosition pos, Set<PawnPosition>neighbours){
//        Set<PawnPosition> neighbours = getNeighbours(pos.pawnId());
        for (PawnPosition nPos : neighbours){
            if(nPos.x() == pos.x() & nPos.y() == nPos.y()){
                return false;
            }
        }
        return true;
    }


    public List<PawnPosition> moveTurn(){
        Set<PawnPosition> setOfN = new HashSet<>();
        List<PawnPosition> myUpdatedPositions = new ArrayList<>();
        PawnPosition newPosition = new PawnPosition2D(-1,-1,-1); //temporary position
        changes = 0;
        for(PawnPosition pos : myPositions){
            System.out.println("Current meeting point: " + meeting + " and current position: " + pos);
            if(dx(pos) == 0 && dy(pos)==0){
                if(canIMove(pos, getNeighbours(pos.pawnId()))) {
                    myUpdatedPositions.add(pos);
                    System.out.println("Dodalem " + pos);
                    for (PawnPosition pos2 : myPositions){
                        if(pos2.pawnId() != pos.pawnId()){
                            myUpdatedPositions.add(pos2);
                        }
                    }
                }

            }else{
                if(dx(pos)>dy(pos)){
                    //ruch w strone x
                    setOfN = getNeighbours(pos.pawnId());
                    newPosition = moveToX(pos);

                    if(canIMove(newPosition,setOfN)){
                        myUpdatedPositions.add(newPosition);
                        System.out.println("Dodalem " + pos);
                        changes++;
                    }else {
                        myUpdatedPositions.add(pos);
                    }
                }
                if(dy(pos)>=dx(pos)){
                    //ruch w stronę y
                    //ruch w strone x
                    setOfN = getNeighbours(pos.pawnId());
                    newPosition = moveToY(pos);

                    if(canIMove(newPosition,setOfN)){
                        myUpdatedPositions.add(newPosition);
                        System.out.println("Dodalem " + pos);
                        changes++;
                    }else {
                        myUpdatedPositions.add(pos);
                        System.out.println("Dodalem " + pos);
                    }
                }
            }
        }
        System.out.println("Before update ListArray: " + myPositions);
        System.out.println("Updated ListArray: " + myUpdatedPositions);
        return new ArrayList<>(myUpdatedPositions);
    }
    @Override
    public Set<PawnPosition> getAllPawns() {
        // TODO Auto-generated method stub
        return new HashSet<>(myPositions);

    }


    @Override
    public Set<PawnPosition> getNeighbours(int pawnId) {
        // TODO Auto-generated method stub
        //
        myMappedPositions = addPawnsToHashMap(myPositions);
        //System.out.println(myMappedPositions);
       Position searchedPos = myMappedPositions.get(pawnId);
       //System.out.println("Searched pos: " + searchedPos);
        Set<PawnPosition>neighbours = new HashSet<>();
        //System.out.println("Pozycja do porownan " + searchedPos);
        for(PawnPosition pos : myPositions){
            if(Math.abs(pos.x() - searchedPos.x())<=1 & Math.abs(pos.y() - searchedPos.y()) <= 1) {
                if(pos.pawnId() != pawnId) {
                    neighbours.add(pos);
                }
                //System.out.println("Dodaje " + pos);
            }
        }
        return new HashSet<>(neighbours);
    }
}
