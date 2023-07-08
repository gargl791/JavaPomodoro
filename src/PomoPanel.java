import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.TimeZone;
import java.awt.Font;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;

public class PomoPanel {

    private SimpleDateFormat df;
    private JPanel pomoPanel, buttonPanel, breakPanel, timePanel;
    private JLabel timerLabel;
    private JButton start, rest, restart, skip;
    private JProgressBar bar;
    private static long timeSetMs = 5 * 1000;
    private static long shortBreakTimeMs = 1 * 1000;
    private static long longBreakTimeMs = 10 * 1000;
    private static long timeTrack;
    private long barFull;
    private long barIncrement;
    private Timer time, breakTime;
    private Date date;
    private Font digitalFont;
    private Font digitalFontBold;
    private String formattedTime;
    private int shortBreakCount = 0;
    private boolean breakFlag = false;
    private PomoAudio audio;

    public PomoPanel() {
        // use custom font for timer text
        InputStream is = getClass().getResourceAsStream("font/digital-7.ttf");
        try {
            digitalFont = Font.createFont(Font.TRUETYPE_FONT, is);

            digitalFontBold = digitalFont.deriveFont(Font.PLAIN, 45f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkPomoTime();
        PomoPanelDesign();

    }

    public JPanel createTimePanel()
    {
        JPanel timePanel = new JPanel();
        timePanel.setBackground(new Color(251, 246, 239));
        timePanel.setLayout(new BorderLayout());

        return timePanel;
    }

    // breakPanel to indicate short breaks
    public JPanel createBreakPanel()
    {
        
        JPanel breakPanel = new JPanel(new FlowLayout());
        breakPanel.setBackground(new Color(251, 246, 239));
        
        return breakPanel;
    }

    //creates panel for interactive jbuttons
    public JPanel createButtonPanel()
    {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(251, 246, 239));

        return buttonPanel;
    }

    //creates main panel for pomoPanel
    public JPanel creatPomoPanel()
    {
        pomoPanel = new JPanel(new BorderLayout());
        pomoPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        pomoPanel.setLayout(new BorderLayout());

        pomoPanel.setBackground(new Color(251, 246, 239));
        return pomoPanel;
    }

    //initializes break timer
    public void registerBreakTime()
    {
        breakTime = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.out.println("Break time");
                System.out.println("m:s " + (int) Math.floor(timeTrack / 1000 / 60) + ":" + (timeTrack / 1000 % 60));
                timeTrack -= 1000; // decrements the time by 1 second

                formatTime();

                if(timeTrack >= 0) {
                    updateProgressBar();
                }
                
                if (timeTrack <= 0) {
                    breakTime.stop();
                    if (breakFlag && shortBreakCount == 4) {
                        addImgBreak(breakPanel, 20);
                    }
                    setBreakFlag(false);
                    setButtonVisibility(buttonPanel, rest, false);
                    setButtonVisibility(buttonPanel, restart, false);
                    setButtonVisibility(buttonPanel, start, true);
                    setButtonVisibility(buttonPanel, skip, false);
                    timerLabel.setForeground(new Color(234, 215, 195));
                    System.out.println("COUNT: " + shortBreakCount);
                    updateTime();
                    formatTime();
                    PomoFrame.setMenuStatus(false);
                    barFull = timeTrack;
                    bar.setMaximum((int) barFull);
                    bar.setValue((int) barFull);
                    bar.setForeground(new Color(221, 190, 169));
                    playAudio(true);
                    System.out.println(PomoAudio.getAlarmDirFile());
                }
            }
        });
    }

    //initializes focus timer
    public void registerPomoTimer() 
    {
        time = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {

                System.out.println(timeTrack / 1000);
                System.out.println("m:s " + (int) Math.floor(timeTrack / 1000 / 60) + ":" + (timeTrack / 1000 % 60));
                timeTrack -= 1000; // decrements the time by 1 second

                formatTime();

                if(timeTrack >= 0) {
                    updateProgressBar();
                }
                if (timeTrack <= 0) {
                    time.stop();
                    setBreakFlag(true);

                    addImgBreak(breakPanel, 20);
                    shortBreakCount++;
                    setButtonVisibility(buttonPanel, rest, true);
                    setButtonVisibility(buttonPanel, restart, false);
                    setButtonVisibility(buttonPanel, skip, false);

                    PomoFrame.setMenuStatus(false);
                    updateTime();
                    formatTime();
                    barFull = timeTrack;
                    bar.setMaximum((int) barFull);
                    bar.setValue((int) barFull);
                    bar.setForeground((new Color(242, 122, 125)));
                    playAudio(true);
                    System.out.println(PomoAudio.getAlarmDirFile());
                }

            }
        });
    }

    public void registerStartButton()
    {
        setButtonVisibility(buttonPanel, restart, true);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.out.println("Start button pressed");
                updateTime();
                if (!time.isRunning()) {
                    barFull = timeTrack;
                    bar.setMaximum((int) barFull);
                    bar.setValue((int) barFull);
                    setButtonVisibility(buttonPanel, skip, true);
                    setButtonVisibility(buttonPanel, restart, true);
                    setButtonVisibility(buttonPanel, start, false);
                    

                    // if the timer is running, disable the settings menu
                    PomoFrame.setMenuStatus(true);

                    time.start();
                    playAudio(false);
                    System.out.println(PomoAudio.getWindUpDirFile());

                }
            }
        });
    }

    public void registerRestButton()
    {
        setButtonVisibility(buttonPanel, rest, false);
        rest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.out.println("Rest button pressed");
                updateTime();
                barFull = timeTrack;
                bar.setMaximum((int) barFull);
                bar.setValue((int) barFull);
                breakTime.start();
                // if the timer is running, disable the settings menu
                PomoFrame.setMenuStatus(true);

                setButtonVisibility(buttonPanel, rest, false);
                setButtonVisibility(buttonPanel, skip, true);
                setButtonVisibility(buttonPanel, restart, true);
                playAudio(false);
                System.out.println(PomoAudio.getWindUpDirFile());
            }
        });
    }

    public void registerSkipButton()
    {
        setButtonVisibility(buttonPanel, skip, false);
        skip.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.out.println("Stop button pressed");
                timeTrack = 0;
                if (time.isRunning() || breakTime.isRunning()) {
                if(breakFlag && shortBreakCount == 4) {
                    bar.setString("LONG BREAK SKIPPED");
                    bar.setMaximum((int) longBreakTimeMs);
                    bar.setValue((int) longBreakTimeMs);
                } else if (breakFlag) {
                    bar.setString("SHORT BREAK SKIPPED");
                    bar.setMaximum((int) shortBreakTimeMs);
                    bar.setValue((int) shortBreakTimeMs);
                } else {
                    bar.setString("FOCUS SKIPPED");
                    bar.setMaximum((int) timeSetMs);
                    bar.setValue((int) timeSetMs);
                }
                }
            }
        });
    }

    public void registerRestartButton()
    {
        setButtonVisibility(buttonPanel, restart, false);
        restart.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.out.println("Restart button pressed");
                String state = "";
                if(time.isRunning()) {
                    time.restart();
                    time.stop();
                    setButtonVisibility(buttonPanel, start, true);
                } else if (breakTime.isRunning()) {
                    breakTime.restart();
                    breakTime.stop();
                    setButtonVisibility(buttonPanel, rest, true);
                }
                updateTime();
                barFull = timeSetMs;
                bar.setMaximum((int) barFull);
                bar.setValue((int) barFull);
                date = new Date(timeTrack);
                formatTime();
                bar.setString("RESTARTED " + state);
                
                setButtonVisibility(buttonPanel, restart, false);
                setButtonVisibility(buttonPanel, skip, false);

                // if the timer is running, disable the settings menu
                PomoFrame.setMenuStatus(false);

            }
        });
    }

    public JProgressBar createProgressBar() 
    {
        bar = new JProgressBar();
        bar.setForeground(new Color(221, 190, 169));
        bar.setStringPainted(true);
        bar.setString("START");
        bar.setMaximum((int) timeSetMs);
        barIncrement = 100 / (timeTrack / 1000);
        barFull = timeSetMs;
        bar.setValue((int) timeSetMs);
        return bar;
    }

    public SimpleDateFormat createDateFormat()
    {
        df = new SimpleDateFormat(new String(timeTrack >= 3600000 ? "HH:mm:ss" : "mm:ss"));
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df;
    }



    public void PomoPanelDesign()
    {
        timePanel = createTimePanel();
        breakPanel = createBreakPanel();
        buttonPanel = createButtonPanel();
        pomoPanel = creatPomoPanel();


        timeTrack = timeSetMs;

        // if time is greater than 1 hour, display hours, else display minutes and
        // seconds
        df = createDateFormat();
        date = new Date(timeTrack);
        formattedTime = df.format(date);

        timerLabel = new JLabel(formattedTime, SwingConstants.CENTER);
        timerLabel.setText(formattedTime);
        timerLabel.setFont(digitalFontBold);
        timerLabel.setForeground(new Color(234, 215, 195));

        bar = createProgressBar();


        registerPomoTimer();

        registerBreakTime();

        timerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        timePanel.add(timerLabel, BorderLayout.NORTH);
        timePanel.add(bar, BorderLayout.SOUTH);
        timePanel.add(timerLabel, BorderLayout.NORTH);

        start = new JButton();
        skip = new JButton();
        restart = new JButton();
        rest = new JButton();

        //start button
        registerStartButton();
        
        // break/rest button
        registerRestButton();

        //skip button for stopping the timer
        registerSkipButton();

        // restart button
        registerRestartButton();

        //set up images and jbutton sizes
        int size = 30;
        restart.setPreferredSize(new Dimension(size, size));
        skip.setPreferredSize(new Dimension(size, size));
        start.setPreferredSize(new Dimension(size, size));
        rest.setPreferredSize(new Dimension(size, size));
        addImg(start, "images/start.png", size);
        addImg(skip, "images/skip.jpg", size);
        addImg(restart, "images/restart.png", size);
        addImg(rest, "images/rest.png", size);

        buttonPanel.add(start);
        buttonPanel.add(skip);
        buttonPanel.add(restart);
        buttonPanel.add(rest);

        start.setBackground(new Color(234, 215, 195));
        skip.setBackground(new Color(234, 215, 195));
        restart.setBackground(new Color(234, 215, 195));
        rest.setBackground(new Color(234, 215, 195));

        pomoPanel.add(breakPanel, BorderLayout.NORTH);
        pomoPanel.add(timePanel, BorderLayout.CENTER);
        pomoPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public JPanel getPomoPanel() {
        return pomoPanel;
    }

    public void setTimeSet(long ts) {
        timeSetMs = ts;
    }

    public void addImg(JButton button, String path, int size) {
        try {
            BufferedImage img = null;
            img = ImageIO.read(new File(path));
            Image img1 = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            ImageIcon format = new ImageIcon(img1);
            button.setIcon(format);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addImgBreak(JPanel panel, int size) {
        StringBuilder sb = new StringBuilder("images/");
        Random r = new Random();
        // rng of chars within a - h
        String imgFile = ".png";
        String randomChar = String.valueOf((char) (r.nextInt(104 - 97 + 1) + 97));

        switch (shortBreakCount) {
            case 0:
                sb.append(randomChar + imgFile);
                System.out.println(sb.toString());
                break;
            case 1:
                sb.append(randomChar + imgFile);
                break;
            case 2:
                sb.append(randomChar + imgFile);
                break;
            case 3:
                sb.append(randomChar + imgFile);
                break;
            case 4:
                setShortBreakCount(0);
                panel.removeAll();
                panel.revalidate();
                panel.repaint();
                return;
        }
        try {
            BufferedImage img = ImageIO.read(new File(sb.toString()));
            Image img1 = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            JLabel bocchi = new JLabel(new ImageIcon(img1));
            panel.add(bocchi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProgressBar() {
        bar.setValue(((int) barFull - 1000));
        barFull = barFull - 1000;
        System.out.println("BARFULL: " + barFull);

        if (barFull == 0 && breakFlag && shortBreakCount == 4) {
            bar.setString("LONG BREAK DONE");
            return;
        } else if (barFull == 0 && breakFlag) {
            bar.setString("SHORT BREAK DONE");
            return;
        } else if (barFull == 0) {
            bar.setString("FOCUS DONE");
            return;
        }

        if (barFull % 2000 == 1000) {
            bar.setString("");
            return;
        }


        if (breakFlag && shortBreakCount == 4) {
            bar.setString("LONG BREAK");
            bar.setForeground((new Color(242, 122, 125)));
        } else if (!breakFlag) {
            bar.setString("FOCUS");
            bar.setForeground((new Color(221, 190, 169)));
        } else if (breakFlag) {
            bar.setString("SHORT BREAK");
            bar.setForeground((new Color(242, 122, 125)));
        }

    }

    public void setButtonVisibility(JPanel panel, JButton b, boolean flag) {
        b.setEnabled(flag);
        b.setVisible(flag);
        panel.revalidate();
        panel.repaint();
    }

    public void formatTime()
    {
        df = new SimpleDateFormat(new String(timeTrack >= 3600000 ? "HH:mm:ss" : "mm:ss"));
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        formattedTime = df.format(new Date(timeTrack));
        System.out.println("TIME: " + formattedTime);
        timerLabel.setText(formattedTime);
        timerLabel.setFont(digitalFontBold);
        if (breakFlag) {
            timerLabel.setForeground(new Color(242, 122, 125));
        } else {
            timerLabel.setForeground(new Color(234, 215, 195));
        }
    }

    public static void playAudio(boolean flag) {
        try {
            PomoAudio.playAudio(flag);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
            e1.printStackTrace();
        }
    }

    public void updateTime() {
        if (breakFlag && shortBreakCount == 4) {
            timeTrack = longBreakTimeMs;
        } else if (breakFlag && shortBreakCount < 4) {
            timeTrack = shortBreakTimeMs;
        } else {
            timeTrack = timeSetMs;
        }
    }

    public void checkPomoTime() {
        File file = new File("data/pomoTime.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write("1500000" + "\n");
                writer.write("300000" + "\n");
                writer.write("600000" + "\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLong()) {
                timeSetMs = scanner.nextLong();
                shortBreakTimeMs = scanner.nextLong();
                longBreakTimeMs = scanner.nextLong();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setShortBreakCount(int count) {
        this.shortBreakCount = count;
    }

    public void setBreakFlag(boolean flag) {
        this.breakFlag = flag;
    }

    public void setPomoTime(long t) {
        while (time.isRunning()) {
            System.out.println("wait pomo");
        }
        timeSetMs = t;

        formatTime();
    }

    // assumes the timer is not running. Method for updating the time after settings
    // change
    public void updateTimeTrack() {
        if (breakFlag && shortBreakCount == 4) {
            timeTrack = longBreakTimeMs;
        } else if (breakFlag && shortBreakCount < 4) {
            timeTrack = shortBreakTimeMs;
        } else {
            timeTrack = timeSetMs;
        }
        formatTime();
        pomoPanel.repaint();
    }

    public void setShortBreakTime(long t) {
        while (breakTime.isRunning()) {
            System.out.println("wait short break");
        }
        shortBreakTimeMs = t;

        formatTime();
    }

    public void setLongBreakTime(long t) {
        while (breakTime.isRunning()) {
            System.out.println("wait long break");
        }
        longBreakTimeMs = t;

        formatTime();
    }

    public long getPomoTime() {
        return timeSetMs;
    }

    public long getShortBreakTime() {
        return shortBreakTimeMs;
    }

    public long getLongBreakTime() {
        return longBreakTimeMs;
    }

    public static void main(String[] args) {
        new PomoFrame();
    }

}
