
public class Kierownik implements Runnable {

    private final Opakowanie opakowanie;

    public Kierownik(Opakowanie opakowanie) {
        this.opakowanie = opakowanie;
    }

    @Override
    public void run() {
        System.out.println( opakowanie );
        ThreadsHelper.sleep(5000);
        System.out.println( opakowanie );
    }

}