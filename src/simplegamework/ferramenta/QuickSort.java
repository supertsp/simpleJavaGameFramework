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
 * QuickSort foi desenvolvido em 1960 e publicado em 1962 <br>
 * Este algoritmo foi extraido de: http://pt.wikipedia.org/wiki/Quicksort
 * <br><br><small>Created on : 04/05/2015, 11:28:35</small>
 * @author C. A. R. Hoare
 * @version 1.0
 */
public class QuickSort {
    
    /**
     * Inicia a ordenação do array passado por parâmetro.
     * @param array O array a ser ordenado
     */
    public static void ordenar(int[] array) {
        ordenar(array, 0, array.length - 1);
    }

    private static void ordenar(int[] vetor, int inicio, int fim) {
        if (inicio < fim) {
            int posicaoPivo = separar(vetor, inicio, fim);
            ordenar(vetor, inicio, posicaoPivo - 1);
            ordenar(vetor, posicaoPivo + 1, fim);
        }
    }

    private static int separar(int[] vetor, int inicio, int fim) {
        int pivo = vetor[inicio];
        int i = inicio + 1, f = fim;
        while (i <= f) {
            if (vetor[i] <= pivo) {
                i++;
            } else if (pivo < vetor[f]) {
                f--;
            } else {
                int troca = vetor[i];
                vetor[i] = vetor[f];
                vetor[f] = troca;
                i++;
                f--;
            }
        }
        vetor[inicio] = vetor[f];
        vetor[f] = pivo;
        return f;
    }

}
