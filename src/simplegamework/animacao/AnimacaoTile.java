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

package simplegamework.animacao;

import simplegamework.ferramenta.AtualizavelList;
import simplegamework.ferramenta.MedidaTempo;
import simplegamework.ferramenta.TempoExecucao;
import simplegamework.ferramenta.AuxiliaresMovimentavel;
import simplegamework.ferramenta.Alternador;
import simplegamework.objetoBasico.TipoColisao;
import simplegamework.objetoBasico.Coordenadas;
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
import simplegamework.padraoProjeto.Animavel;
import simplegamework.padraoProjeto.Coordenavel;
import simplegamework.ferramenta.AuxiliaresMovimentavel.MetodoMovimentoAtual;
import simplegamework.imagem.*;
import java.awt.Graphics2D;

/**
 * As vezes precisa-se colocar um tile animado dentro de uma CamadaTiles, essa
 * classe vem para resolver esse problema.
 * <br><br><small>Created on : 03/08/2015, 23:51:55</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class AnimacaoTile implements Coordenavel, Movimentavel, Animavel, 
        Dimensionavel, ImagemTransformavel, Colidivel, Desenhavel, Atualizavel, 
        AtualizavelListManuseavel
{

    protected Object nomeAcao;
    protected ImagemBitmapList tileList;
    protected TempoExecucao tempoExecucaoTile;
    protected int tileAtual;
    protected boolean playOnce;
    
    //Auxiliares
    private boolean primeiraExecucao;
    private Alternador<MetodoExecucao> estadoAnimacao;
    private AuxiliaresMovimentavel auxMov;
    private AtualizavelList atualizaveis;
    
    private enum MetodoExecucao{
        NENHUM, PLAY, PLAY_ONCE, STOP, PAUSE;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    /**
     * Construtor básico de classe.
     * @param tileList A sequência de tiles a serem animadas
     * @param tempoDuracaoCadaTile O tempo de espera entre cada tile
     * @param medidaPadraoTempo O tipo de tempo a ser trabalhado, ele pode ser:
     * MedidaTempo.NANOSSEGUNDOS, MedidaTempo.MILISSEGUNDOS ou 
     * MedidaTempo.SEGUNDOS
     */
    public AnimacaoTile(
            ImagemBitmapList tileList, double tempoDuracaoCadaTile, 
            MedidaTempo medidaPadraoTempo
    ){
        this.tileList = tileList;
        tempoExecucaoTile = new TempoExecucao(tempoDuracaoCadaTile, 
                medidaPadraoTempo
        );
        
        //Auxiliares
        primeiraExecucao = true;
        estadoAnimacao = new Alternador<>(
                MetodoExecucao.NENHUM,
                MetodoExecucao.PLAY, 
                MetodoExecucao.PLAY_ONCE, 
                MetodoExecucao.PAUSE, 
                MetodoExecucao.STOP
        );
        auxMov = new AuxiliaresMovimentavel();
        atualizaveis = new AtualizavelList();
    }
    
    /**
     * Construtor completo de classe.
     * @param nomeAcao O nome da ação que representa esta sequência de tiles
     * @param tileList A sequência de tiles a serem animadas
     * @param tempoDuracaoCadaTile O tempo de espera entre cada tile
     * @param medidaPadraoTempo O tipo de tempo a ser trabalhado, ele pode ser:
     * MedidaTempo.NANOSSEGUNDOS, MedidaTempo.MILISSEGUNDOS ou 
     * MedidaTempo.SEGUNDOS
     */
    public AnimacaoTile(
            Object nomeAcao, ImagemBitmapList tileList, double tempoDuracaoCadaTile, 
            MedidaTempo medidaPadraoTempo
    ){
        this(tileList, tempoDuracaoCadaTile, medidaPadraoTempo);
        this.nomeAcao = nomeAcao;        
    }
    //</editor-fold>
    
    /**
     * Retorna o objeto que representa o nome da ação.
     * @return Object
     */
    public Object getNomeAcao() {
        return nomeAcao;
    }
    
    /**
     * Retorna o índice do tile atual da animação. Este método é muito útil para
     * se descobrir quando um ciclo de animação termina.
     * @return int
     */
    public int getIndiceTileAtual(){
        return tileAtual;
    }
    
    /**
     * Retorna o list de tiles.
     * @return ImagemBitmapList
     */
    public ImagemBitmapList getTileList(){
        return tileList;
    }
    
    /**
     * Ativa o debugGrafico, ou seja, escreve os atributos de Coordenadas na tela.
     */
    public void ligarDebugGraficoCoordenadas(){
        for (int cont = 0; cont < tileList.size(); cont++) {
            tileList.get(cont).ligarDebugGraficoCoordenadas();
        }
        
    }
    
    /**
     * Desativa o debugGrafico de Coordenadas, ou seja, não serão exibidos os atributos de
     * Coordenadas na tela.
     */
    public void desligarDebugGraficoCoordenadas(){
        tileList.get(0).desligarDebugGraficoCoordenadas();
    }
    
    /**
     * Retorna o estado atual de debugGrafico de Coordenadas.
     * @return boolean
     */
    public boolean isDebugGraficoAtivoCoordenadas(){
        return tileList.get(0).isDebugGraficoAtivoCoordenadas();
    }
    
    /**
     * Ativa a exibição da cor da borda de todas CaixaColisao dentro de cada
     * imagem da animação.
     */
    public void ativarCorBordaCaixaColisaoListTodasImagens() {
        tileList.ativarCorBordaCaixaColisaoListTodasImagens();
    }

    /**
     * Desativa a exibição da cor da borda de todas CaixaColisao dentro de cada
     * imagem da animação.
     */
    public void desativarCorBordaCaixaColisaoListTodasImagens() {
        tileList.desativarCorBordaCaixaColisaoListTodasImagens();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Coordenavel">
    @Override
    public void setX(double x) {
        tileList.setX(x);
    }

    @Override
    public void setY(double y) {
        tileList.setY(y);
    }

    @Override
    public void incrementaX(double aumentoX) {
        tileList.incrementaX(aumentoX);
    }

    @Override
    public void incrementaY(double aumentoY) {
        tileList.incrementaY(aumentoY);
    }

    @Override
    public void setCoordenadas(double x, double y) {
        tileList.setCoordenadas(x, y);
    }
    
    @Override
    public void setCoordenadas(Coordenadas novasCoordenadas) {
        tileList.setCoordenadas(novasCoordenadas);
    }
    

    @Override
    public void incrementarCoordenadas(double aumentoX, double aumentoY) {
        tileList.incrementarCoordenadas(aumentoX, aumentoY);
    }

    @Override
    public double getX() {
        return tileList.getX();
    }

    @Override
    public int getIntX() {
        return tileList.getIntX();
    }

    @Override
    public double getY() {
        return tileList.getY();
    }

    @Override
    public int getIntY() {
        return tileList.getIntY();
    }
    
    @Override
    public Coordenadas getCoordenadas() {
        return tileList.getCoordenadas();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Movimentavel">
    @Override
    public void setVelX(double velX) {
        for (int cont = 0; cont < tileList.size(); cont++) {
            tileList.get(cont).setVelX(velX);
        }
    }

    @Override
    public void setVelY(double velY) {
        for (int cont = 0; cont < tileList.size(); cont++) {
            tileList.get(cont).setVelY(velY);
        }
    }

    @Override
    public void incrementaVelX(double aumentoVelX) {
        for (int cont = 0; cont < tileList.size(); cont++) {
            tileList.get(cont).incrementaVelX(aumentoVelX);
        }
    }

    @Override
    public void incrementaVelY(double aumentoVelY) {
        for (int cont = 0; cont < tileList.size(); cont++) {
            tileList.get(cont).incrementaVelY(aumentoVelY);
        }
    }

    @Override
    public void setVelocidades(double velX, double velY) {
        for (int cont = 0; cont < tileList.size(); cont++) {
            tileList.get(cont).setVelocidades(velX, velY);
        }
    }

    @Override
    public void incrementarVelocidades(double aumentoVelX, double aumentoVelY) {
        for (int cont = 0; cont < tileList.size(); cont++) {
            tileList.get(cont).incrementarVelocidades(aumentoVelX, aumentoVelY);
        }
    }

    @Override
    public double getVelX() {
        return tileList.get(0).getVelX();
    }

    @Override
    public int getIntVelX() {
        return tileList.get(0).getIntVelX();
    }

    @Override
    public double getVelY() {
        return tileList.get(0).getVelY();
    }

    @Override
    public int getIntVelY() {
        return tileList.get(0).getIntVelY();
    }

    @Override
    public void movimentar(TipoMovimento novoMovimento) {
        auxMov.setMovimentoPorTipo(novoMovimento);
        
        for (int cont = 0; cont < tileList.size(); cont++) {
            tileList.get(cont).movimentar(novoMovimento);
        }
    }

    @Override
    public void movimentar(double direcaoEmGraus) {
        auxMov.setMovimentoPorGrau(direcaoEmGraus);
        
        for (int cont = 0; cont < tileList.size(); cont++) {
            tileList.get(cont).movimentar(direcaoEmGraus);
        }
    }
    
    @Override
    public void movimentarCurvaQuadratica(
            double alturaX, double alturaY, 
            double comprimentoX, double comprimentoY){
        auxMov.setMovimentoPorCurvaQuadratica(alturaX, alturaY, comprimentoX, comprimentoY);
        
        for (int cont = 0; cont < tileList.size(); cont++) {
            tileList.get(cont).movimentarCurvaQuadratica(
                    alturaX, alturaY, 
                    comprimentoX, comprimentoY
            );
        }
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
        
        for (int cont = 0; cont < tileList.size(); cont++) {
            tileList.get(cont).movimentarRepetidamente(
                    indiceDeformardorX, indiceDeformardorY, 
                    comprimento, altura, inverter
            );
        }
    }

    @Override
    public void movimentarPorFuncao(String funcao, boolean iniciarEmCoordNegativa) {
        auxMov.setMovimentoPorFuncao(funcao, iniciarEmCoordNegativa);
        
        for (int cont = 0; cont < tileList.size(); cont++) {
            tileList.get(cont).movimentarPorFuncao(funcao, iniciarEmCoordNegativa);
        }
    }

    @Override
    public void pararMovimento() {
        auxMov.setMovimentoNenhum();
        
        for (int cont = 0; cont < tileList.size(); cont++) {
            tileList.get(cont).pararMovimento();
        }
    }

    @Override
    public double calcularFuncao(String funcao) {
        return tileList.get(0).calcularFuncao(funcao);
    }

    @Override
    public double calcularFuncao(String funcao, double intervaloIncial, double intervaloFinal) {
        return tileList.get(0).calcularFuncao(funcao, intervaloIncial, intervaloFinal);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Animavel">
    @Override
    public void play() {
        //Avalia se antes estava em pausa
        if (estadoAnimacao.getEstadoAtual() == MetodoExecucao.PAUSE) {
            tempoExecucaoTile.retomarTempo();
        }
        
        //Muda para o estado de PLAY
        estadoAnimacao.mudarEstadoPara(MetodoExecucao.PLAY);
        
        //Avalia se é a Primeira Execução
        if (primeiraExecucao) {
            tempoExecucaoTile.iniciarTempo();
            primeiraExecucao = false;
        }
        
        //Avalia se Atingiu a Meta
        if (tempoExecucaoTile.atingiuMeta()) {
            tileAtual++;
            
            if (tileAtual >= tileList.size()) {
                tileAtual = 0;                
            }
            
            tempoExecucaoTile.iniciarTempo();
        }        
    }    
        
    @Override
    public void playOnce() {
        playOnce = true;
        
        //Avalia se antes estava em pausa
        if (estadoAnimacao.getEstadoAtual() == MetodoExecucao.PAUSE) {
            tempoExecucaoTile.retomarTempo();
        }
        
        //Muda para o estado de PLAY_ONCE        
        estadoAnimacao.mudarEstadoPara(MetodoExecucao.PLAY_ONCE);
        
        
        //Avalia se é a Primeira Execução
        if (primeiraExecucao) {
            tempoExecucaoTile.iniciarTempo();
            primeiraExecucao = false;
        }        
        
        //Avalia se Atingiu a Meta
        if (tempoExecucaoTile.atingiuMeta()) {
            tileAtual++;

            if (tileAtual >= tileList.size()) {
                playOnce = false;
                tileAtual = 0;
            }
            
            tempoExecucaoTile.iniciarTempo();
        }
    }
    
    @Override
    public void play(int indiceInicial) {
        if (indiceInicial >= 0 && indiceInicial < tileList.size()) {
            tileAtual = indiceInicial;
            play();
        }
    }
    
    @Override
    public void playOnce(int indiceInicial) {
        if (indiceInicial >= 0 && indiceInicial < tileList.size()) {
            tileAtual = indiceInicial;
            playOnce();
        }
    }
    

    @Override
    public void pause() {
        estadoAnimacao.mudarEstadoPara(MetodoExecucao.PAUSE);
        tempoExecucaoTile.pausarTempo();
    }

    @Override
    public void stop() {
        estadoAnimacao.mudarEstadoPara(MetodoExecucao.STOP);
        tileAtual = 0;
        primeiraExecucao = true;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Dimensionavel">
    @Override
    public void redimensionar(double escala) {
        for (int cont = 0; cont < tileList.size(); cont++) {
            tileList.get(cont).redimensionar(escala);
        }
    }
    
    /**
     * Retorna o comprimento do tileList atual em exibição.
     * @return int
     */
    @Override
    public int getComprimento() {
        return tileList.get(tileAtual).getComprimento();
    }
    
    /**
     * Retorna a altura do tileList atual em exibição.
     * @return int
     */
    @Override
    public int getAltura() {
        return tileList.get(tileAtual).getAltura();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: ImagemTransformavel">
    @Override
    public void espelhar(TipoEspelhamento tipoEspelhamento) {
        for (int cont = 0; cont < tileList.size(); cont++) {
            tileList.get(cont).espelhar(tipoEspelhamento);
        }        
    }

    @Override
    public void rotacionar(double graus) {
        for (int cont = 0; cont < tileList.size(); cont++) {
            tileList.get(cont).rotacionar(graus);
        } 
    }

    @Override
    public void rotacionar(double graus, Coordenadas pivoRotacao) {
        for (int cont = 0; cont < tileList.size(); cont++) {
            tileList.get(cont).rotacionar(graus, pivoRotacao);
        } 
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Colidivel">
    @Override
    public CaixaColisao getCaixaColisao(){
        return tileList.get(tileAtual).getCaixaColisao();
    }
    
    @Override
    public boolean colidiu(CaixaColisao outraCaixaColisao, TipoColisao tipoColisao) {
        return tileList.get(tileAtual).colidiu(outraCaixaColisao, tipoColisao);
    }

    @Override
    public LadoRetangulo colidiuNoLado(CaixaColisao outraCaixaColisao, TipoColisao tipoColisao) {
        return tileList.get(tileAtual).colidiuNoLado(outraCaixaColisao, tipoColisao);
    }
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
    @Override
    public void desenha(Graphics2D g) {
        tileList.get(tileAtual).desenha(g);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Atualizavel">
    @Override
    public void atualiza() {
        //<editor-fold defaultstate="collapsed" desc="Movimentos">
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
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Animações">
        if (estadoAnimacao.getEstadoAtual() == MetodoExecucao.PLAY) {
            play();
        }
        
        if (playOnce && estadoAnimacao.getEstadoAtual() == MetodoExecucao.PLAY_ONCE) {
            playOnce();
        }
        
        if (estadoAnimacao.getEstadoAtual() == MetodoExecucao.PAUSE) {
            pause();
        }
        
        if (estadoAnimacao.getEstadoAtual() == MetodoExecucao.STOP) {
            stop();
        }
        //</editor-fold>
        
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
     * Desenha todos tiles com Strings para finalidades de debug.
     * @return String
     */
    @Override
    public String toString() {
        String linhaTopoBase = "+-----------------------+";
        String linhaTopoBaseFinal = "";
        String linhaMeio = "|                       |";
        String linhaMeioTemp = "";
        String linhaMeioCima = "";
        String linhaMeioBaixo = "";
        String linhaMeioInfo = "";
        int qtdLinhaMeio = 3;
        String coord1 = "", coord2 = "";
        String linhaCoordFinal = "";
        int tamanhoLinhaCoord = 23;
        String desenhoFinal;
        
        for (int cont = 0; cont < tileList.size(); cont++) {
            linhaTopoBaseFinal += linhaTopoBase;
            linhaMeioTemp += linhaMeio;
        }
        linhaTopoBaseFinal += "\n";
        linhaMeioTemp += "\n";
        
        desenhoFinal = linhaTopoBaseFinal;                
        
        for (int cont = 0; cont < qtdLinhaMeio; cont++) {
            linhaMeioCima += linhaMeioTemp;
            linhaMeioBaixo += linhaMeioTemp;
        }
        
        //Preenche COORDENADAS SUPERIORES
        for (int indice = 0; indice < tileList.size(); indice++) {
            coord1 = tileList.get(indice)
                    .getCaixaColisao(0)
                    .getLadoCoordenadas(LadoRetangulo.ESQUERDA_CIMA)
                    .toString();

            coord2 = tileList.get(indice)
                    .getCaixaColisao(0)
                    .getLadoCoordenadas(LadoRetangulo.DIREITA_CIMA)
                    .toString();

            String espacos = "";
            int restaEspacos = tamanhoLinhaCoord - (coord1.length() + coord2.length());

            for (int cont = 0; cont < restaEspacos; cont++) {
                espacos += " ";
            }

            linhaCoordFinal += "|" + coord1 + espacos + coord2 + "|";
        }//superiores

        linhaCoordFinal += "\n";
        desenhoFinal += linhaCoordFinal + linhaMeioCima;
        linhaCoordFinal = "";

        //Preenche INFO DO MEIO DIMENSÕES
        for (int indice = 0; indice < tileList.size(); indice++) {//coluna
            String info = 
                    indice + " : " + 
                    tileList.get(indice).getComprimento() + "x" +
                    tileList.get(indice).getAltura() + "px";

            String espacos = "";
            int restaEspacos = tamanhoLinhaCoord - info.length();
            int restaEspacosEsquerda = restaEspacos / 2;
            int restaEspacosDireita = restaEspacos / 2;

            if ((restaEspacosEsquerda + restaEspacosDireita) < restaEspacos) {
                restaEspacosEsquerda += restaEspacos - (restaEspacosEsquerda + restaEspacosDireita);
            }

            for (int cont = 0; cont < restaEspacosEsquerda; cont++) {
                espacos += " ";
            }

            linhaMeioInfo += "|" + espacos + info;
            espacos = "";

            for (int cont = 0; cont < restaEspacosDireita; cont++) {
                espacos += " ";
            }

            linhaMeioInfo += espacos + "|";
        }//info meio - dimensões

        desenhoFinal += linhaMeioInfo + "\n" + linhaMeioBaixo;
        linhaMeioInfo = "";

        //Preenche COORDENADAS INFERIORES
        for (int indice = 0; indice < tileList.size(); indice++) {//coluna            
            coord1 = tileList.get(indice)
                    .getCaixaColisao(0)
                    .getLadoCoordenadas(LadoRetangulo.ESQUERDA_BAIXO)
                    .toString();

            coord2 = tileList.get(indice)
                    .getCaixaColisao(0)
                    .getLadoCoordenadas(LadoRetangulo.DIREITA_BAIXO)
                    .toString();

            String espacos = "";
            int restaEspacos = tamanhoLinhaCoord - (coord1.length() + coord2.length());

            for (int cont = 0; cont < restaEspacos; cont++) {
                espacos += " ";
            }

            linhaCoordFinal += "|" + coord1 + espacos + coord2 + "|";
        }//inferiores

        linhaCoordFinal += "\n";
        desenhoFinal += linhaCoordFinal + linhaTopoBaseFinal;
        linhaCoordFinal = "";
        
        String medida = "";
        
        if (tempoExecucaoTile.getMedidaPadraoTempo() == MedidaTempo.NANOSSEGUNDOS) {
            medida = "ns\n";
        }

        if (tempoExecucaoTile.getMedidaPadraoTempo() == MedidaTempo.MILISSEGUNDOS) {
            medida = "ms\n";
        }

        if (tempoExecucaoTile.getMedidaPadraoTempo() == MedidaTempo.SEGUNDOS) {
            medida = "s\n";
        }
                        
        return
            "AnimacaoTile : " + getNomeAcao() + "{\n" +
            "  - dimesões: " + tileList.size() + " tile(s)\n" +
            "  - tempo entre cada tile: " + tempoExecucaoTile.getMeta() + medida +
            "  - tile atual: " + tileAtual + "\n" +
            "  - estado atual: " + estadoAnimacao.getEstadoAtual() + "\n" +
            desenhoFinal +
            "}\n";
    }
    //</editor-fold>        
    
}
