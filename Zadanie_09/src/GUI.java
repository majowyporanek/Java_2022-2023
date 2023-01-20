import javax.swing.*;
import java.awt.*;

public  class GUI extends JFrame {
    int N = 1;
    double K = 1.0;
    public JSlider slider;
    public JLabel labelN;
    JLabel labelK;
    JPanel northPanel;
    JPanel southPanel;
    JTextField textFieldForK;


    GUI(){
        setTitle("Zadanie 09");
        setSize(1000, 1000);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //north:
        northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(3, 1));
        labelN = new JLabel(); // label
        labelN.setText("N: " + N);
        styleLabels(labelN);
        // slider && description:
        slider = new JSlider(1, 25, 1);
        slider.addChangeListener(e -> {
            N = slider.getValue();
            labelN.setText("N: " + N);
            repaint();
        });
        JLabel sliderDecription = new JLabel();
        styleLabels(sliderDecription);
        sliderDecription.setText("Wybierz N z przedziału <1, 25>");
        northPanel.add(sliderDecription);
        northPanel.add(slider);
        northPanel.add(labelN);




        // center:
        graphPanel.setPreferredSize(new Dimension((int)(getWidth()*2.0/3), (int) (getHeight() * 2.0/3)));



        // south:
        southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(3,1));

        JLabel aboutK = new JLabel();
        styleLabels(aboutK);
        aboutK.setText("Wpisz nową wartość K z przedziału <1.0, 10.0>: ");


        textFieldForK = new JTextField();
        labelK = new JLabel();
        labelK.setText("K: " + K);
        styleLabels(labelK);
        textFieldForK.addActionListener(e -> {
            String kval = textFieldForK.getText();
            try {
                K = Double.parseDouble(kval);
            }catch (NumberFormatException exception){
                labelK.setText("K: " + "WPISANO NIEPRAWIDŁOWĄ WARTOŚĆ! PROSZĘ WPISAĆ K <1.0, 10.0>");

            }

            if(K>=1.0 && K<=10.0) {
                labelK.setText("K: " + K);
                graphPanel.repaint();
            }else {
                labelK.setText("K: " + "WPISANO NIEPRAWIDŁOWĄ WARTOŚĆ! PROSZĘ WPISAĆ K <1.0, 10.0>");
            }
        });
        southPanel.add(aboutK);
        southPanel.add(textFieldForK);
        southPanel.add(labelK);



        add(northPanel, BorderLayout.NORTH);
        add(graphPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);

    }

    public void styleLabels(JLabel label){
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.DARK_GRAY);
    }

    public JPanel graphPanel = new JPanel(){
        public void paint(Graphics g) {;
            super.paint(g);
            int width = (int)graphPanel.getWidth();
            int height =(int)graphPanel.getHeight();
            for (double x = 0; x < width; x+= 1.0) {
                double angle = (double) x / width * K*2 * Math.PI - K * Math.PI;
                double sum = 0;
                double licznik;
                double sinx;

                for (int n = 1; n <= N; n++) {
                    licznik = (-2)*Math.pow(-1, n);
                    sinx = Math.sin(n*angle);
                    sum += licznik/n * sinx;
                }
                int y = (int) (height / 2 + height / 6 * sum);
                y = height - y;
                g.setColor(Color.magenta);
                g.fillOval((int)x, y, 3, 3);
            }
        }
    };



    public static void main(String[] args) {
        GUI okienko = new GUI();
    }
}