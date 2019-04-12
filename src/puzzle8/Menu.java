/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle8;

import core.AudioManager;
import core.ImageManager;
import core.InputManager;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author Felipe Duarte
 */
public class Menu {

    private static final int TELA_PRINCIPAL = 0;
    private static final int ESCOLHA_DE_NIVEL = 1;
    public static final int ESTADO_FINAL = 2;

    private int estadoAtual;
    private int ySeta;
    private int opcaoEscolhida;

    private BufferedImage seta;
    private BufferedImage faixaFacil;
    private BufferedImage faixaMedio;
    private BufferedImage faixaDificil;

    private AudioClip moverSeta;

    public void load() throws IOException {
        seta = ImageManager.getInstance().loadImage("res/seta.png");
        faixaFacil = ImageManager.getInstance().loadImage(
                "res/niveis.png", 0, 0, 190, 45);
        faixaMedio = ImageManager.getInstance().loadImage(
                "res/niveis.png", 0, 45, 190, 45);
        faixaDificil = ImageManager.getInstance().loadImage(
                "res/niveis.png", 0, 90, 190, 45);
        setDefaultOpcaoEscolhida();
        setDefaultEstadoAtual();
        moverSeta = AudioManager.getInstance().loadAudio("res/moverSeta.wav");
    }

    public void update(int currentTick) {
        
        if (estadoAtual == TELA_PRINCIPAL) {

            if (InputManager.getInstance().isJustPressed(KeyEvent.VK_ENTER)) {
                moverSeta.play();
                estadoAtual = ESCOLHA_DE_NIVEL;
            }

        } else if (estadoAtual == ESCOLHA_DE_NIVEL) {

            if (InputManager.getInstance().isJustPressed(KeyEvent.VK_DOWN)) {
                if (ySeta < 408) {
                    ySeta += 100;
                    moverSeta.play();
                }
            }

            if (InputManager.getInstance().isJustPressed(KeyEvent.VK_UP)) {
                if (ySeta > 208) {
                    ySeta -= 100;
                    moverSeta.play();
                }
            }

            if (InputManager.getInstance().isJustPressed(KeyEvent.VK_ENTER)) {
                moverSeta.play();
                estadoAtual = ESTADO_FINAL;
                switch (ySeta) {
                    case 208:
                        opcaoEscolhida = 3;
                        break;
                    case 308:
                        opcaoEscolhida = 4;
                        break;
                    case 408:
                        opcaoEscolhida = 5;
                        break;
                }
            }
            
        }
    }

    public void render(Graphics2D g) {

        if (estadoAtual == TELA_PRINCIPAL) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Harlow Solid Italic", Font.PLAIN, 150));
            g.drawString("Puzzle 8", 120, 160);

            g.setFont(new Font("Arial", Font.PLAIN, 22));
            g.drawString("Começar", 345, 350);
            g.drawImage(seta, 300, 328, null);

            g.setFont(new Font("Arial", Font.ITALIC, 14));
            g.drawString(
                    "2018@-Todos os direitos reservados - Desenvolvido por "
                    + "Felipe Duarte", 158, 550);
        }

        if (estadoAtual == ESCOLHA_DE_NIVEL) {

            g.setFont(new Font("Arial", Font.PLAIN, 42));
            g.setColor(Color.WHITE);
            g.drawString("Escolha um nível", 230, 100);

            g.drawImage(faixaFacil, 300, 200, null);
            g.drawImage(faixaMedio, 300, 300, null);
            g.drawImage(faixaDificil, 300, 400, null);
            g.drawImage(seta, 240, ySeta, null);

            g.setFont(new Font("Arial", Font.PLAIN, 28));
            g.drawString("Fácil", 310, 230);
            g.drawString("Médio", 310, 330);
            g.drawString("Difícil", 310, 430);
        }

    }

    public int getOpcaoEscolhida() {
        return opcaoEscolhida;
    }

    public void setDefaultOpcaoEscolhida() {
        ySeta = 208;
    }

    public int getEstadoAtual() {
        return estadoAtual;
    }

    public void setDefaultEstadoAtual() {
        this.estadoAtual = TELA_PRINCIPAL;
    }

}
