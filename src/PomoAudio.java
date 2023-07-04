import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PomoAudio {
    
    private int alarmVolume;
    private int windUpVolume;
    
    public static void playAudio(File file) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
        
    }

    public static void main(String[]args) {
        Scanner scanner = new Scanner(System.in);
        String response = "";

        while(!response.equals("Q")) {
            System.out.println("P,  S. Q");
            System.out.println("Enter choice:");
            response = scanner.next();
            response = response.toUpperCase();
            if(response.equals("P")) {
        try {
            playAudio(new File("sound/bowomp.wav"));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            }
        }

    }
}
