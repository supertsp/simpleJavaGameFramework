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

import simplegamework.objetoBasico.TipoMovimento;

/**
 * Todo objeto quer será alvo de movimentação na tela precisa implementar essa
 * interface.
 * <br><br><small>Created on : 19/06/2015, 15:09:34</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public interface Movimentavel {
        
    /**
     * Altera o valor da velocidade em x.
     * @param velX O novo valor
     */
    public void setVelX(double velX);
    
    /**
     * Altera o valor da velocidade em y.
     * @param velY O novo valor
     */
    public void setVelY(double velY);
    
    /**
     * Incrementa o valor da velocidade x de acordo com o parâmetro passado.
     * @param aumentoVelX O valor do incremento
     */
    public void incrementaVelX(double aumentoVelX);
    
    /**
     * Incrementa o valor da velocidade y de acordo com o parâmetro passado.
     * @param aumentoVelY O valor do incremento
     */
    public void incrementaVelY(double aumentoVelY);
    
    /**
     * Altera os valores originais de velX e velY para os valores passados por
     * parâmetro.
     * @param velX O novo valor
     * @param velY O novo valor
     */
    public void setVelocidades(double velX, double velY);
    
    /**
     * Incrementa os valores das velocidades de acordo com os parâmetros passados.
     * @param aumentoVelX  O valor a ser incrementado
     * @param aumentoVelY  O valor a ser incrementado
     */
    public void incrementarVelocidades(double aumentoVelX, double aumentoVelY);
    
    /**
     * Retorna a velocidade no eixo x.
     * @return double
     */
    public double getVelX();
    
    /**
     * Retorna a velocidade x como um int.
     * @return int
     */
    public int getIntVelX();
    
    /**
     * Retorna a velocidade no eixo y.
     * @return double
     */
    public double getVelY();
    
    /**
     * Retorna a velocidade no eixo y como um int.
     * @return int
     */
    public int getIntVelY();
        
    /**
     * Realiza um movimento automático com base no parâmetro passado, caso
     * o valor passado seja null não será realizado movimento.<br>
     * Obs.: Todos os tipos de movimentos são realizados com base em velX e velY.
     * @param novoMovimento Enum pré definido com as possibilidades de movimentos
     */
    public void movimentar(TipoMovimento novoMovimento);
    
    /**
     * Realiza um movimento automático com base no parâmetro passado em graus,
     * caso esse valor seja 0 não será realizado movimento.<br>
     * Obs.: Todos os tipos de movimentos são realizados com base em velX e velY.
     * @param direcaoEmGraus A direção do movimento que será realizado
     */
    public void movimentar(double direcaoEmGraus);
    
    /**
     * Realiza um movimento parabólico a partir das Coordenadas iniciais. Caso
     * todos os parâmetros sejam iguais a 0 não será realizado movimento.<br>
     * Obs.: para este movimento velY não tem influência alguma.
     * @param alturaX A altura da curva na coordenada x
     * @param alturaY A altura da curva na coordenada y
     * @param comprimentoX O comprimento da parábola na coordenada x
     * @param comprimentoY O comprimento da parábola na coordenada y
     */
    public void movimentarCurvaQuadratica(
            double alturaX, double alturaY, 
            double comprimentoX, double comprimentoY);
    
    /**
     * Realiza um movimento repetido automático de acordo com os parâmetros passados.
     * @param indiceDeformardorX Valor que deforma o movimento no eixo x,
     * podendo até transformá-lo num movimento circular, do infinito e outros
     * @param indiceDeformardorY Valor que deforma o movimento no eixo y,
     * podendo até transformá-lo num movimento circular, do infinito e outros
     * @param comprimento O comprimento do alcance do movimento
     * @param altura A altura do alcance do movimento
     * @param inverter Inverte os eixos (x e y) para realizar o movimento
     */
    public void movimentarRepetidamente(double indiceDeformardorX, double indiceDeformardorY,
            double comprimento, double altura, boolean inverter);
    
    /**
     * Realiza um movimento de acordo com a função Matemática passada.<br>
     * Toda função Matemática deve seguir o modelo abaixo:<br>
     * <ul>
     * <li>"f(x)= 3x^2 + 0.4"</li>
     * <li>"f(y)= sin(3y^2) - 0.4"</li>
     * </ul>
     * Quando se trabalha com f(x) todas as variáveis devem ser x e o resultado
     * da função será aplicado no eixo y juntamente com a velY. Já se a função for
     * f(y) todas as variáveis deverão ser y e o resultado será aplicado no eixo
     * x juntamente com velX.
     * Abaixo segue uma tabela de símbolos Matemáticos permitidos.<br>
     * <table>
     * <caption>Símbolos e Exemplos</caption>
     * <tr>
     * <th>Símbolo</th>
     * <th>Explicação</th>
     * </tr>
     * <tr>
     * <td>+</td>
     * <td>Adição. Ex.: f(x)= 2 + 2</td>
     * </tr>
     * <tr>
     * <td>-</td>
     * <td>Subtração. Ex.: f(y)= 2 - y</td>
     * </tr>
     * <tr>
     * <td>*</td>
     * <td>Multiplicação. Ex.: f(y)= 2 * 5 - y</td>
     * </tr>
     * <tr>
     * <td>/</td>
     * <td>Divisão. Ex.: f(x)= 25 / 7x</td>
     * </tr>
     * <tr>
     * <td>^</td>
     * <td>Exponenciação. Ex.: f(x)= 2x^2 / x</td>
     * </tr>
     * <tr>
     * <td>%</td>
     * <td>Módulo. Ex.: f(y)= y % 3y</td>
     * </tr>
     * <tr>
     * <td>abs()</td>
     * <td>Valor absoluto. Ex.: f(y)= abs(y)</td>
     * </tr>
     * <tr>
     * <td>acos()</td>
     * <td>Arco cosseno. Ex.: f(x)= 3 + acos(x)</td>
     * </tr>
     * <tr>
     * <td>asin()</td>
     * <td>Arco seno. Ex.: f(x)= asin(x) - 3</td>
     * </tr>
     * <tr>
     * <td>atan()</td>
     * <td>Arco tangente. Ex.: f(x)= atan(2x)</td>
     * </tr>
     * <tr>
     * <td>cbrt()</td>
     * <td>Raiz cúbica. Ex.: f(x)= 1+ cbrt(x)</td>
     * </tr>
     * <tr>
     * <td>ceil()</td>
     * <td>Arredondamento para cima. Ex.: f(x)= ceil(x) + 0.1</td>
     * </tr>
     * <tr>
     * <td>cos()</td>
     * <td>Cosseno. Ex.: f(x)= cos(x + 8)</td>
     * </tr>
     * <tr>
     * <td>cosh()</td>
     * <td>Cosseno hiperbólico. Ex.: f(x)= cos(x + 8)</td>
     * </tr>
     * <tr>
     * <td>exp()</td>
     * <td>Número de Euler, ou mesmo que e^x, sendo e = número de Euler. Ex.: f(x)= exp(x)</td>
     * </tr>
     * <tr>
     * <td>floor()</td>
     * <td>Arredondamento para baixo. Ex.: f(y)= floor(4y)</td>
     * </tr>
     * <tr>
     * <td>log()</td>
     * <td>Logaritmo natural, ou seja, na base e. Ex.: f(y)= log(y) - 10</td>
     * </tr>
     * <tr>
     * <td>log10()</td>
     * <td>Logaritmo na base 10. Ex.: f(y)= log10(y^2)</td>
     * </tr>
     * <tr>
     * <td>log2()</td>
     * <td>Logaritmo na base 2. Ex.: f(y)= log2(y)^3</td>
     * </tr>
     * <tr>
     * <td>sin()</td>
     * <td>Seno. Ex.: f(x)= sin(x) + 7</td>
     * </tr>
     * <tr>
     * <td>sinh()</td>
     * <td>Seno hiperbólico. Ex.: f(x)= sinh(x) - 7</td>
     * </tr>
     * <tr>
     * <td>sqrt()</td>
     * <td>Raiz quadrada. Ex.: f(x)= sqrt(x^4)</td>
     * </tr>
     * <tr>
     * <td>tan()</td>
     * <td>Tangente. Ex.: f(y)= tan(2 - y)</td>
     * </tr>
     * <tr>
     * <td>tanh()</td>
     * <td>Tangente hiperbólica. Ex.: f(x)= tanh(x + x^3)</td>
     * </tr>
     * </table>
     * @param funcao String que define uma função Matemática.
     * @param iniciarEmCoordNegativa Define se a função deverá começar a partir do
     * lado negativo e seguir para o positivo em relação ao objeto a ser movimentado
     */
    public void movimentarPorFuncao(String funcao, boolean iniciarEmCoordNegativa);
    
    /**
     * Paralisa movimento atual chamando movimentar(null), movimentar(0) e
     * movimentarRepetidamente(0.0, 0.0, 0.0, false);
     */
    public void pararMovimento();
    
    /**
     * Efetua e Retorna o cálculo de uma função Matemática passada por parâmetro.
     * Esse cálculo é feito sobre um contandor, que será o x ou y da função, que
     * vai sendo incrementado por velX, quando é f(x), ou velY, quando é f(y).<br>
     * Toda função Matemática deve seguir o modelo abaixo:<br>
     * <ul>
     * <li>"f(x)= 3x^2 + 0.4"</li>
     * <li>"f(y)= sin(3y^2) - 0.4"</li>
     * </ul>
     * Quando se trabalha com f(x) todas as variáveis devem ser x, já se a função 
     * for f(y) todas as variáveis deverão ser y.<br>
     * Abaixo segue uma tabela de símbolos Matemáticos permitidos.<br>
     * <table>
     * <caption>Símbolos e Exemplos</caption>
     * <tr>
     * <th>Símbolo</th>
     * <th>Explicação</th>
     * </tr>
     * <tr>
     * <td>+</td>
     * <td>Adição. Ex.: f(x)= 2 + 2</td>
     * </tr>
     * <tr>
     * <td>-</td>
     * <td>Subtração. Ex.: f(y)= 2 - y</td>
     * </tr>
     * <tr>
     * <td>*</td>
     * <td>Multiplicação. Ex.: f(y)= 2 * 5 - y</td>
     * </tr>
     * <tr>
     * <td>/</td>
     * <td>Divisão. Ex.: f(x)= 25 / 7x</td>
     * </tr>
     * <tr>
     * <td>^</td>
     * <td>Exponenciação. Ex.: f(x)= 2x^2 / x</td>
     * </tr>
     * <tr>
     * <td>%</td>
     * <td>Módulo. Ex.: f(y)= y % 3y</td>
     * </tr>
     * <tr>
     * <td>abs()</td>
     * <td>Valor absoluto. Ex.: f(y)= abs(y)</td>
     * </tr>
     * <tr>
     * <td>acos()</td>
     * <td>Arco cosseno. Ex.: f(x)= 3 + acos(x)</td>
     * </tr>
     * <tr>
     * <td>asin()</td>
     * <td>Arco seno. Ex.: f(x)= asin(x) - 3</td>
     * </tr>
     * <tr>
     * <td>atan()</td>
     * <td>Arco tangente. Ex.: f(x)= atan(2x)</td>
     * </tr>
     * <tr>
     * <td>cbrt()</td>
     * <td>Raiz cúbica. Ex.: f(x)= 1+ cbrt(x)</td>
     * </tr>
     * <tr>
     * <td>ceil()</td>
     * <td>Arredondamento para cima. Ex.: f(x)= ceil(x) + 0.1</td>
     * </tr>
     * <tr>
     * <td>cos()</td>
     * <td>Cosseno. Ex.: f(x)= cos(x + 8)</td>
     * </tr>
     * <tr>
     * <td>cosh()</td>
     * <td>Cosseno hiperbólico. Ex.: f(x)= cos(x + 8)</td>
     * </tr>
     * <tr>
     * <td>exp()</td>
     * <td>Número de Euler, ou mesmo que e^x, sendo e = número de Euler. Ex.: f(x)= exp(x)</td>
     * </tr>
     * <tr>
     * <td>floor()</td>
     * <td>Arredondamento para baixo. Ex.: f(y)= floor(4y)</td>
     * </tr>
     * <tr>
     * <td>log()</td>
     * <td>Logaritmo natural, ou seja, na base e. Ex.: f(y)= log(y) - 10</td>
     * </tr>
     * <tr>
     * <td>log10()</td>
     * <td>Logaritmo na base 10. Ex.: f(y)= log10(y^2)</td>
     * </tr>
     * <tr>
     * <td>log2()</td>
     * <td>Logaritmo na base 2. Ex.: f(y)= log2(y)^3</td>
     * </tr>
     * <tr>
     * <td>sin()</td>
     * <td>Seno. Ex.: f(x)= sin(x) + 7</td>
     * </tr>
     * <tr>
     * <td>sinh()</td>
     * <td>Seno hiperbólico. Ex.: f(x)= sinh(x) - 7</td>
     * </tr>
     * <tr>
     * <td>sqrt()</td>
     * <td>Raiz quadrada. Ex.: f(x)= sqrt(x^4)</td>
     * </tr>
     * <tr>
     * <td>tan()</td>
     * <td>Tangente. Ex.: f(y)= tan(2 - y)</td>
     * </tr>
     * <tr>
     * <td>tanh()</td>
     * <td>Tangente hiperbólica. Ex.: f(x)= tanh(x + x^3)</td>
     * </tr>
     * </table> 
     * @param funcao String que define uma função Matemática.
     * @return double
     */
    public double calcularFuncao(String funcao);
    
    /**
     * Efetua e Retorna o cálculo de uma função Matemática passada por parâmetro.
     * Esse cálculo é feito sobre um contandor, que será o x ou y da função, que
     * vai sendo incrementado por velX, quando é f(x), ou velY, quando é f(y),
     * dentro do limite pré estabelecido nos parâmetros do método.<br>
     * Toda função Matemática deve seguir o modelo abaixo:<br>
     * <ul>
     * <li>"f(x)= 3x^2 + 0.4"</li>
     * <li>"f(y)= sin(3y^2) - 0.4"</li>
     * </ul>
     * Quando se trabalha com f(x) todas as variáveis devem ser x, já se a função 
     * for f(y) todas as variáveis deverão ser y.<br>
     * Abaixo segue uma tabela de símbolos Matemáticos permitidos.
     * <table>
     * <caption>Símbolos e Exemplos</caption>
     * <tr>
     * <th>Símbolo</th>
     * <th>Explicação</th>
     * </tr>
     * <tr>
     * <td>+</td>
     * <td>Adição. Ex.: f(x)= 2 + 2</td>
     * </tr>
     * <tr>
     * <td>-</td>
     * <td>Subtração. Ex.: f(y)= 2 - y</td>
     * </tr>
     * <tr>
     * <td>*</td>
     * <td>Multiplicação. Ex.: f(y)= 2 * 5 - y</td>
     * </tr>
     * <tr>
     * <td>/</td>
     * <td>Divisão. Ex.: f(x)= 25 / 7x</td>
     * </tr>
     * <tr>
     * <td>^</td>
     * <td>Exponenciação. Ex.: f(x)= 2x^2 / x</td>
     * </tr>
     * <tr>
     * <td>%</td>
     * <td>Módulo. Ex.: f(y)= y % 3y</td>
     * </tr>
     * <tr>
     * <td>abs()</td>
     * <td>Valor absoluto. Ex.: f(y)= abs(y)</td>
     * </tr>
     * <tr>
     * <td>acos()</td>
     * <td>Arco cosseno. Ex.: f(x)= 3 + acos(x)</td>
     * </tr>
     * <tr>
     * <td>asin()</td>
     * <td>Arco seno. Ex.: f(x)= asin(x) - 3</td>
     * </tr>
     * <tr>
     * <td>atan()</td>
     * <td>Arco tangente. Ex.: f(x)= atan(2x)</td>
     * </tr>
     * <tr>
     * <td>cbrt()</td>
     * <td>Raiz cúbica. Ex.: f(x)= 1+ cbrt(x)</td>
     * </tr>
     * <tr>
     * <td>ceil()</td>
     * <td>Arredondamento para cima. Ex.: f(x)= ceil(x) + 0.1</td>
     * </tr>
     * <tr>
     * <td>cos()</td>
     * <td>Cosseno. Ex.: f(x)= cos(x + 8)</td>
     * </tr>
     * <tr>
     * <td>cosh()</td>
     * <td>Cosseno hiperbólico. Ex.: f(x)= cos(x + 8)</td>
     * </tr>
     * <tr>
     * <td>exp()</td>
     * <td>Número de Euler, ou mesmo que e^x, sendo e = número de Euler. Ex.: f(x)= exp(x)</td>
     * </tr>
     * <tr>
     * <td>floor()</td>
     * <td>Arredondamento para baixo. Ex.: f(y)= floor(4y)</td>
     * </tr>
     * <tr>
     * <td>log()</td>
     * <td>Logaritmo natural, ou seja, na base e. Ex.: f(y)= log(y) - 10</td>
     * </tr>
     * <tr>
     * <td>log10()</td>
     * <td>Logaritmo na base 10. Ex.: f(y)= log10(y^2)</td>
     * </tr>
     * <tr>
     * <td>log2()</td>
     * <td>Logaritmo na base 2. Ex.: f(y)= log2(y)^3</td>
     * </tr>
     * <tr>
     * <td>sin()</td>
     * <td>Seno. Ex.: f(x)= sin(x) + 7</td>
     * </tr>
     * <tr>
     * <td>sinh()</td>
     * <td>Seno hiperbólico. Ex.: f(x)= sinh(x) - 7</td>
     * </tr>
     * <tr>
     * <td>sqrt()</td>
     * <td>Raiz quadrada. Ex.: f(x)= sqrt(x^4)</td>
     * </tr>
     * <tr>
     * <td>tan()</td>
     * <td>Tangente. Ex.: f(y)= tan(2 - y)</td>
     * </tr>
     * <tr>
     * <td>tanh()</td>
     * <td>Tangente hiperbólica. Ex.: f(x)= tanh(x + x^3)</td>
     * </tr>
     * </table> 
     * @param funcao String que define uma função Matemática.
     * @param intervaloIncial Número inicial do contador
     * @param intervaloFinal Número final do contador
     * @return double Caso seja retornado 0 quer dizer que a função atingiu o 
     * limite final
     */
    public double calcularFuncao(String funcao, double intervaloIncial, double intervaloFinal);
    
}
