import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public PomoPanel() {
        PomoPanelDesign();
    }

    public void PomoPanelDesign() {
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new BorderLayout());
        timeTrack = timeSet;
        
        SimpleDateFormat df = new SimpleDateFormat("mm:ss");
        JLabel timerLabel = new JLabel(df.format(new Date(timeTrack)));
        time = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
            System.out.println(timeTrack / 1000);
            System.out.println("m:s " + (int) Math.floor(timeTrack / 1000 / 60) + ":" + (timeTrack / 1000 % 60));
            timeTrack -= 1000; // decrements the time by 1 second
            
            date = new Date(timeTrack);

            String formattedTime = df.format(date);
            

            timerLabel.setText(formattedTime);
            if(timeTrack == 0) {
                time.stop();
            }
            }
        });
        time.start();




        bar = new JProgressBar();
        bar.setValue(0);
        bar.setStringPainted(false);
        timePanel.add(timerLabel, BorderLayout.NORTH);
        timePanel.add(bar, BorderLayout.SOUTH);

        pomoPanel = new JPanel(new BorderLayout());
        pomoPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        pomoPanel.setLayout(new BorderLayout());

        JButton jb = new JButton("Button1");
        pomoPanel.add(timePanel, BorderLayout.NORTH);
        pomoPanel.add(jb, BorderLayout.CENTER);
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
