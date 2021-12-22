package br.usp.each.typerace.server;

import java.util.HashSet;
import java.util.Set;

public class Player implements Comparable<Player>{
    private static int wordCount = 0; //Contador de total de palavras digitadas para todos os players
    private int errorQuantity;
    private Set<String> wordsMissing; // Palavras que faltam ser digitadas para o player
    private int lastWordTime; // Contagem da última palavra digitada
    private String idPlayer;
    private int acertos; 
    //Contrutor do player
    public Player(Set<String> palavras, String idPlayer){
        this.errorQuantity = 0;
        this.wordsMissing = new HashSet<>(palavras);
        this.lastWordTime = -1;
        this.acertos = 0;
        this.idPlayer = idPlayer;
    }
    //Verifica se palavra que chegou é correta:
    //se sim, remove ela do Set de palavras faltando;
    //se não, incrementa a quantidade de palavras que ele errou;
    //se a quantidade de palavras faltantes for igual a zero, devolve true
    public boolean contabilizeNewWord(String wordSent) {

        if(wordsMissing.contains(wordSent)){
            wordsMissing.remove(wordSent);
            wordCount++;
            acertos++;
            lastWordTime = wordCount;
            return wordsMissing.size()==0;
        }
        else
            this.errorQuantity++; 

        return false;
    }
    public int getErrorsQuantity(){
        return this.errorQuantity;
    }
    public int getAcertos(){
        return this.acertos;
    }
    public static void restartLastWordTimeClassification(){
        wordCount = 0;
    }

    //Comparador entre player para efetuar a classificação
    @Override
    public int compareTo(Player otherPlayer) {
        int qtdAcertosDif = this.getAcertos() - otherPlayer.getAcertos();
        if(qtdAcertosDif != 0)
            return qtdAcertosDif;
        else{
            int errorDif = otherPlayer.errorQuantity - this.errorQuantity;
            if(errorDif != 0)
                return errorDif;
            else
                return otherPlayer.lastWordTime - this.lastWordTime;    
        }
        
    }
    public String toString(){
        return this.idPlayer + ": " + "Acertos - " + this.acertos+ "; Erros - " + this.errorQuantity;
    }

}
