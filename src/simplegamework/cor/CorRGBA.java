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

package simplegamework.cor;

import java.awt.Color;

/**
 * Representa uma cor qualquer no padrão RGBA (Red, Green, Blue, Alpha) em 
 * Hexadecimal. Ou seja, para uma cor vermelha 100% opaca pode ser passado
 * "ff0000ff", já para a mesm a cor com menos opacidade pode ser "ff0000b3".
 * Obs.: Não existe distinção entre letras com caixa alta ou baixa.
 * <br><br><small>Created on : 25/07/2015, 11:13:02</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class CorRGBA {
    
    private Color cor;
    private String codigoHexa;
    private int R;
    private int G;
    private int B;
    private int A;
    
    //Auxiliares
    private enum CorCodigo{
        R, G, B, A;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Construtores">
    /**
     * Construtor padrão de classe.
     * @param codigoHexadecimal O valor da cor na seguinte ordem: "RRGGBBAA"
     */
    public CorRGBA(String codigoHexadecimal){
        initAtributos(codigoHexadecimal);
    }
    
    /**
     * Inicializa CorRGBA com um objeto Color.
     * @param corCriada O objeto Color
     */
    public CorRGBA(Color corCriada){
        codigoHexa = Integer.toHexString(corCriada.getRed()).charAt(0) == '0' ? 
                        "00" :
                        Integer.toHexString(corCriada.getRed());
        
        codigoHexa += Integer.toHexString(corCriada.getGreen()).charAt(0) == '0' ? 
                        "00" :
                        Integer.toHexString(corCriada.getGreen());
        
        codigoHexa += Integer.toHexString(corCriada.getBlue()).charAt(0) == '0' ? 
                        "00" :
                        Integer.toHexString(corCriada.getBlue());
        
        codigoHexa += Integer.toHexString(corCriada.getAlpha()).charAt(0) == '0' ? 
                        "00" :
                        Integer.toHexString(corCriada.getAlpha());
        
        initAtributos(codigoHexa);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: Auxiliares">
    /**
     * Validador do código hexadecimal de cada elemento do RGBA.
     * @param codigoHexa O valor de um dos elementos do RGBA
     * @return boolean
     */
    private boolean isCodigoHexaValido(String codigoHexa){
        char cod0 = codigoHexa.charAt(0);
        char cod1 = codigoHexa.charAt(1);
        
        return
            (cod0 == '0' || cod0 == '1' ||
            cod0 == '2' || cod0 == '3' ||
            cod0 == '4' || cod0 == '5' ||
            cod0 == '6' || cod0 == '7' ||
            cod0 == '8' || cod0 == '9' ||
            cod0 == 'A' || cod0 == 'B' ||
            cod0 == 'C' || cod0 == 'D' ||
            cod0 == 'E' || cod0 == 'F') &&
            (cod1 == '0' || cod1 == '1' ||
            cod1 == '2' || cod1 == '3' ||
            cod1 == '4' || cod1 == '5' ||
            cod1 == '6' || cod1 == '7' ||
            cod1 == '8' || cod1 == '9' ||
            cod1 == 'A' || cod1 == 'B' ||
            cod1 == 'C' || cod1 == 'D' ||
            cod1 == 'E' || cod1 == 'F');
    }
    
    /**
     * Quando o código de uma cor é invalido a String codigoHexa deve ser alterada.
     * @param corEscolhida A a cor a ser zerada (R, G, B ou A)
     */
    private int zerarCodigoCor(CorCodigo corEscolhida){
        //01 23 45 67
        //RR GG BB AA
        
        if (corEscolhida == CorCodigo.R) {
            codigoHexa = "00" + codigoHexa.substring(2);
            return 0;
        }
        
        if (corEscolhida == CorCodigo.G) {
            codigoHexa = codigoHexa.substring(0, 2) + "00" + codigoHexa.substring(4);
            return 0;
        }
        
        if (corEscolhida == CorCodigo.B) {
            codigoHexa = codigoHexa.substring(0, 4) + "00" + codigoHexa.substring(6);
            return 0;
        }
        
        if (corEscolhida == CorCodigo.A) {
            codigoHexa = codigoHexa.substring(0, 6) + "00";
            return 0;
        }
        
        return 0;
    }
    
    /**
     * Inicializador dos atributos de classe.
     * @param codigoHexa A String que representa uma cor no formato RGBA
     */
    private void initAtributos(String codigoHexa){        
        codigoHexa = codigoHexa.trim();
        codigoHexa = codigoHexa.toUpperCase();
        
        if (codigoHexa.length() == 8) {
            this.codigoHexa = codigoHexa;
            R = isCodigoHexaValido(codigoHexa.substring(0, 2)) ?
                    Integer.parseInt(codigoHexa.substring(0, 2), 16) : 
                    zerarCodigoCor(CorCodigo.R);
            
            G = isCodigoHexaValido(codigoHexa.substring(2, 4)) ?
                    Integer.parseInt(codigoHexa.substring(2, 4), 16) :
                    zerarCodigoCor(CorCodigo.G);
            
            B = isCodigoHexaValido(codigoHexa.substring(4, 6)) ?
                    Integer.parseInt(codigoHexa.substring(4, 6), 16) :
                    zerarCodigoCor(CorCodigo.B);
            
            A = isCodigoHexaValido(codigoHexa.substring(6)) ?
                    Integer.parseInt(codigoHexa.substring(6), 16) :
                    zerarCodigoCor(CorCodigo.A);
            
            cor = new Color(R, G, B, A);
        }        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GETs">
    /**
     * Retorna um objeto do tipo Color.
     * @return Color
     */ 
    public Color getColor() {
        return cor;
    }
    
    /**
     * Retorna o código hexadecimal da cor atual.
     * @return String
     */
    public String getCodigoHexadecimal() {
        return codigoHexa;
    }
    
    /**
     * Retorna o valor de Red.
     * @return int no formato decimal.
     */
    public int getR() {
        return R;
    }
    
    /**
     * Retorna o valor de Green.
     * @return int no formato decimal.
     */
    public int getG() {
        return G;
    }
    
    /**
     * Retorna o valor de Blue.
     * @return int no formato decimal.
     */
    public int getB() {
        return B;
    }
    
    /**
     * Retorna o valor de Alpha.
     * @return int no formato decimal.
     */
    public int getA() {
        return A;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Object">
    @Override
    public String toString() {
        return  "CorRGBA{\n" + 
                "  codigoHexa: " + codigoHexa + "\n" +
                "  R: " + R + "\n" +
                "  G: " + G + "\n" +
                "  B: " + B + "\n" +
                "  A: " + A + "\n" +
                "}";
    }
    //</editor-fold>
    
}
