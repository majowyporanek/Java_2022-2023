import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public  class GUI extends JFrame {
    int N = 1;
    double K = 1.0;
    public JSlider slider;
    public JLabel labelN;
    JLabel labelK;
    JLabel errorLabel;
    JPanel northPanel;
    JPanel southPanel;
    JTextField textFieldForK;



    GUI(){
        setTitle("Zadanie 09");
        setSize(1000, 800);
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
        southPanel.setLayout(new GridLayout(2,1));

        JLabel aboutK = new JLabel();
        styleLabels(aboutK);
        aboutK.setText("Wprowadź K należące do <1.0, 10.0>: ");


        textFieldForK = new JTextField();
        labelK = new JLabel();
        labelK.setText("K: " + K);
        errorLabel  = new JLabel();
        styleLabels(errorLabel);
        errorLabel.setForeground(Color.RED);
        styleLabels(labelK);
        textFieldForK.addActionListener(e -> {
            errorLabel.setText("");
            String kval = textFieldForK.getText();
            try {
                K = Double.parseDouble(kval);
            }catch (NumberFormatException exception){
                errorLabel.setText("Niepoprawny format!");
            }

            if(K>=1.0 && K<=10.0) {
                labelK.setText("K: " + K);
                graphPanel.repaint();
            }else {
                errorLabel.setText("Poza akceptowalnym przedziałem!");
            }
        });

        southPanel.add(labelK);
        southPanel.add(aboutK);
        southPanel.add(errorLabel);
        southPanel.add(textFieldForK);


        // adding panels
        add(northPanel, BorderLayout.NORTH);
        add(graphPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);

    }

    public void styleLabels(JLabel label){
        label.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int windowWidth = getWidth();
                int fontSize;
                if(windowWidth > 600) {
                    fontSize = (int) (windowWidth * 0.02);
                }else if(windowWidth <= 600 && windowWidth > 300) {
                    fontSize = (int) (windowWidth * 0.025);
                }else {
                    fontSize = (int)(windowWidth * 0.03);
                }
                label.setFont(new Font("Arial", Font.ITALIC, fontSize));
            }
        });
        label.setForeground(Color.DARK_GRAY);
    }

    public JPanel graphPanel = new JPanel(){
        public void paint(Graphics g) {;
            super.paint(g);
            int width = graphPanel.getWidth();
            int height = graphPanel.getHeight();
            double step = 0.5;
            if(Math.max(width, height) > 800 || K >= 4.0){
                step = 0.1;
            }

            for (double x = 0; x < width; x+= step) {
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
                int weight = 3;
                if(weight <300 &&  height<300){
                    weight = 2;
                }
                g.fillOval((int)x, y, weight, weight);
            }
        }
    };

    public static void main(String[] args) {
        GUI wykres = new GUI();
    }
}