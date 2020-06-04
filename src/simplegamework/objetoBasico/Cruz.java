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

import simplegamework.padraoProjeto.Coordenavel;
import simplegamework.padraoProjeto.Desenhavel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

/**
 * Desenha uma simples cruz, com ou sem borda, na tela.<br>
 * Obs.: A espessura dela sempre será de 1px, no caso dela não desenhar borda, já
 * se tiver borda ela ficará com 3px.
 * <br><br><small>Created on : 08/07/2015, 05:21:49</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class Cruz implements Coordenavel, Desenhavel{
    
    protected GeneralPath cruz;
    protected Coordenadas posicaoNaTela;
    protected int tamanho;
    protected Color cor;
    protected Color corBorda;    
    protected boolean bordaAtiva;
       
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    /**
     * Construtor básico da classe que define a cor da Cruz como Color.BLACK.
     * @param posicaoNaTela As Coordenadas onde o centro da Cruz será posicionado
     * @param tamanho Um valor maior que zero e positivo
     * @param ativarBorda Define se será exibida a borda da Cruz, sendo que, sua
     * cor padrão será Color.WHITE
     */
    public Cruz(Coordenadas posicaoNaTela, int tamanho, boolean ativarBorda){
        this.posicaoNaTela = posicaoNaTela;
        this.tamanho = tamanho > 0 ? tamanho : 1;
        cor = Color.BLACK;
        corBorda = Color.WHITE;
        bordaAtiva = ativarBorda;
        initCruz();
    }
    
    /**
     * Construtor que serve para alterar a cor da Cruz.
     * @param posicaoNaTela As Coordenadas onde o centro da Cruz será posicionado
     * @param tamanho Um valor maior que zero e positivo
     * @param cor A cor da Cruz
     * @param ativarBorda Define se será exibida a borda da Cruz, sendo que, sua
     * cor padrão será Color.WHITE
     */
    public Cruz(Coordenadas posicaoNaTela, int tamanho, Color cor, boolean ativarBorda){
        this(posicaoNaTela, tamanho, ativarBorda);
        this.cor = cor;        
    }
    
    /**
     * Construtor que serve para alterar a cor da Cruz.
     * @param posicaoNaTela As Coordenadas onde o centro da Cruz será posicionado
     * @param tamanho Um valor maior que zero e positivo
     * @param cor A cor da Cruz
     * @param corBorda A cor da borda da Cruz. Nesse caso a borda será ativada
     * automaticamente
     */
    public Cruz(Coordenadas posicaoNaTela, int tamanho, Color cor, Color corBorda){
        this(posicaoNaTela, tamanho, cor, true);
        this.corBorda = corBorda;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: Auxiliares">
    /**
     * Inicializa o desenho da Cruz.
     */
    private void initCruz(){
        cruz = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
        arrumaCoordenadasDesenho();
    }
    
    /**
     * Toda vez que alguma coordenada da Cruz é alterada, esse método deve ser
     * chamado para atualizar o desenho da Cruz.
     */
    private void arrumaCoordenadasDesenho(){
        //limpa o desenho anterior
        cruz.reset();
        
        //linha vertical
        cruz.moveTo(getX(), getY() - tamanho);
        cruz.lineTo(getX(), getY() + tamanho);
        
        //linha horizontal
        cruz.moveTo(getX() - tamanho, getY());
        cruz.lineTo(getX() + tamanho, getY());
    }
    //</editor-fold>
    
    /**
     * Ativa a borda da Cruz.
     */
    public void ativarBorda(){
        bordaAtiva = true;
    }
    
    /**
     * Desativa a borda da Cruz.
     */
    public void desativarBorda(){
        bordaAtiva = false;
    }
    
    /**
     * Retorna o estado da borda da Cruz.
     * @return boolean
     */
    public boolean isBordaAtiva(){
        return bordaAtiva;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Coordenavel">
    @Override
    public void setX(double x) {
        posicaoNaTela.setX(x);
        arrumaCoordenadasDesenho();
    }

    @Override
    public void setY(double y) {
        posicaoNaTela.setY(y);
        arrumaCoordenadasDesenho();
    }

    @Override
    public void incrementaX(double aumentoX) {
        posicaoNaTela.incrementaX(aumentoX);
        arrumaCoordenadasDesenho();
    }

    @Override
    public void incrementaY(double aumentoY) {
        posicaoNaTela.incrementaY(aumentoY);
        arrumaCoordenadasDesenho();
    }

    @Override
    public void setCoordenadas(double x, double y) {
        posicaoNaTela.setCoordenadas(x, y);
        arrumaCoordenadasDesenho();
    }
    
    @Override
    public void setCoordenadas(Coordenadas novasCoordenadas) {
        posicaoNaTela.setCoordenadas(novasCoordenadas);
        arrumaCoordenadasDesenho();
    }
    
    @Override
    public void incrementarCoordenadas(double aumentoX, double aumentoY) {
        posicaoNaTela.incrementarCoordenadas(aumentoX, aumentoY);
        arrumaCoordenadasDesenho();
    }

    @Override
    public double getX() {
        return posicaoNaTela.getX();
    }

    @Override
    public int getIntX() {
        return posicaoNaTela.getIntX();
    }

    @Override
    public double getY() {
        return posicaoNaTela.getY();
    }

    @Override
    public int getIntY() {
        return posicaoNaTela.getIntY();
    }
    
    @Override
    public Coordenadas getCoordenadas() {
        return posicaoNaTela;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
    @Override
    public void desenha(Graphics2D g2d) {
        BasicStroke contornoOriginal = new BasicStroke(0);
        
        if (bordaAtiva) {
            g2d.setColor(corBorda);
            BasicStroke contorno = new BasicStroke(3);
            g2d.setStroke(contorno);
            g2d.draw(cruz);
        }        
        
        g2d.setStroke(contornoOriginal);
        g2d.setColor(cor);        
        g2d.draw(cruz);
    }
    //</editor-fold>
    
}
