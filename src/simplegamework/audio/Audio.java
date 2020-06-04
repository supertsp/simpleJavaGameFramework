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
package simplegamework.audio;

import simplegamework.padraoProjeto.Tocavel;
import java.net.URL;
import javax.sound.sampled.*;

/**
 * Resolve o problema básico de tocar músicas ou sons num determinado momento do
 * jogo. No entanto o Java Sound aceita, sem muitos esforços, apenas arquivos no
 * formato WAV.
 * <br><br><small>Created on : 13/07/2015, 15:14:28</small>
 * @author Tiago Penha Pedroso
 * @version 1.0
 */
public class Audio implements Tocavel{
    
    protected Clip clip;
    
    //Auxiliares
    private boolean emPausa;
    private int posicaoUltimoFrame;
    
    //<editor-fold defaultstate="collapsed" desc="Construtor">
    /**
     * Contrutor padrão de classe.
     * @param URL O endereço do áudio, a partir do Pacote Default Java.
     */
    public Audio(String URL){
        if (validaFormatoWav(URL)) {
            initAudio(URL);
        }
        else{
            System.out.println(
                    "+-------------------------------+\n" +
                    "| Extensão de arquivo inválida! |\n" +
                    "| Extensão permitada: wav       |\n" +
                    "+-------------------------------+\n"
            );
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos: Auxiliares">
    /**
     * Valida a extensão do arquivo de áudio.
     * @param URL O endereço do arquivo
     * @return boolean
     */
    private boolean validaFormatoWav(String URL){
        return URL.substring(URL.length() - 3).contains("wav");
    }
    
    /**
     * Inicializa o atributo clip.
     * @param URL O endereço do arquivo
     */
    private void initAudio(String URLAudio) {
        try {
            //Criando um objeto URl para que possa ser resolvido o problema de
            //não tocar áudio dentro de um JAR
            URL urlObject = this.getClass().getResource(URLAudio);
            
            //Trocando a String para poder tocar de dentro do JAR
            urlObject = new URL(urlObject.toString().replaceFirst("file:/", "file:///"));
            
            //Inicia um AudioInputStream
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(urlObject);
            
            //Pega-se o formato do áudio
            AudioFormat formatoBase = audioInputStream.getFormat();
            
            //Decodifica esse formato
            AudioFormat formatoDecodificado = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    formatoBase.getSampleRate(),
                    16,
                    formatoBase.getChannels(),
                    formatoBase.getChannels() * 2,
                    formatoBase.getSampleRate(),
                    false
            );
            
            //Transforma o formatoDecodificado em AudioInputStream (audioDecodificado)
            AudioInputStream audioDecodificado = AudioSystem.getAudioInputStream(
                    formatoDecodificado, audioInputStream
            );
            
            //Pega um clip de áudio do sistema
            clip = AudioSystem.getClip();
            
            //Abre o arquivo de audioDecodificado
            clip.open(audioDecodificado);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrescrita: Tocavel">
    @Override
    public void play() {
        if (clip != null) {
            if (emPausa) {
                clip.setFramePosition(posicaoUltimoFrame);
                emPausa = false;
            }
            else{
                clip.setFramePosition(0);
            }
            
            clip.start();
        }
    }
    
    @Override
    public void pause() {
         if (clip != null && clip.isRunning()) {
            emPausa = true;
            posicaoUltimoFrame  = clip.getFramePosition();
            stop();
         }
    }
    
    @Override
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    @Override
    public void close() {
        if (clip != null) {
            stop();
            clip.close();
        }
    }
    
    @Override
    public void replay(int qtdVezes) {
        if (clip != null && qtdVezes >= -1) {
            if (emPausa) {
                clip.setFramePosition(posicaoUltimoFrame);
                emPausa = false;
            }
            else{
                clip.setFramePosition(0);
            }
            
            clip.loop(qtdVezes);
        }
    }
    //</editor-fold>    
    
}
