import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PomoSettingsDialog {
    private JTabbedPane tabbedPane;
    private JDialog dialog;
    private JFrame frame;

    public PomoSettingsDialog() {
        PomoSettingsDialogDesign();
    }

    public void PomoSettingsDialogDesign() {
        //Creating the frame and jdialog box
        frame = new JFrame();
        dialog = new JDialog(frame, "Settings", true);
        dialog.setLayout(new GridLayout());
        dialog.setSize(new Dimension(225, 300));

        //Creating tabbed panes
        tabbedPane = new JTabbedPane();
        dialog.add(tabbedPane);

        //JPanel
        JPanel timerPanel = new JPanel();
        JPanel soundPanel = new JPanel();
        timerPanel.setLayout(new BoxLayout(timerPanel, BoxLayout.Y_AXIS));
        soundPanel.setLayout(new GridLayout());


        //setting up the timer panel
        JTextField timerField = new JTextField();
        JTextField shortBreakField = new JTextField();
        JTextField longBreakField = new JTextField();

        timerField.setColumns(4);
        shortBreakField.setColumns(4);
        longBreakField.setColumns(4);

        JPanel changeTime = new JPanel(new FlowLayout());
        JPanel changeBreak = new JPanel(new FlowLayout());
        JPanel changeLongBreak = new JPanel(new FlowLayout());

        JLabel pomodoroLabel = new JLabel("Pomodoro ");
        JLabel shortBreakLabel = new JLabel("Short Break ");
        JLabel longBreakLabel = new JLabel("Long Break ");
        JLabel timeMinutesLabel = new JLabel(" minutes");
        JLabel shortMinutesLabel = new JLabel(" minutes");
        JLabel longMinutesLabel = new JLabel(" minutes");

        JButton saveButton = new JButton("Save");

        changeTime.add(pomodoroLabel);
        changeTime.add(timerField);
        changeTime.add(timeMinutesLabel);

        changeBreak.add(shortBreakLabel);
        changeBreak.add(shortBreakField);
        changeBreak.add(shortMinutesLabel);

        changeLongBreak.add(longBreakLabel);
        changeLongBreak.add(longBreakField);
        changeLongBreak.add(longMinutesLabel);


        

        timerPanel.add(changeTime);
        timerPanel.add(changeBreak);
        timerPanel.add(changeLongBreak);
        timerPanel.add(saveButton);
        timerPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
/*         timerPanel.add(shortBreakField);
        timerPanel.add(longBreakField); */


        
        tabbedPane.add("Timer", timerPanel);
        tabbedPane.add("Sound", soundPanel);
        



        dialog.setVisible(true);

    }

    public static void main(String[]args) {
        new PomoSettingsDialog();
    }

}