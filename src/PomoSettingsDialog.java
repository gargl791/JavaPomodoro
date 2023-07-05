import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import javax.swing.JTextField;


public class PomoSettingsDialog {
    private JTabbedPane tabbedPane;
    private JDialog dialog;
    private static PomoPanel p;

    public PomoSettingsDialog() {
        PomoAudio.checkAudio();
        PomoSettingsDialogDesign();
    }

    public void PomoSettingsDialogDesign() {
        // Creating the frame and jdialog box
        dialog = new JDialog(dialog, "Settings", true);
        dialog.setSize(new Dimension(225, 300));

        // Creating tabbed panes
        tabbedPane = new JTabbedPane();

        // JPanel
        JPanel timerPanel = new JPanel();
        timerPanel.setLayout(new BorderLayout());
        timerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        // setting up the timer panel
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
        gbc.insets = new Insets(5, 5, 5, 5);

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

        /*
         * timerPanel.add(changeTime, BorderLayout.NORTH);
         * timerPanel.add(changeBreak, BorderLayout.CENTER);
         * timerPanel.add(changeLongBreak, BorderLayout.SOUTH);
         * timerPanel.add(saveButton, BorderLayout.SOUTH);
         */
        /*
         * timerPanel.add(shortBreakField);
         * timerPanel.add(longBreakField);
         */

        // initializing the text fields
        p = new PomoPanel();
        timerField.setText(String.valueOf((convertToMin(p.getPomoTime()))));
        shortBreakField.setText(String.valueOf(convertToMin(p.getShortBreakTime())));
        longBreakField.setText(String.valueOf(convertToMin(p.getLongBreakTime())));
        
        // actionListeners
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.setPomoTime(convertToMs(Long.valueOf(timerField.getText())));
                p.setShortBreakTime(convertToMs(Long.valueOf(shortBreakField.getText())));
                p.setLongBreakTime(convertToMs(Long.valueOf(longBreakField.getText())));
                dialog.dispose();
                System.out.println("saved");
            }
        });


        //setting up the soundPanel
        JPanel soundPanel = new JPanel();

        JPanel alarmPanel = new JPanel(new BorderLayout());
        JPanel windUpPanel = new JPanel(new BorderLayout());

        JPanel alarmNorthPanel = new JPanel();
        JPanel alarmCenterPanel = new JPanel();
        JPanel alarmSouthPanel = new JPanel();

        JButton saveButton2 = new JButton("Save");

        alarmNorthPanel.setLayout(new BoxLayout(alarmNorthPanel, BoxLayout.X_AXIS));
        alarmCenterPanel.setLayout(new BoxLayout(alarmCenterPanel, BoxLayout.X_AXIS));
        alarmSouthPanel.setLayout(new BoxLayout(alarmSouthPanel, BoxLayout.X_AXIS));
        
        JLabel alarmLabel = new JLabel("Alarm");

        JTextField alarmText = new JTextField(PomoAudio.getAlarmDirFile().getName());
        JTextField windUpText = new JTextField(PomoAudio.getWindUpDirFile().getName());
        
        //checks if it is using the default audio files
        if(alarmText.getText().equals("alarm.wav")) {
            alarmText.setText("Default");
        }
        if(windUpText.getText().equals("windup.wav")) {
            windUpText.setText("Default");
        }

        alarmText.setEnabled(false);
        alarmText.setColumns(9);

        JButton alarmButton = new JButton("Choose File");
        alarmButton.setPreferredSize(new Dimension(50,10));

        alarmNorthPanel.add(alarmLabel, BorderLayout.WEST);
        alarmNorthPanel.add(alarmText, BorderLayout.CENTER);
        alarmNorthPanel.add(alarmButton, BorderLayout.EAST);

        JLabel windUpLabel = new JLabel("Wind ");
        
        JButton windUpButton = new JButton("Choose File");

        windUpText.setEnabled(false);
        windUpText.setColumns(9);
        windUpButton.setPreferredSize(new Dimension(50,10));
        alarmCenterPanel.add(windUpLabel, BorderLayout.WEST);
        alarmCenterPanel.add(windUpText, BorderLayout.CENTER);
        alarmCenterPanel.add(windUpButton, BorderLayout.EAST);


        alarmPanel.add(alarmNorthPanel, BorderLayout.NORTH);
        alarmPanel.add(alarmCenterPanel, BorderLayout.CENTER);
        alarmPanel.add(saveButton2, BorderLayout.SOUTH);
        alarmPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));



        soundPanel.add(alarmPanel, BorderLayout.NORTH);

        //actionlisteners for alarmPanel
        alarmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == alarmButton) {
                    JFileChooser fileChooser = new JFileChooser();
                    int response = fileChooser.showOpenDialog(null); 

                    if(response == JFileChooser.APPROVE_OPTION) {
                        
                        PomoAudio.saveAudio(new File(fileChooser.getSelectedFile().getAbsolutePath()), true);
                        alarmText.setText(PomoAudio.getAlarmDirFile().getName());
                    }
                }
            }
        });

        windUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == windUpButton) {
                    JFileChooser fileChooser = new JFileChooser();
                    int response = fileChooser.showOpenDialog(null); 

                    if(response == JFileChooser.APPROVE_OPTION) {
                        PomoAudio.saveAudio(new File(fileChooser.getSelectedFile().getAbsolutePath()), false);
                        windUpText.setText(PomoAudio.getWindUpDirFile().getName());
                    }
                }
            }
        });

        saveButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == saveButton2) {
                    PomoAudio.checkAudio();
                    dialog.dispose();
                    System.out.println("saved2");
                }
            }
        });




        tabbedPane.add("Timer", timerPanel);
        tabbedPane.add("Sound", soundPanel);
        

        dialog.add(tabbedPane);

    }

    public void createAndShowGUI() {
        dialog.pack();
        dialog.setVisible(true);
    }

    public static long convertToMin(long ms) {
        return ((ms / 1000) / 60);
    }

    public static long convertToMs(long min) {
        return (min * 1000 * 60);
    }

    public void setLocationRelativeTo(JFrame pomoFrame) {
        int x = pomoFrame.getX();
        int y = pomoFrame.getY();
        dialog.setLocation(x, y);
    }

    
}