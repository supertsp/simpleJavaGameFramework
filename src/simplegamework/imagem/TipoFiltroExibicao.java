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
 * Todos os tipos de filtros que podem ser aplicados numa imagem na hora de sua visualização.
 * <br><br><small>Created on : 20/05/2015, 02:30:56</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public enum TipoFiltroExibicao {
    
    BILINEAR, BICUBIC, NEAREST_NEIGHBOR;
    
    /**
     * Transforma uma String em seu respectivo valor de enum.
     * @param tipoFiltroExibicao A String que contém o texto com o valor do enum
     * @return TipoFiltroExibicao
     */
    public static TipoFiltroExibicao converter(String tipoFiltroExibicao){
        if (tipoFiltroExibicao.equals("BILINEAR")) {
            return BILINEAR;
        }
                
        if (tipoFiltroExibicao.equals("BICUBIC")) {
            return BICUBIC;
        }
        
        if (tipoFiltroExibicao.equals("NEAREST_NEIGHBOR")) {
            return NEAREST_NEIGHBOR;
        }
        
        //ERRO
        return null;
    }
    
}
