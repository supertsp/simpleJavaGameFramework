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
 * Todo objeto que precisa ser animado precisa implementar essa interface.
 * <br><br><small>Created on : 10/07/2015, 00:21:01</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public interface Animavel {
    
    /**
     * Inicializa a animação.
     */
    public void play();
    
    /**
     * Inicializa a animação, porém a executa apenas uma vez.
     */
    public void playOnce();
    
    /**
     * Inicializa a animação a partir de um índice passado.
     * @param indiceInicial O índice da imagem para iniciar a animação
     */
    public void play(int indiceInicial);
    
    /**
     * Inicializa a animação a partir de um índice passado, porém a executa 
     * apenas uma vez.
     * @param indiceInicial O índice da imagem para iniciar a animação
     */
    public void playOnce(int indiceInicial);
    
    /**
     * Pausa a execução da animação. Para retomar a animação a partir do último
     * tile parado, dê play() novamente.
     */
    public void pause();
    
    /**
     * Para a execução da animação. Para reiniciar a animação, dê play() novamente.
     */
    public void stop();
    
}
