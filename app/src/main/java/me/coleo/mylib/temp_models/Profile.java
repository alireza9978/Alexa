package me.coleo.mylib.temp_models;

import ir.coleo.alexa.AlexaLib.Ignore;
import ir.coleo.alexa.AlexaLib.Name;
import ir.coleo.alexa.AlexaLib.Unique;

@Name(nameTo = "fucking profile")
public class Profile {

    @Unique
    @Name(nameTo = "myId")
    private int id;
    @Unique
    @Ignore
    @Name(nameTo = "leave me")
    private String name;
    private int score;
    private String inviteCode;

    public Profile(int id, String name, int score, String inviteCode) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.inviteCode = inviteCode;
    }

    public Profile(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Profile(int id, String name, Address address) {
        this.id = id;
        this.name = name;
    }

    public Profile(int id, int score, String inviteCode) {
        this.id = id;
        this.score = score;
        this.inviteCode = inviteCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public String getInviteCode() {
        return inviteCode;
    }

}
