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
 * Quando é necessário adicionar várias ações dentro do método atualiza(), é
 * necessário implementar essa interface.
 * <br><br><small>Created on : 19/06/2015, 14:08:55</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public interface AtualizavelListManuseavel{
    
    /**
     * Invoca todos os métodos atualiza() dentro do AtualizavelListManuseavel
     */
    public void invocarAtualizavelList();
    
    /**
     * Adiciona um novo elemento ao AtualizavelListManuseavel.
     * @param novoElemento O novo elemento a ser adicionado
     */
    public void addAtualizavel(Atualizavel novoElemento);
    
    /**
     * Adiciona vários elementos ao mesmo tempo dentro do AtualizavelListManuseavel.<br>
     * Ex.: classe.add(elemento1, elemento2, ..., elementoN);
     * @param variosElementos Os novos elementos
     */
    public void addAtualizavel(Atualizavel... variosElementos);
    
    /**
     * Retorna um elemento do AtualizavelListManuseavel de acordo com o índice passado por
 parâmetro.
     * @param indice O elemento procurado
     * @return Caso tenha encontrado o elemento, ele será retornado
     */
    public Atualizavel getAtualizavel(int indice);
    
    /**
     * Retorna sempre o próximo elemento de AtualizavelListManuseavel seguindo a ordem dos 
 índices.
     * @return O próximo elemento, no entanto, caso tenha acabado os elementos
     * o valor retornado será null
     */
    public Atualizavel getNextAtualizavel();
    
    /**
     * Remove um elemento do AtualizavelListManuseavel de acordo com índice passado por 
 parâmetro.
     * @param indice O elemento procurado
     */
    public void removeAtualizavel(int indice);
    
    /**
     * Remove um elemento do AtualizavelListManuseavel de acordo com o elemento passado por 
 parâmetro.
     * @param elementoParaRemover O elemento procurado
     */
    public void removeAtualizavel(Atualizavel elementoParaRemover);
    
    /**
     * Retorna a quantidade de elementos no AtualizavelListManuseavel.
     * @return int
     */
    public int sizeAtualizavelList();
    
}
