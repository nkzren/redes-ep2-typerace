package br.usp.each.typerace.server;

import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Game {

    private long time;
    private ArrayList<String> words;
    private List<Player> players; 

    public Game(){

        makeWordList();
        
    }

    public void makeWordList(){
        String sentence = "the size and age of the cosmos are beyond ordinary human understanding lost somewhere between immensity eternity is our tiny planetary home";
        words = new ArrayList<String>(Arrays.asList(sentence.split(" ")));
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void removePlayer(Player player){
        this.players.remove(player);
    }

    public void startGame(){
        // time counter
    }

    public void finishGame(){
        // time counter
    }

    public List<Player> ranking(){
        Collections.sort(players);
        return players;
    }


}