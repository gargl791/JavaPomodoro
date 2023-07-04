import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;


public class PomoFrame {
    private JFrame pomoFrame;
    private JMenu settings, help;
    private JMenuBar menuBar;
    private static PomoPanel pomoPanel;

    public PomoFrame() {
        PomoDesign();
    }

    public void PomoDesign(){
        pomoFrame = new JFrame();
        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(251, 246, 239));
        pomoFrame.setContentPane(contentPane);
        PomoMenuDesign();
        pomoPanel = new PomoPanel();
        
        pomoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pomoFrame.setPreferredSize(new Dimension(220, 200));
        pomoFrame.pack();
        
        pomoFrame.setTitle("Pomodoro Timer");
        pomoFrame.getContentPane().add(pomoPanel.getPomoPanel());
        pomoFrame.setJMenuBar(menuBar);

        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screenSize.getWidth() - pomoFrame.getWidth() - 185;
        int y = (int) screenSize.getHeight() - pomoFrame.getHeight() - 50;

        pomoFrame.setLocation(x, y);
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
                PomoSettingsDialog settingsGUI = new PomoSettingsDialog();
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