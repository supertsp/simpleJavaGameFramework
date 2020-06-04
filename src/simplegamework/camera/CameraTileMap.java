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

package simplegamework.camera;

import simplegamework.imagem.tile.TileMap;
import simplegamework.objetoBasico.Coordenadas;
import simplegamework.objetoBasico.CaixaColisao;
import simplegamework.objetoBasico.TipoMovimento;
import simplegamework.objetoBasico.LadoRetangulo;
import simplegamework.padraoProjeto.Atualizavel;
import simplegamework.padraoProjeto.Dimensionavel;
import simplegamework.padraoProjeto.Movimentavel;
import simplegamework.padraoProjeto.AtualizavelListManuseavel;
import simplegamework.padraoProjeto.Desenhavel;
import simplegamework.padraoProjeto.Coordenavel;
import simplegamework.cor.CorRGBA;
import simplegamework.ferramenta.AtualizavelList;
import simplegamework.ferramenta.AuxiliaresMovimentavel;
import simplegamework.ferramenta.AuxiliaresMovimentavel.MetodoMovimentoAtual;
import simplegamework.ferramenta.MedidaTempo;
import simplegamework.ferramenta.TempoExecucao;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Uma CameraTileMap determina o foco de visão num TileMap, para ser exibido 
 * apenas esse foco na tela. Ela também pode se movimentar criando um efeito
 * Side-Scrolling.
 * Uma CameraTileMap sempre possui as mesmas dimensões do TileMap o que muda
 * são as Coordenadas em relação ao TileMap. O que vai gerar o efeito de corte
 * de exibição no TileMap não será a câmera mas sim as dimensões da tela do
 * jogo. Nada mais é, como se o TileMap move-se dentro da tela, criando assim,
 * um efeito de movimento e foco.
 * <br><br><small>Created on : 28/05/2015, 21:42:42</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class CameraTileMap implements Coordenavel, Movimentavel, Dimensionavel, 
        Desenhavel, Atualizavel, AtualizavelListManuseavel{
    
    protected CaixaColisao caixaFocoTileMap;
    protected TileMap tileMap;
    protected CaixaColisao caixaColisaoTelaJogo;
    protected LadoRetangulo ladoLimiteTileMap;
    protected AtualizavelList atualizavelList;
    protected CorRGBA corFiltroExibicao;
    protected TempoExecucao intermitenciaCor;
    
    //Auxiliares
    private AuxiliaresMovimentavel auxMov;
    private boolean corLigada;
    
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    /**
     * Uma CameraTileMap sempre possui as mesmas dimensões do TileMap o que muda
     * são as Coordenadas em relação ao TileMap. O que vai gerar o efeito de corte
     * de exibição no TileMap não será a câmera mas sim as dimensões da tela do
     * jogo. Nada mais é, como se o TileMap move-se dentro da tela, criando assim,
     * um efeito de movimento e foco.
     * @param xFocoInicialTileMap Em relação ao TileMap, onde o eixo x deve se
     * posicionar para começar a exibição do TileMap na tela
     * @param yFocoInicialTileMap Em relação ao TileMap, onde o eixo y deve se
     * posicionar para começar a exibição do TileMap na tela
     * @param tileMap O TileMap a ser exibido na tela
     * @param caixaColisaoTelaJogo A CaixaColisao que representa a tela inteira
     * do jogo
     */
    public CameraTileMap(        
        double xFocoInicialTileMap, double yFocoInicialTileMap,
        TileMap tileMap, CaixaColisao caixaColisaoTelaJogo
    ) {        
        caixaFocoTileMap = new CaixaColisao(
                xFocoInicialTileMap, yFocoInicialTileMap, 
                tileMap.getComprimento(), tileMap.getAltura()
        );
        caixaFocoTileMap.setCorBorda(new CorRGBA(Color.MAGENTA));
        
        this.tileMap = tileMap;
        this.caixaColisaoTelaJogo = caixaColisaoTelaJogo;
        
        atualizavelList = new AtualizavelList();
        
        //Auxiliares
        auxMov = new AuxiliaresMovimentavel();
    }
    
    /**
     * Uma CameraTileMap sempre possui as mesmas dimensões do TileMap o que muda
     * são as Coordenadas em relação ao TileMap. O que vai gerar o efeito de corte
     * de exibição no TileMap não será a câmera mas sim as dimensões da tela do
     * jogo. Nada mais é, como se o TileMap move-se dentro da tela, criando assim,
     * um efeito de movimento e foco.
     * @param xFocoInicialTileMap Em relação ao TileMap, onde o eixo x deve se
     * posicionar para começar a exibição do TileMap na tela
     * @param yFocoInicialTileMap Em relação ao TileMap, onde o eixo y deve se
     * posicionar para começar a exibição do TileMap na tela
     * @param tileMap O TileMap a ser exibido na tela
     * @param caixaColisaoTelaJogo A CaixaColisao que representa a tela inteira
     * do jogo
     * @param corFiltroExibicao Uma cor com transparência para colocar sobre 
     * todos objetos a serem exibidos
     */
    public CameraTileMap(        
        double xFocoInicialTileMap, double yFocoInicialTileMap,
        TileMap tileMap, CaixaColisao caixaColisaoTelaJogo, CorRGBA corFiltroExibicao
    ) {        
        this(xFocoInicialTileMap, yFocoInicialTileMap, tileMap, caixaColisaoTelaJogo);
        this.corFiltroExibicao = corFiltroExibicao;
        if (corFiltroExibicao != null) {
            corLigada = true;
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos Auxiliares">
    /**
     * Corrige as coordenadas de todas as camadas do TileMap para que o movimento
     * de câmera possa ocorrer.
     */
    private void arrumarCoordenadasTileMapEmMovimento(){
        int x, y;
        
        for (int contCamada = 0; contCamada < tileMap.sizeCamadas(); contCamada++) {                
            x = caixaFocoTileMap.getIntX() * -1;
            y = caixaFocoTileMap.getIntY() * -1;

            for (int linha = 0; linha < tileMap.sizeRows(); linha++) {
                for (int coluna = 0; coluna < tileMap.sizeColumns(); coluna++) {
                    if (tileMap.getTile(contCamada, linha, coluna) != null) {
                        tileMap.getTile(contCamada, linha, coluna)
                                .setCoordenadas(x, y);   
                    }
                    else if (
                        tileMap.getTileAnimado(contCamada, linha, coluna) != null
                    ){
                        tileMap.getTileAnimado(contCamada, linha, coluna)
                                .setCoordenadas(x, y);
                    }

                    x += tileMap.getComprimentoCadaTile();                
                }

                x = caixaFocoTileMap.getIntX() * -1;
                y += tileMap.getAlturaCadaTile();
            }
        }
    }
    //</editor-fold>
    
    /**
     * Quando a CameraTileMap está se movendo para um dos lados do Retangulo 
     * (ESQUERDA, DIREITA, CIMA, BAIXO), ela precisa ser parada em um desses
     * lados, a fim de dar um efeito de que o personagem chegou ao fim da fase
     * ou outro efeito qualquer.
     * @param lado O lado final do TileMap (ESQUERDA, DIREITA, CIMA, BAIXO)
     */
    public void pararMovimentoNoLadoDoTileMap(LadoRetangulo lado){
        ladoLimiteTileMap = lado;
        
        if (lado == LadoRetangulo.ESQUERDA && getX() * -1 < 0){
            pararMovimento();
            setX(0);
        }
        
        if (
            lado == LadoRetangulo.DIREITA && 
            (getX()* -1) + getComprimento() > caixaColisaoTelaJogo.getComprimento()
        ){            
            pararMovimento();
            setX((caixaColisaoTelaJogo.getComprimento() - getComprimento()) * -1);
        }
        
        if (lado == LadoRetangulo.CIMA && getY() < 0){
            pararMovimento();
            setY(0);
        }
        
        if (
            lado == LadoRetangulo.BAIXO && 
            (getY() + caixaColisaoTelaJogo.getAltura() + 1) >= getAltura()
        ){
            pararMovimento();
            setY(getAltura() - caixaColisaoTelaJogo.getAltura());
        }
    }
    
    /**
     * Inicializa o tempo de duração de exibição da corFiltroExibicao gerando um
     * efeito pisca-pisca.
     * @param milisegundos Tempo de duração de exibição da corFiltroExibicao
     */
    public void setIntermitenciaCorFiltroExibicao(int milisegundos){
        intermitenciaCor = new TempoExecucao(milisegundos, MedidaTempo.MILISSEGUNDOS);
        intermitenciaCor.iniciarTempo();
    }
        
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Coordenavel">
    @Override
    public void setX(double x) {
        caixaFocoTileMap.setX(x);
    }

    @Override
    public void setY(double y) {
        caixaFocoTileMap.setY(y);
    }

    @Override
    public void incrementaX(double aumentoX) {
        caixaFocoTileMap.incrementaX(aumentoX);
    }

    @Override
    public void incrementaY(double aumentoY) {
        caixaFocoTileMap.incrementaY(aumentoY);
    }

    @Override
    public void setCoordenadas(double x, double y) {
        caixaFocoTileMap.setCoordenadas(x, y);
    }
    
    @Override
    public void setCoordenadas(Coordenadas novasCoordenadas) {
        caixaFocoTileMap.setCoordenadas(novasCoordenadas);
    }
    
    @Override
    public void incrementarCoordenadas(double aumentoX, double aumentoY) {
        caixaFocoTileMap.incrementarCoordenadas(aumentoX, aumentoY);
    }

    @Override
    public double getX() {
        return caixaFocoTileMap.getX();
    }

    @Override
    public int getIntX() {
        return caixaFocoTileMap.getIntX();
    }

    @Override
    public double getY() {
        return caixaFocoTileMap.getY();
    }

    @Override
    public int getIntY() {
        return caixaFocoTileMap.getIntY();
    }
    
    @Override
    public Coordenadas getCoordenadas() {
        return caixaFocoTileMap.getCoordenadas();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Movimentavel">
    @Override
    public void setVelX(double velX) {
        caixaFocoTileMap.setVelX(velX);
    }

    @Override
    public void setVelY(double velY) {
        caixaFocoTileMap.setVelY(velY);
    }

    @Override
    public void incrementaVelX(double aumentoVelX) {
        caixaFocoTileMap.incrementaVelX(aumentoVelX);
    }

    @Override
    public void incrementaVelY(double aumentoVelY) {
        caixaFocoTileMap.incrementaVelY(aumentoVelY);
    }

    @Override
    public void setVelocidades(double velX, double velY) {
        caixaFocoTileMap.setVelocidades(velX, velY);
    }

    @Override
    public void incrementarVelocidades(double aumentoVelX, double aumentoVelY) {
        caixaFocoTileMap.incrementarVelocidades(aumentoVelX, aumentoVelY);
    }

    @Override
    public double getVelX() {
        return caixaFocoTileMap.getVelX();
    }

    @Override
    public int getIntVelX() {
        return caixaFocoTileMap.getIntVelX();
    }

    @Override
    public double getVelY() {
        return caixaFocoTileMap.getVelY();
    }

    @Override
    public int getIntVelY() {
        return caixaFocoTileMap.getIntVelY();
    }

    @Override
    public void movimentar(TipoMovimento novoMovimento) {
        auxMov.setMovimentoPorTipo(novoMovimento);
        
        caixaFocoTileMap.movimentar(novoMovimento);
    }
    
    @Override
    public void movimentar(double direcaoEmGraus) {
        auxMov.setMovimentoPorGrau(direcaoEmGraus);
        
        caixaFocoTileMap.movimentar(direcaoEmGraus);
    }    
    
    @Override
    public void movimentarCurvaQuadratica(
            double alturaX, double alturaY, 
            double comprimentoX, double comprimentoY){
        auxMov.setMovimentoPorCurvaQuadratica(alturaX, alturaY, comprimentoX, comprimentoY);
        
        caixaFocoTileMap.movimentarCurvaQuadratica(alturaX, alturaY, comprimentoX, comprimentoY);
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
        
        caixaFocoTileMap.movimentarRepetidamente(
                indiceDeformardorX, indiceDeformardorY, 
                comprimento, altura, inverter
        );
    }

    @Override
    public void movimentarPorFuncao(String funcao, boolean iniciarEmCoordNegativa) {
        auxMov.setMovimentoPorFuncao(funcao, iniciarEmCoordNegativa);
        
        caixaFocoTileMap.movimentarPorFuncao(funcao, iniciarEmCoordNegativa);
    }

    @Override
    public void pararMovimento() {
        auxMov.setMovimentoNenhum();
        caixaFocoTileMap.pararMovimento();
    }

    @Override
    public double calcularFuncao(String funcao) {
        return caixaFocoTileMap.calcularFuncao(funcao);
    }

    @Override
    public double calcularFuncao(String funcao, double intervaloIncial, double intervaloFinal) {
        return caixaFocoTileMap.calcularFuncao(funcao, intervaloIncial, intervaloFinal);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Dimensionavel">
    @Override
    public void redimensionar(double escala) {
        caixaFocoTileMap.redimensionar(escala);
    }

    @Override
    public int getComprimento() {
        return caixaFocoTileMap.getComprimento();
    }

    @Override
    public int getAltura() {
        return caixaFocoTileMap.getAltura();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
    @Override
    public void desenha(Graphics2D g2d) {
        arrumarCoordenadasTileMapEmMovimento();        
        tileMap.desenha(g2d);
        
        if (corFiltroExibicao != null) {
            if (corLigada) {
                g2d.setColor(corFiltroExibicao.getColor());
                g2d.fillRect(0, 0, caixaColisaoTelaJogo.getComprimento(), 
                        caixaColisaoTelaJogo.getAltura());
                
                if (intermitenciaCor != null && intermitenciaCor.atingiuMeta()) {
                    corLigada = false;
                    intermitenciaCor.iniciarTempo();
                }
            }
            
            if (intermitenciaCor != null && intermitenciaCor.atingiuMeta()) {
                corLigada = true;
                intermitenciaCor.iniciarTempo();
            }
        }
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
        
        //<editor-fold defaultstate="collapsed" desc="PararMovimentoNoLado">
        pararMovimentoNoLadoDoTileMap(ladoLimiteTileMap);
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Animando tile animado">
        for (int contCamada = 0; contCamada < tileMap.sizeCamadas(); contCamada++) {
            for (int linha = 0; linha < tileMap.sizeRows(); linha++) {
                for (int coluna = 0; coluna < tileMap.sizeColumns(); coluna++) {
                    if (
                        tileMap.getCamada(contCamada).getTile(linha, coluna) == null &&
                        tileMap.getCamada(contCamada).getTileAnimado(linha, coluna) != null
                    ) {
                        tileMap.getCamada(contCamada).getTileAnimado(linha, coluna).atualiza();
                    }
                }
            }
        }
        //</editor-fold>
                
        invocarAtualizavelList();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: AtualizavelListManuseavel">
    @Override
    public void invocarAtualizavelList() {
        atualizavelList.invocarAtualizavelList();
    }

    @Override
    public void addAtualizavel(Atualizavel novoElemento) {
        atualizavelList.add(novoElemento);
    }

    @Override
    public void addAtualizavel(Atualizavel... variosElementos) {
        atualizavelList.add(variosElementos);
    }

    @Override
    public Atualizavel getAtualizavel(int indice) {
        return atualizavelList.get(indice);
    }

    @Override
    public Atualizavel getNextAtualizavel() {
        return atualizavelList.getNext();
    }

    @Override
    public void removeAtualizavel(int indice) {
        atualizavelList.remove(indice);
    }

    @Override
    public void removeAtualizavel(Atualizavel elementoParaRemover) {
        atualizavelList.remove(elementoParaRemover);
    }

    @Override
    public int sizeAtualizavelList() {
        return atualizavelList.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Object">    
    /**
     * Desenha um retângulo (CameraTileMap) e seus quatro pares de coordenadas
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
        coord1 = caixaFocoTileMap
                .getLadoCoordenadas(LadoRetangulo.ESQUERDA_CIMA)
                .toString();

        coord2 = caixaFocoTileMap
                .getLadoCoordenadas(LadoRetangulo.DIREITA_CIMA)
                .toString();
        
        int restaEspacos = tamanhoLinhaCoord - (coord1.length() + coord2.length());

        for (int cont = 0; cont < restaEspacos; cont++) {
            espacos += " ";
        }
        
        desenhoFinal += "  |" + coord1 + espacos + coord2 + "|\n" + linhaMeioTemp;
        espacos = "";
        
        //COORDENADAS INFERIORES
        coord1 = caixaFocoTileMap
                .getLadoCoordenadas(LadoRetangulo.ESQUERDA_BAIXO)
                .toString();

        coord2 = caixaFocoTileMap
                .getLadoCoordenadas(LadoRetangulo.DIREITA_BAIXO)
                .toString();
        
        restaEspacos = tamanhoLinhaCoord - (coord1.length() + coord2.length());

        for (int cont = 0; cont < restaEspacos; cont++) {
            espacos += " ";
        }

        desenhoFinal += "  |" + coord1 + espacos + coord2 + "|\n  " + linhaTopoBase;
        
        return
            "CameraTileMap {\n" +
            "  dimesões da câmera: " + getComprimento() + "x" + getAltura() + "px\n" +
            "  dimesões do TileMap: linhas(" + tileMap.sizeRows() + "), colunas(" + tileMap.sizeColumns() + 
                ") - " + tileMap.getComprimento() + "x" + tileMap.getAltura() + "px\n" +
            "  dimesões dos tiles: " + tileMap.getComprimentoCadaTile() + "x" + tileMap.getAlturaCadaTile() + "px\n" +
            "  quantidade de tiles por camada no TileMap: " + tileMap.getCamada(0).sizeElements() + "\n" +
            "  corFiltroExibicao: " + 
                (corFiltroExibicao == null ?
                "null" :
                corFiltroExibicao.getCodigoHexadecimal())
                + "\n" +
            "  tempo intermitencia corFiltroExibicao: " +                    
                    (intermitenciaCor == null ?
                    "0" :
                    intermitenciaCor.getMeta()) + "ms\n" +
            desenhoFinal + "\n" +
            "}\n";
    }
    //</editor-fold>
    
}
