
public class Opakowanie {
    private int poleWspĂłĹdzielone;

    synchronized public int get() {
        return poleWspĂłĹdzielone;
    }

    synchronized public void set( int v ) {
        poleWspĂłĹdzielone = v;
    }

    synchronized public void inc() {
        set( get() + 1 );
    }


    @Override
    public String toString() {
        return "Tu wÄtek: " + ThreadsHelper.getThreadName() + " pole: " + poleWspĂłĹdzielone;
    }
}