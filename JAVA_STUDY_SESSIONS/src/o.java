//import java.util.concurrent.atomic.AtomicBoolean;
//
//class Latch {
//    private final AtomicBoolean locked = new AtomicBoolean( false );
//
//    public static String threadName() {
//        return Thread.currentThread().getName();
//    }
//
//    public static void sleep( long time ) {
//        try {
//            Thread.sleep( time );
//        } catch ( InterruptedException e ) {}
//    }
//
//    private boolean lockSuccessful() {
//        System.out.println( "PrĂłba zaĹoĹźenia blokady na rzecz wÄtku " + threadName() );
//        sleep( 1500 );
//        return locked.compareAndSet( false, true );
//    }
//
//    public void lock() {
//        while( ! lockSuccessful() ) {
//            synchronized( locked ) {
//                try {
//                    System.out.println( "PrĂłba zaĹoĹźenia blokady na rzecz wÄtku " + threadName() + " nieudana. Trzeba czekaÄ" );
//                    locked.wait();
//                    System.out.println( "WÄtek "  + threadName() + " zostaĹ obudzony" );
//                } catch ( InterruptedException e ) {}
//            }
//        }
//        System.out.println ( "PrĂłba zaĹoĹźenia blokady na rzecz wÄtku " + threadName() + " UDANA !!!" ) ;
//    }
//
//    public void unlock() {
//        // uproszczona wersja
//        System.out.println( "ZdjÄcie blokady zlecone przez wÄtek " + threadName() );
//        Latch.sleep( 3000 );
//        locked.set( false );
//        System.out.println( "Blokada zdjÄta przez wÄtek " + threadName() );
//        Latch.sleep( 3000 );
//        System.out.println( "Budzimy wÄtki oczekujÄce na zaĹoĹźenie blokady" );
//        Latch.sleep( 3000 );
//        System.out.println( "#######################################################################" );
//        Latch.sleep( 1000 );
//
//        synchronized( locked ) {
//            locked.notifyAll();
//        }
//        Latch.sleep( 1000 );
//    }
//}
//
//class Worker implements Runnable {
//    private final Latch latch;
//
//    public Worker( Latch latch ) {
//        this.latch = latch;
//    }
//
//    @Override
//    public void run() {
//        String name = Latch.threadName();
//        while ( true ) {
//            System.out.println( " --->> tu " + name + " przed lock()" );
//            latch.lock();
//            System.out.println( " --->> tu " + name + " synchronized( lock ) {  // po lock()" );
//            for ( int i = 0; i < 7; i++ ) {
//                System.out.println( " --->> tu " + name + "                         BLA BLA BLA" );
//                Latch.sleep( 1125 );
//            }
//            System.out.println( " --->> tu " + name + "                      } // przed unlock()" );
//            latch.unlock();
//            System.out.println( " --->> tu " + name + " za unlock()" );
//        }
//    }
//}
//
//class Start {
//
//    public static void main(String[] args) {
//        Latch commonLatch = new Latch();
//        Worker w1 = new Worker(commonLatch);
//        Worker w2 = new Worker(commonLatch);
//        Worker w3 = new Worker(commonLatch);
//
//        Thread th1 = new Thread( w1 );
//        Thread th2 = new Thread( w2 );
//        Thread th3 = new Thread( w3 );
//
//        th1.start();
//        th2.start();
//        th3.start();
//    }
//}