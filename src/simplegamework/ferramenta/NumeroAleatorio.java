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
 * A método Math.random() sorteia um número aleatório double, porém todas as vezes
 * que é necessário sortear um número qualquer dentro de uma faixa numérica
 * determinada, ou também, quando é necessário sortear um número qualquer dentro
 * de uma faixa numérica e que não haja números repetidos dentro do sorteio, a
 * Classe Math acaba por não resolver esses problemas. Essa classe porém, resolve
 * todos esses problemas mencionados.
 * <br><small>Created on : 28/10/2015, 21:36:54</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class NumeroAleatorio {
        
    private double[] numerosRepetidos;  
    private double limiteInicial;
    private double limiteFinal;
    
    //AUXILIARES
    private int indiceRepetidos;
    
    //<editor-fold defaultstate="collapsed" desc="Construtor">
    /**
     * Construtor básico da classe.
     * @param qtdNumerosDoSorteio Quando deseja-se sortear números não repetidos,
     * esse parâmetro determina o limite de números a serem sorteados
     * @param limiteInicial O menor número da faixa numérica a ser sorteado
     * @param limiteFinal O maior número da faixa numérica a ser sorteado
     */
    public NumeroAleatorio(
            int qtdNumerosDoSorteio,
            double limiteInicial, double limiteFinal
    ){        
        this.limiteInicial = limiteInicial;
        this.limiteFinal = limiteFinal;        
        numerosRepetidos = new double[qtdNumerosDoSorteio];
        reiniciarSorteioSemRepeticao();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos Auxiliares">
    /**
     * Retorna true caso o número procurado já exista no array numerosRepetidos.
     * @param num O número procurado
     * @return boolean
     */
    private boolean numeroExiste(double num){
        for (int indice = 0; indice < numerosRepetidos.length; indice++) {
            if (num == numerosRepetidos[indice]) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Retorna true caso o array numerosRepetidos já esteja cheio de números
     * sorteados.
     * @return boolean
     */
    private boolean arrayNumerosRepetidosCheio(){
        if (indiceRepetidos >= numerosRepetidos.length) {
            return true;
        }
        else{
            return false;
        }
    }
    //</editor-fold>
    
    /**
     * Sorteia um número aleatório dentro dos limites estabelecidos por parâmetro.<br>
     * Obs.: os números dos limites também podem ser sorteados.
     * @param limiteInicial O menor número da faixa numérica a ser sorteado
     * @param limiteFinal O maior número da faixa numérica a ser sorteado
     * @return int
     */
    public static int sortear(int limiteInicial, int limiteFinal){
        return (int)sortear((double)limiteInicial, (double)limiteFinal);
    }
    
    /**
     * Sorteia um número aleatório dentro dos limites estabelecidos por parâmetro.<br>
     * Obs.: os números dos limites também podem ser sorteados.
     * @param limiteInicial O menor número da faixa numérica a ser sorteado
     * @param limiteFinal O maior número da faixa numérica a ser sorteado
     * @return double
     */
    public static double sortear(double limiteInicial, double limiteFinal){
        double num;
        do{
            num = Math.random() * (limiteFinal + 1);
        }while(num < limiteInicial);
        
        return num;
    }
    
    /**
     * Sorteia um número aleatório dentro dos limites estabelecidos no construtor
     * da classe.<br>
     * Obs.: os números dos limites também podem ser sorteados.
     * @return int
     */
    public int sortearInteger(){
        return sortear((int)limiteInicial, (int)limiteFinal);
    }
    
    /**
     * Sorteia um número aleatório dentro dos limites estabelecidos no construtor
     * da classe.<br>
     * Obs.: os números dos limites também podem ser sorteados.
     * @return double
     */
    public double sortearDouble(){
        return sortear(limiteInicial, limiteFinal);
    }
    
    /**
     * Sorteia um número aleatório dentro dos limites estabelecidos, no construtor
     * da classe, e que não seja repetido.<br>
     * Obs.: os números dos limites também podem ser sorteados.
     * @return int
     */
    public int sortearIntegerSemRepeticao(){
        if (!arrayNumerosRepetidosCheio()) {
            int num;

            do{
                num = sortearInteger();
            }while(numeroExiste(num));
            
            numerosRepetidos[indiceRepetidos] = num;
            indiceRepetidos++;
            return num;
        }
        else{
            return -1;
        }       
    }
    
    /**
     * Sorteia um número aleatório dentro dos limites estabelecidos, no construtor
     * da classe, e que não seja repetido.<br>
     * Obs.: os números dos limites também podem ser sorteados.
     * @return double
     */
    public double sortearDoubleSemRepeticao(){
        if (!arrayNumerosRepetidosCheio()) {
            double num;

            do{
                num = sortearDouble();
            }while(numeroExiste(num));
            
            numerosRepetidos[indiceRepetidos] = num;
            indiceRepetidos++;
            return num;
        }
        else{
            return -1;
        }        
    }
    
    /**
     * Limpa o array que armazena todos os números repetidos para que possa ser
     * permitido novos sorteios sem repetição numérica.
     */
    public void reiniciarSorteioSemRepeticao(){
        for (int indice = 0; indice < numerosRepetidos.length; indice++) {
            numerosRepetidos[indice] = -1;
        }
    }
    
    /**
     * Retorna o tamanho do array que armazena todos os números repetidos.
     * @return int
     */
    public int getQtdNumerosDoSorteio(){
        return numerosRepetidos.length;
    }
    
}
