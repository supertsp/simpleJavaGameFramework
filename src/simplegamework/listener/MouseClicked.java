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

package simplegamework.listener;

import java.awt.event.MouseEvent;

/**
 * Todo objeto que precisar escutar um evento de mouse para quando um botão
 * é clicado deverá implementar essa interface.
 * <br><br><small>Created on : 14/07/2015, 19:37:45</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public interface MouseClicked {
    
    /**
     * Um evento MouseClicked é disparado quando um botão é clicado. A ordem dos
     * botões são:
     * <ul>
     * <li>MouseEvent.BUTTON1: O botão esquerdo</li>
     * <li>MouseEvent.BUTTON3: O botão direito</li>
     * <li>MouseEvent.BUTTON2: O botão do meio</li>
     * </ul>
     * @param e O objeto que receberá o evento.
     */
    public void mouseClicked(MouseEvent e);
    
}
