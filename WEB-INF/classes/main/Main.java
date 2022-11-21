package main;

import user.User;

public class Main {
    public static void main(String[] args) throws Exception {
        User lynda = new User();
        lynda.setIdUser("USR0017");
        lynda.setGenre("feminin");
        User[] users = lynda.getProposition();
        for (User user : users) {
            System.out.println(user.getNom());
        }
    }
}