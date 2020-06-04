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

package simplegamework.imagem.tile;

import simplegamework.padraoProjeto.ListManuseavel;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * ArrayList de CamadaTiles.
 * <br><br><small>Created on : 23/06/2015, 15:46:55</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class CamadaTilesList implements ListManuseavel<CamadaTiles>{
    
    private ArrayList<CamadaTiles> camadas;
    
    //Auxiliares
    private int indiceAtual;
    
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    public CamadaTilesList(CamadaTiles primeiraCamada){
        camadas = new ArrayList<>();
        add(primeiraCamada);
    }
    
    public CamadaTilesList(CamadaTiles... variasCamadas){
        camadas = new ArrayList<>();
        add(variasCamadas);
    }
    //</editor-fold>
    
    /**
     * Retorna o elemento procurado de acordo com o rótulo passado por parâmetro.
     * @param rotulo O rótulo procurado
     * @return CamadaTiles
     */
    public CamadaTiles get(Object rotulo){
        for (int indice = 0; indice < size(); indice++) {
            if (get(indice).getRotulo().equals(rotulo)) {
                return get(indice);
            }
        }
        
        return null;
    }
        
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: ListManuseavel">
    @Override
    public void add(CamadaTiles novoElemento) {
        camadas.add(novoElemento);
    }

    @Override
    public void add(CamadaTiles... variosElementos) {
        camadas.addAll(Arrays.asList(variosElementos));
    }

    @Override
    public CamadaTiles get(int indice) {
        return camadas.get(indice);
    }

    @Override
    public CamadaTiles getNext() {
        CamadaTiles temp = camadas.get(indiceAtual);
        indiceAtual++;
        
        if (indiceAtual >= size()) {
            indiceAtual = 0;
            return null;
        }
        
        return temp;
    }

    @Override
    public void remove(int indice) {
        camadas.remove(indice);
    }

    @Override
    public void remove(CamadaTiles elementoParaRemover) {
        camadas.remove(elementoParaRemover);
    }

    @Override
    public int size() {
        return camadas.size();
    }
    //</editor-fold>
        
}
