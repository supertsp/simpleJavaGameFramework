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
 * Provê o padrão mínimo para implementar uma Matriz bidimensional de array ou
 * ArrayList dentro de uma classe qualquer.
 * <br><br><small>Created on : 22/06/2015, 12:55:45</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 * @param <M> O tipo de matriz a ser manuseada
 */
public interface MatrizListManuseavel<M> {
    
    /**
     * Adiciona um novo elemento à MatrizListManuseavel.
     * @param linha O valor da linha
     * @param coluna O valor da coluna
     * @param novoElemento O novo elemento a ser adicionado
     */
    public void add(int linha, int coluna, M novoElemento);
    
    /**
     * Adiciona vários elementos ao mesmo tempo dentro da MatrizListManuseavel a partir
 da linha e coluna passada. Caso os outros elementos da MatrizListManuseavel já
 estejam preenchidos, eles serão sobrescritos.<br>
     * Ex.: classe.add(linha, coluna, elemento1, elemento2, ..., elementoN);
     * @param linhaInicial A linha inicial de onde serão colocados os novos elementos
     * @param colunaInicial A coluna inicial de onde serão colocados os novos elementos
     * @param variosElementos Os novos elementos a serem adicionados
     */
    public void add(int linhaInicial, int colunaInicial, M... variosElementos);
    
    /**
     * Adiciona sempre o próximo elemento da MatrizListManuseavel seguindo a ordem das
 linhas e colunas até chegar à última linha e coluna disponível. Quando
     * não houverem mais linhas e colunas disponíveis será retornado false.
     * @param novoElemento O novo elemento a ser inserido
     * @return boolean
     */
    public boolean addNext(M novoElemento);
    
    /**
     * Retorna um elemento da MatrizListManuseavel de acordo com a linha e coluna passadas
 por parâmetro.
     * @param linha A linha do elemento procurado
     * @param coluna A coluna do elemento procurado
     * @return Caso tenha encontrado o elemento, ele será retornado
     */
    public M get(int linha,int coluna);
    
    /**
     * Retorna sempre o próximo elemento da MatizList seguindo a ordem das linhas
     * e colunas até chegar à última linha e coluna disponível.<br>
     * Obs.: Não utilize este método dentro de loop infinito.
     * @return O próximo elemento, no entanto, caso tenha acabado os elementos
     * o valor retornado será null
     */
    public M getNext();
    
    /**
     * Remove um elemento da MatrizListManuseavel de acordo com os valores da linha e 
 coluna procurada.<br>
     * Obs.: O elemento removido passa a valer null.
     * @param linha A linha do elemento procurado
     * @param coluna A coluna do elemento procurado
     */
    public void remove(int linha, int coluna);
    
    /**
     * Remove um elemento da MatrizListManuseavel de acordo com o elemento passado por 
 parâmetro. O elemento a ser removido será sempre o primeiro encontrado.<br>
     * Obs.: O elemento removido passa a valer null.
     * @param elementoParaRemover O elemento procurado
     */
    public void remove(M elementoParaRemover);
    
    /**
     * Remove sempre o próximo elemento da MatizList seguindo a ordem das linhas
     * e colunas até chegar à última linha e coluna disponível. Quando não
     * houverem mais linhas e colunas para remover o valor retornado será false.<br>
     * Obs.: O elemento removido passa a valer null.
     * @return boolean
     */
    public boolean removeNext();
    
    /**
     * Retorna a quantidade de elementos na MatrizListManuseavel, ou seja, linhas X colunas.
     * @return int
     */
    public int sizeElements();
    
    /**
     * Retorna a quantidade de linhas na MatrizListManuseavel
     * @return int
     */
    public int sizeRows();
    
    /**
     * Retorna a quantidade de colunas na MatrizListManuseavel.
     * @return int
     */
    public int sizeColumns();
    
}
