package com.artiomtaliaronak.ipr1ppo;

public class User {
    private int id;
    private String username;
    private int score;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public static User activeUser = new User(-1,"",0);

    public User(int id, String username, int score){
        this.id = id;
        this.username = username;
        this.score = score;
    }

}

