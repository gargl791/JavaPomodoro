
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class CountdownTimer{

    private Timer timer; // Timer object
    private int period = 1000; // period for each execution (1 second = 1000 ms)
    private long timeSet; // time set by user
    private long timeTrack;

    public CountdownTimer(long timeSet) {
        this.timeSet = timeSet;
    }

    public CountdownTimer() {
    }

    public long getTimeSet() {
        return timeSet;     
    }

    public long getTimeTrack() {
        return timeTrack;
    }

    public void startCountdown() {
        timeTrack = timeSet;
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
            System.out.println("Timer started");
            System.out.println(timeTrack / 1000);
            System.out.println("m:s " + (int) Math.floor(timeTrack / 1000 / 60) + ":" + (timeTrack / 1000 % 60));
            timeTrack -= 1000; // decrements the time by 1 second
            timeSet = timeTrack;
            if (timeTrack == -1000) {
                endCountdown(); // ends the timer
            }
            }
        };
        timer = new Timer(1000, actionListener);
    }

    public void endCountdown() {
        timer.stop(); // cancels the timer
        System.out.println("Timer finished");
    }

}
