package it.unibo.grubclash.model.Implementation;

import java.io.File;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class Sound {
    
    static Clip clip;
    static File soundFile[] = new File[2];
    private static char FS = File.separatorChar;
    

    public static void setFile(int i){

        soundFile[0] = new File("src" + FS + "main" + FS + "resources" + FS + "sounds" + FS + "explosion.wav");
        soundFile[1] = new File("src" + FS + "main" + FS + "resources" + FS + "sounds" + FS + "dig.wav");
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
