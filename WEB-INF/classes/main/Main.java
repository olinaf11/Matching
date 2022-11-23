package main;

import user.User;

public class Main {
    public static void main(String[] args) throws Exception {
        User tendry = new User();
        tendry.setIdUser("USR0060");
        tendry.setGenre("feminin");
        System.out.println(tendry.getProposition().length);
    }
}