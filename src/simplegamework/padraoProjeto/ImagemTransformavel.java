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

import simplegamework.imagem.TipoEspelhamento;
import simplegamework.objetoBasico.Coordenadas;

/**
 * Todo imagem que precisa de métodos básicos para controlar suas transformações
 * deverá implementar essa interface.<br>
 * Obs.: Para redimensionar uma imagem, implemente a interface Dimensionavel.
 * <br><br><small>Created on : 22/06/2015, 00:08:37</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public interface ImagemTransformavel {
    
    /**
     * Faz o espelhamento de toda imagem de acordo com TipoEspelhamento.
     * @param tipoEspelhamento TipoEspelhamento.HORIZONTAL ou TipoEspelhamento.VERTICAL
     */
    public void espelhar(TipoEspelhamento tipoEspelhamento);
    
    /**
     * Rotaciona toda imagem. <br>
     * Obs.: O pivô de rotação será o default, ou seja, a diagonal esquerda superior.
     * @param graus Valor em graus de quanto a imagem será girada. Ex.: 95,5° = 95.5
     */
    public void rotacionar(double graus);
    
    /**
     * Rotaciona toda imagem. <br>
     * Obs.: Após a rotação, o pivô de rotação volta à sua coordenada anterior
     * @param graus Valor em graus de quanto a imagem será girada. Ex.: 95,5° = 95.5
     * @param pivoRotacao Coordenadas do novo pivô de rotação
     */
    public void rotacionar(double graus, Coordenadas pivoRotacao);
    
}
