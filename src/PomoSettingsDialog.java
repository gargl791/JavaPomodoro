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
import java.io.FileWriter;
import java.io.IOException;

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
    private PomoPanel p;
    private JButton saveButton, saveButton2, alarmButton, windUpButton, defaultButton;
    private JTextField timerField, shortBreakField, longBreakField, alarmText, windUpText;
    private JPanel timerPanel, changeTime, changeBreak, changeLongBreak;

    public PomoSettingsDialog(PomoPanel p) {
        this.p = p;
        PomoAudio.checkAudio();
        PomoSettingsDialogDesign();
    }



    public void registerSaveButton()
    {

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                long timerValue = Long.valueOf(timerField.getText());
                long shortBreakValue = Long.valueOf(shortBreakField.getText());
                long longBreakValue = Long.valueOf(longBreakField.getText());
                if(timerField.getText().length() == 0 || shortBreakField.getText().length() == 0 || longBreakField.getText().length() == 0)
                {
                    System.out.println("empty");
                    return;
                }
                else if (timerField.getText().matches("[a-zA-Z]+") || shortBreakField.getText().matches("[a-zA-Z]+") || longBreakField.getText().matches("[a-zA-Z]+"))
                {
                    System.out.println("contains letters");
                    return;
                }
                else if((timerValue < 1 || timerValue > 1440) || (shortBreakValue < 1 || shortBreakValue > 1440) || (longBreakValue < 1 || longBreakValue > 1440))
                {
                    System.out.println("out of bounds, add within 1-1440 minutes");
                    return;
                } 

                p.setPomoTime(convertToMs(Long.valueOf(timerField.getText())));
                p.setShortBreakTime(convertToMs(Long.valueOf(shortBreakField.getText())));
                p.setLongBreakTime(convertToMs(Long.valueOf(longBreakField.getText())));
                
                //while the timer isnt running, updates the time when changed in settings
                p.updateTimeTrack();

                try (FileWriter writer = new FileWriter("sdata/pomoTime.txt")) {
                    writer.write(String.valueOf(convertToMs(timerValue)));
                    writer.write("\n" + String.valueOf(convertToMs(shortBreakValue)));
                    writer.write("\n" + String.valueOf(convertToMs(longBreakValue)));
                    writer.close();
                } 
                catch (IOException e1) {
                    e1.printStackTrace();
                }
                dialog.dispose();
                System.out.println("saved");
            }
        });
    }

    //save button for soundpanel
    public void registerSaveButton2()
    {
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
    }
    //reverrt to default sounds in soundpanel
    public void registerDefaultButton()
    {
        defaultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == defaultButton) {
                    PomoAudio.setDefaultAudio();
                    alarmText.setText("Default");
                    windUpText.setText("Default");

                }
            }
        });
    }

    //creates the timer panel and all of the other nested jpanels
    public JPanel createMainPanel() 
    {
        JPanel timerPanel = new JPanel();
        timerPanel.setLayout(new BorderLayout());
        timerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        return timerPanel;
    }

    public JPanel createPanel() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        return panel;
    }

    //actionListener for selecting the alarm file
    public void registerAlarmButton()
    {
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
    }

    public void registerWindUpButton()
    {
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
    }


    //initializes the settings dialog
    public void PomoSettingsDialogDesign() {
        // Creating the frame and jdialog box
        dialog = new JDialog(dialog, "Settings", true);
        dialog.setSize(new Dimension(225, 300));

        // Creating tabbed panes
        tabbedPane = new JTabbedPane();

        // JPanel
        timerPanel = createMainPanel();
        changeTime = createPanel();
        changeBreak = createPanel();
        changeLongBreak = createPanel();

        // setting up the timer panel
        timerField = new JTextField();
        shortBreakField = new JTextField();
        longBreakField = new JTextField();

        timerField.setColumns(4);
        shortBreakField.setColumns(4);
        longBreakField.setColumns(4);

    
        JLabel pomodoroLabel = new JLabel("Pomodoro");
        JLabel shortBreakLabel = new JLabel("Short Break");
        JLabel longBreakLabel = new JLabel("Long Break");
        JLabel timeMinutesLabel = new JLabel(" minutes");
        JLabel shortMinutesLabel = new JLabel(" minutes");
        JLabel longMinutesLabel = new JLabel(" minutes");

        saveButton = new JButton("Save");

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

        // initializing the text fields
        timerField.setText(String.valueOf((convertToMin(p.getPomoTime()))));
        shortBreakField.setText(String.valueOf(convertToMin(p.getShortBreakTime())));
        longBreakField.setText(String.valueOf(convertToMin(p.getLongBreakTime())));
        
        // actionListeners
        registerSaveButton();


        //setting up the soundPanel
        JPanel soundPanel = createMainPanel();

        JPanel alarmPanel = new JPanel(new BorderLayout());
        JPanel windUpPanel = new JPanel(new BorderLayout());

        JPanel alarmNorthPanel = createMainPanel();
        JPanel alarmCenterPanel = createMainPanel();
        JPanel alarmSouthPanel = createMainPanel();

        saveButton2 = new JButton("Save");
        defaultButton = new JButton("Default");

        alarmNorthPanel.setLayout(new BoxLayout(alarmNorthPanel, BoxLayout.X_AXIS));
        alarmCenterPanel.setLayout(new BoxLayout(alarmCenterPanel, BoxLayout.X_AXIS));
        alarmSouthPanel.setLayout(new BoxLayout(alarmSouthPanel, BoxLayout.X_AXIS));
        
        JLabel alarmLabel = new JLabel("Alarm");

        alarmText = new JTextField(PomoAudio.getAlarmDirFile().getName());
        windUpText = new JTextField(PomoAudio.getWindUpDirFile().getName());
        
        //checks if it is using the default audio files
        if(alarmText.getText().equals("alarm.wav")) {
            alarmText.setText("Default");
        }
        if(windUpText.getText().equals("windup.wav")) {
            windUpText.setText("Default");
        }

        alarmText.setEnabled(false);
        alarmText.setColumns(9);

        alarmButton = new JButton("Choose File");
        alarmButton.setPreferredSize(new Dimension(50,10));

        alarmNorthPanel.add(alarmLabel, BorderLayout.WEST);
        alarmNorthPanel.add(alarmText, BorderLayout.CENTER);
        alarmNorthPanel.add(alarmButton, BorderLayout.EAST);

        JLabel windUpLabel = new JLabel("Wind ");
        
        windUpButton = new JButton("Choose File");

        windUpText.setEnabled(false);
        windUpText.setColumns(9);
        windUpButton.setPreferredSize(new Dimension(50,10));
        alarmCenterPanel.add(windUpLabel, BorderLayout.WEST);
        alarmCenterPanel.add(windUpText, BorderLayout.CENTER);
        alarmCenterPanel.add(windUpButton, BorderLayout.EAST);

        alarmSouthPanel.add(defaultButton, BorderLayout.NORTH);
        alarmSouthPanel.add(saveButton2, BorderLayout.SOUTH);


        alarmPanel.add(alarmNorthPanel, BorderLayout.NORTH);
        alarmPanel.add(alarmCenterPanel, BorderLayout.CENTER);
        alarmPanel.add(alarmSouthPanel, BorderLayout.SOUTH);
        alarmPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));



        soundPanel.add(alarmPanel, BorderLayout.NORTH);

        //actionlisteners for alarmButton
        registerAlarmButton();

        //actionlisteners for windUpButton
        registerWindUpButton();

        //actionlisteners for saveButton2
        registerSaveButton2();

        //actionListener for default button
        registerDefaultButton();




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