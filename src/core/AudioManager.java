package core;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

/**
 *
 * @author Felipe Duarte
 */
public class AudioManager {
    
    private static AudioManager instance;
    
    private HashMap<String,AudioClip> clips;
    
    private AudioManager(){
        clips = new HashMap<>();
    }
    
    public static AudioManager getInstance(){
        if(instance == null){
            instance = new AudioManager();
        }
        return instance;
    }
    
    public AudioClip loadAudio(String fileName) throws IOException{
        URL url = getClass().getResource("/" + fileName);
        
        if(url == null){
            throw new RuntimeException("O audio " + fileName + " não foi econtrado!");
        }else{
            
            if(clips.containsKey(fileName)){
                return clips.get(fileName);
            }else{
                AudioClip clip = Applet.newAudioClip(url);
                clips.put(fileName, clip);
                return clip;
            }
            
        }
            
    }
    
}