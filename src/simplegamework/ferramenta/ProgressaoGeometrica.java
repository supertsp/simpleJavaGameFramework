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
 * Resolve os problemas comuns das Progressões Geométricas, como por exemplo,
 * descobrir um termo distante qualquer ou a somatória vários termos.
 * <br><br><small>Created on : 08/07/2015, 12:59:01</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class ProgressaoGeometrica {
    
    private double primeiroTermo;
    private double razao;
    
    //<editor-fold defaultstate="collapsed" desc="Construtor">
    /**
     * Construtor básico da classe.
     * @param primeiroTermo O valor do primerio termo da PG
     * @param razao O valor da razão entre cada termo da PG
     */
    public ProgressaoGeometrica(double primeiroTermo, double razao){
        this.primeiroTermo = primeiroTermo;
        this.razao = razao;
    }
    //</editor-fold>
    
    /**
     * Calcula e retorna o valor do N termo procurado.
     * @param indiceTermoN A posição do termo N procurado
     * @return double
     */
    public double calcularTermoN(double indiceTermoN){
        return primeiroTermo * Math.pow(razao, (indiceTermoN - 1));
    }
    
    /**
     * Calcula e retorna o somatório entre os termos da PG até o termo N. 
     * @param indiceTermoN A posição do termo N procurado
     * @return double
     */
    public double calcularSomatorioAteTermoN(double indiceTermoN){
        return primeiroTermo * ((Math.pow(razao, indiceTermoN) - 1) / (razao -1));
    }
    
    /**
     * Retorna um array double com os elementos da progressão a partir do índice
     * do elemento passado por parâmetro.
     * @param qtdElementos O total de elementos no array
     * @param indicePrimeiroTermo O índice do termo que iniciará o array. Obs.:
     * o primeiro termo é de índice = 1
     * @return double[]
     */
    public double[] getArray(int qtdElementos, int indicePrimeiroTermo){
        double[] array = new double[qtdElementos];
        
        for (int i = 0; i < qtdElementos; i++) {
            array[i] = calcularTermoN(indicePrimeiroTermo);
            indicePrimeiroTermo++;
        }
        
        return array;
    }
    
    /**
     * Retorna um array double com os elementos da progressão.
     * @param qtdElementos O total de elementos no array
     * @return double[]
     */
    public double[] getArray(int qtdElementos){
        return getArray(qtdElementos, 1);
    }
    
    /**
     * Retorna um array int com os elementos da progressão a partir do índice
     * do elemento passado por parâmetro.
     * @param qtdElementos O total de elementos no array
     * @param indicePrimeiroTermo O índice do termo que iniciará o array. Obs.:
     * o primeiro termo é de índice = 1
     * @return int[]
     */
    public int[] getIntegerArray(int qtdElementos, int indicePrimeiroTermo){
        int[] array = new int[qtdElementos];
        
        for (int i = 0; i < qtdElementos; i++) {
            array[i] = (int)calcularTermoN(i);
            indicePrimeiroTermo++;
        }
        
        return array;
    }
    
    /**
     * Retorna um array int com os elementos da progressão.
     * @param qtdElementos O total de elementos no array
     * @return int[]
     */
    public int[] getIntegerArray(int qtdElementos){
        return getIntegerArray(qtdElementos, 1);
    }
    
}
