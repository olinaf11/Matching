package main;

import user.User;

public class Main {
    public static void main(String[] args) throws Exception {
        User user = new User("Tendry Ny Avo", "Tendry");
        user.setTable("users");
        user.setIdUser("USR0003");
        user.setGenre("masculin");
        User[] users = user.getProposition();
        for (User user2 : users) {
            System.out.println(user2.getNom());
        }
    }
}