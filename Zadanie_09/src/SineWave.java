//import javax.swing.*;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//import java.awt.*;
//
//public class SineWave extends JFrame {
//    static final int fps_min = 1;
//    static final int fps_max = 25;
//    public SineWave() {
//        setTitle("Sine Wave");
//        setSize(800, 800);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setVisible(true);
//    }
//
//    public void nSlider(ChangeEvent e){
//
//    }
//
//    public void paint(Graphics g) {
//        super.paint(g);
//        int width = getWidth();
//        int height = getHeight();
//        System.out.println("Height: " + height);
//        System.out.println("Width: " + width);
//        JSlider slideN = new JSlider(JSlider.HORIZONTAL, fps_min, fps_max, fps_min);
//        slideN.setPreferredSize(new Dimension(width/5,  height-15));
//        slideN.setLocation(width/7,height/7);
//        System.out.println(slideN.getLocation());
//        slideN.addChangeListener(new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                int N = slideN.getValue();
//                System.out.println(N);
//            }
//        });
//
//        this.add(slideN, BorderLayout.SOUTH);
//        for (int x = 0; x < width; x++) {
//            double angle = (double) x / width * 4 * Math.PI - 2 * Math.PI; // range from -2PI to 2PI
//            double sum = 0;
//            double licznik = 0;
//            double sinx;
//
//            for (int n = 1; n <= 10; n++) {
//                licznik = (-2)*Math.pow(-1, n);
//                sinx = Math.sin(1*angle);
//                sum += licznik/n * sinx;
//            }
//            int y = (int) (height / 2 + height / 6 * sum);
//            y = height - y;
//            g.setColor(Color.magenta);
//            g.fillOval(x, y, 2, 2);
//        }
//    }
//
//    public static double drawSinus(double x) {
//        double sum = 0;
//        for (int i = 1 ; i < 2;i++){
//            sum += (-2)* Math.pow(-1, i)/i * Math.sin(i*x);
//        }
//        return sum;
//    }
//
//    public static void main(String[] args) {
////        for(int x = 0 ; x < 10; x++){
////            double xx = Math.PI * x;
////            System.out.println(drawSinus(xx));
////
////        }
//        SineWave sine = new SineWave();
//
//    }
//}

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SineWave extends JFrame {
    private JSlider slider;
    private JLabel label;
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
            double angle = (double) x / width * 2 * Math.PI - 1 * Math.PI; // range from -2PI to 2PI
            double sum = 0;
            double licznik = 0;
            double sinx;

            for (int n = 1; n <= 1; n++) {
                licznik = (-2)*Math.pow(-1, n);
                sinx = Math.sin(n*angle);
                sum += licznik/n * sinx;
            }
            int y = (int) (height / 2 + height / 6 * sum);
            y = height - y;
            g.setColor(Color.magenta);
            g.fillOval(x, y, 2, 2);
        }
    }

//    public class Slider extends JSlider implements ChangeListener

    public static void main(String[] args) {
        SineWave sine = new SineWave();
    }
}
