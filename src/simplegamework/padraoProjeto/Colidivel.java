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

package simplegamework.padraoProjeto;

import simplegamework.objetoBasico.CaixaColisao;
import simplegamework.objetoBasico.LadoRetangulo;
import simplegamework.objetoBasico.TipoColisao;

/**
 * Todo objeto que precisa de métodos básicos para controlar suas colisões deverá
 * implementar essa interface.
 * <br><br><small>Created on : 18/06/2015, 17:12:48</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public interface Colidivel {
    
    /**
     * Retorna a CaixaColisao afim de ser manipulada pelos métodos colidiu() ou
     * colidiuNoLado() de outros objetos.
     * @return CaixaColisao
     */
    public CaixaColisao getCaixaColisao();
    
    /**
     * Usado para descobrir se o objeto atual colidiu com outro em Tela.
     * @param outroObjeto Outro objeto para ser avaliado a colisão
     * @param tipoColisao Enum que determina se a colisão a ser avaliada é uma
     * colisão INTERNA ou EXTERNA
     * @return true : Caso houve um colisão qualquer <br>
     * false : Caso não houve colisão
     */
    public boolean colidiu(CaixaColisao outroObjeto, TipoColisao tipoColisao);
    
    /**
     * Usado para descobrir se o objeto atual colidiu com outro em Tela e em qual
     * lado que ocorreu a colisão.
     * @param outroObjeto Outro objeto para ser avaliada a colisão
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
    public LadoRetangulo colidiuNoLado(CaixaColisao outroObjeto, TipoColisao tipoColisao);
    
    
}
