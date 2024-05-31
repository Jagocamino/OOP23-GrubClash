package it.unibo.grubclash.model.Implementation;

import java.io.File;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class Sound {
    
    Clip clip;
    File soundFile[] = new File[5];
    private static char FS = File.separatorChar;

    public Sound(){

        soundFile[0] = new File("src" + FS + "main" + FS + "resources" + FS + "sounds" + FS + "explosion.wav");
        soundFile[1] = new File("src" + FS + "main" + FS + "resources" + FS + "sounds" + FS + "dig.wav");
    }

    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch(Exception e){
        }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
