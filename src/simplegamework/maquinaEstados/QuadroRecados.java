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

package simplegamework.maquinaEstados;

import simplegamework.padraoProjeto.ListManuseavel;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * As vezes é preciso trocar mensagens entre os estados da MaquinaEstados, nesse
 * caso a melhor opção é utilizar um QuadroRecados. Sendo assim, essa classe é
 * apenas um ArrayList de Object para que seja o mais flexível possível para
 * receber qualquer tipo de mensagem, desde uma String, um enum e até um objeto
 * mais complexo.
 * <br>
 * <br><small>Created on : 20/09/2015, 23:48:24</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class QuadroRecados implements ListManuseavel<Object>{
    
    private ArrayList<Object> quadro;
    
    //Auxiliares
    private int indiceAtual;
    
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    /**
     * Construtor default de classe.
     */
    public QuadroRecados(){
        quadro = new ArrayList<>();
    }
    
    /**
     * Construtor que permite adicionar vários elementos ao mesmo tempo. Por 
     * exemplo: new QuadroRecados(recado1, recado2, recadoEtc);.
     * @param variosElementos Cada recado novo a ser adicionado
     */
    public QuadroRecados(Object... variosElementos){
        this();
        add(variosElementos);
    }
    //</editor-fold>
    
    /**
     * Adiciona ou Altera um novo elemento na posição passada por parâmetro.
     * @param indice A posição desejada
     * @param novoElemento O novo elemento a ser inserido
     */
    public void add(int indice, Object novoElemento) {
        if (existeElementoEm(indice)) {
            quadro.set(indice, novoElemento);
        }
        else{
            quadro.add(indice, novoElemento);
        }
    }
    
    /**
     * Retorna true caso exista algum elemento na posição procurada.
     * @param indice A posição procurada
     * @return boolean
     */
    public boolean existeElementoEm(int indice){
        boolean existe = false;
        
        try {
            quadro.get(indice);
            existe = true;
        } catch (Exception e) {}
        
        return existe;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: ListManuseavel">
    @Override
    public void add(Object novoElemento) {
        quadro.add(novoElemento);
    }

    @Override
    public void add(Object... variosElementos) {
        quadro.addAll(Arrays.asList(variosElementos));
    }

    @Override
    public Object get(int indice) {
        return quadro.get(indice);
    }

    @Override
    public Object getNext() {
        Object temp = quadro.get(indiceAtual);
        indiceAtual++;
        
        if (indiceAtual >= size()) {
            indiceAtual = 0;
            return null;
        }
        
        return temp;
    }

    @Override
    public void remove(int indice) {
        quadro.remove(indice);
    }

    @Override
    public void remove(Object elementoParaRemover) {
        quadro.remove(elementoParaRemover);
    }

    @Override
    public int size() {
        return quadro.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Object">
    /**
     * Cria uma String com todos os elementos do QuadroRecados para debug.
     * @return String
     */
    @Override
    public String toString() {
        String recados = "";
        for (int cont = 0; cont < size(); cont++) {
            recados += "    [" + cont + "]" + quadro.get(cont) + "\n";
        }
        
        return "QuadroRecados{\n" +
                recados +
                "}";
    }
    //</editor-fold>
    
}
