import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// co pamietac
// wszelkie zdarzenia - szukamy odpowiedniego narzedzia typu listener
// potrzebujemy dodac sobie actionListenera
// uzylismy innego actionListenera dla off i innego dla on
// jezeli potrzebujemy zasymulowac klawiature numeryczna od 0-9
// moze sie okazac ze prosciej jest miec 1 action listenera ktory za pomoca wyczytania czegos takiego dowie sie ktora liserka zostala aktywoiwana
public class Window extends JFrame {
    private final JButton on = new JButton("ON");
    private final JButton off = new JButton("OFF");
    private  final JLabel txt = new JLabel("----- ----- -----");
    private volatile boolean flaga;

    public static void sleep (long msec){
        try{
            Thread.sleep(msec);
        } catch (InterruptedException e){}
    }
    private class Pracka implements  Runnable {

        @Override
        public void run() {
            String napis;
            on.setEnabled(false);
            flaga = true;
            for(int i = 0; i < 100; i++){
                napis = "i = " + i + "watek " + Thread.currentThread().getName();
                txt.setText(napis);
                sleep(1000);
                if(flaga == false)
                    break;
            }
            on.setEnabled(true);
        }
    }

    public static void main(String[] args) {


        //ladne rozwiazanie -> stosowanie pol finalnych bo
        // jezeli zostanie wpisana do nich jakas wartosc
        // jest ona bezpieczna watkowo
        // stan w momencie wpisania jest widoczny dla wszstkich
        // raczej sie nie bedzie zmieniac wiec ladnym rozwiazaniem jest
        // uzycie pola finalnego
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){        //chcemy zeby sie wyswielilo
                Window okno = new Window();

                okno.setTitle("---- Window ----");
                // albo po prostu parametry wpisac wprost
                okno.setSize(600, 500);


                // bedzie nasluchwiac moment klikniecia
                // moment uruchoomienia pracki.
                okno.on.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(e);

                        new Thread(okno.new Pracka()).start();
                    }
                });

                okno.off.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        okno.flaga = false;
                        System.err.println("Konczymy prace dodadkowego watku!");
                    }
                });

                okno.setLayout(new BorderLayout());

                okno.add(okno.on, BorderLayout.WEST);
                okno.add(okno.txt, BorderLayout.CENTER);
                okno.add(okno.off, BorderLayout.EAST);

                //albo wymaga obiektu Dimension

                okno.setVisible(true); // okno ma sie pojawic
                okno.setDefaultCloseOperation(EXIT_ON_CLOSE);
                System.out.println("Kto mnie wołał, czego chciał? " + Thread.currentThread().getName());

            }
        });



    }
}
