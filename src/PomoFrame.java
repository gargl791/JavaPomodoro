import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuListener;


public class PomoFrame {
    private JFrame pomoFrame;
    private JMenu settings, help;
    private JMenuBar menuBar;

    public PomoFrame() {
        PomoDesign();
    }

    public void PomoDesign(){
        pomoFrame = new JFrame();
        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(251, 246, 239));
        pomoFrame.setContentPane(contentPane);
        PomoPanel pomoPanel = new PomoPanel();
        PomoMenuDesign();
        
        pomoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pomoFrame.setPreferredSize(new Dimension(220, 200));
        pomoFrame.setTitle("Pomodoro Timer");
        pomoFrame.getContentPane().add(pomoPanel.getPomoPanel());
        pomoFrame.setJMenuBar(menuBar);
        pomoFrame.pack();
        pomoFrame.setVisible(true);
    }

    public void PomoMenuDesign(){
        menuBar = new JMenuBar();
        settings = new JMenu("Settings");

        help = new JMenu("Help");

        //deselects jmenu when clicked to prevent it from staying highlighted
        settings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getSource() == settings) {
                System.out.println("Settings");
                PomoSettingsDialog settingsGUI = new PomoSettingsDialog(pomoFrame);
                settingsGUI.setLocationRelativeTo(pomoFrame);
                settingsGUI.createAndShowGUI();
                }
            }
        });

        help.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getSource() == help) {
                System.out.println("Help");
                }
            }
        });

        menuBar.add(settings);
        menuBar.add(help);
    }


    public JFrame getPomoFrame() {
        return pomoFrame;
    }


    
}