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

import simplegamework.padraoProjeto.Atualizavel;
import simplegamework.padraoProjeto.ListManuseavel;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * List para manusear vários Atualizavel.
 * <br><br><small>Created on : 25/06/2015, 17:51:22</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class AtualizavelList implements ListManuseavel<Atualizavel>{
    
    ArrayList<Atualizavel> listA;
    
    //Auxliares
    private int indiceAtual;
    
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    public AtualizavelList(){
        listA = new ArrayList<>();
    }
    
    public AtualizavelList(Atualizavel novoElemento){
        this();
        add(novoElemento);
    }
    
    public AtualizavelList(Atualizavel... variosElementos){
        this();
        add(variosElementos);
    }
    //</editor-fold>
    
    /**
     * Invoca todos os métodos desenha(Graphics2D g) dentro do AtualizavelList
     */
    public void invocarAtualizavelList(){
        for (int indice = 0; indice < size(); indice++) {
            get(indice).atualiza();
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: ListManuseavel">
    @Override
    public void add(Atualizavel novoElemento) {
        listA.add(novoElemento);
    }

    @Override
    public void add(Atualizavel... variosElementos) {
        listA.addAll(Arrays.asList(variosElementos));
    }

    @Override
    public Atualizavel get(int indice) {
        return listA.get(indice);
    }

    @Override
    public Atualizavel getNext() {
        Atualizavel temp = listA.get(indiceAtual);
        indiceAtual++;
        
        if (indiceAtual >= size()) {
            indiceAtual = 0;
            return null;
        }
        
        return temp;
    }

    @Override
    public void remove(int indice) {
        listA.remove(indice);
    }

    @Override
    public void remove(Atualizavel elementoParaRemover) {
        listA.remove(elementoParaRemover);
    }

    @Override
    public int size() {
        return listA.size();
    }
    //</editor-fold>
    
}
