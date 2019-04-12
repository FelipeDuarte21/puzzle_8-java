package puzzle8;

import static core.GameSpeedTracker.NANOS_IN_ONE_SECOND;
import java.awt.Graphics2D;

/**
 *
 * @author Felipe Duarte
 */
public class Relogio {
    
    private int segundos;
    private int minutos;
    private int horas;
    
    private long previousNanoTime;
    
    public Relogio(){
        this.segundos = 0;
        this.minutos = 0;
        this.segundos = 0;
        previousNanoTime = System.nanoTime();
    }
    
    public void atualiza(){
        if(System.nanoTime() - previousNanoTime > NANOS_IN_ONE_SECOND){
            previousNanoTime = System.nanoTime();
            segundos++;
            if(segundos == 60){
                segundos = 0;
                minutos++;
                if(minutos == 60){
                    minutos = 0;
                    horas++;
                }
            }
        }        
    }
    
    public void render(Graphics2D g){
        g.drawString("Tempo: " + horas + ":" + minutos + ":" + segundos,660,30);
    }
    
}
