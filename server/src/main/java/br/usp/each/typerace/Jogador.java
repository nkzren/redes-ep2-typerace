package br.usp.each.typerace;


import java.util.Set;
import java.util.HashSet;

public class Jogador {

    private String idJogador;
    private Set<String> palavras;
    private int acertos;

    public Jogador(String idJogador, Set<String> palavras){
        this.idJogador = idJogador;
        this.palavras = palavras;
        this.acertos =0;
    }

    public boolean verificaPalavra(String palavra){
        if(this.palavras.contains(palavra)){
            this.palavras.remove(palavra);
            this.acertos++;
            return true;
    }
    return false;
    }
    public String PalavrasMessage(){
        String resp = "";
        if(this.palavras.size() == 0) resp = "Você terminou as suas palavras. Digite stop para terminar o jogo para todos, ou de mais um tempo para os outros jogadores";
        else for(String palavra : this.palavras){
            resp = palavra +" "+ resp;
        }
        return resp;
    }

    public String getIdJogador(){
        return this.idJogador;
    }
    public int getAcertos(){
        return this.acertos;
    }
    public void setPalavras(Set<String> palavras){
        this.palavras = new HashSet<>();
        this.palavras.addAll(palavras);
        this.acertos = 0;
    }
}

