package br.usp.each.typerace.server;

public class Player {

    private final id;
    private int correct;
    private int wrong;
    private long time;

    public Player (int id){
        this.id = id;
        this.correct = 0;
        this.wrong = 0;
        this.time = 0;
    }

    public int getId(){
        return this.id;
    }

    public int getCorrect(){
        return this.correct;
    }

    public int getWrong(){
        return this.wrong;
    }

    public void increaseCorrect(){
        this.correct++;
    }

    public void increaseWrong(){
        this.wrong++;
    }

}