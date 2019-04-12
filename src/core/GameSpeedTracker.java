package core;

/**
 *
 * @author Felipe Duarte
 */
public class GameSpeedTracker {
    
    public static double NANOS_IN_ONE_SECOND = 1e9;
    private int tickPerSecond;
    private int totalTick;
    private int countTick;
    private long previousNanoTime;
    
    public void start(){
        previousNanoTime = System.nanoTime();
        tickPerSecond = 0;
        totalTick = 0;
        countTick = 0;        
    }
    
    public void countedTick(){
        countTick++;
        totalTick++;
        update();
    }
    
    public void update(){
        if(System.nanoTime() - previousNanoTime > NANOS_IN_ONE_SECOND){
            previousNanoTime = System.nanoTime();
            tickPerSecond = countTick;
            countTick = 0;
        }
    }
    
    public int getTPS(){
        return tickPerSecond;
    }
    
    public int getTotalTick(){
        return totalTick;
    }
}
