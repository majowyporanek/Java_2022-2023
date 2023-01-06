public class Pracownik implements Runnable {

    private final Opakowanie opakowanie;

    public Pracownik(Opakowanie opakowanie) {
        this.opakowanie = opakowanie;
    }

    @Override
    public void run() {
        System.out.println( opakowanie );

        for ( int i = 0; i < 1000000; i++ ) {
            synchronized (opakowanie) {
                opakowanie.set( opakowanie.get() + 1 );
                System.out.println( opakowanie );
                ThreadsHelper.sleep(1000);
            } // synch

            ThreadsHelper.sleep(1);

        } // for


        System.out.println( opakowanie );
        System.out.println( "Pracownik idzie do domu");
    }

}
