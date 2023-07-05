import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class testBox extends JFrame {
    
    public testBox() {
        setTitle("BoxLayout Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        JPanel panel1 = createPanel("Panel 1");
        JPanel panel2 = createPanel("Panel 2");
        JPanel panel3 = createPanel("Panel 3");

        panel1.setLayout(new FlowLayout());
        panel2.setLayout(new FlowLayout());
        panel3.setLayout(new FlowLayout());
        
        mainPanel.add(panel1);
        mainPanel.add(panel2);
        mainPanel.add(panel3);
        
        add(mainPanel);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private JPanel createPanel(String text) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(text));
        return panel;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new testBox().setVisible(true);
        });
        int min = 60 * 1000 * 60;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedTime = df.format((new Date(min)));
        System.out.println(formattedTime);

    }
}