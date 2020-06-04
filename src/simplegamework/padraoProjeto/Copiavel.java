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

/**
 * Existem objetos que são identicos e que precisam estar em lugares distintos
 * na tela ou fazerem movimentos distintos uns dos outros, sendo assim, essa
 * interface garante que um objeto possa ser copiado mas que serão distintos
 * uns dos outros.<br>
 * Obs.: Muitas das vezes o método equals() pode retornar true, porém mesmo assim
 * os objetos são distintos porque foram feitas duas intâncias distintas.
 * <br><br><small>Created on : 06/08/2015, 01:09:54</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 * @param <C> A classe que será objeto de cópia
 */
public interface Copiavel<C> {
    
    /**
     * Retorna um novo objeto porém sendo uma cópia desse. Muito útil nos casos
     * em que se precisa ter muitas elementos iguais na tela mas com Coordenadas
     * ou movimentos distintos uns dos outros.
     * @return C
     */
    public C getCopy();
    
}
