import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
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
        dialog.setSize(new Dimension(225, 300));

        //Creating tabbed panes
        tabbedPane = new JTabbedPane();
        

        //JPanel
        JPanel timerPanel = new JPanel();
        JPanel soundPanel = new JPanel();
        timerPanel.setLayout(new BorderLayout());
        timerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        soundPanel.setLayout(new GridLayout());


        //setting up the timer panel
        JTextField timerField = new JTextField();
        JTextField shortBreakField = new JTextField();
        JTextField longBreakField = new JTextField();

        timerField.setColumns(4);
        shortBreakField.setColumns(4);
        longBreakField.setColumns(4);

        JPanel changeTime = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel changeBreak = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel changeLongBreak = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel pomodoroLabel = new JLabel("Pomodoro");
        JLabel shortBreakLabel = new JLabel("Short Break");
        JLabel longBreakLabel = new JLabel("Long Break");
        JLabel timeMinutesLabel = new JLabel(" minutes");
        JLabel shortMinutesLabel = new JLabel(" minutes");
        JLabel longMinutesLabel = new JLabel(" minutes");

        JButton saveButton = new JButton("Save");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5 ,5, 5, 5);

        changeTime.add(pomodoroLabel);
        changeTime.add(timerField);
        changeTime.add(timeMinutesLabel);

        changeBreak.add(shortBreakLabel);
        changeBreak.add(shortBreakField);
        changeBreak.add(shortMinutesLabel);

        changeLongBreak.add(longBreakLabel);
        changeLongBreak.add(longBreakField);
        changeLongBreak.add(longMinutesLabel);

        changeTime.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        changeBreak.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        changeLongBreak.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        changeTime.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        changeBreak.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        changeLongBreak.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridBagLayout());
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.add(changeTime, gbc);
        northPanel.add(changeBreak, gbc);
        northPanel.add(changeLongBreak, gbc);


        timerPanel.add(northPanel, BorderLayout.NORTH);
        timerPanel.add(saveButton, BorderLayout.SOUTH);

/*         timerPanel.add(changeTime, BorderLayout.NORTH);
        timerPanel.add(changeBreak, BorderLayout.CENTER);
        timerPanel.add(changeLongBreak, BorderLayout.SOUTH);
        timerPanel.add(saveButton, BorderLayout.SOUTH); */
/*         timerPanel.add(shortBreakField);
        timerPanel.add(longBreakField); */


        
        tabbedPane.add("Timer", timerPanel);
        tabbedPane.add("Sound", soundPanel);
        


        dialog.add(tabbedPane);
        dialog.setVisible(true);

    }


    public static void main(String[]args) {
        new PomoSettingsDialog();
    }

}