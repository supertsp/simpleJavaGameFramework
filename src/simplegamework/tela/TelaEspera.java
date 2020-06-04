//<editor-fold defaultstate="collapsed" desc="License">
/* 
 * Copyright (C) 2015 Tiago Penha Pedroso
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
//</editor-fold>

package simplegamework.tela;

import simplegamework.animacao.AnimacaoTile;
import simplegamework.imagem.ImagemBitmap;
import simplegamework.texto.Texto;
import java.awt.EventQueue;
import java.awt.geom.Rectangle2D;

/**
 * Cria uma tela que será utilizada enquanto uma ação estiver sendo processada.
 * Para atingir esse resultado basta criar um objeto TelaEspera antes do processamento
 * e depois chamar fechar() para fechar a tela.
 * <br><br><small>Created on : 13/08/2015, 14:15:32</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class TelaEspera{
    
    protected Texto mensagem;
    protected ImagemBitmap imagem;
    protected AnimacaoTile animacao;
    protected JanelaDesenho janela;
    
    //Auxiliares
    private final int aumento = 20;
    private boolean primeiraExecucao = true;
    
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    /**
     * Construtor para adicionar apenas uma mensagem à tela.
     * @param comprimento O comprimento da tela
     * @param altura A altura da tela
     * @param mensagem A mensagem a ser exibida
     */
    public TelaEspera(int comprimento, int altura, Texto mensagem){
        //Inicialização da Janela
        janela = new JanelaDesenho(comprimento + aumento, altura + aumento, 18, true);
        janela.setShape(new Rectangle2D.Double(aumento - 5, aumento * 2 - 2, comprimento, altura));
        
        //Inicialização do texto
        this.mensagem = mensagem;
        this.mensagem.incrementarCoordenadas(aumento / 2, aumento / 2);
        
        if (this.mensagem != null) {
            janela.getPainelDesenho().addDesenhavel(this.mensagem);
        }
    }
    
    /**
     * Construtor para adicionar apenas uma imagem à tela.
     * @param comprimento O comprimento da tela
     * @param altura A altura da tela
     * @param imagem A imagem a ser exibida
     */
    public TelaEspera(int comprimento, int altura, ImagemBitmap imagem){        
        //Inicialização da Janela
        janela = new JanelaDesenho(comprimento + aumento, altura + aumento, 18, true);
        janela.setShape(new Rectangle2D.Double(aumento - 5, aumento * 2 - 2, comprimento, altura));
        
        //Inicialização da imagem
        this.imagem = imagem;
        this.imagem.incrementarCoordenadas(aumento / 2, aumento / 2);
        
        if (this.imagem != null) {
            janela.getPainelDesenho().addDesenhavel(this.imagem);
        }
    }
    
    /**
     * Construtor para adicionar uma mensagem e uma imagem à tela.
     * @param comprimento O comprimento da tela
     * @param altura A altura da tela
     * @param mensagem A mensagem a ser exibida
     * @param imagem A imagem a ser exibida
     */
    public TelaEspera(int comprimento, int altura, Texto mensagem, ImagemBitmap imagem){
        //Inicialização da Janela
        janela = new JanelaDesenho(comprimento + aumento, altura + aumento, 18, true);
        janela.setShape(new Rectangle2D.Double(aumento - 5, aumento * 2 - 2, comprimento, altura));
        
        //Inicialização da imagem
        this.imagem = imagem;
        this.imagem.incrementarCoordenadas(aumento / 2, aumento / 2);
        
        if (this.imagem != null) {
            janela.getPainelDesenho().addDesenhavel(this.imagem);
        }
        
        //Inicialização do texto
        this.mensagem = mensagem;
        this.mensagem.incrementarCoordenadas(aumento, aumento);
        
        if (this.mensagem != null) {
            janela.getPainelDesenho().addDesenhavel(this.mensagem);
        }
    }
    
    /**
     * Construtor para adicionar uma animação à tela.
     * @param comprimento O comprimento da tela
     * @param altura A altura da tela
     * @param animacao A animação a ser exibida
     */
    public TelaEspera(int comprimento, int altura, AnimacaoTile animacao){        
        //Inicialização da Janela
        janela = new JanelaDesenho(comprimento + aumento, altura + aumento, 18, true);
        janela.setShape(new Rectangle2D.Double(aumento - 5, aumento * 2 - 2, comprimento, altura));
        
        //Inicialização da animação
        this.animacao = animacao;
        this.animacao.incrementarCoordenadas(aumento / 2, aumento / 2);
        
        if (this.animacao != null) {
            janela.getPainelDesenho().addDesenhavel(this.animacao);
        }
    }
    
    /**
     * Construtor para adicionar uma mensagem e uma animação à tela.
     * @param comprimento O comprimento da tela
     * @param altura A altura da tela
     * @param mensagem A mensagem a ser exibida
     * @param animacao A animação a ser exibida
     */
    public TelaEspera(int comprimento, int altura, Texto mensagem, AnimacaoTile animacao){        
        //Inicialização da Janela
        janela = new JanelaDesenho(comprimento + aumento, altura + aumento, 18, true);
        janela.setShape(new Rectangle2D.Double(aumento - 5, aumento * 2 - 2, comprimento, altura));
        
        //Inicialização da animação
        this.animacao = animacao;
        this.animacao.incrementarCoordenadas(aumento / 2, aumento / 2);
        
        if (this.animacao != null) {
            janela.getPainelDesenho().addDesenhavel(this.animacao);
        }

        //Inicialização do texto
        this.mensagem = mensagem;
        this.mensagem.incrementarCoordenadas(aumento / 2, aumento / 2);
        
        if (this.mensagem != null) {
            janela.getPainelDesenho().addDesenhavel(this.mensagem);
        }
    }    
    //</editor-fold>
        
    /**
     * Na primeira vez que é chamado inicializa a TelaJogo dentro de uma Thread 
     * específica e a torna visível, nas outras vezes apenas atribui true para
     * setVisible() da JanelaDesenho.
     */
    public void exibir(){
        if (primeiraExecucao) {
            EventQueue.invokeLater(() -> {
                janela.setVisible(true);
            });
            primeiraExecucao = false;
        }
        else{
            janela.setVisible(true);
        }
    }
    
    /**
     * Apenas oculta a TelaJogo passando false para setVisible() da JanelaDesenho.
     */
    public void fechar(){
        janela.setVisible(false);
    }
    
}
