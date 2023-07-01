import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;


public class PomoFrame {

    public PomoFrame() {
        PomoDesign();
    }

    public void PomoDesign(){
        JFrame frame = new JFrame();
        PomoPanel pomoPanel = new PomoPanel();
        PomoMenu pomoMenu = new PomoMenu();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.setTitle("Pomodoro Timer");
        frame.getContentPane().add(pomoPanel.getPomoPanel());
        frame.setJMenuBar(pomoMenu.getPomoMenu());
        frame.pack();
        frame.setVisible(true);
    }
    
}