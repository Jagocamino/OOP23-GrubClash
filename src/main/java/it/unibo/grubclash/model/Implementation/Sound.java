package it.unibo.grubclash.model.Implementation;

import java.io.File;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class Sound {
    
    private static Clip clip;
    private static File soundFile[] = new File[7];
    private static char FS = File.separatorChar;
    

    public static void setFile(int i){

        soundFile[0] = new File("src" + FS + "main" + FS + "resources" + FS + "sounds" + FS + "explosion.wav");
        soundFile[1] = new File("src" + FS + "main" + FS + "resources" + FS + "sounds" + FS + "dig.wav");
        soundFile[2] = new File("src" + FS + "main" + FS + "resources" + FS + "sounds" + FS + "reload.wav");
        soundFile[3] = new File("src" + FS + "main" + FS + "resources" + FS + "sounds" + FS + "levelup.wav");
        soundFile[4] = new File("src" + FS + "main" + FS + "resources" + FS + "sounds" + FS + "jump.wav");
        soundFile[5] = new File("src" + FS + "main" + FS + "resources" + FS + "sounds" + FS + "shoot.wav");
        soundFile[6] = new File("src" + FS + "main" + FS + "resources" + FS + "sounds" + FS + "victory.wav");
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch(Exception e){
        }
    }
    public static void play(){
        clip.start();
    }
}
