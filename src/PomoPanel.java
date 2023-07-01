import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;



public class PomoPanel {

    private JPanel pomoPanel;
    private JLabel lblTimer;
    private JProgressBar bar;
    private CountdownTimer count;
    private long timeSet = 15 * 1000;
    private long timeTrack;
    private Timer time;
    private Date date;
    private Font digitalFont;
    private Font digitalFontBold;
    private String formattedTime;






    public PomoPanel() {
        //use custom font for timer text
        InputStream is = getClass().getResourceAsStream("/font/digital-7.ttf");
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
        timePanel.setBackground(new Color(141, 153, 174));
        timePanel.setLayout(new BorderLayout());
        timeTrack = timeSet;
        
        SimpleDateFormat df = new SimpleDateFormat("mm:ss");
        date = new Date(timeTrack);
        formattedTime = df.format(date);

        JLabel timerLabel = new JLabel(formattedTime, SwingConstants.CENTER);
        timerLabel.setText(formattedTime);
        timerLabel.setFont(digitalFontBold);
        timerLabel.setForeground(new Color(237, 242, 244));
        
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
            timerLabel.setForeground(new Color(237, 242, 244));
            

            if(timeTrack == 0) {
                time.stop();
            }
            }
        });

        bar = new JProgressBar();
        bar.setValue(0);
        bar.setStringPainted(false);
        timerLabel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        timePanel.add(timerLabel, BorderLayout.NORTH);
        timePanel.add(bar, BorderLayout.SOUTH);
        timePanel.add(timerLabel, BorderLayout.NORTH);

        pomoPanel = new JPanel(new BorderLayout());
        pomoPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        pomoPanel.setLayout(new BorderLayout());

        JButton jb = new JButton("Button1");
        pomoPanel.add(timePanel, BorderLayout.NORTH);
        pomoPanel.add(jb, BorderLayout.CENTER);
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

    public static void main(String[] args) {
        new PomoFrame();
    }
}
