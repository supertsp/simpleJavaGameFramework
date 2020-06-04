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

package simplegamework.ui;

import simplegamework.cor.CorRGBA;
import simplegamework.objetoBasico.CaixaColisao;
import simplegamework.objetoBasico.Coordenadas;
import simplegamework.padraoProjeto.Atualizavel;
import simplegamework.padraoProjeto.Coordenavel;
import simplegamework.padraoProjeto.Desenhavel;
import simplegamework.texto.Texto;
import java.awt.Graphics2D;

/**
 * Uma Faixa Informativa Movimentável é composta por duas faixas e um texto entre as duas.
 * Ela é muito útil para exibir um feedback para o usuário pois ela sempre se 
 * movimenta no eixo x, já caso seja necessário que ela pare o movimento, basta
 * ativar esse recuroso pelo construtor da classe.
 * <br>
 * <br><small>Created on : 30/09/2015, 19:08:31</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class FaixaInformativaMovimentavel implements Coordenavel, 
        Desenhavel, Atualizavel{
    
    protected double x;
    protected double y;
    protected double velocidade;
    
    protected CaixaColisao faixaSuperior;
    protected CaixaColisao faixaInferior;
    protected CorRGBA corFaixa;
    
    protected double margemEntreTextoFaixa;
    
    protected Texto texto;
    protected CorRGBA corTexto;
    protected double yCorrecaoPosicaoTexto;
    protected double xDifFaixaTexto;
    
    protected boolean pararadaMovimentoAtivado;
    protected double xParadaMovimento;
    protected boolean atingiuXParada;
    
    protected boolean movimentandoParaDireita;
    protected boolean movimentandoParaEsquerda;
        
    //<editor-fold defaultstate="collapsed" desc="Construtor">
    /**
     * Construtor sem opção de parada de movimento.
     * @param x A posição inicial da faixa
     * @param y A posição inicial da faixa
     * @param corCadaFaixa A cor das faixas
     * @param comprimentoCadaFaixa O comprimento de cada faixa
     * @param alturaCadaFaixa A altura de cada faixa
     * @param margemEntreTextoFaixa O espaço entre cada faixa e o texto
     * @param texto O texto a ser exibido
     * @param xTexto A posição inicial do texto
     * @param tamanhoTexto O tamanho da font do texto
     * @param yCorrecaoPosicaoTexto Como cada font tem sua variação espacial 
     * (umas são mais condensadas que as outras), o ajuste do texto (margem) entre cada 
     * faixa pode não dar certo, sendo assim, esse valor ajuda no ajuste para
     * a margem esperada
     * @param corTexto A cor do texto
     * @param URLFontTexto A URL do arquivo de font
     * @param velocidade A velocidade do movimento
     */
    public FaixaInformativaMovimentavel(
            double x, double y, CorRGBA corCadaFaixa,
            int comprimentoCadaFaixa, int alturaCadaFaixa,
            double margemEntreTextoFaixa,
            String texto, double xTexto, double tamanhoTexto,
            double yCorrecaoPosicaoTexto, CorRGBA corTexto,
            String URLFontTexto,
            double velocidade
    ){   
        this.x = x;
        this.y = y;
        this.corFaixa = corCadaFaixa;
        this.margemEntreTextoFaixa = margemEntreTextoFaixa;
        this.corTexto = corTexto;
        this.velocidade = velocidade;
        this.yCorrecaoPosicaoTexto = yCorrecaoPosicaoTexto;
        
        xDifFaixaTexto = xTexto - this.x;
        
        initFaixas(comprimentoCadaFaixa, alturaCadaFaixa);
        initTexto(texto, tamanhoTexto, URLFontTexto);
        corrigeCoordenadas();
    }
    
    /**
     * Construtor com parada de movimento.
     * @param x A posição inicial da faixa
     * @param y A posição inicial da faixa
     * @param corCadaFaixa A cor das faixas
     * @param comprimentoCadaFaixa O comprimento de cada faixa
     * @param alturaCadaFaixa A altura de cada faixa
     * @param margemEntreTextoFaixa O espaço entre cada faixa e o texto
     * @param texto O texto a ser exibido
     * @param xTexto A posição inicial do texto
     * @param tamanhoTexto O tamanho da font do texto
     * @param yCorrecaoPosicaoTexto Como cada font tem sua variação espacial 
     * (umas são mais condensadas que as outras), o ajuste do texto (margem) entre cada 
     * faixa pode não dar certo, sendo assim, esse valor ajuda no ajuste para
     * a margem esperada
     * @param corTexto A cor do texto
     * @param URLFontTexto A URL do arquivo de font
     * @param xParadaMovimento A posição onde o será encerrado o movimento
     * @param velocidade A velocidade do movimento
     */
    public FaixaInformativaMovimentavel(
            double x, double y, CorRGBA corCadaFaixa,
            int comprimentoCadaFaixa, int alturaCadaFaixa,
            double margemEntreTextoFaixa,
            String texto, double xTexto, double tamanhoTexto,
            double yCorrecaoPosicaoTexto, CorRGBA corTexto,
            String URLFontTexto,
            double xParadaMovimento, double velocidade
    ){
        this(x, y, corCadaFaixa, comprimentoCadaFaixa, alturaCadaFaixa, 
                margemEntreTextoFaixa, texto, xTexto, tamanhoTexto, yCorrecaoPosicaoTexto, 
                corTexto, URLFontTexto, velocidade);
        pararadaMovimentoAtivado = true;
        this.xParadaMovimento = xParadaMovimento;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método: Auxliar">
    /**
     * Inicializador das faixas.
     * @param comprimentoFaixa O comprimento das faixas
     * @param alturaFaixa A altura das faixas
     */
    private void initFaixas(int comprimentoFaixa, int alturaFaixa){
        faixaSuperior = new CaixaColisao(x, 0, comprimentoFaixa, alturaFaixa);
        faixaSuperior.setCorPreenchimento(this.corFaixa);        
        faixaInferior = new CaixaColisao(x, 0, comprimentoFaixa, alturaFaixa);
        faixaInferior.setCorPreenchimento(this.corFaixa);
    }
    
    /**
     * Inicializador do texto.
     * @param texto O texto a ser exibido
     * @param tamanhoTexto O tamanho da font
     * @param URLFontTexto A URL do arquivo de font
     */
    private void initTexto(String texto, double tamanhoTexto, String URLFontTexto){
        this.texto = new Texto(0, 0, texto, tamanhoTexto, this.corTexto, URLFontTexto);
        this.texto.ativarFiltroExibicao();
    }
    
    /**
     * Corretor das coordenadas das faixas e do texto para que o texto sempre fique
     * entre as faixas.
     */
    private void corrigeCoordenadas(){        
        faixaSuperior.setCoordenadas(x, y);
        
        double xTexto = faixaSuperior.getX() + xDifFaixaTexto;
        double yTexto = faixaSuperior.getY() + faixaSuperior.getAltura() + margemEntreTextoFaixa;
        yTexto += yCorrecaoPosicaoTexto;
        texto.setCoordenadas(xTexto, yTexto);
        
        double yLinha = texto.getY() + texto.getAltura() + margemEntreTextoFaixa;
        faixaInferior.setCoordenadas(faixaSuperior.getX(), yLinha);
    }
    //</editor-fold>
    
    /**
     * Retorna o comprimento total.
     * @return int
     */
    public int getComprimento(){
        return faixaSuperior.getComprimento();
    }
    
    /**
     * Retorna a altura total.
     * @return int
     */
    public int getAltura(){
        return faixaSuperior.getAltura() + texto.getAltura() + faixaInferior.getAltura();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: GETs & SETs">
    /**
     * Retorna o valor da margem entre o texto as faixas.
     * @return int
     */
    public double getMargemEntreTextoFaixa() {
        return margemEntreTextoFaixa;
    }
    
    /**
     * Define a margem entre o texto e as faixas.
     * @param margemEntreTextoFaixa O valor da nova margem
     */
    public void setMargemEntreTextoFaixa(double margemEntreTextoFaixa) {
        this.margemEntreTextoFaixa = margemEntreTextoFaixa;
    }
    
    /**
     * Retorna o texto a ser exibido.
     * @return String
     */
    public String getTexto() {
        return texto.getTexto();
    }
    
    /**
     * Altera o texto a ser exibido.
     * @param texto O novo texto para ser exibido
     */
    public void setTexto(String texto) {
        this.texto.setTexto(texto);
    }
    
    /**
     * Retorna o valor de correção do texto no eixo y.
     * @return double
     */
    public double getYCorrecaoPosicaoTexto(){
        return yCorrecaoPosicaoTexto;
    }
    
    /**
     * Altera o valor de correção do texto no eixo y.
     * @param novoAjuste O valor do novo ajuste
     */
    public void setYCorrecaoPosicaoTexto(double novoAjuste){
        yCorrecaoPosicaoTexto = novoAjuste;
    }
    
    /**
     * Retorna um boolean que informa se está ativa a parada de movimento
     * @return boolean
     */
    public boolean isPararadaMovimentoAtivado() {
        return pararadaMovimentoAtivado;
    }
    
    /**
     * Retorna a posição de parada no eixo x.
     * @return double
     */
    public double getXParadaMovimento() {
        return xParadaMovimento;
    }
    
    /**
     * Retorna um boolean que informa se o movimento cessou.
     * @return boolean
     */
    public boolean isAtingiuXParada(){
        return atingiuXParada;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Coordenavel">
    @Override
    public void setX(double x) {
        this.x = x;
        corrigeCoordenadas();
    }

    @Override
    public void setY(double y) {
        this.y = y;
        corrigeCoordenadas();
    }

    @Override
    public void incrementaX(double aumentoX) {
        x += aumentoX;
        corrigeCoordenadas();
    }

    @Override
    public void incrementaY(double aumentoY) {
        y += aumentoY;
        corrigeCoordenadas();
    }

    @Override
    public void setCoordenadas(double x, double y) {
        this.x = x;
        this.y = y;
        corrigeCoordenadas();
    }

    @Override
    public void setCoordenadas(Coordenadas novasCoordenadas) {
        x = novasCoordenadas.getX();
        y = novasCoordenadas.getY();
        corrigeCoordenadas();
    }

    @Override
    public void incrementarCoordenadas(double aumentoX, double aumentoY) {
        x += aumentoX;
        y += aumentoY;
        corrigeCoordenadas();
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public int getIntX() {
        return (int)x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public int getIntY() {
        return (int)y;
    }

    @Override
    public Coordenadas getCoordenadas() {
        return new Coordenadas(x, y);
    }
    //</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="Métodos: Movimentar">    
    /**
     * Altera a velocidade do movimento no eixo x.
     * @param velocidade O valor da nova velocidade
     */
    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }
    
    /**
     * Aumenta o valor da velocidade do movimento no eixo x.
     * @param aumentoVelocidade O valor do incremento
     */
    public void incrementarVelocidade(double aumentoVelocidade) {
        velocidade += aumentoVelocidade;
    }
    
    /**
     * Retorna o valor da velocidade do movimento no eixo x.
     * @return double
     */
    public double getVelocidade() {
        return velocidade;
    }
    
    /**
     * Retorna o valor da velocidade do movimento no eixo x como um inteiro.
     * @return int
     */
    public int getIntVelocidade(){
        return (int)getVelocidade();
    }
    
    /**
     * Ativa o movimento para direita.
     */
    public void movimentarParaDireita() {
        movimentandoParaDireita = true;
        movimentandoParaEsquerda = false;
    }
    
    /**
    * Ativa o movimento para esquerda.
    */
    public void movimentarParaEsquerda() {
        movimentandoParaDireita = false;
        movimentandoParaEsquerda = true;
    }
    
    /**
     * Paraliza o movimento.
     */
    public void pararMovimento(){
        movimentandoParaDireita = false;
        movimentandoParaEsquerda = false;
        atingiuXParada = true;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
    @Override
    public void desenha(Graphics2D g2d) {
        faixaSuperior.desenha(g2d);
        texto.desenha(g2d);
        faixaInferior.desenha(g2d);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Atualizavel">
    @Override
    public void atualiza() {
        faixaSuperior.atualiza();
        texto.atualiza();
        faixaInferior.atualiza();
        
        if (movimentandoParaDireita && !atingiuXParada) {
            incrementaX(velocidade);
        }
        
        if (movimentandoParaEsquerda && !atingiuXParada) {
            incrementaX(-velocidade);
        }
        
        if (isPararadaMovimentoAtivado() && movimentandoParaDireita && getX() >= getXParadaMovimento()) {
            pararMovimento();
            setX(getXParadaMovimento());
        }
        
        
        if (isPararadaMovimentoAtivado() && movimentandoParaEsquerda && getX() <= getXParadaMovimento()) {
            pararMovimento();
            setX(getXParadaMovimento());
        }
    }
    //</editor-fold>
    
}
