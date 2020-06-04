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

import simplegamework.objetoBasico.CaixaColisao;
import simplegamework.objetoBasico.LadoRetangulo;
import simplegamework.padraoProjeto.Atualizavel;
import simplegamework.padraoProjeto.AtualizavelListManuseavel;
import simplegamework.padraoProjeto.DesenhavelListManuseavel;
import simplegamework.padraoProjeto.Colidivel;
import simplegamework.padraoProjeto.Desenhavel;
import simplegamework.padraoProjeto.Estatavel;
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
import simplegamework.maquinaEstados.MaquinaEstados;
import simplegamework.objetoBasico.TipoColisao;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.*;

/**
 * Essa classe une JanelaDesenho, PainelDesenho e MaquinaEstados, sendo assim,
 * uma facilitadora para se criar uma tela básica para um jogo.
 * <br><br><small>Created on : 18/05/2015, 22:07:42</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class TelaJogo implements Colidivel, DesenhavelListManuseavel, 
        AtualizavelListManuseavel, ListenerListManuseavel{
    
    private JanelaDesenho janela;
    private PainelDesenho painel;
    private MaquinaEstados maquinaEstados;
    
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
    public TelaJogo(
            String titulo, int comprimento, int altura, 
            boolean telaCheia, int UPS
    ){
        janela = new JanelaDesenho(titulo, comprimento, altura, telaCheia, UPS);
        painel = janela.getPainelDesenho();
        
        initMaquinaEstados();
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
    public TelaJogo(
            String titulo, int comprimento, int altura, 
            Image iconeJanela, boolean telaCheia, int UPS
    ){
        janela = new JanelaDesenho(titulo, comprimento, altura, iconeJanela, telaCheia, UPS);
        painel = janela.getPainelDesenho();
        
        initMaquinaEstados();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método: Auxiliar de MaquinaEstado">
    /**
     * Inicializa a MaquinaEstados.
     */
    private void initMaquinaEstados(){
        maquinaEstados = new MaquinaEstados();
        
        //adiciona MaquinaEstados como Desenhavel
        addDesenhavel(maquinaEstados);
        
        //adiciona MaquinaEstados como Atulizavel
        addAtualizavel(maquinaEstados);
        
        //adcionando MaquinaEstados aos listeners do PainelDesenho
        painel.addKeyListener(maquinaEstados);
        painel.addMouseListener(maquinaEstados);
        painel.addMouseMotionListener(maquinaEstados);
        painel.addMouseWheelListener(maquinaEstados);        
    }
    //</editor-fold>
    
    /**
     * Inicializa a TelaJogo dentro de uma Thread específica e a torna visível.
     */
    public void exibir(){
        EventQueue.invokeLater(() -> {
            janela.setVisible(true);
        });
    }
    
    /**
     * Retorna o comprimento da tela.
     * @return int
     */
    public int getComprimento(){
        return painel.getWidth();
    }
    
    /**
     * Retorna a altura da tela.
     * @return int
     */
    public int getAltura(){
        return painel.getHeight();
    }
    
    /**
     * Ativa o debug via terminal que demonstra a capacidade de UPS atingida pela
     * máquina.
     */
    public void ativarDebugTempoExecucao(){
        painel.ativarDebugTempoExecucao();
    }
    
    /**
     * Desativa o debug de UPS.
     */
    public void desativarDebugTempoExecucao(){
        painel.desativarDebugTempoExecucao();
    }
    
    /**
     * Retorna o status do debug de UPS.
     * @return boolean
     */
    public boolean isDebugTempoExecucaoAtivo(){
        return painel.isDebugTempoExecucaoAtivo();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: Manuseio MaquinaEstados">
    /**
     * Retorna a MaquinaEstados.
     * @return MaquinaEstados
     */
    public MaquinaEstados getMaquinaEstados(){
        return maquinaEstados;        
    }
    
    /**
     * Altera a MaquinaEstados, ou seja, apaga a anterior e substitui pela nova
     * passada por parâmetro.
     * @param novaMaquinaEstados A nova MaquinaEstados
     */
    public void setMaquinaEstados(MaquinaEstados novaMaquinaEstados){
        maquinaEstados = novaMaquinaEstados;
    }
    
    /**
     * Substitui a MaquinaEstados anterior pela nova passada por parâmetro e
     * mantém os estados anteriores.<br>
     * Obs.: Os rótulos dos estados anteriores serão perdidos.
     * @param novaMaquinaEstados A nova MaquinaEstados
     */
    public void trocarMaquinaEstados(MaquinaEstados novaMaquinaEstados){
        for (int indice = 0; indice < maquinaEstados.size(); indice++) {
            novaMaquinaEstados.add("ESTADO-" + indice, maquinaEstados.get(indice));
        }
        
        maquinaEstados = novaMaquinaEstados;
    }
    
    /**
     * Adiciona um novo estado à MaquinaEstados.
     * @param rotuloEstado O identificador do estado adicionado
     * @param novoEstado O novo elemento a ser adicionado
     */
    public void addEstado(Object rotuloEstado, Estatavel novoEstado){
        maquinaEstados.add(rotuloEstado, novoEstado);
    }
    
    /**
     * Retorna uma estado de acordo com seu rótulo passado por parâmetro.
     * @param rotuloEstado O rótulo do estado procurado
     * @return Estatavel
     */
    public Estatavel getEstado(Object rotuloEstado){
        return maquinaEstados.get(rotuloEstado);
    }
    
    /**
     * Retorna um estado da MaquinaEstados de acordo com o índice passado por parâmetro.
     * @param indiceEstado O índice do estado procurado
     * @return Retorna o estado procurado caso ele tenha sido encontrado, caso
     * contrário será retornado null
     */
    public Estatavel getEstado(int indiceEstado){
        return maquinaEstados.get(indiceEstado);
    }
    
    /**
     * Retorna o estado atual selecionado previamente.<br>
     * Obs.: Sempre o estado inicial será o primeiro estado adicionado.
     * @return Estatavel
     */
    public Estatavel getEstadoAtual(){
        return maquinaEstados.getEstadoAtual();
    }
    
    /**
     * Retorna o índice do estado atual.
     * @return int
     */
    public int getIndiceEstadoAtual(){
        return maquinaEstados.getIndiceEstadoAtual();
    }
    
    /**
     * Retorna o tempo decorrido de execução do estado atual em milissegundos.
     * @return double
     */
    public double getTempoDecorridoExecucaoEstadoAtual(){
        return maquinaEstados.getTempoDecorridoExecucaoEstadoAtual();
    }
    
    /**
     * Alterna um a um entre os estadosString adicionados.
     */
    public void mudarEstado(){
        maquinaEstados.mudarEstado();
    }
    
    /**
     * Alterna diretamente para o estado passado por parâmetro. 
     * @param rotuloEstado O estado a ser selecionado
     */
    public void mudarEstadoPara(Object rotuloEstado){
        maquinaEstados.mudarEstadoPara(rotuloEstado);
    }
    
    /**
     * Alterna diretamente para o estado passado por parâmetro. 
     * @param indiceEstado O estado a ser selecionado
     */
    public void mudarEstadoPara(int indiceEstado){
        maquinaEstados.mudarEstadoPara(indiceEstado);
    }
    
    /**
     * Remove um estado de acordo com rótulo passado por parâmetro.
     * @param rotuloEstado O rótulo do estado procurado
     */
    public void removeEstado(Object rotuloEstado){
        maquinaEstados.remove(rotuloEstado);
    }
    
    /**
     * Remove um estado de acordo com índice passado por parâmetro.
     * @param indiceEstado O índice do estado procurado
     */
    public void removeEstado(int indiceEstado){
        maquinaEstados.remove(indiceEstado);
    }
    
    /**
     * Retorna a quantidade de estadosString adicionados.
     * @return int
     */
    public int sizeMaquinaEstados(){
        return maquinaEstados.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Colidivel">
    /**
     * Retorna a CaixaColisao que define os limites da tela.
     * @return CaixaColisao
     */
    @Override
    public CaixaColisao getCaixaColisao(){
        return painel.getCaixaColisao();
    }
    
    @Override
    public boolean colidiu(CaixaColisao outraCaixaColisao, TipoColisao tipoColisao) {
        return painel.getCaixaColisao().colidiu(outraCaixaColisao, tipoColisao);
    }

    @Override
    public LadoRetangulo colidiuNoLado(CaixaColisao outraCaixaColisao, TipoColisao tipoColisao) {
        return painel.getCaixaColisao().colidiuNoLado(outraCaixaColisao, tipoColisao);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: DesenhavelListManuseavel">
    @Override
    public void invocarDesenhavelList(Graphics2D g2d) {
        painel.invocarDesenhavelList(g2d);
    }

    @Override
    public void addDesenhavel(Desenhavel novoElemento) {
        painel.addDesenhavel(novoElemento);
    }

    @Override
    public void addDesenhavel(Desenhavel... variosElementos) {
        painel.addDesenhavel(variosElementos);
    }

    @Override
    public Desenhavel getDesenhavel(int indice) {
        return painel.getDesenhavel(indice);
    }

    @Override
    public Desenhavel getNextDesenhavel() {
        return painel.getNextDesenhavel();
    }

    @Override
    public void removeDesenhavel(int indice) {
        painel.removeDesenhavel(indice);
    }

    @Override
    public void removeDesenhavel(Desenhavel elementoParaRemover) {
        painel.removeDesenhavel(elementoParaRemover);
    }

    @Override
    public int sizeDesenhavelList() {
        return painel.sizeDesenhavelList();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: AtualizavelListManuseavel">
    @Override
    public void invocarAtualizavelList() {
        painel.invocarAtualizavelList();
    }

    @Override
    public void addAtualizavel(Atualizavel novoElemento) {
        painel.addAtualizavel(novoElemento);
    }

    @Override
    public void addAtualizavel(Atualizavel... variosElementos) {
        painel.addAtualizavel(variosElementos);
    }

    @Override
    public Atualizavel getAtualizavel(int indice) {
        return painel.getAtualizavel(indice);
    }

    @Override
    public Atualizavel getNextAtualizavel() {
        return painel.getNextAtualizavel();
    }

    @Override
    public void removeAtualizavel(int indice) {
        painel.removeAtualizavel(indice);
    }

    @Override
    public void removeAtualizavel(Atualizavel elementoParaRemover) {
        painel.removeAtualizavel(elementoParaRemover);
    }

    @Override
    public int sizeAtualizavelList() {
        return painel.sizeAtualizavelList();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: Manuseio Listeners de Mouse e Teclado">
    /**
     * Adiciona um KeyListener à tela.
     * @param keyListener O novo evento a ser adiconado
     */
    public void addKeyListener(KeyListener keyListener){
        painel.addKeyListener(keyListener);
    }
    
    /**
     * Adiciona um MouseListener à tela.
     * @param mouseListener O novo evento a ser adiconado
     */
    public void addMouseListener(MouseListener mouseListener){
        painel.addMouseListener(mouseListener);
    }
    
    /**
     * Adiciona um MouseMotionListener à tela.
     * @param mouseMotionListener O novo evento a ser adiconado
     */
    public void addMouseMotionListener(MouseMotionListener mouseMotionListener){
        painel.addMouseMotionListener(mouseMotionListener);
    }
    
    /**
     * Adiciona um MouseWheelListener à tela.
     * @param mouseWheelListener O novo evento a ser adiconado
     */
    public void addMouseWheelListener(MouseWheelListener mouseWheelListener){
        painel.addMouseWheelListener(mouseWheelListener);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: ListenerListManuseavel">
    
    //<editor-fold defaultstate="collapsed" desc="01-Métodos: KeyTyped">
    @Override
    public void addKeyTyped(KeyTyped keyTyped) {
        painel.addKeyTyped(keyTyped);
    }

    @Override
    public KeyTyped getKeyTyped(int indice) {
        return painel.getKeyTyped(indice);
    }

    @Override
    public void removeKeyTyped(int indice) {
        painel.removeKeyTyped(indice);
    }

    @Override
    public void invocarKeyTypedList(KeyEvent event) {
        painel.invocarKeyTypedList(event);
    }

    @Override
    public int sizeKeyTypedList() {
        return painel.sizeKeyTypedList();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="02-Métodos: KeyPressed">
    @Override
    public void addKeyPressed(KeyPressed keyPressed) {
        painel.addKeyPressed(keyPressed);
    }

    @Override
    public KeyPressed getKeyPressed(int indice) {
        return painel.getKeyPressed(indice);
    }

    @Override
    public void removeKeyPressed(int indice) {
        painel.removeKeyPressed(indice);
    }

    @Override
    public void invocarKeyPressedList(KeyEvent event) {
        painel.invocarKeyPressedList(event);
    }

    @Override
    public int sizeKeyPressedList() {
        return painel.sizeKeyPressedList();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="03-Métodos: KeyReleased">
    @Override
    public void addKeyReleased(KeyReleased keyReleased) {
        painel.addKeyReleased(keyReleased);
    }

    @Override
    public KeyReleased getKeyReleased(int indice) {
        return painel.getKeyReleased(indice);
    }

    @Override
    public void removeKeyReleased(int indice) {
        painel.removeKeyReleased(indice);
    }

    @Override
    public void invocarKeyReleasedList(KeyEvent event) {
        painel.invocarKeyReleasedList(event);
    }

    @Override
    public int sizeKeyReleasedList() {
        return painel.sizeKeyReleasedList();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="04-Métodos: MouseClicked">
    @Override
    public void addMouseClicked(MouseClicked mouseClicked) {
        painel.addMouseClicked(mouseClicked);
    }

    @Override
    public MouseClicked getMouseClicked(int indice) {
        return painel.getMouseClicked(indice);
    }

    @Override
    public void removeMouseClicked(int indice) {
        painel.removeMouseClicked(indice);
    }

    @Override
    public void invocarMouseClickedList(MouseEvent event) {
        painel.invocarMouseClickedList(event);
    }

    @Override
    public int sizeMouseClickedList() {
        return painel.sizeMouseClickedList();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="05-Métodos: MousePressed">
    @Override
    public void addMousePressed(MousePressed mousePressed) {
        painel.addMousePressed(mousePressed);
    }

    @Override
    public MousePressed getMousePressed(int indice) {
        return painel.getMousePressed(indice);
    }

    @Override
    public void removeMousePressed(int indice) {
        painel.removeMousePressed(indice);
    }

    @Override
    public void invocarMousePressedList(MouseEvent event) {
        painel.invocarMousePressedList(event);
    }

    @Override
    public int sizeMousePressedList() {
        return painel.sizeMousePressedList();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="06-Métodos: MouseReleased">
    @Override
    public void addMouseReleased(MouseReleased mouseReleased) {
        painel.addMouseReleased(mouseReleased);
    }

    @Override
    public MouseReleased getMouseReleased(int indice) {
        return painel.getMouseReleased(indice);
    }

    @Override
    public void removeMouseReleased(int indice) {
        painel.removeMouseReleased(indice);
    }

    @Override
    public void invocarMouseReleasedList(MouseEvent event) {
        painel.invocarMouseReleasedList(event);
    }

    @Override
    public int sizeMouseReleasedList() {
        return painel.sizeMouseReleasedList();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="07-Métodos: MouseEntered">
    @Override
    public void addMouseEntered(MouseEntered mouseEntered) {
        painel.addMouseEntered(mouseEntered);
    }

    @Override
    public MouseEntered getMouseEntered(int indice) {
        return painel.getMouseEntered(indice);
    }

    @Override
    public void removeMouseEntered(int indice) {
        painel.removeMouseEntered(indice);
    }

    @Override
    public void invocarMouseEnteredList(MouseEvent event) {
        painel.invocarMouseEnteredList(event);
    }

    @Override
    public int sizeMouseEnteredList() {
        return painel.sizeMouseEnteredList();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="08-Métodos: MouseExited">
    @Override
    public void addMouseExited(MouseExited mouseExited) {
        painel.addMouseExited(mouseExited);
    }

    @Override
    public MouseExited getMouseExited(int indice) {
        return painel.getMouseExited(indice);
    }

    @Override
    public void removeMouseExited(int indice) {
        painel.removeMouseExited(indice);
    }

    @Override
    public void invocarMouseExitedList(MouseEvent event) {
        painel.invocarMouseExitedList(event);
    }

    @Override
    public int sizeMouseExitedList() {
        return painel.sizeMouseExitedList();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="09-Métodos: MouseDragged">
    @Override
    public void addMouseDragged(MouseDragged mouseDragged) {
        painel.addMouseDragged(mouseDragged);
    }

    @Override
    public MouseDragged getMouseDragged(int indice) {
        return painel.getMouseDragged(indice);
    }

    @Override
    public void removeMouseDragged(int indice) {
        painel.removeMouseDragged(indice);
    }

    @Override
    public void invocarMouseDraggedList(MouseEvent event) {
        painel.invocarMouseDraggedList(event);
    }

    @Override
    public int sizeMouseDraggedList() {
        return painel.sizeMouseDraggedList();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="10-Métodos: MouseMoved">
    @Override
    public void addMouseMoved(MouseMoved mouseMoved) {
        painel.addMouseMoved(mouseMoved);
    }

    @Override
    public MouseMoved getMouseMoved(int indice) {
        return painel.getMouseMoved(indice);
    }

    @Override
    public void removeMouseMoved(int indice) {
        painel.removeMouseMoved(indice);
    }

    @Override
    public void invocarMouseMovedList(MouseEvent event) {
        painel.invocarMouseMovedList(event);
    }

    @Override
    public int sizeMouseMovedList() {
        return painel.sizeMouseMovedList();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="11-Métodos: MouseWheelMoved">
    @Override
    public void addMouseWheelMoved(MouseWheelMoved mouseWheelMoved) {
        painel.addMouseWheelMoved(mouseWheelMoved);
    }

    @Override
    public MouseWheelMoved getMouseWheelMoved(int indice) {
        return painel.getMouseWheelMoved(indice);
    }

    @Override
    public void removeMouseWheelMoved(int indice) {
        painel.removeMouseWheelMoved(indice);
    }

    @Override
    public void invocarMouseWheelMovedList(MouseWheelEvent event) {
        painel.invocarMouseWheelMovedList(event);
    }

    @Override
    public int sizeMouseWheelMovedList() {
        return painel.sizeMouseWheelMovedList();
    }
    //</editor-fold>
    
    //</editor-fold>
            
}
