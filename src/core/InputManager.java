package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Felipe Duarte
 */
public class InputManager implements KeyListener{
    
    private static final int KEY_PRESSED = 0;
    private static final int KEY_JUST_PRESSED = 1;
    private static final int KEY_RELEASED = 2;
    
    private ArrayList<Integer> pressedKeys;
    private ArrayList<Integer> releasedKeys;
    private HashMap<Integer,Integer> keyCache;
    
    private static InputManager instance;
    
    private InputManager(){
        pressedKeys = new ArrayList<>();
        releasedKeys = new ArrayList<>();
        keyCache = new HashMap<>();
    }
    
    public static InputManager getInstance(){
        if(instance == null){
            instance = new InputManager();
        }
        return instance;
    }
    
    public boolean isPressed(int keyCode){
        return keyCache.containsKey(keyCode) && 
                !keyCache.get(keyCode).equals(KEY_RELEASED);
    }
    
    public boolean isJustPressed(int keyCode){
        return keyCache.containsKey(keyCode) && 
                keyCache.get(keyCode).equals(KEY_JUST_PRESSED);
    }
    
    public boolean isReleased(int keyCode){
        return !keyCache.containsKey(keyCode) || 
                keyCache.get(keyCode).equals(KEY_RELEASED);
    }
    
    public void update(){
        
        for(Integer keyCode: keyCache.keySet()){
            if(isJustPressed(keyCode)){
                keyCache.put(keyCode, KEY_PRESSED);
            }
        }
        
        for(Integer keyCode: releasedKeys){
            keyCache.put(keyCode, KEY_RELEASED);
        }
        
        for(Integer keyCode: pressedKeys){
            if(isReleased(keyCode)){
                keyCache.put(keyCode, KEY_JUST_PRESSED);
            }else{
                keyCache.put(keyCode, KEY_PRESSED);
            }
        }
                
        pressedKeys.clear();
        releasedKeys.clear();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        pressedKeys.add(ke.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        releasedKeys.add(ke.getKeyCode());
    }
    
    
}
