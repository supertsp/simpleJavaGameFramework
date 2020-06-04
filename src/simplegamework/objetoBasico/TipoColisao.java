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
 * Enum indicador dos possíveis tipos de colisões que FormaBasica possa sofrer.
 * <br><br><small>Created on : 01/05/2015, 23:54:24</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public enum TipoColisao {
    
    EXTERNA, INTERNA;
    
    /**
     * Transforma uma String em seu respectivo valor de enum.
     * @param tipoColisao A String que contém o texto com o valor do enum
     * @return TipoColisao
     */
    public static TipoColisao converter(String tipoColisao){
        //NENHUM
        if (tipoColisao.equals("EXTERNA")) {
            return EXTERNA;
        }
                
        if (tipoColisao.equals("INTERNA")) {
            return INTERNA;
        }
        
        //ERRO
        return null;
    }
    
}
