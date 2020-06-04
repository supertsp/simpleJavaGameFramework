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

import simplegamework.ferramenta.AtualizavelList;
import simplegamework.ferramenta.AuxiliaresMovimentavel;
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
import simplegamework.padraoProjeto.Coordenavel;
import simplegamework.padraoProjeto.ListManuseavel;
import simplegamework.ferramenta.AuxiliaresMovimentavel.MetodoMovimentoAtual;
import java.awt.*;
import java.util.*;

/**
 * Arraylist de ImagemBitmap.
 * <br><br><small>Created on : 01/05/2015, 22:18:55</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class ImagemBitmapList implements ListManuseavel<ImagemBitmap>, 
        Coordenavel, Movimentavel, Dimensionavel, ImagemTransformavel, 
        Colidivel, Desenhavel, Atualizavel, AtualizavelListManuseavel{

    private ArrayList<ImagemBitmap> imagemBitmapList;
    
    //Auxiliares
    private int indiceAtual;
    private AuxiliaresMovimentavel auxMov;
    private AtualizavelList atualizaveis;
    
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    /**
     * Construtor default vazio.
     */
    public ImagemBitmapList() {
        imagemBitmapList = new ArrayList<>();
        
        //Auxiliares        
        auxMov = new AuxiliaresMovimentavel();
        atualizaveis = new AtualizavelList();
    }
    
    /**
     * Construtor que já adiciona a primeira imagem ao arrayList.
     * @param novaImagem Nova imagem a ser adicionada
     */
    public ImagemBitmapList(ImagemBitmap novaImagem) {
        this();
        add(novaImagem);
    }
    
    
    /**
     * Construtor que permite adicionar várias imagens ao mesmo tempo ao arrayList.<br>
     * Obs.: Este método executa internamente o método addCopy() para que todas 
     * as imagens adicionadas sejam independentes umas das outras e de outros Lists.
     * @param variasImagens A novas imagens
     */
    public ImagemBitmapList(ImagemBitmap... variasImagens) {
        this();
        for (ImagemBitmap variasImagen : variasImagens) {
            addCopy(variasImagen);
        }
    }
    //</editor-fold>
    
    /**
     * Corrige as Coordenadas de cada imagem, a partir das Coordenadas da primeira
     * imagem, para que uma fique ao lado de outra horizontalmente
     */
    public void arrumarCoordenadasParaExibirHorizontalmente(){
        double x = getX(), y = getY();
        if (size() > 1) {
            for (int cont = 1; cont < size(); cont++) {
                x += get(cont - 1).getComprimento();
                get(cont).setCoordenadas(x, y);
            }
        }
    }
    
    /**
     * Corrige as Coordenadas de cada imagem, a partir das Coordenadas da primeira
     * imagem, para que uma fique ao lado de outra verticalmente
     */
    public void arrumarCoordenadasParaExibirVerticalmente(){
        double x = getX(), y = getY();
        if (size() > 1) {
            for (int cont = 1; cont < size(); cont++) {
                y += get(cont - 1).getAltura();
                get(cont).setCoordenadas(x, y);
            }
        }
    }
    
    /**
     * Adiciona um novo elemento ao ListManuseavel, no entanto, esse novo 
     * elemento já existia dentro do ListManuseavel, sendo assim, se as 
     * Coordenadas do primeiro fossem alteradas, fatalmente do novo elemento 
     * também seriam; ou seja, esse método copia os atributos básicos do 
     * elemento passados por parâmetro e cria uma nova instância com os 
     * atributos copiados, assim ambos tornam-se independentes uns dos outros.
     * @param imagemBitmapParaCopiar A nova imagem a ser copiada e adicionada
     */
    public void addCopy(ImagemBitmap imagemBitmapParaCopiar) {
        ImagemBitmap imagemBitmap = imagemBitmapParaCopiar.getCopy();        
        imagemBitmapList.add(imagemBitmap);
    }
    
    /**
     * Ativa a exibição da cor da borda de todas CaixaColisao dentro do List.
     */
    public void ativarCorBordaCaixaColisaoListTodasImagens() {
        if (size() >= 1) {
            for (int cont = 0; cont < size(); cont++) {
                get(cont).ativarCorBordaCaixaColisaoList();
            }
        }
    }

    /**
     * Desativa a exibição da cor da borda de todas CaixaColisao dentro do List.
     */
    public void desativarCorBordaCaixaColisaoListTodasImagens() {
        if (size() >= 1) {
            for (int cont = 0; cont < size(); cont++) {
                get(cont).desativarCorBordaCaixaColisaoList();
            }
        }
    }
    
    /**
     * Altera a rolagemInfinita de todas imagens no List, ou seja, marca se todas
     * imagens devem ficar rolando infinitamente dentro de seus limites 
     * semelhantemente a uma esteira fabril. Os movimentos da rolagem são 
     * baseados em getVelX() e getVelY().<br>
     * Obs.: Este efeito não é compatível com o espelhamento de imagem.
     * @param tipoRolagem O novo tipo de rolagem infinita
     */
    public void setRolagemInfinita(TipoRolagemInfinita tipoRolagem){
        for (int cont = 0; cont < size(); cont++) {
            get(cont).setRolagemInfinita(tipoRolagem);
        }
    }
    
    /**
     * Retorna o valor da rolagemInfinita apenas da primeira imagem do List.
     * @return TipoRolagemInfinita
     */
    public TipoRolagemInfinita getRolagemInfinita(){
        return get(0).getRolagemInfinita();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: ListManuseavel">
    @Override
    public void add(ImagemBitmap imagemBitmap) {
        imagemBitmapList.add(imagemBitmap);
    }
    
    @Override
    public void add(ImagemBitmap... variosElementos) {
        imagemBitmapList.addAll(Arrays.asList(variosElementos));
    }    
    
    @Override
    public ImagemBitmap get(int indice) {
        return imagemBitmapList.get(indice);
    }
    
    @Override
    public ImagemBitmap getNext() {
        ImagemBitmap temp = imagemBitmapList.get(indiceAtual);
        indiceAtual++;
        
        if (indiceAtual >= size()) {
            indiceAtual = 0;
            return null;
        }
        
        return temp;
    }
    
    @Override
    public void remove(int indice){
        imagemBitmapList.remove(indice);
    }
    
    @Override
    public void remove(ImagemBitmap imagem){
        imagemBitmapList.remove(imagem);
    }
        
    @Override
    public int size() {
        return imagemBitmapList.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Coordenavel">
    @Override
    public void setX(double x) {
        if (size() >= 1) {
            for (int cont = 0; cont < size(); cont++) {
                get(cont).setX(x);
            }
        }
    }

    @Override
    public void setY(double y) {
        if (size() >= 1) {
            for (int cont = 0; cont < size(); cont++) {
                get(cont).setY(y);
            }
        }
    }

    @Override
    public void incrementaX(double aumentoX) {
        if (size() >= 1) {
            for (int cont = 0; cont < size(); cont++) {
                get(cont).incrementaX(aumentoX);
            }
        }
    }

    @Override
    public void incrementaY(double aumentoY) {
        if (size() >= 1) {
            for (int cont = 0; cont < size(); cont++) {
                get(cont).incrementaY(aumentoY);
            }
        }
    }

    @Override
    public void setCoordenadas(double x, double y) {
        if (size() >= 1) {
            for (int cont = 0; cont < size(); cont++) {
                get(cont).setCoordenadas(x, y);
            }
        }
    }
    
    @Override
    public void setCoordenadas(Coordenadas novasCoordenadas) {
        if (size() >= 1) {
            for (int cont = 0; cont < size(); cont++) {
                get(cont).setCoordenadas(novasCoordenadas);
            }
        }
    }

    @Override
    public void incrementarCoordenadas(double aumentoX, double aumentoY) {
        if (size() >= 1) {
            for (int cont = 0; cont < size(); cont++) {
                get(cont).incrementarCoordenadas(aumentoX, aumentoY);
            }
        }
    }

    @Override
    public double getX() {
        return get(0).getX();
    }

    @Override
    public int getIntX() {
        return get(0).getIntX();
    }

    @Override
    public double getY() {
        return get(0).getY();
    }

    @Override
    public int getIntY() {
        return get(0).getIntY();
    }
    
    @Override
    public Coordenadas getCoordenadas() {
        return get(0).getCoordenadas();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Movimentavel">
    @Override
    public void setVelX(double velX) {
        if (size() >= 1) {
            for (int cont = 0; cont < size(); cont++) {
                get(cont).setVelX(velX);
            }
        }
    }

    @Override
    public void setVelY(double velY) {
        if (size() >= 1) {
            for (int cont = 0; cont < size(); cont++) {
                get(cont).setVelY(velY);
            }
        }
    }

    @Override
    public void incrementaVelX(double aumentoVelX) {
        if (size() >= 1) {
            for (int cont = 0; cont < size(); cont++) {
                get(cont).incrementaVelX(aumentoVelX);
            }
        }
    }

    @Override
    public void incrementaVelY(double aumentoVelY) {
        if (size() >= 1) {
            for (int cont = 0; cont < size(); cont++) {
                get(cont).incrementaVelY(aumentoVelY);
            }
        }
    }

    @Override
    public void setVelocidades(double velX, double velY) {
        if (size() >= 1) {
            for (int cont = 0; cont < size(); cont++) {
                get(cont).setVelocidades(velX, velY);
            }
        }
    }

    @Override
    public void incrementarVelocidades(double aumentoVelX, double aumentoVelY) {
        if (size() >= 1) {
            for (int cont = 0; cont < size(); cont++) {
                get(cont).incrementarVelocidades(aumentoVelX, aumentoVelY);
            }
        }
    }

    @Override
    public double getVelX() {
        return get(0).getVelX();
    }

    @Override
    public int getIntVelX() {
        return get(0).getIntVelX();
    }

    @Override
    public double getVelY() {
        return get(0).getVelY();
    }

    @Override
    public int getIntVelY() {
        return get(0).getIntVelY();
    }

    @Override
    public void movimentar(TipoMovimento novoMovimento) {
        auxMov.setMovimentoPorTipo(novoMovimento);
        
        for (int cont = 0; cont < size(); cont++) {
           get(cont).movimentar(novoMovimento);
        }
    }

    @Override
    public void movimentar(double direcaoEmGraus) {
        auxMov.setMovimentoPorGrau(direcaoEmGraus);
        
        for (int cont = 0; cont < size(); cont++) {
            get(cont).movimentar(direcaoEmGraus);
        }
    }

    @Override
    public void movimentarCurvaQuadratica(
            double alturaX, double alturaY, 
            double comprimentoX, double comprimentoY
    ) {
        auxMov.setMovimentoPorCurvaQuadratica(alturaX, alturaY, comprimentoX, comprimentoY);
        
        for (int cont = 0; cont < size(); cont++) {
            get(cont).movimentarCurvaQuadratica(
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
        
        for (int cont = 0; cont < size(); cont++) {
            get(cont).movimentarRepetidamente(
                    indiceDeformardorX, indiceDeformardorY, 
                    comprimento, altura, inverter
            );
        }
    }

    @Override
    public void movimentarPorFuncao(String funcao, boolean iniciarEmCoordNegativa) {
        auxMov.setMovimentoPorFuncao(funcao, iniciarEmCoordNegativa);
        
        for (int cont = 0; cont < size(); cont++) {
            get(cont).movimentarPorFuncao(funcao, iniciarEmCoordNegativa);
        }
    }

    @Override
    public void pararMovimento() {
        auxMov.setMovimentoNenhum();
        
        for (int cont = 0; cont < size(); cont++) {
            get(cont).pararMovimento();
        }
    }

    @Override
    public double calcularFuncao(String funcao) {
        return get(0).calcularFuncao(funcao);
    }

    @Override
    public double calcularFuncao(String funcao, double intervaloIncial, double intervaloFinal) {
        return get(0).calcularFuncao(funcao, intervaloIncial, intervaloFinal);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: ImagemTransformavel">
    @Override
    public void espelhar(TipoEspelhamento tipoEspelhamento){
        for (int indice = 0; indice < size(); indice++) {
            get(indice).espelhar(tipoEspelhamento);
        }
    }
    
    @Override
    public void rotacionar(double graus) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).rotacionar(graus);
        } 
    }   
    
    @Override
    public void rotacionar(double graus, Coordenadas pivoRotacao) {
        for (int indice = 0; indice < size(); indice++) {
            get(indice).rotacionar(graus, pivoRotacao);
        } 
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Dimensionavel">
    @Override
    public void redimensionar(double escala) {
        for (int cont = 0; cont < size(); cont++) {
            get(cont).redimensionar(escala);
        }
    }
    
    /**
     * Retorna o comprimento da ImagemBitmap de índice=0.
     * @return int em pixels
     */
    @Override
    public int getComprimento() {
        return get(0).getComprimento();
    }
    
    /**
     * Retorna o comprimento da ImagemBitmap solicitada no parâmetro.
     * @param indice O índice da imagem solicitada
     * @return int em pixels
     */
    public int getComprimento(int indice) {
        return get(indice).getComprimento();
    }
    
    /**
     * Retorna a altura da ImagemBitmap de índice=0.
     * @return int em pixels
     */
    @Override
    public int getAltura() {
        return get(0).getAltura();
    }    

    /**
     * Retorna a altura da ImagemBitmap solicitada no parâmetro.
     * @param indice O índice da imagem solicitada
     * @return int em pixels
     */
    public int getAltura(int indice) {
        return get(indice).getAltura();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Colidivel e Alterações">
    /**
     * Retorna a CaixaColisao da primeira imagem adicionada.
     * @return CaixaColisao
     */
    @Override
    public CaixaColisao getCaixaColisao(){
        return get(0).getCaixaColisao();
    }
    
    /**
     * Retorna a CaixaColisao de acordo com índice passado por parâmetro.
     * @param indice O índice da imagem procurada
     * @return CaixaColisao
     */
    public CaixaColisao getCaixaColisao(int indice){
        return get(indice).getCaixaColisao();
    }
    
    /**
     * Usado para descobrir se a ImagemBitmap de índice=0 colidiu com outra em 
     * Tela.
     * @param outroObjeto Outra CaixaColisao para ser avaliada a colisão
     * @param tipoColisao Enum que determina se a colisão a ser avaliada é uma
     * colisão INTERNA ou EXTERNA
     * @return true : Caso houve um colisão qualquer <br>
     * false : Caso não houve colisão
     */
    @Override
    public boolean colidiu(CaixaColisao outroObjeto, TipoColisao tipoColisao) {
        return get(0).colidiu(outroObjeto, tipoColisao);
    }
    
    /**
     * Usado para descobrir se a ImagemBitmap do índice passado por parâmetro
     * colidiu com outra em Tela.
     * @param indiceImagem O índice da imagem para ser avaliada a colisão
     * @param outroObjeto Outra CaixaColisao para ser avaliada a colisão
     * @param tipoColisao Enum que determina se a colisão a ser avaliada é uma
     * colisão INTERNA ou EXTERNA
     * @return true : Caso houve um colisão qualquer <br>
     * false : Caso não houve colisão
     */
    public boolean colidiu(int indiceImagem, CaixaColisao outroObjeto, TipoColisao tipoColisao) {
        return get(indiceImagem).colidiu(outroObjeto, tipoColisao);
    }
    
    /**
     * Usado para descobrir se a ImagemBitmap do índice passado por parâmetro
     * colidiu, de acordo com o índice da CaixaColisao procurada, com outra em Tela.
     * @param indiceImagem O índice da imagem para ser avaliada a colisão
     * @param indiceCaixaColisao O índice da CaixaColisao procurada
     * @param outroObjeto Outra CaixaColisao para ser avaliada a colisão
     * @param tipoColisao Enum que determina se a colisão a ser avaliada é uma
     * colisão INTERNA ou EXTERNA
     * @return true : Caso houve um colisão qualquer <br>
     * false : Caso não houve colisão
     */
    public boolean colidiu(int indiceImagem, int indiceCaixaColisao, 
            CaixaColisao outroObjeto, TipoColisao tipoColisao) {
        return get(indiceImagem).getCaixaColisao(indiceCaixaColisao)
                .colidiu(outroObjeto, tipoColisao);
    }
    
    /**
     * Usado para descobrir se a ImagemBitmap de índice=0 colidiu com outra em 
     * Tela e em qual lado que ocorreu a colisão.
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
        return get(0).colidiuNoLado(outroObjeto, tipoColisao);
    }

    /**
     * Usado para descobrir se a ImagemBitmap do índice passado por parâmetro
     * colidiu com outra em Tela e em qual lado que ocorreu a colisão.
     * @param indiceImagem O índice da imagem procurada
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
    public LadoRetangulo colidiuNoLado(int indiceImagem, CaixaColisao outroObjeto, TipoColisao tipoColisao) {
        return get(indiceImagem).colidiuNoLado(outroObjeto, tipoColisao);
    }
    
    /**
     * Usado para descobrir se a ImagemBitmap do índice passado por parâmetro
     * colidiu, de acordo com o índice da CaixaColisao procurada, com outra em
     * Tela e em qual lado que ocorreu a colisão.
     * @param indiceImagem O índice da imagem procurada
     * @param indiceCaixaColisao O índice da CaixaColisao procurada
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
    public LadoRetangulo colidiuNoLado(int indiceImagem, int indiceCaixaColisao, 
            CaixaColisao outroObjeto, TipoColisao tipoColisao) {
        return get(indiceImagem).getCaixaColisao(indiceCaixaColisao)
                .colidiuNoLado(outroObjeto, tipoColisao);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
    @Override
    public void desenha(Graphics2D g2d) {        
        for (ImagemBitmap imagem : imagemBitmapList) {
            imagem.desenha(g2d);
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
