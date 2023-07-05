import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
    private static File alarmDirFile;
    private static File windUpDirFile;

    public static void playAudio(boolean flag)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // if flag is true, play alarm, else play windup
        AudioInputStream audioStream;
        if (flag) {
            audioStream = AudioSystem.getAudioInputStream(alarmDirFile);
        } else {
            audioStream = AudioSystem.getAudioInputStream(windUpDirFile);
        }
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();

    }

    public static void saveAudio(File file, boolean flag) {
        // if flag is true, save to alarmDirFile, else save to windUpDirFile
        FileWriter writer;
        if (file == null)
            return;
        try {
            if (flag) {
                writer = new FileWriter("bin/data/alarmDir.txt");
                writer.write(file.getAbsolutePath());
                writer.close();
                alarmDirFile = file;
                System.out.println(alarmDirFile);

            } else {
                writer = new FileWriter("bin/data/windUpDir.txt");
                
                writer.write(file.getAbsolutePath());
                writer.close();
                windUpDirFile = file;
                System.out.println(windUpDirFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkAudio() {
        File alarmPath = new File("bin/data/alarmDir.txt");
        File windUpPath = new File("bin/data/windUpDir.txt");
        Scanner scanner;
        try {
            if (!alarmPath.exists()) {
                alarmPath = new File("sound/alarm.wav");
                saveAudio(alarmPath, true);
            } else {
                scanner = new Scanner(alarmPath);
                while (scanner.hasNextLine()) {
                    alarmPath = new File(scanner.nextLine());
                }
                System.out.println(alarmPath);
                saveAudio(alarmPath, true);
            }
            if (!windUpPath.exists()) {
                windUpPath = new File("sound/windup.wav");
                saveAudio(windUpPath, false);
            } else {
                scanner = new Scanner(windUpPath);
                while (scanner.hasNextLine()) {
                    windUpPath = new File(scanner.nextLine());
                }
                System.out.println(windUpPath);
                saveAudio(windUpPath, false);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static File getAlarmDirFile() {
        return alarmDirFile;
    }

    public static File getWindUpDirFile() {
        return windUpDirFile;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String response = "";

        while (!response.equals("Q")) {
            System.out.println("P,  S. Q");
            System.out.println("Enter choice:");
            response = scanner.next();
            response = response.toUpperCase();
            if (response.equals("P")) {
                try {
                    playAudio(true);
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (response.equals("S")) {
                try {
                    playAudio(false);
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
}
