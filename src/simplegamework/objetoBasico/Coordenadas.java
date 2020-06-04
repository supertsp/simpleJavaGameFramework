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
 * 
 * ATENÇÃO!!!
 * Este código utiliza um software, ou parte dele, que está sob a licença APACHE 2.0
 * Software: exp4j-0.4.5 - http://www.objecthunter.net/exp4j/
 */
//</editor-fold>

package simplegamework.objetoBasico;

import simplegamework.padraoProjeto.Movimentavel;
import simplegamework.padraoProjeto.Desenhavel;
import simplegamework.padraoProjeto.Coordenavel;
import simplegamework.exp4j.ExpressionBuilder;
import simplegamework.exp4j.Expression;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;

/**
 * Define as coordenadas quer serão utilizadas para controlar objetos e exibições
 * na Tela. No entanto, para fins mais realistas, as coordenadas trabalham com double.
 * <br><br><small>Created on : 03/05/2015, 13:20:41</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class Coordenadas implements Coordenavel, Movimentavel, Desenhavel {

    protected double x;
    protected double y;
    protected double velX;
    protected double velY;
    protected boolean debugGraficoAtivo;
    
    //Auxiliares
    private ArrayList<Point> historicoCoordenadas;
    
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    /**
     * Inicializa as coordenadas x e y.
     * @param x O valor da nova coordenada x em relação à Tela
     * @param y O valor da nova coordenada Y em relação à Tela
     */
    public Coordenadas(double x, double y) {
        this.x = x;
        this.y = y;
        historicoCoordenadas = new ArrayList<>();        
    }
    
    /**
     * Inicializa tantos as coordenadas, como as velocidades delas.
     * @param x O valor da nova coordenada x em relação à Tela
     * @param y O valor da nova coordenada Y em relação à Tela
     * @param velX O novo valor da velocidade de x deverá ser positivo, caso 
     * contrário o valor padrão será 0
     * @param velY O novo valor da velocidade de y deverá ser positivo, caso 
     * contrário o valor padrão será 0
     */
    public Coordenadas(double x, double y, double velX, double velY) {
        this(x, y);
        setVelocidades(velX, velY);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: Auxiliares">
    private final int   METODO_MOVIMENTAR_NENHUM = 0;
    private final int   METODO_MOVIMENTAR = 1;
    private final int   METODO_MOVIMENTAR_CURVA_QUADRATICA = 2;
    private final int   METODO_MOVIMENTAR_REPET = 3;
    private final int   METODO_MOVIMENTAR_FUNCAO = 4;
    private final int   METODO_CALCULA_FUNCAO = 5;
    private final int   METODO_CALCULA_FUNCAO_INTERVALO = 6;
    /**
     * Reinicia os atributos auxiliares para realizar os movimentos
     */
    private void reiniciarAuxiliaresMovimentavel(int metodoExecucao){
        if (metodoExecucao == METODO_MOVIMENTAR_NENHUM) {
            //movimentar
            contQuadradoX = 0;
            contQuadradoY = 0;
            
            //movimentarCurvaQuadratica            
            points = new ArrayList<>();
            contCurvaQuadratica = 0;
            
            //movimentarRepetidamente
            contMovCirculoX = 0;
            contMovCirculoY = 0;
            pivoX = 0;
            pivoY = 0;

            //movimentarPorFuncao
            contMovFuncao = 0;
            
            //calculaFuncao
            contCalculaFuncao = 0;
            
            //calculaFuncaoIntervalo
            contCalculaFuncaoIntervalo = 0;
        }
        
        if (metodoExecucao == METODO_MOVIMENTAR) {            
            //movimentarCurvaQuadratica            
            points = new ArrayList<>();
            contCurvaQuadratica = 0;
            
            //movimentarRepetidamente
            contMovCirculoX = 0;
            contMovCirculoY = 0;
            pivoX = 0;
            pivoY = 0;

            //movimentarPorFuncao
            contMovFuncao = 0;
            
            //calculaFuncao
            contCalculaFuncao = 0;
            
            //calculaFuncaoIntervalo
            contCalculaFuncaoIntervalo = 0;
        }
        
        if (metodoExecucao == METODO_MOVIMENTAR_CURVA_QUADRATICA) {            
            //movimentar
            contQuadradoX = 0;
            contQuadradoY = 0;
            
            //movimentarRepetidamente
            contMovCirculoX = 0;
            contMovCirculoY = 0;
            pivoX = 0;
            pivoY = 0;

            //movimentarPorFuncao
            contMovFuncao = 0;
            
            //calculaFuncao
            contCalculaFuncao = 0;
            
            //calculaFuncaoIntervalo
            contCalculaFuncaoIntervalo = 0;
        }
        
        if (metodoExecucao == METODO_MOVIMENTAR_REPET) {
            //movimentar
            contQuadradoX = 0;
            contQuadradoY = 0;
            
            //movimentarCurvaQuadratica            
            points = new ArrayList<>();
            contCurvaQuadratica = 0;
            
            //movimentarPorFuncao
            contMovFuncao = 0;
            
            //calculaFuncao
            contCalculaFuncao = 0;
            
            //calculaFuncaoIntervalo
            contCalculaFuncaoIntervalo = 0;
        }
        
        if (metodoExecucao == METODO_MOVIMENTAR_FUNCAO) {
            //movimentar
            contQuadradoX = 0;
            contQuadradoY = 0;
            
            //movimentarCurvaQuadratica            
            points = new ArrayList<>();
            contCurvaQuadratica = 0;
            
            //movimentarRepetidamente
            contMovCirculoX = 0;
            contMovCirculoY = 0;
            pivoX = 0;
            pivoY = 0;
            
            //calculaFuncao
            contCalculaFuncao = 0;
            
            //calculaFuncaoIntervalo
            contCalculaFuncaoIntervalo = 0;
        }
        
        if (metodoExecucao == METODO_CALCULA_FUNCAO) {
            //movimentar
            contQuadradoX = 0;
            contQuadradoY = 0;
            
            //movimentarCurvaQuadratica            
            points = new ArrayList<>();
            contCurvaQuadratica = 0;
            
            //movimentarRepetidamente
            contMovCirculoX = 0;
            contMovCirculoY = 0;
            pivoX = 0;
            pivoY = 0;
            
            //movimentarPorFuncao
            contMovFuncao = 0;
            
            //calculaFuncaoIntervalo
            contCalculaFuncaoIntervalo = 0;
        }
        
        if (metodoExecucao == METODO_CALCULA_FUNCAO_INTERVALO) {
            //movimentar
            contQuadradoX = 0;
            contQuadradoY = 0;
            
            //movimentarCurvaQuadratica            
            points = new ArrayList<>();
            contCurvaQuadratica = 0;
            
            //movimentarRepetidamente
            contMovCirculoX = 0;
            contMovCirculoY = 0;
            pivoX = 0;
            pivoY = 0;
            
            //movimentarPorFuncao
            contMovFuncao = 0;
            
            //calculaFuncao
            contCalculaFuncao = 0;
        }
    }
    
    /**
     * Descobre se a função passada é f(x).
     * @param funcao A função a ser analizada
     * @return boolean
     */
    private boolean isFuncaoX(String funcao){
        if (funcao.length() >= 5) {
            //f(?)=
            //012345
            return funcao.substring(0, 5).contains("x");
        }
        
        return false;
    }
    
    /**
     * Descobre se a função passada é f(y).
     * @param funcao A função a ser analizada
     * @return boolean
     */
    private boolean isFuncaoY(String funcao){
        if (funcao.length() >= 5) {
            //f(?)=
            //012345
            return funcao.substring(0, 5).contains("y");
        }
        
        return false;
    }
    
    /**
     * Calcula e retorna o cálculo da função e contador passados por parâmetro.
     * @param funcao A função a ser calculada
     * @param contador O incrementador da função
     * @return double
     */
    private double calculaSolucaoXY(String funcao, double contador){
        funcao = funcao.trim();
        double solucao = 0.0;
        
        if (!funcao.equals("")) { 
            if (isFuncaoX(funcao)) {
                Expression e = new ExpressionBuilder(funcao.substring(5))
                    .variables("x")
                    .build()
                    .setVariable("x", contador);
                solucao = e.evaluate();
            }

            if (isFuncaoY(funcao)) {
                Expression e = new ExpressionBuilder(funcao.substring(5))
                    .variables("y")
                    .build()
                    .setVariable("y", contador);
                solucao = e.evaluate();
            }
        }
        
        return solucao;
    }
    //</editor-fold>
    
    /**
     * Ativa o debugGrafico, ou seja, escreve os atributos de Coordenadas na tela.
     */
    public void ligarDebugGrafico(){
        debugGraficoAtivo = true;
    }
    
    /**
     * Desativa o debugGrafico, ou seja, não serão exibidos os atributos de
     * Coordenadas na tela.
     */
    public void desligarDebugGrafico(){
        debugGraficoAtivo = false;
    }
    
    /**
     * Retorna o estado atual de debugGrafico.
     * @return boolean
     */
    public boolean isDebugGraficoAtivo(){
        return debugGraficoAtivo;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Coordenavel">
    @Override
    public void setX(double x) {
        this.x = x;
        
        if (debugGraficoAtivo) {
            historicoCoordenadas.add(new Point(getIntX(), getIntY()));
        }
    }

    @Override
    public void setY(double y) {
        this.y = y;
        
        if (debugGraficoAtivo) {
            historicoCoordenadas.add(new Point(getIntX(), getIntY()));
        }
    }

    @Override
    public void incrementaX(double aumentoX) {
        x += aumentoX;
        
        if (debugGraficoAtivo) {
            historicoCoordenadas.add(new Point(getIntX(), getIntY()));
        }
    }

    @Override
    public void incrementaY(double aumentoY) {
        y += aumentoY;
        
        if (debugGraficoAtivo) {
            historicoCoordenadas.add(new Point(getIntX(), getIntY()));
        }
    }

    @Override
    public void setCoordenadas(double x, double y) {
        this.x = x;
        this.y = y;
        
        if (debugGraficoAtivo) {
            historicoCoordenadas.add(new Point(getIntX(), getIntY()));
        }
    }
    
    @Override
    public void setCoordenadas(Coordenadas novasCoordenadas) {
        this.x = novasCoordenadas.getX();
        this.y = novasCoordenadas.getY();
        
        if (debugGraficoAtivo) {
            historicoCoordenadas.add(new Point(getIntX(), getIntY()));
        }
    }
    

    @Override
    public void incrementarCoordenadas(double aumentoX, double aumentoY) {
        x += aumentoX;
        y += aumentoY;
        
        if (debugGraficoAtivo) {
            historicoCoordenadas.add(new Point(getIntX(), getIntY()));
        }
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public int getIntX() {
        return (int) x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public int getIntY() {
        return (int) y;
    }
    
    @Override
    public Coordenadas getCoordenadas() {
        return this;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Movimentavel">
    @Override
    public void setVelX(double velX) {
        this.velX = velX;
    }

    @Override
    public void setVelY(double velY) {
        this.velY = velY;
    }

    @Override
    public void incrementaVelX(double aumentoVelX) {
        velX += aumentoVelX;
    }

    @Override
    public void incrementaVelY(double aumentoVelY) {
        velY += aumentoVelY;
    }

    @Override
    public void setVelocidades(double velX, double velY) {
        this.velX = velX;
        this.velY = velY;
    }

    @Override
    public void incrementarVelocidades(double aumentoVelX, double aumentoVelY) {
        velX += aumentoVelX;
        velY += aumentoVelY;
    }

    @Override
    public double getVelX() {
        return velX;
    }

    @Override
    public int getIntVelX() {
        return (int) velX;
    }

    @Override
    public double getVelY() {
        return velY;
    }

    @Override
    public int getIntVelY() {
        return (int) velY;
    }
    
    private double contQuadradoX = 0;
    private double contQuadradoY = 0;
    @Override
    public void movimentar(TipoMovimento movimento) {
        reiniciarAuxiliaresMovimentavel(METODO_MOVIMENTAR);
        
        if (movimento != null) {
            boolean movimentou = false;
            
            if (movimento == TipoMovimento.DIREITA) {
                x += velX;
                movimentou = true;
            }

            if (movimento == TipoMovimento.ESQUERDA) {
                x -= velX;
                movimentou = true;
            }

            if (movimento == TipoMovimento.BAIXO) {
                y += velY;
                movimentou = true;
            }

            if (movimento == TipoMovimento.CIMA) {
                y -= velY;
                movimentou = true;
            }

            if (movimento == TipoMovimento.DIREITA_BAIXO) {
                x += velX;
                y += velY;
                movimentou = true;
            }

            if (movimento == TipoMovimento.ESQUERDA_BAIXO) {
                x -= velX;
                y += velY;
                movimentou = true;
            }

            if (movimento == TipoMovimento.DIREITA_CIMA) {
                x += velX;
                y -= velY;
                movimentou = true;
            }

            if (movimento == TipoMovimento.ESQUERDA_CIMA) {
                x -= velX;
                y -= velY;
                movimentou = true;
            }
            
            if (movimento == TipoMovimento.SENOIDAL_X) {
                x += velX;
                y += Math.sin(x * 0.085) * velY;
                movimentou = true;
            }
            
            if (movimento == TipoMovimento.SENOIDAL_X_NEGATIVO) {
                x += velX * -1;
                y += (Math.sin(x * 0.085) * velY) * -1;
                movimentou = true;
            }
            
            if (movimento == TipoMovimento.COSSENOIDE_X) {
                x += velX;
                y += Math.cos(x * 0.085) * velY;
                movimentou = true;
            }
            
            if (movimento == TipoMovimento.COSSENOIDE_X_NEGATIVO) {
                x += velX * -1;
                y += (Math.cos(x * 0.085) * velY) * -1;
                movimentou = true;
            }
            
            if (movimento == TipoMovimento.SENOIDAL_Y) {
                x += Math.sin(y * 0.085) * velX;
                y += velY;
                movimentou = true;
            }
            
            if (movimento == TipoMovimento.SENOIDAL_Y_NEGATIVO) {
                x += (Math.sin(y * 0.085) * velX) * -1;
                y += velY * -1;
                movimentou = true;
            }
            
            if (movimento == TipoMovimento.COSSENOIDE_Y) {
                x += Math.cos(y * 0.085) * velX;
                y += velY;
                movimentou = true;
            }
            
            if (movimento == TipoMovimento.COSSENOIDE_Y_NEGATIVO) {
                x += (Math.cos(y * 0.085) * velX) * -1;
                y += velY * -1;
                movimentou = true;
            }
            
            if (movimento == TipoMovimento.QUADRADO_X) {
                x += velX;
                y += (Math.pow(contQuadradoY / 20, 2)) * -1;
                contQuadradoY += velY;
                movimentou = true;
            }
            
            if (movimento == TipoMovimento.QUADRADO_Y) {                
                x += (Math.pow(contQuadradoX / 10, 2)) * -1;
                y += velY;
                contQuadradoX += velX;
                movimentou = true;
            }
            
            if (
                    movimento == TipoMovimento.SEGUNDO_GRAU_X || 
                    movimento == TipoMovimento.SEGUNDO_GRAU_X_NEGATIVO
            ) { 
                int fator = 1;
                if (movimento == TipoMovimento.SEGUNDO_GRAU_X_NEGATIVO) {
                    fator = -1;
                }
                double a = 0.1;
                double b = 0;
                double c = -3;
                double solucaoY = (Math.pow(contQuadradoY / 10, 2) * a) + (b * contQuadradoY) + c;
                
                x += velX;
                y += solucaoY * fator;
                
                contQuadradoX += velX;
                contQuadradoY += velY;
                movimentou = true;
            }
            
            if (
                    movimento == TipoMovimento.SEGUNDO_GRAU_Y ||
                    movimento == TipoMovimento.SEGUNDO_GRAU_Y_NEGATIVO
            ) {
                int fator = 1;
                if (movimento == TipoMovimento.SEGUNDO_GRAU_Y_NEGATIVO) {
                    fator = -1;
                }
                
                double a = 0.1;
                double b = 0;
                double c = -3;
                double solucaoX = (Math.pow(contQuadradoX / 10, 2) * a) + (b * contQuadradoX) + c;
                
                x += solucaoX * fator;
                y += velY;
                
                contQuadradoX += velX;
                contQuadradoY += velY;
                movimentou = true;
            }
            
            if (debugGraficoAtivo && movimentou) {
                historicoCoordenadas.add(new Point(getIntX(), getIntY()));
                movimentou = false;
            }
        }
    }
    
    @Override
    public void movimentar(double direcaoEmGraus) {
        if (direcaoEmGraus > 0 || direcaoEmGraus < 0) {
            x += Math.cos((direcaoEmGraus * Math.PI) / 180) * velX;
            y += Math.sin((direcaoEmGraus * Math.PI) / 180) * velY;
            
            if (debugGraficoAtivo) {
                historicoCoordenadas.add(new Point(getIntX(), getIntY()));
            }
        }
    }
    
    private QuadCurve2D curva = new QuadCurve2D.Double();                
    private ArrayList<Point> points = new ArrayList<>();
    private int contCurvaQuadratica;
    @Override
    public void movimentarCurvaQuadratica(
            double alturaX, double alturaY, 
            double comprimentoX, double comprimentoY){
        reiniciarAuxiliaresMovimentavel(METODO_MOVIMENTAR_CURVA_QUADRATICA);
        
        if (alturaX == 0 && alturaY == 0 && comprimentoX == 0 && comprimentoY == 0) {
            reiniciarAuxiliaresMovimentavel(METODO_MOVIMENTAR_NENHUM);
        }
        else{
            if (contCurvaQuadratica == 0) {
                curva.setCurve(
                        //posição inicial
                        getX(), getY(),
                        //posição ponto máximo curva
                        getX() + alturaX, getY() + alturaY,
                        //posição final
                        getX() + comprimentoX, getY() + comprimentoY
                );
                preencherArrayListPoint(curva, points);
            }

            if ((int)contCurvaQuadratica < points.size()) {
                x = points.get((int)contCurvaQuadratica).getX();
                y = points.get((int)contCurvaQuadratica).getY();
                contCurvaQuadratica += velX;
            }
            
            if (debugGraficoAtivo) {
                historicoCoordenadas.add(new Point(getIntX(), getIntY()));
            }
        }
    }
    
    /**
     * Preenche um ArrayList de Point com todas as Coordenadas de uma QuadCurve2D.<br>
     * Método extraido e alterdado do fórum:
     * http://www.coderanch.com/t/455284/GUI/java/Moving-circle-line<br> 
     * Publicado por: Craig Wood<br>
     * Postado em: 25/07/2009 12:01:18
     * @param curve O desenho da curva
     * @param point O Arraylist
     */
    private void preencherArrayListPoint(QuadCurve2D curve, ArrayList<Point> point) {
        double flatness = 0.0001;
        PathIterator pit = curve.getPathIterator(null, flatness);
        double[] coords = new double[2];
        double max = -Double.MAX_VALUE;
        double min = Double.MAX_VALUE;
        Point2D.Double lastPoint = new Point2D.Double();
        
        while(!pit.isDone()) {
            int type = pit.currentSegment(coords);
            switch(type) {
                case PathIterator.SEG_MOVETO:
                    break;
                case PathIterator.SEG_LINETO:
                    double dist = lastPoint.distance(coords[0], coords[1]);
                    
                    point.add(new Point((int)coords[0], (int)coords[1]));
                    
                    if(dist < min) min = dist;
                    if(dist > max) max = dist;
                    break;
                default:
                    System.out.println("Unexpected type: " + type);
            }
            lastPoint.setLocation(coords[0], coords[1]);
            pit.next();
        }
    }
    
    private double contMovCirculoX = 0;
    private double contMovCirculoY = 0;
    private double pivoX = 0;
    private double pivoY = 0;
    @Override
    public void movimentarRepetidamente(double indiceDeformardorX, double indiceDeformardorY,
            double comprimento, double altura, boolean inverter){
        reiniciarAuxiliaresMovimentavel(METODO_MOVIMENTAR_REPET);
        
        if (indiceDeformardorX != 0 && indiceDeformardorY != 0 && comprimento != 0 && altura != 0) {
            //Executando primeira vez invertido?
            if (contMovCirculoX == 0.0 && contMovCirculoY == 0.0 && inverter) {
                pivoX = (getX() - (Math.sin(contMovCirculoX * indiceDeformardorX) * comprimento)) + 0.01;
                pivoY = (getY() - (Math.cos(contMovCirculoY * indiceDeformardorY) * altura)) + 0.01;
            }
            
            //Executando primeira vez não invertido?
            if (contMovCirculoX == 0.0 && contMovCirculoY == 0.0 && !inverter) {
                pivoX = (getX() - (Math.cos(contMovCirculoX * indiceDeformardorX) * comprimento)) + 0.01;
                pivoY = (getY() - (Math.sin(contMovCirculoY * indiceDeformardorY) * altura)) + 0.01;
            }
            
            if (inverter) {
                x = (Math.sin(contMovCirculoX * indiceDeformardorX) * comprimento) + pivoX;
                y = (Math.cos(contMovCirculoY * indiceDeformardorY) * altura) + pivoY;
            }
            else{
                x = (Math.cos(contMovCirculoX * indiceDeformardorX) * comprimento) + pivoX;
                y = (Math.sin(contMovCirculoY * indiceDeformardorY) * altura) + pivoY;
            }
            
            contMovCirculoX += velX;
            contMovCirculoY += velY;
            
            if (debugGraficoAtivo) {
                historicoCoordenadas.add(new Point(getIntX(), getIntY()));
            }
        }
    }
    
    private double contMovFuncao = 0;
    @Override
    public void movimentarPorFuncao(String funcao, boolean iniciarEmCoordNegativa){
        reiniciarAuxiliaresMovimentavel(METODO_MOVIMENTAR_FUNCAO);
        
        if (contMovFuncao == 0 && iniciarEmCoordNegativa) {
            contMovFuncao = (velX + velY) * -40;
        }
                
        if (isFuncaoX(funcao)) {            
            double solucao = calculaSolucaoXY(funcao, contMovFuncao);
            x += contMovFuncao + velX;
            y += solucao + velY;
            contMovFuncao += velX;
        }

        if (isFuncaoY(funcao)) {            
            double solucao = calculaSolucaoXY(funcao, contMovFuncao);
            x += solucao + velX;
            y += contMovFuncao + velY;
            contMovFuncao += velY;
        }

        if (debugGraficoAtivo) {
            historicoCoordenadas.add(new Point(getIntX(), getIntY()));
        }
                     
    }
    
    @Override
    public void pararMovimento() {
        movimentar(null);
        movimentar(0.0);
        movimentarCurvaQuadratica(0, 0, 0, 0);
        movimentarRepetidamente(0.0, 0.0, 0.0, 0.0, false);
        movimentarPorFuncao("", false);
    }
    
    private double contCalculaFuncao = 0;
    @Override
    public double calcularFuncao(String funcao) {
        reiniciarAuxiliaresMovimentavel(METODO_CALCULA_FUNCAO);
        double solucao = calculaSolucaoXY(funcao, contCalculaFuncao);
        
        if (isFuncaoX(funcao)) {
            contCalculaFuncao += velX;
        }

        if (isFuncaoY(funcao)) {
            contCalculaFuncao += velY;
        }
        
        return solucao;
    }
    
    private double contCalculaFuncaoIntervalo = 0;
    @Override
    public double calcularFuncao(String funcao, double intervaloIncial, double intervaloFinal) {
        reiniciarAuxiliaresMovimentavel(METODO_CALCULA_FUNCAO_INTERVALO);
        
        if (contCalculaFuncaoIntervalo == 0) {
            contCalculaFuncaoIntervalo = intervaloIncial;
        }
        
        double solucao = calculaSolucaoXY(funcao, contCalculaFuncaoIntervalo);
        
        if (isFuncaoX(funcao)) {
            contCalculaFuncaoIntervalo += velX;
        }

        if (isFuncaoY(funcao)) {
            contCalculaFuncaoIntervalo += velY;
        }
        
        if (contCalculaFuncaoIntervalo >= intervaloFinal) {
            return 0;
        }
        
        return solucao;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
    @Override
    public void desenha(Graphics2D g2d) {
        if (debugGraficoAtivo) {
            g2d.setColor(Color.MAGENTA);
            g2d.setFont(new Font("Arial", Font.BOLD, 10));
            g2d.drawString("x: " + x + ", y: " + y + " - velX: " + velX + ", velY: " + velY, 10, 10);
            
            g2d.setColor(Color.GREEN);
            for (Point hist : historicoCoordenadas) {
                g2d.fillOval((int)hist.getX(), (int)hist.getY(), 2, 2);
            }            
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Object">
    /**
     * Compara se as coordenadas de dois objetos Coordenadas são idênticas.
     * @param coordenadas O outro objeto a ser comparado
     * @return true : Caso os valores das coordenadas de ambos objetos forem os
     * mesmos <br>
     * false : Caso apenas uma ou nenhuma das coordenadas de ambos objetos forem
     * iguais
     */
    @Override
    public boolean equals(Object coordenadas) {
        return coordenadas instanceof Coordenadas &&
                ((Coordenadas) coordenadas).x == x &&
                ((Coordenadas) coordenadas).y == y &&
                ((Coordenadas) coordenadas).velX == velX &&
                ((Coordenadas) coordenadas).velY == velY;
    }

    /**
     * Retorna uma String com os valores das coordenadas.
     * @return String de acordo com o seguinte modelo \"(x, y)\"
     */
    @Override
    public String toString() {
        return "(" + getIntX() + "," + getIntY() + ")";
    }
    //</editor-fold>
    
}
