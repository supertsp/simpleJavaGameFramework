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
 * Todo objeto que precisa tocar algum som ou música precisa implementar essa 
 * interface.
 * <br><br><small>Created on : 10/07/2015, 00:21:01</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public interface Tocavel {
    
    /**
     * Inicializa o áudio, porém ele será executado apenas uma vez.
     */
    public void play();
    
    /**
     * Pausa a execução do áudio. Para retomar o áudio a partir do ponto de parada
     * dê play() novamente.
     */
    public void pause();
    
    /**
     * Para a execução do áudio. Para reiniciar o áudio, dê play() novamente.
     */
    public void stop();
    
    /**
     * Utilizado para fechar o recurso de áudio.
     */
    public void close();
    
    /**
     * Tem o mesmo efeito do play(), no entanto fica repetindo de acordo com o
     * valor de repetições passado por parâmetro.
     * @param qtdVezes O número de repetições. Para marcar repetições infinitas
     * passe o valor -1.
     */
    public void replay(int qtdVezes);
    
}
