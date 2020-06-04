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
 * Todo objeto que precisa ser um elemento da MaquinaEstados precisa implementar
 * essa interface.<br>
 * É Uma boa prática de programação criar um atributo do tipo MaquinaEstados na
 * classe que implementar essa interface. O intuito disso é permitir que cada
 * estado tenha acesso a MaquinaEstados para que possa haver a  mudança de estado
 * de acordo com a necessidade da programação.<br>
 * Uma outra boa prática também é reiniciar (reset) todos os atributos de classe
 * no método aoIniciarEstado().<br>
 * Atenção: Essa interface é uma subclasse de Desenhavel e Atualizavel.
 * <br><br><small>Created on : 08/08/2015, 11:35:19</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public interface Estatavel extends Desenhavel, Atualizavel{
            
    /**
     * Toda vez que um estado for iniciado esse método será chamado.
     */
    public void aoIniciarEstado();
    
    /**
     * Toda vez que for feita uma troca de estados, esse método é chamado antes
     * da troca ocorrer.
     */
    public void aoEncerrarEstado();
    
    /**
     * Reinicia todos atributos do estado para que todas as vezes em que os métodos
     * aoIniciarEstado() e aoEncerrarEstado() forem chamados não seja necessário
     * dar new em todos atributos, pois isso deixaria o jogo mais lento.
     */
    public void reiniciarTodosAtributos();
    
}
