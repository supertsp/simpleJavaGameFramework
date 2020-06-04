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

import simplegamework.objetoBasico.LadoRetangulo;
import simplegamework.imagem.*;

/**
 * Uma folha de sprites ou sprite sheet serve para importar uma ImagemBitmap e
 * fatiá-la em SpriteTile.
 * <br><br><small>Created on : 01/05/2015, 22:18:55</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class SpriteSheet{

    protected ImagemBitmap spriteSheet;
    
    //<editor-fold defaultstate="collapsed" desc="Construtor">
    /**
     * Construtor básico de um SpriteSheet.
     * @param urlSpriteSheet A URL do SpriteSheet
     */
    public SpriteSheet(String urlSpriteSheet) {
        spriteSheet = new ImagemBitmap(urlSpriteSheet, 0, 0);
    }
    //</editor-fold>
    
    public SpriteTile getFatia(
            int xInicial, int yInicial, 
            int comprimentoFinal, int alturaFinal,
            double xPivoAlinhamento, double yPivoAlinhamento, 
            boolean exibirPivoAlinhamento
    ) {
        return new SpriteTile(
                spriteSheet.getFatiaImagemBitmap(xInicial, yInicial, comprimentoFinal, alturaFinal), 
                xPivoAlinhamento, yPivoAlinhamento, exibirPivoAlinhamento);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Object">       
    /**
     * Desenha um retângulo (SpriteSheet) e seus quatro pares de coordenadas
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
        coord1 = spriteSheet.getCaixaColisaoImagemToda()
                .getLadoCoordenadas(LadoRetangulo.ESQUERDA_CIMA)
                .toString();

        coord2 = spriteSheet.getCaixaColisaoImagemToda()
                .getLadoCoordenadas(LadoRetangulo.DIREITA_CIMA)
                .toString();
        
        int restaEspacos = tamanhoLinhaCoord - (coord1.length() + coord2.length());

        for (int cont = 0; cont < restaEspacos; cont++) {
            espacos += " ";
        }
        
        desenhoFinal += "  |" + coord1 + espacos + coord2 + "|\n" + linhaMeioTemp;
        espacos = "";
        
        //COORDENADAS INFERIORES
        coord1 = spriteSheet.getCaixaColisaoImagemToda()
                .getLadoCoordenadas(LadoRetangulo.ESQUERDA_BAIXO)
                .toString();

        coord2 = spriteSheet.getCaixaColisaoImagemToda()
                .getLadoCoordenadas(LadoRetangulo.DIREITA_BAIXO)
                .toString();
        
        restaEspacos = tamanhoLinhaCoord - (coord1.length() + coord2.length());

        for (int cont = 0; cont < restaEspacos; cont++) {
            espacos += " ";
        }

       desenhoFinal += "  |" + coord1 + espacos + coord2 + "|\n  " + linhaTopoBase;
        
                        
        return
            "SpriteSheet {\n" +
            "  - dimesões: " + spriteSheet.getComprimento() + "x" + spriteSheet.getAltura() + "px\n" +
            desenhoFinal + "\n" +
            "}\n";
    }
    //</editor-fold>
    
}
