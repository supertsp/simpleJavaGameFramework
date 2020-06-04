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

package simplegamework.imagem;

/**
 * O enum TipoRolagemInfinita serve para definir como a imagem efetuará o efeito
 * de rolagem infinita.
 * <br>
 * <br><small>Created on : 26/09/2015, 14:47:36</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public enum TipoRolagemInfinita {
    
    NENHUMA, VERTICAL, HORIZONTAL;   

    /**
     * Transforma uma String em seu respectivo valor de enum.
     * @param tipoRolagemInfinita A String que contém o texto com o valor do enum
     * @return TipoRolagemInfinita
     */
    public static TipoRolagemInfinita converter(String tipoRolagemInfinita){
        if (tipoRolagemInfinita.equals("NENHUMA")) {
            return NENHUMA;
        }
                
        if (tipoRolagemInfinita.equals("VERTICAL")) {
            return VERTICAL;
        }
        
        if (tipoRolagemInfinita.equals("HORIZONTAL")) {
            return HORIZONTAL;
        }
        
        //ERRO
        return null;
    }
    
}
