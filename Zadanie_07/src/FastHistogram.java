import java.util.*;

public class FastHistogram implements Histogram{
    private int threadsNum;
    private Vector vector;
    private Map<Integer, Integer>histogram = new HashMap<>();
    public boolean ready;
    List<Map<Integer, Integer>> minimaps = new ArrayList<>();

    @Override
    public void setup(int threads, int bins) {
        this.threadsNum = threads;
        ready = false;
    }

//    void changeValue(int i, Vector currVec, Map<Integer,Integer>partMap) {
//        if(!partMap.containsKey(currVec.getValue(i))) {
//            partMap.put(currVec.getValue(i), 1);
//        }else {
//            partMap.put(currVec.getValue(i), partMap.get(currVec.getValue(i)) + 1);
//        }
//    }
//    void countNumbersInVector(Vector v, int start, int end, Map<Integer, Integer> currentMap){
//        for(int i = start; i < end; i++) {
//            changeValue(i, v, currentMap);
//        }
//        updateHistogramFunc(currentMap);
//    }

    public void StartThreadsAndWait(List<Thread>threads){
        for(int i = 0; i<threadsNum; i++){
            threads.get(i).start();
        }

        for(int i = 0; i<threadsNum; i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

//            try {
//                threads.get(threads.size()-1).join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
    }



    @Override
    public void setVector(Vector vector) {
        this.vector = vector;
        new Thread(new CountAndWork()).start();
    }


    class CountAndWork implements Runnable{

        @Override
        public void run() {
            List<Thread>threads = new ArrayList<>();
            int divider = (vector.getSize()-1)/threadsNum;

            for(int i = 0; i<threadsNum - 1; i++){
                minimaps.add(new HashMap<>());
                threads.add(new Thread(new Counter(i * divider, (i + 1) * divider, minimaps.get(i))));
            }

            minimaps.add(new HashMap<>());
            threads.add(new Thread(new Counter((threadsNum - 1) * divider, vector.getSize() , minimaps.get(threadsNum-1))));


            StartThreadsAndWait(threads);

            for(Map<Integer, Integer> minimap : minimaps){
                for(Integer key : minimap.keySet()){
                    if(!histogram.containsKey(key)){
                        histogram.put(key, minimap.get(key));
                    }else {
                        histogram.put(key, minimap.get(key) + histogram.get(key));
                    }
                }
            }
            ready = true;
        }
    }

//    @Override
//    public void setVector(Vector vector) {
//        List<Thread>threads = new ArrayList<>();
//        int divider = (vector.getSize()-1)/threadsNum;
//        int s = 0;
//        int e = divider;
//        for(int i = 0; i<threadsNum; i++){
//            if(i == (threadsNum-1)){e = vector.getSize();}
//            Thread t = new Thread(new Counter(vector, s, e));
//            s+=divider;
//            e+=divider;
//            threads.add(t);
//        }
//        startCount = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                StartThreads(threads);
//            }
//        });
//        startCount.start();
//        try {
//            startCount.join();
//        } catch (InterruptedException ex) {
//            throw new RuntimeException(ex);
//        }
//    }




    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public Map<Integer, Integer> histogram() {
        return histogram;
    }

    public class Counter implements Runnable{
        int start_index;
        int end_index;
        Map<Integer, Integer> minimap;

        Counter(int s, int e, Map<Integer, Integer> minimap){
            this.start_index = s;
            this.end_index = e;
            this.minimap = minimap;
        }

        @Override
        public void run() {
            for(int i = start_index; i<end_index; i++){
                int val = vector.getValue(i);
                if(!minimap.containsKey(val)){
                    minimap.put(val, 1);
                }else {
                    minimap.put(val, minimap.get(val)+1);
                }
            }
        }
    }





    //    public void updateHistogramFunc(Map<Integer, Integer> part){
//        for(Integer i : part.keySet()){
//            if (!containsKey(i)) {
//                histogram.put(i, part.get(i));
//            } else {
//                histogram.put(i, histogram.get(i) + part.get(i));
//            }
//        }
//    }
}
