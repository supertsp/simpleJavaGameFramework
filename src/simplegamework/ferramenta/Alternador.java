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

package simplegamework.ferramenta;

import simplegamework.padraoProjeto.ListManuseavel;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Alternador de estados adaptável a qualquer classe, baseado num ArrayList.<br>
 * Obs.: Utilize enuns ao invés de constantes. No entanto essa classe funciona
 * com constantes ou qualquer outro tipo de objeto.
 * <br><br><small>Created on : 14/06/2015, 17:47:13</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 * @param <E> O tipo de objeto a ser trabalhado
 */
public class Alternador<E> implements ListManuseavel<E>{
    
    private ArrayList<E> valores;
    
    //Auxiliares
    private int indiceGetAtual;
    private int indiceEstadoAtual;
    
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    /**
     * Construtor padrão de classe.<br>
     * Obs.: Caso o método getEstadoAtual() seja chamado logo em seguida será
     * diparado um erro por não haver estado algum inserido.
     */
    public Alternador(){
        valores = new ArrayList<>();
    }
    
    /**
     * Construtor principal que adiciona um elemento (objeto) inicial que
     * representa o primeiro estado a ser trabalhado.<br>
     * Obs.: O primeiro estado inserido será estado atual caso seja chamado o 
     * método getEstadoAtual() logo após o construtor.
     * @param primeiroEstado Objeto do mesmo tipo passado na declaração do objeto. Ex.:
     * Alternador&lsaquo;Maquina&rsaquo; x = new Alternador(Maquina.LIGADA);
     */
    public Alternador(E primeiroEstado){
        valores = new ArrayList<>();
        valores.add(primeiroEstado);
    }
    
    /**
     * Construtor para adicionar vários elementos (objetos) ao mesmo tempo. Para
     * usar este construtor é preciso passar no mínimo mais de 3 estados.<br>
     * Ex.: Alternador&lsaquo;Maquina&rsaquo; x = new Alternador(Maquina.LIGADA, 
     * Maquina.DESLIGADA, Maquina.ESPERA);<br>
     * Obs.: O primeiro estado inserido será estado atual caso seja chamado o 
     * método getEstadoAtual() logo após o construtor.
     * @param variosEstados Pode-se passar quantos parâmetros forem necessários
     * para inicializar o Alternador
     */
    public Alternador(E... variosEstados){
        valores = new ArrayList<>();
        add(variosEstados);        
    }
    //</editor-fold>
    
    /**
     * Adiciona um estado de acordo com o tipo do objeto passado.
     * @param estado Objeto do mesmo tipo passado na declaração do objeto. Ex.: <br>
     * Alternador&lsaquo;Maquina&rsaquo; x = new Alternador(Maquina.LIGADA, Maquina.DESLIGADA);<br>
     * x.add(Maquina.SOBRECARREGADA);
     */
    @Override
    public void add(E estado){
        valores.add(estado);
    }
    
    /**
     * Adiciona vários estados ao mesmo tempo.<br>
     * Ex.: objeto.add(elemento1, elemento2, ..., elementoN);
     */
    @Override
    public void add(E... variosElementos) {
        valores.addAll(Arrays.asList(variosElementos));
    }
    
    /**
     * Retorna o estado atual selecionado, começando do primeiro até o último adicionado.
     * @return estado
     */
    @Override
    public E getNext() {        
        if (size() > 0) {
            E e = get(indiceGetAtual);
            indiceGetAtual++;

            if (indiceGetAtual >= size()) {
                indiceGetAtual = 0;
                return null;
            }

            return e;
        }
        else{
            return null;
        }
    }
    
    /**
     * Retorna um estado de acordo com o valor do índice passado por parâmetro.
     * @param indice O estado procurado
     * @return E
     */
    @Override
    public E get(int indice){
        if (size() > 0) {
            return valores.get(indice);
        }
        else{
            return null;
        }
    }
    
    /**
     * Retorna o estado atual selecionado previamente.
     * @return E
     */
    public E getEstadoAtual(){
        if (size() > 0) {
            return get(indiceEstadoAtual);
        }
        else{
            return null;
        }
    }
    
    /**
     * Retorna o índice do estado atual.
     * @return int
     */
    public int getIndiceEstadoAtual(){
        return indiceEstadoAtual;
    }
    
    /**
     * Alterna um a um entre os estados adicionados.
     */
    public void mudarEstado(){
        indiceEstadoAtual++;
        
        if (indiceEstadoAtual >= size()) {
            indiceEstadoAtual = 0;
        }
    }
    
    /**
     * Alterna diretamente para o estado passado por parâmetro. 
     * @param estadoSelecionado O estado a ser selecionado
     */
    public void mudarEstadoPara(E estadoSelecionado){
        for (int indice = 0; indice < size(); indice++) {
            if (estadoSelecionado == get(indice)) {
                indiceEstadoAtual = indice;
                break;
            }
        }
    }
    
    /**
     * Alterna diretamente para o estado passado por parâmetro. 
     * @param indiceEstado O estado a ser selecionado 
     */
    public void mudarEstadoPara(int indiceEstado){
        indiceEstadoAtual = indiceEstado;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void remove(int indice) {        
        valores.remove(indice);
        mudarEstado();
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void remove(E elementoParaRemover) {
        valores.remove(elementoParaRemover);
        mudarEstado();
    }
    
    /**
     * Retorna a quantidade de estados adicionados.
     * @return int
     */
    @Override
    public int size(){
        return valores.size();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Object">
    /**
     * Cria uma String com todos os elementos do Alternador para debug.
     * @return String
     */
    @Override
    public String toString() {
        String saida = "";
        for (int cont = 0; cont < size(); cont++) {
            if (cont + 1 == size()) {
                saida += get(cont);
            }
            else{
                saida += get(cont) + ", ";
            }
        }
        
        return "Alternador<E>{\n" + 
                "  estadoAtual: " + getEstadoAtual() + "\n" +
                "  todos estados: " + saida + "\n" +
                "}";
    }
    //</editor-fold>
    
}
