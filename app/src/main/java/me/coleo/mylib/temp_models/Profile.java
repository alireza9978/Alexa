package me.coleo.mylib.temp_models;

import me.coleo.mylib.AlexaLib.Name;
import me.coleo.mylib.AlexaLib.Unique;

@Name(nameTo = "fucking profile")
public class Profile {

    @Unique
    @Name(nameTo = "myId")
    private int id;
    @Unique
    @Name(nameTo = "leave me")
    private String name;
    private int score;
    private String inviteCode;
    private Address address;

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
