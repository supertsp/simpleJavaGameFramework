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

package simplegamework.ferramenta;

import simplegamework.objetoBasico.TipoMovimento;
import simplegamework.padraoProjeto.Movimentavel;

/**
 * Toda vez que se implementa Movimentavel é necessário guardar os valores passados
 * nos métodos de movimento afim de serem passados novamente dentro do método 
 * atualiza(), da interface Atualizavel, pois caso contrário o movimento não 
 * acontece.
 * <br><br><small>Created on : 11/07/2015, 12:03:30</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class AuxiliaresMovimentavel{
    
    private Alternador<MetodoMovimentoAtual> metodoChamado;
    private TipoMovimento movimentoPorTipo;
    private double movimentoPorGrau;
    private MovimentoPorCurvaQuadratica movimentoPorCurvaQuadratica;
    private MovimentoPorRepeticao movimentoPorRepeticao;
    private MovimentoPorFuncao movimentoPorFuncao;
    
    public enum MetodoMovimentoAtual{
        NENHUM, TIPO, GRAU, CURVA_QUADRATICA, REPETIDAMENTE, FUNCAO
    }
    
    //<editor-fold defaultstate="collapsed" desc="Construtor">
    /**
     * Construtor padrão de classe.
     */
    public AuxiliaresMovimentavel(){
        metodoChamado = new Alternador<>(
                    MetodoMovimentoAtual.NENHUM,
                    MetodoMovimentoAtual.TIPO, 
                    MetodoMovimentoAtual.GRAU, 
                    MetodoMovimentoAtual.CURVA_QUADRATICA, 
                    MetodoMovimentoAtual.REPETIDAMENTE, 
                    MetodoMovimentoAtual.FUNCAO
        );
        
        metodoChamado.mudarEstadoPara(MetodoMovimentoAtual.NENHUM);
        
        movimentoPorCurvaQuadratica = new MovimentoPorCurvaQuadratica(0, 0, 0, 0);
        movimentoPorRepeticao = new MovimentoPorRepeticao(0, 0, 0, 0, false);
        movimentoPorFuncao = new MovimentoPorFuncao("", false);
    }
    //</editor-fold>
    
    /**
     * Retorna o MetodoMovimentavel que está em uso.
     * @return MetodoMovimentavel
     */
    public MetodoMovimentoAtual getMetodoMovimentavelAtual(){
        return metodoChamado.getEstadoAtual();
    }
    
    /**
     * Util para chamar diretamente o tipo de movimento escolhido, ou também,
     * para parar um movimento. Sendo assim, economiza-se vários ifs. Esse
     * método geralmente é chamado dentro de um atualiza(), pois a cada atualização
     * é necessário validar o tipo de movimento que está em execução para que
     * ele continue sendo realizado.
     * @param objeto O objeto que deseja executar/parar um movimento
     */
    public void executarMetodoMovimentoAtual(Movimentavel objeto){
        if (getMetodoMovimentavelAtual() == MetodoMovimentoAtual.TIPO) {
            objeto.movimentar(getMovimentoPorTipo());
        }
        
        if (getMetodoMovimentavelAtual() == MetodoMovimentoAtual.GRAU) {
            objeto.movimentar(getMovimentoPorGrau());
        }
        
        if (getMetodoMovimentavelAtual() == MetodoMovimentoAtual.CURVA_QUADRATICA) {
            objeto.movimentarCurvaQuadratica(
                    getMovimentoPorCurvaQuadratica().alturaX, 
                    getMovimentoPorCurvaQuadratica().alturaY, 
                    getMovimentoPorCurvaQuadratica().comprimentoX, 
                    getMovimentoPorCurvaQuadratica().comprimentoY
            );
        }
        
        if (getMetodoMovimentavelAtual() == MetodoMovimentoAtual.REPETIDAMENTE) {
            objeto.movimentarRepetidamente(
                    getMovimentoPorRepeticao().indiceDeformardorX, 
                    getMovimentoPorRepeticao().indiceDeformardorY, 
                    getMovimentoPorRepeticao().alturaMovimento, 
                    getMovimentoPorRepeticao().alturaMovimento, 
                    getMovimentoPorRepeticao().inverter
            );
        }
        
        if (getMetodoMovimentavelAtual() == MetodoMovimentoAtual.FUNCAO) {
            objeto.movimentarPorFuncao(
                    getMovimentoPorFuncao().funcao, 
                    getMovimentoPorFuncao().iniciarEmCoordNegativa
            );
        }
        
        if (getMetodoMovimentavelAtual() == MetodoMovimentoAtual.NENHUM) {
            objeto.pararMovimento();
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Método: Reinicia Atributos">
    /**
     * Reinicia os atributos auxiliares para realizar os movimentos
     * @param metodoExecucao Método que está em execução no momento
     */
    private void reiniciarAuxiliaresMovimentavel(MetodoMovimentoAtual metodoExecucao){
        if (metodoExecucao == MetodoMovimentoAtual.NENHUM) {
            //movimento por tipo
            movimentoPorTipo = null;
            
            //movimento por grau
            movimentoPorGrau = 0;
            
            //movimento por curva quadratica
            movimentoPorCurvaQuadratica.alturaX = 0;
            movimentoPorCurvaQuadratica.alturaY = 0;
            movimentoPorCurvaQuadratica.comprimentoX = 0;
            movimentoPorCurvaQuadratica.comprimentoY = 0;
            
            //movimentarRepetidamente
            movimentoPorRepeticao.indiceDeformardorX = 0;
            movimentoPorRepeticao.indiceDeformardorY = 0;
            movimentoPorRepeticao.comprimentoMovimento = 0;
            movimentoPorRepeticao.alturaMovimento = 0;
            movimentoPorRepeticao.inverter = false;
            
            //movimentarPorFuncao
            movimentoPorFuncao.funcao = "";
            movimentoPorFuncao.iniciarEmCoordNegativa = false;
        }
        
        if (metodoExecucao == MetodoMovimentoAtual.TIPO) {
            //movimento por grau
            movimentoPorGrau = 0;
            
            //movimento por curva quadratica
            movimentoPorCurvaQuadratica.alturaX = 0;
            movimentoPorCurvaQuadratica.alturaY = 0;
            movimentoPorCurvaQuadratica.comprimentoX = 0;
            movimentoPorCurvaQuadratica.comprimentoY = 0;
            
            //movimentarRepetidamente
            movimentoPorRepeticao.indiceDeformardorX = 0;
            movimentoPorRepeticao.indiceDeformardorY = 0;
            movimentoPorRepeticao.comprimentoMovimento = 0;
            movimentoPorRepeticao.alturaMovimento = 0;
            movimentoPorRepeticao.inverter = false;
            
            //movimentarPorFuncao
            movimentoPorFuncao.funcao = "";
            movimentoPorFuncao.iniciarEmCoordNegativa = false;
        }
        
        if (metodoExecucao == MetodoMovimentoAtual.GRAU) {
            //movimento por tipo
            movimentoPorTipo = null;
            
            //movimento por curva quadratica
            movimentoPorCurvaQuadratica.alturaX = 0;
            movimentoPorCurvaQuadratica.alturaY = 0;
            movimentoPorCurvaQuadratica.comprimentoX = 0;
            movimentoPorCurvaQuadratica.comprimentoY = 0;
            
            //movimentarRepetidamente
            movimentoPorRepeticao.indiceDeformardorX = 0;
            movimentoPorRepeticao.indiceDeformardorY = 0;
            movimentoPorRepeticao.comprimentoMovimento = 0;
            movimentoPorRepeticao.alturaMovimento = 0;
            movimentoPorRepeticao.inverter = false;
            
            //movimentarPorFuncao
            movimentoPorFuncao.funcao = "";
            movimentoPorFuncao.iniciarEmCoordNegativa = false;
        }
        
        if (metodoExecucao == MetodoMovimentoAtual.CURVA_QUADRATICA) {
            //movimento por tipo
            movimentoPorTipo = null;
            
            //movimento por grau
            movimentoPorGrau = 0;
            
            //movimentarRepetidamente
            movimentoPorRepeticao.indiceDeformardorX = 0;
            movimentoPorRepeticao.indiceDeformardorY = 0;
            movimentoPorRepeticao.comprimentoMovimento = 0;
            movimentoPorRepeticao.alturaMovimento = 0;
            movimentoPorRepeticao.inverter = false;
            
            //movimentarPorFuncao
            movimentoPorFuncao.funcao = "";
            movimentoPorFuncao.iniciarEmCoordNegativa = false;
        }
        
        if (metodoExecucao == MetodoMovimentoAtual.REPETIDAMENTE) {
            //movimento por tipo
            movimentoPorTipo = null;
            
            //movimento por grau
            movimentoPorGrau = 0;
            
            //movimento por curva quadratica
            movimentoPorCurvaQuadratica.alturaX = 0;
            movimentoPorCurvaQuadratica.alturaY = 0;
            movimentoPorCurvaQuadratica.comprimentoX = 0;
            movimentoPorCurvaQuadratica.comprimentoY = 0;
            
            //movimentarPorFuncao
            movimentoPorFuncao.funcao = "";
            movimentoPorFuncao.iniciarEmCoordNegativa = false;
        }
        
        if (metodoExecucao == MetodoMovimentoAtual.FUNCAO) {
            //movimento por tipo
            movimentoPorTipo = null;
            
            //movimento por grau
            movimentoPorGrau = 0;
            
            //movimento por curva quadratica
            movimentoPorCurvaQuadratica.alturaX = 0;
            movimentoPorCurvaQuadratica.alturaY = 0;
            movimentoPorCurvaQuadratica.comprimentoX = 0;
            movimentoPorCurvaQuadratica.comprimentoY = 0;
            
            //movimentarRepetidamente
            movimentoPorRepeticao.indiceDeformardorX = 0;
            movimentoPorRepeticao.indiceDeformardorY = 0;
            movimentoPorRepeticao.comprimentoMovimento = 0;
            movimentoPorRepeticao.alturaMovimento = 0;
            movimentoPorRepeticao.inverter = false;
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="SETs das Variáveis Auxiliares">
    /**
     * Salava o tipo de movimento desejado.
     * @param novoMovimento O TipoMovimento a ser salvo
     */
    public void setMovimentoPorTipo(TipoMovimento novoMovimento) {
        metodoChamado.mudarEstadoPara(MetodoMovimentoAtual.TIPO);
        reiniciarAuxiliaresMovimentavel(MetodoMovimentoAtual.TIPO);
        this.movimentoPorTipo = novoMovimento;
    }
    
    /**
     * Salva a direção do movimento.
     * @param direcaoEmGraus A direção do movimento a ser salvo
     */
    public void setMovimentoPorGrau(double direcaoEmGraus) {
        metodoChamado.mudarEstadoPara(MetodoMovimentoAtual.GRAU);
        reiniciarAuxiliaresMovimentavel(MetodoMovimentoAtual.GRAU);
        this.movimentoPorGrau = direcaoEmGraus;
    }
    
    /**
     * Salva os parâmetros necessários para movimentar numa curva quadrática.
     * @param alturaX O valor da alturaX
     * @param alturaY O valor da alturaY
     * @param comprimentoX O valor do comprimentoX
     * @param comprimentoY O valor do comprimentoY
     */
    public void setMovimentoPorCurvaQuadratica(
            double alturaX, double alturaY, 
            double comprimentoX, double comprimentoY){
        metodoChamado.mudarEstadoPara(MetodoMovimentoAtual.CURVA_QUADRATICA);
        reiniciarAuxiliaresMovimentavel(MetodoMovimentoAtual.CURVA_QUADRATICA);
        movimentoPorCurvaQuadratica.alturaX = alturaX;
        movimentoPorCurvaQuadratica.alturaY = alturaY;
        movimentoPorCurvaQuadratica.comprimentoX = comprimentoX;
        movimentoPorCurvaQuadratica.comprimentoY = comprimentoY;
    }
    
    /**
     * Salva todos os parâmetros necessários para movimentar repetidamente.
     * @param indiceDeformardorX O valor do indice a ser salvo
     * @param indiceDeformardorY O valor do indice a ser salvo
     * @param comprimento O valor do comprimento a ser salvo
     * @param altura O valor da altura a ser salva
     * @param inverter O valor booleano a ser salvo
     */
    public void setMovimentoPorRepeticao(
            double indiceDeformardorX, double indiceDeformardorY, 
            double comprimento, double altura, boolean inverter
    ) {
        metodoChamado.mudarEstadoPara(MetodoMovimentoAtual.REPETIDAMENTE);
        reiniciarAuxiliaresMovimentavel(MetodoMovimentoAtual.REPETIDAMENTE);
        movimentoPorRepeticao.indiceDeformardorX = indiceDeformardorX;
        movimentoPorRepeticao.indiceDeformardorY = indiceDeformardorY;
        movimentoPorRepeticao.comprimentoMovimento = comprimento;
        movimentoPorRepeticao.alturaMovimento = altura;
        movimentoPorRepeticao.inverter = inverter;
    }
    
    /**
     * Salva todos os parâmetros necessários para movimentar de acordo com a
     * função matemática passada.
     * @param funcao A função a ser salva
     * @param iniciarEmCoordNegativa O booleano a ser salvo
     */
    public void setMovimentoPorFuncao(String funcao, boolean iniciarEmCoordNegativa) {
        metodoChamado.mudarEstadoPara(MetodoMovimentoAtual.FUNCAO);
        reiniciarAuxiliaresMovimentavel(MetodoMovimentoAtual.FUNCAO);
        movimentoPorFuncao.funcao = funcao;
        movimentoPorFuncao.iniciarEmCoordNegativa = iniciarEmCoordNegativa;
    }
    
    /**
     * Salva o estado atual de movimento como NENHUM.
     */
    public void setMovimentoNenhum() {
        metodoChamado.mudarEstadoPara(MetodoMovimentoAtual.NENHUM);
        reiniciarAuxiliaresMovimentavel(MetodoMovimentoAtual.NENHUM);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GETs das Variáveis Auxiliares">
    /**
     * Retorna a variável auxiliar de MovimentoPorTipo
     * @return TipoMovimento
     */
    public TipoMovimento getMovimentoPorTipo(){
        return movimentoPorTipo;
    }
    
    /**
     * Retorna a variável auxiliar de MovimentoPorGrau
     * @return double
     */
    public double getMovimentoPorGrau(){
        return movimentoPorGrau;
    }
    
    /**
     * Retorna um objeto que contém todos os valores salvos para efetuar o
     * MovimentoPorCurvaQuadratica.
     * @return MovimentoPorCurvaQuadratica
     */
    public MovimentoPorCurvaQuadratica getMovimentoPorCurvaQuadratica(){
        return movimentoPorCurvaQuadratica;
    }
    
    /**
     * Retorna um objeto que contém todos os valores salvos para efetuar o
     * MovimentoPorRepeticao.
     * @return MovimentoPorRepeticao
     */
    public MovimentoPorRepeticao getMovimentoPorRepeticao(){
        return movimentoPorRepeticao;
    }
    
    /**
     * Retorna um objeto que contém todos os valores salvos para efetuar o
     * MovimentoPorFuncao
     * @return MovimentoPorRepeticao
     */
    public MovimentoPorFuncao getMovimentoPorFuncao(){
        return movimentoPorFuncao;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="InnerClass: MovimentoPorCurvaQuadratica">
    /**
     * InnerClass simples para ser usada como atribuição e retorno das variáveis
     * auxiliares.
     */
    public class MovimentoPorCurvaQuadratica {
        
        public double alturaX;
        public double alturaY;
        public double comprimentoX;
        public double comprimentoY;
        
        /**
         * Construtor básico da InnerClass.
         * @param alturaX O valor da alturaX
         * @param alturaY O valor da alturaY
         * @param comprimentoX O valor do comprimentoX
         * @param comprimentoY O valor do comprimentoY
         */
        public MovimentoPorCurvaQuadratica(
                double alturaX, double alturaY, 
                double comprimentoX, double comprimentoY){
    
            this.alturaX = alturaX;
            this.alturaY = alturaY;
            this.comprimentoX = comprimentoX;
            this.comprimentoY = comprimentoY;
        }
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="InnerClass: MovimentoPorRepeticao">
    /**
     * InnerClass simples para ser usada como atribuição e retorno das variáveis
     * auxiliares.
     */
    public class MovimentoPorRepeticao {
        
        public double indiceDeformardorX;
        public double indiceDeformardorY;
        public double comprimentoMovimento;
        public double alturaMovimento;
        public boolean inverter;
        
        /**
         * Construtor básico da InnerClass.
         * @param indiceDeformardorX O valor do indice a ser armazenado
         * @param indiceDeformardorY O valor do indice a ser armazenado
         * @param comprimento O valor do comprimento a ser armazenado
         * @param altura O valor da altura a ser armazenada
         * @param inverter O valor booleano a ser armazenado
         */
        public MovimentoPorRepeticao(
                double indiceDeformardorX, double indiceDeformardorY, 
                double comprimento, double altura, boolean inverter
        ){
            this.indiceDeformardorX = indiceDeformardorX;
            this.indiceDeformardorY = indiceDeformardorY;
            this.comprimentoMovimento = comprimento;
            this.alturaMovimento = altura;
            this.inverter = inverter;            
        }
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="InnerClass: MovimentoPorFuncao">
    /**
     * InnerClass simples para ser usada como atribuição e retorno das variáveis
     * auxiliares.
     */
    public class MovimentoPorFuncao{
        public String funcao;
        public boolean iniciarEmCoordNegativa;
        
        /**
         * Construtor básico da InnerClass
         * @param funcao O valor da função a ser armazenada
         * @param iniciarEmCoordNegativa O valor booleano a ser armazenado
         */
        public MovimentoPorFuncao(String funcao, boolean iniciarEmCoordNegativa){
            this.funcao = funcao;
            this.iniciarEmCoordNegativa = iniciarEmCoordNegativa;
        }
    }
    //</editor-fold>
    
}
