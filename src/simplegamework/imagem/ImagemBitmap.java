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

import simplegamework.objetoBasico.TipoColisao;
import simplegamework.objetoBasico.Coordenadas;
import simplegamework.objetoBasico.CaixaColisao;
import simplegamework.objetoBasico.TipoMovimento;
import simplegamework.objetoBasico.LadoRetangulo;
import simplegamework.objetoBasico.CaixaColisaoList;
import simplegamework.padraoProjeto.Atualizavel;
import simplegamework.padraoProjeto.Dimensionavel;
import simplegamework.padraoProjeto.ImagemTransformavel;
import simplegamework.padraoProjeto.Movimentavel;
import simplegamework.padraoProjeto.AtualizavelListManuseavel;
import simplegamework.padraoProjeto.Colidivel;
import simplegamework.padraoProjeto.Desenhavel;
import simplegamework.padraoProjeto.Copiavel;
import simplegamework.padraoProjeto.CaixaColisaoListManuseavel;
import simplegamework.padraoProjeto.Coordenavel;
import simplegamework.cor.CorRGBA;
import simplegamework.ferramenta.AtualizavelList;
import simplegamework.ferramenta.AuxiliaresMovimentavel;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Define uma imagem bitmap básica (com ou sem transparência) a ser 
 * exibida numa Tela. Os formatos permitidos são: JPEG, JPG, GIF e PNG.<br>
 * Toda ImagemBitmap possui um atributo bloqueada que é inicializado com false.
 * A finalidade desse atributo depende do uso e do contexto da ImagemBitmap.<br>
 * Quando é instanciado um objeto dessa classe, ela cria uma CaixaColisaoList
 * pois utiliza o conceito de que uma ImagemBitmap pode possuir inúmeras CaixaColisao
 * a fim de permitir uma colisão mais realista. Também é criado uma CaixaColisao
 * inicial dentro do List que segue as mesmas Coordenadas e dimensões da ImagemBitmap,
 * a constante que lida com ela é ImagemBitmap.CX_COLISAO_IMG_TODA ou basta chamar
 * o método objeto.getCaixaColisaoImagemToda(). Quando é adicionada uma nova
 * CaixaColisao deve-se levar em conta que as novas Coordenadas passadas refletem
 * as Coordenadas internas da ImagemBitmap, ou seja, independente da posição que
 * a ImagemBitmap estiver, as Coordenadas internas sempre começam em x=0 e y=0.<br>
 * Obs.: Uma ImagemBitmap não tem suporte à bordas, no entanto cada CaixaColisao
 * dela possui.
 * <br><br><small>Created on : 01/05/2015, 22:18:55</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class ImagemBitmap implements Copiavel<ImagemBitmap>, CaixaColisaoListManuseavel, Coordenavel, 
        Movimentavel, Dimensionavel, ImagemTransformavel, Colidivel, Desenhavel, 
        Atualizavel, AtualizavelListManuseavel{

    protected Image imagem;
    protected String URL;
    protected boolean importouImagem;
    protected int codigo;
    protected boolean bloqueada;
    
    protected CaixaColisaoList caixaColisaoList;
    public static final int CX_COLISAO_IMG_TODA = 0;
    
    protected TipoFiltroExibicao tipoFiltroExibicao;
    protected TipoEspelhamento tipoEspelhamento;
    protected TipoRolagemInfinita rolagemInfinita;
    
    protected AtualizavelList atualizaveis;
    
    //Auxiliares
    private AuxiliaresMovimentavel auxMov;
    
    private Coordenadas coordRotacao;
    private double radianosRotacao;
    private AffineTransform affineTransform;
    private double xRolagem;
    private double yRolagem;
        
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    /**
     * Construtor básico de uma ImagemBitmap.<br>
     * Obs.: O valor default de código é -71460.
     * @param imagemURL O endereço da localização da imagem, a partir do Pacote
     * Default Java. Ex.: "/Pasta/bg.png".
     * @param x A coordenada x inicial da imagem para exibição na Tela
     * @param y A coordenada y inicial da imagem para exibição na Tela
     */
    public ImagemBitmap(String imagemURL, double x, double y) {
        URL = imagemURL;
        importar();
        initAtributos(x, y);
    }
    
    /**
     * Construtor usado para tiles, pois cada tile pode ter um código único para
     * exibição.
     * @param imagemURL O endereço da localização da imagem. Ex.: "/Pasta/bg.png"
     * @param x A coordenada x inicial da imagem para exibição na Tela
     * @param y A coordenada y inicial da imagem para exibição na Tela
     * @param codigo O código de exibição da imagem
     */
    public ImagemBitmap(String imagemURL, double x, double y, int codigo){
        this(imagemURL, x, y);
        this.codigo = codigo;        
    }
    
    /**
     * Construtor usado para tiles, pois cada tile pode ter um código único, para 
     * exibição, e também seu estado, bloqueada ou não.<br>
     * Obs.: O bloqueio da ImagemBitmap não influência na sua visualização.
     * @param imagemURL O endereço da localização da imagem. Ex.: "/Pasta/bg.png"
     * @param x A coordenada x inicial da imagem para exibição na Tela
     * @param y A coordenada y inicial da imagem para exibição na Tela
     * @param codigo O código de exibição da imagem
     * @param bloqueada Define se a ImagemBitmap está bloqueada ou não
     */
    public ImagemBitmap(String imagemURL, double x, double y, int codigo, boolean bloqueada){
        this(imagemURL, x, y);
        this.codigo = codigo;        
        this.bloqueada = bloqueada;
    }
    
    /**
     * Construtor que transforma uma Image em ImagemBitmap.
     * @param imagemCriada Imagem que já tinha sido criada
     * @param x A coordenada x inicial da imagem para exibição na Tela
     * @param y A coordenada y inicial da imagem para exibição na Tela
     */
    public ImagemBitmap(Image imagemCriada, double x, double y){
        imagem = imagemCriada;
        importouImagem = imagem.getHeight(null) > 0;
        initAtributos(x, y);
    }
    
    /**
     * Construtor que transforma uma BufferedImage em ImagemBitmap.
     * @param imagemCriada Imagem que já tinha sido criada
     * @param x A coordenada x inicial da imagem para exibição na Tela
     * @param y A coordenada y inicial da imagem para exibição na Tela
     */
    public ImagemBitmap(BufferedImage imagemCriada, double x, double y){
        imagem = imagemCriada.getScaledInstance(
                imagemCriada.getWidth(), imagemCriada.getHeight(), 
                Image.SCALE_SMOOTH
        );
        importouImagem = imagem.getHeight(null) > 0;
        initAtributos(x, y);
    }
    
    /**
     * Construtor que transforma uma Image em ImagemBitmap.
     * @param imagemCriada Imagem que já tinha sido criada
     * @param x A coordenada x inicial da imagem para exibição na Tela
     * @param y A coordenada y inicial da imagem para exibição na Tela
     * @param codigo O código da ImagemBitmap, geralmente utilizado em tiles
     */
    public ImagemBitmap(Image imagemCriada, double x, double y, int codigo){
        this(imagemCriada, x, y);
        this.codigo = codigo;
    }
    
    /**
     * Construtor que transforma uma BufferedImage em ImagemBitmap.
     * @param imagemCriada Imagem que já tinha sido criada
     * @param x A coordenada x inicial da imagem para exibição na Tela
     * @param y A coordenada y inicial da imagem para exibição na Tela
     * @param codigo O código da ImagemBitmap, geralmente utilizado em tiles
     */
    public ImagemBitmap(BufferedImage imagemCriada, double x, double y, int codigo){
        this(imagemCriada, x, y);
        this.codigo = codigo;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: Auxiliares">
    /**
     * Importa uma nova imagem e subistitui a antiga.
     * @param imagemURL O endereço da localização da imagem. Ex.: "/Pasta/bg.png"
     * @return true : Caso importou com sucesso <br>
     * false : Caso houve algum problema com URL da imagem ou seu formato.
     */
    private void importar() {
        if (!URL.equals("")) {        
            try {
                imagem = ImageIO.read(this.getClass().getResource(URL));               
                importouImagem = true;
            } catch (Exception e) {
                //caso uma imagem anterior tenha sido importada com sucesso essa 
                //variável teria continuado a valer true
                importouImagem = false;
                System.out.println(
                        "+------------------------------------+\n" +
                        "| Erro ao importar a ImagemBitmap! :(\n" +
                        "| URL: " + URL + "\n" +
                        "+------------------------------------+\n"
                );
            }
        }
        else{
            importouImagem = false;
        }
    }
    
    /**
     * Incializador dos atributos de classe.
     * @param x A coordenada x inicial da imagem para exibição na Tela
     * @param y A coordenada y inicial da imagem para exibição na Tela
     */
    private void initAtributos(double x, double y){
        if (importouImagem) {
            codigo = -71460;
            caixaColisaoList = new CaixaColisaoList(
                 new CaixaColisao(
                    "CX_COLISAO_IMG_TODA", x, y,
                    imagem.getWidth(null), imagem.getHeight(null)
                 )
            );
            
            //Auxiliares
            coordRotacao = new Coordenadas(0, 0);
           
            affineTransform = new AffineTransform();
            tipoFiltroExibicao = TipoFiltroExibicao.BICUBIC;
            rolagemInfinita = TipoRolagemInfinita.NENHUMA;
            xRolagem = x;
            yRolagem = y;
            
            atualizaveis = new AtualizavelList();
            
            auxMov = new AuxiliaresMovimentavel();
        }
    }
    
    /**
     * Quando a ImagemBitmap se move pela operação SET suas CaixaColisao dentro 
     * do List devem acompanhar as novas Coordenadas.
     * @param xAtual O valor de x antes da mudança
     * @param yAtual O valor de y antes da mudança
     * @param xNovo O valor de x após a mudança
     * @param yNovo O valor de y após a mudança
     */
    private void corrigeCoordenadasCaixaColisaoListOperacaoSet(
            double xAtual, double yAtual, double xNovo, double yNovo
    ){
        if (sizeCaixaColisaoList() > 1) {
            double difX = xNovo - xAtual;
            double difY = yNovo - yAtual;
            for (int indice = 1; indice < sizeCaixaColisaoList(); indice++) {
                getCaixaColisao(indice).incrementarCoordenadas(difX, difY);
            }
        }
    }
    
    /**
     * Quando a ImagemBitmap se move pela operação INCREMENTO suas CaixaColisao
     * dentro do List devem acompanhar as novas Coordenadas.
     * @param incX O valor a ser incrementado em x
     * @param incY O valor a ser incrementado em y
     */
    private void corrigeCoordenadasCaixaColisaoListOperacaoIncrementa(
            double incX, double incY
    ){
        if (sizeCaixaColisaoList() > 1) {
            for (int indice = 1; indice < sizeCaixaColisaoList(); indice++) {
                getCaixaColisao(indice).incrementarCoordenadas(incX, incY);
            }
        }
    }
    
    /**
     * Caso uma nova CaixaColisao ultrapasse os limites da ImagemBitmap, ela será
     * corrigida.
     * @param cx A CaixaColisao a ser avaliada
     * @return CaixaColisao
     */
    private CaixaColisao corrigeCoordenadasNovaCaixaColisao(CaixaColisao cx){
        double x = cx.getX(), y = cx.getY();
        int comp = cx.getComprimento(), alt = cx.getAltura();
        Object rotulo = cx.getRotulo();
        CorRGBA cor = cx.getCorBorda();
        boolean bordaAtiva = cx.isCorBordaAtivada();
        
        if ((x + comp) > (getX() + getComprimento())) {
            double dif = (x + comp) - (getX() + getComprimento());
            comp -= (int)dif;
        }
        
        if ((y + alt) > (getY() + getAltura())) {
            double dif = (y + alt) - (getY() + getAltura());
            alt -= (int)dif;
        }
        
        cx = new CaixaColisao(rotulo, x, y, comp, alt, cor);
        if (bordaAtiva) {
            cx.ativarCorBorda();
        }
        
        return cx;
    }    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: Statics">
    /**
     * Retorna uma Image vazia e toda transparente de acordo com o comprimento
     * e altura solicitada.<br>
     * Obs.: As dimensões limite são 100x100.
     * @param comprimento O valor do comprimento solicitado
     * @param altura O valor da altura solicitada
     * @return Image
     */
    public static Image getImageVaziaTransparente(int comprimento, int altura){
        if (comprimento <= 100 && altura <= 100) {
            ImagemBitmap imagemNull = new ImagemBitmap(
                    "/framework/imagem/megaTileTransparente.gif", 0, 0, -1
            );        

            return imagemNull.getFatiaImage(0, 0, comprimento, altura);
        }
        
        return null;
    }
    
    /**
     * Retorna uma ImagemBitmap vazia e toda transparente de acordo com o comprimento
     * e altura solicitada.<br>
     * Obs.: As dimensões limite são 100x100. O código da imagem  retornada será -1.
     * @param comprimento O valor do comprimento solicitado
     * @param altura O valor da altura solicitada
     * @param codigo O código da nova imagem
     * @return ImagemBitmap
     */
    public static ImagemBitmap getImagemBitmapVaziaTransparente(int comprimento, int altura, int codigo){
        Image tempImage = getImageVaziaTransparente(comprimento, altura);
        
        if (tempImage != null) {
            return new ImagemBitmap(tempImage, 0, 0, codigo);
        }
        
        return null;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: Fatiadores">
    /**
     * Fatiador de imagem que retorna uma Image.<br>
     * Obs.: Caso algum valor de parâmetro seja inválido (Ex.: comprimentoFinal &gt; comprimento),
     * o valor passado será descartado e será mantido o orignal (Ex.: comprimentoFinal = comprimento).
     * @param xInicial Posição x em relação à imagem original e não com a Tela
     * @param yInicial Posição y em relação à imagem original e não com a Tela
     * @param comprimentoFinal Comprimento da nova imagem em relação à imagem original
     * @param alturaFinal Altura da nova imagem em relação à imagem original
     * @return Image : Caso o fatiamento tenha ocorrido com sucesso<br>
     * null : Caso houve alguma falha ao fatiar
     */
    public Image getFatiaImage(int xInicial, int yInicial, int comprimentoFinal, int alturaFinal) {
        if (importouImagem) {            
            xInicial = xInicial < 0 ? 0 : xInicial;
            xInicial = xInicial > getComprimento() ? 0 : xInicial;
            yInicial = yInicial < 0 ? 0 : yInicial;
            yInicial = yInicial > getAltura() ? 0 : yInicial;
            
            comprimentoFinal = comprimentoFinal < 0 ?
                    (getComprimento() - xInicial) : comprimentoFinal;
            comprimentoFinal = comprimentoFinal > (getComprimento() - xInicial) ? 
                    (getComprimento() - xInicial) : comprimentoFinal;
            
            alturaFinal = alturaFinal < 0 ?
                    (getAltura() - yInicial) : alturaFinal;
            alturaFinal = alturaFinal > (getAltura() - yInicial) ? 
                    (getAltura() - yInicial) : alturaFinal;
            
            Image temp = null;
            
            try {
                temp =  getBufferedImage()
                            .getSubimage(
                                    xInicial, yInicial, 
                                    comprimentoFinal, alturaFinal
                            );
            } catch (Exception e) {
                System.out.println(                 
                        "+-------------------------------------+\n" +
                        "|ERRO AO TENTAR FATIAR A IMAGEM!!! :( |\n" +
                        "+-------------------------------------+\n" +
                        "xInicial: " + xInicial + ", yInicial: " + yInicial + "\n" +
                        "comprimentoFinal: " + comprimentoFinal + ", alturaFinal: " + alturaFinal
                );
                e.printStackTrace();
            }
            
            return temp;            
        }
        else{
            return null;
        }
    }
    
    /**
     * Fatiador de imagem que mantém as mesmas coordenadas da imagem original.<br>
     * Obs.: Caso algum valor de parâmetro seja inválido (Ex.: comprimentoFinal &gt; comprimento),
     * o valor passado será descartado e será mantido o orignal (Ex.: comprimentoFinal = comprimento).
     * @param xInicial Posição x em relação à imagem original e não com a Tela
     * @param yInicial Posição y em relação à imagem original e não com a Tela
     * @param comprimentoFinal Comprimento da nova imagem em relação à imagem original
     * @param alturaFinal Altura da nova imagem em relação à imagem original
     * @return ImagemBitmap : Caso o fatiamento tenha ocorrido com sucesso<br>
     * null : Caso houve alguma falha ao fatiar
     */
    public ImagemBitmap getFatiaImagemBitmap(int xInicial, int yInicial, int comprimentoFinal, int alturaFinal) {
        Image tempImage = getFatiaImage(xInicial, yInicial, comprimentoFinal, alturaFinal);
        
        if (tempImage != null) {
            ImagemBitmap temp = new ImagemBitmap(tempImage, getX(), getY(), getCodigo());
            return temp;
        }
        return null;
    }
    //</editor-fold>
    
    /**
     * Ativa o debugGrafico, ou seja, escreve os atributos de Coordenadas na tela.
     */
    public void ligarDebugGraficoCoordenadas(){
        caixaColisaoList.get(CX_COLISAO_IMG_TODA).ligarDebugGraficoCoordenadas();
    }
    
    /**
     * Desativa o debugGrafico, ou seja, não serão exibidos os atributos de
     * Coordenadas na tela.
     */
    public void desligarDebugGraficoCoordenadas(){
        caixaColisaoList.get(CX_COLISAO_IMG_TODA).desligarDebugGraficoCoordenadas();
    }
    
    /**
     * Retorna o estado atual de debugGrafico.
     * @return boolean
     */
    public boolean isDebugGraficoAtivoCoordenadas(){
        return caixaColisaoList.get(CX_COLISAO_IMG_TODA).isDebugGraficoAtivoCoordenadas();
    }
        
    //<editor-fold defaultstate="collapsed" desc="GETs & SETs">
    /**
     * Retorna a imagem importada no formato Image.
     * @return Image
     */
    public Image getImage(){
        return imagem;
    }
    
    /**
     * Retorna a imagem importada no formato BufferedImage.
     * @return BufferedImage
     */
    public BufferedImage getBufferedImage(){
        return (BufferedImage) imagem;
    }
    
    /**
     * Retorna o endereço da imagem importada. Caso os contrutores de imagens
     * já importadas tenha sido utilizado, o retorno será uma String vazia.
     * @return String
     */
    public String getURL(){
        return URL;
    }
    
    /**
     * Retorna o status da importação da imagem.
     * @return boolean
     */
    public boolean isImportouImagem(){
        return importouImagem;
    }
    
    /**
     * Altera o código da ImagemBitmap.
     * @param novoCodigo O valor do código
     */
    public void setCodigo(int novoCodigo){
        codigo = novoCodigo;
    }
    
    /**
     * Retorna o código da ImagemBitmap.
     * @return int
     */
    public int getCodigo(){
        return codigo;
    }
    
    /**
     * Altera o valor de bloqueada para true.
     */
    public void bloquear(){
        bloqueada = true;
    }
    
    /**
     * Altera o valor de bloqueada para false.
     */
    public void desbloquear(){
        bloqueada = false;
    }
    
    /**
     * Retorna o status do bloqueio da ImagemBitmap.
     * @return boolean
     */
    public boolean isBloqueada(){
        return bloqueada;
    }
    
    /**
     * Retorna a CaixaColisao principal que é um retangulo com as mesmas dimensões
     * e coordenadas da ImagemBitmap.
     * @return CaixaColisao
     */
    public CaixaColisao getCaixaColisaoImagemToda(){
        return caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA);
    }
    
    /**
     * Altera o TipoFiltroExibicao da ImagemBitmap. O valor padrão é NEAREST_NEIGHBOR.
     * @param tipoFiltro O valores podem ser BILINEAR, BICUBIC, NEAREST_NEIGHBOR
     */
    public void setTipoFiltroExibicao(TipoFiltroExibicao tipoFiltro){
        tipoFiltroExibicao = tipoFiltro;
    }
    
    /**
     * Retorna o valor do TipoFiltroExibicao atual.
     * @return TipoFiltroExibicao
     */
    public TipoFiltroExibicao getTipoFiltroExibicao(){
        return tipoFiltroExibicao;
    }
    
    /**
     * Altera a rolagemInfinita, ou seja, marca se uma imagem deve ficar rolando
     * infinitamente dentro de seus limites semelhantemente a uma esteira fabril.
     * Os movimentos da rolagem são baseados em getVelX() e getVelY().<br>
     * Obs.: Este efeito não é compatível com o espelhamento de imagem.
     * @param tipoRolagem O novo tipo de rolagem infinita
     */
    public void setRolagemInfinita(TipoRolagemInfinita tipoRolagem){
        rolagemInfinita = tipoRolagem;
    }
    
    /**
     * Retorna o valor de rolagemInfinita.
     * @return TipoRolagemInfinita
     */
    public TipoRolagemInfinita getRolagemInfinita(){
        return rolagemInfinita;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Copiavel">
    /**
     * Retorna um novo objeto porém sendo uma cópia desse. Muito útil nos casos
     * em que se precisa ter muitas imagens iguais na tela com Coordenadas
     * diferentes umas das outras.
     * @return ImagemBitmap
     */
    @Override
    public ImagemBitmap getCopy(){
        ImagemBitmap copia = new ImagemBitmap(imagem, getX(), getY());
        copia.setVelocidades(getVelX(), getVelY());
        return copia;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: CaixaColisaoListManuseavel">
    @Override
    public void addCaixaColisao(CaixaColisao novoElemento) {
        novoElemento.incrementarCoordenadas(getX(), getY());
        novoElemento = corrigeCoordenadasNovaCaixaColisao(novoElemento);
        caixaColisaoList.add(novoElemento);
    }

    @Override
    public void addCaixaColisao(CaixaColisao... variosElementos) {
        for (int indice = 0; indice < sizeCaixaColisaoList(); indice++) {
            variosElementos[indice].incrementarCoordenadas(getX(), getY());
            variosElementos[indice] = corrigeCoordenadasNovaCaixaColisao(variosElementos[indice]);
        }
        caixaColisaoList.add(variosElementos);
    }

    @Override
    public CaixaColisao getCaixaColisao(int indice) {
        return caixaColisaoList.get(indice);
    }

    @Override
    public CaixaColisao getNextCaixaColisao() {
        return caixaColisaoList.getNext();
    }
    
    @Override
    public CaixaColisao getCaixaColisao(Object rotulo) {
        return caixaColisaoList.get(rotulo);
    }

    @Override
    public void removeCaixaColisao(int indice) {
        caixaColisaoList.remove(indice);
    }

    @Override
    public void removeCaixaColisao(CaixaColisao elementoParaRemover) {
        caixaColisaoList.remove(elementoParaRemover);
    }
    
    @Override
    public void removeCaixaColisao(Object rotulo) {
        for (int indice = 0; indice < sizeCaixaColisaoList(); indice++) {
            if (caixaColisaoList.get(indice).getRotulo().equals(rotulo)) {
                caixaColisaoList.remove(indice);
            }
        }
    }
    
    @Override
    public int sizeCaixaColisaoList() {
        return caixaColisaoList.size();
    }
    
    @Override
    public void ativarCorBordaCaixaColisaoList() {
        for (int indice = 0; indice < sizeCaixaColisaoList(); indice++) {
            caixaColisaoList.get(indice).ativarCorBorda();
        }
    }

    @Override
    public void desativarCorBordaCaixaColisaoList() {
        for (int indice = 0; indice < sizeCaixaColisaoList(); indice++) {
            caixaColisaoList.get(indice).desativarCorBorda();
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Coordenavel">
    @Override
    public void setX(double x) {
        if (importouImagem) {
            corrigeCoordenadasCaixaColisaoListOperacaoSet(getX(), getY(), x, getY());
            caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).setX(x);
            xRolagem = x;
        }        
    }

    @Override
    public void setY(double y) {
        if (importouImagem) {
            corrigeCoordenadasCaixaColisaoListOperacaoSet(getX(), getY(), getX(), y);
            caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).setY(y);
            yRolagem = y;
        }
    }

    @Override
    public void incrementaX(double aumentoX) {
        if (importouImagem) {
            corrigeCoordenadasCaixaColisaoListOperacaoIncrementa(aumentoX, 0);
            caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).incrementaX(aumentoX);
            xRolagem += aumentoX;
        }
    }

    @Override
    public void incrementaY(double aumentoY) {
        if (importouImagem) {
            corrigeCoordenadasCaixaColisaoListOperacaoIncrementa(0, aumentoY);
            caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).incrementaY(aumentoY);
            yRolagem += aumentoY;
        }  
    }

    @Override
    public void setCoordenadas(double x, double y) {
        if (importouImagem) {
            corrigeCoordenadasCaixaColisaoListOperacaoSet(getX(), getY(), x, y);
            caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).setCoordenadas(x, y);
            xRolagem = x;
            yRolagem = y;
        }  
    }
    
    @Override
    public void setCoordenadas(Coordenadas novasCoordenadas) {
        if (importouImagem) {
            corrigeCoordenadasCaixaColisaoListOperacaoSet(
                    getX(), getY(), 
                    novasCoordenadas.getX(), novasCoordenadas.getY()
            );
            
            caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).setCoordenadas(novasCoordenadas);
            xRolagem = novasCoordenadas.getX();
            yRolagem = novasCoordenadas.getY();
        }
    }
    
    @Override
    public void incrementarCoordenadas(double aumentoX, double aumentoY) {
        if (importouImagem) {
            corrigeCoordenadasCaixaColisaoListOperacaoIncrementa(aumentoX, aumentoY);
            caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).incrementarCoordenadas(aumentoX, aumentoY);
            xRolagem += aumentoX;
            yRolagem += aumentoY;
        }  
    }

    @Override
    public double getX() {
        if (importouImagem) {
            return caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).getX();
        }
        return -1.0;
    }

    @Override
    public int getIntX() {
        if (importouImagem) {
            return caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).getIntX();
        }
        return -1;
    }

    @Override
    public double getY() {
        if (importouImagem) {
            return caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).getY();
        }
        return -1.0;
    }

    @Override
    public int getIntY() {
        if (importouImagem) {
            return caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).getIntY();
        }
        return -1;
    }
    
    @Override
    public Coordenadas getCoordenadas() {
        if (importouImagem) {
            return caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA)
                    .getCoordenadas();
        }
        return null;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Movimentavel">
    @Override
    public void setVelX(double velX) {
        if (importouImagem) {
            caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).setVelX(velX);
        }
    }

    @Override
    public void setVelY(double velY) {
        if (importouImagem) {
            caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).setVelY(velY);
        }
    }

    @Override
    public void incrementaVelX(double aumentoVelX) {
        if (importouImagem) {
            caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).incrementaVelX(aumentoVelX);
        }
    }

    @Override
    public void incrementaVelY(double aumentoVelY) {
        if (importouImagem) {
            caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).incrementaVelY(aumentoVelY);
        }
    }

    @Override
    public void setVelocidades(double velX, double velY) {
        if (importouImagem) {
            caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).setVelocidades(velX, velY);
        }
    }

    @Override
    public void incrementarVelocidades(double aumentoVelX, double aumentoVelY) {
        if (importouImagem) {
            caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).incrementarVelocidades(aumentoVelX, aumentoVelY);
        }
    }

    @Override
    public double getVelX() {
        if (importouImagem) {
            return caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).getVelX();
        }
        return -1.0;
    }

    @Override
    public int getIntVelX() {
        if (importouImagem) {
            return caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).getIntVelX();
        }
        return -1;
    }

    @Override
    public double getVelY() {
        if (importouImagem) {
            return caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).getVelY();
        }
        return -1.0;
    }

    @Override
    public int getIntVelY() {
        if (importouImagem) {
            return caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).getIntVelY();
        }
        return -1;
    }

    @Override
    public void movimentar(TipoMovimento novoMovimento) {
        auxMov.setMovimentoPorTipo(novoMovimento);
        
        if (importouImagem) {
            double xAnterior = getX();
            double yAnterior = getY();            
            caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).movimentar(novoMovimento);
            corrigeCoordenadasCaixaColisaoListOperacaoSet(xAnterior, yAnterior, getX(), getY());
        }
    }
    
    @Override
    public void movimentar(double direcaoEmGraus) {
        auxMov.setMovimentoPorGrau(direcaoEmGraus);
        
        if (importouImagem) {
            double xAnterior = getX();
            double yAnterior = getY();
            caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).movimentar(direcaoEmGraus);
            corrigeCoordenadasCaixaColisaoListOperacaoSet(xAnterior, yAnterior, getX(), getY());
        }
    }
    
    @Override
    public void movimentarCurvaQuadratica(
            double alturaX, double alturaY, 
            double comprimentoX, double comprimentoY){
        auxMov.setMovimentoPorCurvaQuadratica(alturaX, alturaY, comprimentoX, comprimentoY);
        
        if (importouImagem) {
            double xAnterior = getX();
            double yAnterior = getY();
            caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA)
                    .movimentarCurvaQuadratica(alturaX, alturaY, comprimentoX, comprimentoY);
            corrigeCoordenadasCaixaColisaoListOperacaoSet(xAnterior, yAnterior, getX(), getY());
        }
    }

    @Override
    public void movimentarRepetidamente(
            double indiceDeformardorX, double indiceDeformardorY,
            double comprimento, double altura, boolean inverter
    ){
        auxMov.setMovimentoPorRepeticao(
                indiceDeformardorX, indiceDeformardorY, 
                comprimento, altura, inverter
        );
        
        if (importouImagem) {
            double xAnterior = getX();
            double yAnterior = getY();
            
            caixaColisaoList
                    .get(ImagemBitmap.CX_COLISAO_IMG_TODA)
                    .movimentarRepetidamente(
                            indiceDeformardorX, indiceDeformardorY, 
                            comprimento, altura, inverter);
            corrigeCoordenadasCaixaColisaoListOperacaoSet(xAnterior, yAnterior, getX(), getY());
        }
    }
    
    @Override
    public void movimentarPorFuncao(String funcao, boolean iniciarEmCoordNegativa){
        auxMov.setMovimentoPorFuncao(funcao, iniciarEmCoordNegativa);
        
        if (importouImagem) {
            double xAnterior = getX();
            double yAnterior = getY();
            caixaColisaoList
                    .get(ImagemBitmap.CX_COLISAO_IMG_TODA)
                    .movimentarPorFuncao(funcao, iniciarEmCoordNegativa);
            corrigeCoordenadasCaixaColisaoListOperacaoSet(xAnterior, yAnterior, getX(), getY());
        }
    }
    
    @Override
    public void pararMovimento() {
        if (importouImagem) {
            auxMov.setMovimentoNenhum();
            caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).pararMovimento();
        }
    }
    
    @Override
    public double calcularFuncao(String funcao) {
        double calculo = 0.0;
        
        if (importouImagem) {
            calculo = getCaixaColisaoImagemToda().calcularFuncao(funcao);
        }
        
        return calculo;
    }

    @Override
    public double calcularFuncao(String funcao, double intervaloIncial, double intervaloFinal) {
        double calculo = 0.0;
        
        if (importouImagem) {
            calculo = getCaixaColisaoImagemToda().calcularFuncao(funcao, intervaloIncial, intervaloFinal);
        }
        
        return calculo;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Dimensionavel">
    @Override
    public void redimensionar(double escala) {
        if (importouImagem && escala > 0) {
            int comprimento = (int)(getComprimento() * escala);
            int altura = (int)(getAltura() * escala);
            imagem = imagem.getScaledInstance(comprimento, altura, Image.SCALE_SMOOTH);
        }
    }

    @Override
    public int getComprimento() {
        if (importouImagem) {
            return imagem.getWidth(null);
        }
        return -1;
    }

    @Override
    public int getAltura() {
        if (importouImagem) {
            return imagem.getHeight(null);
        }
        return -1;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: ImagemTransformavel">
    @Override
    public void espelhar(TipoEspelhamento tipoEspelhamento){
        if (importouImagem) {
            this.tipoEspelhamento = tipoEspelhamento;
        }
    }
    
    /**
     * Ao rotacionar a imagem e movimentá-la ao mesmo tempo, sua caixa de colisão
     * principal (que cobre as dimensões da imgame)
     */
    @Override
    public void rotacionar(double graus) {
        if (importouImagem) {
            radianosRotacao = Math.toRadians(graus);
            coordRotacao.setX((getComprimento() / 2.0) + getX());
            coordRotacao.setY((getAltura() / 2.0) + getY());
        }
    }
    
    @Override
    public void rotacionar(double graus, Coordenadas pivoRotacao) {
        if (importouImagem) {
            radianosRotacao = Math.toRadians(graus);
            coordRotacao = pivoRotacao; 
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Colidivel">
    /**
     * Retorna o mesmo que getCaixaColisaoImagemToda().
     * @return CaixaColisao
     */
    @Override
    public CaixaColisao getCaixaColisao(){
        return caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA);
    }
    
    @Override
    public boolean colidiu(CaixaColisao outraCaixaColisao, TipoColisao tipoColisao) {
        if (importouImagem) {
            return caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).colidiu(outraCaixaColisao, tipoColisao);
        }
        return false;
    }

    @Override
    public LadoRetangulo colidiuNoLado(CaixaColisao outraCaixaColisao, TipoColisao tipoColisao) {
        if (importouImagem) {
            return caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).colidiuNoLado(outraCaixaColisao, tipoColisao);
        }
        return null;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
    @Override
    public void desenha(Graphics2D g2d) {
        if (importouImagem) {
            getCaixaColisaoImagemToda().desenha(g2d);
                        
            //<editor-fold defaultstate="collapsed" desc="Rotações">
            //faz os cálculos de rotação
            affineTransform.rotate(radianosRotacao, coordRotacao.getX(), coordRotacao.getY());

            //aplica os cálculos para Graphics2D
            g2d.setTransform(affineTransform);
            //</editor-fold>
        
            //<editor-fold defaultstate="collapsed" desc="Filtros">
            //aplica o tipo de efeito BICUBIC na imagem
            if (tipoFiltroExibicao == TipoFiltroExibicao.BICUBIC) {
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                        RenderingHints.VALUE_INTERPOLATION_BICUBIC
                );
            }

            //aplica o tipo de efeito BILINEAR na imagem
            if (tipoFiltroExibicao == TipoFiltroExibicao.BILINEAR) {
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR
                );
            }

            //aplica o tipo de efeito NEAREST_NEIGHBOR na imagem
            if (tipoFiltroExibicao == TipoFiltroExibicao.NEAREST_NEIGHBOR) {
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                        RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
                );
            }
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Espelhamentos">
            if (tipoEspelhamento == TipoEspelhamento.HORIZONTAL) {
                //d = destination; s = source
                g2d.drawImage(imagem,
                      // dxTop, dyTop, dxBotton, dyBotton       
                        getIntX(), getIntY(), getComprimento() + getIntX(), getAltura() + getIntY(),
                      // sxTop, syTop, sxBotton, syBotton  
                        getComprimento(), 0, 0, getAltura(),
                        null
                );
            }

            if (tipoEspelhamento == TipoEspelhamento.VERTICAL) {
                //d = destination; s = source
                g2d.drawImage(imagem,
                      // dxTop, dyTop, dxBotton, dyBotton       
                        getIntX(), getIntY(), getComprimento() + getIntX(), getAltura() + getIntY(),
                      // sxTop, syTop, sxBotton, syBotton  
                        0, getAltura(), getComprimento(), 0,
                        null
                );
            }
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Rolagens Infinitas">
            if (rolagemInfinita == TipoRolagemInfinita.HORIZONTAL) {
                //EXIBE O COMPLEMENTO DESENHO À ESQUERDA
                g2d.drawImage(
                        imagem,
                        (int)(xRolagem - getComprimento()),
                        (int)yRolagem,
                        null
                );
                
                //EXIBE O COMPLEMENTO DESENHO À DIREITA
                g2d.drawImage(
                        imagem,
                        (int)(xRolagem + getComprimento()),
                        (int)yRolagem,
                        null
                );
                
                //EXIBE O DESENHO
                g2d.drawImage(
                        imagem,
                        (int)xRolagem,
                        (int)yRolagem,
                        null
                );
            }
            
            if (rolagemInfinita == TipoRolagemInfinita.VERTICAL) {
                //EXIBE O COMPLEMENTO DESENHO À CIMA
                g2d.drawImage(
                        imagem,
                        (int)xRolagem,
                        (int)(yRolagem - getAltura()),
                        null
                );
                
                //EXIBE O COMPLEMENTO DESENHO À BAIXO
                g2d.drawImage(
                        imagem,
                        (int)xRolagem,
                        (int)(yRolagem + getAltura()),
                        null
                );
                
                //EXIBE O DESENHO
                g2d.drawImage(
                        imagem,
                        (int)xRolagem,
                        (int)yRolagem,
                        null
                );
            }
            //</editor-fold>
            
            //Imgem sem alterações
            if (
                    (tipoEspelhamento == null || 
                    tipoEspelhamento == TipoEspelhamento.NENHUM) &&
                    rolagemInfinita == TipoRolagemInfinita.NENHUMA
            ) {
                g2d.drawImage(imagem, getIntX(), getIntY(),  getComprimento(), getAltura(), null);
            }
                        
            //limpa os valores de grausRotacao, coordRotacao.getX() e coordRotacao.getY()
            radianosRotacao = 0.0;
            coordRotacao.setX(getX());
            coordRotacao.setY(getY());
        }
        else{
            g2d.setColor(Color.red);
            g2d.drawString("NÃO IMPORTOU IMAGEM! URL: " +URL, 1, 1);
        }
        
        for (int indice = 0; indice < sizeCaixaColisaoList(); indice++) {
            getCaixaColisao(indice).desenha(g2d);
        }
        
    }
    //</editor-fold>
       
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Atualizavel">
    @Override
    public void atualiza() {
        auxMov.executarMetodoMovimentoAtual(this);
        
        //<editor-fold defaultstate="collapsed" desc="Rolagens Infinitas">
        if (rolagemInfinita == TipoRolagemInfinita.HORIZONTAL) {
            xRolagem += getVelX();
        }
        
        if (rolagemInfinita == TipoRolagemInfinita.VERTICAL) {
            yRolagem += getVelY();
        }
        
        if (xRolagem > (getX() + getComprimento())) {
            xRolagem = getX();
        }
        
        if ((xRolagem + getComprimento()) < getX()) {
            xRolagem = getX() + getComprimento();
        }
        
        if (yRolagem > (getY() + getAltura())) {
            yRolagem = getY();
        }
        
        if ((yRolagem + getAltura()) < getY()) {
            yRolagem = getY() + getAltura();
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
     * Caso todos os atributos comparados sejam iguais o retorno será true, caso
     * contrário, será false.<br>
     * Atributos:
     * <ul>
     * <li>imagem</li>
     * <li>comprimento</li>
     * <li>altura</li>
     * </ul>
     * @param outraImagemBitmap A outra ImagemBitmap a ser comparada
     * @return boolean
     */
    @Override
    public boolean equals(Object outraImagemBitmap) {
        return  outraImagemBitmap instanceof ImagemBitmap &&
                ((ImagemBitmap) outraImagemBitmap).getImage().equals(getImage()) &&
                ((ImagemBitmap) outraImagemBitmap).getComprimento() == getComprimento() &&
                ((ImagemBitmap) outraImagemBitmap).getAltura() == getAltura();
    }
    
    @Override
    public String toString() {
        String linhaTopoBase = "+-----------------------+";
        String linhaMeio = "|                       |";
        String coord1 = "", coord2 = "";
        int tamanhoLinhaCoord = 23;
        
        //Preenche CAIXA DE EXIBIÇÃO
        String desenhoFinal = "  " + linhaTopoBase + "\n";
            
        coord1 = caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA)
                .getLadoCoordenadas(LadoRetangulo.ESQUERDA_CIMA)
                .toString();

        coord2 = caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA)
                .getLadoCoordenadas(LadoRetangulo.DIREITA_CIMA)
                .toString();
        
        String espacos = "";
        int restaEspacos = tamanhoLinhaCoord - (coord1.length() + coord2.length());

        for (int contEspaco = 0; contEspaco < restaEspacos; contEspaco++) {
            espacos += " ";
        }

        desenhoFinal +=
                "  |" + coord1 + espacos + coord2 + "|\n" + 
                "  " + linhaMeio + "\n" + 
                "  " + linhaMeio + "\n" + 
                "  " + linhaMeio + "\n" +
                "  " + linhaMeio + "\n" + 
                "  " + linhaMeio + "\n" + 
                "  " + linhaMeio + "\n" +
                "  " + linhaMeio + "\n"
        ;

        coord1 = caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).getLadoCoordenadas(LadoRetangulo.ESQUERDA_BAIXO)
                .toString();

        coord2 = caixaColisaoList.get(ImagemBitmap.CX_COLISAO_IMG_TODA).getLadoCoordenadas(LadoRetangulo.DIREITA_BAIXO)
                .toString();
        
        espacos = "";
        restaEspacos = tamanhoLinhaCoord - (coord1.length() + coord2.length());

        for (int contEspaco = 0; contEspaco < restaEspacos; contEspaco++) {
            espacos += " ";
        }
        
        desenhoFinal += 
                "  |" + coord1 + espacos + coord2 + "|\n" + 
                "  " + linhaTopoBase;
        
        String cxColisoes = "";
        for (int indice = 0; indice < caixaColisaoList.size(); indice++) {
            cxColisoes += caixaColisaoList.get(indice).toString() + "\n";
        }
        
        //Imprime Desenho final
        return  "ImagemBitmap{\n" +
                "  dimensões: " + getComprimento() + "x" + getAltura() + " px\n" +
                "  codigo: " + codigo + "\n" + 
                "  bloqueou imagem? " + (bloqueada ? "sim" : "não") + "\n" +
                "  velX: " + getVelX() + "px, velY: " + getVelY() + "px\n\n" +
                desenhoFinal + "\n" +
                "  importou imagem? " + (importouImagem ? "sim" : "não") + "\n" +
                cxColisoes +
                "}"
        ;
    }
    //</editor-fold>
    
}
