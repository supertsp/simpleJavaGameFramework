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

package simplegamework.objetoBasico;

import simplegamework.ferramenta.AtualizavelList;
import simplegamework.padraoProjeto.Atualizavel;
import simplegamework.padraoProjeto.AtualizavelListManuseavel;
import simplegamework.padraoProjeto.Coordenavel;
import simplegamework.padraoProjeto.Desenhavel;
import simplegamework.padraoProjeto.ListManuseavel;
import simplegamework.padraoProjeto.Movimentavel;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * ArrayList de CaixaColisao.
 * <br><br><small>Created on : 20/06/2015, 17:22:03</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class CaixaColisaoList implements ListManuseavel<CaixaColisao>, Coordenavel,
        Movimentavel, Desenhavel, Atualizavel, AtualizavelListManuseavel{
    
    private ArrayList<CaixaColisao> cxs;
    
    //Auxiliares
    protected AtualizavelList atualizaveis;
    private int indiceAtual;
    
    //<editor-fold defaultstate="collapsed" desc="Costrutores">
    /**
     * Apenas instancia o ArrayList de CaixaColisao.
     */
    public CaixaColisaoList(){
        cxs = new ArrayList<>();
        atualizaveis = new AtualizavelList();
    }
    
    /**
     * Cria o ArrayList de CaixaColisao e adiciona o primeiro elemento.
     * @param primeiroElemento O primeiro elemento a ser adicionado
     */
    public CaixaColisaoList(CaixaColisao primeiroElemento){
        this();
        cxs.add(primeiroElemento);
    }
    //</editor-fold>
    
    /**
     * Retorna um elemento de acordo com seu rótulo passado por parâmetro.
     * @param rotulo O identificador procurado que localiza o elemento
     * @return CaixaColisao
     */
    public CaixaColisao get(Object rotulo){
        for (int indice = 0; indice < size(); indice++) {
            if (cxs.get(indice).getRotulo().equals(rotulo)) {
                return cxs.get(indice);
            }
        }
        
        return null;
    }
        
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: ListManuseavel">
    @Override
    public void add(CaixaColisao novoElemento) {
        cxs.add(novoElemento);
    }

    @Override
    public void add(CaixaColisao... variosElementos) {
        cxs.addAll(Arrays.asList(variosElementos));
    }

    @Override
    public CaixaColisao get(int indice) {
        return cxs.get(indice);
    }

    @Override
    public CaixaColisao getNext() {
        CaixaColisao temp = cxs.get(indiceAtual);
        indiceAtual++;
        
        if (indiceAtual >= size()) {
            indiceAtual = 0;
            return null;
        }
        
        return temp;
    }

    @Override
    public void remove(int indice) {
        cxs.remove(indice);
    }

    @Override
    public void remove(CaixaColisao elementoParaRemover) {
        cxs.remove(elementoParaRemover);
    }

    @Override
    public int size() {
        return cxs.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Coordenavel">
    @Override
    public void setX(double x) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).setX(x);
        }
    }

    @Override
    public void setY(double y) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).setY(y);
        }
    }

    @Override
    public void incrementaX(double aumentoX) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).incrementaX(aumentoX);
        }
    }

    @Override
    public void incrementaY(double aumentoY) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).incrementaY(aumentoY);
        }
    }

    @Override
    public void setCoordenadas(double x, double y) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).setCoordenadas(x, y);
        }
    }

    @Override
    public void setCoordenadas(Coordenadas novasCoordenadas) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).setCoordenadas(novasCoordenadas);
        }
    }

    @Override
    public void incrementarCoordenadas(double aumentoX, double aumentoY) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).incrementarCoordenadas(aumentoX, aumentoY);
        }
    }

    @Override
    public double getX() {
        return get(0).getX();
    }

    @Override
    public int getIntX() {
        return get(0).getIntX();
    }

    @Override
    public double getY() {
        return get(0).getY();
    }

    @Override
    public int getIntY() {
        return get(0).getIntY();
    }

    @Override
    public Coordenadas getCoordenadas() {
        return get(0).getCoordenadas();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Movimentavel">
    @Override
    public void setVelX(double velX) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).setVelX(velX);
        }
    }

    @Override
    public void setVelY(double velY) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).setVelY(velY);
        }
    }

    @Override
    public void incrementaVelX(double aumentoVelX) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).incrementaVelX(aumentoVelX);
        }
    }

    @Override
    public void incrementaVelY(double aumentoVelY) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).incrementaVelY(aumentoVelY);
        }
    }

    @Override
    public void setVelocidades(double velX, double velY) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).setVelocidades(velX, velY);
        }
    }

    @Override
    public void incrementarVelocidades(double aumentoVelX, double aumentoVelY) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).incrementarVelocidades(aumentoVelX, aumentoVelY);
        }
    }

    @Override
    public double getVelX() {
        return get(0).getVelX();
    }

    @Override
    public int getIntVelX() {
        return get(0).getIntVelX();
    }

    @Override
    public double getVelY() {
        return get(0).getVelY();
    }

    @Override
    public int getIntVelY() {
        return get(0).getIntVelY();
    }

    @Override
    public void movimentar(TipoMovimento novoMovimento) {        
        for (int indice = 0; indice < size(); indice++) {
            get(indice).movimentar(novoMovimento);
        }
    }

    @Override
    public void movimentar(double direcaoEmGraus) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).movimentar(direcaoEmGraus);
        }
    }

    @Override
    public void movimentarCurvaQuadratica(double alturaX, double alturaY, double comprimentoX, double comprimentoY) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).movimentarCurvaQuadratica(alturaX, alturaY, comprimentoX, comprimentoY);
        }
    }

    @Override
    public void movimentarRepetidamente(double indiceDeformardorX, double indiceDeformardorY, double comprimento, double altura, boolean inverter) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).movimentarRepetidamente(indiceDeformardorX, indiceDeformardorY, comprimento, altura, inverter);
        }
    }

    @Override
    public void movimentarPorFuncao(String funcao, boolean iniciarEmCoordNegativa) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).movimentarPorFuncao(funcao, iniciarEmCoordNegativa);
        }
    }

    @Override
    public void pararMovimento() {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).pararMovimento();
        }
    }

    @Override
    public double calcularFuncao(String funcao) {
        return get(0).calcularFuncao(funcao);
    }

    @Override
    public double calcularFuncao(String funcao, double intervaloIncial, double intervaloFinal) {
        return get(0).calcularFuncao(funcao, intervaloIncial, intervaloFinal);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
    @Override
    public void desenha(Graphics2D g2d) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).desenha(g2d);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Atualizavel">
    @Override
    public void atualiza() {
        invocarAtualizavelList();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: AtualizavelListManuseavel">
    @Override
    public void invocarAtualizavelList() {
        atualizaveis.invocarAtualizavelList();
    }

    @Override
    public void addAtualizavel(Atualizavel novoElemento) {
        atualizaveis.add(novoElemento);
    }

    @Override
    public void addAtualizavel(Atualizavel... variosElementos) {
        atualizaveis.add(variosElementos);
    }

    @Override
    public Atualizavel getAtualizavel(int indice) {
        return atualizaveis.get(indice);
    }

    @Override
    public Atualizavel getNextAtualizavel() {
        return atualizaveis.getNext();
    }

    @Override
    public void removeAtualizavel(int indice) {
        atualizaveis.remove(indice);
    }

    @Override
    public void removeAtualizavel(Atualizavel elementoParaRemover) {
        atualizaveis.remove(elementoParaRemover);
    }

    @Override
    public int sizeAtualizavelList() {
        return atualizaveis.size();
    }
    //</editor-fold>
    
    
    
}
