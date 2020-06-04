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
 * Os tipos de movimentos mais básicos estão neste enum.
 * <br><br><small>Created on : 01/05/2015, 22:18:55</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public enum TipoMovimento{
    
    NENHUM, PARADO,
    ESQUERDA, DIREITA, CIMA, BAIXO,
    ESQUERDA_CIMA, DIREITA_CIMA,
    ESQUERDA_BAIXO, DIREITA_BAIXO,
    SENOIDAL_X, COSSENOIDE_X, SENOIDAL_Y, COSSENOIDE_Y,
    SENOIDAL_X_NEGATIVO, COSSENOIDE_X_NEGATIVO, SENOIDAL_Y_NEGATIVO, COSSENOIDE_Y_NEGATIVO,
    QUADRADO_Y, QUADRADO_X,
    SEGUNDO_GRAU_X, SEGUNDO_GRAU_Y,
    SEGUNDO_GRAU_X_NEGATIVO, SEGUNDO_GRAU_Y_NEGATIVO;
    
    /**
     * Transforma uma String em seu respectivo valor de enum.
     * @param tipoMovimento A String que contém o texto com o valor do enum
     * @return TipoMovimento
     */
    public static TipoMovimento converter(String tipoMovimento){
        //NENHUM
        if (tipoMovimento.equals("NENHUM")) {
            return NENHUM;
        }
                
        if (tipoMovimento.equals("PARADO")) {
            return PARADO;
        }
        
        //DIREÇÕES BÁSICAS
        if (tipoMovimento.equals("ESQUERDA")) {
            return ESQUERDA;
        }
        
        if (tipoMovimento.equals("DIREITA")) {
            return DIREITA;
        }
        
        if (tipoMovimento.equals("CIMA")) {
            return CIMA;
        }
        
        if (tipoMovimento.equals("BAIXO")) {
            return BAIXO;
        }
        
        //DIAGONAIS
        if (tipoMovimento.equals("ESQUERDA_CIMA")) {
            return ESQUERDA_CIMA;
        }
        
        if (tipoMovimento.equals("DIREITA_CIMA")) {
            return DIREITA_CIMA;
        }
        
        if (tipoMovimento.equals("ESQUERDA_BAIXO")) {
            return ESQUERDA_BAIXO;
        }
        
        if (tipoMovimento.equals("DIREITA_BAIXO")) {
            return DIREITA_BAIXO;
        }
        
        //SENOIDES E COSSEINOIDES POSITIVAS
        if (tipoMovimento.equals("SENOIDAL_X")) {
            return SENOIDAL_X;
        }
        
        if (tipoMovimento.equals("COSSENOIDE_X")) {
            return COSSENOIDE_X;
        }
        
        if (tipoMovimento.equals("SENOIDAL_Y")) {
            return SENOIDAL_Y;
        }
        
        if (tipoMovimento.equals("COSSENOIDE_Y")) {
            return COSSENOIDE_Y;
        }
        
        //SENOIDES E COSSEINOIDES NEGATIVAS
        if (tipoMovimento.equals("SENOIDAL_X_NEGATIVO")) {
            return SENOIDAL_X_NEGATIVO;
        }
        
        if (tipoMovimento.equals("COSSENOIDE_X_NEGATIVO")) {
            return COSSENOIDE_X_NEGATIVO;
        }
        
        if (tipoMovimento.equals("SENOIDAL_Y_NEGATIVO")) {
            return SENOIDAL_Y_NEGATIVO;
        }
        
        if (tipoMovimento.equals("COSSENOIDE_Y_NEGATIVO")) {
            return COSSENOIDE_Y_NEGATIVO;
        }
        
        //QUADRADOS
        if (tipoMovimento.equals("QUADRADO_Y")) {
            return QUADRADO_Y;
        }
        
        if (tipoMovimento.equals("QUADRADO_X")) {
            return QUADRADO_X;
        }
        
        //SEGUNDO GRAU
        if (tipoMovimento.equals("SEGUNDO_GRAU_X")) {
            return SEGUNDO_GRAU_X;
        }
        
        if (tipoMovimento.equals("SEGUNDO_GRAU_Y")) {
            return SEGUNDO_GRAU_Y;
        }
        
        if (tipoMovimento.equals("SEGUNDO_GRAU_X_NEGATIVO")) {
            return SEGUNDO_GRAU_X_NEGATIVO;
        }
        
        if (tipoMovimento.equals("SEGUNDO_GRAU_Y_NEGATIVO")) {
            return SEGUNDO_GRAU_Y_NEGATIVO;
        }
        
        //ERRO
        return null;
    }
    
}
