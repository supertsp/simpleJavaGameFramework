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

import simplegamework.padraoProjeto.Dimensionavel;
import simplegamework.padraoProjeto.ImagemTransformavel;
import simplegamework.padraoProjeto.MatrizTileManuseavel;
import simplegamework.padraoProjeto.Desenhavel;
import simplegamework.padraoProjeto.ListManuseavel;
import simplegamework.animacao.AnimacaoTile;
import simplegamework.imagem.ImagemBitmap;
import simplegamework.imagem.ImagemBitmapMatriz;
import simplegamework.imagem.TipoEspelhamento;
import simplegamework.objetoBasico.CaixaColisao;
import simplegamework.objetoBasico.Coordenadas;
import simplegamework.objetoBasico.LadoRetangulo;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Uma camada de tiles de TileMap representa uma ImagemBitmapMatriz com suas funcionabilidades
 * adaptadas para solucionar os problemas básicos de um TileMap.<br>
 * Como por exemplo, um TileMap pode ser uma fase do jogo e suas camadas representam
 * as camadas de imagens a serem exibidas a fim de dar profundidade e realismo ao 
 * jogo. Sendo assim, por isso cada CamadaTiles possui um rótulo.
 * <br><br><small>Created on : 01/05/2015, 22:18:55</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class CamadaTiles implements MatrizTileManuseavel, Dimensionavel, ImagemTransformavel, 
        Desenhavel
{
    
    private Object rotulo;
    private ImagemBitmapMatriz camadaTiles;
    private TileAnimadoList tileAnimadoList;
    private int comprimentoEmTiles;
    private int alturaEmTiles;
    private int comprimento;
    private int altura;
    
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    /**
     * Construtor básico de uma CamadaTiles.
     * @param tileSheet O objeto que representa todo o TileSheet a fim de criar a camada
     * @param CamadaTilesEmCSV O objeto do arquivo CSV que representa uma camada, mas em códigos
     * @param codigoTileNulo O valor do código de tile que representa um área nula
     */
    public CamadaTiles(
            TileSheet tileSheet, 
            CamadaTilesArquivoCSV CamadaTilesEmCSV, int codigoTileNulo
    ) {
        this.comprimentoEmTiles = CamadaTilesEmCSV.sizeColumns();
        this.alturaEmTiles = CamadaTilesEmCSV.sizeRows();
        
        if (tileSheet != null) {
            camadaTiles = new ImagemBitmapMatriz(alturaEmTiles, comprimentoEmTiles);
            initCamada(tileSheet, CamadaTilesEmCSV, codigoTileNulo);
            arrumarCoordenadasCadaTiles();
            comprimento = comprimentoEmTiles * tileSheet.getComprimentoCadaTile();
            altura = alturaEmTiles * tileSheet.getAlturaCadaTile();
        }
        
        tileAnimadoList = new TileAnimadoList();
    }
    
    /**
     * Construtor que já adiciona o rótulo da CamadaTiles.
     * @param rotuloCamada O identificador dessa camada, podendo ser um String ou Enum
     * @param tileSheet O objeto que representa todo o TileSheet a fim de criar a camada
     * @param CamadaTilesEmCSV O objeto do arquivo CSV que representa uma camada, mas em códigos
     * @param codigoTileNulo O valor do código de tile que representa um área nula
     */
    public CamadaTiles(
            Object rotuloCamada, TileSheet tileSheet, 
            CamadaTilesArquivoCSV CamadaTilesEmCSV, int codigoTileNulo
    ) {
        this(tileSheet, CamadaTilesEmCSV, codigoTileNulo);
        this.rotulo = rotuloCamada;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: Auxiliares">
    /**
     * Inicializa a camada de acordo com os códigos dos tiles no arquivo CSV. 
     * @param tileSheet O objeto que representa todo o TileSheet a fim de criar a camada
     * @param CamadaTilesEmCSV O objeto do arquivo CSV que representa uma camada, mas em códigos
     * @param codigoTileNulo O valor do código de tile que representa um área nula
     */
    private void initCamada(TileSheet tileSheet, CamadaTilesArquivoCSV CamadaTilesEmCSV, int codigoTileNulo) {
        int codigoTile = codigoTileNulo;
        ImagemBitmap tile;
        
        for (int linha = 0; linha < alturaEmTiles; linha++) {
            for (int coluna = 0; coluna < comprimentoEmTiles; coluna++) {
                codigoTile = CamadaTilesEmCSV.getCodigoTile(linha, coluna);
                tile = tileSheet.getTile(codigoTile);
                                
                if (tile != null) {
                    camadaTiles.add(
                        linha, 
                        coluna, 
                        //É preciso dar new para não alterar o TileSheet quando
                        //a camada for alterada
                        new ImagemBitmap(tile.getImage(), 0, 0, codigoTile)
                    );
                }
            }//for coluna
        }//for linha
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
                if (getTile(linha, coluna) != null) {
                    getTile(linha, coluna).setCoordenadas(x, y);
                }
                else if (
                    tileAnimadoList != null && tileAnimadoList.size() > 0 &&
                    getTileAnimado(linha, coluna) != null
                ) {                    
                    getTileAnimado(linha, coluna).setCoordenadas(x, y);
                }
                
                x += getComprimentoCadaTile();                
            }
            
            x = 0;
            y += getAlturaCadaTile();
        }
    }
    //</editor-fold>
    
    /**
     * Adiciona um tile animado para substituir um tile normal quando a camada
     * for desenhada.
     * @param linha A linha que tile animado será colocado
     * @param coluna A coluna que tile animado será colocado
     * @param tileAnimado O tile animado
     */
    public void addTileAnimado(int linha, int coluna, AnimacaoTile tileAnimado){
        tileAnimado.setCoordenadas(
                getTile(linha, coluna).getX(),
                getTile(linha, coluna).getY()
        );
        tileAnimadoList.add(new TileAnimado(linha, coluna, tileAnimado));
        camadaTiles.remove(linha, coluna);
    }
    
    /**
     * Retorna o tile animado procurado.
     * @param linha A linha procurada
     * @param coluna A coluna procurada
     * @return AnimacaoTile
     */
    public AnimacaoTile getTileAnimado(int linha, int coluna){
        for (int cont = 0; cont < tileAnimadoList.size(); cont++) {
            if (
                linha == tileAnimadoList.get(cont).linha &&
                coluna == tileAnimadoList.get(cont).coluna
            ) {
                return tileAnimadoList.get(cont).tileAnimado;
            }
        }
        
        return null;
    }
    
    /**
     * Remove um tile animado adicionado.
     * @param linha A linha procurada
     * @param coluna A coluna procurada
     */
    public void removeTileAnimado(int linha, int coluna){
        for (int cont = 0; cont < tileAnimadoList.size(); cont++) {
            if (
                linha == tileAnimadoList.get(cont).linha &&
                coluna == tileAnimadoList.get(cont).coluna
            ) {
                tileAnimadoList.remove(cont);
            }
        }
    }
    
    /**
     * Retorna a quantidade de TileAnimado.
     * @return int
     */
    public int sizeTileAnimado(){
        return tileAnimadoList.size();
    }
    
    //<editor-fold defaultstate="collapsed" desc="GET">
    /**
     * Retorna o rótulo da CamadaTiles.
     * @return Object
     */
    public Object getRotulo(){
        return rotulo;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: MatrizTileManuseavel">
    @Override
    public int getTileCodigo(int linha, int coluna) {
        return camadaTiles.get(linha, coluna).getCodigo();
    }

    @Override
    public ImagemBitmap getTile(int linha, int coluna) {
        return camadaTiles.get(linha, coluna);
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
    public ImagemBitmap getNextTile(){
        return camadaTiles.getNext();
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
        return alturaEmTiles;
    }

    @Override
    public int sizeColumns() {
        return comprimentoEmTiles;
    }

    @Override
    public int getComprimentoCadaTile() {
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (camadaTiles.get(linha, coluna) != null) {
                    return camadaTiles.get(linha, coluna).getComprimento();
                }
            }
        }
        return 0;
    }

    @Override
    public int getAlturaCadaTile() {
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (camadaTiles.get(linha, coluna) != null) {
                    return camadaTiles.get(linha, coluna).getAltura();
                }
            }
        }
        return 0;
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
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Dimensionavel">
    @Override
    public void redimensionar(double escala) {
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                getTile(linha, coluna).redimensionar(escala);
            }
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: ImagemTransformavel">
    @Override
    public void espelhar(TipoEspelhamento tipoEspelhamento) {
        camadaTiles.espelhar(tipoEspelhamento);
    }

    @Override
    public void rotacionar(double graus) {
        camadaTiles.rotacionar(graus);
    }

    @Override
    public void rotacionar(double graus, Coordenadas pivoRotacao) {
        camadaTiles.rotacionar(graus, pivoRotacao);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
    @Override
    public void desenha(Graphics2D g2d) {
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (getTile(linha, coluna) != null) {
                    getTile(linha, coluna).desenha(g2d);
                }
                else if (
                    tileAnimadoList != null && tileAnimadoList.size() > 0 &&
                    getTileAnimado(linha, coluna) != null
                ) {        
                    getTileAnimado(linha, coluna).desenha(g2d);                    
                }
            }
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Object">
    /**
     * Desenha a CamadaTiles com Strings para finalidades de debug.
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
                if (getTile(linha, coluna) != null) {
                    coord1 = getTile(linha, coluna)
                            .getCaixaColisao(0)
                            .getLadoCoordenadas(LadoRetangulo.ESQUERDA_CIMA)
                            .toString();

                    coord2 = getTile(linha, coluna)
                            .getCaixaColisao(0)
                            .getLadoCoordenadas(LadoRetangulo.DIREITA_CIMA)
                            .toString();
                }
                else{
                    coord1 = coord2 = "";
                }
                
                
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
            "CamadaTiles : " + getRotulo() + "{\n" +
            "  - dimesões da matriz: linhas(" + sizeRows() + "), colunas(" + sizeColumns() + 
                ") - " + getComprimento() + "x" + getAltura() + "px\n" +
            "  - dimesões dos tiles: " + getComprimentoCadaTile() + "x" + getAlturaCadaTile() + "px\n" +
            "  - quantidade de tiles na matriz: " + sizeElements() + "\n" +
            desenhoFinal + "\n" +
            "}\n";
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="InnerClass: TileAnimado e TileAnimadoList">
    /**
     * InnerClass que resolve o problema de um tile animado.
     */
    private class TileAnimado{
        int linha;
        int coluna;
        AnimacaoTile tileAnimado;
        
        //<editor-fold defaultstate="collapsed" desc="Construtor">
        /**
         * Construtor básico de classe.
         * @param linha A linha do tile
         * @param coluna A coluna do tile
         * @param tileAnimado O tileAnimado
         */
        public TileAnimado(int linha, int coluna, AnimacaoTile tileAnimado) {
            this.linha = linha;
            this.coluna = coluna;
            this.tileAnimado = tileAnimado;
        }
        //</editor-fold>
        
    }
    
    /**
     * InnerClass que resolve o problema de um ListManuseavel de TileAnimado.
     */
    private class TileAnimadoList implements ListManuseavel<TileAnimado>{
        
        private ArrayList<TileAnimado> tileList;
    
        //Auxiliares
        private int indiceAtual;
        
        //<editor-fold defaultstate="collapsed" desc="Construtor">
        /**
         * Construtor básico de classe.
         */
        public TileAnimadoList() {
            tileList = new ArrayList<>();
        }
        //</editor-fold>
                
        //<editor-fold defaultstate="collapsed" desc="Sobrescrita: ListManuseavel">
        @Override
        public void add(TileAnimado novoElemento) {
            tileList.add(novoElemento);
        }

        @Override
        public void add(TileAnimado... variosElementos) {
            tileList.addAll(Arrays.asList(variosElementos));
        }

        @Override
        public TileAnimado get(int indice) {
            return tileList.get(indice);
        }

        @Override
        public TileAnimado getNext() {
            TileAnimado temp = tileList.get(indiceAtual);
            indiceAtual++;

            if (indiceAtual >= size()) {
                indiceAtual = 0;
                return null;
            }

            return temp;
        }

        @Override
        public void remove(int indice) {
            tileList.remove(indice);
        }

        @Override
        public void remove(TileAnimado elementoParaRemover) {
            tileList.remove(elementoParaRemover);
        }

        @Override
        public int size() {
            return tileList.size();
        }
        //</editor-fold>
        
    }
    //</editor-fold>
    
}
