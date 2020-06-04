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

import java.util.ArrayList;

/**
 * Fila de tamanho dinâmico com base em ArrayList.
 * <br><br><small>Created on : 03/05/2015, 23:18:09</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 * @param <T> O tipo de fila a ser criada
 */
public class Fila<T> {
    
    protected ArrayList<T> fila;
    protected int tamanhoDeterminado;
    
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    /**
     * Construtor para fila de tamanho dinâmico.
     */
    public Fila() {
        fila = new ArrayList<>();
    }
    
    /**
     * Construtor para fila de tamanho fixo.
     * @param tamanho O tamanho fixo da fila
     */
    public Fila(int tamanho) {
        tamanhoDeterminado = tamanho;
        fila = new ArrayList<>(tamanho);
    }
    //</editor-fold>
    
    /**
     * Adiciona um elemento específico ao final da fila.
     * Caso a fila seja de tamanho fixo nada mais será adicionado.
     * @param elemento Elemento a ser adicionado
     */
    public void add(T elemento){
        if (tamanhoDeterminado != 0 && size() < tamanhoDeterminado) {
            fila.add(elemento);
        }
        if (tamanhoDeterminado == 0) {
            fila.add(elemento);
        }
    }
    
    /**
     * Retorna um elemento da fila baseado em seu índice.
     * @param indice O valor do índice do elemento procurado
     * @return O elemento procurado.
     */
    public T get(int indice){
        return fila.get(indice);
    }
    
    /**
     * Exclui o primeiro elemento da fila, ou seja, o de índice 0.
     * @return O elemento excluído
     */
    public T remove(){
        return fila.remove(0);
    }
    
    /**
     * Retorna o tamanho da fila.
     * @return O tamanho da fila
     */
    public int size(){
        return fila.size();
    }
    
    /**
     * Método para descrobir se a fila está vazia.
     * @return true : Caso a fila esteja vazia <br>
     * false: Caso haja algume elemento na fila
     */
    public boolean vazia(){
        return fila.isEmpty();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Object">
    /**
      * Cria uma String com todos os elementos da fila.
      * @return String
      */
    @Override
    public String toString() {
        String txt = "Fila{\n";
        for (int indice = 0; indice < size(); indice++) {
            txt += "   [" + indice + "]: " + get(indice) + "\n";
        }
        txt += "}";
        return txt;
    }
    //</editor-fold>
    
}
