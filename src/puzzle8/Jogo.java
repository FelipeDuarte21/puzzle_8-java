package puzzle8;

import core.Game;
import core.InputManager;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 *
 * @author Felipe Duarte
 */
public class Jogo extends Game{

    private static final int ESTADO_MENU = 0;
    private static final int ESTADO_JOGANDO = 1;
    
    private int estadoAtual;
    
    private Menu menu;
    private Puzzle puzzle;
    
    public Jogo(){
        menu = new Menu();
        estadoAtual = ESTADO_MENU;
    }
    
    @Override
    public void onLoad() {        
        try {
            menu.load();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void onUpdate(int currentTick) {
        if(InputManager.getInstance().isJustPressed(KeyEvent.VK_ESCAPE)){
            terminate();
        }
        
        if(estadoAtual == ESTADO_MENU){
            menu.update(currentTick);
            if(menu.getEstadoAtual() == Menu.ESTADO_FINAL){
                estadoAtual = ESTADO_JOGANDO;
                puzzle = new Puzzle(menu.getOpcaoEscolhida());
                try {
                    puzzle.load();
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        }
        
        if(estadoAtual == ESTADO_JOGANDO){
            puzzle.update(currentTick);
            if(puzzle.getEstadoAtual() == Puzzle.ESTADO_VOLTA){
                estadoAtual = ESTADO_MENU;
                menu.setDefaultEstadoAtual();
                menu.setDefaultOpcaoEscolhida();
            }
        }
        
    }

    @Override
    public void onRender(Graphics2D g) {
        
        if(estadoAtual == ESTADO_MENU){
            menu.render(g);
        }
        
        if(estadoAtual == ESTADO_JOGANDO){
            puzzle.render(g);
        }
    
    }

    @Override
    public void onUnload() {
    
    
    }
    
}
