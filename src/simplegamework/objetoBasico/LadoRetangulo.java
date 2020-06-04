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

package simplegamework.objetoBasico;

/**
 * Enum que marca os quatro lados de um retângulo qualquer e suas diagonais.<br>
 * Ex.:
 * <pre>
 * (1)---(C)---(2)
 *  |           |
 * (E)         (D)
 *  |           |
 * (3)---(B)---(4) 
 * </pre>
 * <ul>
 * <li><b>1: </b>LadoRetangulo.ESQUERDA_CIMA</li>
 * <li><b>2: </b>LadoRetangulo.DIREITA_CIMA</li>
 * <li><b>3: </b>LadoRetangulo.ESQUERDA_BAIXO</li>
 * <li><b>4: </b>LadoRetangulo.DIREITA_BAIXO</li>
 * <li><b>E: </b>LadoRetangulo.ESQUERDA</li>
 * <li><b>D: </b>LadoRetangulo.DIREITA</li>
 * <li><b>C: </b>LadoRetangulo.CIMA</li>
 * <li><b>B: </b>LadoRetangulo.BAIXO</li>
 * </ul>
 * <br><br><small>Created on : 04/05/2015, 02:00:18</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public enum LadoRetangulo {
    
    ESQUERDA, DIREITA, CIMA, BAIXO,
    ESQUERDA_CIMA, DIREITA_CIMA,
    ESQUERDA_BAIXO, DIREITA_BAIXO;
    
    public static LadoRetangulo converter(String ladoRetangulo){
        //LADOS BÁSICOS
        if (ladoRetangulo.equals("ESQUERDA")) {
            return ESQUERDA;
        }
        
        if (ladoRetangulo.equals("DIREITA")) {
            return DIREITA;
        }
        
        if (ladoRetangulo.equals("CIMA")) {
            return CIMA;
        }
        
        if (ladoRetangulo.equals("BAIXO")) {
            return BAIXO;
        }
        
        //DIAGONAIS
        if (ladoRetangulo.equals("ESQUERDA_CIMA")) {
            return ESQUERDA_CIMA;
        }
        
        if (ladoRetangulo.equals("DIREITA_CIMA")) {
            return DIREITA_CIMA;
        }
        
        if (ladoRetangulo.equals("ESQUERDA_BAIXO")) {
            return ESQUERDA_BAIXO;
        }
        
        if (ladoRetangulo.equals("DIREITA_BAIXO")) {
            return DIREITA_BAIXO;
        }
        
        //ERRO
        return null;
    }
    
}
