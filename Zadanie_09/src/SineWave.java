import javax.swing.*;
import java.awt.*;

public class SineWave extends JFrame {
    public SineWave() {
        setTitle("Sine Wave");
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        int width = getWidth();
        int height = getHeight();
        for (int x = 0; x < width; x++) {
            double angle = (double) x / width * 4 * Math.PI - 2 * Math.PI; // range from -2PI to 2PI
            double sum = 0;
            double licznik = 0;
            double sinx;

            for (int n = 1; n <= 3; n++) {
                licznik = (-2)*Math.pow(-1, n);
                sinx = Math.sin(1*angle);
                sum += licznik/n * sinx;
            }
            int y = (int) (height / 2 + height / 6 * sum);
            y = height - y;
            g.setColor(Color.magenta);
            g.fillOval(x, y, 2, 2);
        }
    }

    public static double drawSinus(double x) {
        double sum = 0;
        for (int i = 1 ; i < 2;i++){
            sum += (-2)* Math.pow(-1, i)/i * Math.sin(i*x);
        }
        return sum;
    }

    public static void main(String[] args) {
//        for(int x = 0 ; x < 10; x++){
//            double xx = Math.PI * x;
//            System.out.println(drawSinus(xx));
//
//        }
        SineWave sine = new SineWave();

    }
}
