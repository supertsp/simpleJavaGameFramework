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
 * Todo objeto que será um atributo de um Estatavel deverá implementar essa 
 * interface para que possa ser reiniciado e não perder desempenho do jogo
 * efetuando vários news a cada vez que os estados são mudados.
 * <br>
 * <br><small>Created on : 29/09/2015, 21:21:28</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public interface AtributoEstatavel {
    
    /**
     * Reinicia todos atributos do atributo de estado para que todas as vezes em
     * que os métodos Estatavel.aoIniciarEstado() e Estatavel.aoEncerrarEstado()
     * forem chamados não seja necessário dar new em todos atributos, pois isso
     * deixaria o jogo mais lento. Sendo assim um Estatavel chama seu método
     * reiniciarTodosAtributos() que por sua vez chama reiniciarTodosAtributos()
     * de cada um de seus atributos.
     */
    public void reiniciarTodosAtributos();
    
}
