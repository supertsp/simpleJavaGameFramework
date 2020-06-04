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

package simplegamework.texto;

import simplegamework.padraoProjeto.Atualizavel;
import simplegamework.padraoProjeto.Dimensionavel;
import simplegamework.padraoProjeto.Movimentavel;
import simplegamework.padraoProjeto.AtualizavelListManuseavel;
import simplegamework.padraoProjeto.Colidivel;
import simplegamework.padraoProjeto.Desenhavel;
import simplegamework.padraoProjeto.Coordenavel;
import simplegamework.cor.CorRGBA;
import simplegamework.ferramenta.AtualizavelList;
import simplegamework.ferramenta.AuxiliaresMovimentavel;
import simplegamework.objetoBasico.CaixaColisao;
import simplegamework.objetoBasico.Coordenadas;
import simplegamework.objetoBasico.LadoRetangulo;
import simplegamework.objetoBasico.TipoColisao;
import simplegamework.objetoBasico.TipoMovimento;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Um Texto pode ser desenhado com bordas, ter cores com transparência, ser
 * movimentado e alterado outros atributos básicos.
 * <br><br><small>Created on : 27/07/2015, 20:42:49</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class Texto implements Coordenavel, Movimentavel, Dimensionavel, 
        Colidivel, Desenhavel, Atualizavel, AtualizavelListManuseavel{
    
    protected Coordenadas coord;
    private String texto;
    protected Shape textoVetor;
    protected double tamanho;
    private double espessuraBorda;
    private CorRGBA corTexto;
    private CorRGBA corBorda;
    protected String URLFonte;
    protected Font fonte;
    protected boolean filtroExibicaoAtivo;
    protected CaixaColisao caixaColisao;
    
    //Auxiliares
    private TextLayout textLayout;
    private AuxiliaresMovimentavel auxMov;
    private AtualizavelList atualizaveis;
    
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    /**
     * Construtor que inicializa tanto a espessuraBorda quanto a corBorda.
     * @param x A coordenada x inicial
     * @param y A coordenada y inicial
     * @param texto O texto a ser exibido
     * @param tamanho O tamanho da Fonte
     * @param corTexto A cor do texto
     * @param espessuraBorda A espessura da borda
     * @param corBorda A cor da borda
     */
    public Texto(double x, double y, String texto,  double tamanho, 
            CorRGBA corTexto, double espessuraBorda, CorRGBA corBorda){
        coord = new Coordenadas(x, y);
        this.texto = texto;
        this.tamanho = tamanho > 7 ? tamanho : 7;
        this.corTexto = corTexto;
        this.espessuraBorda = espessuraBorda >= 0 ? espessuraBorda : 0;
        this.corBorda = corBorda;
        
        fonte = new Font("Arial", Font.PLAIN, (int)this.tamanho);
        
        initCaixaColisao();
        
        //Auxiliares
        auxMov = new AuxiliaresMovimentavel();
        atualizaveis = new AtualizavelList();
    }
    
    /**
     * Construtor padrão.
     * @param x A coordenada x inicial
     * @param y A coordenada y inicial
     * @param texto O texto a ser exibido
     * @param tamanho O tamanho da Fonte
     * @param corTexto A cor do texto
     */
    public Texto(double x, double y, String texto, double tamanho, CorRGBA corTexto){
        this(x, y, texto, tamanho, corTexto, 0, null);
    }
    
    /**
     * Construtor para importar um arquivo de Font externa não instalada no S.O..
     * @param x A coordenada x inicial
     * @param y A coordenada y inicial
     * @param texto O texto a ser exibido
     * @param tamanho O tamanho da Fonte
     * @param corTexto A cor do texto
     * @param URLFonte O endereço do arquivo de Fonte. Obs.: A extensão do arquivo
     * precisa ser .ttf
     */
    public Texto(double x, double y, String texto,  double tamanho, CorRGBA corTexto, String URLFonte){
        this(x, y, texto, tamanho, corTexto);
        this.URLFonte = URLFonte;
        initFonteURL();
        initCaixaColisao();
    }    
    
    /**
     * Construtor completo.
     * @param x A coordenada x inicial
     * @param y A coordenada y inicial
     * @param texto O texto a ser exibido
     * @param tamanho O tamanho da Fonte
     * @param corTexto A cor do texto
     * @param espessuraBorda A espessura da borda
     * @param corBorda A cor da borda
     * @param URLFonte O endereço do arquivo de Fonte. Obs.: A extensão do arquivo
     * precisa ser .ttf
     */
    public Texto(double x, double y, String texto,  double tamanho, CorRGBA corTexto, double espessuraBorda, CorRGBA corBorda, String URLFonte){
        this(x, y, texto, tamanho, corTexto, espessuraBorda, corBorda);
        this.URLFonte = URLFonte;
        initFonteURL();
        initCaixaColisao();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: Auxiliares">
    /**
     * Inicializa a CaixaColisao e o TextLayout afim de se obter as dimesões do 
     * texto escrito na tela.
     */
    private void initCaixaColisao(){
        Graphics2D g2d = (Graphics2D)new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR).getGraphics();
        
        //Converte uma String para TextLayout
        textLayout = new TextLayout(texto, fonte, g2d.getFontRenderContext());
        
        //Desenha uma caixa em torno do texo, bom para descobrir as dimensões dele        
        Rectangle limitesTexto = textLayout.getPixelBounds(g2d.getFontRenderContext(), (float)getX(), (float)getY());
        
        //Inicializa a CaixaColisao
        caixaColisao = new CaixaColisao(
                limitesTexto.x - espessuraBorda / 2, 
                limitesTexto.y - espessuraBorda / 2, 
                (int)(limitesTexto.width + espessuraBorda), 
                (int)(limitesTexto.height + espessuraBorda)
        );
    }
    
    /**
     * Importa um arquivo de Fonte.
     */
    private void initFonteURL(){
        if (URLFonte.substring(URLFonte.length() - 3).contains("ttf")) {
            try {
                fonte = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(URLFonte));
                fonte = fonte.deriveFont(Font.PLAIN, (int)tamanho);
            } catch (Exception e) {System.out.println("aki");}
        }
         else{
            System.out.println(
                    "+-------------------------------+\n" +
                    "| Extensão de arquivo inválida! |\n" +
                    "| Extensão permitada: ttf       |\n" +
                    "+-------------------------------+\n"
            );
        }        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: GETs & SETs">
    /**
     * Retorna o texto a ser desenhado.
     * @return String
     */
    public String getTexto() {
        return texto;
    }
    
    /**
     * Altera o novoTexto a ser desenhado. Obs.: Este método pode custar desempenho
     * do jogo.
     * @param novoTexto O novo novoTexto
     */
    public void setTexto(String novoTexto) {
        texto = novoTexto;
        initCaixaColisao();
    }
    
    /**
     * Retorna a espessura da borda.
     * @return double
     */
    public double getEspessuraBorda() {
        return espessuraBorda;
    }
    
    /**
     * Altera o valor da espessura da borda. Obs.: Este método pode custar desempenho
     * do jogo.
     * @param novaEspessuraBorda A nova espessura da borda
     */
    public void setEspessuraBorda(double novaEspessuraBorda) {
        espessuraBorda = novaEspessuraBorda;
    }
    
    /**
     * Retorna a cor do texto.
     * @return CorRGBA
     */
    public CorRGBA getCorTexto() {
        return corTexto;
    }
    
    /**
     * Altera a cor do texto.
     * @param novaCorTexto A nova cor a ser atribuida
     */
    public void setCorTexto(CorRGBA novaCorTexto) {
        corTexto = novaCorTexto;
    }
    
    /**
     * Retorna a cor da borda.
     * @return CorRGBA
     */
    public CorRGBA getCorBorda() {
        return corBorda;
    }
    
    /**
     * Altera a cor da borda.
     * @param novaCorBorda A nova cor da borda
     */
    public void setCorBorda(CorRGBA novaCorBorda) {
        corBorda = novaCorBorda;
    }    
    
    /**
     * Ativa o filtro Antialiasing quando o texto é desenhado.
     */
    public void ativarFiltroExibicao(){
        filtroExibicaoAtivo = true;
    }
    
    /**
     * Desativa o filtro Antialiasing quando o texto é desenhado.
     */
    public void desativarFiltroExibicao(){
        filtroExibicaoAtivo = false;
    }
    
    /**
     * Retorna o estado do filtro de exibição.
     * @return boolean
     */
    public boolean isFiltroExibicaoAtivo(){
        return filtroExibicaoAtivo;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Coordenavel">
    @Override
    public void setX(double x) {
        coord.setX(x);
    }

    @Override
    public void setY(double y) {
        coord.setY(y);
    }

    @Override
    public void incrementaX(double aumentoX) {
        coord.incrementaX(aumentoX);
    }

    @Override
    public void incrementaY(double aumentoY) {
        coord.incrementaY(aumentoY);
    }

    @Override
    public void setCoordenadas(double x, double y) {
        coord.setCoordenadas(x, y);
    }
    
    @Override
    public void setCoordenadas(Coordenadas novasCoordenadas) {
        coord.setCoordenadas(novasCoordenadas);
    }
    
    @Override
    public void incrementarCoordenadas(double aumentoX, double aumentoY) {
        coord.incrementarCoordenadas(aumentoX, aumentoY);
    }

    @Override
    public double getX() {
        return coord.getX();
    }

    @Override
    public int getIntX() {
        return coord.getIntX();
    }

    @Override
    public double getY() {
        return coord.getY();
    }

    @Override
    public int getIntY() {
        return coord.getIntY();
    }
    
    @Override
    public Coordenadas getCoordenadas() {
        return coord;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Movimentavel">
    @Override
    public void setVelX(double velX) {
        coord.setVelX(velX);
    }

    @Override
    public void setVelY(double velY) {
        coord.setVelY(velY);
    }

    @Override
    public void incrementaVelX(double aumentoVelX) {
        coord.incrementaVelX(aumentoVelX);
    }

    @Override
    public void incrementaVelY(double aumentoVelY) {
        coord.incrementaVelY(aumentoVelY);
    }

    @Override
    public void setVelocidades(double velX, double velY) {
        coord.setVelocidades(velX, velY);
    }

    @Override
    public void incrementarVelocidades(double aumentoVelX, double aumentoVelY) {
        coord.incrementarVelocidades(aumentoVelX, aumentoVelY);
    }

    @Override
    public double getVelX() {
        return coord.getVelX();
    }

    @Override
    public int getIntVelX() {
        return coord.getIntVelX();
    }

    @Override
    public double getVelY() {
        return coord.getVelY();
    }

    @Override
    public int getIntVelY() {
        return coord.getIntVelY();
    }

    @Override
    public void movimentar(TipoMovimento novoMovimento) {
        auxMov.setMovimentoPorTipo(novoMovimento);        
        coord.movimentar(novoMovimento);
    }

    @Override
    public void movimentar(double direcaoEmGraus) {
        auxMov.setMovimentoPorGrau(direcaoEmGraus);        
        coord.movimentar(direcaoEmGraus);
    }

    @Override
    public void movimentarCurvaQuadratica(
            double alturaX, double alturaY, 
            double comprimentoX, double comprimentoY
    ) {
        auxMov.setMovimentoPorCurvaQuadratica(
                alturaX, alturaY, comprimentoX, comprimentoY);
        coord.movimentarCurvaQuadratica(alturaX, alturaY, comprimentoX, comprimentoY);
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
        coord.movimentarRepetidamente(indiceDeformardorX, indiceDeformardorY, 
                comprimento, altura, inverter);
    }

    @Override
    public void movimentarPorFuncao(String funcao, boolean iniciarEmCoordNegativa) {
        auxMov.setMovimentoPorFuncao(funcao, iniciarEmCoordNegativa);
        coord.movimentarPorFuncao(funcao, iniciarEmCoordNegativa);
    }

    @Override
    public void pararMovimento() {
        auxMov.setMovimentoNenhum();
        coord.pararMovimento();
    }

    @Override
    public double calcularFuncao(String funcao) {
        return coord.calcularFuncao(funcao);
    }

    @Override
    public double calcularFuncao(String funcao, double intervaloIncial, double intervaloFinal) {
        return coord.calcularFuncao(funcao, intervaloIncial, intervaloFinal);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Dimensionavel">
    @Override
    public void redimensionar(double escala) {
        fonte = new Font("Arial", Font.PLAIN, (int)(tamanho * escala));
        initCaixaColisao();
    }

    @Override
    public int getComprimento() {
        return caixaColisao.getComprimento();
    }

    @Override
    public int getAltura() {
        return caixaColisao.getAltura();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Colidivel">
    @Override
    public CaixaColisao getCaixaColisao(){
        return caixaColisao;
    }
    
    @Override
    public boolean colidiu(CaixaColisao outraCaixaColisao, TipoColisao tipoColisao) {
        return caixaColisao.colidiu(outraCaixaColisao, tipoColisao);
    }

    @Override
    public LadoRetangulo colidiuNoLado(CaixaColisao outraCaixaColisao, TipoColisao tipoColisao) {
        return caixaColisao.colidiuNoLado(outraCaixaColisao, tipoColisao);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
    @Override
    public void desenha(Graphics2D g2d) {
        //Como a fonte não é desenhada exatamente na posição pedida, precisa de um ajuste
        int ajuste = 27;
        
        //Habilita o filtro exibição
        if (isFiltroExibicaoAtivo()) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        //Aplica o tipo de fonte escolhida
        g2d.setFont(fonte);
        
        //Aplica transformação de posição para posicionar o texto no local correto
        AffineTransform tranformacao = new AffineTransform();
        tranformacao.setToTranslation(getX(), getY() + ajuste);
        
        //Transforma o texto especial em vetor
        textoVetor = textLayout.getOutline(tranformacao);
        
        //Existe Borda?
        if (corBorda != null) {
            BasicStroke contorno = new BasicStroke((float)espessuraBorda);
            g2d.setStroke(contorno);
            g2d.setColor(corBorda.getColor());
            g2d.draw(textoVetor);
        }
        
        //Existe texto?
        if (corTexto != null) {
            g2d.setStroke(new BasicStroke(0));
            g2d.setColor(corTexto.getColor());
            g2d.fill(textoVetor);
        }
        
        //Desabilita o filtro exibição para não interferir nos desenhos de outros objetos
        if (isFiltroExibicaoAtivo()) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        }
    }
    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Atualizavel">
    @Override
    public void atualiza() {
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
