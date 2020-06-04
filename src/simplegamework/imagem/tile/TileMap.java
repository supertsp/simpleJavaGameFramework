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

import simplegamework.objetoBasico.Coordenadas;
import simplegamework.padraoProjeto.Dimensionavel;
import simplegamework.padraoProjeto.ImagemTransformavel;
import simplegamework.padraoProjeto.Desenhavel;
import simplegamework.animacao.AnimacaoTile;
import simplegamework.imagem.*;
import java.awt.Graphics2D;

/**
 * Classe que gerencia todas as CamadasTileMap e as ordena para exibição, criando
 * assim um melhor noção de profundidade no jogo. Um TileMap também pode definir
 * uma fase ou parte de fase de um jogo qualquer.
 * <br><br><small>Created on : 26/05/2015, 23:59:30</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class TileMap implements Dimensionavel, ImagemTransformavel, Desenhavel{
    
    private Object rotulo;
    private CamadaTilesList camadas;
    
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    /**
     * Construtor dinâmico que permite a inserção de quantas camadas forem necessárias
     * @param variasCamadas Quantos parâmetros forem necessários, podem ser passados
     * para adicionar cada camada
     */
    public TileMap(CamadaTiles... variasCamadas){
        camadas = new CamadaTilesList(variasCamadas);
    }
    
    /**
     * Construtor que permite a inserção do identificador do TileMap juntamente
     * com inúmeras camadas simultâneamente.
     * @param rotulo O rótulo do TileMap
     * @param variasCamadas Quantos parâmetros forem necessários, podem ser passados
     * para adicionar cada camada
     */
    public TileMap(Object rotulo, CamadaTiles... variasCamadas){
        this(variasCamadas);
        this.rotulo = rotulo;
    }
    //</editor-fold>
    
    /**
     * Retorna o rótulo do TileMap.
     * @return Object
     */
    public Object getRotulo(){
        return rotulo;
    }
    
    /**
     * Retorna um tile de uma CamadaTiles.
     * @param indiceCamada A camada procurada
     * @param linha A linha onde está o tile
     * @param coluna A coluna onde está o tile
     * @return ImagemBitmap
     */
    public ImagemBitmap getTile(int indiceCamada, int linha, int coluna){
        return camadas.get(indiceCamada).getTile(linha, coluna);
    }
    
    /**
     * Retorna um tile animado de uma CamadaTiles.
     * @param indiceCamada A camada procurada
     * @param linha A linha onde está o tile
     * @param coluna A coluna onde está o tile
     * @return AnimacaoTile
     */
    public AnimacaoTile getTileAnimado(int indiceCamada, int linha, int coluna){
        return camadas.get(indiceCamada).getTileAnimado(linha, coluna);
    }
    
    /**
     * Retorna o comprimento de cada tile.
     * @return int
     */
    public int getComprimentoCadaTile(){
        return getCamada(0).getComprimentoCadaTile();
    }
    
    /**
     * Retorna a altura de cada tile.
     * @return int
     */
    public int getAlturaCadaTile(){
        return getCamada(0).getAlturaCadaTile();
    }
    
    /**
     * Retorna a quantidade de linhas no TileMap.
     * @return int
     */
    public int sizeRows(){
        return getCamada(0).sizeRows();
    }
    
    /**
     * Retorna a quantidade de colunas no TileMap.
     * @return int
     */
    public int sizeColumns(){
        return getCamada(0).sizeColumns();
    }
    
    /**
     * Corrige as coordenadas de todas as camadas do TileMap começando com o
     * primeiro tile da primeira camada em x=0, y=0 e assim sucessivamente.<br>
     * Geralmente esse método pode ser chamado após usar os movimentos em
     * CameraTileMap, pois essa classe altera as Coordenadas de CamadaTiles.
     */
    public void arrumarCoordenadas(){
        int x, y;
        
        for (int contCamada = 0; contCamada < sizeCamadas(); contCamada++) {
            x = y = 0;

            for (int linha = 0; linha < sizeRows(); linha++) {
                for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                    if (getTile(contCamada, linha, coluna) != null) {
                        getTile(contCamada, linha, coluna)
                                .setCoordenadas(x, y);
                    }
                    else if (
                        getCamada(contCamada).sizeTileAnimado() > 0 &&
                        getTileAnimado(contCamada, linha, coluna) != null
                    ) {        
                        getTileAnimado(contCamada, linha, coluna)
                                .setCoordenadas(x, y);
                    }

                    x += getComprimentoCadaTile();                
                }

                x = 0;
                y += getAlturaCadaTile();
            }
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: Controle CamadaTilesList">
    /**
     * Adiciona uma nova CamadaTiles.
     * @param novaCamada A nova CamadaTiles a ser adicionado
     */
    public void addCamada(CamadaTiles novaCamada){
        camadas.add(novaCamada);
    }
    
    /**
     * Adiciona várias camadas ao mesmo tempo.<br>
     * Ex.: classe.addCamada(camada1, camada2, ..., camadaN);
     * @param variasCamadas As novss camadas
     */
    public void addCamada(CamadaTiles... variasCamadas){
        camadas.add(variasCamadas);
    }
    
    /**
     * Retorna uma camada de acordo com o índice passado por parâmetro.
     * @param indice O camada procurada
     * @return Caso tenha encontrado a camada, ela será retornada
     */
    public CamadaTiles getCamada(int indice){
        return camadas.get(indice);
    }
    
    /**
     * Retorna a camada procurada de acordo com o rótulo passado por parâmetro.
     * @param rotulo O rótulo procurado
     * @return CamadaTiles
     */
    public CamadaTiles getCamada(Object rotulo){
        return camadas.get(rotulo);
    }
    
    /**
     * Retorna sempre a próxima camada seguindo a ordem dos índices.
     * @return O próxima camada, no entanto, caso tenha acabado as camadas
     * o valor retornado será null
     */
    public CamadaTiles getNextCamada(){
        return camadas.getNext();
    }
    
    /**
     * Remove uma camada de acordo com índice passado por parâmetro.
     * @param indice A camada procurada
     */
    public void removeCamada(int indice){
        camadas.remove(indice);
    }
    
    /**
     * Remove uma camada de acordo com o camada passado por parâmetro
     * @param camadaParaRemover A camada procurada
     */
    public void removeCamada(CamadaTiles camadaParaRemover){
        camadas.remove(camadaParaRemover);
    }
    
    /**
     * Remove uma camada de acordo com o rótulo da camada procurada
     * @param rotulo O rótulo de camada procurado
     */
    public void removeCamada(Object rotulo){
        for (int indice = 0; indice < sizeCamadas(); indice++) {
            if (getCamada(indice).getRotulo().equals(rotulo)) {
                removeCamada(indice);
                break;
            }
        }
    }
    
    /**
     * Retorna a quantidade de camadas.
     * @return int
     */
    public int sizeCamadas(){
        return camadas.size();
    }
    //</editor-fold>    
       
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Dimensionavel">
    @Override
    public void redimensionar(double escala) {
        for (int indice = 0; indice < sizeCamadas(); indice++) {
            getCamada(indice).redimensionar(escala);
        }
    }

    @Override
    public int getComprimento() {
        return getCamada(0).getComprimento();
    }

    @Override
    public int getAltura() {
        return getCamada(0).getAltura();
    }
    //</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: ImagemTransformavel">
    @Override
    public void espelhar(TipoEspelhamento tipoEspelhamento) {
        for (int indice = 0; indice < sizeCamadas(); indice++) {
            getCamada(indice).espelhar(tipoEspelhamento);
        }
    }

    @Override
    public void rotacionar(double graus) {
        for (int indice = 0; indice < sizeCamadas(); indice++) {
            getCamada(indice).rotacionar(graus);
        }
    }

    @Override
    public void rotacionar(double graus, Coordenadas pivoRotacao) {
        for (int indice = 0; indice < sizeCamadas(); indice++) {
            getCamada(indice).rotacionar(graus, pivoRotacao);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
    @Override
    public void desenha(Graphics2D g2d) {
        for (int indice = 0; indice < sizeCamadas(); indice++) {
            getCamada(indice).desenha(g2d);
        }
    }
    //</editor-fold>
    
}
