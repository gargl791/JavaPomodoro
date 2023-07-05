import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;


public class PomoFrame {
    private JFrame pomoFrame;
    private static JMenu settings;
    private JMenu help;
    private JMenuBar menuBar;
    private static PomoPanel pomoPanel;
    private static boolean menuStatus; // false is disabled, true is enabled

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

        PomoAudio.checkAudio(); //checks if audio files exist
        
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
                if(e.getSource() == settings && menuStatus == false) {
                System.out.println("Settings");
                System.out.println(menuStatus);
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


    public static void setMenuStatus(boolean flag) {
        if(flag) {
            settings.setEnabled(false);
            PomoFrame.menuStatus = true;
        }
        else {
            settings.setEnabled(true);
            PomoFrame.menuStatus = false;

        }
    }

    public JFrame getPomoFrame() {
        return pomoFrame;
    }


    
}