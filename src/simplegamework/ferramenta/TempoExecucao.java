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

/**
 * Gerenciador do TempoExecucao de instruções, trechos de códigos, métodos ou
 * classes.
 * <br><br><small>Created on : 10/07/2015, 22:04:01</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class TempoExecucao {

    protected double tInicial;
    protected double tFinal;
    protected double tFinalPausa;
    protected boolean pausado;
    protected double tDiferenca;
    protected double tRestanteParaMeta;
    protected double tMeta;
    protected MedidaTempo medidaPadrao;
        
    public static final double BILHAO = 1000000000;
    public static final double MILHAO = 1000000;
    public static final double MIL = 1000;
    
    //DEFINIDORES DOS TIPOS DE ORIGEM DA COLETA DE TEMPO DO SISTEMA
    private final byte TEMPO_POR_SYTEM_NANO = 1;
    private final byte TEMPO_POR_SYTEM_MILI = 2;
    
    //ORIGEM DO TEMPO PADRÃO
    private final byte ORIGEM_TEMPO_PADRAO = TEMPO_POR_SYTEM_NANO;
    
    //<editor-fold defaultstate="collapsed" desc="Construtor">
    /**
     * Construtor básico da classe.
     * @param meta O tempo máximo de execução a ser alcançado
     * @param medidaPadrao O tipo de tempo a ser trabalhado, ele pode ser:
     * MedidaTempo.NANOSSEGUNDOS, MedidaTempo.MILISSEGUNDOS ou 
     * MedidaTempo.SEGUNDOS
     */
    public TempoExecucao(double meta, MedidaTempo medidaPadrao) {
        tMeta = meta;
        this.medidaPadrao = medidaPadrao;
    }
    //</editor-fold>
    
    /**
     * Retorna uma fatia de tempo coletada do Sistema Operacional de acordo com
     * a medida padrão estabelecido no construtor.
     * @return double
     */
    private double getTempo(){
        if (ORIGEM_TEMPO_PADRAO == TEMPO_POR_SYTEM_NANO) {
            if (medidaPadrao == MedidaTempo.NANOSSEGUNDOS) {
                return System.nanoTime();
            }
            if (medidaPadrao == MedidaTempo.MILISSEGUNDOS) {
                return System.nanoTime() / MILHAO;
            }
            if (medidaPadrao == MedidaTempo.SEGUNDOS) {
                return System.nanoTime() / BILHAO;
            }
        }
        
        if (ORIGEM_TEMPO_PADRAO == TEMPO_POR_SYTEM_MILI) {
            if (medidaPadrao == MedidaTempo.NANOSSEGUNDOS) {
                return System.currentTimeMillis() * MILHAO;
            }
            if (medidaPadrao == MedidaTempo.MILISSEGUNDOS) {
                return System.currentTimeMillis();
            }
            if (medidaPadrao == MedidaTempo.SEGUNDOS) {
                return System.currentTimeMillis() / MIL;
            }
        }
        
        return 0;
    }
    
    /**
     * Altera a meta de tempo a ser atingida.
     * @param novaMeta O tempo máximo de execução a ser alcançado
     */
    public void setMeta(double novaMeta){
        tMeta = novaMeta;
    }
    
    /**
     * Retorna o valor do tempo de meta
     * @return double
     */
    public double getMeta(){
        return tMeta;
    }
    
    /**
     * Retorna o tipo de medida de tempo escolhida.
     * @return MedidaTempo
     */
    public MedidaTempo getMedidaPadraoTempo(){
        return medidaPadrao;
    }
    
    /**
     * Inicializa o contador interno de tempo.
     */
    public void iniciarTempo() {
        tInicial = getTempo();
    }
    
    /**
     * Retorna o tempoInicial.
     * @return double
     */
    public double getTempoInicial(){
        return tInicial;
    }
    
    /**
     * Finaliza o contador interno de tempo.
     */
    public void finalizarTempo() {
        tFinal = getTempo();
    }
    
    /**
     * Retorna o tempoFinal.
     * @return double
     */
    public double getTempoFinal(){
        return tFinal;
    }
    
    /**
     * Pausa o contador interno de tempo.
     */
    public void pausarTempo(){
        if (!pausado) {
            pausado = true;
            tFinalPausa = getTempo();
        }        
    }
    
    /**
     * Retoma o contador interno de tempo onde parou após a pausa.
     */
    public void retomarTempo(){
        if (pausado) {
            double dif = tFinalPausa - tInicial;
            tInicial = getTempo() + dif;
            pausado = false;
        }       
    }
    
    /**
     * Retorna o estado da coleta de tempo, ou seja, Pausado ou Não Pausado.
     * @return boolean
     */
    public boolean isPausado(){
        return pausado;
    }
    
    /**
     * Compara os contandores internos de início e fim de tempo para descobrir
     * se a meta foi alcançada. Caso a meta tenha sido atingida o retorno será
     * true, caso contrário será false e o valor do tempo restante para atingir
     * a meta ficará disponível pelo método getTempoRestanteParaMeta().
     * @return boolean
     */
    public boolean atingiuMeta() {
        if (getTempoDecorrido() >= tMeta) {
            return true;
        }
        else{
            finalizarTempo();
            return false;
        }
    }
    
    /**
     * Retorna o tempo decorrido até o presente momento desde que o método
     * iniciarTempo() tenha sido chamado anteriormente.
     * @return double
     */
    public double getTempoDecorrido(){
        return getTempo() - tInicial;
    }
    
    /**
     * Retorna o tempo restante para atingir a meta de tempo no formato da medida
     * de tempo padrão.
     * @return double
     */
    public double getTempoRestanteParaMeta() {
        return tMeta - (tFinal - tInicial);
    }
    
    /**
     * Para a execução de todo o software pelo tempo determinado passado por 
     * parâmetro, ou seja, utiliza Thread.sleep().
     * @param tempoEspera Esse valor deve respeitar a medidaPadrao passada no 
     * construtor da classe
     */
    public void pararExecucaoDoSoftware(double tempoEspera) {
        long convertMili = 0;
        
        if (medidaPadrao == MedidaTempo.NANOSSEGUNDOS) {
            convertMili = (long)(tempoEspera / MILHAO);
        }

        if (medidaPadrao == MedidaTempo.SEGUNDOS) {
            convertMili = (long)(tempoEspera * MIL);
        }
                
        try {
            Thread.sleep(convertMili);
        } catch (Exception e) {}
    }
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Object">
    /**
      * Cria uma String que demonstra o status atual da classe.
      * @return String
      */
    @Override
    public String toString() {        
        String medida = "";
        
        if (medidaPadrao == MedidaTempo.NANOSSEGUNDOS) {
            medida = "ns\n";
        }

        if (medidaPadrao == MedidaTempo.MILISSEGUNDOS) {
            medida = "ms\n";
        }

        if (medidaPadrao == MedidaTempo.SEGUNDOS) {
            medida = "s\n";
        }
        
        return  "TempoExecucao : " + medidaPadrao + " {\n" +
                "  - inicial: " + tInicial + medida + 
                "  -   final: " + tFinal + medida + 
                "  -    meta: " + tMeta + medida + 
                "  - tempoRestanteParaMeta: " + getTempoRestanteParaMeta() + medida + 
                "  - tempoDecorrido: " + getTempoDecorrido() + medida +
                "}\n";
    }
    //</editor-fold>

}
