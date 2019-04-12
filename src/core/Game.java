package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

/**
 *
 * @author Felipe Duarte
 */
public abstract class Game implements WindowListener{

    private JFrame mainWindow;
    private BufferStrategy bufferStrategy;
    private boolean active;
    
    private GameSpeedTracker speedTracker;
    private int expectedTPS;
    private double expectedNanoTimeTick;
    
    public Game(){
        mainWindow = new JFrame("Puzzle 8");
        mainWindow.setSize(800, 600);
        mainWindow.addWindowListener(this);
        mainWindow.addKeyListener(InputManager.getInstance());
        speedTracker = new GameSpeedTracker();
        active = false;
    }
            
    public void terminate(){
        active = false;
    }
    
    private void load(){
        mainWindow.setIgnoreRepaint(true);
        mainWindow.setUndecorated(true);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
        mainWindow.createBufferStrategy(2);
        bufferStrategy = mainWindow.getBufferStrategy();
        active = true;
        speedTracker.start();
        onLoad();
    }
    
    public void run(){
        
        load();
        
        expectedTPS = 20;
        expectedNanoTimeTick = GameSpeedTracker.NANOS_IN_ONE_SECOND/expectedTPS;
        long previousAtNextTick = System.nanoTime();
        int maxSkippedFrame = 10;
        int skippedFrame = 0;
        
        while(active){            
            speedTracker.update();
            
            if(System.nanoTime() > previousAtNextTick && 
            skippedFrame < maxSkippedFrame){
                
                previousAtNextTick += expectedNanoTimeTick;
                
                InputManager.getInstance().update();
                update();
                skippedFrame++;
                
            }else{
                render();
                skippedFrame = 0;
            }
            
        }
        
        unload();
        
    }
    
    private void update(){
        speedTracker.countedTick();
        onUpdate(speedTracker.getTotalTick());
    }
    
    private void render(){
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        
        g.setColor(new Color(63,124,182));
        g.fillRect(0, 0, getWidth(), getHeight());
        
        onRender(g);
        
        /*g.setColor(Color.WHITE);
        g.fillRect(0, 0, 100, 30);
        g.setColor(Color.BLACK);
        g.drawString("TPS: " + speedTracker.getTPS(), 20, 15);*/
        
        g.dispose();
        bufferStrategy.show();
    }
    
    private void unload(){
        onUnload();
        bufferStrategy.dispose();
        mainWindow.dispose();
    }
    
    public int getWidth(){
        return mainWindow.getWidth();
    }
    
    public int getHeight(){
        return mainWindow.getHeight();
    }
    
    public abstract void onLoad();
    public abstract void onUpdate(int currentTick);
    public abstract void onRender(Graphics2D g);
    public abstract void onUnload();
    
    
    @Override
    public void windowOpened(WindowEvent we) {}

    @Override
    public void windowClosing(WindowEvent we) {
        terminate();
    }

    @Override
    public void windowClosed(WindowEvent we) {}

    @Override
    public void windowIconified(WindowEvent we) {}

    @Override
    public void windowDeiconified(WindowEvent we) {}

    @Override
    public void windowActivated(WindowEvent we) {}

    @Override
    public void windowDeactivated(WindowEvent we) {}
    
}
