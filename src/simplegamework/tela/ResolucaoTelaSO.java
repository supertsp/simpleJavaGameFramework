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

package simplegamework.tela;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Facilitador das propriedades da tela do Sistema Operacional.
 * <br><br><small>Created on : 04/05/2015, 23:14:50</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class ResolucaoTelaSO {
    
    private static final Toolkit tk = Toolkit.getDefaultToolkit();  
    private static final Dimension d = tk.getScreenSize();  
    
    /**
     * Retorna o comprimento da rezolução de tela atual.
     * @return int
     */
    public static int getComprimento(){
        return d.width;
    }
    
    /**
     * Retorna a altura da rezolução de tela atual.
     * @return int
     */
    public static int getAltura(){
        return d.height;
    }
    
    /**
     * Retorna a proporção da rezolução da tela atual.
     * @return double
     */
    public static double getProporcao(){
        return (double)d.width / (double)d.height;
    }
    
}
