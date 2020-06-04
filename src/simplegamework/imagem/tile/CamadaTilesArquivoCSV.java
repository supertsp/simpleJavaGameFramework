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

import java.io.*;

/**
 * Classe que importa um arquivo CSV que representa uma CamadaTiles e cria uma matriz
 * com o código de cada tile que está no arquivo CSV.<br>
 * Obs.: O separador padrão do arquivo CSV deve ser a vírgula ou ponto-e-vírgula.
 * <br><br><small>Created on : 01/05/2015, 22:18:55</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class CamadaTilesArquivoCSV {
    
    private String URL;
    private int[][] matrizCodigos;
    private boolean criouMatrizCodigos;
    private int qtdLinhas;
    private int qtdColunas;
    
    //Auxiliares
    private int linhaAtual;
    private int colunaAtual;
    
    //<editor-fold defaultstate="collapsed" desc="Construtor">
    /**
     * Construtor básico que cria a matriz de códigos de acordo com o arquivo
     * CSV importado
     * @param URL Endereço do arquivo CSV, a partir do Pacote Default Java.
     * @param qtdColunas A quantidade de colunas no CSV ou o comprimento do 
     * tile map (medido em tiles)
     * @param qtdLinhas A quantidade de linhas no CSV ou a altura do tile map 
     * (medido em tiles)
     */
    public CamadaTilesArquivoCSV(String URL, int qtdColunas, int qtdLinhas) {
        this.URL = URL;
        this.qtdLinhas = qtdLinhas;
        this.qtdColunas = qtdColunas;
        
        criarMatrizCodigos();
    }
    //</editor-fold>
    
    /**
     * Cria a matriz de códigos com base no arquivo CSV.
     */
    private void criarMatrizCodigos() {
        matrizCodigos = new int[qtdLinhas][qtdColunas];
        String linhaLida = "";
        try {
            InputStream arquivoIn = getClass().getResourceAsStream(URL);
            BufferedReader buff = new BufferedReader(new InputStreamReader(arquivoIn));

            for(int linha = 0; linha < qtdLinhas; linha++) {
                linhaLida = buff.readLine();
                linhaLida = linhaLida.trim();
                String[] arrayLinhaAtual;
                
                if (linhaLida.contains(";")) {
                    arrayLinhaAtual = linhaLida.split(";");
                }
                else{
                    arrayLinhaAtual = linhaLida.split(",");
                }
                
                for (int coluna = 0; coluna < qtdColunas; coluna++) {                   
                    matrizCodigos[linha][coluna] = 
                            Integer.parseInt(arrayLinhaAtual[coluna].trim());
                }
            }
            
            criouMatrizCodigos = true;
        } catch (Exception e) {
            System.out.println("ERRO AO TENTAR LER O ARQUIVO CSV!");
            e.printStackTrace();
        }
    }
    
    /**
     * Retorna o códgio do primeiro elemento da matriz (linha = 0, coluna = 0) e
     * depois avança para o próximo elemento e assim sucessivamente.
     * @return int Se a matriz de códigos foi criada com sucesso o retorno será
     * o código do tile, caso contrário retornará -1
     */
    public int getCodigoTile() {
        if (criouMatrizCodigos) {           
            int codigo = matrizCodigos[linhaAtual][colunaAtual];
            colunaAtual++;

            if (colunaAtual == qtdColunas) {
                colunaAtual = 0;
                linhaAtual++;            
            }

            return codigo;
        }
       return -1;
    }
    
    /**
     * Retorna o códgio do tile de acordo com os valores de linha e coluna passados
     * por parâmetro.
     * @param linha A posição da linha do elemento procurado
     * @param coluna A posição da coluna do elemento procurado
     * @return int Se a matriz de códigos foi criada com sucesso o retorno será
     * o código do tile, caso contrário retornará -1
     */
    public int getCodigoTile(int linha, int coluna) {
        if (criouMatrizCodigos) {
            return matrizCodigos[linha][coluna];
        }
        
        return -1;
    }
    
    /**
     * Retorna a URL do arquivo importado.
     * @return String
     */
    public String getURL() {
        return URL;
    }
    
    /**
     * Retorna a quantidade de elementos no TileSheet, ou seja, linhas X colunas.
     * @return int
     */
    public int sizeElements() {
        return qtdLinhas * qtdColunas;
    }
    
    /**
     * Retorna a quantidade de linhas no TileSheet.
     * @return int
     */
    public int sizeRows() {
        return qtdLinhas;
    }

    /**
     * Retorna a quantidade de colunas no TileSheet.
     * @return int
     */
    public int sizeColumns() {
        return qtdColunas;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Object">
    /**
     * Transforma todo conteúdo e status do objeto de tipo CamadaTilesArquivoCSV
     * numa String mais amigável de se entender o objeto como um todo.
     * @return String
     */
    @Override
    public String toString() {
        String esquerdaCima1  = "+---";
        String esquerdaCima2  = "|   ";
        String direitaCima1   = "---+";
        String direitaCima2   = "   |";
        String esquerdaBaixo1 = "|   ";
        String esquerdaBaixo2 = "+---";
        String direitaBaixo1  = "   |";
        String direitaBaixo2  = "---+";
        
        String codigos = "";
        int maiorLinha = 0;
        
        if (criouMatrizCodigos) {        
            for (int linha = 0; linha < qtdLinhas; linha++) {
                codigos += "     ";
                String linhaTemp = "";

                for (int coluna = 0; coluna < qtdColunas; coluna++) {
                    if ((coluna + 1) == qtdColunas) {
                        linhaTemp += matrizCodigos[linha][coluna];
                    }
                    else{
                        linhaTemp += matrizCodigos[linha][coluna] + ", ";
                    }
                }

                codigos += linhaTemp;

                if (linhaTemp.length() > maiorLinha) {
                    maiorLinha = linhaTemp.length();
                }

                codigos += "\n";
            }
        }
        else{
            codigos = "     Não foi possível ler o arquivo CSV\n";
            maiorLinha = codigos.length();
        }
        
        maiorLinha -= 4;
        
        String espacoMaiorLinha = "";
        for (int cont = 0; cont < maiorLinha; cont++) {
            espacoMaiorLinha += " ";
        }
        
        return "TileMapArquivoCSV{\n" +
                "   qtdLinhas: " + qtdLinhas + "  -  qtdColunas: " + qtdColunas + "\n" +
                "   " + esquerdaCima1 + espacoMaiorLinha + direitaCima1 + "\n" + 
                "   " + esquerdaCima2 + espacoMaiorLinha + direitaCima2 + "\n" +
                codigos +
                "   " + esquerdaBaixo1 + espacoMaiorLinha + direitaBaixo1 + "\n" + 
                "   " + esquerdaBaixo2 + espacoMaiorLinha + direitaBaixo2 + "\n" + 
                "   qtdCodigos: " + sizeElements() + "\n}"
        ;
    }
    //</editor-fold>

}
