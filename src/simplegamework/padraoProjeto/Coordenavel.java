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

import simplegamework.objetoBasico.Coordenadas;

/**
 * Todo objeto que precisar manipular as coordenadas x e y precisa implementar
 * essa interface.
 * <br><br><small>Created on : 30/06/2015, 12:35:12</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public interface Coordenavel {
    
    /**
     * Altera o valor da coordena x.
     * @param x O novo valor
     */
    public void setX(double x);
    
    /**
     * Altera o valor da coordena y.
     * @param y O novo valor
     */
    public void setY(double y);
    
    /**
     * Incrementa o valor da coordenada x de acordo com o parâmetro passado.
     * @param aumentoX O valor do incremento
     */
    public void incrementaX(double aumentoX);
    
    /**
     * Incrementa o valor da coordenada y de acordo com o parâmetro passado.
     * @param aumentoY O valor do incremento
     */
    public void incrementaY(double aumentoY);
        
    /**
     * Altera os valores das coordenadas x e y.
     * @param x O novo valor de x
     * @param y O novo valor de y
     */
    public void setCoordenadas(double x, double y);
    
    /**
     * Altera os valores das coordenadas passando um objeto Coordenadas.
     * @param novasCoordenadas O objeto Coordenadas
     */
    public void setCoordenadas(Coordenadas novasCoordenadas);
    
    /**
     * Incrementa os valores das coordenadas x e y de acordo com os parâmetros 
     * passados.
     * @param aumentoX O valor a ser incrementado em x
     * @param aumentoY O valor a ser incrementado em y
     */
    public void incrementarCoordenadas(double aumentoX, double aumentoY);
    
    /**
     * Retorna o valor da coordenada x.
     * @return double
     */
    public double getX();
    
    /**
     * Retorna a coordenada x como um int.
     * @return int
     */
    public int getIntX();
    
    /**
     * Retorna o valor da coordenada y.
     * @return double
     */
    public double getY();
    
    /**
     * Retorna a coordenada y como um int.
     * @return int
     */
    public int getIntY();
    
    /**
     * Retorna as Coordenadas.
     * @return Coordenadas
     */
    public Coordenadas getCoordenadas();
    
}
