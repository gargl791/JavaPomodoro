import java.awt.FlowLayout;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

        System.out.println((int)'a');
        System.out.println((int)'b');
        System.out.println((int)'c');
        System.out.println((int)'d');
        System.out.println((int)'e');
        System.out.println((int)'f');
        System.out.println((int)'g');
        System.out.println((int)'h' + "\n\n");
        Random r = new Random();
        for(int i = 0; i < 10; i++) {
            System.out.println(Character.toChars(r.nextInt(104 - 97 + 1) + 97));
        }
        
    }
}