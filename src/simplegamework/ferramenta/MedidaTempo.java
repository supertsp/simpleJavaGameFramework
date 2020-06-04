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

package simplegamework.ferramenta;

/**
 * Este enum é usado em conjunto com a classe TempoExecucao para determinar a
 * medida de tempo a ser trabalhada.
 * <br><br><small>Created on : 12/07/2015, 10:09:40</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public enum MedidaTempo {
    
    NANOSSEGUNDOS, MILISSEGUNDOS, SEGUNDOS;
    
    /**
     * Transforma uma String em seu respectivo valor de enum.
     * @param medidaTempo A String que contém o texto com o valor do enum
     * @return MedidaTempo
     */
    public static MedidaTempo converter(String medidaTempo){
        if (medidaTempo.equals("NANOSSEGUNDOS")) {
            return NANOSSEGUNDOS;
        }
                
        if (medidaTempo.equals("MILISSEGUNDOS")) {
            return MILISSEGUNDOS;
        }
        
        if (medidaTempo.equals("SEGUNDOS")) {
            return SEGUNDOS;
        }
        
        //ERRO
        return null;
    }
    
}
