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

import simplegamework.objetoBasico.CaixaColisao;

/**
 * Todo objeto que contém uma CaixaColisaoList deverá implementar essa classe.
 * <br><br><small>Created on : 25/06/2015, 11:20:15</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public interface CaixaColisaoListManuseavel {
    
    /**
     * Adiciona um novo elemento ao List de CaixaColisao.
     * @param novoElemento O novo elemento a ser adicionado
     */
    public void addCaixaColisao(CaixaColisao novoElemento);
    
    /**
     * Adiciona vários elementos ao mesmo tempo dentro do List de CaixaColisao.<br>
     * Ex.: classe.add(elemento1, elemento2, ..., elementoN);
     * @param variosElementos Os novos elementos
     */
    public void addCaixaColisao(CaixaColisao... variosElementos);
    
    /**
     * Retorna um elemento do List de CaixaColisao de acordo com o índice passado
     * por parâmetro.
     * @param indice O elemento procurado
     * @return Caso tenha encontrado o elemento, ele será retornado
     */
    public CaixaColisao getCaixaColisao(int indice);
    
    /**
     * Retorna sempre o próximo elemento de List de CaixaColisao seguindo a 
     * ordem dos índices.
     * @return O próximo elemento, no entanto, caso tenha acabado os elementos
     * o valor retornado será null
     */
    public CaixaColisao getNextCaixaColisao();
    
    /**
     * Retorna um elemento de acordo com seu rótulo passado por parâmetro.
     * @param rotulo O identificador procurado que localiza o elemento
     * @return CaixaColisao
     */
    public CaixaColisao getCaixaColisao(Object rotulo);
    
    /**
     * Remove um elemento do List de CaixaColisao de acordo com índice passado 
     * por parâmetro.
     * @param indice O elemento procurado
     */
    public void removeCaixaColisao(int indice);
    
    /**
     * Remove um elemento do List de CaixaColisao de acordo com o elemento 
     * passado por parâmetro.
     * @param elementoParaRemover O elemento procurado
     */
    public void removeCaixaColisao(CaixaColisao elementoParaRemover);
    
    /**
     * Remove um elemento de acordo com seu rótulo passado por parâmetro.
     * @param rotulo O identificador procurado que localiza o elemento
     */
    public void removeCaixaColisao(Object rotulo);
    
    /**
     * Retorna a quantidade de elementos no List de CaixaColisao.
     * @return int
     */
    public int sizeCaixaColisaoList();
    
     /**
     * Ativa a exibição da cor da borda de todas CaixaColisao dentro do List.
     */
    public void ativarCorBordaCaixaColisaoList();
    
    /**
     * Desativa a exibição da cor da borda de todas CaixaColisao dentro do List.
     */
    public void desativarCorBordaCaixaColisaoList();
    
}
