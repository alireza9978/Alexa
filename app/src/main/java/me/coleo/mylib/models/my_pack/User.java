package me.coleo.mylib.models.my_pack;

import java.util.ArrayList;
import java.util.List;

import me.coleo.mylib.models.Profile;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private List<User> friends;
    private Address home;
    private Profile profile;

    public User(int id, String firstName, String lastName, Address home, Profile profile) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.home = home;
        this.friends = new ArrayList<>();
        this.profile = profile;
    }

    public void addFriend(User friend) {
        friends.add(friend);
    }

    public void removeFriend(User friend) {
        friends.remove(friend);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public void setHome(Address home) {
        this.home = home;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<User> getFriends() {
        return friends;
    }

    public Address getHome() {
        return home;
    }

    public Profile getProfile() {
        return profile;
    }
}
