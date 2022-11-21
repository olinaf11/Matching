package main;

import user.User;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            User mertina = new User();
            mertina.setIdUser("USR0004");
            mertina.setNom("Mertina");
            mertina.setGenre("feminin");
            for (User user : mertina.getProposition()) {
                System.out.println(user.getNom());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}