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
    private JProgressBar bar;
    private CountdownTimer count;
    private long timeSet = 300 * 1000;
    private long timeTrack;
    private long barFull;
    private long barIncrement;
    private Timer time;
    private Date date;
    private Font digitalFont;
    private Font digitalFontBold;
    private String formattedTime;






    public PomoPanel() {
        //use custom font for timer text
        InputStream is = getClass().getResourceAsStream("font/digital-7.ttf");
        try {
            digitalFont = Font.createFont(Font.TRUETYPE_FONT, is);

            digitalFontBold = digitalFont.deriveFont(Font.PLAIN, 50f);
        } 
        catch (Exception e) {
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
        bar.setStringPainted(false);
        bar.setMaximum((int)timeSet);
        barIncrement = 100/(timeTrack/1000);
        barFull = timeSet;
        bar.setValue((int) timeSet);
        
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
            

            if(timeTrack == 0) {
                time.stop();
            }
            }
        });
        timerLabel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        timePanel.add(timerLabel, BorderLayout.NORTH);
        timePanel.add(bar, BorderLayout.SOUTH);
        timePanel.add(timerLabel, BorderLayout.NORTH);

        pomoPanel = new JPanel(new BorderLayout());
        pomoPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        pomoPanel.setLayout(new BorderLayout());

        //buttonPanel for button interaction
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(251, 246, 239));
        pomoPanel.setBackground(new Color(251, 246, 239));

        JButton start = new JButton();
        JButton pause = new JButton();
        JButton restart = new JButton();
        int size = 30;
        restart.setPreferredSize(new Dimension(size, size));
        pause.setPreferredSize(new Dimension(size, size));
        start.setPreferredSize(new Dimension(size, size));
        addImg(start, "images/start.png", size);
        addImg(pause, "images/pause.png", size);
        addImg(restart, "images/restart.png", size);

        buttonPanel.add(start);
        buttonPanel.add(pause);
        buttonPanel.add(restart);

        start.setBackground(new Color(234, 215, 195));
        pause.setBackground(new Color(234, 215, 195));
        restart.setBackground(new Color(234, 215, 195));
        


        pomoPanel.add(timePanel, BorderLayout.NORTH);
        pomoPanel.add(buttonPanel, BorderLayout.CENTER);
        time.start();
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

    public void addImg(JButton button, String path, int size){
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

    public void updateProgressBar(){
        bar.setValue(((int)barFull - 1000));
        barFull = barFull - 1000;
    }

    public static void main(String[] args) {
        new PomoFrame();
    }

}
