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

package simplegamework.listener;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Quando é necessário gerenciar vários ArrayList de eventos diversos, deve-se
 * implementar essa interface. O tipos de eventos disponíveis são de Mouse e Teclado.
 * <br><br><small>Created on : 14/07/2015, 19:11:10</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public interface ListenerListManuseavel {   
    
    //<editor-fold defaultstate="collapsed" desc="01-Métodos: KeyTyped">
    /**
     * Adiciona uma novo evento KeyTyped.
     * @param keyTyped O novo evento
     */
    public void addKeyTyped(KeyTyped keyTyped);
    
    /**
     * Retorna um evento KeyTyped adicionado de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     * @return KeyTyped
     */
    public KeyTyped getKeyTyped(int indice);
    
    /**
     * Remove um evento KeyTyped de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     */
    public void removeKeyTyped(int indice);
    
    /**
     * Invoca todos os eventos KeyTyped adicionados.
     * @param event O objeto que recebeu o disparo do evento
     */
    public void invocarKeyTypedList(KeyEvent event);
    
    /**
     * Retorna a quantidade de eventos KeyTyped adicionados.
     * @return int
     */
    public int sizeKeyTypedList();
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="02-Métodos: KeyPressed">
    /**
     * Adiciona uma novo evento KeyPressed.
     * @param keyPressed O novo evento
     */
    public void addKeyPressed(KeyPressed keyPressed);
    
    /**
     * Retorna um evento KeyPressed adicionado de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     * @return KeyPressed
     */
    public KeyPressed getKeyPressed(int indice);
    
    /**
     * Remove um evento KeyPressed de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     */
    public void removeKeyPressed(int indice);
    
    /**
     * Invoca todos os eventos KeyPressed adicionados.
     * @param event O objeto que recebeu o disparo do evento
     */
    public void invocarKeyPressedList(KeyEvent event);
    
    /**
     * Retorna a quantidade de eventos KeyPressed adicionados.
     * @return int
     */
    public int sizeKeyPressedList();
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="03-Métodos: KeyReleased">
    /**
     * Adiciona uma novo evento KeyReleased.
     * @param keyReleased O novo evento
     */
    public void addKeyReleased(KeyReleased keyReleased);
    
    /**
     * Retorna um evento KeyReleased adicionado de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     * @return KeyReleased
     */
    public KeyReleased getKeyReleased(int indice);
    
    /**
     * Remove um evento KeyReleased de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     */
    public void removeKeyReleased(int indice);
    
    /**
     * Invoca todos os eventos KeyReleased adicionados.
     * @param event O objeto que recebeu o disparo do evento
     */
    public void invocarKeyReleasedList(KeyEvent event);
    
    /**
     * Retorna a quantidade de eventos KeyReleased adicionados.
     * @return int
     */
    public int sizeKeyReleasedList();
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="04-Métodos: MouseClicked">
    /**
     * Adiciona uma novo evento MouseClicked.
     * @param mouseClicked O novo evento
     */
    public void addMouseClicked(MouseClicked mouseClicked);
    
    /**
     * Retorna um evento MouseClicked adicionado de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     * @return MouseClicked
     */
    public MouseClicked getMouseClicked(int indice);
    
    /**
     * Remove um evento MouseClicked de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     */
    public void removeMouseClicked(int indice);
    
    /**
     * Invoca todos os eventos MouseClicked adicionados.
     * @param event O objeto que recebeu o disparo do evento
     */
    public void invocarMouseClickedList(MouseEvent event);
    
    /**
     * Retorna a quantidade de eventos MouseClicked adicionados.
     * @return int
     */
    public int sizeMouseClickedList();
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="05-Métodos: MousePressed">
    /**
     * Adiciona uma novo evento MousePressed.
     * @param mousePressed O novo evento
     */
    public void addMousePressed(MousePressed mousePressed);
    
    /**
     * Retorna um evento MousePressed adicionado de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     * @return MousePressed
     */
    public MousePressed getMousePressed(int indice);
    
    /**
     * Remove um evento MousePressed de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     */
    public void removeMousePressed(int indice);
    
    /**
     * Invoca todos os eventos MousePressed adicionados.
     * @param event O objeto que recebeu o disparo do evento
     */
    public void invocarMousePressedList(MouseEvent event);
    
    /**
     * Retorna a quantidade de eventos MousePressed adicionados.
     * @return int
     */
    public int sizeMousePressedList();
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="06-Métodos: MouseReleased">
    /**
     * Adiciona uma novo evento MouseReleased.
     * @param mouseReleased O novo evento
     */
    public void addMouseReleased(MouseReleased mouseReleased);
    
    /**
     * Retorna um evento MouseReleased adicionado de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     * @return MouseReleased
     */
    public MouseReleased getMouseReleased(int indice);
    
    /**
     * Remove um evento MouseReleased de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     */
    public void removeMouseReleased(int indice);
    
    /**
     * Invoca todos os eventos MouseReleased adicionados.
     * @param event O objeto que recebeu o disparo do evento
     */
    public void invocarMouseReleasedList(MouseEvent event);
    
    /**
     * Retorna a quantidade de eventos MouseReleased adicionados.
     * @return int
     */
    public int sizeMouseReleasedList();
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="07-Métodos: MouseEntered">
    /**
     * Adiciona uma novo evento MouseEntered.
     * @param mouseEntered O novo evento
     */
    public void addMouseEntered(MouseEntered mouseEntered);
    
    /**
     * Retorna um evento MouseEntered adicionado de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     * @return MouseEntered
     */
    public MouseEntered getMouseEntered(int indice);
    
    /**
     * Remove um evento MouseEntered de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     */
    public void removeMouseEntered(int indice);
    
    /**
     * Invoca todos os eventos MouseEntered adicionados.
     * @param event O objeto que recebeu o disparo do evento
     */
    public void invocarMouseEnteredList(MouseEvent event);
    
    /**
     * Retorna a quantidade de eventos MouseEntered adicionados.
     * @return int
     */
    public int sizeMouseEnteredList();
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="08-Métodos: MouseExited">
    /**
     * Adiciona uma novo evento MouseExited.
     * @param mouseExited O novo evento
     */
    public void addMouseExited(MouseExited mouseExited);
    
    /**
     * Retorna um evento MouseExited adicionado de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     * @return MouseExited
     */
    public MouseExited getMouseExited(int indice);
    
    /**
     * Remove um evento MouseExited de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     */
    public void removeMouseExited(int indice);
    
    /**
     * Invoca todos os eventos MouseExited adicionados.
     * @param event O objeto que recebeu o disparo do evento
     */
    public void invocarMouseExitedList(MouseEvent event);
    
    /**
     * Retorna a quantidade de eventos MouseExited adicionados.
     * @return int
     */
    public int sizeMouseExitedList();
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="09-Métodos: MouseDragged">
    /**
     * Adiciona uma novo evento MouseDragged.
     * @param mouseDragged O novo evento
     */
    public void addMouseDragged(MouseDragged mouseDragged);
    
    /**
     * Retorna um evento MouseDragged adicionado de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     * @return MouseDragged
     */
    public MouseDragged getMouseDragged(int indice);
    
    /**
     * Remove um evento MouseDragged de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     */
    public void removeMouseDragged(int indice);
    
    /**
     * Invoca todos os eventos MouseDragged adicionados.
     * @param event O objeto que recebeu o disparo do evento
     */
    public void invocarMouseDraggedList(MouseEvent event);
    
    /**
     * Retorna a quantidade de eventos MouseDragged adicionados.
     * @return int
     */
    public int sizeMouseDraggedList();
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="10-Métodos: MouseMoved">
    /**
     * Adiciona uma novo evento MouseMoved.
     * @param mouseMoved O novo evento
     */
    public void addMouseMoved(MouseMoved mouseMoved);
    
    /**
     * Retorna um evento MouseMoved adicionado de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     * @return MouseMoved
     */
    public MouseMoved getMouseMoved(int indice);
    
    /**
     * Remove um evento MouseMoved de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     */
    public void removeMouseMoved(int indice);
    
    /**
     * Invoca todos os eventos MouseMoved adicionados.
     * @param event O objeto que recebeu o disparo do evento
     */
    public void invocarMouseMovedList(MouseEvent event);
    
    /**
     * Retorna a quantidade de eventos MouseMoved adicionados.
     * @return int
     */
    public int sizeMouseMovedList();
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="11-Métodos: MouseWheelMoved">
    /**
     * Adiciona uma novo evento MouseWheelMoved.
     * @param mouseWheelMoved O novo evento
     */
    public void addMouseWheelMoved(MouseWheelMoved mouseWheelMoved);
    
    /**
     * Retorna um evento MouseWheelMoved adicionado de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     * @return MouseWheelMoved
     */
    public MouseWheelMoved getMouseWheelMoved(int indice);
    
    /**
     * Remove um evento MouseWheelMoved de acordo com o índice procurado.
     * @param indice O índice do evento procurado
     */
    public void removeMouseWheelMoved(int indice);
    
    /**
     * Invoca todos os eventos MouseWheelMoved adicionados.
     * @param event O objeto que recebeu o disparo do evento
     */
    public void invocarMouseWheelMovedList(MouseWheelEvent event);
    
    /**
     * Retorna a quantidade de eventos MouseWheelMoved adicionados.
     * @return int
     */
    public int sizeMouseWheelMovedList();
    //</editor-fold>
    
}
