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
 * Pilha de tamanho dinâmico com base em ArrayList.
 * <br><br><small>Created on : 04/05/2015, 01:43:00</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 * @param <T> O tipo de pilha a ser criada
 */
public class Pilha<T> {
    
    protected ArrayList<T> pilha;
    protected int tamanhoDeterminado;
    
    /**
     * Construtor para pilha de tamanho dinâmico.
     */
    public Pilha() {
        pilha = new ArrayList<>();
    }
    
    /**
     * Construtor para pilha de tamanho fixo.
     * @param tamanho O tamanho fixo da pilha
     */
    public Pilha(int tamanho) {
        tamanhoDeterminado = tamanho;
        pilha = new ArrayList<>(tamanho);
    }
    
    /**
     * Adiciona um elemento específico ao final da pilha.
     * Caso a pilha seja de tamanho fixo nada mais será adicionado.
     * @param elemento Elemento a ser adicionado
     */
    public void add(T elemento){
        if (tamanhoDeterminado != 0 && size() < tamanhoDeterminado) {
            pilha.add(elemento);
        }
        if (tamanhoDeterminado == 0) {
            pilha.add(elemento);
        }
    }
    
    /**
     * Retorna um elemento da pilha baseada em seu índice.
     * @param indice O valor do índice do elemento procurado
     * @return O elemento procurado.
     */
    public T get(int indice){
        return pilha.get(indice);
    }
    
    /**
     * Exclui o último elemento da pilha, ou seja, o de índice maior.
     * @return O elemento excluído
     */
    public T remove(){
        return pilha.remove(size() - 1);
    }
    
    /**
     * Retorna o tamanho da pilha.
     * @return O tamanho da pilha
     */
    public int size(){
        return pilha.size();
    }
    
    /**
     * Método para descrobir se a pilha está vazia.
     * @return true : Caso a pilha esteja vazia <br>
     * false: Caso haja algume elemento na pilha
     */
    public boolean vazia(){
        return pilha.isEmpty();
    }
    
    /**
      * Cria uma String com todos os elementos da pilha.
      * @return A String com todos os elementos da pilha
      */
    @Override
    public String toString() {
        String txt = "Pilha{\n";
        for (int indice = 0; indice < size(); indice++) {
            txt += "   [" + indice + "]: " + get(indice) + "\n";
        }
        txt += "}";
        return txt;
    }
    
}
