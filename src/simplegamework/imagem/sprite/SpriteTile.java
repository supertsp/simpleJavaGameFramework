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

package simplegamework.imagem.sprite;

import simplegamework.objetoBasico.TipoColisao;
import simplegamework.objetoBasico.Coordenadas;
import simplegamework.objetoBasico.Cruz;
import simplegamework.objetoBasico.CaixaColisao;
import simplegamework.objetoBasico.TipoMovimento;
import simplegamework.objetoBasico.LadoRetangulo;
import simplegamework.padraoProjeto.Atualizavel;
import simplegamework.padraoProjeto.Dimensionavel;
import simplegamework.padraoProjeto.ImagemTransformavel;
import simplegamework.padraoProjeto.Movimentavel;
import simplegamework.padraoProjeto.AtualizavelListManuseavel;
import simplegamework.padraoProjeto.Colidivel;
import simplegamework.padraoProjeto.Desenhavel;
import simplegamework.padraoProjeto.Copiavel;
import simplegamework.padraoProjeto.CaixaColisaoListManuseavel;
import simplegamework.padraoProjeto.Coordenavel;
import simplegamework.ferramenta.AtualizavelList;
import simplegamework.ferramenta.AuxiliaresMovimentavel;
import simplegamework.ferramenta.AuxiliaresMovimentavel.MetodoMovimentoAtual;
import simplegamework.imagem.*;
import java.awt.Graphics2D;
import java.util.Objects;

/**
 * Um SpriteTile nada mais é que um elemento de um Sprite que contém um 
 * pivoAlinhamento para que na hora da exibição todos os SpriteTile contidos
 * num Sprite sejam alinhados por ele e não pelas Coordenadas padrão de uma
 * ImagemBitmap.
 * <br><br><small>Created on : 07/07/2015, 18:28:44</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class SpriteTile implements Copiavel<SpriteTile>, Coordenavel, Movimentavel, Dimensionavel, 
        ImagemTransformavel, Colidivel, CaixaColisaoListManuseavel, 
        Desenhavel, Atualizavel, AtualizavelListManuseavel
{
    
    protected ImagemBitmap tile;
    protected PivoAlinhamento pivoAlinhamento;
    
    //Auxiliares
    private AuxiliaresMovimentavel auxMov;
    private AtualizavelList atualizaveis;
            
    //<editor-fold defaultstate="collapsed" desc="Construtor">
    /**
     * Construtor básico de uma tile que serve de base para o Sprite.
     * @param tile Uma ImagemBitmap que representa um tile que irá compor um Sprite
     * @param xPivoAlinhamento A coordenada x que definem o centro de exibição do
     * personagem
     * @param yPivoAlinhamento A coordenada y que definem o centro de exibição do
     * personagem
     * @param exibirPivoAlinhamento Determinante da exibição do pivoAlinhamento
     */
    public SpriteTile(ImagemBitmap tile, double xPivoAlinhamento, 
            double yPivoAlinhamento, boolean exibirPivoAlinhamento){
        this.tile = tile;        
        pivoAlinhamento = new PivoAlinhamento(exibirPivoAlinhamento);
        setCoordenadasPivoAlinhamento(xPivoAlinhamento, yPivoAlinhamento);
        
        //Auxiliares
        auxMov = new AuxiliaresMovimentavel();
        atualizaveis = new AtualizavelList();
    }
    //</editor-fold>
    
    /**
     * Retorna uma ImagemBitmap que representa o tile.
     * @return ImagemBitmap
     */
    public ImagemBitmap getImagemBitmap(){
        return tile;
    }
    
    /**
     * Altera o código do tile.
     * @param novoCodigo O valor do código
     */
    public void setCodigo(int novoCodigo){
        tile.setCodigo(novoCodigo);
    }
    
    /**
     * Retorna o código do tile.
     * @return int
     */
    public int getCodigo(){
        return tile.getCodigo();
    }
    
    /**
     * Altera o valor de bloqueada para true.
     */
    public void bloquear(){
        tile.bloquear();
    }
    
    /**
     * Altera o valor de bloqueada para false.
     */
    public void desbloquear(){
        tile.desbloquear();
    }
    
    /**
     * Retorna o status do bloqueio da ImagemBitmap.
     * @return boolean
     */
    public boolean isBloqueada(){
        return tile.isBloqueada();
    }
    
    /**
     * Ativa o debugGrafico, ou seja, escreve os atributos de Coordenadas na tela.
     */
    public void ligarDebugGraficoCoordenadas(){
        getCaixaColisao(0).ligarDebugGraficoCoordenadas();
    }
    
    /**
     * Desativa o debugGrafico, ou seja, não serão exibidos os atributos de
     * Coordenadas na tela.
     */
    public void desligarDebugGraficoCoordenadas(){
        getCaixaColisao(0).desligarDebugGraficoCoordenadas();
    }
    
    /**
     * Retorna o estado atual de debugGrafico.
     * @return boolean
     */
    public boolean isDebugGraficoAtivoCoordenadas(){
        return getCaixaColisao(0).isDebugGraficoAtivoCoordenadas();
    }
    
    /**
     * Altera a rolagemInfinita, ou seja, marca se um SpriteTile deve ficar rolando
     * infinitamente dentro de seus limites semelhantemente a uma esteira fabril.
     * Os movimentos da rolagem são baseados em getVelX() e getVelY().<br>
     * Obs.: Este efeito não é compatível com o espelhamento de imagem.
     * @param tipoRolagem O novo tipo de rolagem infinita
     */
    public void setRolagemInfinita(TipoRolagemInfinita tipoRolagem){
        tile.setRolagemInfinita(tipoRolagem);
    }
    
    /**
     * Retorna o valor de rolagemInfinita.
     * @return TipoRolagemInfinita
     */
    public TipoRolagemInfinita getRolagemInfinita(){
        return tile.getRolagemInfinita();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Copiavel">
    @Override
    public SpriteTile getCopy(){
        SpriteTile copia = new SpriteTile(
                tile.getCopy(), 
                getXPivoAlinhamento(), 
                getYPivoAlinhamento(), 
                isAtivaExibicaoPivoAlinhamento()
        );
        return copia;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: PivoAlinhamento">
    /**
     * Altera o valor da coordena x no pivoAlinhamento em relação ao tile e não 
     * à tela. Caso seja passado uma coordenada inválida, ela será corrigida para
     * 0.
     * @param x O novo valor
     */
    public void setXPivoAlinhamento(double x) {
        x += getX();
        if (x >= getX() && x < (getX() + getComprimento())) {
            pivoAlinhamento.setX(x);
        }
        else{
            pivoAlinhamento.setX(getX());
        }
    }

    /**
     * Altera o valor da coordena y no pivoAlinhamento em relação ao tile e não 
     * à tela. Caso seja passado uma coordenada inválida, ela será corrigida para
     * 0.
     * @param y O novo valor
     */
    public void setYPivoAlinhamento(double y) {
        y += getY();
        if (y >= getY() && y < (getY() + getAltura())) {
            pivoAlinhamento.setY(y);
        }
        else{
            pivoAlinhamento.setY(getY());
        }
    }

    /**
     * Incrementa o valor da coordenada x no pivoAlinhamento de acordo com o 
     * parâmetro passado em relação ao tile e não à tela. Caso a coordenada com
     * o novo incremento ultrapasse as dimensões do tile, não será aplicado o 
     * valor do incremento.
     * @param aumentoX O valor do incremento
     */
    public void incrementaXPivoAlinhamento(double aumentoX) {
        double x = getXPivoAlinhamento() + aumentoX;
        if (x >= getX() && x < (getX() + getComprimento())) {
            pivoAlinhamento.incrementaX(aumentoX);
        }
    }

    /**
     * Incrementa o valor da coordenada y no pivoAlinhamento de acordo com o 
     * parâmetro passado em relação ao tile e não à tela. Caso a coordenada com
     * o novo incremento ultrapasse as dimensões do tile, não será aplicado o 
     * valor do incremento.
     * @param aumentoY O valor do incremento
     */
    public void incrementaYPivoAlinhamento(double aumentoY) {
        double y = getYPivoAlinhamento() + aumentoY;
        if (y >= getY() && y < (getY() + getAltura())) {
            pivoAlinhamento.incrementaY(aumentoY);
        }
    }

    /**
     * Altera os valores das coordenadas x e y no pivoAlinhamento em relação ao 
     * tile e não à tela. Caso as Coordenadas passadas sejam inválidas, elas serão
     * corrigidas para 0,0
     * @param x O novo valor de x
     * @param y O novo valor de y
     */
    public void setCoordenadasPivoAlinhamento(double x, double y) {
        x += getX();
        y += getY();
        if (
            x >= getX() && x < (getX() + getComprimento()) &&
            y >= getY() && y < (getY() + getAltura())
        ) {
            pivoAlinhamento.setCoordenadas(x, y);
        }
        else{
            pivoAlinhamento.setCoordenadas(getX(), getY());
        }
    }

    /**
     * Incrementa os valores das coordenadas x e y no pivoAlinhamento de acordo 
     * com os parâmetros passados em relação ao tile e não à tela. Caso as 
     * Coordenadas com os novos incrementos ultrapassem as dimensões do tile, 
     * não serão aplicados os valores dos incrementos.
     * @param aumentoX O valor a ser incrementado em x
     * @param aumentoY O valor a ser incrementado em y
     */
    public void incrementarCoordenadasPivoAlinhamento(double aumentoX, double aumentoY) {
        double x = getXPivoAlinhamento() + aumentoX;
        double y = getYPivoAlinhamento() + aumentoY;
        if (
            x >= getX() && x < (getX() + getComprimento()) &&
            y >= getY() && y < (getY() + getAltura())
        ) {
            pivoAlinhamento.incrementarCoordenadas(aumentoX, aumentoY);
        }
    }

    /**
     * Retorna o valor da coordenada x do pivoAlinhamento em relação à tela.
     * @return double
     */
    public double getXPivoAlinhamento() {
        return pivoAlinhamento.getX();
    }

    /**
     * Retorna a coordenada x do pivoAlinhamento como um int em relação à tela.
     * @return int
     */
    public int getIntXPivoAlinhamento() {
        return pivoAlinhamento.getIntX();
    }

    /**
     * Retorna o valor da coordenada y do pivoAlinhamento em relação à tela.
     * @return double
     */
    public double getYPivoAlinhamento() {
        return pivoAlinhamento.getY();
    }

    /**
     * Retorna a coordenada y do pivoAlinhamento como um int em relação à tela.
     * @return int
     */
    public int getIntYPivoAlinhamento() {
        return pivoAlinhamento.getIntY();
    }
    
    /**
     * Posiciona automativamente o pivoAlinhamento no meio do lado inferior do 
     * tile em relação a ele e não à tela.
     */
    public void posicionarPivoAlinhamentoAoCentroInferiorDoTile(){
        setCoordenadasPivoAlinhamento(
                getComprimento() / 2,
                getAltura() - 1
        );
    }
    
    /**
     * Ativa a exibição do desenho do pivoAlinhamento.
     */
    public void ativarExibicaoPivoAlinhamento(){
        pivoAlinhamento.ativarExibicaoPivo();
    }
    
    /**
     * Desativa a exibição do desenho do pivoAlinhamento.
     */
    public void desativarExibicaoPivoAlinhamento(){
        pivoAlinhamento.desativarExibicaoPivo();
    }
    
    /**
     * Retorna o estado da exibição do desenho do pivoAlinhamento.
     * @return boolean
     */
    public boolean isAtivaExibicaoPivoAlinhamento(){
        return pivoAlinhamento.isExibirPivo();
    }
    
    /**
     * Corrige as Coordenadas do pivoAlinhamento quando as Coordenadas do tile é
     * alterado.
     * @param xTileAntes O valor de x antes da mudança
     * @param yTileAntes O valor de y antes da mudança
     * @param xTileDepois O valor de x após a mudança
     * @param yTileDepois O valor de y após a mudança
     */
    private void arrumarCoordenadasPivoAlinhamento(
            double xTileAntes, double yTileAntes, 
            double xTileDepois, double yTileDepois
    ){
        double difX = getXPivoAlinhamento() - xTileAntes;
        double difY = getYPivoAlinhamento() - yTileAntes;
        pivoAlinhamento.setCoordenadas(xTileDepois + difX, yTileDepois + difY);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Coordenavel">
    @Override
    public void setX(double x) {
        double xAntes = getX();
        tile.setX(x);
        arrumarCoordenadasPivoAlinhamento(xAntes, getY(), getX(), getY());
    }

    @Override
    public void setY(double y) {
        double yAntes = getY();
        tile.setY(y);
        arrumarCoordenadasPivoAlinhamento(getX(), yAntes, getX(), getY());
    }

    @Override
    public void incrementaX(double aumentoX) {
        tile.incrementaX(aumentoX);
        incrementaXPivoAlinhamento(aumentoX);
    }

    @Override
    public void incrementaY(double aumentoY) {
        tile.incrementaY(aumentoY);
        incrementaYPivoAlinhamento(aumentoY);
    }

    @Override
    public void setCoordenadas(double x, double y) {
        double xAntes = getX();
        double yAntes = getY();
        tile.setCoordenadas(x, y);
        arrumarCoordenadasPivoAlinhamento(xAntes, yAntes, getX(), getY());
    }
    
    @Override
    public void setCoordenadas(Coordenadas novasCoordenadas) {
        double xAntes = getX();
        double yAntes = getY();
        tile.setCoordenadas(novasCoordenadas);
        arrumarCoordenadasPivoAlinhamento(xAntes, yAntes, getX(), getY());
    }
    
    @Override
    public void incrementarCoordenadas(double aumentoX, double aumentoY) {
        tile.incrementarCoordenadas(aumentoX, aumentoY);
        incrementarCoordenadasPivoAlinhamento(aumentoX, aumentoY);
    }

    @Override
    public double getX() {
        return tile.getX();
    }

    @Override
    public int getIntX() {
        return tile.getIntX();
    }

    @Override
    public double getY() {
        return tile.getY();
    }

    @Override
    public int getIntY() {
        return tile.getIntY();
    }
    
    @Override
    public Coordenadas getCoordenadas() {
        return tile.getCoordenadas();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Movimentavel">
    @Override
    public void setVelX(double velX) {
        tile.setVelX(velX);
    }

    @Override
    public void setVelY(double velY) {
        tile.setVelY(velY);
    }

    @Override
    public void incrementaVelX(double aumentoVelX) {
        tile.incrementaVelX(aumentoVelX);
    }

    @Override
    public void incrementaVelY(double aumentoVelY) {
        tile.incrementaVelY(aumentoVelY);
    }

    @Override
    public void setVelocidades(double velX, double velY) {
        tile.setVelocidades(velX, velY);
    }

    @Override
    public void incrementarVelocidades(double aumentoVelX, double aumentoVelY) {
        tile.incrementarVelocidades(aumentoVelX, aumentoVelY);
    }

    @Override
    public double getVelX() {
        return tile.getVelX();
    }

    @Override
    public int getIntVelX() {
        return tile.getIntVelX();
    }

    @Override
    public double getVelY() {
        return tile.getVelY();
    }

    @Override
    public int getIntVelY() {
        return tile.getIntVelY();
    }

    @Override
    public void movimentar(TipoMovimento novoMovimento) {
        auxMov.setMovimentoPorTipo(novoMovimento);
        
        double xAntes = getX();
        double yAntes = getY();
        
        tile.movimentar(novoMovimento);
        
        arrumarCoordenadasPivoAlinhamento(xAntes, yAntes, getX(), getY());
    }

    @Override
    public void movimentar(double direcaoEmGraus) {
        auxMov.setMovimentoPorGrau(direcaoEmGraus);
        
        double xAntes = getX();
        double yAntes = getY();
        
        tile.movimentar(direcaoEmGraus);
        
        arrumarCoordenadasPivoAlinhamento(xAntes, yAntes, getX(), getY());
    }
    
    @Override
    public void movimentarCurvaQuadratica(
            double alturaX, double alturaY, 
            double comprimentoX, double comprimentoY){
        auxMov.setMovimentoPorCurvaQuadratica(alturaX, alturaY, comprimentoX, comprimentoY);
        
        double xAntes = getX();
        double yAntes = getY();
        
        tile.movimentarCurvaQuadratica(alturaX, alturaY, comprimentoX, comprimentoY);
        
        arrumarCoordenadasPivoAlinhamento(xAntes, yAntes, getX(), getY());
    }

    @Override
    public void movimentarRepetidamente(
            double indiceDeformardorX, double indiceDeformardorY, 
            double comprimento, double altura, boolean inverter
    ) {
        auxMov.setMovimentoPorRepeticao(
                indiceDeformardorX, indiceDeformardorY, 
                comprimento, altura, inverter
        );
        
        double xAntes = getX();
        double yAntes = getY();
        
        tile.movimentarRepetidamente(
                indiceDeformardorX, indiceDeformardorY, 
                comprimento, altura, inverter
        );
        
        arrumarCoordenadasPivoAlinhamento(xAntes, yAntes, getX(), getY());
    }

    @Override
    public void movimentarPorFuncao(String funcao, boolean iniciarEmCoordNegativa) {
        auxMov.setMovimentoPorFuncao(funcao, iniciarEmCoordNegativa);
        
        double xAntes = getX();
        double yAntes = getY();
        
        tile.movimentarPorFuncao(funcao, iniciarEmCoordNegativa);
        
        arrumarCoordenadasPivoAlinhamento(xAntes, yAntes, getX(), getY());
    }

    @Override
    public void pararMovimento() {
        auxMov.setMovimentoNenhum();
        
        double xAntes = getX();
        double yAntes = getY();
        
        tile.pararMovimento();
        
        arrumarCoordenadasPivoAlinhamento(xAntes, yAntes, getX(), getY());
    }

    @Override
    public double calcularFuncao(String funcao) {
        return tile.calcularFuncao(funcao);
    }

    @Override
    public double calcularFuncao(String funcao, double intervaloIncial, double intervaloFinal) {
        return tile.calcularFuncao(funcao, intervaloIncial, intervaloFinal);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Dimensionavel">
    @Override
    public void redimensionar(double escala) {
        tile.redimensionar(escala);
    }

    @Override
    public int getComprimento() {
        return tile.getComprimento();
    }

    @Override
    public int getAltura() {
        return tile.getAltura();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: ImagemTransformavel">
    @Override
    public void espelhar(TipoEspelhamento tipoEspelhamento) {
        tile.espelhar(tipoEspelhamento);
    }

    @Override
    public void rotacionar(double graus) {
        tile.rotacionar(graus);
    }

    @Override
    public void rotacionar(double graus, Coordenadas pivoRotacao) {
        tile.rotacionar(graus, pivoRotacao);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Colidivel">
    @Override
    public CaixaColisao getCaixaColisao(){
        return tile.getCaixaColisao();
    }
    
    @Override
    public boolean colidiu(CaixaColisao outraCaixaColisao, TipoColisao tipoColisao) {
        return tile.colidiu(outraCaixaColisao, tipoColisao);
    }

    @Override
    public LadoRetangulo colidiuNoLado(CaixaColisao outraCaixaColisao, TipoColisao tipoColisao) {
        return tile.colidiuNoLado(outraCaixaColisao, tipoColisao);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: CaixaColisaoListManuseavel">
    @Override
    public void addCaixaColisao(CaixaColisao novoElemento) {
        tile.addCaixaColisao(novoElemento);
    }

    @Override
    public void addCaixaColisao(CaixaColisao... variosElementos) {
        tile.addCaixaColisao(variosElementos);
    }

    @Override
    public CaixaColisao getCaixaColisao(int indice) {
        return tile.getCaixaColisao(indice);
    }

    @Override
    public CaixaColisao getNextCaixaColisao() {
        return tile.getNextCaixaColisao();
    }

    @Override
    public CaixaColisao getCaixaColisao(Object rotulo) {
        return tile.getCaixaColisao(rotulo);
    }

    @Override
    public void removeCaixaColisao(int indice) {
        tile.removeCaixaColisao(indice);
    }

    @Override
    public void removeCaixaColisao(CaixaColisao elementoParaRemover) {
        tile.removeCaixaColisao(elementoParaRemover);
    }

    @Override
    public void removeCaixaColisao(Object rotulo) {
        tile.removeCaixaColisao(rotulo);
    }

    @Override
    public int sizeCaixaColisaoList() {
        return tile.sizeCaixaColisaoList();
    }

    @Override
    public void ativarCorBordaCaixaColisaoList() {
        tile.ativarCorBordaCaixaColisaoList();
    }

    @Override
    public void desativarCorBordaCaixaColisaoList() {
        tile.desativarCorBordaCaixaColisaoList();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
    @Override
    public void desenha(Graphics2D g2d) {
        tile.desenha(g2d);
        
        if (isAtivaExibicaoPivoAlinhamento()) {
            pivoAlinhamento.desenha(g2d);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Atualizavel">
    @Override
    public void atualiza() {        
        if (auxMov.getMetodoMovimentavelAtual() == MetodoMovimentoAtual.TIPO) {
            movimentar(auxMov.getMovimentoPorTipo());
        }
        
        if (auxMov.getMetodoMovimentavelAtual() == MetodoMovimentoAtual.GRAU) {
            movimentar(auxMov.getMovimentoPorGrau());
        }
        
        if (auxMov.getMetodoMovimentavelAtual() == MetodoMovimentoAtual.CURVA_QUADRATICA) {
            movimentarCurvaQuadratica(
                    auxMov.getMovimentoPorCurvaQuadratica().alturaX, 
                    auxMov.getMovimentoPorCurvaQuadratica().alturaY, 
                    auxMov.getMovimentoPorCurvaQuadratica().comprimentoX, 
                    auxMov.getMovimentoPorCurvaQuadratica().comprimentoY
            );
        }
                
        if (auxMov.getMetodoMovimentavelAtual() == MetodoMovimentoAtual.REPETIDAMENTE) {
            movimentarRepetidamente(
                    auxMov.getMovimentoPorRepeticao().indiceDeformardorX, 
                    auxMov.getMovimentoPorRepeticao().indiceDeformardorY, 
                    auxMov.getMovimentoPorRepeticao().alturaMovimento, 
                    auxMov.getMovimentoPorRepeticao().alturaMovimento, 
                    auxMov.getMovimentoPorRepeticao().inverter
            );
        }
        
        if (auxMov.getMetodoMovimentavelAtual() == MetodoMovimentoAtual.FUNCAO) {
            movimentarPorFuncao(
                    auxMov.getMovimentoPorFuncao().funcao, 
                    auxMov.getMovimentoPorFuncao().iniciarEmCoordNegativa
            );
        }
        
        if (auxMov.getMetodoMovimentavelAtual() == MetodoMovimentoAtual.NENHUM) {
            pararMovimento();
        }
        
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
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.tile);
        return hash;
    }
    
    /**
     * Descobre se dois SpriteTile são iguais comparando seus respectivos 
     * hashCode().
     * @param outroSpriteTile Outro SpriteTile a ser comparado
     * @return boolean
     */
    @Override
    public boolean equals(Object outroSpriteTile) {
        if (outroSpriteTile == null) {
            return false;
        }
        
        if (getClass() != outroSpriteTile.getClass()) {
            return false;
        }
        
        final SpriteTile other = (SpriteTile) outroSpriteTile;
        return Objects.equals(this.tile, other.tile);
    }
    
    /**
     * Desenha um retângulo (SpriteTile) e seus quatro pares de coordenadas
     * para um melhor debug do mesmo e de sua localização na tela.
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
        coord1 = tile.getCaixaColisaoImagemToda()
                .getLadoCoordenadas(LadoRetangulo.ESQUERDA_CIMA)
                .toString();

        coord2 = tile.getCaixaColisaoImagemToda()
                .getLadoCoordenadas(LadoRetangulo.DIREITA_CIMA)
                .toString();
        
        int restaEspacos = tamanhoLinhaCoord - (coord1.length() + coord2.length());

        for (int cont = 0; cont < restaEspacos; cont++) {
            espacos += " ";
        }
        
        desenhoFinal += "  |" + coord1 + espacos + coord2 + "|\n" + linhaMeioTemp;
        espacos = "";
        
        //COORDENADAS INFERIORES
        coord1 = tile.getCaixaColisaoImagemToda()
                .getLadoCoordenadas(LadoRetangulo.ESQUERDA_BAIXO)
                .toString();

        coord2 = tile.getCaixaColisaoImagemToda()
                .getLadoCoordenadas(LadoRetangulo.DIREITA_BAIXO)
                .toString();
        
        restaEspacos = tamanhoLinhaCoord - (coord1.length() + coord2.length());

        for (int cont = 0; cont < restaEspacos; cont++) {
            espacos += " ";
        }

       desenhoFinal += "  |" + coord1 + espacos + coord2 + "|\n  " + linhaTopoBase;
        
                        
        return
            "SpriteTile {\n" +
            "  - dimesões: " + getComprimento() + "x" + getAltura() + "px\n" +
            desenhoFinal + "\n" +
            "  - pivoAlinhamento: " + pivoAlinhamento.getX() + ", " + pivoAlinhamento.getY() + "\n" +
            "  - exibir pivoAlinhamento? " + isAtivaExibicaoPivoAlinhamento() + "\n" +
            "}\n";
    }
    //</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="InnerClass: PivoAlinhamento">
    /**
     * Inner Class que alinha as Coordenadas do pivo e da cruz para sempre terem
     * os mesmos valores
     */
    protected class PivoAlinhamento implements Coordenavel, Desenhavel{
        protected Coordenadas pivo;
        protected Cruz cruz;
        protected boolean exibirPivo;
        
        //<editor-fold defaultstate="collapsed" desc="Construtor">
        /**
         * Construtor básico da inner class.
         * @param exibirPivo Determinante da exibição do pivo
         */
        public PivoAlinhamento(boolean exibirPivo){
            pivo = new Coordenadas(0, 0);
            cruz = new Cruz(pivo, 2, true);
            this.exibirPivo = exibirPivo;
        }
        //</editor-fold>
        
        /**
         * Ativa a exibição do pivo.
         */
        public void ativarExibicaoPivo(){
            exibirPivo = true;
        }
        
        /**
         * Desativa a exibição do pivo.
         */
        public void desativarExibicaoPivo(){
            exibirPivo = false;
        }
        
        /**
         * Retorna o estado da exibição do pivo.
         * @return boolean
         */
        public boolean isExibirPivo(){
            return exibirPivo;
        }
        
        //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Coordenavel">
        @Override
        public void setX(double x) {
            pivo.setX(x);
            cruz.setX(x);
        }

        @Override
        public void setY(double y) {
            pivo.setY(y);
            cruz.setY(y);
        }

        @Override
        public void incrementaX(double aumentoX) {
            pivo.incrementaX(aumentoX);
            cruz.incrementaX(aumentoX);
        }

        @Override
        public void incrementaY(double aumentoY) {
            pivo.incrementaY(aumentoY);
            cruz.incrementaY(aumentoY);
        }

        @Override
        public void setCoordenadas(double x, double y) {
            pivo.setCoordenadas(x, y);
            cruz.setCoordenadas(x, y);
        }
        
        @Override
        public void setCoordenadas(Coordenadas novasCoordenadas) {
            pivo.setCoordenadas(novasCoordenadas);
            cruz.setCoordenadas(novasCoordenadas);
        }
        
        @Override
        public void incrementarCoordenadas(double aumentoX, double aumentoY) {
            pivo.incrementarCoordenadas(aumentoX, aumentoY);
            cruz.incrementarCoordenadas(aumentoX, aumentoY);
        }

        @Override
        public double getX() {
            return pivo.getX();
        }

        @Override
        public int getIntX() {
            return pivo.getIntX();
        }

        @Override
        public double getY() {
            return pivo.getY();
        }

        @Override
        public int getIntY() {
            return pivo.getIntY();
        }
        
        @Override
        public Coordenadas getCoordenadas() {
            return pivo.getCoordenadas();
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
        @Override
        public void desenha(Graphics2D g2d) {
            if (exibirPivo) {
                cruz.desenha(g2d);
            }
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Object">
        /**
         * Desenha um retângulo (SpriteTile) e seus quatro pares de coordenadas
         * para um melhor debug do mesmo e de sua localização na tela.
         * @return Uma String contendo o desenho.
         */
        @Override
        public String toString() {
            return
                "PivoAlinhamento {\n" +
                "  - coordenadas pivo: x: " + pivo.getX() + ", y: " + pivo.getY() + "\n" +
                "  - coordenadas cruz: x: " + cruz.getX() + ", y: " + cruz.getY() + "\n" +
                "  - exibir? : " + isExibirPivo() + "\n" + 
                "}\n";
        }
        //</editor-fold>
        
    }
    //</editor-fold>
    
}
