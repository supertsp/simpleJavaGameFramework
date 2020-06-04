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

package simplegamework.maquinaEstados;

import simplegamework.padraoProjeto.Atualizavel;
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
import simplegamework.ferramenta.Alternador;
import simplegamework.ferramenta.MedidaTempo;
import simplegamework.ferramenta.TempoExecucao;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Uma MaquinaEstados é utilizada para controlar os menus, fases, subfases e outros
 * elementos mais que são utilizados para construir as partes de um jogo. Essa
 * classe sempre chamará os métodos desenha() e atualiza() do estado atual 
 * selecionado.<br>
 * Para se adicionar um novo estado é preciso que o objeto implemente Estatavel. <br>
 * Caso os estados precisem comunicar entre si deverá ser utilizado o QuadroRecados,
 * bastando apenas adicionar/recuperar/remover os recados.
 * 
 * <br><br><small>Created on : 08/08/2015, 11:32:06</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class MaquinaEstados implements Desenhavel, Atualizavel, KeyListener, 
        MouseListener, MouseMotionListener, MouseWheelListener, ListenerListManuseavel{

    protected Alternador<Object> rotuloEstados;
    protected Alternador<Estatavel> estados;
    protected TempoExecucao tempoExecucaoEstado;
    protected QuadroRecados quadroRecados;
    
    //Auxliares
    private int indiceEstadoAnterior;
    private TempoExecucao tempoEsperaMudarEstado;
    private boolean entrouEmEspera;
    private int indiceEstadoEscolhido;
    private Object rotuloEstadoEscolhido;
    
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
     * Construtor básico de classe.
     */
    public MaquinaEstados(){
        rotuloEstados = new Alternador<>();
        estados = new Alternador<>();
        tempoExecucaoEstado = new TempoExecucao(0, MedidaTempo.MILISSEGUNDOS);  
        quadroRecados = new QuadroRecados();
        
        //Auxliar
        tempoEsperaMudarEstado = new TempoExecucao(0, MedidaTempo.SEGUNDOS);
        indiceEstadoEscolhido = -1;
        
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
    
    /**
     * Construtor completo de classe.
     * @param rotuloEstado O rótulo do novo estado
     * @param estadoInicial O novo estado a ser adicionado
     */
    public MaquinaEstados(Object rotuloEstado, Estatavel estadoInicial){
        this();
        add(rotuloEstado, estadoInicial);
        tempoExecucaoEstado.iniciarTempo();
        getEstadoAtual().aoIniciarEstado();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métdos Auxiliares">
    //private 
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: Manuseáveis de Estados">
    /**
     * Adiciona um novo estado à MaquinaEstados.
     * @param rotuloEstado O identificador do estado adicionado
     * @param novoEstado O novo elemento a ser adicionado
     */
    public void add(Object rotuloEstado, Estatavel novoEstado){
        if (size() == 0) {
            rotuloEstados.add(rotuloEstado);
            estados.add(novoEstado);
            novoEstado.aoIniciarEstado();
            tempoExecucaoEstado.iniciarTempo();
        }
        else{
            rotuloEstados.add(rotuloEstado);
            estados.add(novoEstado);
        }        
    }
    
    /**
     * Retorna um estado da MaquinaEstados de acordo com o índice passado por parâmetro.
     * @param indice O índice do estado procurado
     * @return Retorna o estado procurado caso ele tenha sido encontrado, caso
     * contrário será retornado null
     */
    public Estatavel get(int indice){
        return estados.get(indice);
    }
    
    /**
     * Retorna uma estado de acordo com seu rótulo passado por parâmetro.
     * @param rotuloEstado O rótulo do estado procurado
     * @return Estatavel
     */
    public Estatavel get(Object rotuloEstado){
        for (int indice = 0; indice < estados.size(); indice++) {
            if (rotuloEstado == rotuloEstados.get(indice)) {
                return estados.get(indice);
            }
        }
        
        return null;
    }
    
    /**
     * Retorna o estado atual selecionado previamente.<br>
     * Obs.: Sempre o estado inicial será o primeiro estado adicionado.
     * @return Estatavel
     */
    public Estatavel getEstadoAtual(){
        return estados.getEstadoAtual();
    }
    
    /**
     * Retorna o estado anterior ao atual
     * @return Estatavel
     */
    public Estatavel getEstadoAnterior(){
        return estados.get(indiceEstadoAnterior);
    }
    
    /**
     * Retorna o índice do estado atual.
     * @return int
     */
    public int getIndiceEstadoAtual(){
        return estados.getIndiceEstadoAtual();
    }
    
    /**
     * Retorna o índice do estado anterior ao atual.
     * @return int
     */
    public int getIndiceEstadoAnterior(){
        return indiceEstadoAnterior;
    }
    
    /**
     * Alterna um a um entre os estados adicionados.
     */
    public void mudarEstado(){
        if (!entrouEmEspera) {
            //Guardando referência anterior
            indiceEstadoAnterior = estados.getIndiceEstadoAtual();

            //Encerrando estado anterior
            estados.getEstadoAtual().aoEncerrarEstado();

            //Mudando para o novo estado
            rotuloEstados.mudarEstado();
            estados.mudarEstado();

            //Inicia a marcação do tempo de execução
            tempoExecucaoEstado.iniciarTempo();

            //Carregando a pré inicialização do estado
            estados.getEstadoAtual().aoIniciarEstado();
        }
    }
    
    /**
     * Alterna um a um entre os estados adicionados somente após o tempo de espera
     * ter findado.
     * @param tempoEspera O tempo de aguardo antes de efeturar a mudança
     */
    public void mudarEstado(double tempoEspera){
        if (!entrouEmEspera) {
            entrouEmEspera = true;
            tempoEsperaMudarEstado.setMeta(tempoEspera);
            tempoEsperaMudarEstado.iniciarTempo();
        }
    }
    
    /**
     * Alterna diretamente para o estado passado por parâmetro. 
     * @param rotuloEstado O estado a ser selecionado
     */
    public void mudarEstadoPara(Object rotuloEstado){
        if (!entrouEmEspera) {
            //Guardando referência anterior
            indiceEstadoAnterior = estados.getIndiceEstadoAtual();

            //Encerrando estado anterior
            estados.getEstadoAtual().aoEncerrarEstado();

            //Mudando para o novo estado        
            rotuloEstados.mudarEstadoPara(rotuloEstado);
            estados.mudarEstadoPara(rotuloEstados.getIndiceEstadoAtual());

            //Inicia a marcação do tempo de execução
            tempoExecucaoEstado.iniciarTempo();

            //Carregando a pré inicialização do estado
            estados.getEstadoAtual().aoIniciarEstado();
        }
    }
    
    /**
     * Alterna diretamente para o estado passado por parâmetro somente após o
     * tempo de espera ter findado.
     * @param rotuloEstado O estado a ser selecionado
     * @param tempoEspera O tempo de aguardo antes de efeturar a mudança
     */
    public void mudarEstadoPara(Object rotuloEstado, double tempoEspera){
        if (!entrouEmEspera) {
            entrouEmEspera = true;
            tempoEsperaMudarEstado.setMeta(tempoEspera);
            tempoEsperaMudarEstado.iniciarTempo();
            rotuloEstadoEscolhido = rotuloEstado;
        }
    }
    
    /**
     * Alterna diretamente para o estado passado por parâmetro. 
     * @param indiceEstado O estado a ser selecionado
     */
    public void mudarEstadoPara(int indiceEstado){
        if (!entrouEmEspera) {
            //Guardando referência anterior
            indiceEstadoAnterior = estados.getIndiceEstadoAtual();

            //Encerrando estado anterior
            estados.getEstadoAtual().aoEncerrarEstado();

            //Mudando para o novo estado
            rotuloEstados.mudarEstadoPara(indiceEstado);
            estados.mudarEstadoPara(indiceEstado);

            //Inicia a marcação do tempo de execução
            tempoExecucaoEstado.iniciarTempo();

            //Carregando a pré inicialização do estado
            estados.getEstadoAtual().aoIniciarEstado();
        }
    }
    
    /**
     * Alterna diretamente para o estado passado por parâmetro somente após o
     * tempo de espera ter findado.
     * @param indiceEstado O estado a ser selecionado
     * @param tempoEspera O tempo de aguardo antes de efeturar a mudança
     */
    public void mudarEstadoPara(int indiceEstado, double tempoEspera){
        if (!entrouEmEspera) {
            entrouEmEspera = true;
            tempoEsperaMudarEstado.setMeta(tempoEspera);
            tempoEsperaMudarEstado.iniciarTempo();
            indiceEstadoEscolhido = indiceEstado;
        }
    }
    
    /**
     * Remove um estado de acordo com índice passado por parâmetro.
     * @param indiceEstado O índice do estado procurado
     */
    public void remove(int indiceEstado){
        rotuloEstados.remove(indiceEstado);
        estados.remove(indiceEstado);
    }
    
    /**
     * Remove um estado de acordo com rótulo passado por parâmetro.
     * @param rotuloEstado O rótulo do estado procurado
     */
    public void remove(Object rotuloEstado){
        for (int indice = 0; indice < estados.size(); indice++) {
            if (rotuloEstado == rotuloEstados.get(indice)) {
                rotuloEstados.remove(indice);
                estados.remove(indice);
                break;
            }
        }
    }
    
    /**
     * Retorna a quantidade de estados adicionados.
     * @return int
     */
    public int size(){
        return estados.size();
    }
    
    /**
     * Retorna o tempo decorrido de execução do estado atual em milissegundos.
     * @return double
     */
    public double getTempoDecorridoExecucaoEstadoAtual(){
        return tempoExecucaoEstado.getTempoDecorrido();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: Manuseáveis de QuadroRecados">
    /**
     * Retorna o QuadroRecados.
     * @return QuadroRecados
     */
    public QuadroRecados getQuadroRecados(){
        return quadroRecados;
    }
    
    /**
     * Adiciona um novo recado ao QuadroRecados.
     * @param novoElemento O novo recado a ser adicionado
     */
    public void addRecado(Object novoElemento) {
        quadroRecados.add(novoElemento);
    }
    
    /**
     * Adiciona ou Altera um novo recado no QuadroRecados de acordo com a posição desejada.
     * @param indice A posição desejada
     * @param novoElemento O novo recado a ser adicionado
     */
    public void addRecado(int indice, Object novoElemento){
        quadroRecados.add(indice, novoElemento);
    }

    /**
     * Adiciona vários recados ao mesmo tempo dentro do QuadroRecados.<br>
     * Ex.: objeto.addRecado(elemento1, elemento2, ..., elementoN);
     * @param variosElementos Os novos recados
     */
    public void addRecados(Object... variosElementos) {
        quadroRecados.add(variosElementos);
    }

    /**
     * Retorna um recado do QuadroRecados de acordo com o índice passado por parâmetro.
     * @param indice O elemento procurado
     * @return Object Retorna o recado procurado caso ele tenha sido encontrado, caso
     * contrário será retornado null
     */
    public Object getRecado(int indice) {
        return quadroRecados.get(indice);
    }

    /**
     * Retorna sempre o próximo recadao do QuadroRecados seguindo a ordem dos índices.<br>
     * Obs.: Não utilize este método dentro de loop infinito.
     * @return Object O próximo recado, no entanto, caso tenha acabado os recados
     * o valor retornado será null
     */
    public Object getNextRecado() {
        return quadroRecados.getNext();
    }

    /**
     * Remove um recado do QuadroRecados de acordo com índice passado por parâmetro.
     * @param indice O recado procurado
     */
    public void removeRecado(int indice) {
        quadroRecados.remove(indice);
    }

    /**
     * Remove um elemento do ListManuseavel de acordo com o elemento passado por parâmetro.
     * @param elementoParaRemover O elemento procurado
     */
    public void removeRecado(Object elementoParaRemover) {
        quadroRecados.remove(elementoParaRemover);
    }

    /**
     * Retorna a quantidade de recados no QuadroRecados.
     * @return int
     */
    public int sizeQuadroRecados() {
        return quadroRecados.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
    @Override
    public void desenha(Graphics2D g2d) {
        if (getEstadoAtual() != null) {
            getEstadoAtual().desenha(g2d);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Atualizavel">
    @Override
    public void atualiza() {
        if (getEstadoAtual() != null) {
            getEstadoAtual().atualiza();
        }
        
        if (entrouEmEspera && tempoEsperaMudarEstado.atingiuMeta()) {
            entrouEmEspera = false;
            
            //Método escolhido: mudarEstado()
            if (indiceEstadoEscolhido == -1 && rotuloEstadoEscolhido == null) {
                mudarEstado();
            }
            
            //Método escolhido: mudarEstadoPara(int)
            if (indiceEstadoEscolhido >= 0 && rotuloEstadoEscolhido == null) {
                mudarEstadoPara(indiceEstadoEscolhido);
            }
            
            //Método escolhido: mudarEstadoPara(Object)
            if (indiceEstadoEscolhido == -1 && rotuloEstadoEscolhido != null) {
                mudarEstadoPara(rotuloEstadoEscolhido);
            }
            
            //Reinicia as auxiliares
            indiceEstadoEscolhido = -1;
            rotuloEstadoEscolhido = null;
        }
    }
    //</editor-fold>   
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: KeyListener">
    @Override
    public void keyTyped(KeyEvent e) {
        invocarKeyTypedList(e);
                
        if (getEstadoAtual() instanceof KeyTyped || getEstadoAtual() instanceof KeyListener) {
            ((KeyTyped)getEstadoAtual()).keyTyped(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        invocarKeyPressedList(e);
                
        if (getEstadoAtual() instanceof KeyPressed || getEstadoAtual() instanceof KeyListener) {
            ((KeyPressed)getEstadoAtual()).keyPressed(e);
        }        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        invocarKeyReleasedList(e);
        
        if (getEstadoAtual() instanceof KeyReleased || getEstadoAtual() instanceof KeyListener) {
            ((KeyReleased)getEstadoAtual()).keyReleased(e);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: MouseListener">
    @Override
    public void mouseClicked(MouseEvent e) {
        invocarMouseClickedList(e);
                
        if (getEstadoAtual() instanceof MouseClicked || getEstadoAtual() instanceof MouseListener) {
            ((MouseClicked)getEstadoAtual()).mouseClicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        invocarMousePressedList(e);
                
        if (getEstadoAtual() instanceof MousePressed || getEstadoAtual() instanceof MouseListener) {
            ((MousePressed)getEstadoAtual()).mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        invocarMouseReleasedList(e);
                
        if (getEstadoAtual() instanceof MouseReleased || getEstadoAtual() instanceof MouseListener) {
            ((MouseReleased)getEstadoAtual()).mouseReleased(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        invocarMouseEnteredList(e);
                
        if (getEstadoAtual() instanceof MouseEntered || getEstadoAtual() instanceof MouseListener) {
            ((MouseEntered)getEstadoAtual()).mouseEntered(e);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        invocarMouseExitedList(e);
        
        if (getEstadoAtual() instanceof MouseExited || getEstadoAtual() instanceof MouseListener) {
            ((MouseExited)getEstadoAtual()).mouseExited(e);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: MouseMotionListener">
    @Override
    public void mouseDragged(MouseEvent e) {
        invocarMouseDraggedList(e);
                
        if (getEstadoAtual() instanceof MouseDragged || getEstadoAtual() instanceof MouseMotionListener) {
            ((MouseDragged)getEstadoAtual()).mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        invocarMouseMovedList(e);
        
        if (getEstadoAtual() instanceof MouseMoved || getEstadoAtual() instanceof MouseMotionListener) {
            ((MouseMoved)getEstadoAtual()).mouseMoved(e);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: MouseWheelListener">
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        invocarMouseWheelMovedList(e);
        
        if (getEstadoAtual() instanceof MouseWheelMoved || getEstadoAtual() instanceof MouseWheelListener) {
            ((MouseWheelMoved)getEstadoAtual()).mouseWheelMoved(e);
        }
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
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Object">
    /**
     * Cria uma String com todos os elementos da MaquinaEstados para debug.
     * @return String
     */
    @Override
    public String toString() {
        String estadosString = "";
        for (int cont = 0; cont < size(); cont++) {
            estadosString += "    [" + cont + "]" + rotuloEstados.get(cont) + "\n";
        }
        
        String recados = "";
        for (int cont = 0; cont < sizeQuadroRecados(); cont++) {
            recados += "    [" + cont + "]" + quadroRecados.get(cont) + "\n";
        }
        
        return "MaquinaEstados{\n" + 
                "  estadoAtual: " + rotuloEstados.getEstadoAtual() + " - em execução: " + 
                    getTempoDecorridoExecucaoEstadoAtual() + "ms\n" +
                "  todos estados: \n" + estadosString +
                "  todos recados: \n" + recados +
                "}";
    }
    //</editor-fold>
    
}
