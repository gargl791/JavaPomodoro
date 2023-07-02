import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
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
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Font;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;

public class PomoPanel {

    private JPanel pomoPanel;
    private JLabel lblTimer;
    private JButton start, rest, restart;
    private JProgressBar bar;
    private CountdownTimer count;
    private long timeSet = 900 * 1000;
    private long shortBreakTime = 350 * 1000;
    private long longBreakTime = 400 * 1000;
    private long timeTrack;
    private long barFull;
    private long barIncrement;
    private Timer time, breakTime;
    private Date date;
    private Font digitalFont;
    private Font digitalFontBold;
    private String formattedTime;
    private int shortBreakCount = 0;
    private boolean breakFlag = false;

    public PomoPanel() {
        // use custom font for timer text
        InputStream is = getClass().getResourceAsStream("font/digital-7.ttf");
        try {
            digitalFont = Font.createFont(Font.TRUETYPE_FONT, is);

            digitalFontBold = digitalFont.deriveFont(Font.PLAIN, 45f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PomoPanelDesign();
    }

    public void PomoPanelDesign() {
        JPanel timePanel = new JPanel();
        timePanel.setBackground(new Color(251, 246, 239));
        timePanel.setLayout(new BorderLayout());
        timeTrack = timeSet;

        SimpleDateFormat df = new SimpleDateFormat("mm:ss");
        date = new Date(timeTrack);
        formattedTime = df.format(date);

        JLabel timerLabel = new JLabel(formattedTime, SwingConstants.CENTER);
        timerLabel.setText(formattedTime);
        timerLabel.setFont(digitalFontBold);
        timerLabel.setForeground(new Color(234, 215, 195));

        bar = new JProgressBar();
        bar.setForeground(new Color(221, 190, 169));
        bar.setStringPainted(true);
        bar.setString("START");
        bar.setMaximum((int) timeSet);
        barIncrement = 100 / (timeTrack / 1000);
        barFull = timeSet;
        bar.setValue((int) timeSet);

        // breakPanel to indicate short breaks
        JPanel breakPanel = new JPanel(new FlowLayout());
        breakPanel.setBackground(new Color(251, 246, 239));

        time = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {

                System.out.println(timeTrack / 1000);
                System.out.println("m:s " + (int) Math.floor(timeTrack / 1000 / 60) + ":" + (timeTrack / 1000 % 60));
                timeTrack -= 1000; // decrements the time by 1 second

                date = new Date(timeTrack);

                formattedTime = df.format(date);

                timerLabel.setText(formattedTime);
                timerLabel.setFont(digitalFontBold);
                timerLabel.setForeground(new Color(234, 215, 195));

                updateProgressBar();
                if(timeTrack == 0){
                    time.stop();
                    setBreakFlag(true);
                    
                    addImgBreak(breakPanel, 15);
                    shortBreakCount++;
                    setButtonVisibility(breakPanel, rest, true);
                    setButtonVisibility(breakPanel, restart, false);
                    timerLabel.setForeground(new Color(242, 122, 125));
                }

            }
        });

        breakTime = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.out.println("Break time");
                System.out.println("m:s " + (int) Math.floor(timeTrack / 1000 / 60) + ":" + (timeTrack / 1000 % 60));
                timeTrack -= 1000; // decrements the time by 1 second

                date = new Date(timeTrack);

                formattedTime = df.format(date);

                timerLabel.setText(formattedTime);
                timerLabel.setFont(digitalFontBold);
                timerLabel.setForeground(new Color(242, 122, 125));

                updateProgressBar();
                if(breakFlag && shortBreakCount == 4){
                    addImgBreak(breakPanel, 20);
                }
                if(timeTrack == 0){
                    breakTime.stop();
                    setBreakFlag(false);
                    setButtonVisibility(breakPanel, rest, false);
                    setButtonVisibility(breakPanel, restart, false);
                    setButtonVisibility(breakPanel, start, true);
                    timerLabel.setForeground(new Color(234, 215, 195));
                    System.out.println("COUNT: " + shortBreakCount);
                }
            }
        });
        timerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        timePanel.add(timerLabel, BorderLayout.NORTH);
        timePanel.add(bar, BorderLayout.SOUTH);
        timePanel.add(timerLabel, BorderLayout.NORTH);

        pomoPanel = new JPanel(new BorderLayout());
        pomoPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        pomoPanel.setLayout(new BorderLayout());

        // buttonPanel for button interaction
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(251, 246, 239));
        pomoPanel.setBackground(new Color(251, 246, 239));

        start = new JButton();
        /* JButton pause = new JButton(); */
        restart = new JButton();
        rest = new JButton();

        // start button
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.out.println("Start button pressed");
                timeTrack = timeSet;
                if (!time.isRunning()) {
                    time.start();
                barFull = timeTrack;
                bar.setValue((int) barFull);
                setButtonVisibility(buttonPanel, restart, true);
                setButtonVisibility(buttonPanel, start, false);
                }
            }
        });
        // break/rest button
        setButtonVisibility(buttonPanel, rest, false);
        rest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.out.println("Rest button pressed");
                if(breakFlag && shortBreakCount == 4) {
                    timeTrack = longBreakTime;
                    System.out.println("LONG BREAK");
                }
                else if (breakFlag && shortBreakCount < 4) {
                    timeTrack = shortBreakTime;
                }
                barFull = timeTrack;
                bar.setValue((int) barFull);
                breakTime.start();
                setButtonVisibility(buttonPanel, rest, false);
            }
        });

        /*
         * //pause button
         * setButtonVisibility(buttonPanel, pause, false);
         * pause.addActionListener(new ActionListener() {
         * 
         * @Override
         * public void actionPerformed(java.awt.event.ActionEvent e) {
         * System.out.println("Pause button pressed");
         * if(time.isRunning()){
         * time.stop();
         * }
         * }
         * });
         */

        // restart button
        setButtonVisibility(buttonPanel, restart, false);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.out.println("Restart button pressed");
                time.stop();
                timeTrack = timeSet;
                barFull = timeSet;
                bar.setValue((int) timeSet);
                date = new Date(timeTrack);
                formattedTime = df.format(date);
                timerLabel.setText(formattedTime);
                timerLabel.setFont(digitalFontBold);
                timerLabel.setForeground(new Color(234, 215, 195));
                bar.setString("RESTARTED");
                setButtonVisibility(buttonPanel, start, true);
                setButtonVisibility(buttonPanel, restart, false);
            }
        });

        int size = 30;
        restart.setPreferredSize(new Dimension(size, size));
        // pause.setPreferredSize(new Dimension(size, size));
        start.setPreferredSize(new Dimension(size, size));
        rest.setPreferredSize(new Dimension(size, size));
        addImg(start, "images/start.png", size);
        // addImg(pause, "images/pause.png", size);
        addImg(restart, "images/restart.png", size);
        addImg(rest, "images/rest.png", size);

        buttonPanel.add(start);
        // buttonPanel.add(pause);
        buttonPanel.add(restart);
        buttonPanel.add(rest);

        start.setBackground(new Color(234, 215, 195));
        // pause.setBackground(new Color(234, 215, 195));
        restart.setBackground(new Color(234, 215, 195));
        rest.setBackground(new Color(234, 215, 195));

        pomoPanel.add(breakPanel, BorderLayout.NORTH);
        pomoPanel.add(timePanel, BorderLayout.CENTER);
        pomoPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public JPanel getPomoPanel() {
        return pomoPanel;
    }

    public long currentTime() {
        return count.getTimeTrack();
    }

    public void setTimeSet(long ts) {
        timeSet = ts;
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
        StringBuilder sb = new StringBuilder();
        switch (shortBreakCount) {
            case 0:
                sb.setLength(0);
                sb = new StringBuilder("images/a.gif");
                break;
            case 1:
                sb.setLength(0);
                sb = new StringBuilder("images/b.png");
                break;
            case 2:
                sb.setLength(0);
                sb = new StringBuilder("images/c.png");
                break;
            case 3:
                sb.setLength(0);
                sb = new StringBuilder("images/d.png");
                break;
            case 4:
                setShortBreakCount(0);
                panel.removeAll();
                panel.revalidate();
                panel.repaint();
                return;
            default:
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

        if(barFull == 0) {
            bar.setString("FINISHED");
            return;
        }
        if(barFull % 2000 == 1000){
            bar.setString("");
            return;
        }
        if(breakFlag && shortBreakCount == 4){
            bar.setString("LONG BREAK");
        }
        else if (!breakFlag){
            bar.setString("FOCUS");
        }
        else if(breakFlag){
            bar.setString("SHORT BREAK");
        }
    }

    public void setButtonVisibility(JPanel panel, JButton b, boolean flag) {
        b.setEnabled(flag);
        b.setVisible(flag);
        panel.revalidate();
        panel.repaint();
    }

    public void setShortBreakCount(int count) {
        this.shortBreakCount = count;
    }

    public void setBreakFlag(boolean flag) {
        this.breakFlag = flag;
    }

    public static void main(String[] args) {
        new PomoFrame();
    }

}
