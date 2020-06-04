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

import simplegamework.padraoProjeto.Atualizavel;
import simplegamework.padraoProjeto.Dimensionavel;
import simplegamework.padraoProjeto.Movimentavel;
import simplegamework.padraoProjeto.AtualizavelListManuseavel;
import simplegamework.padraoProjeto.Colidivel;
import simplegamework.padraoProjeto.Desenhavel;
import simplegamework.padraoProjeto.Coordenavel;
import simplegamework.cor.CorRGBA;
import simplegamework.ferramenta.AtualizavelList;
import simplegamework.ferramenta.MedidaTempo;
import simplegamework.ferramenta.TempoExecucao;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Caixa (Retangulo) que gerencia as colisões com outras caixas.
 * <br><br><small>Created on : 18/06/2015, 16:46:40</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class CaixaColisao implements Coordenavel, Movimentavel, Dimensionavel, 
        Colidivel, Desenhavel, Atualizavel, AtualizavelListManuseavel{
    
    protected Object rotulo;
    protected Coordenadas coordPivo;
    protected int comprimento;
    protected int altura;
    protected CorRGBA corBorda;
    protected double espessuraBorda;
    protected boolean corBordaAtivada;
    protected CorRGBA corPreenchimento;
    protected TempoExecucao intermitenciaCorPreenchimento;
    
    protected AtualizavelList atualizaveis;
    
    //Auxiliares
    private boolean corPreenchimentoLigada;
    
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    /**
     * Construtor padrão de classe que já atribui a Color.GREEN para borda.
     * @param x O valor da coordenada x
     * @param y O valor da coordeanda y
     * @param comprimento O valor do comprimento
     * @param altura O valor da altura
     */
    public CaixaColisao(double x, double y, int comprimento, int altura){
        this.comprimento = comprimento > 0 ? comprimento : 0;
        this.altura = altura > 0 ? altura : 0;
        corBorda = new CorRGBA(Color.GREEN);
        espessuraBorda = 0;
        
        if (this.comprimento > 0 && this.altura > 0) {
            coordPivo = new Coordenadas(x, y);
        }
        
        atualizaveis = new AtualizavelList();
    }
    
    /**
     * Construtor que permite escolher a cor da borda.
     * @param x O valor da coordenada x
     * @param y O valor da coordeanda y
     * @param comprimento O valor do comprimento
     * @param altura O valor da altura
     * @param corBorda O valor da cor da borda
     */
    public CaixaColisao(double x, double y, int comprimento, int altura, CorRGBA corBorda){
        this(x, y, comprimento, altura);
        this.corBorda = corBorda;
        if (corBorda != null) {
            corBordaAtivada = true;
        }
    }
    
    /**
     * Construtor que permite escolher a cor da borda e do preenchimento.
     * @param x O valor da coordenada x
     * @param y O valor da coordeanda y
     * @param comprimento O valor do comprimento
     * @param altura O valor da altura
     * @param corBorda O valor da cor da borda
     * @param corPreenchimento O valor da cor do preenchimento
     */
    public CaixaColisao(double x, double y, int comprimento, int altura, 
            CorRGBA corBorda, CorRGBA corPreenchimento){
        this(x, y, comprimento, altura, corBorda);
        this.corPreenchimento = corPreenchimento;
        
        if (corPreenchimento != null) {
            corPreenchimentoLigada = true;
        }
    }
    
    /**
     * Construtor básico que permite adicionar um rótulo, mas que já atribui o 
     * valor Color.GREEN para cor da borda.
     * @param rotulo O valor do rótulo
     * @param x O valor da coordenada x
     * @param y O valor da coordeanda y
     * @param comprimento O valor do comprimento
     * @param altura O valor da altura
     */
    public CaixaColisao(Object rotulo, double x, double y, int comprimento, int altura){
        this(x, y, comprimento, altura);
        this.rotulo = rotulo;
    }
    
    /**
     * Construtor básico que permite adicionar um rótulo e a cor da borda.
     * @param rotulo O valor do rótulo
     * @param x O valor da coordenada x
     * @param y O valor da coordeanda y
     * @param comprimento O valor do comprimento
     * @param altura O valor da altura
     * @param corBorda O valor da cor da borda
     */
    public CaixaColisao(
            Object rotulo, double x, double y, int comprimento, 
            int altura, CorRGBA corBorda
    ){
        this(x, y, comprimento, altura, corBorda);
        this.rotulo = rotulo;
    }
    
    /**
     * Construtor completo que permite alterar todos atributos.
     * @param rotulo O valor do rótulo
     * @param x O valor da coordenada x
     * @param y O valor da coordeanda y
     * @param comprimento O valor do comprimento
     * @param altura O valor da altura
     * @param corBorda O valor da cor da borda
     * @param corPreenchimento O valor da cor do preenchimento
     */
    public CaixaColisao(
            Object rotulo, double x, double y, int comprimento, 
            int altura, CorRGBA corBorda, CorRGBA corPreenchimento
    ){
        this(x, y, comprimento, altura, corBorda, corPreenchimento);
        this.rotulo = rotulo;        
    }
    //</editor-fold>
        
    /**
     * Usado para descobrir o valor de x e y na diagonal inferior direita da 
     * CaixaColisao.
     * @return Coordenadas
     */
    public Coordenadas getCoordenadasInferiorDireita() {
        int x = (coordPivo.getIntX() + comprimento - 1);
        int y = (coordPivo.getIntY() + altura - 1);
        return new Coordenadas(x, y);
    }
    
    /**
     * Usado para descobrir as coordenadas x e y do meio da CaixaColisao
     * @return Coordenadas
     */
    public Coordenadas getCoordenadasCentrais() {
        int x = (int) ((comprimento / 2.0) + coordPivo.getX());
        int y = (int) ((altura / 2.0) + coordPivo.getY());
        return new Coordenadas(x, y);
    }
    
    /**
     * Usado para descobrir as coordenadas x e y em qualquer lado ou diagonal 
     * da CaixaColisão.<br>
     * Ex.:
     * <pre>
     * (1)---(C)---(2)
     *  |           |
     * (E)         (D)
     *  |           |
     * (3)---(B)---(4) 
     * </pre>
     * <ul>
     * <li><b>1: </b>LadoRetangulo.ESQUERDA_CIMA</li>
     * <li><b>2: </b>LadoRetangulo.DIREITA_CIMA</li>
     * <li><b>3: </b>LadoRetangulo.ESQUERDA_BAIXO</li>
     * <li><b>4: </b>LadoRetangulo.DIREITA_BAIXO</li>
     * <li><b>E: </b>LadoRetangulo.ESQUERDA</li>
     * <li><b>D: </b>LadoRetangulo.DIREITA</li>
     * <li><b>C: </b>LadoRetangulo.CIMA</li>
     * <li><b>B: </b>LadoRetangulo.BAIXO</li>
     * </ul>
     * @param ladoCoordenadas Enum que representa todos os lados de um retângulo
     * @return Coordenadas
     */
    public Coordenadas getLadoCoordenadas(LadoRetangulo ladoCoordenadas){
        Coordenadas ladoQualquer = null;
        
        //<editor-fold defaultstate="collapsed" desc="Diagonais">
        if (ladoCoordenadas == LadoRetangulo.ESQUERDA_CIMA) {
            ladoQualquer = coordPivo;
        }
        
        if (ladoCoordenadas == LadoRetangulo.DIREITA_CIMA) {
            ladoQualquer = new Coordenadas(
                    getCoordenadasInferiorDireita().getX(), getY()
            );
        }
        
        if (ladoCoordenadas == LadoRetangulo.ESQUERDA_BAIXO) {
            ladoQualquer = new Coordenadas(
                    getX(), getCoordenadasInferiorDireita().getY()
            );
        }
        
        if (ladoCoordenadas == LadoRetangulo.DIREITA_BAIXO) {
            ladoQualquer = getCoordenadasInferiorDireita();
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Laterais">
        if (ladoCoordenadas == LadoRetangulo.ESQUERDA) {
            ladoQualquer = new Coordenadas(
                    getX(), (getY() / 2.0)
            );
        }
        
        if (ladoCoordenadas == LadoRetangulo.DIREITA) {
            ladoQualquer = new Coordenadas(
                    getCoordenadasInferiorDireita().getX(), (getY() / 2.0)
            );
        }
        
        if (ladoCoordenadas == LadoRetangulo.CIMA) {
            ladoQualquer = new Coordenadas(
                    (getX() / 2.0), getY()
            );
        }
        
        if (ladoCoordenadas == LadoRetangulo.BAIXO) {
            ladoQualquer = new Coordenadas(
                    (getX() / 2.0), getCoordenadasInferiorDireita().getY()
            );
        }
        //</editor-fold>
        
        return ladoQualquer;
    }

    /**
     * Usado para descobrir o valor da área da CaixaColisao.
     * @return int
     */
    public int getArea() {
        return comprimento * altura;
    }

    /**
     * Usado para descobrir o valor do perímetro da CaixaColisao.
     * @return int
     */
    public int getPerimetro() {
        return 2 * (comprimento + altura);
    }

    /**
     * Usado para descobrir o comprimento da diagonal da CaixaColisao.
     * @return int
     */
    public int getComprimentoDiagonal() {
        return (int) (Math.sqrt((comprimento * comprimento) + (altura * altura)));
    }
    
    /**
     * Ativa o debugGrafico, ou seja, escreve os atributos de Coordenadas na tela.
     */
    public void ligarDebugGraficoCoordenadas(){
        coordPivo.ligarDebugGrafico();
    }
    
    /**
     * Desativa o debugGrafico, ou seja, não serão exibidos os atributos de
     * Coordenadas na tela.
     */
    public void desligarDebugGraficoCoordenadas(){
        coordPivo.desligarDebugGrafico();
    }
    
    /**
     * Retorna o estado atual de debugGrafico.
     * @return boolean
     */
    public boolean isDebugGraficoAtivoCoordenadas(){
        return coordPivo.isDebugGraficoAtivo();
    }
    
    //<editor-fold defaultstate="collapsed" desc="GETs & SETs">
    /**
     * Retorna o rótulo da CaixaColisao.
     * @return Object
     */
    public Object getRotulo(){
        return rotulo;
    }
    
    /**
     * Altera a cor da borda da CaixaColisao.
     * @param novaCorBorda A nova cor da borda
     */
    public void setCorBorda(CorRGBA novaCorBorda){
        corBorda = novaCorBorda;
    }
    
    /**
     * Retorna a cor da borda da CaixaColisao.
     * @return Color
     */
    public CorRGBA getCorBorda(){
        return corBorda;
    }
    
    /**
     * Altera a espessura da borda da CaixaColisao.
     * @param espessura O novo valor da espessura
     */
    public void setEspessuraBorda(double espessura){
        if (espessura >= 0) {
            espessuraBorda = espessura;
        }        
    }
    
    /**
     * Retorna o valor da espessura da borda da CaixaColisao.
     * @return double
     */
    public double getEspessuraBorda(){
        return espessuraBorda;
    }
    
    /**
     * Ativa a exibição da cor da borda da CaixaColisao.
     */
    public void ativarCorBorda(){
        corBordaAtivada = true;
    }
    
    /**
     * Desativa a exibição da cor da borda da CaixaColisao.
     */
    public void desativarCorBorda(){
        corBordaAtivada = false;
    }
    
    /**
     * Retorna o estado de exibição da cor da borda da CaixaColisao.
     * @return boolean
     */
    public boolean isCorBordaAtivada(){
        return corBordaAtivada;
    }
    
    /**
     * Altera a cor do preenchimento.
     * @param novaCorPreenchimento A nova cor
     */
    public void setCorPreenchimento(CorRGBA novaCorPreenchimento){
        corPreenchimento = novaCorPreenchimento;
        corPreenchimentoLigada = true;
    }
    
    /**
     * Retorna a cor do prenchimento.
     * @return CorRGBA
     */
    public CorRGBA getCorPreenchimento(){
        return corPreenchimento;
    }
    
    /**
     * Inicializa o tempo de duração de exibição da corPreenchimento gerando um
     * efeito pisca-pisca.
     * @param milisegundos Tempo de duração de exibição da corPreenchimento
     */
    public void setIntermitenciaCorPreenchimento(int milisegundos){
        intermitenciaCorPreenchimento = new TempoExecucao(milisegundos, MedidaTempo.MILISSEGUNDOS);
        intermitenciaCorPreenchimento.iniciarTempo();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Coordenavel">
    @Override
    public void setX(double x) {
        coordPivo.setX(x);
    }

    @Override
    public void setY(double y) {
        coordPivo.setY(y);
    }

    @Override
    public void incrementaX(double aumentoX) {
        coordPivo.incrementaX(aumentoX);
    }

    @Override
    public void incrementaY(double aumentoY) {
        coordPivo.incrementaY(aumentoY);
    }

    @Override
    public void setCoordenadas(double x, double y) {
        coordPivo.setCoordenadas(x, y);
    }
    
    @Override
    public void setCoordenadas(Coordenadas novasCoordenadas) {
        coordPivo.setCoordenadas(novasCoordenadas);
    }
    
    @Override
    public void incrementarCoordenadas(double aumentoX, double aumentoY) {
        coordPivo.incrementarCoordenadas(aumentoX, aumentoY);
    }

    @Override
    public double getX() {
        return coordPivo.getX();
    }

    @Override
    public int getIntX() {
        return coordPivo.getIntX();
    }

    @Override
    public double getY() {
        return coordPivo.getY();
    }

    @Override
    public int getIntY() {
        return coordPivo.getIntY();
    }
    
    @Override
    public Coordenadas getCoordenadas() {
        return coordPivo;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Movimentavel">
    @Override
    public void setVelX(double velX) {
        coordPivo.setVelX(velX);
    }

    @Override
    public void setVelY(double velY) {
        coordPivo.setVelY(velY);
    }

    @Override
    public void incrementaVelX(double aumentoVelX) {
        coordPivo.incrementaVelX(aumentoVelX);
    }

    @Override
    public void incrementaVelY(double aumentoVelY) {
        coordPivo.incrementaVelY(aumentoVelY);
    }

    @Override
    public void setVelocidades(double velX, double velY) {
        coordPivo.setVelocidades(velX, velY);
    }

    @Override
    public void incrementarVelocidades(double aumentoVelX, double aumentoVelY) {
        coordPivo.incrementarVelocidades(aumentoVelX, aumentoVelY);
    }

    @Override
    public double getVelX() {
        return coordPivo.getVelX();
    }

    @Override
    public int getIntVelX() {
        return coordPivo.getIntVelX();
    }

    @Override
    public double getVelY() {
        return coordPivo.getVelY();
    }

    @Override
    public int getIntVelY() {
        return coordPivo.getIntVelY();
    }

    @Override
    public void movimentar(TipoMovimento novoMovimento) {
        coordPivo.movimentar(novoMovimento);
    }
    
    @Override
    public void movimentar(double direcaoEmGraus) {
        coordPivo.movimentar(direcaoEmGraus);
    }
    
    @Override
    public void movimentarCurvaQuadratica(
            double alturaX, double alturaY, 
            double comprimentoX, double comprimentoY){
        coordPivo.movimentarCurvaQuadratica(alturaX, alturaY, comprimentoX, comprimentoY);
    }
    
    @Override
    public void movimentarRepetidamente(
            double indiceDeformardorX, double indiceDeformardorY,
            double comprimento, double altura, boolean inverter
    ){
        coordPivo.movimentarRepetidamente(
                indiceDeformardorX, indiceDeformardorY, 
                comprimento, altura, inverter
        );
    }
    
    @Override
    public void movimentarPorFuncao(String funcao, boolean iniciarEmCoordNegativa){
        coordPivo.movimentarPorFuncao(funcao, iniciarEmCoordNegativa);
    }
    
    @Override
    public void pararMovimento() {
        coordPivo.pararMovimento();
    }
    
    @Override
    public double calcularFuncao(String funcao) {
        return coordPivo.calcularFuncao(funcao);
    }

    @Override
    public double calcularFuncao(String funcao, double intervaloIncial, double intervaloFinal) {
        return coordPivo.calcularFuncao(funcao, intervaloIncial, intervaloFinal);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Dimensionavel">
    @Override
    public void redimensionar(double escala) {
        comprimento = (int)(comprimento * escala);
        altura = (int)(altura * escala);
    }

    @Override
    public int getComprimento() {
        return comprimento;
    }

    @Override
    public int getAltura() {
        return altura;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Colidivel">
    @Override
    public CaixaColisao getCaixaColisao(){
        return this;
    }
    
    @Override
    public boolean colidiu(CaixaColisao outraCaixaColisao, TipoColisao tipoColisao) {
        LadoRetangulo lado = colidiuNoLado(outraCaixaColisao, tipoColisao);
        return lado != null;
    }

    @Override
    public LadoRetangulo colidiuNoLado(CaixaColisao outraCaixaColisao, TipoColisao tipoColisao) {
        LadoRetangulo lado = null;
        boolean esquerda = false,
                direita = false,
                cima = false,
                baixo = false;

        //<editor-fold defaultstate="collapsed" desc="Colisoes INTERNAS">
        if (tipoColisao == TipoColisao.INTERNA) {
            if (    
                (getIntX() + getComprimento()) >=
                (outraCaixaColisao.getIntX() + outraCaixaColisao.getComprimento())
            ) {
                lado = LadoRetangulo.DIREITA;
                direita = true;
            }

            if (
                    (getIntY() + getAltura()) >=
                    (outraCaixaColisao.getIntY() + outraCaixaColisao.getAltura())
            ) {
                lado = LadoRetangulo.BAIXO;
                baixo = true;
            }

            if (getIntX() <= outraCaixaColisao.getIntX()) {
                lado = LadoRetangulo.ESQUERDA;
                esquerda = true;
            }

            if (getIntY() <= outraCaixaColisao.getIntY()) {
                lado = LadoRetangulo.CIMA;
                cima = true;
            }

            if (esquerda && cima) {
                lado = LadoRetangulo.ESQUERDA_CIMA;
            }

            if (esquerda && baixo) {
                lado = LadoRetangulo.ESQUERDA_BAIXO;
            }

            if (direita && cima) {
                lado = LadoRetangulo.DIREITA_CIMA;
            }

            if (direita && baixo) {
                lado = LadoRetangulo.DIREITA_BAIXO;
            }
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Colisoes EXTERNAS">
        if (tipoColisao == TipoColisao.EXTERNA) {
            if (
                    ((getIntX() + getComprimento()) > outraCaixaColisao.getIntX())
                    && (getIntX() < (outraCaixaColisao.getIntX() + outraCaixaColisao.getComprimento()))
                    && ((getIntY() + getAltura()) > outraCaixaColisao.getIntY())
                    && (getIntY() < (outraCaixaColisao.getIntY() + outraCaixaColisao.getAltura()))
            ) {
                //ATRIBUIÇÃO DAS  AUXILIARES
                if (getIntX() <= outraCaixaColisao.getIntX()) {
                    direita = true;
                }

                if (getIntX() >= outraCaixaColisao.getIntX()) {
                    esquerda = true;
                }

                if (getIntY() >= outraCaixaColisao.getIntY()) {
                    cima = true;
                }

                if (getIntY() <= outraCaixaColisao.getIntY()) {
                    baixo = true;
                }

                //VALIDANDO AS AUXILIARES
                if (esquerda) {
                    lado = LadoRetangulo.ESQUERDA;
                }

                if (direita) {
                    lado = LadoRetangulo.DIREITA;
                }

                if (cima) {
                    lado = LadoRetangulo.CIMA;
                }

                if (baixo) {
                    lado = LadoRetangulo.BAIXO;
                }

                if (esquerda && cima) {
                    lado = LadoRetangulo.ESQUERDA_CIMA;
                }

                if (esquerda && baixo) {
                    lado = LadoRetangulo.ESQUERDA_BAIXO;
                }

                if (direita && cima) {
                    lado = LadoRetangulo.DIREITA_CIMA;
                }

                if (direita && baixo) {
                    lado = LadoRetangulo.DIREITA_BAIXO;
                }

                //CORRIGINDO OS BUGS DE QDO UM OBJETO É MENOR E ENTRA NO PRINCIPAL
                //Analisando CIMA, BAIXO
                if (
                    getIntX() <= outraCaixaColisao.getIntX()
                    &&  getCoordenadasInferiorDireita().getIntX() >= 
                        outraCaixaColisao.getCoordenadasInferiorDireita().getIntX()
                    && getIntY() >= outraCaixaColisao.getIntY()
                ) {
                    lado = LadoRetangulo.CIMA;
                }

                if (
                    getIntX() <= outraCaixaColisao.getIntX()
                    &&  getCoordenadasInferiorDireita().getIntX() >= 
                        outraCaixaColisao.getCoordenadasInferiorDireita().getIntX()
                    && getIntY() <= outraCaixaColisao.getIntY()
                ) {
                    lado = LadoRetangulo.BAIXO;
                }

                if (
                    getIntX() >= outraCaixaColisao.getIntX()
                    &&  getCoordenadasInferiorDireita().getIntX() <= 
                        outraCaixaColisao.getCoordenadasInferiorDireita().getIntX()
                    && getIntY() <= outraCaixaColisao.getIntY()
                ) {
                    lado = LadoRetangulo.BAIXO;
                }

                if (
                    getIntX() >= outraCaixaColisao.getIntX()
                    &&  getCoordenadasInferiorDireita().getIntX() <=
                        outraCaixaColisao.getCoordenadasInferiorDireita().getIntX()
                    && getIntY() >= outraCaixaColisao.getIntY()
                ) {
                    lado = LadoRetangulo.CIMA;
                }

                //Analisando ESQUERDA, DIREITA
                if (
                    getIntY() <= outraCaixaColisao.getIntY()
                    &&  getCoordenadasInferiorDireita().getIntY() >= 
                        outraCaixaColisao.getCoordenadasInferiorDireita().getIntY()
                    && getIntX() >= outraCaixaColisao.getIntX()
                ) {
                    lado = LadoRetangulo.ESQUERDA;
                }

                if (
                    getIntY() <= outraCaixaColisao.getIntY()
                    &&  getCoordenadasInferiorDireita().getIntY() >=
                        outraCaixaColisao.getCoordenadasInferiorDireita().getIntY()
                    && getIntX() <= outraCaixaColisao.getIntX()
                ) {
                    lado = LadoRetangulo.DIREITA;
                }

                if (
                    getIntY() >= outraCaixaColisao.getIntY()
                    &&  getCoordenadasInferiorDireita().getIntY() <= 
                        outraCaixaColisao.getCoordenadasInferiorDireita().getIntY()
                    && getIntX() >= outraCaixaColisao.getIntX()
                ) {
                    lado = LadoRetangulo.ESQUERDA;
                }

                if (
                    getIntY() >= outraCaixaColisao.getIntY()
                    &&  getCoordenadasInferiorDireita().getIntY() <=
                        outraCaixaColisao.getCoordenadasInferiorDireita().getIntY()
                    && getIntX() <= outraCaixaColisao.getIntX()
                ) {
                    lado = LadoRetangulo.DIREITA;
                }
            }
        }
        //</editor-fold>

        return lado;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
    @Override
    public void desenha(Graphics2D g2d) {
        coordPivo.desenha(g2d);
        
        if (corPreenchimento != null) {
            if (corPreenchimentoLigada) {
                g2d.setColor(corPreenchimento.getColor());
                g2d.fillRect(getIntX(), getIntY(), getComprimento(), getAltura());
                
                if (intermitenciaCorPreenchimento != null && 
                        intermitenciaCorPreenchimento.atingiuMeta()) {
                    corPreenchimentoLigada = false;
                    intermitenciaCorPreenchimento.iniciarTempo();
                }
            }
            
            if (intermitenciaCorPreenchimento != null && 
                    intermitenciaCorPreenchimento.atingiuMeta()) {
                corPreenchimentoLigada = true;
                intermitenciaCorPreenchimento.iniciarTempo();
            }
        }
        
        if (corBordaAtivada) {
            BasicStroke contorno = new BasicStroke((float)getEspessuraBorda());
            g2d.setStroke(contorno);
            g2d.setColor(corBorda.getColor());            
            g2d.drawRect(getIntX(), getIntY(), getComprimento(), getAltura());
            contorno = new BasicStroke(0);
            g2d.setStroke(contorno);
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
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Object">
    /**
     * Caso todos os atributos comparados sejam iguais o retorno será true, caso
     * contrário, será false.<br>
     * Atributos:
     * <ul>
     * <li>rotulo</li>
     * <li>coordPivo</li>
     * <li>comprimento</li>
     * <li>altura</li>
     * <li>corBorda</li>
     * </ul>
     * @param caixaColisao Outra CaixaColisao a ser comparada
     * @return boolean
     */
    @Override
    public boolean equals(Object caixaColisao) {
        return  caixaColisao instanceof Coordenadas &&
                ((CaixaColisao) caixaColisao).getRotulo().equals(getRotulo()) &&
                ((CaixaColisao) caixaColisao).getX() == getX() &&
                ((CaixaColisao) caixaColisao).getY() == getY() &&
                ((CaixaColisao) caixaColisao).getVelX() == getVelX() &&
                ((CaixaColisao) caixaColisao).getVelY() == getVelY() &&
                ((CaixaColisao) caixaColisao).getComprimento() == getComprimento() &&
                ((CaixaColisao) caixaColisao).getAltura() == getAltura() &&
                ((CaixaColisao) caixaColisao).getCorBorda().equals(getCorBorda());
    }
    
    
    /**
     * Desenha um retângulo e seus quatro pares de coordenadas para um melhor debug
     * do mesmo e de sua localização na tela.
     * @return Uma String contendo o desenho.
     */
    @Override
    public String toString() {
        String linhaTopoBase = "+-----------------------+";
        String linhaMeio = "|                       |";
        String linhaMeioTemp = "";
        int qtdLinhaMeio = 6;
        String coord1 = "", coord2 = "";
        int tamanhoLinhaCoord = 23;
        String desenhoFinal;
        String espacos = "";
        
        //CABEÇALHO        
        desenhoFinal = "  " + linhaTopoBase + "\n";
        
        for (int cont = 0; cont < qtdLinhaMeio; cont++) {
            linhaMeioTemp += "  " + linhaMeio + "\n";
        }
        
        //COORDENADAS SUPERIORES
        coord1 = getLadoCoordenadas(LadoRetangulo.ESQUERDA_CIMA)
                .toString();

        coord2 = getLadoCoordenadas(LadoRetangulo.DIREITA_CIMA)
                .toString();
        
        int restaEspacos = tamanhoLinhaCoord - (coord1.length() + coord2.length());

        for (int cont = 0; cont < restaEspacos; cont++) {
            espacos += " ";
        }
        
        desenhoFinal += "  |" + coord1 + espacos + coord2 + "|\n" + linhaMeioTemp;
        espacos = "";
        
        //COORDENADAS INFERIORES
        coord1 = getLadoCoordenadas(LadoRetangulo.ESQUERDA_BAIXO)
                .toString();

        coord2 = getLadoCoordenadas(LadoRetangulo.DIREITA_BAIXO)
                .toString();
        
        restaEspacos = tamanhoLinhaCoord - (coord1.length() + coord2.length());

        for (int cont = 0; cont < restaEspacos; cont++) {
            espacos += " ";
        }

        desenhoFinal += "  |" + coord1 + espacos + coord2 + "|\n  " + linhaTopoBase;
        
        return  this.getClass().getSimpleName() + " : " + rotulo + "{\n" +
                "  dimensões: " + getComprimento() + "x" + getAltura() + " px\n" +
                "  coordenadas: x(" + getX() + "), y(" + getY() + ") px\n" +
                "  velocidades: x(" + getVelX() + "), y(" + getVelY() + ") px\n" +
                "  corBorda: " +                    
                    (corBorda == null ?
                    "null" :
                    corBorda.getCodigoHexadecimal()) + "\n" +
                "  corPreenchimento: " +                    
                    (corPreenchimento == null ?
                    "null" :
                    corPreenchimento.getCodigoHexadecimal()) + "\n" +
                "  tempo intermitencia exibição corPreenchimento: " +                    
                    (intermitenciaCorPreenchimento == null ?
                    "0" :
                    intermitenciaCorPreenchimento.getMeta()) + "ms\n" +
                desenhoFinal + "\n" +
                "}";
    }
    //</editor-fold>

    
    
    
}
