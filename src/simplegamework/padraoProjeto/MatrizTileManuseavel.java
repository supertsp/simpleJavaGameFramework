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

package simplegamework.padraoProjeto;

import simplegamework.imagem.ImagemBitmap;
import simplegamework.objetoBasico.CaixaColisao;

/**
 * Todo objeto que manuseia tiles deve implementar essa interface.
 * <br><br><small>Created on : 23/06/2015, 07:51:06</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public interface MatrizTileManuseavel {
    
    /**
     * Retorna o código do tile procurado na matriz de tiles.
     * @param linha A linha do tile
     * @param coluna A coluna do tile
     * @return int
     */
    public int getTileCodigo(int linha, int coluna);
    
    /**
     * Retorna o tile procurado na matriz de tiles.
     * @param linha A linha do tile
     * @param coluna A coluna do tile
     * @return ImagemBitmap
     */
    public ImagemBitmap getTile(int linha, int coluna);
    
    /**
     * Retorna o tile procurado na matriz de tiles.
     * @param codigo O código do tile
     * @return ImagemBitmap
     */
    public ImagemBitmap getTile(int codigo);
    
    /**
     * Retorna o próximo tile disponível, começando da primeira linha e coluna
     * da matriz de tiles e vai até a última linha e coluna. Quando acabam os tiles
     * o retorno será null
     * @return ImagemBitmap
     */
    public ImagemBitmap getNextTile();
        
    /**
     * Retorna a CaixaColisao que envolve todo o tile procurado na matriz de tiles.
     * @param linha A linha do tile
     * @param coluna A coluna do tile
     * @return CaixaColisao
     */
    public CaixaColisao getTileCaixaColisaoImagemToda(int linha, int coluna);
    
    /**
     * Retorna a CaixaColisao que envolve todo o tile procurado na matriz de tiles.
     * @param codigo O código do tile
     * @return CaixaColisao
     */
    public CaixaColisao getTileCaixaColisaoImagemToda(int codigo);
        
    /**
     * Retorna a quantidade de tiles na matriz de tiles.
     * @return int
     */
    public int sizeElements();
    
    /**
     * Retorna a quantidade de linhas na matriz de tiles.
     * @return int
     */
    public int sizeRows();

    /**
     * Retorna a quantidade de colunas na matriz de tiles.
     * @return int
     */
    public int sizeColumns();
    
    /**
     * Retorna o comprimento de cada tile na matriz de tiles.
     * @return int
     */
    public int getComprimentoCadaTile();
    
    /**
     * Retorna o comprimento de cada tile na matriz de tiles.
     * @return int
     */
    public int getAlturaCadaTile();
    
    /**
     * Retorna o comprimento da matriz de tiles (comprimentoCadaTile X sizeColumns).
     * @return int
     */
    public int getComprimento();
    
    /**
     * Retorna a altura da matriz de tiles (comprimentoCadaTile X sizeColumns).
     * @return int
     */
    public int getAltura();
    
}
