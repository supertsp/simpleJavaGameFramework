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

package simplegamework.imagem.tile;

import simplegamework.padraoProjeto.MatrizTileManuseavel;
import simplegamework.padraoProjeto.Desenhavel;
import simplegamework.imagem.ImagemBitmap;
import simplegamework.imagem.ImagemBitmapMatriz;
import simplegamework.objetoBasico.CaixaColisao;
import simplegamework.objetoBasico.LadoRetangulo;
import java.awt.Graphics2D;

/**
 * Uma folha de tile ou TileSheet é uma ImagemBitmap que é fatiada para gerar
 * uma ImagemBitmapMatriz que cada tile (pequena ImagemBitmap) contém um código.
 * Essa classe resolve o problema acima para que fique mais fácil a construção de
 * cada CamadaTiles e por fim do próprio TileMap.<br>
 * O código de cada tile é gerado automaticamente começando do 0 (linha=0, coluna=0)
 * até o último, a saber, n (linha=última, coluna=última).<br>
 * Um TileSheet também pode ter um rótulo a fim de indenficá-lo quando se possui
 * muitos TileSheets.
 * <br><br><small>Created on : 01/05/2015, 22:18:55</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class TileSheet implements MatrizTileManuseavel, Desenhavel{
    
    protected String imagemTileSheetURL;
    protected Object rotulo;
    protected ImagemBitmapMatriz matrizTiles;
    protected int comprimentoCadaTile;
    protected int alturaCadaTile;
    protected int margemEntreTiles;
    protected int margemExtraNaDireitaBaixo;
    protected int qtdLinhas;
    protected int qtdColunas;
    
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    /**
     * Construtor básico que gera a ImagemBitmapMatriz de tiles.<br>
     * Obs.: Caso a imagem a ser importada possua um espaço entre cada tile, este
     * espaço também será levado em conta para espaçar cada tile da margem da folha,
     * ou seja, produzirá o mesmo efeito do padding em CSS.
     * @param imagemTileSheetURL O endereço da imagem que contém o desenho do 
     * tile sheet, a partir do Pacote Default Java.
     * @param comprimentoCadaTile O comprimento de cada tile na imagem
     * @param alturaCadaTile A altura de cada tile na imagem
     * @param margemEntreTiles Espaço entre cada tile
     */
    public TileSheet(
            String imagemTileSheetURL, int comprimentoCadaTile, int alturaCadaTile, 
            int margemEntreTiles
    ){        
        this.imagemTileSheetURL = imagemTileSheetURL;
        ImagemBitmap desenhoTiles = new ImagemBitmap(imagemTileSheetURL, 0, 0);
        this.comprimentoCadaTile = comprimentoCadaTile > 0 ? comprimentoCadaTile : 0;
        this.alturaCadaTile = alturaCadaTile > 0 ? alturaCadaTile : 0;
        this.margemEntreTiles = margemEntreTiles > 0 ? margemEntreTiles : 0;
        margemExtraNaDireitaBaixo = margemEntreTiles;        
        initQtdLinhasColunas(desenhoTiles);
        initMatrizTiles(desenhoTiles);
        arrumarCoordenadasCadaTiles();
    }
    /**
     * Construtor que gera uma ImagemBitmapMatriz de tiles com um rótulo.<br>
     * Obs.: Caso a imagem a ser importada possua um espaço entre cada tile, este
     * espaço também será levado em conta para espaçar cada tile da margem da folha,
     * ou seja, produzirá o mesmo efeito do padding em CSS.
     * @param rotuloTileSheet O identificador do TileSheet
     * @param imagemTileSheetURL O endereço da imagem que contém o desenho de cada tile
     * @param comprimentoCadaTile O comprimento de cada tile na imagem
     * @param alturaCadaTile A altura de cada tile na imagem
     * @param margemEntreTiles Espaço entre cada tile
     */
    public TileSheet(
            Object rotuloTileSheet, String imagemTileSheetURL, int comprimentoCadaTile, 
            int alturaCadaTile, int margemEntreTiles
    ){
        this(imagemTileSheetURL, comprimentoCadaTile, alturaCadaTile, margemEntreTiles);
        rotulo = rotuloTileSheet;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metódos: Auxiliares">
    /**
     * Descobre a quantidade de linhas e colunas necessárias para criar a matriz
     * de tiles.
     * @param desenhoTiles A ImagemBitmap que representa o desenho de cada tile
     */
    private void initQtdLinhasColunas(ImagemBitmap desenhoTiles) {
        if (desenhoTiles.isImportouImagem()) {
            qtdLinhas = (int) (
                    (desenhoTiles.getAltura() - margemExtraNaDireitaBaixo)
                    / (alturaCadaTile + margemEntreTiles + 0.0)
            );
            qtdColunas = (int) (
                    (desenhoTiles.getComprimento() - margemExtraNaDireitaBaixo)
                    / (comprimentoCadaTile + margemEntreTiles + 0.0)
            );
        }
    }
    
    /**
     * Cria a matriz de tiles baseada na quantidade de linhas e colunas.
     * @param desenhoTiles A ImagemBitmap que representa o desenho de cada tile
     */
    private void initMatrizTiles(ImagemBitmap desenhoTiles) {
        if (desenhoTiles.isImportouImagem()) {        
            matrizTiles = new ImagemBitmapMatriz(qtdLinhas, qtdColunas);
            int x = margemEntreTiles;
            int y = margemEntreTiles;
            int codigoImagem = 0;
            
            for (int linha = 0; linha < qtdLinhas; linha++) {
                for (int coluna = 0; coluna < qtdColunas; coluna++) {
                    if (x <= desenhoTiles.getComprimento()) {
                        ImagemBitmap tempTile = desenhoTiles
                                .getFatiaImagemBitmap(
                                        x,
                                        y,
                                        comprimentoCadaTile,
                                        alturaCadaTile
                        );                        
                        tempTile.setCodigo(codigoImagem);
                        matrizTiles.addNext(tempTile);
                        
                        codigoImagem++;
                    }//if
                    
                    x += comprimentoCadaTile + margemEntreTiles;
                }//for coluna

                x = margemEntreTiles;
                y += alturaCadaTile + margemEntreTiles;
            }//for linha
        }
    }
    
    /**
     * Como cada tile já vem com sua própria coordenada, faz-se necessário ajustar
     * as coordenadas para que sejam condizentes com essa CamadaTiles.
     */
    private void arrumarCoordenadasCadaTiles(){
        int x = 0;
        int y = 0;
        
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                getTile(linha, coluna).setCoordenadas(x, y);                
                x += getComprimentoCadaTile();                
            }
            
            x = 0;
            y += getAlturaCadaTile();
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GETs">
    /**
     * Retorna a URL da imagem do TileSheet.
     * @return String
     */
    public String getImagemTileSheetURL(){
        return imagemTileSheetURL;
    }
    
    /**
     * Retorna o rótulo do TileSheet.
     * @return Object
     */
    public Object getRotulo(){
        return rotulo;
    }
    
    /**
     * Retorna o valor da margem entre cada tile.<br>
     * Obs.: Assume-se que os valores das margens dos 4 lados da folha é o mesmo do
     * espaçamento entre cada tile.
     * @return int
     */
    public int getMargemEntreTiles(){
        return margemEntreTiles;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: MatrizTileManuseavel">
    @Override
    public int getTileCodigo(int linha, int coluna) {
        return matrizTiles.get(linha, coluna).getCodigo();
    }

    @Override
    public ImagemBitmap getTile(int linha, int coluna) {
        return matrizTiles.get(linha, coluna);
    }

    @Override
    public ImagemBitmap getTile(int codigo) {
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if(getTile(linha, coluna).getCodigo() == codigo){
                    return getTile(linha, coluna);
                }
            }
        }
        
        return  null;
    }

    @Override
    public ImagemBitmap getNextTile() {
        return matrizTiles.getNext();
    }

    @Override
    public CaixaColisao getTileCaixaColisaoImagemToda(int linha, int coluna) {
        return getTile(linha, coluna).getCaixaColisaoImagemToda();
    }

    @Override
    public CaixaColisao getTileCaixaColisaoImagemToda(int codigo) {
        return getTile(codigo).getCaixaColisaoImagemToda();
    }

    @Override
    public int sizeElements() {
        return sizeRows() * sizeColumns();
    }

    @Override
    public int sizeRows() {
        return qtdLinhas;
    }

    @Override
    public int sizeColumns() {
        return qtdColunas;
    }

    @Override
    public int getComprimentoCadaTile() {
        return comprimentoCadaTile;
    }

    @Override
    public int getAlturaCadaTile() {
        return alturaCadaTile;
    }

    @Override
    public int getComprimento() {
        return getComprimentoCadaTile() * sizeColumns();
    }

    @Override
    public int getAltura() {
        return getAlturaCadaTile() * sizeRows();
    }
    //</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
    @Override
    public void desenha(Graphics2D g2d) {
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                getTile(linha, coluna).desenha(g2d);
            }
        }
    }
    //</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Object">
    /**
     * Retorna uma String que descreve o objeto TileSheet
     * @return String
     */
    @Override
    public String toString(){
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
        
        for (int cont = 0; cont < sizeColumns(); cont++) {
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
        
        int contTile = 0;
        for (int linha = 0; linha < sizeRows(); linha++) {//linha
            //Preenche COORDENADAS SUPERIORES
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                coord1 = getTile(linha, coluna)
                        .getCaixaColisao(0)
                        .getLadoCoordenadas(LadoRetangulo.ESQUERDA_CIMA)
                        .toString();

                coord2 = getTile(linha, coluna)
                        .getCaixaColisao(0)
                        .getLadoCoordenadas(LadoRetangulo.DIREITA_CIMA)
                        .toString();
                
                String espacos = "";
                int restaEspacos = tamanhoLinhaCoord - (coord1.length() + coord2.length());
                
                for (int cont = 0; cont < restaEspacos; cont++) {
                    espacos += " ";
                }
                
                linhaCoordFinal += "|" + coord1 + espacos + coord2 + "|";
            }//coluna
            
            linhaCoordFinal += "\n";
            desenhoFinal += linhaCoordFinal + linhaMeioCima;
            linhaCoordFinal = "";
            
            //Preenche INFO DO MEIO
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {//coluna
                String info = contTile + " : {" + linha + "," + coluna + "} = " + 
                        getTile(linha, coluna).getCodigo();
                
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
                contTile++;
            }//coluna
            
            desenhoFinal += linhaMeioInfo + "\n" + linhaMeioBaixo;
            linhaMeioInfo = "";
            
            //Preenche COORDENADAS INFERIORES
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {//coluna            
                coord1 = getTile(linha, coluna)
                        .getCaixaColisao(0)
                        .getLadoCoordenadas(LadoRetangulo.ESQUERDA_BAIXO)
                        .toString();

                coord2 = getTile(linha, coluna)
                        .getCaixaColisao(0)
                        .getLadoCoordenadas(LadoRetangulo.DIREITA_BAIXO)
                        .toString();
                
                String espacos = "";
                int restaEspacos = tamanhoLinhaCoord - (coord1.length() + coord2.length());
                
                for (int cont = 0; cont < restaEspacos; cont++) {
                    espacos += " ";
                }
                
                linhaCoordFinal += "|" + coord1 + espacos + coord2 + "|";
            }//coluna
            
            linhaCoordFinal += "\n";
            desenhoFinal += linhaCoordFinal + linhaTopoBaseFinal;
            linhaCoordFinal = "";
        }//linha
                        
        return
            "TileSheet : " + getRotulo() + "{\n" +
            "  - dimesões da matriz: linhas(" + sizeRows() + "), colunas(" + sizeColumns() + ")\n" +
            "  - dimesões dos tiles: " + getComprimentoCadaTile() + "x" + getAlturaCadaTile() + "px\n" +
            "  - quantidade de tiles na matriz: " + sizeElements() + "\n" + 
            "  - margem entre cada tile e nas laterais da folha: " + getMargemEntreTiles() + "px\n" +
            desenhoFinal + "\n" +
            "}\n";
    }
    //</editor-fold>
    
}
