package me.coleo.mylib.temp_models;

public class Profile {

    private int id;
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
