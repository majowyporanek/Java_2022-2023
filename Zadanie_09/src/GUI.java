import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {
    // tworze konstruktor
    public GUI(){
        setSize(300, 200);
        setTitle("Moje pierwsze okienko");
    }
    public static void main(String[] args) {
        //tworze nowe okienko
        GUI okienko = new GUI(); // tworzymy obiekt

        // definiujemy zachowanie okkienka po zamknieciu
        okienko.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okienko.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
