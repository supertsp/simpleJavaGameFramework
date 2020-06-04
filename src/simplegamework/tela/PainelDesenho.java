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

import simplegamework.ferramenta.AtualizavelList;
import simplegamework.ferramenta.MedidaTempo;
import simplegamework.ferramenta.DesenhavelList;
import simplegamework.ferramenta.TempoExecucao;
import simplegamework.padraoProjeto.Atualizavel;
import simplegamework.padraoProjeto.AtualizavelListManuseavel;
import simplegamework.padraoProjeto.DesenhavelListManuseavel;
import simplegamework.padraoProjeto.Desenhavel;
import simplegamework.listener.MouseMoved;
import simplegamework.listener.MouseExited;
import simplegamework.listener.MouseWheelMoved;
import simplegamework.listener.ListenerListManuseavel;
import simplegamework.listener.MousePressed;
import simplegamework.listener.KeyReleased;
import simplegamework.listener.MouseReleased;
import simplegamework.listener.MouseClicked;
import simplegamework.listener.MouseDragged;
import simplegamework.listener.KeyPressed;
import simplegamework.listener.KeyTyped;
import simplegamework.listener.MouseEntered;
import simplegamework.objetoBasico.CaixaColisao;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Painel básico para desenhar objetos Desenháveis e para ser aclopado a uma
 * janela (JFrame).<br>
 * ATENÇÃO: Recomenda-se não instanciar essa classe! Ao invés disso utilize a 
 * classe TelaJogo porque ela é uma junção entre PainelDesenho, JanelaDesenho e 
 * MaquinaEstados.
 * <br><br><small>Created on : 01/05/2015, 22:18:55</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class PainelDesenho extends JPanel implements Desenhavel, 
        DesenhavelListManuseavel, Atualizavel, AtualizavelListManuseavel, 
        Runnable, KeyListener, MouseListener, MouseMotionListener, 
        MouseWheelListener, ListenerListManuseavel
{
    
    private int UPS;    
    private CaixaColisao caixaColisao;    
    private DesenhavelList desenhaveis;
    private AtualizavelList atualizaveis;
    
    //Auxiliares para o novo repaint()
    private TempoExecucao tempoExecucao;
    private boolean debugTempoExecucaoAtivo;
    private Thread threadDesenha;
    private Graphics2D bufferDeGraphics2D;
    private BufferedImage bufferDeImagensParaDesenhar;
    private volatile boolean rodando;
    
    //Auxiliares para ListenerList
    private ArrayList<KeyPressed> keyPressedList;
    private ArrayList<KeyReleased> keyReleasedList;
    private ArrayList<KeyTyped> keyTypedList;
    private ArrayList<MouseClicked> mouseClickedList;
    private ArrayList<MouseDragged> mouseDraggedList;
    private ArrayList<MouseEntered> mouseEnteredList;
    private ArrayList<MouseExited> mouseExitedList;
    private ArrayList<MouseMoved> mouseMovedList;
    private ArrayList<MousePressed> mousePressedList;
    private ArrayList<MouseReleased> mouseReleasedList;
    private ArrayList<MouseWheelMoved> mouseWheelMovedList;
    
    //<editor-fold defaultstate="collapsed" desc="Construtor">
    /**
     * Construtor padrão de classe.
     * @param comprimento O comprimento da janela
     * @param altura A altura da janela
     * @param UPS O valor das atualizações por segundo (Update Per Second), ou
     * seja, quantas vezes o método desenha(Graphics2D g) será chamado. Atenção!
     * Um bom UPS para computadores lentos é 16, pois caso contrário o jogo
     * ficará mais lento em alguns computadores e em outros bem mais rápido.
     */
    public PainelDesenho(int comprimento, int altura, int UPS) {
        super();
        super.setDoubleBuffered(true);
        caixaColisao = new CaixaColisao(0, 0, comprimento, altura);
        setDimensoes(comprimento, altura);
        desenhaveis = new DesenhavelList();
        atualizaveis = new AtualizavelList();
        this.UPS = UPS > 0 ? UPS : 1;
        super.setBackground(Color.WHITE);
        super.setFocusable(true);
        super.requestFocus();
        
        tempoExecucao = new TempoExecucao(1000 / UPS, MedidaTempo.MILISSEGUNDOS);
        
        initThreadRepaint();
        
        //Adicionando Listeners
        super.addKeyListener(this);
        super.addMouseListener(this);
        super.addMouseMotionListener(this);
        super.addMouseWheelListener(this);
        
        //Auxiliares para ListenerList
        keyPressedList = new ArrayList<>();
        keyReleasedList = new ArrayList<>();
        keyTypedList = new ArrayList<>();
        mouseClickedList = new ArrayList<>();
        mouseDraggedList = new ArrayList<>();
        mouseEnteredList = new ArrayList<>();
        mouseExitedList = new ArrayList<>();
        mouseMovedList = new ArrayList<>();
        mousePressedList = new ArrayList<>();
        mouseReleasedList = new ArrayList<>();
        mouseWheelMovedList = new ArrayList<>();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: Auxiliares">
    /**
     * Altera as dimensões do PainelDesenho.
     * @param comprimento O valor do novo comprimento
     * @param altura O valor da nova altura
     */
    private void setDimensoes(int comprimento, int altura) {
        super.setSize(new Dimension(comprimento, altura));
        super.setPreferredSize(new Dimension(comprimento, altura));
    }
    
    /**
     * Paralisa o método repaint() original e inicia um novo personalizado.
     */
    private void initThreadRepaint(){
        super.setIgnoreRepaint(true);
        try {
            bufferDeGraphics2D = (Graphics2D)this.getGraphics();
            SwingUtilities.invokeAndWait(() -> {
                paintComponents(bufferDeGraphics2D);
            });     
        } catch (Exception e) {
            System.out.println("ERRO AO RETARDAR O REPAINT() NO CONSTRUTOR DE "
                    + "PAINELDESENHO");
        }
    }
    
    /**
     * Inicializa a Thread que será a responsável por desenhar no painel.
     */
    private void initThreadDesenha() {
        if (!rodando || threadDesenha == null) {
            threadDesenha = new Thread(this);
            threadDesenha.start();
        }
    }
    
    /**
     * Inicializa o laço de repetição do jogo, ou seja, o novo repaint().
     */
    private void initGameLoop(){
        while (rodando) {
            tempoExecucao.iniciarTempo();
            
            //início: novo repaint()
            renderizaParaBufferedImage();
            limpaTela();
            desenha(bufferDeGraphics2D);
            desenhaDoBufferDeImagens();
            //fim: novo repaint()
            
            atualiza();
            
            //Sempre deve haver uma pausa mínima na Thread
            pausarRepaint(5);
            
            //Caso a meta não tenha sido atingida, pausa mais um pouco
            if (!tempoExecucao.atingiuMeta()) {
                pausarRepaint((int)tempoExecucao.getTempoRestanteParaMeta());
            }
            
            //<editor-fold defaultstate="collapsed" desc="debugTempoExecucao">
            if (debugTempoExecucaoAtivo) {
                int tempoDecorrido = (int)tempoExecucao.getTempoDecorrido();
                System.out.println(
                        "tempoExecucao: " + tempoDecorrido + "ms;" +
                        " metaDeTempo: " + tempoExecucao.getMeta() + "ms;" +
                        " UPS Pedido: " + UPS + ";" +
                        " UPS Medido: " + (tempoDecorrido == 0 ? "?" : 1000 / tempoDecorrido)
                );
            }
            //</editor-fold>
        }
    }
    
    /**
     * Pega todos objetos para serem desenhados e atribui para uma BufferedImage
     */
    private void renderizaParaBufferedImage() {
        if (bufferDeImagensParaDesenhar == null) {
            //Creates an off-screen drawable image to be used for double buffering.
            bufferDeImagensParaDesenhar = (BufferedImage) super.createImage(
                    super.getWidth(),
                    super.getHeight()
            );

            if (bufferDeImagensParaDesenhar == null) {
                System.out.println("imagemParaPintar == null!!! :(");
                return;
            } else {
                bufferDeGraphics2D = (Graphics2D) bufferDeImagensParaDesenhar.getGraphics();
            }
        }
    }
    
    /**
     * Limpa a tela desenhando um tela branca.
     */
    private void limpaTela(){
        bufferDeGraphics2D.setColor(Color.WHITE);
        bufferDeGraphics2D.fillRect(0, 0, super.getWidth(), super.getHeight());
    }
    
    /**
     * Desenha tudo o que estiver no buffer de imagens.
     */
    private void desenhaDoBufferDeImagens() {      
        Graphics2D g;
        try {
            //Obtem-se o  Graphics atual do JPanel
            g = (Graphics2D)this.getGraphics();
            if ((g != null) && (bufferDeImagensParaDesenhar != null)) {
                g.drawImage(bufferDeImagensParaDesenhar, 0, 0, null);
            }
            //Sincroniza o display em alguns sistemas, com por exemplo: Gnu/Linux
            Toolkit.getDefaultToolkit().sync(); 
            g.dispose();
        } catch (Exception e) {
            System.out.println("Erro ao desenha na tela: " + e);
        }
    }
    
    /**
     * Efetua uma pausa em todo sistema
     * @param tempoEmMilisegundos O valor da intermitência de pausa
     */
    private void pausarRepaint(int tempoEmMilisegundos){
        tempoEmMilisegundos = tempoEmMilisegundos <= 0 ? 5 : tempoEmMilisegundos;
        try {
            Thread.sleep(tempoEmMilisegundos);
        } catch (Exception e) {}
    }
    //</editor-fold>
    
    /**
     * Ativa o debug via terminal que demonstra a capacidade de UPS atingida pela
     * máquina.
     */
    public void ativarDebugTempoExecucao(){
        debugTempoExecucaoAtivo = true;
    }
    
    /**
     * Desativa o debug de UPS.
     */
    public void desativarDebugTempoExecucao(){
        debugTempoExecucaoAtivo = false;
    }
    
    /**
     * Retorna o status do debug de UPS.
     * @return boolean
     */
    public boolean isDebugTempoExecucaoAtivo(){
        return debugTempoExecucaoAtivo;
    }
    
    /**
     * Retorna a CaixaColisao que define os limites da tela.
     * @return CaixaColisao
     */
    public CaixaColisao getCaixaColisao(){
        return caixaColisao;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: JComponent">
    /**
     * Makes this Container displayable by connecting it to a native screen 
     * resource. Making a container displayable will cause all of its children 
     * to be made displayable. This method is called internally by the toolkit 
     * and should not be called directly by programs.
     */
    @Override
    public void addNotify() {
        super.addNotify();
        initThreadDesenha();
    }
    
    /**
     * Calls the UI delegate's paint method, if the UI delegate is non-null. 
     * We pass the delegate a copy of the Graphics object to protect the rest 
     * of the paint code from irrevocable changes (for example, 
     * Graphics.translate).<br>
     * If you override this in a subclass you should not make permanent changes 
     * to the passed in Graphics. For example, you should not alter the clip 
     * Rectangle or modify the transform. If you need to do these operations 
     * you may find it easier to create a new Graphics from the passed in 
     * Graphics and manipulate it. Further, if you do not invoker super's 
     * implementation you must honor the opaque property, that is if this 
     * component is opaque, you must completely fill in the background in a 
     * non-opaque color. If you do not honor the opaque property you will 
     * likely see visual artifacts.<br>
 The passed in Graphics object might have a transform other than the 
 identify transform installed on it. In this case, you might getMouseReleased 
 unexpected results if you cumulatively apply another transform.
     * @param g Um objeto Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bufferDeImagensParaDesenhar != null) {
            g.drawImage(bufferDeImagensParaDesenhar, 0, 0, null);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Runnable">
    /**
     * Repete: atualiza, renderiza, sleep
     */
    @Override
    public void run() {
        rodando = true;
        
        initGameLoop();
        
        System.exit(0);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
    @Override
    public void desenha(Graphics2D g) {
        if (caixaColisao.getCorPreenchimento() != null) {
            caixaColisao.desenha(g);
        }
        
        invocarDesenhavelList(g);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Atualizavel">
    @Override
    public void atualiza() {
        invocarAtualizavelList();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: DesenhavelListManuseavel">
    @Override
    public void invocarDesenhavelList(Graphics2D g2d) {
        desenhaveis.invocarDesenhavelList(g2d);
    }

    @Override
    public void addDesenhavel(Desenhavel novoElemento) {
        desenhaveis.add(novoElemento);
    }

    @Override
    public void addDesenhavel(Desenhavel... variosElementos) {
        desenhaveis.add(variosElementos);
    }

    @Override
    public Desenhavel getDesenhavel(int indice) {
        return desenhaveis.get(indice);
    }

    @Override
    public Desenhavel getNextDesenhavel() {
        return desenhaveis.getNext();
    }

    @Override
    public void removeDesenhavel(int indice) {
        desenhaveis.remove(indice);
    }

    @Override
    public void removeDesenhavel(Desenhavel elementoParaRemover) {
        desenhaveis.remove(elementoParaRemover);
    }

    @Override
    public int sizeDesenhavelList() {
        return desenhaveis.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: AtualizavelListManuseavel">
    @Override
    public void invocarAtualizavelList() {
        atualizaveis.invocarAtualizavelList();
    }

    @Override
    public void addAtualizavel(Atualizavel novoElemento) {
        atualizaveis.add(novoElemento);
    }

    @Override
    public void addAtualizavel(Atualizavel... variosElementos) {
        atualizaveis.add(variosElementos);
    }

    @Override
    public Atualizavel getAtualizavel(int indice) {
        return atualizaveis.get(indice);
    }

    @Override
    public Atualizavel getNextAtualizavel() {
        return atualizaveis.getNext();
    }

    @Override
    public void removeAtualizavel(int indice) {
        atualizaveis.remove(indice);
    }

    @Override
    public void removeAtualizavel(Atualizavel elementoParaRemover) {
        atualizaveis.remove(elementoParaRemover);
    }

    @Override
    public int sizeAtualizavelList() {
        return atualizaveis.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: KeyListener">
    @Override
    public void keyTyped(KeyEvent e) {
        invocarKeyTypedList(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        invocarKeyPressedList(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        invocarKeyReleasedList(e);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: MouseListener">
    @Override
    public void mouseClicked(MouseEvent e) {
        invocarMouseClickedList(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        invocarMousePressedList(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        invocarMouseReleasedList(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        invocarMouseEnteredList(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        invocarMouseExitedList(e);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: MouseMotionListener">
    @Override
    public void mouseDragged(MouseEvent e) {
        invocarMouseDraggedList(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        invocarMouseMovedList(e);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: MouseWheelListener">
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        invocarMouseWheelMovedList(e);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: ListenerListManuseavel">
    
    //<editor-fold defaultstate="collapsed" desc="01-Métodos: KeyTyped">
    @Override
    public void addKeyTyped(KeyTyped keyTyped) {
        keyTypedList.add(keyTyped);
    }

    @Override
    public KeyTyped getKeyTyped(int indice) {
        return keyTypedList.get(indice);
    }

    @Override
    public void removeKeyTyped(int indice) {
        keyTypedList.remove(indice);
    }

    @Override
    public void invocarKeyTypedList(KeyEvent event) {
        for (int indice = 0; indice < keyTypedList.size(); indice++) {
            keyTypedList.get(indice).keyTyped(event);
        }
    }

    @Override
    public int sizeKeyTypedList() {
        return keyTypedList.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="02-Métodos: KeyPressed">
    @Override
    public void addKeyPressed(KeyPressed keyPressed) {
        keyPressedList.add(keyPressed);
    }

    @Override
    public KeyPressed getKeyPressed(int indice) {
        return keyPressedList.get(indice);
    }

    @Override
    public void removeKeyPressed(int indice) {
        keyPressedList.remove(indice);
    }

    @Override
    public void invocarKeyPressedList(KeyEvent event) {
        for (int indice = 0; indice < keyPressedList.size(); indice++) {
            keyPressedList.get(indice).keyPressed(event);
        }
    }

    @Override
    public int sizeKeyPressedList() {
        return keyPressedList.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="03-Métodos: KeyReleased">
    @Override
    public void addKeyReleased(KeyReleased keyReleased) {
        keyReleasedList.add(keyReleased);
    }

    @Override
    public KeyReleased getKeyReleased(int indice) {
        return keyReleasedList.get(indice);
    }

    @Override
    public void removeKeyReleased(int indice) {
        keyReleasedList.remove(indice);
    }

    @Override
    public void invocarKeyReleasedList(KeyEvent event) {
        for (int indice = 0; indice < keyReleasedList.size(); indice++) {
            keyReleasedList.get(indice).keyReleased(event);
        }
    }

    @Override
    public int sizeKeyReleasedList() {
        return keyReleasedList.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="04-Métodos: MouseClicked">
    @Override
    public void addMouseClicked(MouseClicked mouseClicked) {
        mouseClickedList.add(mouseClicked);
    }

    @Override
    public MouseClicked getMouseClicked(int indice) {
        return mouseClickedList.get(indice);
    }

    @Override
    public void removeMouseClicked(int indice) {
        mouseClickedList.remove(indice);
    }

    @Override
    public void invocarMouseClickedList(MouseEvent event) {
        for (int indice = 0; indice < mouseClickedList.size(); indice++) {
            mouseClickedList.get(indice).mouseClicked(event);
        }
    }

    @Override
    public int sizeMouseClickedList() {
        return mouseClickedList.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="05-Métodos: MousePressed">
    @Override
    public void addMousePressed(MousePressed mousePressed) {
        mousePressedList.add(mousePressed);
    }

    @Override
    public MousePressed getMousePressed(int indice) {
        return mousePressedList.get(indice);
    }

    @Override
    public void removeMousePressed(int indice) {
        mousePressedList.remove(indice);
    }

    @Override
    public void invocarMousePressedList(MouseEvent event) {
        for (int indice = 0; indice < mousePressedList.size(); indice++) {
            mousePressedList.get(indice).mousePressed(event);
        }
    }

    @Override
    public int sizeMousePressedList() {
        return mousePressedList.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="06-Métodos: MouseReleased">
    @Override
    public void addMouseReleased(MouseReleased mouseReleased) {
        mouseReleasedList.add(mouseReleased);
    }

    @Override
    public MouseReleased getMouseReleased(int indice) {
        return mouseReleasedList.get(indice);
    }

    @Override
    public void removeMouseReleased(int indice) {
        mouseReleasedList.remove(indice);
    }

    @Override
    public void invocarMouseReleasedList(MouseEvent event) {
        for (int indice = 0; indice < mouseReleasedList.size(); indice++) {
            mouseReleasedList.get(indice).mouseReleased(event);
        }
    }

    @Override
    public int sizeMouseReleasedList() {
        return mouseReleasedList.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="07-Métodos: MouseEntered">
    @Override
    public void addMouseEntered(MouseEntered mouseEntered) {
        mouseEnteredList.add(mouseEntered);
    }

    @Override
    public MouseEntered getMouseEntered(int indice) {
        return mouseEnteredList.get(indice);
    }

    @Override
    public void removeMouseEntered(int indice) {
        mouseEnteredList.remove(indice);
    }

    @Override
    public void invocarMouseEnteredList(MouseEvent event) {
        for (int indice = 0; indice < mouseEnteredList.size(); indice++) {
            mouseEnteredList.get(indice).mouseEntered(event);
        }
    }

    @Override
    public int sizeMouseEnteredList() {
        return mouseEnteredList.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="08-Métodos: MouseExited">
    @Override
    public void addMouseExited(MouseExited mouseExited) {
        mouseExitedList.add(mouseExited);
    }

    @Override
    public MouseExited getMouseExited(int indice) {
        return mouseExitedList.get(indice);
    }

    @Override
    public void removeMouseExited(int indice) {
        mouseExitedList.remove(indice);
    }

    @Override
    public void invocarMouseExitedList(MouseEvent event) {
        for (int indice = 0; indice < mouseExitedList.size(); indice++) {
            mouseExitedList.get(indice).mouseExited(event);
        }
    }

    @Override
    public int sizeMouseExitedList() {
        return mouseExitedList.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="09-Métodos: MouseDragged">
    @Override
    public void addMouseDragged(MouseDragged mouseDragged) {
        mouseDraggedList.add(mouseDragged);
    }

    @Override
    public MouseDragged getMouseDragged(int indice) {
        return mouseDraggedList.get(indice);
    }

    @Override
    public void removeMouseDragged(int indice) {
        mouseDraggedList.remove(indice);
    }

    @Override
    public void invocarMouseDraggedList(MouseEvent event) {
        for (int indice = 0; indice < mouseDraggedList.size(); indice++) {
            mouseDraggedList.get(indice).mouseDragged(event);
        }
    }

    @Override
    public int sizeMouseDraggedList() {
        return mouseDraggedList.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="10-Métodos: MouseMoved">
    @Override
    public void addMouseMoved(MouseMoved mouseMoved) {
        mouseMovedList.add(mouseMoved);
    }

    @Override
    public MouseMoved getMouseMoved(int indice) {
        return mouseMovedList.get(indice);
    }

    @Override
    public void removeMouseMoved(int indice) {
        mouseMovedList.remove(indice);
    }

    @Override
    public void invocarMouseMovedList(MouseEvent event) {
        for (int indice = 0; indice < mouseMovedList.size(); indice++) {
            mouseMovedList.get(indice).mouseMoved(event);
        }
    }

    @Override
    public int sizeMouseMovedList() {
        return mouseMovedList.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="11-Métodos: MouseWheelMoved">
    @Override
    public void addMouseWheelMoved(MouseWheelMoved mouseWheelMoved) {
        mouseWheelMovedList.add(mouseWheelMoved);
    }

    @Override
    public MouseWheelMoved getMouseWheelMoved(int indice) {
        return mouseWheelMovedList.get(indice);
    }

    @Override
    public void removeMouseWheelMoved(int indice) {
        mouseWheelMovedList.remove(indice);
    }

    @Override
    public void invocarMouseWheelMovedList(MouseWheelEvent event) {
        for (int indice = 0; indice < mouseWheelMovedList.size(); indice++) {
            mouseWheelMovedList.get(indice).mouseWheelMoved(event);
        }
    }

    @Override
    public int sizeMouseWheelMovedList() {
        return mouseWheelMovedList.size();
    }
    //</editor-fold>
    
    //</editor-fold>
        
}
