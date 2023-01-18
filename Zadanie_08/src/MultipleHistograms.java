import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MultipleHistograms implements Histogram{
    private static Consumer<HistogramResult>histogramConsumer;
    @Override
    public void setup(int bins, Consumer<HistogramResult> histogramConsumer) {
        this.histogramConsumer = histogramConsumer;
    }

    @Override
    public void addVector(int vectorID, Vector vector) {
        new Thread(new CountAndWork(vector, vectorID)).start();
    }

    // thread do liczenia powtorzen w histogramie
    class CountAndWork implements Runnable{
        int vectorID;
        Vector vector;
        Map<Integer, Integer> histogram;
        CountAndWork(Vector vector, int vectorID){
            this.vector = vector;
            this.vectorID = vectorID;
            histogram = new HashMap<>();
        }
        @Override
        public void run() {
            for(int i = 0; i<vector.getSize(); i++){
                int val = vector.getValue(i);
                if(!histogram.containsKey(val)){
                    histogram.put(val, 1);
                }
                else {
                    histogram.put(val, histogram.get(val)+1);
                }
            }
            // or new HashMap<>....
            HistogramResult doneHistogram = new HistogramResult(vectorID, histogram);
            new Thread(new notifyHistogramConsumer(doneHistogram)).start();
        }

    }


    // klasa do powiadamiania ze histogram został policzony
    class notifyHistogramConsumer implements Runnable{
        HistogramResult histogramResult;
        notifyHistogramConsumer(HistogramResult histogramResult){
            this.histogramResult = histogramResult;
        }
        @Override
        public void run() {
            histogramConsumer.accept(histogramResult);
        }
    }
}
