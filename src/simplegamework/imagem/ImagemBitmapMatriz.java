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

package simplegamework.imagem;

import simplegamework.padraoProjeto.Atualizavel;
import simplegamework.padraoProjeto.Dimensionavel;
import simplegamework.padraoProjeto.ImagemTransformavel;
import simplegamework.padraoProjeto.Movimentavel;
import simplegamework.padraoProjeto.AtualizavelListManuseavel;
import simplegamework.padraoProjeto.Colidivel;
import simplegamework.padraoProjeto.Desenhavel;
import simplegamework.padraoProjeto.MatrizListManuseavel;
import simplegamework.padraoProjeto.Coordenavel;
import simplegamework.ferramenta.Alternador;
import simplegamework.ferramenta.AtualizavelList;
import simplegamework.ferramenta.AuxiliaresMovimentavel;
import simplegamework.objetoBasico.CaixaColisao;
import simplegamework.objetoBasico.Coordenadas;
import simplegamework.objetoBasico.LadoRetangulo;
import simplegamework.objetoBasico.TipoColisao;
import simplegamework.objetoBasico.TipoMovimento;
import java.awt.Graphics2D;

/**
 * Classe base de um array bidimensional, comumente usado para Tilesheets.<br>
 * Obs.: No caso do espelhamento do array as imagens terão suas posições trocadas.
 * <br><br><small>Created on : 01/05/2015, 22:18:55</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class ImagemBitmapMatriz implements MatrizListManuseavel<ImagemBitmap>, 
        Coordenavel, Movimentavel, Dimensionavel, ImagemTransformavel, Colidivel,
        Desenhavel, Atualizavel, AtualizavelListManuseavel{

    private ImagemBitmap[][] matriz;
    private int qtdLinhas;
    private int qtdColunas;
    
    //Auxiliares
    private int linhaAtual;
    private int colunaAtual;
    private Alternador<Executando> executando;
    private AuxiliaresMovimentavel auxMov;
    private AtualizavelList atualizaveis;
    
    private enum Executando{
        ADD_NEXT, GET_NEXT, REMOVE_NEXT;
    }
        
    //<editor-fold defaultstate="collapsed" desc="Construtor">
    /**
     * Construtor para definir o tamanho da matriz.
     * @param qtdLinhas A quantidade de linhas na matriz.
     * @param qtdColunas A quantidade de colunas na matriz.
     */
    public ImagemBitmapMatriz(int qtdLinhas, int qtdColunas) {
        this.qtdLinhas = qtdLinhas > 0 ? qtdLinhas : 1;
        this.qtdColunas = qtdColunas > 0 ? qtdColunas : 1;
        matriz = new ImagemBitmap[qtdLinhas][qtdColunas];
        executando = new Alternador<>(
                Executando.ADD_NEXT, 
                Executando.GET_NEXT, 
                Executando.REMOVE_NEXT
        );
        
        //Auxiliares        
        auxMov = new AuxiliaresMovimentavel();
        atualizaveis = new AtualizavelList();
    }
    //</editor-fold>
    
    /**
     * Adiciona um novo elemento à matriz, no entanto, esse novo 
     * elemento já existia dentro dela, sendo assim, se as Coordenadas do 
     * primeiro fossem alteradas, fatalmente do novo elemento também seriam; 
     * ou seja, esse método copia os atributos básicos do elemento passados por 
     * parâmetro e cria uma nova instância com os atributos copiados, assim 
     * ambos tornam-se independentes uns dos outros.
     * @param linha A linha onde será adicionada a imagem
     * @param coluna A coluna onde será adicionada a imagem
     * @param imagemBitmapParaCopiar A nova imagem a ser copiada e adicionada
     */
    public void addCopy(int linha, int coluna, ImagemBitmap imagemBitmapParaCopiar) {
        ImagemBitmap imagemBitmap = imagemBitmapParaCopiar.getCopy();        
        add(linha, coluna, imagemBitmap);
    }
    
    /**
     * Ativa a exibição da cor da borda de todas CaixaColisao dentro da matriz.
     */
    public void ativarCorBordaCaixaColisaoListTodasImagens() {
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                get(linha, coluna).ativarCorBordaCaixaColisaoList();
            }
        }
    }

    /**
     * Desativa a exibição da cor da borda de todas CaixaColisao dentro da matriz.
     */
    public void desativarCorBordaCaixaColisaoListTodasImagens() {
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                get(linha, coluna).desativarCorBordaCaixaColisaoList();
            }
        }
    }
    
    /**
     * Altera a rolagemInfinita de todas imagens na Matriz, ou seja, marca se todas
     * imagens devem ficar rolando infinitamente dentro de seus limites 
     * semelhantemente a uma esteira fabril. Os movimentos da rolagem são 
     * baseados em getVelX() e getVelY().<br>
     * Obs.: Este efeito não é compatível com o espelhamento de imagem.
     * @param tipoRolagem O novo tipo de rolagem infinita
     */
    public void setRolagemInfinita(TipoRolagemInfinita tipoRolagem){
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                get(linha, coluna).setRolagemInfinita(tipoRolagem);
            }
        }
    }
    
    /**
     * Retorna o valor da rolagemInfinita apenas da primeira imagem da Matriz.
     * @return TipoRolagemInfinita
     */
    public TipoRolagemInfinita getRolagemInfinita(){
        return get(0, 0).getRolagemInfinita();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: MatrizListManuseavel">
    @Override
    public void add(int linha, int coluna, ImagemBitmap novoElemento) {
        matriz[linha][coluna] = novoElemento;
    }

    @Override
    public void add(int linhaInicial, int colunaInicial, ImagemBitmap... variosElementos) {
        int totalElementos = variosElementos.length;
        int indice = 0;
        boolean fim = false;
        
        for (int linha = linhaInicial; linha < sizeRows(); linha++) {
            if (fim) {
                break;
            }
            
            for (int coluna = colunaInicial; coluna < sizeColumns(); coluna++) {
                add(linha, coluna, variosElementos[indice]);
                indice++;
                
                if (indice == totalElementos) {
                    fim = true;
                    break;
                }
                
            }
        }
    }

    @Override
    public boolean addNext(ImagemBitmap novoElemento) {
        boolean removeu = false;
        
        if (executando.getEstadoAtual() == Executando.ADD_NEXT) {
            if (linhaAtual == sizeRows()) {
                linhaAtual = 0;
            }
            else{
                add(linhaAtual, colunaAtual, novoElemento);
                removeu = true;
                colunaAtual++;

                if (colunaAtual == sizeColumns()) {
                    colunaAtual = 0;
                    linhaAtual++;
                }
            }
        }
        else{
            executando.mudarEstadoPara(Executando.ADD_NEXT);
            linhaAtual = 0;
            colunaAtual = 0;
            
            add(linhaAtual, colunaAtual, novoElemento);
            removeu = true;
            colunaAtual++;
            
            if (colunaAtual == sizeColumns()) {
                colunaAtual = 0;
                linhaAtual++;
            }
        }
        
        return removeu;
    }

    @Override
    public ImagemBitmap get(int linha, int coluna) {
        return matriz[linha][coluna];
    }

    @Override
    public ImagemBitmap getNext() {
        ImagemBitmap temp = null;
        
        if (executando.getEstadoAtual() == Executando.GET_NEXT) {
            if (linhaAtual == sizeRows()) {
                linhaAtual = 0;
            }
            else{
                temp = get(linhaAtual, colunaAtual);
                colunaAtual++;

                if (colunaAtual == sizeColumns()) {
                    colunaAtual = 0;
                    linhaAtual++;
                }
            }
        }
        else{
            executando.mudarEstadoPara(Executando.GET_NEXT);
            linhaAtual = 0;
            colunaAtual = 0;
            
            temp = get(linhaAtual, colunaAtual);
            colunaAtual++;
            
            if (colunaAtual == sizeColumns()) {
                colunaAtual = 0;
                linhaAtual++;
            }
        }
        
        return temp;
    }

    @Override
    public void remove(int linha, int coluna) {
        matriz[linha][coluna] = null;
    }

    @Override
    public void remove(ImagemBitmap elementoParaRemover) {
        boolean achou = false;
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (matriz[linha][coluna].equals(elementoParaRemover)) {
                    matriz[linha][coluna] = null;
                    achou = true;
                    break;
                }
            }
            
            if (achou) {
                break;
            }
        }
    }

    @Override
    public boolean removeNext() {
        boolean removeu = false;
        
        if (executando.getEstadoAtual() == Executando.ADD_NEXT) {
            if (linhaAtual == sizeRows()) {
                linhaAtual = 0;
            }
            else{
                remove(linhaAtual, colunaAtual);
                removeu = true;
                colunaAtual++;

                if (colunaAtual == sizeColumns()) {
                    colunaAtual = 0;
                    linhaAtual++;
                }
            }
        }
        else{
            executando.mudarEstadoPara(Executando.ADD_NEXT);
            linhaAtual = 0;
            colunaAtual = 0;
            
            remove(linhaAtual, colunaAtual);
            removeu = true;
            colunaAtual++;
            
            if (colunaAtual == sizeColumns()) {
                colunaAtual = 0;
                linhaAtual++;
            }
        }
        
        return removeu;
    }

    @Override
    public int sizeElements() {
        return qtdLinhas * qtdColunas;
    }

    @Override
    public int sizeRows() {
        return qtdLinhas;
    }

    @Override
    public int sizeColumns() {
        return qtdColunas;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Coordenavel">
    @Override
    public void setX(double x) {
        double xx = x, yy = get(0, 0).getY();
        
        if (sizeRows() >= 1 && sizeColumns() > 1) {
            for (int linha = 0; linha < sizeRows(); linha++) {
                for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                    if (get(linha, coluna) != null) {
                        get(linha, coluna).setCoordenadas(xx, yy);
                        xx += get(linha, coluna).getComprimento();
                    }
                }
                
                xx = x;
                yy += get(linha, 0).getAltura();
            }
        }
    }

    @Override
    public void setY(double y) {
        double xx = get(0, 0).getX(), yy = y;
        
        if (sizeRows() >= 1 && sizeColumns() > 1) {
            for (int linha = 0; linha < sizeRows(); linha++) {
                for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                    if (get(linha, coluna) != null) {
                        get(linha, coluna).setCoordenadas(xx, yy);
                        xx += get(linha, coluna).getComprimento();
                    }
                }
                
                xx = get(0, 0).getX();
                yy += get(linha, 0).getAltura();
            }
        }
    }

    @Override
    public void incrementaX(double aumentoX) {
        double xx = get(0, 0).getX() + aumentoX, yy = get(0, 0).getY();
        
        if (sizeRows() >= 1 && sizeColumns() > 1) {
            for (int linha = 0; linha < sizeRows(); linha++) {
                for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                    if (get(linha, coluna) != null) {
                        get(linha, coluna).setCoordenadas(xx, yy);
                        xx += get(linha, coluna).getComprimento();
                    }
                }
                
                xx = get(0, 0).getX() + aumentoX;
                yy += get(linha, 0).getAltura();
            }
        }
    }

    @Override
    public void incrementaY(double aumentoY) {
        double xx = get(0, 0).getX(), yy = get(0, 0).getY() + aumentoY;
        
        if (sizeRows() >= 1 && sizeColumns() > 1) {
            for (int linha = 0; linha < sizeRows(); linha++) {
                for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                    if (get(linha, coluna) != null) {
                        get(linha, coluna).setCoordenadas(xx, yy);
                        xx += get(linha, coluna).getComprimento();
                    }
                }
                
                xx = get(0, 0).getX();
                yy += get(linha, 0).getAltura();
            }
        }
    }

    @Override
    public void setCoordenadas(double x, double y) {
        double xx = x, yy = y;
        
        if (sizeRows() >= 1 && sizeColumns() > 1) {
            for (int linha = 0; linha < sizeRows(); linha++) {
                for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                    if (get(linha, coluna) != null) {
                        get(linha, coluna).setCoordenadas(xx, yy);
                        xx += get(linha, coluna).getComprimento();
                    }
                }
                
                xx = x;
                yy += get(linha, 0).getAltura();
            }
        }
    }
    
    @Override
    public void setCoordenadas(Coordenadas novasCoordenadas) {
        double xx = novasCoordenadas.getX(), yy = novasCoordenadas.getY();
        
        if (sizeRows() >= 1 && sizeColumns() > 1) {
            for (int linha = 0; linha < sizeRows(); linha++) {
                for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                    if (get(linha, coluna) != null) {
                        get(linha, coluna).setCoordenadas(novasCoordenadas);
                        xx += get(linha, coluna).getComprimento();
                    }
                }
                
                xx = novasCoordenadas.getX();
                yy += get(linha, 0).getAltura();
            }
        }
    }
    

    @Override
    public void incrementarCoordenadas(double aumentoX, double aumentoY) {
        double xx = get(0, 0).getX() + aumentoX, yy = get(0, 0).getY() + aumentoY;
        
        if (sizeRows() >= 1 && sizeColumns() > 1) {
            for (int linha = 0; linha < sizeRows(); linha++) {
                for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                    if (get(linha, coluna) != null) {
                        get(linha, coluna).setCoordenadas(xx, yy);
                        xx += get(linha, coluna).getComprimento();
                    }
                }
                
                xx = get(0, 0).getX() + aumentoX;
                yy += get(linha, 0).getAltura();
            }
        }
    }

    @Override
    public double getX() {
        return get(0, 0).getX();
    }

    @Override
    public int getIntX() {
        return get(0, 0).getIntX();
    }

    @Override
    public double getY() {
        return get(0, 0).getY();
    }

    @Override
    public int getIntY() {
        return get(0, 0).getIntY();
    }
    
    @Override
    public Coordenadas getCoordenadas() {
        return get(0, 0).getCoordenadas();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Movimentavel">
    @Override
    public void setVelX(double velX) {
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (get(linha, coluna) != null) {
                    get(linha, coluna).setVelX(velX);
                }
            }
        }
    }

    @Override
    public void setVelY(double velY) {
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (get(linha, coluna) != null) {
                    get(linha, coluna).setVelY(velY);
                }
            }
        }
    }

    @Override
    public void incrementaVelX(double aumentoVelX) {
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (get(linha, coluna) != null) {
                    get(linha, coluna).incrementaVelX(aumentoVelX);
                }
            }
        }
    }

    @Override
    public void incrementaVelY(double aumentoVelY) {
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (get(linha, coluna) != null) {
                    get(linha, coluna).incrementaVelY(aumentoVelY);
                }
            }
        }
    }

    @Override
    public void setVelocidades(double velX, double velY) {
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (get(linha, coluna) != null) {
                    get(linha, coluna).setVelocidades(velX, velY);
                }
            }
        }
    }

    @Override
    public void incrementarVelocidades(double aumentoVelX, double aumentoVelY) {
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (get(linha, coluna) != null) {
                    get(linha, coluna).incrementarVelocidades(aumentoVelX, aumentoVelY);
                }
            }
        }
    }

    @Override
    public double getVelX() {
        return get(0, 0).getVelX();
    }

    @Override
    public int getIntVelX() {
        return get(0, 0).getIntVelX();
    }

    @Override
    public double getVelY() {
        return get(0, 0).getVelY();
    }

    @Override
    public int getIntVelY() {
        return get(0, 0).getIntVelY();
    }

    @Override
    public void movimentar(TipoMovimento novoMovimento) {
        auxMov.setMovimentoPorTipo(novoMovimento);
        
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (get(linha, coluna) != null) {
                    get(linha, coluna).movimentar(novoMovimento);
                }
            }
        }
    }

    @Override
    public void movimentar(double direcaoEmGraus) {
        auxMov.setMovimentoPorGrau(direcaoEmGraus);
        
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (get(linha, coluna) != null) {
                    get(linha, coluna).movimentar(direcaoEmGraus);
                }
            }
        }
    }

    @Override
    public void movimentarCurvaQuadratica(
            double alturaX, double alturaY, 
            double comprimentoX, double comprimentoY) {
        auxMov.setMovimentoPorCurvaQuadratica(alturaX, alturaY, comprimentoX, comprimentoY);
        
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (get(linha, coluna) != null) {
                    get(linha, coluna).movimentarCurvaQuadratica(
                            alturaX, alturaY, comprimentoX, comprimentoY);
                }
            }
        }
    }

    @Override
    public void movimentarRepetidamente(
            double indiceDeformardorX, double indiceDeformardorY, 
            double comprimento, double altura, boolean inverter) {
        auxMov.setMovimentoPorRepeticao(
                indiceDeformardorX, indiceDeformardorY, 
                comprimento, altura, inverter
        );
        
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (get(linha, coluna) != null) {
                    get(linha, coluna).movimentarRepetidamente(
                            indiceDeformardorX, indiceDeformardorY, 
                            comprimento, altura, inverter);
                }
            }
        }
    }

    @Override
    public void movimentarPorFuncao(String funcao, boolean iniciarEmCoordNegativa) {
        auxMov.setMovimentoPorFuncao(funcao, iniciarEmCoordNegativa);
        
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (get(linha, coluna) != null) {
                    get(linha, coluna).movimentarPorFuncao(funcao, iniciarEmCoordNegativa);
                }
            }
        }
    }

    @Override
    public void pararMovimento() {
        auxMov.setMovimentoNenhum();
        
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (get(linha, coluna) != null) {
                    get(linha, coluna).pararMovimento();
                }
            }
        }
    }

    @Override
    public double calcularFuncao(String funcao) {
        return get(0, 0).calcularFuncao(funcao);
    }

    @Override
    public double calcularFuncao(String funcao, double intervaloIncial, double intervaloFinal) {
        return get(0, 0).calcularFuncao(funcao, intervaloIncial, intervaloFinal);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Dimensionavel e Alterações">
    @Override
    public void redimensionar(double escala) {
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (get(linha, coluna) != null) {
                    get(linha, coluna).redimensionar(escala);
                }
            }
        }
        
        setCoordenadas(getX(), getY());
    }
    
    /**
     * Retorna o comprimento da ImagemBitmap de linha=0 e coluna=0.
     * @return int em pixels
     */
    @Override
    public int getComprimento() {
        return get(0, 0).getComprimento();
    }
    
    /**
     * Retorna o comprimento da matriz.
     * @return int em pixels
     */
    public int getComprimentoMatriz(){
        int comp = 0, pulos = 0;
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (get(linha, coluna) != null) {
                    comp += get(linha, coluna).getComprimento();
                }
                else{
                    pulos++;
                }
            }
            
            if (pulos > 0) {
                comp = 0;
                pulos = 0;
            }
            else{
                break;
            }
        }
        
        return comp;
    }
    
    /**
     * Retorna o comprimento da ImagemBitmap solicitada no parâmetro.   
     * @param linha A linha da imagem
     * @param coluna A coluna da imagem
     * @return int em pixels
     */
    public int getComprimento(int linha, int coluna) {
        return get(linha, coluna).getComprimento();
    }
    
    /**
     * Retorna a altura da ImagemBitmap de linha=0 e coluna=0.
     * @return int em pixels
     */
    @Override
    public int getAltura() {
        return get(0, 0).getAltura();
    }
    
    /**
     * Retorna a altura da matriz.
     * @return int em pixels
     */
    public int getAlturaMatriz(){
        int alt = 0, pulos = 0;
        for (int coluna = 0; coluna < sizeColumns(); coluna++) {
            for (int linha = 0; linha < sizeRows(); linha++) {
                if (get(linha, coluna) != null) {
                    alt += get(linha, coluna).getAltura();
                }
                else{
                    pulos++;
                }
            }
            
            if (pulos > 0) {
                alt = 0;
                pulos = 0;
            }
            else{
                break;
            }
        }
        
        return alt;
    }
    
    /**
     * Retorna a altura da ImagemBitmap solicitada no parâmetro.   
     * @param linha A linha da imagem
     * @param coluna A coluna da imagem
     * @return int em pixels
     */
    public int getAltura(int linha, int coluna) {
        return get(linha, coluna).getAltura();
    }
    //</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: ImagemTransformavel">
    @Override
    public void espelhar(TipoEspelhamento tipoEspelhamento) {       
        if (tipoEspelhamento != TipoEspelhamento.NENHUM || tipoEspelhamento != null) {
            ImagemBitmap[][] matrizTemp = new ImagemBitmap[qtdLinhas][qtdColunas];

            //copia a matriz
            for (int linha = 0; linha < sizeRows(); linha++) {
                for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                    if (get(linha, coluna) != null) {
                        get(linha, coluna).espelhar(tipoEspelhamento);
                        matrizTemp[linha][coluna] = get(linha, coluna);
                    }
                }
            }
            
            //inverte horizontalmente
            if (tipoEspelhamento == TipoEspelhamento.HORIZONTAL) {
                for (int linha = 0; linha < sizeRows(); linha++) {
                    for (int coluna = (sizeColumns() - 1); coluna >= 0; coluna--) {
                        if (get(linha, coluna) != null) {
                            addNext(matrizTemp[linha][coluna]);
                        }
                    }
                }
            }

            //inverte verticalmente
            if (tipoEspelhamento == TipoEspelhamento.VERTICAL) {
                for (int linha = (sizeRows() - 1); linha >= 0; linha--) {
                    for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                        if (get(linha, coluna) != null) {
                            addNext(matrizTemp[linha][coluna]);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void rotacionar(double graus) {
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (get(linha, coluna) != null) {
                    get(linha, coluna).rotacionar(graus);
                }
            }
        }
    }

    @Override
    public void rotacionar(double graus, Coordenadas pivoRotacao) {
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (get(linha, coluna) != null) {
                    get(linha, coluna).rotacionar(graus, pivoRotacao);
                }
            }
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Colidivel e Alterações">
    /**
     * Retorna a CaixaColisao da primeira imagem adicionada.
     * @return CaixaColisao
     */
    @Override
    public CaixaColisao getCaixaColisao(){
        return get(0, 0).getCaixaColisao();
    }
    
    /**
     * Retorna a CaixaColisao de acordo a linha e coluna passados por parâmetro.
     * @param linha A linha da imagem procurada
     * @param coluna A coluna da imagem procurada
     * @return CaixaColisao
     */
    public CaixaColisao getCaixaColisao(int linha, int coluna){
        return get(linha, coluna).getCaixaColisao();
    }
    
    /**
     * Usado para descobrir se a ImagemBitmap de linha=0 e coluna=0 colidiu com 
     * outra em Tela.
     * @param outroObjeto Outra CaixaColisao para ser avaliada a colisão
     * @param tipoColisao Enum que determina se a colisão a ser avaliada é uma
     * colisão INTERNA ou EXTERNA
     * @return true : Caso houve um colisão qualquer <br>
     * false : Caso não houve colisão
     */
    @Override
    public boolean colidiu(CaixaColisao outroObjeto, TipoColisao tipoColisao) {
        return get(0, 0).colidiu(outroObjeto, tipoColisao);
    }
    
    /**
     * Usado para descobrir se a ImagemBitmap da linha e coluna passados por
     * parâmetro colidiu com outra em Tela.
     * @param linha A linha procurada
     * @param coluna O coluna procurada
     * @param outroObjeto Outra CaixaColisao para ser avaliada a colisão
     * @param tipoColisao Enum que determina se a colisão a ser avaliada é uma
     * colisão INTERNA ou EXTERNA
     * @return true : Caso houve um colisão qualquer <br>
     * false : Caso não houve colisão
     */
    public boolean colidiu(int linha, int coluna, CaixaColisao outroObjeto, TipoColisao tipoColisao) {
        return get(linha, coluna).colidiu(outroObjeto, tipoColisao);
    }
    
    /**
     * Usado para descobrir se a ImagemBitmap de linha=0 e coluna=0 colidiu com 
     * outra em Tela e em qual lado que ocorreu a colisão.
     * @param outroObjeto Outra CaixaColisao para ser avaliada a colisão
     * @param tipoColisao Enum que determina se a colisão a ser avaliada é uma
     * colisão INTERNA ou EXTERNA
     * @return null : Caso não houve colisão <br>
     * ESQUERDA : Caso houve um colisão na esquerda <br>
     * DIREITA : Caso houve um colisão na direita <br>
     * CIMA : Caso houve um colisão na parte superior <br>
     * BAIXO : Caso houve um colisão na parte inferior <br>
     * ESQUERDA_CIMA : Caso houve um colisão na diagonal esquerda superiror <br>
     * DIREITA_CIMA : Caso houve um colisão na diagonal direita superior <br>
     * ESQUERDA_BAIXO : Caso houve um colisão na diagonal esquerda inferior <br>
     * DIREITA_BAIXO : Caso houve um colisão na diagonal direita inferior <br>
     */
    @Override
    public LadoRetangulo colidiuNoLado(CaixaColisao outroObjeto, TipoColisao tipoColisao) {
        return get(0, 0).colidiuNoLado(outroObjeto, tipoColisao);
    }
    
    /**
     * Usado para descobrir se a ImagemBitmap da linha e coluna passados por 
     * parâmetro colidiu com outra em Tela e em qual lado que ocorreu a colisão.
     * @param linha A linha procurada
     * @param coluna A coluna procurada
     * @param outroObjeto Outra CaixaColisao para ser avaliada a colisão
     * @param tipoColisao Enum que determina se a colisão a ser avaliada é uma
     * colisão INTERNA ou EXTERNA
     * @return null : Caso não houve colisão <br>
     * ESQUERDA : Caso houve um colisão na esquerda <br>
     * DIREITA : Caso houve um colisão na direita <br>
     * CIMA : Caso houve um colisão na parte superior <br>
     * BAIXO : Caso houve um colisão na parte inferior <br>
     * ESQUERDA_CIMA : Caso houve um colisão na diagonal esquerda superiror <br>
     * DIREITA_CIMA : Caso houve um colisão na diagonal direita superior <br>
     * ESQUERDA_BAIXO : Caso houve um colisão na diagonal esquerda inferior <br>
     * DIREITA_BAIXO : Caso houve um colisão na diagonal direita inferior <br>
     */
    public LadoRetangulo colidiuNoLado(int linha, int coluna, 
            CaixaColisao outroObjeto, TipoColisao tipoColisao) {
        return get(linha, coluna).colidiuNoLado(outroObjeto, tipoColisao);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
    @Override
    public void desenha(Graphics2D g2d) {        
        for (int linha = 0; linha < sizeRows(); linha++) {
            for (int coluna = 0; coluna < sizeColumns(); coluna++) {
                if (get(linha, coluna) != null) {
                    get(linha, coluna).desenha(g2d);
                }
            }
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Atualizavel">
    @Override
    public void atualiza() {
        //<editor-fold defaultstate="collapsed" desc="Movimentos">
        if (auxMov.getMetodoMovimentavelAtual() == AuxiliaresMovimentavel.MetodoMovimentoAtual.TIPO) {
            movimentar(auxMov.getMovimentoPorTipo());
        }
        
        if (auxMov.getMetodoMovimentavelAtual() == AuxiliaresMovimentavel.MetodoMovimentoAtual.GRAU) {
            movimentar(auxMov.getMovimentoPorGrau());
        }
        
        if (auxMov.getMetodoMovimentavelAtual() == AuxiliaresMovimentavel.MetodoMovimentoAtual.CURVA_QUADRATICA) {
            movimentarCurvaQuadratica(
                    auxMov.getMovimentoPorCurvaQuadratica().alturaX, 
                    auxMov.getMovimentoPorCurvaQuadratica().alturaY, 
                    auxMov.getMovimentoPorCurvaQuadratica().comprimentoX, 
                    auxMov.getMovimentoPorCurvaQuadratica().comprimentoY
            );
        }
        
        if (auxMov.getMetodoMovimentavelAtual() == AuxiliaresMovimentavel.MetodoMovimentoAtual.REPETIDAMENTE) {
            movimentarRepetidamente(
                    auxMov.getMovimentoPorRepeticao().indiceDeformardorX, 
                    auxMov.getMovimentoPorRepeticao().indiceDeformardorY, 
                    auxMov.getMovimentoPorRepeticao().alturaMovimento, 
                    auxMov.getMovimentoPorRepeticao().alturaMovimento, 
                    auxMov.getMovimentoPorRepeticao().inverter
            );
        }
        
        if (auxMov.getMetodoMovimentavelAtual() == AuxiliaresMovimentavel.MetodoMovimentoAtual.FUNCAO) {
            movimentarPorFuncao(
                    auxMov.getMovimentoPorFuncao().funcao, 
                    auxMov.getMovimentoPorFuncao().iniciarEmCoordNegativa
            );
        }
        
        if (auxMov.getMetodoMovimentavelAtual() == AuxiliaresMovimentavel.MetodoMovimentoAtual.NENHUM) {
            pararMovimento();
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
    
}
