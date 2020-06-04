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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * JanelaDesenho básica de um jogo, sendo assim, ela já inicializa com bloqueio de
 * redimensionar pelo usuário.
 * ATENÇÃO: Recomenda-se não instanciar essa classe! Ao invés disso utilize a 
 * classe TelaJogo porque ela é uma junção entre PainelDesenho, JanelaDesenho e 
 * MaquinaEstados.
 * <br><br><small>Created on : 01/05/2015, 22:18:55</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class JanelaDesenho extends JFrame implements WindowListener, WindowFocusListener, WindowStateListener{
        
    private PainelDesenho painelDesenho;
    private boolean telaCheia;
    private int UPS;
    
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    /**
     * Construtor básico da classe.
     * @param titulo O título que irá aparecer na barra de títulos
     * @param comprimento O comprimento da janela
     * @param altura A altura da janela
     * @param telaCheia Define se a janela será em tela cheia ou não.
     * @param UPS O valor das atualizações por segundo (Update Per Second), ou
     * seja, quantas vezes o método desenha(Graphics2D g) será chamado. Atenção!
     * Um bom UPS para computadores lentos é 16, pois caso contrário o jogo
     * ficará mais lento em alguns computadores e em outros bem mais rápido.
     */
    public JanelaDesenho(
            String titulo, int comprimento, int altura, 
            boolean telaCheia, int UPS
    ){
        super(titulo);
        
        //Desativando todos os métodos de repaint para um melhor controle manual
        super.setIgnoreRepaint(true);
        super.getContentPane().setIgnoreRepaint(true);
        super.getLayeredPane().setIgnoreRepaint(true);
        
        painelDesenho = new PainelDesenho(comprimento, altura, UPS);       
        super.setContentPane(painelDesenho);
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        super.setAutoRequestFocus(true);
        super.setResizable(false);
        super.setDefaultLookAndFeelDecorated(true);
        
        ativarTelaCheia(telaCheia);
        
        if (telaCheia) {
            setDimensoes(ResolucaoTelaSO.getComprimento(), ResolucaoTelaSO.getAltura());
        }
        else{
            setDimensoes(comprimento, altura);
        }
        
        if (UPS > 0) {
            this.UPS = UPS;
        }
        
        super.addWindowListener(this);
        super.addWindowFocusListener(this);
        super.addWindowStateListener(this);
        
        moverParaCentroTela();
        super.pack();
    }
    
    /**
     * Construtor completo da classe
     * @param titulo O título que irá aparecer na barra de títulos
     * @param comprimento O comprimento da janela
     * @param altura A altura da janela
     * @param iconeJanela O ícone da janela
     * @param telaCheia Define se a janela será em tela cheia ou não.
     * @param UPS O valor das atualizações por segundo (Update Per Second), ou
     * seja, quantas vezes o método desenha(Graphics2D g) será chamado. Atenção!
     * Um bom UPS para computadores lentos é 16, pois caso contrário o jogo
     * ficará mais lento em alguns computadores e em outros bem mais rápido.
     */
    public JanelaDesenho(
            String titulo, int comprimento, int altura, 
            Image iconeJanela, boolean telaCheia, int UPS
    ){
        this(titulo, comprimento, altura, telaCheia, UPS);
        super.setIconImage(iconeJanela);
        super.pack();
    }
    
    /**
     * Construtor utilizado para criar um janela simples sem a decoração das bordas
     * e botões de uma janela padrão.
     * @param comprimento O comprimento da janela
     * @param altura A altura da janela
     * @param UPS O valor das atualizações por segundo (Update Per Second), ou
     * seja, quantas vezes o método desenha(Graphics2D g) será chamado. Atenção!
     * Um bom UPS para computadores lentos é 16, pois caso contrário o jogo
     * ficará mais lento em alguns computadores e em outros bem mais rápido.
     * @param decorarJanela Booleano que marca se a janela deve ser decorada ou não
     */
    public JanelaDesenho(int comprimento, int altura, int UPS, boolean decorarJanela){
        super();
        
        //este método pode ser executado apenas no construtor
        super.setUndecorated(decorarJanela);
        
        //Desativando todos os métodos de repaint para um melhor controle manual
        super.setIgnoreRepaint(true);
        super.getContentPane().setIgnoreRepaint(true);
        super.getLayeredPane().setIgnoreRepaint(true);
        
        painelDesenho = new PainelDesenho(comprimento, altura, UPS);       
        super.setContentPane(painelDesenho);
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        super.setAutoRequestFocus(true);
        super.setResizable(false);
        super.setDefaultLookAndFeelDecorated(true);
        
        setDimensoes(comprimento, altura);
        
        if (UPS > 0) {
            this.UPS = UPS;
        }
        
        super.addWindowListener(this);
        super.addWindowFocusListener(this);
        super.addWindowStateListener(this);
        
        moverParaCentroTela();
        super.pack();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: Auxiliares">
    /**
     * Caso o valor passado por parâmetro seja true, a tela cheia será ativada.
     * @param telaCheia Define se a tela cheia será ativada ou não
     */
    private void ativarTelaCheia(boolean telaCheia){
        this.telaCheia = telaCheia;
        
        if (telaCheia) {
            super.setExtendedState(Frame.MAXIMIZED_BOTH);
            
            //este método pode ser executado apenas no construtor
            super.setUndecorated(true);
        }
    }
    
    /**
     * Alterador das dimensões da Janea e do PainelDesenho
     * @param comprimento O valor do novo comprimento
     * @param altura O valor da nova altura
     */
    private void setDimensoes(int comprimento, int altura){
        if (comprimento > 0 && altura > 0) {
            super.setSize(comprimento, altura);
            super.pack();
        }
    }
    //</editor-fold>
        
    /**
     * Retorna se a JanelaDesenho está em tela cheia ou não.
     * @return boolean
     */
    public boolean isTelaCheia(){
        return telaCheia;
    }
    
    /**
     * Alinhador da JanelaDesenho ao centro exato da tela.
     */
    public void moverParaCentroTela(){
        super.setLocation(
                (ResolucaoTelaSO.getComprimento() / 2) - (super.getWidth() / 2), 
                (ResolucaoTelaSO.getAltura() / 2) - (super.getHeight() / 2)
        );
    }
    
    /**
     * Retorna o PainelDesenho utilizado pela JanelaDesenho para desenhar todos os objetos.
     * @return PainelDesenho
     */
    public PainelDesenho getPainelDesenho(){
        return painelDesenho;
    }  
    
    /**
     * Retorna o valor do UPS (Update Per Second) atual.
     * @return int
     */
    public int getUPS() {
        return UPS;
    }   
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Windowlistener Events">
    @Override
    public void windowActivated(WindowEvent we) {
        //Invoked when the Window is set to be the active Window.
    }
    
    @Override
    public void windowClosed(WindowEvent we) {
        //Invoked when a window has been closed as the result of calling dispose on the window.
        System.exit(0);
    }
    
    @Override
    public void windowClosing(WindowEvent we) {
        //Invoked when the user attempts to close the window from the window's system menu.
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        //Invoked when a Window is no longer the active Window.
    }
    
    @Override
    public void windowDeiconified(WindowEvent we) {
        //Invoked when a window is changed from a minimized to a normal state.
    }
    
    @Override
    public void windowIconified(WindowEvent we) {
        //Invoked when a window is changed from a normal to a minimized state.
    }
    
    @Override
    public void windowOpened(WindowEvent we) {
        //Invoked the first time a window is made visible.
    }
    
    @Override
    public void windowGainedFocus(WindowEvent we) {
    }

    @Override
    public void windowLostFocus(WindowEvent we) {
    }

    @Override
    public void windowStateChanged(WindowEvent we) {
    }
    //</editor-fold>
    
}
