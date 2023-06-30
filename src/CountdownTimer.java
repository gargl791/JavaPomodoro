
import java.util.Timer;
import java.util.TimerTask;

public class CountdownTimer {

    private Timer timer;
    private TimerTask task;
    private long delay = 3000; //delay before timer starts
    private long period = 1000; //period for each TimerTask run() execution (1 second = 1000 ms)
    private long timeSet; //time set by user

    public CountdownTimer(long timeSet){
        this.timeSet= timeSet;
    }

    public void startCountdown(){
        timer = new Timer(); //new Timer object

        //creates a new TimerTask object
        task = new TimerTask(){
            long timeTrack = timeSet;
        public void run(){
            System.out.println(timeTrack/1000);
            timeTrack -= 1000; //decrements the time by 1 second
            if(timeTrack == 0){
                endCountdown(); //ends the timer
                
            }
            
        }
        };

        timer.schedule(task, delay, period); //schedules the TimerTask to run after the delay and repeatedly after the period
    }

    public void endCountdown(){
        timer.cancel(); //cancels the timer
        System.out.println("Timer finished");
    }
    
}
