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

import java.awt.Graphics2D;

/**
 * Quando é necessário adicionar várias ações dentro do método desenha(), é
 * necessário implementar essa interface.
 * <br><br><small>Created on : 19/06/2015, 14:49:51</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public interface DesenhavelListManuseavel{
    
    /**
     * Invoca todos os métodos desenha(Graphics2D g) dentro do DesenhavelListManuseavel
     * @param g2d O objeto para desenhar na tela
     */
    public void invocarDesenhavelList(Graphics2D g2d);
    
    /**
     * Adiciona um novo elemento ao DesenhavelListManuseavel.
     * @param novoElemento O novo elemento a ser adicionado
     */
    public void addDesenhavel(Desenhavel novoElemento);
    
    /**
     * Adiciona vários elementos ao mesmo tempo dentro do DesenhavelListManuseavel.<br>
     * Ex.: classe.add(elemento1, elemento2, ..., elementoN);
     * @param variosElementos Os novos elementos
     */
    public void addDesenhavel(Desenhavel... variosElementos);
    
    /**
     * Retorna um elemento do DesenhavelListManuseavel de acordo com o índice passado por
 parâmetro.
     * @param indice O elemento procurado
     * @return Caso tenha encontrado o elemento, ele será retornado
     */
    public Desenhavel getDesenhavel(int indice);
    
    /**
     * Retorna sempre o próximo elemento de DesenhavelListManuseavel seguindo a ordem dos 
 índices.
     * @return O próximo elemento, no entanto, caso tenha acabado os elementos
     * o valor retornado será null
     */
    public Desenhavel getNextDesenhavel();
    
    /**
     * Remove um elemento do DesenhavelListManuseavel de acordo com índice passado por 
 parâmetro.
     * @param indice O elemento procurado
     */
    public void removeDesenhavel(int indice);
    
    /**
     * Remove um elemento do DesenhavelListManuseavel de acordo com o elemento passado por 
 parâmetro.
     * @param elementoParaRemover O elemento procurado
     */
    public void removeDesenhavel(Desenhavel elementoParaRemover);
    
    /**
     * Retorna a quantidade de elementos no DesenhavelListManuseavel.
     * @return int
     */
    public int sizeDesenhavelList();
    
}
