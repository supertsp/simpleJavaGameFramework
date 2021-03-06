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

import java.awt.event.MouseWheelEvent;

/**
 * Todo objeto que precisar escutar um evento de mouse para quando a roda é 
 * girada deverá implementar essa interface.
 * <br><br><small>Created on : 14/07/2015, 23:00:49</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public interface MouseWheelMoved {
    
    /**
     * Um evento MouseWheelMoved é disparado quando a roda é girad.
     * @param e O objeto que receberá o evento.
     */
    public void mouseWheelMoved(MouseWheelEvent e);
    
}
