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
import java.util.Random;

/**
 *
 * @author Felipe Duarte
 */
public class Puzzle {

    private static final int ESTADO_EMBARALHANDO = 0;
    private static final int ESTADO_JOGANDO = 1;
    private static final int ESTADO_VERIFICANDO = 2;
    private static final int ESTADO_FINAL = 3;
    public static final int ESTADO_VOLTA = 4;

    private int estadoAtual;

    private final int tamanho;
    private int movimentos;
    private final int[][] matrizJogo;
    private int linhaZero;
    private int colunaZero;

    private final BufferedImage[] matrizJogoImagem;
    private BufferedImage painel3;
    private BufferedImage painel4;
    private BufferedImage painel5;

    private final Random aleatorio;
    private Relogio tempo;
    
    private AudioClip moverPeca;

    public Puzzle(int tamanho) {
        this.tamanho = tamanho;
        movimentos = 0;
        matrizJogo = new int[this.tamanho][this.tamanho];
        matrizJogoImagem = new BufferedImage[24];
        aleatorio = new Random();
        tempo = new Relogio();
    }

    public void load() throws IOException {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                matrizJogoImagem[count] = ImageManager.getInstance()
                        .loadImage("res/peças.png", j * 100, i * 100, 100, 100);
                count++;
            }
        }

        painel3 = ImageManager.getInstance().loadImage("res/painel3.png");
        painel4 = ImageManager.getInstance().loadImage("res/painel4.png");
        painel5 = ImageManager.getInstance().loadImage("res/painel5.png");
        moverPeca = AudioManager.getInstance().loadAudio("res/moverPeças.wav");
        estadoAtual = ESTADO_EMBARALHANDO;
    }

    public void update(int currentTick) {
        if(InputManager.getInstance().isJustPressed(KeyEvent.VK_F1)){
            estadoAtual = ESTADO_VOLTA;
        }
        
        if (estadoAtual == ESTADO_EMBARALHANDO) {
            prepararMatriz();
        }

        if (estadoAtual == ESTADO_JOGANDO) {
            tempo.atualiza();
            movimentarMatriz();
        }

        if (estadoAtual == ESTADO_VERIFICANDO) {
            verificarMatriz();
        }

        
    }

    private void prepararMatriz() {

        //Coloca os numeros
        int count = 0;
        for (int i = 0; i < this.tamanho; i++) {
            for (int j = 0; j < this.tamanho; j++) {
                matrizJogo[i][j] = count;
                count++;
            }
        }

        //Emabaralha os numeros
        int linha, coluna, numero;
        for (int i = this.tamanho - 1; i >= 0; i--) {
            for (int j = this.tamanho - 1; j >= 0; j--) {
                linha = aleatorio.nextInt(i + 1);
                coluna = aleatorio.nextInt(j + 1);
                numero = matrizJogo[i][j];
                matrizJogo[i][j] = matrizJogo[linha][coluna];
                matrizJogo[linha][coluna] = numero;
                if (matrizJogo[i][j] == 0) {
                    linhaZero = i;
                    colunaZero = j;
                }
            }
        }

        estadoAtual = ESTADO_JOGANDO;

    }

    private void movimentarMatriz() {
        if (InputManager.getInstance().isJustPressed(KeyEvent.VK_UP)) {
            if (linhaZero + 1 < tamanho) {
                moverPeca.play();
                movimentos++;
                matrizJogo[linhaZero][colunaZero]
                        = matrizJogo[linhaZero + 1][colunaZero];
                linhaZero++;
                matrizJogo[linhaZero][colunaZero] = 0;
                estadoAtual = ESTADO_VERIFICANDO;
            }
        }else if (InputManager.getInstance().isJustPressed(KeyEvent.VK_RIGHT)) {
            if (colunaZero - 1 >= 0) {
                moverPeca.play();
                moverPeca.play();
                movimentos++;
                matrizJogo[linhaZero][colunaZero]
                        = matrizJogo[linhaZero][colunaZero - 1];
                colunaZero--;
                matrizJogo[linhaZero][colunaZero] = 0;
                estadoAtual = ESTADO_VERIFICANDO;
            }
        }else if (InputManager.getInstance().isJustPressed(KeyEvent.VK_DOWN)) {
            if (linhaZero - 1 >= 0) {
                moverPeca.play();
                movimentos++;
                matrizJogo[linhaZero][colunaZero]
                        = matrizJogo[linhaZero - 1][colunaZero];
                linhaZero--;
                matrizJogo[linhaZero][colunaZero] = 0;
                estadoAtual = ESTADO_VERIFICANDO;
            }
        }else if (InputManager.getInstance().isJustPressed(KeyEvent.VK_LEFT)) {
            if (colunaZero + 1 < tamanho) {
                moverPeca.play();
                movimentos++;
                matrizJogo[linhaZero][colunaZero]
                        = matrizJogo[linhaZero][colunaZero + 1];
                colunaZero++;
                matrizJogo[linhaZero][colunaZero] = 0;
                estadoAtual = ESTADO_VERIFICANDO;
            }
        }

    }

    private void verificarMatriz() {
        int count = 0, numero = 0;
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                numero++;
                if (matrizJogo[i][j] == numero) {
                    count++;
                }
            }
        }

        if (count == tamanho * tamanho - 1) {
            estadoAtual = ESTADO_FINAL;
        } else {
            estadoAtual = ESTADO_JOGANDO;
        }
    }

    public void render(Graphics2D g) {

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.setColor(Color.WHITE);
        g.drawString("Movimentos: " + movimentos, 20, 30);
        g.drawString("Puzzle 8", 360, 30);
        tempo.render(g);
        g.drawString("ESC - Sair", 20, 580);
        g.drawString("F1 - Voltar ao Início", 320, 580);
        g.drawString("Mover: Use as Setas", 600, 580);

        if (estadoAtual == ESTADO_JOGANDO || estadoAtual == ESTADO_VERIFICANDO) {
            int posX = 0, posY = 0;
            switch (tamanho) {
                case 3:
                    posX = 250;
                    posY = 150;
                    g.drawImage(painel3, posX, posY, null);
                    break;
                case 4:
                    posX = 200;
                    posY = 100;
                    g.drawImage(painel4, posX, posY, null);
                    break;
                case 5:
                    posX = 150;
                    posY = 50;
                    g.drawImage(painel5, posX, posY, null);
                    break;
            }

            for (int i = 0; i < this.tamanho; i++) {
                for (int j = 0; j < this.tamanho; j++) {
                    if (matrizJogo[i][j] != 0) {
                        g.drawImage(matrizJogoImagem[matrizJogo[i][j] - 1],
                                posX + j * 100, posY + i * 100, null);
                    }
                }
            }

        }

        if (estadoAtual == ESTADO_FINAL) {
            g.setFont(new Font("Arial", Font.PLAIN, 36));
            g.setColor(Color.yellow);
            g.drawString("Parabéns Você Resolveu o Puzzle!", 132, 282);
        }

    }
    
    public int getEstadoAtual(){
        return estadoAtual;
    }

}
