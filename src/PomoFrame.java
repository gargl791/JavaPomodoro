import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class PomoFrame {

    public PomoFrame() {
        PomoDesign();
    }

    public void PomoDesign(){
        JFrame frame = new JFrame();
        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(251, 246, 239));
        frame.setContentPane(contentPane);
        PomoPanel pomoPanel = new PomoPanel();
        PomoMenu pomoMenu = new PomoMenu();
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(220, 190));
        frame.setTitle("Pomodoro Timer");
        frame.getContentPane().add(pomoPanel.getPomoPanel());
        frame.setJMenuBar(pomoMenu.getPomoMenu());
        frame.pack();
        frame.setVisible(true);
    }
    
}