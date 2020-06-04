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

package simplegamework.padraoProjeto;

/**
 * Provê o padrão mínimo para implementar um ArrayList dentro de uma classe qualquer.
 * <br><br><small>Created on : 19/06/2015, 10:58:46</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 * @param <T> O Tipo de objeto a ser trabalhado
 */
public interface ListManuseavel<T> {
    
    /**
     * Adiciona um novo elemento ao ListManuseavel.
     * @param novoElemento O novo elemento a ser adicionado
     */
    public void add(T novoElemento);
    
    /**
     * Adiciona vários elementos ao mesmo tempo dentro do ListManuseavel.<br>
     * Ex.: objeto.add(elemento1, elemento2, ..., elementoN);
     * @param variosElementos Os novos elementos
     */
    public void add(T... variosElementos);
    
    /**
     * Retorna um elemento do ListManuseavel de acordo com o índice passado por parâmetro.
     * @param indice O elemento procurado
     * @return Retorna o elemento procurado caso ele tenha sido encontrado, caso
     * contrário será retornado null
     */
    public T get(int indice);
    
    /**
     * Retorna sempre o próximo elemento de ListManuseavel seguindo a ordem dos índices.<br>
     * Obs.: Não utilize este método dentro de loop infinito.
     * @return O próximo elemento, no entanto, caso tenha acabado os elementos
     * o valor retornado será null
     */
    public T getNext();
    
    /**
     * Remove um elemento do ListManuseavel de acordo com índice passado por parâmetro.
     * @param indice O elemento procurado
     */
    public void remove(int indice);
    
    /**
     * Remove um elemento do ListManuseavel de acordo com o elemento passado por parâmetro.
     * @param elementoParaRemover O elemento procurado
     */
    public void remove(T elementoParaRemover);
    
    /**
     * Retorna a quantidade de elementos no ListManuseavel.
     * @return int
     */
    public int size();
    
}
