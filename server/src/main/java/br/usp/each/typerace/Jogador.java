package br.usp.each.typerace;

import java.util.List;

public class Jogador {

    private String idJogador;
    private List<String> palavras;
    private int acertos;

    public Jogador(String idJogador, List<String> palavras){
        this.idJogador = idJogador;
        this.palavras = palavras;
        this.acertos =0;
    }

    public boolean verificaPalavra(String palavra){
        if(this.palavras.contains(palavra)){
            palavras.remove(palavra);
            acertos++;
            return true;
    }
    return false;
    }
    public String PalavrasMessage(){
        String resp = "";
        if(this.palavras.size() == 0) resp = "Parabens, você terminou as suas palavras. Digite stop para terminar o jogo para todos, ou de mais um tempo para eles";
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
    public void setPalavras(List<String> palavras){
        this.palavras = palavras;
    }
}

