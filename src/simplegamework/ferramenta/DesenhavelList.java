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

import simplegamework.padraoProjeto.Desenhavel;
import simplegamework.padraoProjeto.ListManuseavel;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * List para manusear vários Desenhavel.
 * <br><br><small>Created on : 25/06/2015, 17:51:02</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class DesenhavelList implements ListManuseavel<Desenhavel>{
    
    ArrayList<Desenhavel> listD;
    
    //Auxliares
    private int indiceAtual;
    
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    public DesenhavelList(){
        listD = new ArrayList<>();
    }
    
    public DesenhavelList(Desenhavel novoElemento){
        this();
        add(novoElemento);
    }
    
    public DesenhavelList(Desenhavel... variosElementos){
        this();
        add(variosElementos);
    }
    //</editor-fold>
    
    /**
     * Invoca todos os métodos desenha(Graphics2D g) dentro do DesenhavelList
     * @param g2d O objeto para desenhar na tela
     */
    public void invocarDesenhavelList(Graphics2D g2d){
        for (int indice = 0; indice < size(); indice++) {
            get(indice).desenha(g2d);
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: ListManuseavel">
    @Override
    public void add(Desenhavel novoElemento) {
        listD.add(novoElemento);
    }

    @Override
    public void add(Desenhavel... variosElementos) {
        listD.addAll(Arrays.asList(variosElementos));
    }

    @Override
    public Desenhavel get(int indice) {
        return listD.get(indice);
    }

    @Override
    public Desenhavel getNext() {
        Desenhavel temp = listD.get(indiceAtual);
        indiceAtual++;
        
        if (indiceAtual >= size()) {
            indiceAtual = 0;
            return null;
        }
        
        return temp;
    }

    @Override
    public void remove(int indice) {
        listD.remove(indice);
    }

    @Override
    public void remove(Desenhavel elementoParaRemover) {
        listD.remove(elementoParaRemover);
    }

    @Override
    public int size() {
        return listD.size();
    }
    //</editor-fold>
    
}
