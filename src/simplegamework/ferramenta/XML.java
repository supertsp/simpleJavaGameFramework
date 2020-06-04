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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A classe XML serve como facilitadora para abrir um arquivo XML e poder manipular
 * seus respectivos elementos (tags).
 * <br>
 * <br><small>Created on : 10/10/2015, 12:05:45</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class XML {
    
    protected Document documento;
    protected Element elementoRaiz;
    
    //<editor-fold defaultstate="collapsed" desc="Construtor">
    /**
     * Construtor básico de classe.
     * @param URL O endereço do arquivo XML.
     */
    public XML(String URL){
        initXML(URL);
        
        if (!isDocumentoXMLValido()) {
            documento = null;
            elementoRaiz = null;
            
            System.out.println(
                    "+-----------------------------------------+\n" +
                    "| O arquivo aberto não é um XML válido :( |\n" +
                    "| URL: " + URL + "\n" +
                    "+-----------------------------------------+\n"
            );
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: Auxiliares">
    /**
     * Valida se o arquivo aberto é do tipo XML.
     * @return boolean
     */
    private boolean isDocumentoXMLValido(){
        return documento.getXmlEncoding() != null;
    }
    
    /**
     * Inicializa o atributo documento e elementoRaiz.
     * @param URL O endereço do arquivo
     */
    private void initXML(String URLXML) {
        try {            
            DocumentBuilderFactory fabricaConstrutoraDocumentos = DocumentBuilderFactory.newInstance();
            DocumentBuilder construtorDocumento = fabricaConstrutoraDocumentos.newDocumentBuilder();
            documento = construtorDocumento.parse(this.getClass().getResourceAsStream(URLXML));
            elementoRaiz = documento.getDocumentElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //</editor-fold>
    
    /**
     * Retorna o documento XML.
     * @return Document
     */
    public Document getDocumento(){
        return documento;
    }
    
    /**
     * Retorna o primeiro elemento do documento, ou seja, o elemento raiz.
     * @return Element
     */
    public Element getElementoRaiz(){
        return elementoRaiz;
    }
    
    /**
     * Retorna todos os filhos da tag raiz. Obs.: a tag de abertura xml não é
     * contada.
     * @return Element[] 
     */
    public Element[] getElementosFilhosRaiz(){
        NodeList list = elementoRaiz.getChildNodes();
        int qtdElementos = 0;
        
        for (int indice = 0; indice < list.getLength(); indice++) {
            if (!list.item(indice).getNodeName().equals("#text")) {
                qtdElementos++;
            }
        }
        
        Element[] elementos = new Element[qtdElementos];
        
        int contElementos = 0;
        for (int indice = 0; indice < list.getLength(); indice++) {
            if (!list.item(indice).getNodeName().equals("#text")) {
                elementos[contElementos] = (Element) list.item(indice);
                contElementos++;
            }
        }
        
        return elementos;
    }
    
    /**
     * Retorna um array de elementos da tag procurada no documento.
     * @param nomeTag O nome da tag procurada no documento
     * @return Element[]
     */
    public Element[] getElementos(String nomeTag){
        NodeList list = elementoRaiz.getElementsByTagName(nomeTag);
        Element[] elementos = new Element[list.getLength()];
        
        for (int indice = 0; indice < list.getLength(); indice++) {
            elementos[indice] = (Element)list.item(indice);
        }
        
        return elementos;
    }
    
    /**
     * Retorna a quantidade de elementos, de acordo com a tag procurada, 
     * existentes no documento.
     * @param nomeTag O nome da tag procurada no documento
     * @return int
     */
    public int sizeElementos(String nomeTag){
        return elementoRaiz.getElementsByTagName(nomeTag).getLength();
    }
    
    /**
     * Retorna um único elemento a partir do array de elementos encontrados.
     * @param nomeTag O nome da tag procurada
     * @param indiceElemento O índice do elemento procurado no array
     * @return Element
     */
    public Element getElemento(String nomeTag, int indiceElemento){
        return (Element)elementoRaiz.getElementsByTagName(nomeTag).item(indiceElemento);
    }
    
    /**
     * Retorna um único elemento no documento de acordo com id procurado
     * @param idElemento O id do elemento procurado
     * @return Element
     */
    public Element getElemento(String idElemento){
        return documento.getElementById(idElemento);
    }
    
    /**
     * Retorna todos elementos filhos encontrados, de acordo a tag filho procurada,
     * em qualquer nível de nó dentro da tag pai determinada.
     * @param tagPai A tag pai procurada
     * @param indicePai Como exitirá um array com vários pais, o índice do pai
     * procurado deverá ser passado
     * @param tagFilho A tag dos filhos procurados
     * @return Element[]
     */
    public Element[] getElementosFilhos(String tagPai, int indicePai, String tagFilho){
        NodeList list = getElementos(tagPai)[indicePai].getElementsByTagName(tagFilho);        
        Element[] filhos = new Element[sizeElementosFilhos(tagPai, indicePai, tagFilho)];
        
        for (int contFilho = 0; contFilho < list.getLength(); contFilho++) {
            filhos[contFilho] = (Element)list.item(contFilho);            
        }
        
        return filhos;        
    }
    
    /**
     * Retorna a quantidade de elementos filhos encontrados, de acordo a tag 
     * filho procurada, em qualquer nível de nó dentro da tag pai determinada.
     * @param tagPai O nome da tag pai procurada no documento
     * @param indicePai Como exitirá um array com vários pais, o índice o pai
     * procurado deverá ser passado
     * @param tagFilho O nome da tag do filho procurada no documento
     * @return int
     */
    public int sizeElementosFilhos(String tagPai, int indicePai, String tagFilho){
        return getElementos(tagPai)[indicePai].getElementsByTagName(tagFilho).getLength();
    }
    
    /**
     * Retorna todos os elementos filhos diretos existentes dentro de uma determinada
     * tag pai.
     * @param tagPai A tag pai procurada
     * @param indicePai O índice da tag pai procurada
     * @return Element[]
     */
    public Element[] getElementosFilhos(String tagPai, int indicePai){
        NodeList list = getElementos(tagPai)[indicePai].getChildNodes();        
        Element[] filhos = new Element[sizeElementosFilhos(tagPai, indicePai)];
        
        for (int contFilho = 0; contFilho < list.getLength(); contFilho++) {
            filhos[contFilho] = (Element)list.item(contFilho);            
        }
        
        return filhos;   
    }
    
    /**
     * Retorna a quantidade de elementos filhos diretos existentes num determinadao
     * pai.
     * @param tagPai O nome da tag pai procurada no documento
     * @param indicePai O índice da tag pai procurada
     * @return int
     */
    public int sizeElementosFilhos(String tagPai, int indicePai){
        return getElementos(tagPai)[indicePai].getChildNodes().getLength();
    }
    
    
    /**
     * Retorna um único elemento filho a partir da tag pai procurada.
     * @param tagPai A tag pai procurada
     * @param indicePai Como exitirá um array com vários pais, o índice do pai
     * procurado deverá ser passado
     * @param indiceFilho O índice do filho procurado
     * @return Element
     */
    public Element getElementoFilho(String tagPai, int indicePai, int indiceFilho){        
        return getElementosFilhos(tagPai, indicePai)[indiceFilho];
    }
    
    /**
     * Retorna todos os elementos netos, especificados na tag neto, existentes 
     * em qualquer nível de nó dentro da tag pai determinada. Esse é um recurso
     * bem útil para se acessar diretamente o 2º nível de nó de uma tag.
     * @param tagPai A tag pai procurada
     * @param indicePai O índice da tag pai procurada
     * @param tagFilho A tag filho procurada
     * @param indiceFilho O índice da tag filho procurada
     * @param tagNeto A tag neto procurada
     * @return Element[]
     */
    public Element[] getElementosNetos(
                String tagPai, int indicePai, 
                String tagFilho, int indiceFilho, 
                String tagNeto
    ){
        Element[] filhos = getElementosFilhos(tagPai, indicePai, tagFilho);
        Element[] netos = new Element[sizeElementosNetos(tagPai, indicePai, tagFilho, indiceFilho, tagNeto)];
        
        for (int contNeto = 0; contNeto < netos.length; contNeto++) {
            netos[contNeto] = (Element)filhos[indiceFilho]
                    .getElementsByTagName(tagNeto).item(contNeto);
        }
        
        return netos;
    }
    
    /**
     * Retorna a quantidade de elementos netos, especificados na tag neto, 
     * existentes em qualquer nível de nó dentro da tag pai determinada.
     * @param tagPai A tag pai procurada
     * @param indicePai O índice da tag pai procurada
     * @param tagFilho A tag filho procurada
     * @param indiceFilho O índice da tag filho procurada
     * @param tagNeto A tag neto procurada
     * @return int
     */
    public int sizeElementosNetos(
                String tagPai, int indicePai, 
                String tagFilho, int indiceFilho, 
                String tagNeto
    ){
        return getElementosFilhos(tagPai, indicePai, tagFilho)[indiceFilho]
                .getElementsByTagName(tagNeto).getLength();
    }
    
    /**
     * Retorna todos os elementos netos diretos existentes nas determinadas tag
     * pai e filho.
     * @param tagPai A tag pai procurada
     * @param indicePai O índice da tag pai procurada
     * @param tagFilho A tag filho procurada
     * @param indiceFilho O índice da tag filho procurada
     * @return Element[]
     */
    public Element[] getElementosNetos(
                String tagPai, int indicePai, 
                String tagFilho, int indiceFilho
    ){
        Element[] filhos = getElementosFilhos(tagPai, indicePai, tagFilho);
        Element[] netos = new Element[sizeElementosNetos(tagPai, indicePai, tagFilho, indiceFilho)];
        
        for (int contNeto = 0; contNeto < netos.length; contNeto++) {
            netos[contNeto] = (Element)filhos[indiceFilho]
                    .getChildNodes().item(contNeto);
        }
        
        return netos;
    }
    
    /**
     * Retorna a quantidade de elementos netos diretos existentes nas determinadas
     * tag pai e filho.
     * @param tagPai A tag pai procurada
     * @param indicePai O índice da tag pai procurada
     * @param tagFilho A tag filho procurada
     * @param indiceFilho O índice da tag filho procurada
     * @return int
     */
    public int sizeElementosNetos(
                String tagPai, int indicePai, 
                String tagFilho, int indiceFilho
    ){
        return getElementosFilhos(tagPai, indicePai, tagFilho)[indiceFilho].getChildNodes().getLength();
    }
    
    /**
     * Retorna o elemento neto de acordo com as especificações nos parâmetros.
     * @param tagPai A tag pai procurada
     * @param indicePai O índice da tag pai procurada
     * @param tagFilho A tag filho procurada
     * @param indiceFilho O índice da tag filho procurada
     * @param tagNeto A tag neto procurada
     * @param indiceNeto O índice do neto procurado
     * @return Element
     */
    public Element getElementoNeto(
                String tagPai, int indicePai, 
                String tagFilho, int indiceFilho, 
                String tagNeto, int indiceNeto
    ){               
        return getElementosNetos(tagPai, indicePai, tagFilho, indiceFilho, tagNeto)[indiceNeto];
    }
    
}
