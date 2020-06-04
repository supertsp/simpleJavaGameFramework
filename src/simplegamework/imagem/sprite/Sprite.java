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

import simplegamework.imagem.TipoRolagemInfinita;
import simplegamework.objetoBasico.Coordenadas;
import simplegamework.objetoBasico.LadoRetangulo;
import simplegamework.padraoProjeto.Coordenavel;
import simplegamework.padraoProjeto.Desenhavel;
import simplegamework.padraoProjeto.ListManuseavel;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * Um Sprite consiste numa sequência lógica de imagens com Coordenadas pivô 
 * de alinhamento em cada uma no intuito de servirem de base para animação de 
 * personagens ou de elementos em cena, no entanto, o Sprite deve ter um 
 * identificador de ação para que fique melhor organizado cada sprite.<br>
 * Obs.: Esta classe funciona como um ArrayList de SpriteTile.
 * <br><br><small>Created on : 01/05/2015, 22:18:55</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class Sprite implements Coordenavel, ListManuseavel<SpriteTile>, Desenhavel{

    protected Object nomeAcao;
    protected ArrayList<SpriteTile> sprite;
    
    //Auxiliares
    private int indiceAtual;
    
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    /**
     * Construtor básico na qual deve ser passado um objeto qualquer que represente
     * o nome da ação do sprite. Esse objeto pode ser desde uma classe comum,
     * um String e até um enum.
     * @param nomeAcao O nome da ação que representa este sprite
     */
    public Sprite(Object nomeAcao) {
        this.nomeAcao = nomeAcao;
        sprite = new ArrayList<>();
    }
    
    /**
     * Construtor completo na qual deve ser passado um objeto qualquer que 
     * represente o nome da ação do sprite e vários SpriteTile. O objeto  nomeAcao
     * pode ser desde uma classe comum, um String e até um enum.<br>
     * Obs.: O primeiro SpriteTile adicionado será tido como base para alinhar 
     * todos os demais SpriteTile adcionados. Esse construtor adiciona cada
     * elemento por cópia, ou seja, chama o método interno addCopy().
     * @param nomeAcao O nome da ação que representa este sprite
     * @param variosElementos Os novos elementos. Ex.: 
     * new Sprite("andando", elemento1, elemento2, ..., elementoN);
     */
    public Sprite(Object nomeAcao, SpriteTile... variosElementos) {
        this(nomeAcao);
        for (int cont = 0; cont < variosElementos.length; cont++) {
            addCopy(variosElementos[cont]);
        }        
    }
    //</editor-fold>
        
    /**
     * Retorna o objeto que representa o nome da ação desse sprite
     * @return Object
     */
    public Object getNomeAcao() {
        return nomeAcao;
    }
    
    /**
     * Adiciona um novo elemento ao ListManuseavel, no entanto, esse novo 
     * elemento já existia dentro do ListManuseavel, sendo assim, se as 
     * Coordenadas do primeiro fossem alteradas, fatalmente do novo elemento 
     * também seriam; ou seja, esse método copia os atributos básicos do 
     * elemento passada por parâmetro e cria uma nova instância com os atributos
     * copiados, assim ambos tornam-se independentes uns dos outros.
     * @param spriteTileParaCopiar O novo SpriteTile a ser copiado e adicionado
     */
    public void addCopy(SpriteTile spriteTileParaCopiar) {
        SpriteTile copia = spriteTileParaCopiar.getCopy();        
        sprite.add(copia);
    }
    
    /**
     * Altera a rolagemInfinita de SpriteTile no Sprite, ou seja, marca se todas
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
     * Retorna o valor da rolagemInfinita apenas do primeiro SpriteTile no Sprite.
     * @return TipoRolagemInfinita
     */
    public TipoRolagemInfinita getRolagemInfinita(){
        return get(0).getRolagemInfinita();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: PivoAlinhamento">
    /**
     * Ajusta automaticamente todas as Coordenadas de todos os SpriteTile pelo
     * pivoAlinhamento do primeiro SpriteTile adicionado ao Sprite.<br>
     * Obs.: Este método é chamdado a cada add() ou remove(), no entando toda
     * vez que as Coordenadas de posição ou do pivoAlinhamento do primeiro tile
     * for alterada esse método deve ser invocado manualmente.
     */
    public void ajustarTodosSpriteTilePeloPivoAlinhamento(){
        if (size() >= 2) {
            double xPivo = get(0).getXPivoAlinhamento();
            double yPivo = get(0).getYPivoAlinhamento();
            double difX, difY;
            
            for (int cont = 1; cont < size(); cont++) {
                difX = get(cont).getXPivoAlinhamento() - get(cont).getX();
                difY = get(cont).getYPivoAlinhamento() - get(cont).getY();
                get(cont).setCoordenadas(xPivo - difX, yPivo - difY);
            }
        }
    }
    
    /**
     * Ativa a exibição do desenho do pivoAlinhamento em todos os SpriteTile.
     */
    public void ativarExibicaoPivoAlinhamentoTodosSpriteTile(){
        for (int cont = 0; cont < size(); cont++) {
            get(cont).ativarExibicaoPivoAlinhamento();
        }
    }
    
    /**
     * Desativa a exibição do desenho do pivoAlinhamento em todos os SpriteTile.
     */
    public void desativarExibicaoPivoAlinhamentoTodosSpriteTile(){
        for (int cont = 0; cont < size(); cont++) {
            get(cont).desativarExibicaoPivoAlinhamento();
        }
    }
    
    /**
     * Retorna o estado da exibição do desenho do pivoAlinhamento em todos os 
     * SpriteTile, ou seja, todos precisam ser true para retornar true.
     * @return boolean
     */
    public boolean isAtivaExibicaoPivoAlinhamentoTodosSpriteTile(){
        boolean ativo = false;
        for (int cont = 0; cont < size(); cont++) {
            if (!get(cont).isAtivaExibicaoPivoAlinhamento()) {
                return false;
            }
            else{
                ativo = true;
            }
        }
        
        return ativo;
    }
    
    /**
     * Posiciona automativamente o pivoAlinhamento no meio do lado inferior do 
     * tile em relação a ele e não à tela.
     */
    public void posicionarPivoAlinhamentoAoCentroInferiorTodosSpriteTile(){
        for (int cont = 0; cont < size(); cont++) {
            get(cont).posicionarPivoAlinhamentoAoCentroInferiorDoTile();
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Coordenavel">
    @Override
    public void setX(double x) {
        for (int cont = 0; cont < size(); cont++) {
            get(cont).setX(x);
        }
        ajustarTodosSpriteTilePeloPivoAlinhamento();
    }

    @Override
    public void setY(double y) {
        for (int cont = 0; cont < size(); cont++) {
            get(cont).setY(y);
        }
        ajustarTodosSpriteTilePeloPivoAlinhamento();
    }

    @Override
    public void incrementaX(double aumentoX) {
        for (int cont = 0; cont < size(); cont++) {
            get(cont).incrementaX(aumentoX);
        }
        ajustarTodosSpriteTilePeloPivoAlinhamento();
    }

    @Override
    public void incrementaY(double aumentoY) {
        for (int cont = 0; cont < size(); cont++) {
            get(cont).incrementaY(aumentoY);
        }
        ajustarTodosSpriteTilePeloPivoAlinhamento();
    }

    @Override
    public void setCoordenadas(double x, double y) {
        for (int cont = 0; cont < size(); cont++) {
            get(cont).setCoordenadas(x, y);
        }
        ajustarTodosSpriteTilePeloPivoAlinhamento();
    }
    
    @Override
    public void setCoordenadas(Coordenadas novasCoordenadas) {
        for (int cont = 0; cont < size(); cont++) {
            get(cont).setCoordenadas(novasCoordenadas);
        }
        ajustarTodosSpriteTilePeloPivoAlinhamento();
    }
    

    @Override
    public void incrementarCoordenadas(double aumentoX, double aumentoY) {
        for (int cont = 0; cont < size(); cont++) {
            get(cont).incrementarCoordenadas(aumentoX, aumentoY);
        }
        ajustarTodosSpriteTilePeloPivoAlinhamento();
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
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: ListManuseavel">
    @Override
    public void add(SpriteTile novoElemento) {
        if (size() >= 1) {
            sprite.add(novoElemento);
            ajustarTodosSpriteTilePeloPivoAlinhamento();
        }
        else{
            sprite.add(novoElemento);
        }
    }

    @Override
    public void add(SpriteTile... variosElementos) {
        if (size() >= 1) {
            sprite.addAll(Arrays.asList(variosElementos));
            ajustarTodosSpriteTilePeloPivoAlinhamento();
        }
        else{
            if (variosElementos.length >= 2) {
                sprite.addAll(Arrays.asList(variosElementos));
                ajustarTodosSpriteTilePeloPivoAlinhamento();
            }
            else{
                sprite.add(variosElementos[0]);
            }
        }
    }

    @Override
    public SpriteTile get(int indice) {
        return sprite.get(indice);
    }

    @Override
    public SpriteTile getNext() {
        SpriteTile temp = sprite.get(indiceAtual);
        indiceAtual++;
        
        if (indiceAtual >= size()) {
            indiceAtual = 0;
            return null;
        }
        
        return temp;
    }

    @Override
    public void remove(int indice) {
        if (indice == 0) {
            sprite.remove(indice);
            ajustarTodosSpriteTilePeloPivoAlinhamento();
        }
        else{
            sprite.remove(indice);
        }
    }

    @Override
    public void remove(SpriteTile elementoParaRemover) {
        if (elementoParaRemover.equals(get(0))) {
            sprite.remove(0);
            ajustarTodosSpriteTilePeloPivoAlinhamento();
        }
        else{
            sprite.remove(elementoParaRemover);
        }
    }

    @Override
    public int size() {
        return sprite.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Desenhavel">
    @Override
    public void desenha(Graphics2D g2d) {
        for (int cont = size() - 1; cont >= 0; cont--) {
            get(cont).desenha(g2d);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Object">
    /**
     * Desenha todos os SpriteTile com Strings para finalidades de debug.
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
        
        for (int cont = 0; cont < size(); cont++) {
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
        
        //Preenche COORDENADAS SUPERIORES
        for (int indice = 0; indice < size(); indice++) {
            coord1 = get(indice)
                    .getCaixaColisao(0)
                    .getLadoCoordenadas(LadoRetangulo.ESQUERDA_CIMA)
                    .toString();

            coord2 = get(indice)
                    .getCaixaColisao(0)
                    .getLadoCoordenadas(LadoRetangulo.DIREITA_CIMA)
                    .toString();

            String espacos = "";
            int restaEspacos = tamanhoLinhaCoord - (coord1.length() + coord2.length());

            for (int cont = 0; cont < restaEspacos; cont++) {
                espacos += " ";
            }

            linhaCoordFinal += "|" + coord1 + espacos + coord2 + "|";
        }//superiores

        linhaCoordFinal += "\n";
        desenhoFinal += linhaCoordFinal + linhaMeioCima;
        linhaCoordFinal = "";

        //Preenche INFO DO MEIO DIMENSÕES
        for (int indice = 0; indice < size(); indice++) {//coluna
            String info = 
                    indice + " : " + 
                    get(indice).getComprimento() + "x" +
                    get(indice).getAltura() + "px";

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
        }//info meio - dimensões

        desenhoFinal += linhaMeioInfo + "\n";
        linhaMeioInfo = "";
        
        //Preenche INFO DO MEIO PIVO
        for (int indice = 0; indice < size(); indice++) {//coluna
            String info = 
                    "+ (" + 
                    get(indice).getIntXPivoAlinhamento() + ", " +
                    get(indice).getIntYPivoAlinhamento() + ") : " +
                    get(indice).isAtivaExibicaoPivoAlinhamento();

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
        }//info meio pivo

        desenhoFinal += linhaMeioInfo + "\n" + linhaMeioBaixo;
        linhaMeioInfo = "";

        //Preenche COORDENADAS INFERIORES
        for (int indice = 0; indice < size(); indice++) {//coluna            
            coord1 = get(indice)
                    .getCaixaColisao(0)
                    .getLadoCoordenadas(LadoRetangulo.ESQUERDA_BAIXO)
                    .toString();

            coord2 = get(indice)
                    .getCaixaColisao(0)
                    .getLadoCoordenadas(LadoRetangulo.DIREITA_BAIXO)
                    .toString();

            String espacos = "";
            int restaEspacos = tamanhoLinhaCoord - (coord1.length() + coord2.length());

            for (int cont = 0; cont < restaEspacos; cont++) {
                espacos += " ";
            }

            linhaCoordFinal += "|" + coord1 + espacos + coord2 + "|";
        }//inferiores

        linhaCoordFinal += "\n";
        desenhoFinal += linhaCoordFinal + linhaTopoBaseFinal;
        linhaCoordFinal = "";
        
                        
        return
            "Sprite : " + getNomeAcao() + "{\n" +
            "  - dimesões: " + size() + " tile(s)\n" +
            desenhoFinal + 
            "Obs.: o símb. + é um sinônimo para pivoAlinhamento e o booleando " +
            "indica se o pivoAlinhamento deve ser exibido.\n" +
            "}\n";
    }
    //</editor-fold>

}
