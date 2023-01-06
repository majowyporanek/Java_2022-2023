import java.util.List;

public class ThreadsHelper {
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
    public static void doNothing(long msec) {
        msec += System.currentTimeMillis();
        do {
        } while (msec > System.currentTimeMillis());
    }

    /**
     * Metoda wykonuje siÄ przez podany okres czasu.
     *
     * @param msec liczba milisekund
     */
    public static void sleep(long msec) {
        msec += System.currentTimeMillis();
        do {
        } while (msec > System.currentTimeMillis());
    }

    /**
     * Metoda zwraca nazwÄ wÄtku, ktĂłry jÄ wykonaĹ
     *
     * @return nazwa wÄtku
     */
    public static String getThreadName() {
        return Thread.currentThread().getName();
    }

}