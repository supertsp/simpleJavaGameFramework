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

package simplegamework.texto;

import simplegamework.ferramenta.AtualizavelList;
import simplegamework.ferramenta.AuxiliaresMovimentavel;
import simplegamework.ferramenta.MedidaTempo;
import simplegamework.ferramenta.TempoExecucao;
import simplegamework.objetoBasico.CaixaColisao;
import simplegamework.objetoBasico.Coordenadas;
import simplegamework.objetoBasico.LadoRetangulo;
import simplegamework.objetoBasico.TipoColisao;
import simplegamework.objetoBasico.TipoMovimento;
import simplegamework.padraoProjeto.Atualizavel;
import simplegamework.padraoProjeto.AtualizavelListManuseavel;
import simplegamework.padraoProjeto.Colidivel;
import simplegamework.padraoProjeto.Coordenavel;
import simplegamework.padraoProjeto.Desenhavel;
import simplegamework.padraoProjeto.Dimensionavel;
import simplegamework.padraoProjeto.Movimentavel;
import java.awt.Graphics2D;

/**
 * Faz o efeito pisca-pisca num Texto de acordo com o valor de intermitência de
 * tempo em segundos.
 * <br>
 * <br><small>Created on : 24/09/2015, 13:29:15</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class TextoPiscaPisca implements Coordenavel, Movimentavel, Colidivel, 
        Dimensionavel, Desenhavel, Atualizavel, AtualizavelListManuseavel{
    
    protected Texto texto;
    protected TempoExecucao tempoPiscaPisca;
    protected boolean desenhaTexto;
    
    //Auxiliares
    private AuxiliaresMovimentavel auxMov;
    private AtualizavelList atualizaveis;
    
    //<editor-fold defaultstate="collapsed" desc="Construtor">
    /**
     * Construtor básico de classe.
     * @param texto O texto a ser desenhado
     * @param tempoPiscaPisca O intervalo de tempo (segundos) para a ação pisca-pisca
     */
    public TextoPiscaPisca(Texto texto, double tempoPiscaPisca){
        this.texto = texto;
        this.tempoPiscaPisca = new TempoExecucao(tempoPiscaPisca, MedidaTempo.SEGUNDOS);
        this.tempoPiscaPisca.iniciarTempo();
        
        //Auxiliares
        auxMov = new AuxiliaresMovimentavel();
        atualizaveis = new AtualizavelList();
    }
    //</editor-fold>
    
    /**
     * Retorna o objeto Texto.
     * @return Texto
     */
    public Texto getTexto(){
        return texto;
    }
    
    /**
     * Altera o tempo (segundos) da intermitência do pisca-pisca.
     * @param novoTempo O valor do novo intervalo de tempo
     */
    public void setTempoPiscaPisca(double novoTempo){
        tempoPiscaPisca.setMeta(novoTempo);
    }
    
    /**
     * Ativa o filtro Antialiasing quando o texto é desenhado.
     */
    public void ativarFiltroExibicao(){
        texto.ativarFiltroExibicao();
    }
    
    /**
     * Desativa o filtro Antialiasing quando o texto é desenhado.
     */
    public void desativarFiltroExibicao(){
        texto.desativarFiltroExibicao();
    }
    
    /**
     * Retorna o estado do filtro de exibição.
     * @return boolean
     */
    public boolean isFiltroExibicaoAtivo(){
        return texto.isFiltroExibicaoAtivo();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Coordenavel">
    @Override
    public void setX(double x) {
        texto.setX(x);
    }

    @Override
    public void setY(double y) {
        texto.setY(y);
    }

    @Override
    public void incrementaX(double aumentoX) {
        texto.incrementaX(aumentoX);
    }

    @Override
    public void incrementaY(double aumentoY) {
        texto.incrementaY(aumentoY);
    }

    @Override
    public void setCoordenadas(double x, double y) {
        texto.setCoordenadas(x, y);
    }
    
    @Override
    public void setCoordenadas(Coordenadas novasCoordenadas) {
        texto.setCoordenadas(novasCoordenadas);
    }
    
    @Override
    public void incrementarCoordenadas(double aumentoX, double aumentoY) {
        texto.incrementarCoordenadas(aumentoX, aumentoY);
    }

    @Override
    public double getX() {
        return texto.getX();
    }

    @Override
    public int getIntX() {
        return texto.getIntX();
    }

    @Override
    public double getY() {
        return texto.getY();
    }

    @Override
    public int getIntY() {
        return texto.getIntY();
    }

    @Override
    public Coordenadas getCoordenadas() {
        return texto.getCoordenadas();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Movimentavel">
    @Override
    public void setVelX(double velX) {
        texto.setVelX(velX);
    }

    @Override
    public void setVelY(double velY) {
        texto.setVelY(velY);
    }

    @Override
    public void incrementaVelX(double aumentoVelX) {
        texto.incrementaVelX(aumentoVelX);
    }

    @Override
    public void incrementaVelY(double aumentoVelY) {
        texto.incrementaVelY(aumentoVelY);
    }

    @Override
    public void setVelocidades(double velX, double velY) {
        texto.setVelocidades(velX, velY);
    }

    @Override
    public void incrementarVelocidades(double aumentoVelX, double aumentoVelY) {
        texto.incrementarVelocidades(aumentoVelX, aumentoVelY);
    }

    @Override
    public double getVelX() {
        return texto.getVelX();
    }

    @Override
    public int getIntVelX() {
        return texto.getIntVelX();
    }

    @Override
    public double getVelY() {
        return texto.getVelY();
    }

    @Override
    public int getIntVelY() {
        return texto.getIntVelY();
    }

    @Override
    public void movimentar(TipoMovimento novoMovimento) {
        auxMov.setMovimentoPorTipo(novoMovimento);        
        texto.movimentar(novoMovimento);
    }

    @Override
    public void movimentar(double direcaoEmGraus) {
        auxMov.setMovimentoPorGrau(direcaoEmGraus);        
        texto.movimentar(direcaoEmGraus);
    }

    @Override
    public void movimentarCurvaQuadratica(
            double alturaX, double alturaY, 
            double comprimentoX, double comprimentoY
    ) {
        auxMov.setMovimentoPorCurvaQuadratica(
                alturaX, alturaY, comprimentoX, comprimentoY);
        texto.movimentarCurvaQuadratica(alturaX, alturaY, comprimentoX, comprimentoY);
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
        texto.movimentarRepetidamente(indiceDeformardorX, indiceDeformardorY, 
                comprimento, altura, inverter);
    }

    @Override
    public void movimentarPorFuncao(String funcao, boolean iniciarEmCoordNegativa) {
        auxMov.setMovimentoPorFuncao(funcao, iniciarEmCoordNegativa);
        texto.movimentarPorFuncao(funcao, iniciarEmCoordNegativa);
    }

    @Override
    public void pararMovimento() {
        auxMov.setMovimentoNenhum();
        texto.pararMovimento();
    }

    @Override
    public double calcularFuncao(String funcao) {
        return texto.calcularFuncao(funcao);
    }

    @Override
    public double calcularFuncao(String funcao, double intervaloIncial, double intervaloFinal) {
        return texto.calcularFuncao(funcao, intervaloIncial, intervaloFinal);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Colidivel">
    @Override
    public CaixaColisao getCaixaColisao() {
        return texto.getCaixaColisao();
    }

    @Override
    public boolean colidiu(CaixaColisao outroObjeto, TipoColisao tipoColisao) {
        return texto.colidiu(outroObjeto, tipoColisao);
    }

    @Override
    public LadoRetangulo colidiuNoLado(CaixaColisao outroObjeto, TipoColisao tipoColisao) {
        return texto.colidiuNoLado(outroObjeto, tipoColisao);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Dimensionavel">
    @Override
    public void redimensionar(double escala) {
        texto.redimensionar(escala);
    }

    @Override
    public int getComprimento() {
        return texto.getComprimento();
    }

    @Override
    public int getAltura() {
        return texto.getAltura();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
    @Override
    public void desenha(Graphics2D g2d) {
        if (desenhaTexto) {            
            texto.desenha(g2d);
        }
        
        if (tempoPiscaPisca.atingiuMeta() && desenhaTexto) {
            tempoPiscaPisca.iniciarTempo();
            desenhaTexto = false;
        }
        
        if (tempoPiscaPisca.atingiuMeta() && !desenhaTexto) {
            tempoPiscaPisca.iniciarTempo();
            desenhaTexto = true;
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Atualizavel">
    @Override
    public void atualiza() {
        auxMov.executarMetodoMovimentoAtual(texto);
        
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
