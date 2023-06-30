public class PomodoroApp {
    
    public static void main(String[] args) {
        CountdownTimer count = new CountdownTimer(10000);
        count.startCountdown();
    }
}
