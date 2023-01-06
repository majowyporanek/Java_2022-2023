
public class TimeHelper {
    /**
     * Metoda zwraca czas w sekundach liczonych od 1.1.1970
     *
     * @return liczba sekund
     */
    public static double getTime() {
        return System.currentTimeMillis() / 1000.0;
    }

    /**
     * Metoda usypia program na podanych okres czasu.
     *
     * @param msec liczba milisekund snu
     */
    public static void sleep(long msec) {
        try {
            Thread.sleep(msec);
        } catch (InterruptedException e) {
        }
    }
}