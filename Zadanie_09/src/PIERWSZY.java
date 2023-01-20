//import javax.swing.*;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.ComponentAdapter;
//import java.awt.event.ComponentEvent;
//
//public class GUI extends JFrame implements ActionListener {
//
//    private JSlider slider;
//    private JLabel labelN;
//    private JLabel labelK;
//    private JTextField textK;
//    private static int N = 1;
//    private static double K = 1;
//
//    public GUI() {
//        setTitle("Wykres");
//        setSize(800, 800);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//        setLayout(new BorderLayout());
//        JPanel subPanel = new JPanel();
//        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
//        labelN = new JLabel();
//        labelK = new JLabel();
//
//        // dla wpisywania K
//        JLabel aboutK = new JLabel();
//        aboutK.setText("Wpisz nową wartość K: ");
//        textK = new JTextField();
//
//        textK.addActionListener(e -> {
//            String kval = textK.getText();
//            try {
//                K = Double.parseDouble(kval);
//            }catch (NumberFormatException exception){
//
//            }
//
//            if(K>=1.0 && K<=10.0) {
//                labelK.setText("K: " + K);
//                repaint();
//            }else {
//                labelK.setText("K: " + "WPISANO NIEPRAWIDŁOWĄ WARTOŚĆ! PROSZĘ WPISAĆ K [1.0, 10.0]");
//            }
//        });
//
//
//        // slider:
//        slider = new JSlider(1, 25, 1);
//        slider.addChangeListener(e -> {
//            N = slider.getValue();
//            labelN.setText("N: " + N);
//            repaint();
//        });
//        slider.setPreferredSize(new Dimension((int) (getWidth()*0.3), slider.getPreferredSize().height));
//        add(slider, BorderLayout.NORTH);
//
//        // labels:
//        labelN.setText("N: " + slider.getValue());
//        labelK.setText("K: " + K);
//        styleLabels(labelK);
//        styleLabels(labelN);
//
//        subPanel.add(labelN, BorderLayout.SOUTH);
//        subPanel.add(labelK, BorderLayout.NORTH);
////        textK.setBounds(0,0,10,10);
//        subPanel.add(textK, BorderLayout.EAST);
//
//        add(subPanel, BorderLayout.SOUTH);
//        setVisible(true);
//    }
//
//    public void styleLabels(JLabel label){
//        label.setFont(new Font("Arial", Font.BOLD, 16));
//        label.setAlignmentX(0.5f);
//        label.setForeground(Color.darkGray);
//    }
//
//    public void paint(Graphics g) {;
//        super.paint(g);
//        int width = (int)(getWidth());
//        int height =(int)(getHeight()*0.8);
//        for (double x = 0; x < width; x+= 1.0) {
//            double angle = (double) x / width * K*2 * Math.PI - K * Math.PI;
//            double sum = 0;
//            double licznik;
//            double sinx;
//
//            for (int n = 1; n <= N; n++) {
//                licznik = (-2)*Math.pow(-1, n);
//                sinx = Math.sin(n*angle);
//                sum += licznik/n * sinx;
//            }
//            int y = (int) (height / 2 + height / 6 * sum);
//            y = height - y;
//            g.setColor(Color.magenta);
//            g.fillOval((int)x, y, 2, 2);
//        }
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        this.repaint();
//    }
//
//    public static void main(String[] args) {
//        GUI okienko = new GUI();
//    }
//
//}
